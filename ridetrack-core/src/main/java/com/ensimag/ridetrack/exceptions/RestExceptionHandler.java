package com.ensimag.ridetrack.exceptions;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@ControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
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
			.message(ex.getLocalizedMessage())
			.error(String.join(",", errors))
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

	@ExceptionHandler({DataIntegrityViolationException.class})
	public ResponseEntity<RestErrorInfo> handleDataIntegrityException(HttpServletRequest req,
		DataIntegrityViolationException ex) {
		RestErrorInfo restErrorInfo = RestErrorInfo.builder()
			.path(req.getServletPath())
			.status(HttpStatus.CONFLICT.value())
			.timestamp(OffsetDateTime.now().toString())
			.build();
		if (ex.getCause() instanceof ConstraintViolationException) {
			ConstraintViolationException constraintViolationEx = (ConstraintViolationException) ex
				.getCause();
			String constraintName = constraintViolationEx.getConstraintName();
			restErrorInfo.setMessage(
				messageService.translateCodeIntoMsg(constraintName));
			restErrorInfo.setError(messageService.getConstraintErrorCode(constraintName));
		}

		return new ResponseEntity<>(restErrorInfo, new HttpHeaders(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler({Exception.class})
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest req) {
		RestErrorInfo restErrorInfo = RestErrorInfo.builder()
			.path(req.getContextPath())
			.message(ex.getLocalizedMessage())
			.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
			.timestamp(OffsetDateTime.now().toString())
			.error("error occurred")
			.build();
		return new ResponseEntity<>(
			restErrorInfo, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}


}
