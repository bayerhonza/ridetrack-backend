package com.ensimag.ridetrack.rest.exceptions;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ensimag.ridetrack.exception.RestException;
import com.ensimag.ridetrack.exception.RidetrackConflictException;
import com.ensimag.ridetrack.exception.RidetrackNotFoundException;
import com.ensimag.ridetrack.exception.RidetrackValidationException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	private final RestExceptionMessageService messageService;

	public RestExceptionHandler(RestExceptionMessageService messageService) {
		this.messageService = messageService;

	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(
		NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

		RestErrorInfo restErrorInfo = RestErrorInfo.builder()
			.status(HttpStatus.NOT_FOUND.value())
			.message(ex.getLocalizedMessage())
			.error(error)
			.timestamp(OffsetDateTime.now().toString())
			.path(request.getContextPath())
			.build();
		return new ResponseEntity<>(restErrorInfo, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
		MethodArgumentNotValidException ex,
		HttpHeaders headers,
		HttpStatus status,
		WebRequest request) {

		List<String> errors = new ArrayList<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		RestErrorInfo restErrorInfo = RestErrorInfo.builder()
			.status(HttpStatus.BAD_REQUEST.value())
			.message(String.join(",", errors))
			.error("error.rest.validation")
			.build();
		return handleExceptionInternal(ex, restErrorInfo, headers, HttpStatus.BAD_REQUEST,
			request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
		MissingServletRequestParameterException ex, HttpHeaders headers,
		HttpStatus status, WebRequest request) {
		String error = ex.getParameterName() + " parameter is missing";

		RestErrorInfo restErrorInfo = RestErrorInfo.builder()
			.status(HttpStatus.BAD_REQUEST.value())
			.message(ex.getLocalizedMessage())
			.error(error)
			.timestamp(OffsetDateTime.now().toString())
			.path(request.getContextPath())
			.build();
		return handleExceptionInternal(ex, restErrorInfo, headers, HttpStatus.BAD_REQUEST,
			request);
	}

	@ExceptionHandler({DataIntegrityViolationException.class, RidetrackConflictException.class})
	public ResponseEntity<RestErrorInfo> handleDataIntegrityException(WebRequest req,
		Exception ex) {
		if (ex.getCause() instanceof ConstraintViolationException) {
			ConstraintViolationException constraintViolationEx = (ConstraintViolationException) ex
				.getCause();
			String constraintName = constraintViolationEx.getConstraintName();
			String errorCode = messageService.translateCodeIntoMsg(constraintName);
			String errorMsg = messageService.getConstraintErrorCode(constraintName);
			return buildResponseEntity(errorCode, errorMsg, HttpStatus.CONFLICT, req);
		} else if (ex instanceof RidetrackConflictException) {
			RidetrackConflictException conflictException = (RidetrackConflictException) ex;
			String constraintName = conflictException.getConflictingProperty();
			String errorCode = messageService.translateCodeIntoMsg(constraintName);
			String errorMsg = messageService.getConstraintErrorCode(constraintName);
			return buildResponseEntity(errorCode, errorMsg, HttpStatus.CONFLICT, req);
		}
		return buildResponseEntity("error occurred", HttpStatus.CONFLICT, ex, req);
	}
	
	@ExceptionHandler({ RestException.class })
	public ResponseEntity<RestErrorInfo> handleHttpRestException(RestException ex, WebRequest req) {
		return buildResponseEntity(ex.getLocalizedMessage(), ex.getStatusCode(), ex, req);
	}
	
	@ExceptionHandler({ AccessDeniedException.class, AuthenticationException.class })
	public ResponseEntity<RestErrorInfo> handleSpringException(Exception ex, WebRequest req) {
		return buildResponseEntity(ex.getLocalizedMessage(), HttpStatus.UNAUTHORIZED, ex, req);
	}
	
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<RestErrorInfo> handleAll(Exception ex, WebRequest req) {
		log.debug("Internal error stack trace: ", ex);
		return buildResponseEntity("error occurred", HttpStatus.INTERNAL_SERVER_ERROR, ex, req);
	}
	
	@ExceptionHandler({ RidetrackValidationException.class })
	public ResponseEntity<RestErrorInfo> handleValidationException(RidetrackValidationException ex,
			WebRequest req) {
		String errorCode = "error.rest." + ex.getEntityName() + "." + ex.getProperty() + ".nonValid";
		return buildResponseEntity(errorCode, HttpStatus.BAD_REQUEST, ex, req);
	}

	@ExceptionHandler({RidetrackNotFoundException.class})
	public ResponseEntity<RestErrorInfo> handleNotFoundException(RidetrackNotFoundException ex,
		WebRequest req) {
		return buildResponseEntity("error.rest.notFound", HttpStatus.NOT_FOUND, ex, req);
	}

	private ResponseEntity<RestErrorInfo> buildResponseEntity(String errorCode, HttpStatus status,
		Exception ex, WebRequest request) {
		return buildResponseEntity(ex.getLocalizedMessage(), errorCode, status, request);
	}

	private ResponseEntity<RestErrorInfo> buildResponseEntity(String errorCode, String errorMsg,
		HttpStatus status, WebRequest request) {
		RestErrorInfo restErrorInfo = RestErrorInfo.builder()
			.path(request.getContextPath())
			.message(errorMsg)
			.status(status.value())
			.timestamp(OffsetDateTime.now().toString())
			.error(errorCode)
			.build();

		return new ResponseEntity<>(
			restErrorInfo, new HttpHeaders(), status);
	}


}
