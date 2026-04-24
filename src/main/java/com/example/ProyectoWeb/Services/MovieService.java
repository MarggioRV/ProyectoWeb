package com.example.ProyectoWeb.Services;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.ProyectoWeb.dto.Movies;

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
}
