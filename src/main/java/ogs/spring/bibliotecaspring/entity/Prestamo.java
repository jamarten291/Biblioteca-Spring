package ogs.spring.bibliotecaspring.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Prestamo {
    public enum EstadoPrestamo {
        ACTIVO,
        ATRASADO,
        DEVUELTO,
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prestamoId;
    // Relaciones
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "libroId")
    private Libro libro;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "socioId")
    private Socio socio;
    private LocalDate fechaPrestamo;
    private LocalDate fechaLimite;
    private LocalDate fechaDevolucion;
    private Integer diasRetraso;
    @Enumerated(EnumType.STRING)
    private EstadoPrestamo estado;
    // TODO relacionar entidad con Libro y Socio

    public boolean estaAtrasado() {
        return fechaDevolucion.isAfter(fechaLimite);
    }

    public Prestamo() {
    }

    public Long getPrestamoId() {
        return prestamoId;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
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
