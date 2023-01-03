package com.connection.emi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServerError {
	long timestamp;
	Integer status;
	String errorCode;
	String errorMessage;
	long traceId;
	String errorDeatils;
	String path;
}
