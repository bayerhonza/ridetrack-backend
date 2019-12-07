package com.ensimag.ridetrack.auth;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

@ExtendWith(MockitoExtension.class)
class TokenRequestFilterTest {
	
	@Mock
	private TokenProvider tokenProvider;
	
	@Mock
	private UserDetailsService userDetailsService;
	
	private TokenRequestFilter instance;
	
	@BeforeEach
	public void setUp() {
		instance = new TokenRequestFilter("/testing", tokenProvider, userDetailsService);
	}
	
	@Test
	public void testResolveToken_OK() {
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		when(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer 123456789");
		
		String result = instance.resolveToken(httpServletRequest);
		
		assertEquals("123456789", result);
	}
	
	@Test
	public void testResolveToken_KO() {
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		when(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("qqsdfqsfqsdfqsdfqsdf");
		
		String result = instance.resolveToken(httpServletRequest);
		
		assertNull(result);
	}
	
	@Test
	public void testFiltering_withToken() throws ServletException, IOException {
		MockHttpServletRequest req = new MockHttpServletRequest("POST", "/testing");
		req.setServletPath("/testing");
		req.addHeader(HttpHeaders.AUTHORIZATION, "Bearer 123456789");
		HttpServletResponse response = mock(HttpServletResponse.class);
		FilterChain filterChain = mock(FilterChain.class);
		
		RtUserPrincipal userDetails = new RtUserPrincipal("username1", "pwwd1234", Collections.emptySet());
		
		when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);
		when(tokenProvider.getUsernameFromToken("123456789")).thenReturn("username1");
		
		instance.doFilter(req, response, filterChain);
		
		verify(userDetailsService, times(1)).loadUserByUsername("username1");
	}
	
	@Test
	public void testFiltering_tokenKO() throws ServletException, IOException {
		MockHttpServletRequest req = new MockHttpServletRequest("POST", "/testing");
		HttpServletResponse response = mock(HttpServletResponse.class);
		FilterChain filterChain = mock(FilterChain.class);
		
		instance.doFilter(req, response, filterChain);
		
		verify(userDetailsService, times(0)).loadUserByUsername(anyString());
	}
	
	@Test
	public void testFiltering_authNotRequired() throws ServletException, IOException {
		HttpServletRequest req = new MockHttpServletRequest("POST", "/auth_not_required");
		HttpServletResponse response = mock(HttpServletResponse.class);
		FilterChain filterChain = mock(FilterChain.class);
		
		instance.doFilter(req, response, filterChain);
		
		verify(userDetailsService, times(0)).loadUserByUsername(anyString());
	}
}