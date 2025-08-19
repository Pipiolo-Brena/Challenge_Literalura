package com.aluracursos.Literalura.dto;

import java.util.List;

public record LibroDTO(
        Long autorId,
        String titulo,
        List<String> idioma,
        Long descargas
) {
}
