package ogs.spring.bibliotecaspring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
public class Socio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long socioId;
    private String nombre;
    private String apellidos;
    private String email;
    private LocalDate fechaNacimiento;
    private LocalDate fechaAlta;
    private EstadoSocio estado;
    private LocalDate fechaFinPenalizacion;
    @OneToMany(mappedBy = "socio", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Prestamo> prestamos;

    public Socio() {
    }

    public Long getSocioId() {
        return socioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public EstadoSocio getEstado() {
        return estado;
    }

    public void setEstado(EstadoSocio estado) {
        this.estado = estado;
    }

    public LocalDate getFechaFinPenalizacion() {
        return fechaFinPenalizacion;
    }

    public void setFechaFinPenalizacion(LocalDate fechaFinPenalizacion) {
        this.fechaFinPenalizacion = fechaFinPenalizacion;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    public int getNumPrestamosActivos() {
        int cont = 0;
        for (Prestamo p : prestamos) {
            if (p.getEstado().equals(Prestamo.EstadoPrestamo.ACTIVO)) cont++;
        }

        return cont;
    }

    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }

    @Override
    public String toString() {
        return "Socio{" +
                "id=" + socioId +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", email='" + email + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", fechaAlta=" + fechaAlta +
                ", estado=" + estado +
                ", fechaFinPenalizacion=" + fechaFinPenalizacion +
                '}';
    }
}
