package br.com.integration.tests.sample.component.integration.tests;

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

import br.com.integration.tests.sample.component.ArithComponent;
import br.com.integration.tests.sample.vo.ArithResponseVO;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ArithComponentTest {

	private static final String EMPTY_LIST_ERROR_MESSAGE = "Empty list";
	private static final String INVALID_NUMBER_LIST_ERROR_MESSAGE = "A list of valid numbers must be provided";
	private static final String DIVISION_BY_ZERO_ERROR_MESSAGE = "Division by ZERO is not allowed";

	@Autowired
	private ArithComponent arithComponent;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testSum() {
		List<BigDecimal> values = this.values();

		ArithResponseVO<BigDecimal> arithResponseVO = (ArithResponseVO<BigDecimal>) this.arithComponent.sum(values);
		Assert.assertNotNull(arithResponseVO);
		Assert.assertNotNull(arithResponseVO.getResult());
		Assert.assertEquals(HttpStatus.CREATED.value(), arithResponseVO.getHttpStatusCode());
		Assert.assertEquals(BigDecimal.valueOf(130), arithResponseVO.getResult());
	}

	@Test
	@SuppressWarnings({ "unchecked" })
	public void testSumEmptyList() {
		List<BigDecimal> emptyList = Collections.emptyList();

		ArithResponseVO<BigDecimal> arithResponseVO = (ArithResponseVO<BigDecimal>) this.arithComponent.sum(emptyList);
		Assert.assertNotNull(arithResponseVO);
		Assert.assertNotNull(arithResponseVO.getResult());
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), arithResponseVO.getHttpStatusCode());
		Assert.assertEquals(EMPTY_LIST_ERROR_MESSAGE, arithResponseVO.getResult());
	}

	@Test
	@SuppressWarnings({ "unchecked" })
	public void testSumInvalidList() {
		ArithResponseVO<BigDecimal> arithResponseVO = (ArithResponseVO<BigDecimal>) this.arithComponent.sum(null);
		Assert.assertNotNull(arithResponseVO);
		Assert.assertNotNull(arithResponseVO.getResult());
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), arithResponseVO.getHttpStatusCode());
		Assert.assertEquals(INVALID_NUMBER_LIST_ERROR_MESSAGE, arithResponseVO.getResult());
	}

	@Test
	@SuppressWarnings({ "unchecked" })
	public void testSubtract() {
		List<BigDecimal> values = this.values();

		ArithResponseVO<BigDecimal> arithResponseVO = (ArithResponseVO<BigDecimal>) this.arithComponent
				.subtract(values);
		Assert.assertNotNull(arithResponseVO);
		Assert.assertNotNull(arithResponseVO.getResult());
		Assert.assertEquals(HttpStatus.CREATED.value(), arithResponseVO.getHttpStatusCode());
		Assert.assertEquals(BigDecimal.valueOf(70), arithResponseVO.getResult());
	}

	@Test
	@SuppressWarnings({ "unchecked" })
	public void testMultiply() {
		List<BigDecimal> values = this.values();

		ArithResponseVO<BigDecimal> arithResponseVO = (ArithResponseVO<BigDecimal>) this.arithComponent
				.multiply(values);
		Assert.assertNotNull(arithResponseVO);
		Assert.assertNotNull(arithResponseVO.getResult());
		Assert.assertEquals(HttpStatus.CREATED.value(), arithResponseVO.getHttpStatusCode());
		Assert.assertEquals(BigDecimal.valueOf(12500), arithResponseVO.getResult());
	}

	@Test
	@SuppressWarnings({ "unchecked" })
	public void testDivide() {
		List<BigDecimal> values = this.values();

		ArithResponseVO<BigDecimal> arithResponseVO = (ArithResponseVO<BigDecimal>) this.arithComponent.divide(values);
		Assert.assertNotNull(arithResponseVO);
		Assert.assertNotNull(arithResponseVO.getResult());
		Assert.assertEquals(HttpStatus.CREATED.value(), arithResponseVO.getHttpStatusCode());
		Assert.assertEquals(BigDecimal.valueOf(0.8), arithResponseVO.getResult());
	}

	@Test
	@SuppressWarnings({ "unchecked" })
	public void testDivideByZero() {
		List<BigDecimal> values = this.values();
		values = new ArrayList<>(values);
		values.add(BigDecimal.ZERO);

		ArithResponseVO<BigDecimal> arithResponseVO = (ArithResponseVO<BigDecimal>) this.arithComponent.divide(values);
		Assert.assertNotNull(arithResponseVO);
		Assert.assertNotNull(arithResponseVO.getResult());
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), arithResponseVO.getHttpStatusCode());
		Assert.assertEquals(DIVISION_BY_ZERO_ERROR_MESSAGE, arithResponseVO.getResult());
	}

	@Test
	@SuppressWarnings({ "unchecked" })
	public void testPower() {
		BigDecimal value = BigDecimal.valueOf(10);
		int power = 2;

		ArithResponseVO<BigDecimal> arithResponseVO = (ArithResponseVO<BigDecimal>) this.arithComponent.power(value,
				power);
		Assert.assertNotNull(arithResponseVO);
		Assert.assertNotNull(arithResponseVO.getResult());
		Assert.assertEquals(HttpStatus.CREATED.value(), arithResponseVO.getHttpStatusCode());
		Assert.assertEquals(BigDecimal.valueOf(100), arithResponseVO.getResult());
	}

	private List<BigDecimal> values() {
		return Arrays.asList(BigDecimal.valueOf(100), BigDecimal.valueOf(25), BigDecimal.valueOf(5));
	}

}
