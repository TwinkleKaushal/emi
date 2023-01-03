package com.connection.emi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import com.connection.emi.model.EmiPauseModel;
import com.connection.emi.model.common.EmiPauseResponseModel;
import com.connection.emi.service.IEmiPause;

@SpringBootTest
class EmiPauseserviceTest {
	
	@Autowired
	IEmiPause emipause;

	@Test
	@Order(1)
	void testingResultOfEmiPause() {
		EmiPauseModel pauseModel = new EmiPauseModel(200000, 5, 2, 2, 2);
		emipause.calculatedEMIpause(pauseModel);
		EmiPauseResponseModel expectedResponse = new EmiPauseResponseModel(HttpStatus.CREATED.value(),
				"EMI Calculation is successfully completed", 8774, 10583, 1670, 24, 26);
		assertEquals(emipause.calculatedEMIpause(pauseModel), expectedResponse);
	}
}
