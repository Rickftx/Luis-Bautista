package com.LuisBautista.app.controller;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.LuisBautista.app.entity.Calificacion;
import com.LuisBautista.app.exception.NotFoundException;
import com.LuisBautista.app.repository.CalificacionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/api/calificaciones")
public class CalificacionController {
    @Autowired
    private CalificacionRepository calificacionRepository;

    @GetMapping("/")
    public List<Calificacion> getAllCalificaciones() {
        return calificacionRepository.findAll();
    }

    @GetMapping("/{id}")
    public Calificacion getCalificacionById(@PathVariable String id) {
        return calificacionRepository.findById(id).orElseThrow(() -> new NotFoundException("Calificacion no encontrado"));
    }

    @PostMapping("/")
    public Calificacion saveCalificacion(@RequestBody Map<String, Object> body) {
        ObjectMapper mapper = new ObjectMapper();
        Calificacion calificacion = mapper.convertValue(body, Calificacion.class);
        return calificacionRepository.save(calificacion);
    }

    @PutMapping("/{id}")
    public Calificacion updateCalificacion(@PathVariable String id, @RequestBody Map<String, Object> body) {
        ObjectMapper mapper = new ObjectMapper();
        Calificacion calificacion = mapper.convertValue(body, Calificacion.class);
        calificacion.setId(id);
        return calificacionRepository.save(calificacion);
    }

    @DeleteMapping("/{id}")
    public Calificacion deleteCalificacion(@PathVariable String id) {
    	Calificacion calificacion = calificacionRepository.findById(id).orElseThrow(() -> new NotFoundException("Calificacion no encontrado"));
    	calificacionRepository.deleteById(id);
        return calificacion;
    }
}