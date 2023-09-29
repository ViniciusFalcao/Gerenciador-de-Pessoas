package com.testebackend.gerenciamentodepessoas.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.apache.catalina.mapper.Mapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.testebackend.gerenciamentodepessoas.models.Address;
import com.testebackend.gerenciamentodepessoas.models.MainAddress;
import com.testebackend.gerenciamentodepessoas.models.Pessoa;
import com.testebackend.gerenciamentodepessoas.repository.AddressRepository;
import com.testebackend.gerenciamentodepessoas.repository.MainAdressRepository;
import com.testebackend.gerenciamentodepessoas.repository.PessoaRepository;

import ch.qos.logback.core.net.ObjectWriter;
import jakarta.transaction.Transactional;

@WebMvcTest(MainController.class)
class MainControllerTest {
	@MockBean
	private AddressRepository aRepository;
	@MockBean
	private MainAdressRepository mRepository;
	@MockBean
	private PessoaRepository pRepository;
	@Autowired
	private MockMvc mockMvc;
	AutoCloseable autoCloseable;
	Pessoa pessoa;
	Address address;
	Address addressRelated;

	@BeforeEach
	void setUp() throws Exception {
		autoCloseable = MockitoAnnotations.openMocks(this);
		pessoa = new Pessoa(UUID.randomUUID(), "João Gomes", "08/09/1987", null, null);
		address = new Address(UUID.randomUUID(), "880 Rua Olimpica", "94380", 600, "São Paulo", null);

		addressRelated = address = new Address(UUID.randomUUID(), "880 Rua Olimpica", "94380", 600, "São Paulo", null);
		pRepository.save(pessoa);
		aRepository.save(address);
	}

	@AfterEach
	void tearDown() throws Exception {
		autoCloseable.close();
	}

	String ObjectToJson(Object object) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		com.fasterxml.jackson.databind.ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(object);
		return requestJson;

	}

	@Test
	void testAddPessoa() throws Exception {
		String requestJson = this.ObjectToJson(pessoa);

		when(pRepository.save(pessoa)).thenReturn(pessoa);
		this.mockMvc.perform(post("http://localhost:8080/pessoa/adicionar").contentType(MediaType.APPLICATION_JSON)
				.content(requestJson)).andDo(print()).andExpect(status().isOk());

	}

	@Test
	void testPutPessoa() throws Exception {
		var p = new Pessoa(pessoa.getId(), "Augusto Santos", "08/09/1987", null, null);
		pRepository.save(p);
		when(pRepository.findById(p.getId())).thenReturn(Optional.of(p));
		String requestJson = this.ObjectToJson(p);
		this.mockMvc.perform(
				put("http://localhost:8080/pessoa/editar").contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	void testBuscarPessoaById() throws Exception {
		when(pRepository.findById(pessoa.getId())).thenReturn(Optional.of(pessoa));
		this.mockMvc.perform(get("http://localhost:8080/pessoa/buscar/"+pessoa.getId())).andDo(print())
		.andExpect(status().isOk());
	}

	@Test
	void testListarPessoas() throws Exception {
		when(pRepository.findAll()).thenReturn(Set.of(pessoa));
		this.mockMvc.perform(get("http://localhost:8080/pessoa/listar")).andDo(print())
		.andExpect(status().isOk());
		
	}

	@Test
	void testAddAddress() throws Exception {
		String requestJson = this.ObjectToJson(aRepository.save(addressRelated));

		when(aRepository.save(address)).thenReturn(address);

		this.mockMvc
				.perform(post("http://localhost:8080/address/adicionar/" + pessoa.getId().toString())
						.contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andDo(print()).andExpect(status().isBadRequest());

	}

	@Test
	void testListAddressByPessoaId() {
		when(pRepository.save(pessoa)).thenReturn(pessoa);
	}

	@Test
	void testAddAddressMain() throws Exception {
		String requestJson = this.ObjectToJson(address);

		MainAddress mainA = new MainAddress(UUID.randomUUID(), address.getLogradouro(), address.getCep(),
				address.getNumero(), address.getCidade());
		pessoa.setMainAddress(mainA);
		pRepository.save(pessoa);
		when(pessoa.getMainAddress()).thenReturn(mainA);
		/*
		 * this.mockMvc.perform(post("http://localhost:8080/address/main").param(
		 * "pessoa", pessoa.getId().toString()) .param("address",
		 * address.getId().toString())).andDo(print()).andExpect(status().isOk());
		 */
	}

}
