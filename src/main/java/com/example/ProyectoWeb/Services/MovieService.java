package com.example.ProyectoWeb.Services;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.ProyectoWeb.dto.Movies;
// import java.util.Collections;
// import java.util.Objects;
// import java.util.stream.Collectors;

@Service
public class MovieService {

    // URL base del backend
    private final String URL = "http://localhost:8081/movies";

    // Cliente HTTP para consumir la API
    private final RestTemplate restTemplate = new RestTemplate();

    // Consiguir todas las películas desde la API (Get)
    public List<Movies> getAllMovies() {
        // Llama al endpoint GET /movies y recibe un array JSON
        Movies[] response = restTemplate.getForObject(URL, Movies[].class);
        // Convierte el array a lista y lo retorna
        return Arrays.asList(response);
    }

    // Obtiener la movie x ID (Get)
    public Movies getMovieById(int id) {
        // Llama al endpoint GET /movies/{id} y retorna la película
        return restTemplate.getForObject(URL + "/" + id, Movies.class);
    }

    // Crear una nueva movie (POST)
    public Movies crearMovie(Movies movie) {
        return restTemplate.postForObject(URL, movie, Movies.class);
    }

    // Actualizar una película existente (PUT)
    public void actualizarMovie(int id, Movies movie) {
        restTemplate.put(URL + "/" + id, movie);
    }

    // Eliminar una movie existente
    public void eliminarMovie(int id) {
        restTemplate.delete(URL + "/" + id);
    }

    // Listado de proximamente
    public List<Movies> getMoviesProximamente() {
        // Obtener data de las movies desde la API
        Movies[] r = restTemplate.getForObject(URL + "/proximamente", Movies[].class);
        // Convertir array a List y retornar
        return Arrays.asList(r);
    }

    // Listado de cartelera
    public List<Movies> getMoviesEnCartelera() {
        // Obtener data de las movies desde la API
        Movies[] r = restTemplate.getForObject(URL + "/cartelera", Movies[].class);
        // Convertir array a List y retornar
        return Arrays.asList(r);
    }

    // Reutiliza Caretelera pero limita a 5
    public List<Movies> getMoviesDestacadas() {
        return getMoviesEnCartelera().stream()
                // Corte
                .limit(5)
                .toList();
    }

    // Hacer llamada a la API para buscar por título
    public List<Movies> buscarXTitulo(String titulo) {
        try {
            // Llamada al endpoint de búsqueda de la API
            Movies[] response = restTemplate.getForObject(
                    URL + "/buscar?titulo=" + titulo,
                    Movies[].class);

            // Por si no devuelve nada
            if (response == null) {
                throw new RuntimeException("No se recibió respuesta de la API");
            }

            return Arrays.asList(response);

        } catch (Exception e) {
            System.err.println("Error buscando película: " + e.getMessage());
            return List.of();
        }
    }
}
