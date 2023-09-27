package com.testebackend.gerenciamentodepessoas.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PessoaController {
	@GetMapping("/")
	public String index() {
		return"bem vindo";
		
	}

}
