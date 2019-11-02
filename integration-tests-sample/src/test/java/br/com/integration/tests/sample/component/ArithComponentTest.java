package br.com.integration.tests.sample.component;

import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import br.com.integration.tests.sample.services.ClientArithService;
import br.com.integration.tests.sample.types.ArithOperations;
import br.com.integration.tests.sample.vo.ArithResponseVO;
import br.com.integration.tests.sample.vo.DefaultArithRequestVO;

@RunWith(value = MockitoJUnitRunner.class)
public class ArithComponentTest {

	@Mock
	private ClientArithService clientArithService;
	
	private ArithComponent arithComponent;
	
	@Before
	public void setUp() throws Exception {
		this.arithComponent = new ArithComponent(this.clientArithService);
	}

	@Test
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void testSum() {
		List<BigDecimal> values = this.values();
		
		DefaultArithRequestVO request = this.createDefaultArithRequestVO(values, ArithOperations.SUM);
		ArithResponseVO response = this.createSuccessArithResponseVO(BigDecimal.valueOf(130));
		
		Mockito.when(this.clientArithService.doArithOperation(request)).thenReturn(response);
		
		ArithResponseVO<BigDecimal> arithResponseVO = (ArithResponseVO<BigDecimal>) this.arithComponent.sum(values);
		Assert.assertNotNull(arithResponseVO);
		Assert.assertNotNull(arithResponseVO.getResult());
		Assert.assertEquals(HttpStatus.CREATED.value(), arithResponseVO.getHttpStatusCode());
		Assert.assertEquals(response.getResult(), arithResponseVO.getResult());
		
		Mockito.verify(this.clientArithService, Mockito.times(1)).doArithOperation(request);
		Mockito.verifyNoMoreInteractions(this.clientArithService);
	}

	@Test
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void testSumEmptyList() {
		List<BigDecimal> emptyList = Collections.emptyList();
		
		DefaultArithRequestVO request = this.createDefaultArithRequestVO(emptyList, ArithOperations.SUM);
		ArithResponseVO response = this.createBadRequestArithResponseVO();
		
		Mockito.when(this.clientArithService.doArithOperation(request)).thenReturn(response);
		
		ArithResponseVO<BigDecimal> arithResponseVO = (ArithResponseVO<BigDecimal>) this.arithComponent.sum(emptyList);
		Assert.assertNotNull(arithResponseVO);
		Assert.assertNotNull(arithResponseVO.getResult());
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), arithResponseVO.getHttpStatusCode());
		Assert.assertEquals(response.getResult(), arithResponseVO.getResult());
		
		Mockito.verify(this.clientArithService, Mockito.times(1)).doArithOperation(request);
		Mockito.verifyNoMoreInteractions(this.clientArithService);
	}

	@Test
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void testSumInvalidList() {
		List<BigDecimal> invalidList = null;
		
		DefaultArithRequestVO request = this.createDefaultArithRequestVO(invalidList, ArithOperations.SUM);
		ArithResponseVO response = this.createInternalServerErrorArithResponseVO();
		
		Mockito.when(this.clientArithService.doArithOperation(request)).thenReturn(response);
		
		ArithResponseVO<BigDecimal> arithResponseVO = (ArithResponseVO<BigDecimal>) this.arithComponent.sum(invalidList);
		Assert.assertNotNull(arithResponseVO);
		Assert.assertNotNull(arithResponseVO.getResult());
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), arithResponseVO.getHttpStatusCode());
		Assert.assertEquals(response.getResult(), arithResponseVO.getResult());
		
		Mockito.verify(this.clientArithService, Mockito.times(1)).doArithOperation(request);
		Mockito.verifyNoMoreInteractions(this.clientArithService);
	}

	@Test
	public void testSubtract() {
		fail("Not yet implemented");
	}

	@Test
	public void testMultiply() {
		fail("Not yet implemented");
	}

	@Test
	public void testDivide() {
		fail("Not yet implemented");
	}

	@Test
	public void testPower() {
		fail("Not yet implemented");
	}
	
	private List<BigDecimal> values() {
		return Arrays.asList(BigDecimal.valueOf(100), BigDecimal.valueOf(25), BigDecimal.valueOf(5));
	}
	
	private DefaultArithRequestVO createDefaultArithRequestVO(List<BigDecimal> values,
			ArithOperations arithOperations) {
		return new DefaultArithRequestVO(values, arithOperations);
	}
	
	private ArithResponseVO<BigDecimal> createSuccessArithResponseVO(BigDecimal total) {
		return new ArithResponseVO<BigDecimal>(HttpStatus.CREATED.value(), total);
	}
	
	private ArithResponseVO<String> createBadRequestArithResponseVO() {
		return new ArithResponseVO<String>(HttpStatus.BAD_REQUEST.value(), "Empty list");
	}
	
	private ArithResponseVO<String> createInternalServerErrorArithResponseVO() {
		return new ArithResponseVO<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "A valid number list should be provided");
	}

}
