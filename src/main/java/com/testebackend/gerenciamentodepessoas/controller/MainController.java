package com.testebackend.gerenciamentodepessoas.controller;

import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.testebackend.gerenciamentodepessoas.models.Address;
import com.testebackend.gerenciamentodepessoas.models.MainAddress;
import com.testebackend.gerenciamentodepessoas.models.Pessoa;
import com.testebackend.gerenciamentodepessoas.repository.AddressRepository;
import com.testebackend.gerenciamentodepessoas.repository.MainAdressRepository;
import com.testebackend.gerenciamentodepessoas.repository.PessoaRepository;

@RestController
public class MainController {
	@Autowired
	private PessoaRepository pRepository;
	@Autowired
	private AddressRepository aRepository;
	@Autowired
	private MainAdressRepository mRepository;

	@GetMapping("/")
	public String index() {
		return "bem vindo";

	}

	// Endpoint para criar uma pessoa
	@PostMapping("pessoa/adicionar")
	public ResponseEntity<Pessoa> addPessoa(@RequestBody Pessoa pessoa) {
		return ResponseEntity.ok(pRepository.save(pessoa));
	}

	// End point para editar pessoa
	@PutMapping("pessoa/editar")
	public ResponseEntity<Pessoa> putPessoa(@RequestBody Pessoa pessoa) {
		try {
			var find = pRepository.findById(pessoa.getId()).get();
			return ResponseEntity.ok(find);

		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}

	}

	// Endpoint para buscar pessoa pelo id
	@GetMapping("pessoa/buscar/{id}")
	public ResponseEntity<Pessoa> buscarPessoaById(@PathVariable UUID id) {
		try {
			var find = pRepository.findById(id).get();
			return ResponseEntity.ok(find);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();

		}
	}

	// Endpoint para listar pessoas
	@GetMapping("pessoa/listar")
	public ResponseEntity<Iterable<Pessoa>> listarPessoas() {
		return ResponseEntity.ok(pRepository.findAll());
	}

	// End point para salvar um endereço relacionada a um pessoa
	@PostMapping("address/adicionar/{id_pessoa}")
	public ResponseEntity<Address> AddAddress(@RequestBody Address address, @PathVariable UUID id_pessoa) {
		try {
			var pessoa = pRepository.findById(id_pessoa).get();
			address.setPessoa(pessoa);
			return ResponseEntity.ok(aRepository.save(address));

		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

	}

	// End point para Listar endereços de uma pessoa
	@GetMapping("address/{id_pessoa}")
	public ResponseEntity<Set<Address>> listAddressByPessoaId(@PathVariable UUID id_pessoa) {
		try {
			var pessoa = pRepository.findById(id_pessoa).get();
			return ResponseEntity.ok(aRepository.findByPessoa(pessoa));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	// Endpoint para salvar endereço principal da pessoa
	@PostMapping("address/main")
	public ResponseEntity<Pessoa> AddAddressMain(@RequestParam("pessoa") UUID id_pessoa,
			@RequestParam("address") UUID id_address) {
		try {
			var pessoa = pRepository.findById(id_pessoa).get();
			var address = aRepository.findById(id_address).get();
			var main_address = new MainAddress(null, address.getLogradouro(), address.getCep(), address.getNumero(),
					address.getCidade());
			pessoa.setMainAddress(main_address);
			return ResponseEntity.ok(pRepository.save(pessoa));

		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	/*- Criar uma pessoa
	
	- Editar uma pessoa
	
	- Consultar uma pessoa
	
	- Listar pessoas
	
	- Criar endereço para pessoa
	
	- Listar endereços da pessoa
	
	- Poder informar qual endereço é o principal da pessoa
	*/
}
