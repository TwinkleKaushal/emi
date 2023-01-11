package com.connection.emi.service;

import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import com.connection.emi.model.EmiModel;
import com.connection.emi.model.common.EmiResponseModel;

@Validated
public interface IEmiService {
	/**
	 * 
	 * @param model The object of EMimodel passed as a argument which contains loan
	 *              amount,rate of interest and tenure
	 * @return This method returns the schedule for calculated EMI
	 * @throws Exception Exception is thrown when there is some problem in EMI
	 *                   schedule
	 */
	public EmiResponseModel GenerateEMI(@Valid EmiModel model);

	/**
	 * 
	 * @param p variable to store the value of loan amount
	 * @param r variable to store the value of rate of interest
	 * @param t variable to store the value of tenure period in month's
	 * @return This method returns the calculated EMI
	 * @throws Exception Exception is thrown when there is some problem in EMI
	 *                   calculation
	 */
	public void calculatedEMI(Integer p, double r, Integer t);
}
