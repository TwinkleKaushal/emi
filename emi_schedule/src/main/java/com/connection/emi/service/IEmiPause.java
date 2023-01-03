package com.connection.emi.service;

import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import com.connection.emi.model.EmiPauseModel;
import com.connection.emi.model.common.EmiPauseResponseModel;

@Validated
public interface IEmiPause {
	/**
	 * 
	 * @param pausemodel The object of EmiPauseModel passed as a argument which
	 *                   contains some variable that we used in calculation
	 * @return This method returns the calculated EMI , total Interest and interest
	 *         after the moratorium period and overall loan tenure
	 * @throws Exception Exception is thrown when there is some problem in
	 *                   calculating EMI
	 */
	public EmiPauseResponseModel calculatedEMIpause(@Valid EmiPauseModel pausemodel);
}
