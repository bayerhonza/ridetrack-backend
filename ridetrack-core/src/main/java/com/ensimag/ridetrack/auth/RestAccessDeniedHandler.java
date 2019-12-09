package com.ensimag.ridetrack.auth;
import java.io.IOException;
import java.time.OffsetDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.ensimag.ridetrack.rest.exceptions.RestErrorInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RestAccessDeniedHandler implements AccessDeniedHandler {
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		RestErrorInfo errorResponse = RestErrorInfo.builder()
				.error("error.access.denied")
				.message("Access denied")
				.status(HttpStatus.FORBIDDEN.value())
				.path(request.getServletPath())
				.timestamp(OffsetDateTime.now().toString())
				.build();
		
		ObjectMapper mapper = new ObjectMapper();
		response.getWriter().write(mapper.writeValueAsString(errorResponse));
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		
	}
}
