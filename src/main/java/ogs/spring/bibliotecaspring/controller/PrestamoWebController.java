package ogs.spring.bibliotecaspring.controller;

import jakarta.validation.Valid;
import ogs.spring.bibliotecaspring.entity.Prestamo;
import ogs.spring.bibliotecaspring.repository.LibroRepository;
import ogs.spring.bibliotecaspring.repository.PrestamoRepository;
import ogs.spring.bibliotecaspring.repository.SocioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/prestamos")
public class PrestamoWebController {

    private final LibroRepository libroRepository;
    private final SocioRepository socioRepository;
    private final PrestamoRepository prestamoRepository;

    public PrestamoWebController(LibroRepository libroRepository, SocioRepository socioRepository, PrestamoRepository prestamoRepository) {
        this.libroRepository = libroRepository;
        this.socioRepository = socioRepository;
        this.prestamoRepository = prestamoRepository;
    }

    @GetMapping("/menu")
    public String menu() {
        return "menu";
    }

    @GetMapping("/nuevo")
    public String nuevoPrestamo(Model model) {
        // Agrega al modelo un préstamo vacío y una lista de socios y libros antes de devolver el formulario
        model.addAttribute("prestamo", new Prestamo());
        model.addAttribute("socios", socioRepository.findAll());
        model.addAttribute("libros", libroRepository.findAll());
        return "crear-prestamo";
    }

    @GetMapping("/eliminar")
    public String eliminarPrestamo(Model model) {
        model.addAttribute("prestamos", prestamoRepository.findAll());
        return "eliminar-prestamo";
    }

    @GetMapping("/listar")
    public String listadoPrestamo(Model model) {
        model.addAttribute("prestamos", prestamoRepository.findAll());
        return "listado-prestamo";
    }

    @PostMapping()
    public String crearPrestamo(@Valid Prestamo prestamo) {
        // Guarda el préstamo recibido por el modelo
        prestamoRepository.save(prestamo);
        return "redirect:/prestamos/listar";
    }

    @DeleteMapping()
    public String borrarPrestamoByParam(@RequestParam("prestamoId") Long id) {
        prestamoRepository.deleteById(id);
        return "redirect:/prestamos/listar";
    }

    @DeleteMapping("/{id}")
    public String borrarPrestamoByPath(@PathVariable("prestamoId") Long id) {
        prestamoRepository.deleteById(id);
        return "redirect:/prestamos/listar";
    }
}
