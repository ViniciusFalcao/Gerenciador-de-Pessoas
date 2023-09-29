package com.testebackend.gerenciamentodepessoas.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.testebackend.gerenciamentodepessoas.models.Address;
import com.testebackend.gerenciamentodepessoas.models.Pessoa;

@DataJpaTest
public class AddressRepositoryTest {
	@Autowired
	private AddressRepository aRepository;
	@Autowired
	private PessoaRepository pRepository;
	Address address;
	Pessoa pessoa;
	
	@BeforeEach
	void setUp(){
		pessoa=new Pessoa(null, "João Gomes", "08/09/1987", null, null);
		pessoa=pRepository.save(pessoa);
		address=new Address(null, "880 Rua Olimpica", "94380", 600, "São Paulo",pessoa);
		address=aRepository.save(address);
	}
	@AfterEach
	void tearDown() {
		pessoa=null;
		address=null;
		pRepository.deleteAll();
		aRepository.deleteAll();
		
	}
	
	//Test case sucess
	@Test
	void acharEnderecosdaPessoa() {
		var list_address= aRepository.findByPessoa(pessoa);
		assertThat(list_address.contains(address)).isEqualTo(true);
		
	}
	
	//Test case fail
	@Test
	void acharEnderecosdaPessoaFailure() {
		aRepository.delete(address);
		var list_address= aRepository.findByPessoa(pessoa);
		assertThat(list_address.contains(address)).isEqualTo(false);
		
	}
	
	
	

}
