package com.LuisBautista.app.controller;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.LuisBautista.app.entity.Estudiante;
import com.LuisBautista.app.exception.NotFoundException;
import com.LuisBautista.app.repository.EstudianteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/api/estudiantes")
public class EstudianteController {
    @Autowired
    private EstudianteRepository estudianteRepository;

    @GetMapping("/")
    public List<Estudiante> getAllEstudiantes() {
        return estudianteRepository.findAll();
    }

    @GetMapping("/{id}")
    public Estudiante getEstudianteById(@PathVariable String id) {
        return estudianteRepository.findById(id).orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));
    }

    @PostMapping("/")
    public Estudiante saveEstudiante(@RequestBody Map<String, Object> body) {
        ObjectMapper mapper = new ObjectMapper();
        Estudiante estudiante = mapper.convertValue(body, Estudiante.class);
        return estudianteRepository.save(estudiante);
    }

    @PutMapping("/{id}")
    public Estudiante updateEstudiante(@PathVariable String id, @RequestBody Map<String, Object> body) {
        ObjectMapper mapper = new ObjectMapper();
        Estudiante etudiante = mapper.convertValue(body, Estudiante.class);
        etudiante.setId(id);
        return estudianteRepository.save(etudiante);
    }

    @DeleteMapping("/{id}")
    public Estudiante deleteEstudiante(@PathVariable String id) {
    	Estudiante estudiante = estudianteRepository.findById(id).orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));
    	estudianteRepository.deleteById(id);
        return estudiante;
    }
    
    @PutMapping("/{numeroDocumento}")
    public Estudiante resultEstudiante(@PathVariable String numeroDocumento) {
    	return estudianteRepository.findByNumeroDocumento(numeroDocumento);
    }
}
