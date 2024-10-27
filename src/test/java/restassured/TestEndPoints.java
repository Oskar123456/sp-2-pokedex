package restassured;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.PrintStream;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

import dk.obhnothing.persistence.HibernateConfig;
import dk.obhnothing.persistence.dao.PokeDAO;
import dk.obhnothing.persistence.dto.PokemonDTO;
import dk.obhnothing.persistence.ent.Pokemon;
import dk.obhnothing.persistence.service.Mapper;
import dk.obhnothing.routes.PokemonRoutes;
import dk.obhnothing.security.entities.Role;
import dk.obhnothing.security.entities.User;
import dk.obhnothing.utilities.Utils;
import io.javalin.Javalin;
import io.restassured.RestAssured;
import io.restassured.RestAssured.*;
import io.restassured.internal.ResponseSpecificationImpl.HamcrestAssertionClosure;
import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.matcher.RestAssuredMatchers.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.*;

public class TestEndPoints
{

    static EntityManagerFactory EMF;
    static ObjectMapper om;
    static Javalin jav;
    private static String securityToken;

    private static void login(String username, String password) {
        ObjectNode objectNode = om.createObjectNode()
            .put("username", username)
            .put("password", password);
        String loginInput = objectNode.toString();
        securityToken = RestAssured.given()
            .contentType("application/json")
            .body(loginInput)
            //.when().post("/api/login")
            .when().post("/api/auth/login")
            .then()
            .extract().path("token");
        System.out.println("TOKEN ---> " + securityToken);
    }

    @BeforeAll
    static void init()
    {
        PrintStream stdout = System.out;
        PrintStream stderr = System.err;
        System.setOut(new PrintStream(PrintStream.nullOutputStream()));
        System.setErr(new PrintStream(PrintStream.nullOutputStream()));

        HibernateConfig.Init(HibernateConfig.Mode.TEST);
        EMF = HibernateConfig.getEntityManagerFactory();
        PokeDAO.Init(EMF);

        try (EntityManager em = EMF.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM User u").executeUpdate();
            em.createQuery("DELETE FROM Role r").executeUpdate();
            User user = new User("user", "user123");
            User admin = new User("admin", "admin123");
            User superUser = new User("super", "super123");
            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            user.addRole(userRole);
            admin.addRole(adminRole);
            superUser.addRole(userRole);
            superUser.addRole(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(superUser);
            em.persist(userRole);
            em.persist(adminRole);
            em.getTransaction().commit();
        }

        jav = PokemonRoutes.setup();
        new Thread(() -> jav.start(8080)).run();

        om = Utils.getObjectMapper();
        om.enable(SerializationFeature.INDENT_OUTPUT);

        System.setOut(stdout);
        System.setErr(stderr);

    }

    @AfterAll
    static void teardown()
    {
        PrintStream stdout = System.out;
        PrintStream stderr = System.err;
        System.setOut(new PrintStream(PrintStream.nullOutputStream()));
        System.setErr(new PrintStream(PrintStream.nullOutputStream()));
        jav.stop();
        System.setOut(stdout);
        System.setErr(stderr);
    }

    @Test @DisplayName("ensure empty")
    void testInit()
    {

        Pokemon[] pokemons = RestAssured.get("api/pokemon/all").then().extract().body().jsonPath().getObject("", Pokemon[].class);
        assertThat(pokemons.length, equalTo(0));

    }

    @Test @DisplayName("CRUD: create, update, delete")
    void testCreate() throws Exception
    {
        login("admin", "admin123");

        String json = new String(ClassLoader.getSystemResourceAsStream("ditto.json").readAllBytes());

        PokemonDTO pokeDTO = om.readValue(json, PokemonDTO.class);
        /* CREATE */
        Pokemon pokemon_ditto = RestAssured.given()
            .contentType("application/json").body(json)
            .header("Authorization", "Bearer " + securityToken)
            .when().post("/api/pokemon")
            .then().extract().body().as(Pokemon.class);

        assertThat(pokemon_ditto.id, equalTo(13337));
        assertThat(pokemon_ditto.hp, equalTo(48));

        Pokemon pokemon_ditto_from_db = PokeDAO.findById(13337);

        assertThat(pokemon_ditto_from_db, equalTo(pokemon_ditto));

        pokeDTO.name = "not_ditto";

        /* UPDATE */
        Pokemon pokemon_ditto_posted = RestAssured.given()
            .contentType("application/json").body(om.writeValueAsString(pokeDTO))
            .header("Authorization", "Bearer " + securityToken)
            .when().post("/api/pokemon")
            .then().extract().body().as(Pokemon.class);

        pokemon_ditto_from_db = PokeDAO.findById(13337);

        assertThat(pokemon_ditto_from_db.name, equalTo("not_ditto"));

        /* DELETE */
        Pokemon pokemon_ditto_deleted = RestAssured.given()
            .contentType("application/json").queryParam("id", 13337)
            .header("Authorization", "Bearer " + securityToken)
            .when().delete("/api/pokemon")
            .then().extract().body().as(Pokemon.class);

        Pokemon pokemon_ditto_from_db_after_delete = PokeDAO.findById(13337);

        assertThat(pokemon_ditto_from_db_after_delete, equalTo(null));

    }


}



















