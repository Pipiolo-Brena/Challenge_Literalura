package com.aluracursos.Literalura.service;

import com.aluracursos.Literalura.model.DatosLibro;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosRespuesta(@JsonProperty("results") List<DatosLibro> resultados) {

    public List<DatosLibro> getResultados() {
        return  resultados;
    }
}
