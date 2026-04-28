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

    private final String URL = "http://localhost:8081/movies";
    private final RestTemplate restTemplate = new RestTemplate();

    public List<Movies> getAllMovies() {
        Movies[] response = restTemplate.getForObject(URL, Movies[].class);
        return Arrays.asList(response);
    }

    public Movies getMovieById(int id) {
        // Obtener data de la movie desde la API
        return restTemplate.getForObject(URL + "/" + id, Movies.class);
    }

    public Movies crearMovie(Movies movie) {
        return restTemplate.postForObject(URL, movie, Movies.class);
    }

    public void actualizarMovie(int id, Movies movie) {
        restTemplate.put(URL + "/" + id, movie);
    }

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
