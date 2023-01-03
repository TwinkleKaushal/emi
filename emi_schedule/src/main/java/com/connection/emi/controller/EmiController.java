package com.connection.emi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.connection.emi.model.EmiModel;
import com.connection.emi.model.EmiPauseModel;
import com.connection.emi.model.common.EmiPauseResponseModel;
import com.connection.emi.model.common.EmiResponseModel;
import com.connection.emi.service.impl.EmiPauseImpl;
import com.connection.emi.service.impl.EmiServiceImpl;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping("/v1/loans")
public class EmiController {
	@Autowired
	EmiServiceImpl emiservice;
	@Autowired
	EmiPauseImpl emiPauseImpl;
	@Autowired
	Gson gson;

	/**
	 * @param emidto object of Emidto class that is used to hold the data for the
	 *               call
	 * @throws Exception throws exception if there is error to call the service call
	 *                   method
	 */
	@PostMapping("/emi/schedule")
	public ResponseEntity<EmiResponseModel> emiCalculator(@RequestBody String emidto) throws Exception {
		log.info("Inside controller");
		EmiModel model = gson.fromJson(emidto, EmiModel.class);
		return new ResponseEntity<>(emiservice.GenerateEMI(model), HttpStatus.CREATED);

	}
	/**
	 * 
	 * @param pauseDto This is the object of EmiPauseDto which contains
	 * @return This methods call's the service class and returns the calculated EMI
	 *         after some pause which is called EMI moratorium
	 * @throws Exception it will throw the exception if there is some error in
	 *                   calculating EMI
	 */
	@PostMapping("/emiPause/samePaymentANDextendedTerm")
	public ResponseEntity<EmiPauseResponseModel> emiGenetarion(@RequestBody String pauseDto) throws Exception {
		log.info("Inside controller");
		EmiPauseModel pausemodel = gson.fromJson(pauseDto, EmiPauseModel.class);
		return new ResponseEntity<>(emiPauseImpl.calculatedEMIpause(pausemodel), HttpStatus.CREATED);

	}

}
