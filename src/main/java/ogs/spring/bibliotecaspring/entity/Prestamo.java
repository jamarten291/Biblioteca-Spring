package ogs.spring.bibliotecaspring.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@Entity
public class Prestamo {

    public enum EstadoPrestamo {
        ACTIVO,
        ATRASADO,
        DEVUELTO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prestamoId;

    // Relaciones obligatorias
    @NotNull(message = "El libro es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "libroId", nullable = false)
    private Libro libro;

    @NotNull(message = "El socio es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "socioId", nullable = false)
    private Socio socio;

    // Fechas
    @NotNull(message = "La fecha de préstamo es obligatoria")
    @PastOrPresent(message = "La fecha de préstamo no puede ser futura")
    private LocalDate fechaPrestamo;

    @NotNull(message = "La fecha límite es obligatoria")
    private LocalDate fechaLimite;

    private LocalDate fechaDevolucion;

    @Min(value = 0, message = "Los días de retraso no pueden ser negativos")
    private Integer diasRetraso;

    @NotNull(message = "El estado del préstamo es obligatorio")
    @Enumerated(EnumType.STRING)
    private EstadoPrestamo estado;

    /* =======================
       Constructores
       ======================= */

    public Prestamo() {
        this.fechaPrestamo = LocalDate.now();
        this.estado = EstadoPrestamo.ACTIVO;
        this.diasRetraso = 0;
    }

    /* =======================
       Validaciones cruzadas
       ======================= */

    @AssertTrue(message = "La fecha límite debe ser posterior o igual a la fecha de préstamo")
    public boolean isFechaLimiteValida() {
        if (fechaPrestamo == null || fechaLimite == null) {
            return true; // @NotNull se encarga
        }
        return !fechaLimite.isBefore(fechaPrestamo);
    }

    @AssertTrue(message = "La fecha de devolución no puede ser anterior a la fecha de préstamo")
    public boolean isFechaDevolucionValida() {
        if (fechaDevolucion == null || fechaPrestamo == null) {
            return true;
        }
        return !fechaDevolucion.isBefore(fechaPrestamo);
    }

    /* =======================
       Lógica de dominio
       ======================= */

    public boolean estaAtrasado() {
        return fechaDevolucion != null &&
                fechaLimite != null &&
                fechaDevolucion.isAfter(fechaLimite);
    }

    /* =======================
       Getters & Setters
       ======================= */

    public Long getPrestamoId() {
        return prestamoId;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public Integer getDiasRetraso() {
        return diasRetraso;
    }

    public void setDiasRetraso(Integer diasRetraso) {
        this.diasRetraso = diasRetraso;
    }

    public EstadoPrestamo getEstado() {
        return estado;
    }

    public void setEstado(EstadoPrestamo estado) {
        this.estado = estado;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }
}
