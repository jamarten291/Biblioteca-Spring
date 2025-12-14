package ogs.spring.bibliotecaspring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

import jakarta.validation.constraints.*;

@Entity
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long libroId;

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 100, message = "El título no puede exceder 100 caracteres")
    private String titulo;

    @NotBlank(message = "El autor es obligatorio")
    @Size(max = 50, message = "El nombre del autor no puede exceder 50 caracteres")
    private String autor;

    @NotNull(message = "El año de publicación es obligatorio")
    @Min(value = 1, message = "El año de publicación debe ser mayor que 0")
    private Integer yearPublicacion;

    @NotBlank(message = "La categoría es obligatoria")
    @Size(max = 50, message = "La categoría no puede exceder 50 caracteres")
    private String categoria;

    @NotBlank(message = "El ISBN es obligatorio")
    @Size(min = 10, max = 13, message = "El ISBN debe tener entre 10 y 13 caracteres")
    private String isbn;

    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Prestamo> prestamos;

    /* =======================
       Constructores
       ======================= */
    public Libro() {
    }

    /* =======================
       Getters & Setters
       ======================= */
    public Long getLibroId() {
        return libroId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Integer getYearPublicacion() {
        return yearPublicacion;
    }

    public void setYearPublicacion(Integer yearPublicacion) {
        this.yearPublicacion = yearPublicacion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + libroId +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", yearPublicacion=" + yearPublicacion +
                ", categoria='" + categoria + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }
}
