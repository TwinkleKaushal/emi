package com.connection.emi.advise;

import java.time.Instant;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import com.connection.emi.exception.NoSuchElementException;
import com.connection.emi.exception.ServerError;
import com.connection.emi.exception.ValidationError;
import com.google.gson.stream.MalformedJsonException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ValidationError> inavlidArgument(ConstraintViolationException ex, WebRequest req) {
		ValidationError ve = new ValidationError(HttpStatus.UNPROCESSABLE_ENTITY.value(),
				ex.getMessage().split(": ")[1], req.getDescription(false));
		return new ResponseEntity<>(ve, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity<ValidationError> inavlidArgumentexception(NumberFormatException ex, WebRequest req) {
		ValidationError ve = new ValidationError(HttpStatus.UNPROCESSABLE_ENTITY.value(),
				"Number should be in numeric format only", req.getDescription(false));
		return new ResponseEntity<>(ve, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MalformedJsonException.class)
	public ResponseEntity<ValidationError> invalidMalformedException(MalformedJsonException ex, WebRequest req) {
		ValidationError ve = new ValidationError(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Please enter some value",
				req.getDescription(false));
		return new ResponseEntity<>(ve, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ServerError> serverErrorHandling(NoSuchElementException ex, HttpServletRequest req,HttpServletResponse res, WebRequest re) {
		ServerError se = new ServerError(Instant.now().toEpochMilli(), HttpStatus.NOT_FOUND.value(), "1",
				ex.getMessage(), Instant.now().getEpochSecond(), ex.getMessage(), re.getDescription(false));
		return new ResponseEntity<>(se, HttpStatus.NOT_FOUND);

	}

}