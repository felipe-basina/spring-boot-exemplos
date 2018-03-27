package br.com.becommerce.sample.becommercesample;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cadastro")
public class CadastroAPI {

	private CadastroRepositorio cadastroRepositorio;
	
	@Autowired
	public CadastroAPI(CadastroRepositorio cadastroRepositorio) {
		this.cadastroRepositorio = cadastroRepositorio;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Cadastro> getAll() {
		return this.cadastroRepositorio.findAll();
	}
	
}
