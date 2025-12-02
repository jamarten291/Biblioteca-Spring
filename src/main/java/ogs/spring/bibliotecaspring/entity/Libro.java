package ogs.spring.bibliotecaspring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long libroId;
    private String titulo;
    private String autor;
    private Integer yearPublicacion;
    private String categoria;
    private String isbn;
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Prestamo> prestamos;

    public Libro() {
    }

    public Long getLibroId() {
        return libroId;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public Integer getYearPublicacion() {
        return yearPublicacion;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setYearPublicacion(Integer yearPublicacion) {
        this.yearPublicacion = yearPublicacion;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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
