package com.aluracursos.Literalura.repository;

import com.aluracursos.Literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    Optional<Libro> findByTituloContainingIgnoreCase(String nombreSerie);

    Optional<Libro> findByTituloIgnoreCase(String titulo);

    List<Libro> findByIdioma(String idioma);
}
