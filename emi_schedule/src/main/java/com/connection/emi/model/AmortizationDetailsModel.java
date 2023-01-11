package com.connection.emi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmortizationDetailsModel {
	
	int currentYear;
	String currentMonth;
	double emi;
	double interest;
	double principal;
	double outstanding;
}
