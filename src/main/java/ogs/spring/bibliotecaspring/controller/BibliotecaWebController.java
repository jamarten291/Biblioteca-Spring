package ogs.spring.bibliotecaspring.controller;

import jakarta.validation.Valid;
import ogs.spring.bibliotecaspring.entity.Libro;
import ogs.spring.bibliotecaspring.entity.Socio;
import ogs.spring.bibliotecaspring.service.LibroService;
import ogs.spring.bibliotecaspring.service.PrestamoService;
import ogs.spring.bibliotecaspring.service.SocioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping()
public class BibliotecaWebController {

    private final LibroService libroService;
    private final SocioService socioService;
    private final PrestamoService prestamoService;

    public BibliotecaWebController(LibroService libroService,
                                   SocioService socioService,
                                   PrestamoService prestamoService) {
        this.libroService = libroService;
        this.socioService = socioService;
        this.prestamoService = prestamoService;
    }

    @GetMapping("/menu")
    public String menu() {
        return "menu_principal";
    }

    @GetMapping("/gestion-libro")
    public String gestionLibro() {
        return "menu_libro";
    }

    @GetMapping("/gestion-socio")
    public String gestionSocio() {
        return "menu_socio";
    }

    @GetMapping("/nuevo-prestamo")
    public String nuevoPrestamo(Model model) {
        return "crear_prestamo";
    }

    @GetMapping("/nuevo-libro")
    public String nuevoLibro(Model model) {
        model.addAttribute("libro", new Libro());
        return "crear_libro";
    }

    @GetMapping("/nuevo-socio")
    public String nuevoSocio(Model model) {
        model.addAttribute("socio", new Socio());
        return "crear_socio";
    }

    @GetMapping("/eliminar-prestamo")
    public String eliminarPrestamo(Model model) {
        return "eliminar_prestamo";
    }

    @GetMapping("/eliminar-libro")
    public String eliminarLibro(Model model) {
        return "eliminar_libro";
    }

    @GetMapping("/eliminar-socio")
    public String eliminarSocio(Model model) {
        return "eliminar_socio";
    }

    @GetMapping("/listar-prestamo")
    public String listadoPrestamo(Model model) {
        model.addAttribute("prestamos", prestamoService.listarPrestamos());
        return "listado_prestamo";
    }

    @GetMapping("/listar-libro")
    public String listadoLibro(Model model) {
        model.addAttribute("libros", libroService.listarLibros());
        return "listado_libro";
    }

    @GetMapping("/listar-libro-stats")
    public String listadoLibroPorPrestados(Model model) {
        model.addAttribute("libros", libroService.getLibrosMasPrestados());
        return "listado_libro";
    }

    @GetMapping("/listar-socio")
    public String listadoSocio(Model model) {
        model.addAttribute("socios", socioService.listarSocios());
        return "listado_socio";
    }

    @GetMapping("/editar-libro")
    public String editarLibro(Model model) {
        return "editar_libro";
    }

    @GetMapping("/editar-socio")
    public String editarSocio(Model model) {
        return "editar_socio";
    }

    @PostMapping("/add-prestamo")
    public String crearPrestamo(@RequestParam("socioId") Long socio,
                                @RequestParam("libroId") Long libro) {
        prestamoService.crearPrestamo(socio, libro);
        return "redirect:/listar-prestamo";
    }

    @PostMapping("/add-libro")
    public String crearLibro(@Valid Libro libro) {
        libroService.guardarLibro(libro);
        return "redirect:/listar-libro";
    }

    @PostMapping("/add-socio")
    public String crearSocio(@Valid Socio socio) {
        socioService.guardar(socio);
        return "redirect:/listar-socio";
    }

    @DeleteMapping("/delete-prestamo")
    public String borrarPrestamoByParam(@RequestParam("prestamoId") Long id) {
        prestamoService.devolverPrestamo(id);
        return "redirect:/listar-prestamo";
    }

    @DeleteMapping("/delete-libro")
    public String borrarLibroByParam(@RequestParam("libroId") Long id) {
        libroService.eliminarLibro(id);
        return "redirect:/listar-libro";
    }

    @DeleteMapping("/delete-socio")
    public String borrarSocioByParam(@RequestParam("socioId") Long id) {
        socioService.eliminar(id);
        return "redirect:/listar-socio";
    }
}
