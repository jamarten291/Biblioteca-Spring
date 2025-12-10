package ogs.spring.bibliotecaspring.controller;

import jakarta.validation.Valid;
import ogs.spring.bibliotecaspring.entity.Libro;
import ogs.spring.bibliotecaspring.entity.Prestamo;
import ogs.spring.bibliotecaspring.entity.Socio;
import ogs.spring.bibliotecaspring.repository.LibroRepository;
import ogs.spring.bibliotecaspring.repository.PrestamoRepository;
import ogs.spring.bibliotecaspring.repository.SocioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping()
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

    @GetMapping("/nuevo-prestamo")
    public String nuevoPrestamo(Model model) {
        // Agrega al modelo un préstamo vacío y una lista de socios y libros antes de devolver el formulario
        model.addAttribute("prestamo", new Prestamo());
        return "crear_prestamo";
    }

    @GetMapping("/eliminar-prestamo")
    public String eliminarPrestamo(Model model) {
        model.addAttribute("prestamos", prestamoRepository.findAll());
        return "eliminar_prestamo";
    }

    @GetMapping("/listar-prestamo")
    public String listadoPrestamo(Model model) {
        model.addAttribute("prestamos", prestamoRepository.findAll());
        return "listado_prestamo";
    }

    @PostMapping("/add-prestamo")
    public String crearPrestamo(@Valid Prestamo prestamo,
                                @RequestParam("socioId") Long socio,
                                @RequestParam("libroId") Long libro) {
        // Guarda el préstamo recibido por el modelo
        Socio socioObj = socioRepository.findById(socio).orElseThrow();
        Libro libroObj = libroRepository.findById(libro).orElseThrow();

        prestamo.setLibro(libroObj);
        prestamo.setSocio(socioObj);

        prestamoRepository.save(prestamo);
        return "redirect:/listar-prestamo";
    }

    @DeleteMapping("/delete-prestamo")
    public String borrarPrestamoByParam(@RequestParam("prestamoId") Long id) {
        prestamoRepository.deleteById(id);
        return "redirect:/listar-prestamo";
    }
}
