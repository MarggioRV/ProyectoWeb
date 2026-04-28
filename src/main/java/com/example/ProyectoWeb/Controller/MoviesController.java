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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MoviesController {

        @Autowired
        private MovieService movieService; // Declaracion_Dependencia

        @GetMapping("/")
        public String home(Model model) {
                // Obtener data de las movies y send a vista
                model.addAttribute("destacadas", movieService.getMoviesEnCartelera()); // API
                model.addAttribute("cartelera", movieService.getMoviesEnCartelera());
                model.addAttribute("proximamente", movieService.getMoviesProximamente());
                return "index";
        }

        // Listado con filtrado (op) por clasificacion, formato e idioma
        @GetMapping("/movies")
        public String viewMovies(

                        // Rcb filtro de clasificación desde la URL
                        @RequestParam(value = "clasificacion", required = false) String clasificacion, // required
                                                                                                       // false,
                                                                                                       // significa
                                                                                                       // opcional

                        // Rcb filtro de formato
                        @RequestParam(value = "formato", required = false) String formato,

                        // Rcb filtro de idioma
                        @RequestParam(value = "idioma", required = false) String idioma,

                        // Rcb filtro de año
                        @RequestParam(value = "anio", required = false) Integer anio,
                        // Objeto send
                        Model model) {

                // Obtener todas las películas desde el service
                List<Movies> lista = movieService.getAllMovies();

                // Si clasificación tiene valor, filtrar lista
                if (clasificacion != null && !clasificacion.isBlank())
                        // lista es convertida, momentaneamente, a stream para aplicar filtro
                        lista = lista.stream()
                                        // Solo deja coincidencias ignorando mayúsculas/minúsculas
                                        .filter(p -> p.getClasificacion().equalsIgnoreCase(clasificacion))
                                        // Convierte stream en lista nuevamente
                                        .toList();

                // Lo =, con formato
                if (formato != null && !formato.isBlank())
                        lista = lista.stream()
                                        // Filtra x formato
                                        .filter(p -> p.getFormato().equalsIgnoreCase(formato))
                                        .toList();

                // Lo =, con idioma
                if (idioma != null && !idioma.isBlank())
                        lista = lista.stream()
                                        // Filtra x idioma
                                        .filter(p -> p.getIdioma().equalsIgnoreCase(idioma))
                                        .toList();

                // Lo =, con año
                if (anio != null)
                        lista = lista.stream()
                                        .filter(m -> m.getFechaEstreno().getYear() == anio)
                                        .toList();

                // Enviar lista final filtrada a la vista
                model.addAttribute("movies", lista);

                // Enviar valores seleccionados para mantener filtros marcados
                model.addAttribute("clasificacion", clasificacion);
                model.addAttribute("formato", formato);
                model.addAttribute("idioma", idioma);
                model.addAttribute("anio", anio);

                // Retorna plantilla
                return "movies";
        }

        // Detalle x Pelicula x ID
        @GetMapping("/movies/{id}")
        public String viewMovie(@PathVariable int id, Model model) {

                // Obtener la movie desde la API
                Movies movie = movieService.getMovieById(id);

                // Si no existe, ir a error
                if (movie == null) {
                        return "error1";
                }

                // Send datos a vista
                model.addAttribute("movie", movie);

                return "movieDetalle";
        }

        @GetMapping("/movie/editar")
        public String crudPage(Model model) {

                // Send datos a vista
                model.addAttribute("movie", new Movies()); // formulario vacío
                model.addAttribute("movies", movieService.getAllMovies()); // cards existentes

                return "editar";
        }

        // Crear película
        @PostMapping("/movie/editar")
        public String crearMovie(@ModelAttribute Movies movie, RedirectAttributes ra) {
                // Capurando y pintando mensajes
                try {
                        // Llamada al servicio para crear película
                        movieService.crearMovie(movie);

                        // Mensaje de éxito
                        ra.addFlashAttribute("alertTipo", "success");
                        ra.addFlashAttribute("alertMsg", "Película creada correctamente.");
                } catch (Exception e) {
                        // Mensaje de error
                        ra.addFlashAttribute("alertTipo", "danger");
                        ra.addFlashAttribute("alertMsg", "Error al crear");
                }
                return "redirect:/movie/editar";
        }

        // Actualizar película
        @PostMapping("/movie/editar/{id}")
        public String actualizarMovie(@PathVariable int id, @ModelAttribute Movies movie, RedirectAttributes ra) {
                try {
                        // Llamada al servicio para actualizar
                        movieService.actualizarMovie(id, movie);
                        // Mensaje de éxito
                        ra.addFlashAttribute("alertTipo", "success");
                        ra.addFlashAttribute("alertMsg", "Película #" + id + " actualizada correctamente.");
                } catch (Exception e) {
                        // Mensaje de error
                        ra.addFlashAttribute("alertTipo", "danger");
                        ra.addFlashAttribute("alertMsg", "Error al actualizar");
                }
                return "redirect:/movie/editar";
        }

        // Eliminar
        @PostMapping("/movie/eliminar/{id}")
        public String eliminarMovie(@PathVariable int id, RedirectAttributes ra) {
                try {
                        // Llamada al servicio para delete
                        movieService.eliminarMovie(id);
                        // Mensaje de éxito
                        ra.addFlashAttribute("alertTipo", "warning");
                        ra.addFlashAttribute("alertMsg", "Película #" + id + " eliminada.");
                } catch (Exception e) {
                        // Mensaje de error
                        ra.addFlashAttribute("alertTipo", "danger");
                        ra.addFlashAttribute("alertMsg", "Error al eliminar");
                }
                return "redirect:/movie/editar";
        }

        // Cargar formulario con datos de película existente
        @GetMapping("/movie/editar/{id}")
        public String cargarEditar(@PathVariable int id, Model model) {
                // Obtener la movie desde la API y send a vista
                model.addAttribute("movie", movieService.getMovieById(id));
                model.addAttribute("movies", movieService.getAllMovies());
                return "editar";
        }

        // Buscar por título o ID
        @GetMapping("/buscar")
        public String buscar(@RequestParam String q, Model model) {

                // Búsqueda vacía
                if (q == null || q.isBlank()) {
                        return "redirect:/movies";
                }

                // Si es número, buscar por ID directamente
                if (q.matches("\\d+")) {
                        return "redirect:/movies/" + q;
                }

                // Si es texto, delegar búsqueda al servicio
                List<Movies> resultado = movieService.buscarXTitulo(q);

                // Si solo hay un resultado, ir directo al detalle
                if (resultado.size() == 1) {
                        return "redirect:/movies/" + resultado.get(0).getId();
                }

                // Si hay varios, mostrarlos en movies
                if (!resultado.isEmpty()) {
                        model.addAttribute("movies", resultado);
                        model.addAttribute("busqueda", q);
                        return "movies";
                }

                // Si no hay resultados, buscar director
                String directorQuery = q.replace("_", " "); // Acepta, tmb, guiones bajos

                // Buscar por director (mayúsculas/minúsculas)
                List<Movies> porDirector = movieService.getAllMovies().stream()
                                .filter(m -> m.getDirector().toLowerCase()
                                                .contains(directorQuery.toLowerCase()))
                                .toList();

                // Si hay resultados por director, mostrarlos
                if (!porDirector.isEmpty()) {
                        model.addAttribute("movies", porDirector);
                        model.addAttribute("busqueda", q);
                        return "movies";
                }

                // Sin resultados = pagina error
                return "error1";
        }
}