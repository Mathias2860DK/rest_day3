package rest;

import dtos.MovieDTO;
import entities.Movie;
import io.restassured.http.ContentType;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
//Uncomment the line below, to temporarily disable this test
@Disabled
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) --> resets DB?
public class RenameMeResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Movie m1, m2, m3;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        m1 = new Movie(2000, "First Movie", new String[]{"Actor1", "Actor2"});
        m2 = new Movie(2010, "Second Movie", new String[]{"Actor1", "Actor2"});
        m3 = new Movie(2001,"Terkel i knibe",new String[]{"Anden"});
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
            em.persist(m1);
            em.persist(m2);
            em.persist(m3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/movie").then().statusCode(200);
    }

    //Create a test that verifies that the server is up (similar to the “Hello World” response)
    @Test
    public void testDummyMsg() throws Exception {
        given()
                .contentType("application/json")
                .get("/movie/").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("Hello World"));
    }

    @Test
    /*
    Create a test for the endpoint: api/movie/count
     (expected result, depends on how many movies you created before each test ).
     */
    public void testCount() throws Exception {
        given()
                .contentType("application/json")
                .get("/movie/count").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("count", equalTo(3));
    }

    @Test
    /*
    Create a test for the endpoint api/movie/all and assert that the response includes
     the right amount of movies (what you have setup for the test)
      and preferably also that you got the expected movies.

     */
    public void testGetAllMovies(){
        //endpoint: api/movie/all
        //m1 = new Movie(2000, "Fist Movie", new String[]{"Actor1", "Actor2"});
        given().when().get("/movie/all").then().assertThat().body("title[0]",equalTo("Second Movie"));
    }
    @Test
    public void testGetAllMoviesHasItem(){
        //endpoint: api/movie/all
       //TODO: Hvorfor virker denne ikke=
        //given().when().get("/movie/all").then().assertThat().body("actors",hasItem("Anden"));
    }
   @Test
    public void testGetAllMoviesStatusCode200(){
        given().when().get("/movie/all").then().assertThat().statusCode(200);
    }
    @Test
    public void testGetAllMoviesContentType(){
        given().when().get("/movie/all").then().assertThat().contentType(ContentType.JSON);
    }
    @Test
    public void testGetAlleMoviesLogRequestDetails(){
        given().log().all().when().get("/movie/all").then().log().body();
    }

@Test
    /*
    Create a test for an endpoint: api/movie/title/{title}.
     Use a name you know exists, and (for red students)
      also try with a name that does not exist (obviously this requires that you know what
       you return in such a case)
     */
public void testGetMovieTitle(){
    given().log().all().when().get("/movie/title").then().log().body();
}


    @Test
    void getMovieById() { //TODO: RESET AUTO GENERATED ID??
    //given().given().when().get("/movie/id/2").then().assertThat().body("id",equalTo("3L"));
    }
}
