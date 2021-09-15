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
    public String getRenameMeCount() {
       
       // long count = FACADE.getRenameMeCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":"+10+"}";  //Done manually so no need for a DTO
    }

    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllEmployees() {
        Populator.populate();
        EntityManager em = EMF.createEntityManager();
        List<Movie> movies = em.createNamedQuery("Movie.getAll").getResultList();

        List<MovieDTO> movieDTOS = new ArrayList<>();
        for (Movie movie: movies) {
            MovieDTO movieDTO = new MovieDTO(movie);
            movieDTOS.add(movieDTO);
        }

        return GSON.toJson(movieDTOS);
    }

}
