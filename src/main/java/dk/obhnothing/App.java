package dk.obhnothing;

import java.io.PrintStream;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import dk.obhnothing.persistence.HibernateConfig;
import dk.obhnothing.persistence.dao.PokeDAO;
import dk.obhnothing.persistence.ent.Pokemon;
import dk.obhnothing.persistence.service.Fetch;
import dk.obhnothing.routes.PokemonRoutes;
import dk.obhnothing.utilities.Utils;
import io.javalin.Javalin;
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

    private static ObjectMapper jsonMapper = Utils.getObjectMapper();
    private static Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);
    private static EntityManagerFactory EMF;

    public static void main(String... args)
    {
        /* INIT */
        jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
        //PrintStream stdout = System.out;
        //PrintStream stderr = System.err;
        //System.setOut(new PrintStream(PrintStream.nullOutputStream()));
        //System.setErr(new PrintStream(PrintStream.nullOutputStream()));

        HibernateConfig.Init(HibernateConfig.Mode.DEV);
        EMF = HibernateConfig.getEntityManagerFactory();
        PokeDAO.Init(EMF);
        Random rng = new Random();

        try

        {

            Javalin jav = PokemonRoutes.setup();
            jav.start(9999);

            //System.setOut(stdout);
            //System.setErr(stderr);

            System.out.println("listening..." + System.lineSeparator());

            for (int i = 0; i < 100; ++i) {
                Pokemon p = Fetch.pokemonById(i);
            }

            List<Pokemon> p_all = PokeDAO.getAll();
            System.out.println(p_all.stream().map(pk -> pk.name).collect(Collectors.joining(", ")));

            /* TEST */

        }

        catch(Exception e)

        {
            e.printStackTrace();
            EMF.close();
        }

    }

}





























