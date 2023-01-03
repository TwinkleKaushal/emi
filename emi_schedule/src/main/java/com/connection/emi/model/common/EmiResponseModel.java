package com.connection.emi.model.common;

import java.util.List;
import com.connection.emi.model.AmortizationDetailsModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class EmiResponseModel {
	Integer status;
	String message;
	long emi;
	long interest;
	long TotalAmountPayable;
	List<AmortizationDetailsModel> Amortizationtable;
	}
