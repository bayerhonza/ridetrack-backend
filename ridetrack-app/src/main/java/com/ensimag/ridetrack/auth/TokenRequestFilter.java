package com.ensimag.ridetrack.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import lombok.extern.slf4j.Slf4j;

/**
 * Filter for token
 */
@Slf4j
public class TokenRequestFilter extends GenericFilterBean {
	
	private final TokenProvider tokenProvider;
	
	private final UserDetailsService userDetailsService;
	
	private final AuthenticationEntryPoint entryPoint;
	
	private final RequestMatcher requiresAuthenticationRequestMatcher;
	
	/**
	 * Constructor
	 * @param antPattern pattern to filter
	 * @param tokenProvider token of provider
	 * @param userDetailsService user service service
	 * @param entryPoint entry point
	 */
	public TokenRequestFilter(String antPattern,
			TokenProvider tokenProvider,
			UserDetailsService userDetailsService,
			AuthenticationEntryPoint entryPoint) {
		this.tokenProvider = tokenProvider;
		this.userDetailsService = userDetailsService;
		this.entryPoint = entryPoint;
		requiresAuthenticationRequestMatcher = new AntPathRequestMatcher(antPattern);
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		if (!requiresAuthentication(request)) {
			chain.doFilter(request, response);
			return;
		}
		
		final String jwtToken = resolveToken(request);
		
		if (!StringUtils.hasText(jwtToken)) {
			chain.doFilter(request, response);
			return;
		}
		try {
			tokenProvider.validateToken(jwtToken);
			
			UserDetails principal = userDetailsService.loadUserByUsername(tokenProvider.getUsernameFromToken(jwtToken));
			UsernamePasswordAuthenticationToken authToken =
					new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
			authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			SecurityContext context = SecurityContextHolder.createEmptyContext();
			context.setAuthentication(authToken);
			SecurityContextHolder.setContext(context);
			
			chain.doFilter(request, response);
		} catch (AuthenticationException ex) {
			onAuthenticationFailure(request, response, ex);
		}
	}
	
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		entryPoint.commence(request, response, exception);
	}
	
	protected boolean requiresAuthentication(HttpServletRequest request) {
		return requiresAuthenticationRequestMatcher.matches(request);
	}
	
	protected String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
