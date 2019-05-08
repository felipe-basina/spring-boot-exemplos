package br.com.becommerce.sample.becommercesample;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface CadastroRepositorio extends PagingAndSortingRepository<Cadastro, Long> {

	public Cadastro findByNome(@Param("nome") String nome);
	
}
