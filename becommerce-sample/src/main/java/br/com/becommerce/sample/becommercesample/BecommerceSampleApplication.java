package br.com.becommerce.sample.becommercesample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class BecommerceSampleApplication {

	private static final Logger logger = LoggerFactory.getLogger(BecommerceSampleApplication.class);
	
	public static void main(String[] args) throws JsonProcessingException {
		ApplicationContext context = SpringApplication.run(BecommerceSampleApplication.class,
				args);
		CadastroRepositorio cadastroRepositorio = context.getBean(CadastroRepositorio.class);

		ObjectMapper objectMapper = new ObjectMapper();
		for (int index = 0; index < 6; index++) {
			logger.info("Index {}", index);
			Pageable pageable = PageRequest.of(index, 5);
			//Iterable<Cadastro> cadastros = cadastroRepositorio.findAll(pageable);
			//cadastros.forEach(System.out::println);
			Page<Cadastro> resultPage = cadastroRepositorio.findAll(pageable);
			logger.info("{}", objectMapper.writeValueAsString(resultPage));
		}
		
	}
}
