package dk.obhnothing;

import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sun.net.httpserver.HttpServer;

import dk.obhnothing.control.*;
import dk.obhnothing.persistence.HibernateConfig;
import dk.obhnothing.persistence.dao.hoteldao;
import dk.obhnothing.persistence.dao.roomdao;
import dk.obhnothing.persistence.entities.hotel;
import dk.obhnothing.persistence.entities.room;
import dk.obhnothing.utilities.JsonUtils;
import dk.obhnothing.utilities.PrettyPrinter;
import dk.obhnothing.utilities.JsonUtils.JsonObjectStr;
import io.javalin.Javalin;
import io.javalin.http.Context;
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

    private static Routes routes = new Routes();
    private static ObjectMapper jsonMapper = new Utils().getObjectMapper();
    private static SecurityController securityController = SecurityController.getInstance();
    private static AccessController accessController = new AccessController();
    private static Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);
    private static EntityManagerFactory EMF;

    public static void main(String... args)
    {
        /* INIT */
        //PrintStream stdout = System.out;
        //PrintStream stderr = System.err;
        //System.setOut(new PrintStream(PrintStream.nullOutputStream()));
        //System.setErr(new PrintStream(PrintStream.nullOutputStream()));

        Random rng = new Random();

        HibernateConfig.Init(HibernateConfig.Mode.DEV);
        EMF = HibernateConfig.getEntityManagerFactory();
        hoteldao.init(EMF);
        roomdao.init(EMF);
        ObjectMapper om = new ObjectMapper();
        om.findAndRegisterModules();
        om.enable(SerializationFeature.INDENT_OUTPUT);

        try

        {

            Javalin jav = Javalin.create(config -> {
                config.showJavalinBanner = false;
                config.staticFiles.add(System.getenv("PWD") + "/src/main/resources/html", Location.EXTERNAL);
                config.staticFiles.add(System.getenv("PWD") + "/src/main/resources/js", Location.EXTERNAL);
                config.staticFiles.add(System.getenv("PWD") + "/src/main/resources/css", Location.EXTERNAL);
                config.staticFiles.add(System.getenv("PWD") + "/src/main/resources/images", Location.EXTERNAL);
                config.bundledPlugins.enableRouteOverview("/routes");
                //config.router.contextPath = "/api"; // base path for all endpoints
                config.router.apiBuilder(SecurityRoutes.getSecuredRoutes());
                config.router.apiBuilder(SecurityRoutes.getSecurityRoutes());
                config.router.apiBuilder(routes.getRoutes());
            });

            Master.Init(jav);
            jav.start(8080);

            //System.setOut(stdout);
            //System.setErr(stderr);

            /* TEST */

        }

        catch(Exception e)

        {
            e.printStackTrace();
            EMF.close();
        }

    }

}





























