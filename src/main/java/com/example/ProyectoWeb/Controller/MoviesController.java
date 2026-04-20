package com.example.ProyectoWeb.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ProyectoWeb.Models.Movies;

// import java.util.List;
// import java.util.stream.Collectors;

// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestParam;

// import com.example.demo.Model.Movies;

@Controller
public class MoviesController {

    String imagen = "https://m.media-amazon.com/images/I/61qhqhlA14L._AC_SY300_SX300_QL70_ML2_.jpg";

    private final List<Movies> movies = List.of(

        new Movies(
            1, 
            "Avatar", 
            "James Cameron",
            "Un exmarine se une a los Na'vi.",
            "Ciencia Ficción", 
            "HD", 
            "Inglés", imagen, 
            "PG-13",
            LocalDate.of(2009, 12, 18), 
            LocalDate.of(2010, 4, 10)
        ),

        new Movies(
            3, 
            "El Proyecto de la Bruja de Blair", 
            "Daniel Myrick",
            "Tres jóvenes desaparecen en un bosque.",
            "Terror", 
            "HD", 
            "Inglés", imagen,
            "+18",
            LocalDate.of(1999, 7, 30), 
            LocalDate.of(1999, 10, 15)
        ),

        new Movies(
            4, 
            "Hereditary", 
            "Ari Aster",
            "Una familia marcada por una presencia oscura.",
            "Terror", 
            "HD", 
            "Inglés", imagen,
            "+18",
            LocalDate.of(2018, 6, 8), LocalDate.of(2018, 9, 1)
        ),

        new Movies(
            5, 
            "Star Wars: Episodio III – La Venganza de los Sith", "George Lucas",
            "La caída de Anakin Skywalker.",
            "Ciencia Ficción", 
            "HD", "Inglés", imagen,
            "PG-13",
            LocalDate.of(2005, 5, 19), 
            LocalDate.of(2005, 9, 1)
        ),

        new Movies(
            6, 
            "Eclipse Cuántico", 
            "Lena Hartmann",
            "Una anomalía cuántica amenaza la realidad.",
            "Ciencia Ficción", 
            "4K", 
            "Inglés", imagen,
            "PG-13",
            LocalDate.of(2027, 6, 14), 
            LocalDate.of(2027, 10, 1)
        ),

        new Movies(
            7, 
            "Horizonte Pasado-Futuro", 
            "Marco Velázquez",
            "Una Tesla, revivido, controla el clima global.",
            "Ciencia Ficción", 
            "4K", 
            "Inglés", imagen,
            "PG-13",
            LocalDate.of(2028, 3, 22), 
            LocalDate.of(2028, 7, 15)
        ),

        //  Rápidos y Furiosos XV 
        new Movies(
            8, 
            "Rápidos y Furiosos XV: Carrera Final", 
            "Justin Lin",
            "Dom enfrenta su última misión en una carrera por el anillo de jupiter.",
            "Acción", 
            "4K",
            "Inglés", imagen,
            "PG-13",
            LocalDate.of(2029, 5, 10), 
            LocalDate.of(2029, 9, 1)
        ),

        //  Dragon Ball Super: Broly
        new Movies(
            11, "Dragon Ball Super: Broly", 
            "Tatsuya Nagamine",
            "Goku y Vegeta enfrentan al legendario Broly.",
            "Anime", 
            "HD", 
            "Japonés", imagen,
            "PG-13",
            LocalDate.of(2018, 12, 14), 
            LocalDate.of(2019, 3, 1)
        )
    );

    @GetMapping("/movies")
    public String mostrarProductos(@RequestParam(value="clasificacion", required=false) String clasificacion, Model model) {
        List<Movies> listaFiltrada;
        if(clasificacion != null) {
            listaFiltrada = movies.stream()
                                    .filter(p -> p.getClasificacion().equalsIgnoreCase(clasificacion))
                                    .collect(Collectors.toList());
        } else {         listaFiltrada = movies;        }
        model.addAttribute("movies", listaFiltrada);
        model.addAttribute("clasificacion", clasificacion);
        return "movies";
    }

    @GetMapping("/movies/{id}")
    public String mostrarProductosClasificacion(@PathVariable String id, Model model) {
        List<Movies> listaFiltrada;
        listaFiltrada = movies.stream()
                                    .filter(p -> p.getClasificacion().equalsIgnoreCase(id))
                                    .collect(Collectors.toList());
        model.addAttribute("movies", listaFiltrada);
        model.addAttribute("clasificacion", id);
        return "movies";
    }
}