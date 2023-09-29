package com.testebackend.gerenciamentodepessoas.models;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
@Entity
@Data
@Table
@AllArgsConstructor
public class Pessoa {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String name;
	private String birthDate;
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "main_address_id", referencedColumnName = "id")
    private MainAddress mainAddress;
	@OneToMany(mappedBy="pessoa" ,cascade=CascadeType.ALL)
    private Set<Address> address;
	
	public Pessoa(){}
	

}
