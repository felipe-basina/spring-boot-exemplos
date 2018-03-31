package br.com.becommerce.sample.becommercesample;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasToString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CadastroAPITest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private ObjectMapper objectMapper;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	public void testGetAll() throws Exception {
		this.mockMvc.perform(get("/cadastro")).andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(jsonPath("$", hasSize(6)));
	}

	@Test
	public void testGetById() throws Exception {
		this.mockMvc.perform(get("/cadastro/2")).andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(jsonPath("$", hasEntry("id", 2)))
			.andExpect(jsonPath("$", hasEntry("nome", "CATATAU")))
			.andExpect(jsonPath("$", hasKey("dtCriacao")));
	}

	@Test
	public void testGetByIdNotFound() throws Exception {
		long id = 10;
		this.mockMvc.perform(get("/cadastro/" + id)).andExpect(status().isNotFound())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(jsonPath("$", hasToString("ID {" + id + "} nao encontrado")));
	}

	@Test
	public void testSave() throws Exception {
		CadastroForm form = new CadastroForm();
		form.setNome("NOVO REGISTRO");
		
		this.mockMvc.perform(post("/cadastro")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(objectMapper.writeValueAsString(form)))
			.andExpect(status().isCreated());
	}

	@Test
	public void testSaveBadRequest() throws Exception {
		CadastroForm form = new CadastroForm();
		form.setNome(null);
		
		this.mockMvc.perform(post("/cadastro")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(objectMapper.writeValueAsString(form)))
			.andExpect(status().isBadRequest());
	}

	@Test
	public void testSaveBadRequestExistingValue() throws Exception {
		CadastroForm form = new CadastroForm();
		form.setNome("CATATAU");
		
		this.mockMvc.perform(post("/cadastro")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(objectMapper.writeValueAsString(form)))
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(jsonPath("$", hasToString("Nome {" + form.getNome() + "} existente")));
	}

	@Test
	public void testUpdate() throws Exception {
		CadastroForm form = new CadastroForm();
		form.setNome("CATATAUZIN");
		
		this.mockMvc.perform(put("/cadastro/2")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(objectMapper.writeValueAsString(form)))
			.andExpect(status().isOk());
	}

	@Test
	public void testUpdateBadRequest() throws Exception {
		CadastroForm form = new CadastroForm();
		form.setNome(null);
		
		this.mockMvc.perform(put("/cadastro/2")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(objectMapper.writeValueAsString(form)))
			.andExpect(status().isBadRequest());
	}

	@Test
	public void testUpdateNotFound() throws Exception {
		CadastroForm form = new CadastroForm();
		form.setNome("ZE COLMEIA");
		
		long id = 10;
		
		this.mockMvc.perform(put("/cadastro/" + id)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(objectMapper.writeValueAsString(form)))
			.andExpect(status().isNotFound())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(jsonPath("$", hasToString("ID {" + id + "} nao encontrado")));
	}

	@Test
	public void testDelete() throws Exception {
		this.mockMvc.perform(delete("/cadastro/1")).andExpect(status().isOk());
	}

	@Test
	public void testDeleteNotFound() throws Exception {
		long id = 10;
		
		this.mockMvc.perform(delete("/cadastro/" + id)).andExpect(status().isNotFound())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(jsonPath("$", hasToString("ID {" + id + "} nao encontrado")));
	}

}
