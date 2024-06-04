package com.LuisBautista.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; // Agregada esta importación
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.LuisBautista.app.entity.Calificacion;
import com.LuisBautista.app.entity.Estudiante;
import com.LuisBautista.app.exception.NotFoundException;
import com.LuisBautista.app.repository.CalificacionRepository;
import com.LuisBautista.app.repository.EstudianteRepository;

@Controller // Asegúrate de agregar la anotación @Controller
@RequestMapping("/estudiantes")
public class EstudianteTemplateController {
	@Autowired
	private EstudianteRepository estudianteRepository;

	@Autowired
	private CalificacionRepository calificacionRepository;

	@GetMapping("/")
	public String estudianteListTemplate(Model model) {
		model.addAttribute("estudiantes", estudianteRepository.findAll());
		return "estudiantes-list";
	}

	@GetMapping("/new")
	public String estudiantesNewTemplate(Model model) {
		model.addAttribute("estudiante", new Estudiante());
		return "estudiantes-form";
	}

	@GetMapping("/edit/{id}")
	public String estudianteEditTemplate(@PathVariable("id") String id, Model model) {
		model.addAttribute("estudiante",
				estudianteRepository.findById(id).orElseThrow(() -> new NotFoundException("Estudiante no encontrado")));
		return "estudiantes-form";
	}

	@PostMapping("/save")
	public String estudiantesSaveProcess(@ModelAttribute("usuario") Estudiante estudiante) {
		if (estudiante.getId().isEmpty()) {
			estudiante.setId(null);
		}
		if (estudiante.getRevisado().isEmpty()) {
			estudiante.setRevisado("no");
		}
		estudianteRepository.save(estudiante);
		return "vista-puntaje";
	}

	@PostMapping("/salvar")
	public String estudiantesSalvarProcess(@ModelAttribute("usuario") Estudiante estudiante) {
		if (estudiante.getId().isEmpty()) {
			estudiante.setId(null);
		}
		if (estudiante.getRevisado().isEmpty()) {
			estudiante.setRevisado("no");
		}

		estudianteRepository.save(estudiante);
		return "redirect:/estudiantes/";
	}

	@GetMapping("/delete/{id}")
	public String estudiantesDeleteProcess(@PathVariable("id") String id) {
		Estudiante estudiante = estudianteRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));
		Calificacion calificacion = calificacionRepository.findByEstudiante(estudiante);
		if (calificacion != null) {
			calificacionRepository.deleteById(calificacion.getId());
		}
		estudianteRepository.deleteById(id);
		return "redirect:/estudiantes/";
	}

	@GetMapping("/registro")
	public String registroTemplate(Model model) {
		model.addAttribute("estudiante", new Estudiante());
		return "registro-estudiante";
	}

	@PostMapping("/ingresar")
	public String login(@RequestParam("numeroDocumento") String numeroDocumento,
			@RequestParam("numeroRegistro") String numeroRegistro, Model model) {
		// Verificar las credenciales
		System.out.println("Documento: " + numeroDocumento + " Registro:" + numeroRegistro);

		Estudiante estudiante = estudianteRepository.findByNumeroDocumento(numeroDocumento);
		if (estudiante != null) {
			// Inicio de sesión exitoso, redirigir a la página de resultado con la variable
			// en la URL
			System.out.println(
					"Documento: " + estudiante.getNumeroDocumento() + " Registro:" + estudiante.getNumeroRegistro());
			return "redirect:/estudiantes/resultado/" + numeroDocumento;
		} else {
			// Inicio de sesión fallido, mostrar mensaje de error en la página de inicio
			model.addAttribute("authenticationFailed", true);
			model.addAttribute("errorMessage", "No se encontró ningún estudiante");
			return "login-general";
		}
	}

	@GetMapping("/resultado/{numeroDocumento}")
	public String estudianteResultTemplate(@PathVariable("numeroDocumento") String numeroDocumento, Model model) {
		Estudiante estudiante = estudianteRepository.findByNumeroDocumento(numeroDocumento);
		model.addAttribute("calificacion", calificacionRepository.findByEstudiante(estudiante));
		estudiante.setRevisado("si");
		estudianteRepository.save(estudiante);
		return "resultados-estudiante"; // Ajusta el nombre de la vista según tu configuración
	}

	@GetMapping("/detallado/{numeroDocumento}")
	public String estudianteDetailtTemplate(@PathVariable("numeroDocumento") String numeroDocumento, Model model) {
		Estudiante estudiante = estudianteRepository.findByNumeroDocumento(numeroDocumento);
		model.addAttribute("calificacion", calificacionRepository.findByEstudiante(estudiante));
		return "resultado-detallado"; // Ajusta el nombre de la vista según tu configuración
	}

}
