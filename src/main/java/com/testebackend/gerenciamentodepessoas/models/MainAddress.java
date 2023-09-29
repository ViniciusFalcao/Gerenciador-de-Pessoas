package com.testebackend.gerenciamentodepessoas.models;

import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@Table
@AllArgsConstructor
public class MainAddress  {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String logradouro;
	private String cep;
	private Integer numero;
	private String cidade;
	public MainAddress(){}
	

}
