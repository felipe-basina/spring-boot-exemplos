package br.com.becommerce.sample.becommercesample;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public Iterable<Cadastro> getAll() {
		return this.cadastroRepositorio.findAll();
	}
	
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getById(@PathVariable("id") Long id) {
		if (id == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Optional<Cadastro> optional = this.cadastroRepositorio.findById(id);
		
		if (!optional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID {".concat(String.valueOf(id)).concat("} nao encontrado"));
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(optional.get());
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> save(@RequestBody CadastroForm form) {
		if (StringUtils.isEmpty(form.getNome())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if (this.cadastroRepositorio.findByNome(form.getNome().toUpperCase()) != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nome {".concat(form.getNome()).concat("} existente"));
		}
		
		this.cadastroRepositorio.save(new Cadastro(form.getNome()));
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody CadastroForm form) {
		if (StringUtils.isEmpty(form.getNome())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Optional<Cadastro> optional = this.cadastroRepositorio.findById(id);
		
		if (!optional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID {".concat(String.valueOf(id)).concat("} nao encontrado"));
		}
		
		Cadastro cadastro = optional.get();
		cadastro.setNome(form.getNome());
		
		this.cadastroRepositorio.save(cadastro);
		
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		if (id == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Optional<Cadastro> optional = this.cadastroRepositorio.findById(id);
		
		if (!optional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID {".concat(String.valueOf(id)).concat("} nao encontrado"));
		}
		
		this.cadastroRepositorio.deleteById(id);
		
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
}
