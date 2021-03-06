package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.MovieDTO;
import entities.Movie;
import facades.MovieFacade;
import facades.Populator;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

//Todo Remove or change relevant parts before ACTUAL use
@Path("movie")
public class RenameMeResource {

    private final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private final MovieFacade FACADE =  MovieFacade.getMovieFacade(EMF);
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMovieCount() {
       
        long count = FACADE.getMovieCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":"+count+"}";  //Done manually so no need for a DTO
    }

    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllMovies() {
        EntityManager em = EMF.createEntityManager();
        List<Movie> movies = em.createNamedQuery("Movie.getAll").getResultList();

        List<MovieDTO> movieDTOS = new ArrayList<>();
        for (Movie movie: movies) {
            MovieDTO movieDTO = new MovieDTO(movie);
            movieDTOS.add(movieDTO);
        }

        return GSON.toJson(movieDTOS);
    }

    @Path("populate")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String populate() {
        Populator.populate();
        return "Congrats. Movies has been inserted";
    }


    @Path("title/{title}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMovieByTitle(@PathParam("title") String title) {
List<MovieDTO> movieDTOS = FACADE.getMovieDTOByTitle(title);

        return GSON.toJson(movieDTOS);
    }

    @Path("id/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMovieById(@PathParam("id") long id) {
        //http://localhost:8080/rest_day3_war_exploded/api/movie/id/2
        MovieDTO movieDto = FACADE.getMovieDtoById(id);

        return GSON.toJson(movieDto);
    }

}
