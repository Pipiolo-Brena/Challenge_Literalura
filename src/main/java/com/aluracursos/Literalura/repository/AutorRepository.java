package com.aluracursos.Literalura.repository;

import com.aluracursos.Literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor,Long> {

    Optional<Autor> findByNombreIgnoreCase(String nombre);

    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento <= :anio AND (a.fechaFallecido > :anio OR a.fechaFallecido IS NULL)")
    List<Autor> buscarAutoresVivosPorAnio(Integer anio);
}
