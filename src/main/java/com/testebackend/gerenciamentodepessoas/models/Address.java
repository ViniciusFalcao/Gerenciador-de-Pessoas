package com.testebackend.gerenciamentodepessoas.models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@Table
@AllArgsConstructor
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String logradouro;
	private String cep;
	private Integer numero;
	private String cidade;
	@ManyToOne
    @JoinColumn(name="pessoa_id")
	private Pessoa pessoa;
	
	public Address(){}
	

}
