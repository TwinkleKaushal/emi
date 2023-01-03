package com.connection.emi.model.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmiPauseResponseModel {
	Integer status;
	String message;
	long emi;
	long interest;
	long increasedInterest;
	Integer postMoratoriumPeriod;
	Integer overAllLoanTenure;

}
