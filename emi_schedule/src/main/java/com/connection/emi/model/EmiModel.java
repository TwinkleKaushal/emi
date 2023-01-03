package com.connection.emi.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmiModel {
	@Range(min = 25000, max = 500000000, message = "Please enter value between 25000-50000000")
	@NotNull(message = "Please enter some loan value")
	long loanAmount;
	@NotNull(message = "rate of interest cannot be null")
	@Range(min = 1, max = 100, message = "Please enter valid rate of interest")
	@Digits(fraction = 2, integer = 3, message = "Please enter valid rate of interest i.e. only two decimal digits are allowed")
	double rateOfInterest;
	@NotNull(message = "can't be null")
	Integer tenure;

}
