package com.example.ProyectoWeb.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.ProyectoWeb.dto.Movies;
import com.example.ProyectoWeb.Services.MovieService;

@Controller
public class MoviesController {

        @Autowired 
        private MovieService movieService; // Declaracion_Dependencia

        @GetMapping("/")
        public String home(Model model){
                List<Movies> movies = movieService.getAllMovies(); // Inyeccion
                model.addAttribute("movies", movies);
                return "index";
        }

        // Listado con filtrado (op) por clasificacion
        @GetMapping("/movies")
        public String viewMovies(@RequestParam(value = "clasificacion", required = false) String clasificacion, Model model){
               
                // Obtencion de las movies desde Api
                List<Movies> lista = movieService.getAllMovies();

                // Si hay filtro, se aplica
                if (clasificacion != null) {
                        lista = lista.stream()
                                .filter(p -> p.getClasificacion().equalsIgnoreCase(clasificacion))
                                .toList();
                }

                // Send datos a vista
                model.addAttribute("movies", lista);
                model.addAttribute("clasificacion", clasificacion);

                return "movies";
        }

        // Detalle x Pelicula x ID
        @GetMapping("/movies/{id}")
        public String viewMovie(@PathVariable int id, Model model) {

                // Obtener la movie desde la API
                Movies movie = movieService.getMovieById(id);
                
                // Send datos a vista
                model.addAttribute("movie", movie);

                return "movieDetalle";
        }
}