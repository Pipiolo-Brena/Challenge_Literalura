package com.aluracursos.Literalura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String titulo;
    private String idioma;
    private double descargas;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores;

    public Libro(){}

    public Libro(DatosLibro datosLibro) {
        if (!datosLibro.idioma().isEmpty()) {
            this.idioma = datosLibro.idioma().get(0);
        } else {
            this.idioma = "desconocido"; // O algún valor por defecto
        }
        this.titulo = datosLibro.titulo();
        this.descargas = datosLibro.descargas();
    }

    @Override
    public String toString() {
        return "Libro{" +
                "Titulo='" + titulo + '\'' +
                ", idioma='" + idioma + '\'' +
                ", descargas=" + descargas +
                ", autores=" + autores.stream()
                .map(Autor::getNombre)
                .collect(Collectors.toList()) +
                '}';
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getDescargas() {
        return descargas;
    }

    public void setDescargas(double descargas) {
        this.descargas = descargas;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public void setAutor(Autor autor) {

    }
}
