package com.LuisBautista.app.controller;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.LuisBautista.app.entity.Coordinador;
import com.LuisBautista.app.exception.NotFoundException;
import com.LuisBautista.app.repository.CoordinadorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/api/coordinadores")
public class CoordinadorController {
    @Autowired
    private CoordinadorRepository coordinadorRepository;

    @GetMapping("/")
    public List<Coordinador> getAllCoordinadores() {
        return coordinadorRepository.findAll();
    }

    @GetMapping("/{id}")
    public Coordinador getCoordinadorById(@PathVariable String id) {
        return coordinadorRepository.findById(id).orElseThrow(() -> new NotFoundException("Coordinador no encontrado"));
    }

    @PostMapping("/")
    public Coordinador saveCoordinador(@RequestBody Map<String, Object> body) {
        ObjectMapper mapper = new ObjectMapper();
        Coordinador coordinador = mapper.convertValue(body, Coordinador.class);
        return coordinadorRepository.save(coordinador);
    }

    @PutMapping("/{id}")
    public Coordinador updateCoordinador(@PathVariable String id, @RequestBody Map<String, Object> body) {
        ObjectMapper mapper = new ObjectMapper();
        Coordinador coordinador = mapper.convertValue(body, Coordinador.class);
        coordinador.setId(id);
        return coordinadorRepository.save(coordinador);
    }

    @DeleteMapping("/{id}")
    public Coordinador deleteCoordinador(@PathVariable String id) {
    	Coordinador coordinador = coordinadorRepository.findById(id).orElseThrow(() -> new NotFoundException("Coordinador no encontrado"));
    	coordinadorRepository.deleteById(id);
        return coordinador;
    }
}


