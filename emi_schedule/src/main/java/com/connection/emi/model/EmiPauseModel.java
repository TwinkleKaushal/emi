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
@AllArgsConstructor
@NoArgsConstructor
public class EmiPauseModel {
	@Range(min = 25000, max = 500000000, message = "Please enter value between 25000-50000000")
	@NotNull(message = "Please enter some loan value")
	Integer loanAmount;
	@NotNull(message = "Please enter some loan value")
	@Digits(fraction = 2, integer = 3, message = "Please enter valid rate of interest i.e. only two decimal digits are allowed")
	double rateOfInterest;
	@NotNull(message = "Please enter some loan value")
	Integer tenure;
	@NotNull(message = "Please enter some loan value")
	Integer noOfEmiPaid;
	@NotNull(message = "Please enter some loan value")
	Integer moratoriumPeriod;
}
