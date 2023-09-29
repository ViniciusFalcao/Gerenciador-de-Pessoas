package com.testebackend.gerenciamentodepessoas.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.testebackend.gerenciamentodepessoas.models.MainAddress;
@Repository
public interface MainAdressRepository extends CrudRepository<MainAddress, UUID>{
	

}
