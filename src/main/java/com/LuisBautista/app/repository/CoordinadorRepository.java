package com.LuisBautista.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.LuisBautista.app.entity.Coordinador;

public interface CoordinadorRepository extends MongoRepository<Coordinador, String>{
	
	Coordinador findByUserAndPassword(String user, String password);
}
