package com.connection.emi.advise;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import com.connection.emi.common.MessageTypeConstant;
import com.connection.emi.exception.NoSuchElementException;
import com.connection.emi.exception.ServerError;
import com.connection.emi.exception.ValidationError;
import com.connection.emi.model.common.LoggingResponseModel;
import com.google.gson.Gson;
import com.google.gson.stream.MalformedJsonException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@Autowired
	Gson gson;
	
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ValidationError> inavlidArgument(ConstraintViolationException ex, WebRequest req) {
		 String msg= ex.getMessage().split(": ")[1];
		ValidationError ve=null;
		ve=ValidationError.builder().status(HttpStatus.UNPROCESSABLE_ENTITY.value())
				.errorMessage(msg)
				.path(req.getDescription(false)).build();
		
		 log.error(gson.toJson(LoggingResponseModel.builder()
	                .message(msg)
	                .messageTypeId(MessageTypeConstant.ERROR.getMessage())
	                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY)
	                .build()));
		return new ResponseEntity<>(ve, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity<ValidationError> inavlidArgumentexception(NumberFormatException ex, WebRequest req) {
		String msg="Number should be in numeric format only";
		ValidationError ve=ValidationError.builder().status(HttpStatus.UNPROCESSABLE_ENTITY.value())
				.errorMessage(msg)
				.path(req.getDescription(false)).build();
		
		 log.error(gson.toJson(LoggingResponseModel.builder()
	                .message(msg)
	                .messageTypeId(MessageTypeConstant.ERROR.getMessage())
	                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY)
	                .build()));
		return new ResponseEntity<>(ve, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MalformedJsonException.class)
	public ResponseEntity<ValidationError> invalidMalformedException(MalformedJsonException ex, WebRequest req) {
		String msg= "Please enter some value";
		ValidationError ve=null;
		ve=ValidationError.builder().status(HttpStatus.UNPROCESSABLE_ENTITY.value())
				.errorMessage(msg)
				.path(req.getDescription(false)).build();
		
		log.error(gson.toJson(LoggingResponseModel.builder()
                .message(msg)
                .messageTypeId(MessageTypeConstant.ERROR.getMessage())
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY)
                .build()));
		
		return new ResponseEntity<>(ve, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ServerError> serverErrorHandling(NoSuchElementException ex, HttpServletRequest req,HttpServletResponse res, WebRequest re) {
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		ex.printStackTrace(printWriter);
		String stackTrace = stringWriter.toString();
		
		String msg=ex.getMessage();
		ServerError se=null;
		se=ServerError.builder().timestamp(Instant.now().toEpochMilli())
				.status(HttpStatus.NOT_FOUND.value())
				.errorCode(MessageTypeConstant.ERROR.getMessage())
				.errorMessage(msg)
				.traceId(Instant.now().getEpochSecond())
				.errorDeatils(stackTrace)
				.path(re.getDescription(false)).build();
		return new ResponseEntity<>(se, HttpStatus.NOT_FOUND);

	}

}