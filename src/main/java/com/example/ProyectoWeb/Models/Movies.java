package com.example.ProyectoWeb.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Movies {
    @Id
    private long id;
    private String titulo;
    private String Director;
    private String anio_estreno;
    private boolean isActual;
    private String imagen;
    private String sinopsis;
    private String genero;
    private String formato;
    private String idioma;

    public Movies() {
    }

    public Movies(long id, String titulo, String director, String anio_estreno, boolean isActual, String imagen,
            String sinopsis, String genero, String formato, String idioma) {
        this.id = id;
        this.titulo = titulo;
        Director = director;
        this.anio_estreno = anio_estreno;
        this.isActual = isActual;
        this.imagen = imagen;
        this.sinopsis = sinopsis;
        this.genero = genero;
        this.formato = formato;
        this.idioma = idioma;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public String getAnio_estreno() {
        return anio_estreno;
    }

    public void setAnio_estreno(String anio_estreno) {
        this.anio_estreno = anio_estreno;
    }

    public boolean isActual() {
        return isActual;
    }

    public void setActual(boolean isActual) {
        this.isActual = isActual;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
}
