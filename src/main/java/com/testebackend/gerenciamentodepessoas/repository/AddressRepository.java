package com.testebackend.gerenciamentodepessoas.repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.testebackend.gerenciamentodepessoas.models.Address;
import com.testebackend.gerenciamentodepessoas.models.Pessoa;

@Repository
public interface AddressRepository extends CrudRepository<Address, UUID> {
	Set<Address> findByPessoa(Pessoa pessoa);
}
