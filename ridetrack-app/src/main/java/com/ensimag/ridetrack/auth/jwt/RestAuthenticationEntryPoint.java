package com.ensimag.ridetrack.auth.jwt;

import java.io.IOException;
import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.ensimag.ridetrack.rest.exceptions.RestErrorInfo;
import com.fasterxml.jackson.databind.ObjectMapper;


public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
		throws IOException {
		String message = "Unauthorized";
		
		if (authException instanceof CredentialsExpiredException) {
			message = "Expired credentials";
		}
		
		RestErrorInfo errorResponse = RestErrorInfo.builder()
				.error("error.unauthorized")
				.message(message)
				.status(HttpServletResponse.SC_FORBIDDEN)
				.path(request.getServletPath())
				.timestamp(OffsetDateTime.now().toString())
				.build();
		
		ObjectMapper mapper = new ObjectMapper();
		response.getWriter().write(mapper.writeValueAsString(errorResponse));
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setContentType("application/json");
	}
}
