package ogs.spring.bibliotecaspring.controller;

import jakarta.validation.Valid;
import ogs.spring.bibliotecaspring.entity.Prestamo;
import ogs.spring.bibliotecaspring.service.LibroService;
import ogs.spring.bibliotecaspring.service.PrestamoService;
import ogs.spring.bibliotecaspring.service.SocioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping()
public class PrestamoWebController {

    private final LibroService libroService;
    private final SocioService socioService;
    private final PrestamoService prestamoService;

    public PrestamoWebController(LibroService libroService,
                                 SocioService socioService,
                                 PrestamoService prestamoService) {
        this.libroService = libroService;
        this.socioService = socioService;
        this.prestamoService = prestamoService;
    }

    @GetMapping("/menu")
    public String menu() {
        return "menu";
    }

    @GetMapping("/nuevo-prestamo")
    public String nuevoPrestamo(Model model) {
        return "crear_prestamo";
    }

    @GetMapping("/eliminar-prestamo")
    public String eliminarPrestamo(Model model) {
        return "eliminar_prestamo";
    }

    @GetMapping("/listar-prestamo")
    public String listadoPrestamo(Model model) {
        model.addAttribute("prestamos", prestamoService.listarPrestamos());
        return "listado_prestamo";
    }

    @PostMapping("/add-prestamo")
    public String crearPrestamo(@Valid Prestamo prestamo,
                                @RequestParam("socioId") Long socio,
                                @RequestParam("libroId") Long libro) {
        prestamoService.crearPrestamo(socio, libro);
        return "redirect:/listar-prestamo";
    }

    @DeleteMapping("/delete-prestamo")
    public String borrarPrestamoByParam(@RequestParam("prestamoId") Long id) {
        prestamoService.devolverPrestamo(id);
        return "redirect:/listar-prestamo";
    }
}
