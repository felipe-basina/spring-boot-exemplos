package br.com.becommerce.security.api.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resource")
public class ResourceController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping
	public String resource() {
		logger.info("resource operation /api/resource");
		return "ok";
	}

}
