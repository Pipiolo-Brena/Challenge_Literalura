package com.aluracursos.Literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String nombre;
    private int fechaNacimiento;
    private  int fechaFallecido;
    @ManyToMany(mappedBy = "autores", fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor() {
        this.libros = new ArrayList<>(); // <-- Inicialización en el constructor vacío
    }

    public Autor(DatosAutor datos) {
        this.nombre = datos.nombre();
        this.fechaNacimiento = datos.fechaNacimiento();
        this.fechaFallecido = datos.fechaFallecido();
        this.libros = new ArrayList<>(); // <-- Inicialización en el constructor con parámetros
    }

    @Override
    public String toString() {
        return "Autor{" +
                "Nombre='" + nombre + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", fechaFallecido=" + fechaFallecido +
                ", libros=" + libros.stream()
                .map(Libro::getTitulo)
                .collect(Collectors.toList()) +
                '}';
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public int getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(int fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getFechaFallecido() {
        return fechaFallecido;
    }

    public void setFechaFallecido(int fechaFallecido) {
        this.fechaFallecido = fechaFallecido;
    }
}
