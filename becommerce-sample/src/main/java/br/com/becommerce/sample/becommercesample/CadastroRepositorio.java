package br.com.becommerce.sample.becommercesample;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface CadastroRepositorio extends JpaRepository<Cadastro, Long> {

	public Cadastro findByNome(@Param("nome") String nome);
	
}
