package dk.obhnothing;

import java.io.PrintStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import dk.obhnothing.control.Master;
import dk.obhnothing.persistence.HibernateConfig;
import dk.obhnothing.persistence.dto.PokemonDTO;
import dk.obhnothing.routes.PokemonRoutes;
import dk.obhnothing.security.controllers.AccessController;
import dk.obhnothing.security.controllers.SecurityController;
import dk.obhnothing.security.routes.SecurityRoutes;
import dk.obhnothing.utilities.Utils;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import jakarta.persistence.EntityManagerFactory;

/*
 * Web development....
 * -------------------
 * Oskar Bahner Hansen
 * ......obhnothing.dk
 * .........2024-09-29
 * -------------------
 */

public class App
{

    private static ObjectMapper jsonMapper = new Utils().getObjectMapper();
    private static Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);
    private static EntityManagerFactory EMF;

    public static void main(String... args)
    {
        /* INIT */
        jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
        PrintStream stdout = System.out;
        PrintStream stderr = System.err;
        System.setOut(new PrintStream(PrintStream.nullOutputStream()));
        System.setErr(new PrintStream(PrintStream.nullOutputStream()));

        HibernateConfig.Init(HibernateConfig.Mode.DEV);
        EMF = HibernateConfig.getEntityManagerFactory();
        Random rng = new Random();

        try

        {

            Javalin jav = PokemonRoutes.setup();
            jav.start(8080);

            System.setOut(stdout);
            System.setErr(stderr);

            System.out.println("listening..." + System.lineSeparator());

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create("https://pokeapi.co/api/v2/pokemon/ditto"))
                .build();
            HttpResponse<String> res = client.send(req, BodyHandlers.ofString());

            String res_str = res.body();
            //System.out.println(res_str);

            PokemonDTO pokDTO = jsonMapper.readValue(res_str, PokemonDTO.class);

            System.out.println(pokDTO.toString());
            System.out.println(jsonMapper.writeValueAsString(pokDTO));

            /* TEST */

        }

        catch(Exception e)

        {
            e.printStackTrace();
            EMF.close();
        }

    }

}





























