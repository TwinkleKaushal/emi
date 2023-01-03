package com.connection.emi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.connection.emi.model.EmiModel;
import com.connection.emi.service.IEmiService;

@SpringBootTest
class EmiServiceTest {

	@Autowired
	IEmiService emiservice;

	@Test
	void testingResultOfEmiPause() {
		EmiModel model = new EmiModel(400000, 7, 1);
		long actualEMI = emiservice.GenerateEMI(model).getEmi();
		long actualinterest = emiservice.GenerateEMI(model).getInterest();
		long actualAmountToPay = emiservice.GenerateEMI(model).getTotalAmountPayable();
		long expectedEMi = 34611;
		long expectedInterest = 15328;
		long expectedAmountToPay = 415328;
		assertEquals(expectedEMi, actualEMI);
		assertEquals(expectedInterest, actualinterest);
		assertEquals(expectedAmountToPay, actualAmountToPay);

	}

}
