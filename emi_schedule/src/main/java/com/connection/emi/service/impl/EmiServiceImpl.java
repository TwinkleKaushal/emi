package com.connection.emi.service.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.connection.emi.model.AmortizationDetailsModel;
import com.connection.emi.model.EmiModel;
import com.connection.emi.model.common.EmiResponseModel;
import com.connection.emi.model.common.LoggingResponseModel;
import com.connection.emi.service.IEmiService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

/**
 * This class is used to generate EMI and the amortization schedule with
 * respective to that EMI
 */
@Slf4j
@Service
public class EmiServiceImpl implements IEmiService {
	@Autowired
	Gson gson;

	Integer p;
	Integer t;
	double r;
	double calculatedEMI;
	double amountPay;
	double InterestPay;
	double tenure;
	double balance;
	final DecimalFormat df = new DecimalFormat("0.00");

	/**
	 * This method is used to calculate EMI
	 */
	@Override
	public void calculatedEMI(Integer p, double r, Integer t) {
		try {
			calculatedEMI = p * r / 100 * Math.pow(1 + r / 100, t) / (Math.pow(1 + r / 100, t) - 1);
			amountPay = calculatedEMI * t;
			InterestPay = amountPay - p;
		} catch (Exception e) {
			log.error("error in calculating EMI", e);
		}
	}

	/**
	 * this method is used to generate the amortization schedule
	 * 
	 * @param calculatedEMI variable to store the calculated EMI
	 * 
	 * @param amountPay     variable to store the Total amount to pay after specific
	 *                      interest charges
	 * 
	 * @param InterestPay   variable to store the total interest pay on the given
	 *                      loan amount
	 * 
	 * @param tenure        variable to store the loan period by years
	 * 
	 * @param balance       variable to check the remaining outstanding balance
	 *                      after each EMI deposit
	 */
	@Override
	public EmiResponseModel GenerateEMI(EmiModel model) {

		LoggingResponseModel msgStart = LoggingResponseModel.builder().message("Service class execution initiated")
				.build();
		log.info(gson.toJson(msgStart));
		List<AmortizationDetailsModel> Amortizationtable = new ArrayList<>();
		p = (int) model.getLoanAmount();
		r = (model.getRateOfInterest() / 12);
		t = (model.getTenure() * 12);
		try {

			calculatedEMI(p, r, t);
			SimpleDateFormat format = new SimpleDateFormat("yyyy");
			String formattedDate = format.format(new Date());
			int year = Integer.parseInt(formattedDate);

			Month month = Month.from(LocalDate.now());
			int currentMonth = month.getValue();
			tenure = model.getTenure();
			if (currentMonth != 1) {
				tenure = tenure + 1;
			}
			for (int i = 0; i < tenure * 12; i++) {

				AmortizationDetailsModel modelDetails = new AmortizationDetailsModel();
				modelDetails.setCurrentYear(year);
				String nextMonth = month.plus(i).name();
				modelDetails.setCurrentMonth(nextMonth);
				if (nextMonth.equals("DECEMBER"))
					year++;
				double paidInterest = p * (model.getRateOfInterest() / (12 * 100));
				double paidPrincipal = calculatedEMI - paidInterest;
				balance = amountPay - calculatedEMI;
				modelDetails.setEmi(Double.parseDouble(df.format(calculatedEMI)));
				modelDetails.setInterest(Double.parseDouble(df.format(paidInterest)));
				modelDetails.setPrincipal(Double.parseDouble(df.format(paidPrincipal)));
				modelDetails.setOutstanding(Double.parseDouble(df.format(balance)));
				Amortizationtable.add(modelDetails);
				p = (int) (p - paidPrincipal);
				amountPay = balance;
				if (modelDetails.getOutstanding() <= 0)
					break;
			}
			LoggingResponseModel msgEnd = LoggingResponseModel.builder().message("Emi calculated").build();
			log.info(gson.toJson(msgEnd));

		} catch (NumberFormatException e) {
			throw new NumberFormatException("Number should be in numeric format only");
		}
		/**
		 * Service level object of EmiResponseModel
		 */
		EmiResponseModel responsemodel = EmiResponseModel.builder().status(HttpStatus.CREATED.value())
				.message("EMI Calculation is successfully completed").emi(Math.round(calculatedEMI))
				.interest(Math.round(InterestPay)).TotalAmountPayable(Math.round(calculatedEMI * t))
				.Amortizationtable(Amortizationtable).build();
		return responsemodel;
	}
}
