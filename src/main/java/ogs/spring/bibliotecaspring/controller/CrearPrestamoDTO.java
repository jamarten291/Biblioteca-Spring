package ogs.spring.bibliotecaspring.controller;

public class CrearPrestamoDTO {
    private Long socioId;
    private Long libroId;

    public CrearPrestamoDTO(Long socioId, Long libroId) {
        this.socioId = socioId;
        this.libroId = libroId;
    }

    public Long getSocioId() {
        return socioId;
    }

    public Long getLibroId() {
        return libroId;
    }
}
