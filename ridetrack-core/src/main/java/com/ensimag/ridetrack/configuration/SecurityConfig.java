package com.ensimag.ridetrack.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ensimag.ridetrack.auth.RtUserManager;
import com.ensimag.ridetrack.auth.TokenProvider;
import com.ensimag.ridetrack.jwt.RtAuthenticationEntryPoint;
import com.ensimag.ridetrack.auth.TokenRequestFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final String[] AUTH_WHITELIST = {
			"/v2/api-docs",
			"/swagger-resources",
			"/swagger-resources/**",
			"/configuration/ui",
			"/configuration/security",
			"/swagger-ui.html",
			"/webjars/**",
			"/"
	};
	
	private final RtAuthenticationEntryPoint authEntryPoint;
	
	private final TokenProvider tokenProvider;
	
	public SecurityConfig(
			@Autowired TokenProvider tokenProvider,
			@Autowired RtAuthenticationEntryPoint authEntryPoint) {
		this.authEntryPoint = authEntryPoint;
		this.tokenProvider = tokenProvider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// @formatter:off
		httpSecurity
				.csrf()
					.disable()
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
					.exceptionHandling().authenticationEntryPoint(authEntryPoint)
				.and()
					.addFilterBefore(new TokenRequestFilter("/api/**", tokenProvider, userDetailsService()), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
					.antMatchers(HttpMethod.POST,"/authenticate").permitAll()
				.anyRequest().authenticated();
		// @formatter:on
	}
	
	@Override
	public void configure(WebSecurity webSecurity) {
		webSecurity
				.ignoring().antMatchers(AUTH_WHITELIST);
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth, RtUserManager userDetailsService) throws Exception {
		auth.userDetailsService(userDetailsService).userDetailsPasswordManager(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
