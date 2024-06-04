package com.LuisBautista.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.LuisBautista.app.entity.Calificacion;
import com.LuisBautista.app.entity.Estudiante;

public interface CalificacionRepository extends MongoRepository<Calificacion, String>{
	Calificacion findByEstudiante(Estudiante estudiante);
}
