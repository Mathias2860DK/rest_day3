package dtos;

import entities.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieDTO {
    private int year;
    private String title;
    private String[] actors;


    public MovieDTO(Movie movie) {
        this.year = movie.getYear();
        this.title = movie.getTitle();
        this.actors = movie.getActors();
    }
/*
public static List<RenameMeDTO> getDtos(List<RenameMe> rms){
        List<RenameMeDTO> rmdtos = new ArrayList();
        rms.forEach(rm->rmdtos.add(new RenameMeDTO(rm)));
        return rmdtos;
    }
 */
    public static List<MovieDTO> getDtos(List<Movie> movies) {
        List<MovieDTO> movieDTOS = new ArrayList<>();
        movies.forEach(movie -> movieDTOS.add(new MovieDTO(movie)));
        return movieDTOS;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getActors() {
        return actors;
    }

    public void setActors(String[] actors) {
        this.actors = actors;
    }
}
