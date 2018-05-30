package br.com.becommerce.security.api.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class HomeController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping
	public String home() {
		logger.info("home operation /api");
		return "Home Page";
	}

}
