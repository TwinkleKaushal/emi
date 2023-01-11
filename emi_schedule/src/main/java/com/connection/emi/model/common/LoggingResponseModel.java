package com.connection.emi.model.common;

import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoggingResponseModel {
	
	private HttpStatus statusCode;
	private String message;
	private Integer messageTypeId;

}
