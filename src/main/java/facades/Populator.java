/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.MovieDTO;
import entities.Movie;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        MovieFacade movieFacade = MovieFacade.getMovieFacade(emf);
        movieFacade.create(new MovieDTO(new Movie(2000, "FistMovie", new String[]{"Actor1", "Actor2"})));
        movieFacade.create(new MovieDTO(new Movie(2010, "Second Movie", new String[]{"Actor1", "Actor2"})));
        movieFacade.create(new MovieDTO(new Movie(2020, "Third Movie", new String[]{"Actor1", "Actor2"})));
    }
    
    public static void main(String[] args) {
        populate();
    }
}
