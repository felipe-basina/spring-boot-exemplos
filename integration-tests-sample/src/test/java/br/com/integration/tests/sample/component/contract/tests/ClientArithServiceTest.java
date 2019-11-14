package br.com.integration.tests.sample.component.contract.tests;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.integration.tests.sample.services.ClientArithService;
import br.com.integration.tests.sample.types.ArithOperations;
import br.com.integration.tests.sample.vo.ArithParams;
import br.com.integration.tests.sample.vo.ArithResponseVO;
import br.com.integration.tests.sample.vo.DefaultArithRequestVO;
import br.com.integration.tests.sample.vo.PowerArithRequestVO;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ClientArithServiceTest {

	@Autowired
	private ClientArithService clientArithService;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSum() {
		ArithParams arithParams = this.createDefaultArithParams(this.values(), ArithOperations.SUM);

		ArithResponseVO<?> arithResponseVO = this.clientArithService.doArithOperation(arithParams);
		Assert.assertNotNull(arithResponseVO);
		Assert.assertNotNull(arithResponseVO.getResult());
		Assert.assertTrue(this.isSuccessHttpResponse(arithResponseVO.getHttpStatusCode()));
		Assert.assertTrue(this.isANumber(arithResponseVO.getResult()));
	}

	@Test
	public void testSumEmptyList() {
		ArithParams arithParams = this.createDefaultArithParams(Collections.emptyList(), ArithOperations.SUM);

		ArithResponseVO<?> arithResponseVO = this.clientArithService.doArithOperation(arithParams);
		Assert.assertNotNull(arithResponseVO);
		Assert.assertNotNull(arithResponseVO.getResult());
		Assert.assertFalse(this.isSuccessHttpResponse(arithResponseVO.getHttpStatusCode()));
		Assert.assertFalse(this.isANumber(arithResponseVO.getResult()));
	}

	@Test
	public void testSumInvalidList() {
		ArithParams arithParams = this.createDefaultArithParams(null, ArithOperations.SUM);

		ArithResponseVO<?> arithResponseVO = this.clientArithService.doArithOperation(arithParams);
		Assert.assertNotNull(arithResponseVO);
		Assert.assertNotNull(arithResponseVO.getResult());
		Assert.assertFalse(this.isSuccessHttpResponse(arithResponseVO.getHttpStatusCode()));
		Assert.assertFalse(this.isANumber(arithResponseVO.getResult()));
	}

	@Test
	public void testSubtract() {
		ArithParams arithParams = this.createDefaultArithParams(this.values(), ArithOperations.SUBTRACT);

		ArithResponseVO<?> arithResponseVO = this.clientArithService.doArithOperation(arithParams);
		Assert.assertNotNull(arithResponseVO);
		Assert.assertNotNull(arithResponseVO.getResult());
		Assert.assertTrue(this.isSuccessHttpResponse(arithResponseVO.getHttpStatusCode()));
		Assert.assertTrue(this.isANumber(arithResponseVO.getResult()));
	}

	@Test
	public void testMultiply() {
		ArithParams arithParams = this.createDefaultArithParams(this.values(), ArithOperations.MULTIPLY);

		ArithResponseVO<?> arithResponseVO = this.clientArithService.doArithOperation(arithParams);
		Assert.assertNotNull(arithResponseVO);
		Assert.assertNotNull(arithResponseVO.getResult());
		Assert.assertTrue(this.isSuccessHttpResponse(arithResponseVO.getHttpStatusCode()));
		Assert.assertTrue(this.isANumber(arithResponseVO.getResult()));
	}

	@Test
	public void testDivide() {
		ArithParams arithParams = this.createDefaultArithParams(this.values(), ArithOperations.DIVISION);

		ArithResponseVO<?> arithResponseVO = this.clientArithService.doArithOperation(arithParams);
		Assert.assertNotNull(arithResponseVO);
		Assert.assertNotNull(arithResponseVO.getResult());
		Assert.assertTrue(this.isSuccessHttpResponse(arithResponseVO.getHttpStatusCode()));
		Assert.assertTrue(this.isANumber(arithResponseVO.getResult()));
	}

	@Test
	@SuppressWarnings({ "unchecked" })
	public void testDivideByZero() {
		List<BigDecimal> values = this.values();
		values = new ArrayList<>(values);
		values.add(BigDecimal.ZERO);

		ArithParams arithParams = this.createDefaultArithParams(values, ArithOperations.DIVISION);

		ArithResponseVO<?> arithResponseVO = (ArithResponseVO<BigDecimal>) this.clientArithService
				.doArithOperation(arithParams);
		Assert.assertNotNull(arithResponseVO);
		Assert.assertNotNull(arithResponseVO.getResult());
		Assert.assertFalse(this.isSuccessHttpResponse(arithResponseVO.getHttpStatusCode()));
		Assert.assertFalse(this.isANumber(arithResponseVO.getResult()));
	}

	@Test
	public void testPower() {
		ArithParams arithParams = this.createPowerArithParams(BigDecimal.valueOf(10), 2);

		ArithResponseVO<?> arithResponseVO = this.clientArithService.doArithOperation(arithParams);
		Assert.assertNotNull(arithResponseVO);
		Assert.assertNotNull(arithResponseVO.getResult());
		Assert.assertTrue(this.isSuccessHttpResponse(arithResponseVO.getHttpStatusCode()));
		Assert.assertTrue(this.isANumber(arithResponseVO.getResult()));
	}

	private List<BigDecimal> values() {
		return Arrays.asList(BigDecimal.valueOf(100), BigDecimal.valueOf(25), BigDecimal.valueOf(5));
	}

	private ArithParams createDefaultArithParams(List<BigDecimal> numbers, ArithOperations arithOperations) {
		return new DefaultArithRequestVO(numbers, arithOperations);
	}

	private ArithParams createPowerArithParams(BigDecimal number, int power) {
		return new PowerArithRequestVO(number, power);
	}

	private boolean isSuccessHttpResponse(int httpStatus) {
		return HttpStatus.valueOf(httpStatus).is2xxSuccessful();
	}

	private boolean isANumber(Object object) {
		try {
			new BigDecimal(String.valueOf(object));
			return Boolean.TRUE;
		} catch (Exception ex) {
			return Boolean.FALSE;
		}
	}
}
