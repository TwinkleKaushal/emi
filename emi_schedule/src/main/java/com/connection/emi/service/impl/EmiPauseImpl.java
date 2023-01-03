package com.connection.emi.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.connection.emi.exception.NoSuchElementException;
import com.connection.emi.model.EmiPauseModel;
import com.connection.emi.model.common.EmiPauseResponseModel;
import com.connection.emi.service.IEmiPause;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmiPauseImpl implements IEmiPause {
	double p;
	Integer t;
	double r;
	Integer postperiod;
	Integer totalLoanTenure;
	Integer numberOfEMI;
	Integer totalMoratoriumperiod;
	double calculatedEMI;
	double amountPay;
	double interestPay;
	double tenure;
	double balance;
	double increasedInterestIs;
	double newAmountToPay;
	/**
	 * This method is used to calculate EMI by using the standard mathematical
	 * formulae
	 */
	@Override
	public EmiPauseResponseModel calculatedEMIpause(EmiPauseModel pausemodel)  {
		log.info("Service class execution initiated");
		p = pausemodel.getLoanAmount();
		r = (pausemodel.getRateOfInterest() / 12);
		t = (pausemodel.getTenure() * 12);
		numberOfEMI = pausemodel.getNoOfEmiPaid();
		totalMoratoriumperiod = pausemodel.getMoratoriumPeriod();
		if (numberOfEMI >= t) {
			throw new NoSuchElementException ("Your EMI is already completed or you entered a wrong values, Please check once again!");
		}
		calculatedEMI = p * r / 100 * Math.pow(1 + r / 100, t) / (Math.pow(1 + r / 100, t) - 1);
		amountPay = calculatedEMI * t;
		interestPay = amountPay - p;
		double interestIs = 0;
		double totalInterest = 0;
		Integer increasedMonthCount = totalMoratoriumperiod;
		for (int i = 1; i <= increasedMonthCount; i++) {
			interestIs = p * r / 100;
			p = p + interestIs;
			totalInterest += interestIs;
		}
		increasedInterestIs = totalInterest;
		postperiod = (t - numberOfEMI) + totalMoratoriumperiod;
		totalLoanTenure = t + totalMoratoriumperiod;
		newAmountToPay = calculatedEMI * totalLoanTenure;
		log.info("Emi calculated");
		log.info("execution completed");
		/**
		 * Service level object of EmiPauseResponseModel
		 */
		EmiPauseResponseModel responsemodel = EmiPauseResponseModel.builder().status(HttpStatus.CREATED.value())
				.message("EMI Calculation is successfully completed").emi(Math.round(calculatedEMI))
				.interest(Math.round(interestPay)).increasedInterest(Math.round(increasedInterestIs))
				.postMoratoriumPeriod(postperiod).overAllLoanTenure(totalLoanTenure).build();
		return responsemodel;
	}
}
