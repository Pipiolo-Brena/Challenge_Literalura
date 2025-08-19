package com.aluracursos.Literalura.principal;

import com.aluracursos.Literalura.model.Autor;
import com.aluracursos.Literalura.model.DatosAutor;
import com.aluracursos.Literalura.model.DatosLibro;
import com.aluracursos.Literalura.model.Libro;
import com.aluracursos.Literalura.repository.AutorRepository;
import com.aluracursos.Literalura.repository.LibroRepository;
import com.aluracursos.Literalura.service.ConvierteDatos;
import com.aluracursos.Literalura.service.DatosRespuesta;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private com.aluracursos.screenmatch.service.ConsumoAPI consumoApi = new com.aluracursos.screenmatch.service.ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosLibro> datosLibros = new ArrayList<>();
    private LibroRepository repositorioLibros;
    private AutorRepository repositorioAutores;
    private List<Libro> libros;
    private List<Autor> autores;

    public Principal(LibroRepository repository, AutorRepository repository2) {
        this.repositorioLibros = repository;
        this.repositorioAutores= repository2;
    }
    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ***********************************
                    Bienvenido a la biblioteca literalura
                    
                    1 - Buscar libro por titulo 
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    3 - Listar libros por idioma
                    
                    0 - Salir
                    ***********************************
                    Selccione una opcion:
                    """;
            System.out.println(menu);
            try {
                opcion = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un número válido.");
                continue;
            }
            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    mostrarLibrosRegistrados();
                    break;
                case 3:
                    mostrarAutoresregistrados();
                    break;
                case 4:
                    autoresFecha();
                    break;
                case 5:
                    librosIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void librosIdioma() {
        System.out.println("Introduce el idioma para buscar libros (ej: en, es, fr):");
        var idioma = teclado.nextLine();
        List<Libro> librosPorIdioma = repositorioLibros.findByIdioma(idioma);
        System.out.println(librosPorIdioma);
    }

    private void autoresFecha() {
        System.out.println("Introduce el año para buscar autores vivos:");
        var anio = teclado.nextInt();
        List<Autor> autoresVivos = repositorioAutores.buscarAutoresVivosPorAnio(anio);
        System.out.println(autoresVivos);
    }

    private List<DatosLibro> getDatosLibro() {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "+"));
        System.out.println(json);
        DatosRespuesta resultado = conversor.obtenerDatos(json, DatosRespuesta.class);

        return resultado.getResultados();
    }
    // ... dentro de tu clase Main
    private void buscarLibro() {
        System.out.println("Escribe el nombre del libro que deseas buscar:");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "+"));

        DatosRespuesta datosRespuesta = conversor.obtenerDatos(json, DatosRespuesta.class);

        List<DatosLibro> datosLibros = datosRespuesta.resultados();

        if (datosLibros == null || datosLibros.isEmpty()) {
            System.out.println("No se encontraron libros con ese título.");
            return;
        }
        DatosLibro datos = datosLibros.get(0);
        Optional<Libro> libroExistente = repositorioLibros.findByTituloIgnoreCase(datos.titulo());

        if (libroExistente.isPresent()) {
            System.out.println("El libro '" + datos.titulo() + "' ya está registrado en la base de datos.");
        } else {
            Libro libro = new Libro(datos);

            if (datos.autor() != null && !datos.autor().isEmpty()) {
                DatosAutor datosAutor = datos.autor().get(0);

                Optional<Autor> autorExistente = repositorioAutores.findByNombreIgnoreCase(datosAutor.nombre());
                Autor autor;

                if (autorExistente.isPresent()) {
                    autor = autorExistente.get();
                } else {
                    autor = new Autor(datosAutor);
                }

                List<Autor> autoresDelLibro = new ArrayList<>();
                autoresDelLibro.add(autor);
                libro.setAutores(autoresDelLibro);

                autor.getLibros().add(libro);
            } else {
                System.out.println("Este libro no tiene autor en los datos de la API.");
            }
            repositorioLibros.save(libro);
            System.out.println("Libro guardado exitosamente: " + libro.toString());
        }
    }

    private void mostrarLibrosRegistrados() {
        libros = repositorioLibros.findAll();
        libros.stream()
                .forEach(System.out::println);
    }

    private void mostrarAutoresregistrados() {
        autores = repositorioAutores.findAll();
        autores.stream()
                .forEach(System.out::println);
    }
}
