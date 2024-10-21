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

import dk.obhnothing.persistence.HibernateConfig;
import dk.obhnothing.persistence.dao.hoteldao;
import dk.obhnothing.persistence.dao.roomdao;
import dk.obhnothing.persistence.dto.hoteldto;
import dk.obhnothing.persistence.entities.hotel;
import dk.obhnothing.persistence.service.Mapping;
import dk.obhnothing.utilities.PrettyPrinter;
import dk.obhnothing.App;
import dk.obhnothing.control.Master;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.restassured.RestAssured;
import io.restassured.RestAssured.*;
import io.restassured.internal.ResponseSpecificationImpl.HamcrestAssertionClosure;
import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.matcher.RestAssuredMatchers.*;
import jakarta.persistence.EntityManagerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.*;

public class TestEndPoints
{

    static EntityManagerFactory EMF;
    static ObjectMapper om;
    static Javalin jav;

    @BeforeAll
    static void init()
    {
        PrintStream stdout = System.out;
        PrintStream stderr = System.err;
        System.setOut(new PrintStream(PrintStream.nullOutputStream()));
        System.setErr(new PrintStream(PrintStream.nullOutputStream()));

        PrettyPrinter.withColor("aoe", PrettyPrinter.ANSIColorCode.ANSI_BLACK);
        HibernateConfig.Init(HibernateConfig.Mode.TEST);
        EMF = HibernateConfig.getEntityManagerFactory();
        hoteldao.init(EMF);
        roomdao.init(EMF);

        jav = Javalin.create(config -> {
            config.showJavalinBanner = false;
            config.staticFiles.add(System.getenv("PWD") + "/src/main/resources/html", Location.EXTERNAL);
            config.staticFiles.add(System.getenv("PWD") + "/src/main/resources/js", Location.EXTERNAL);
            config.staticFiles.add(System.getenv("PWD") + "/src/main/resources/css", Location.EXTERNAL);
            config.staticFiles.add(System.getenv("PWD") + "/src/main/resources/images", Location.EXTERNAL);
        });

        Master.Init(jav);
        new Thread(() -> jav.start(8080)).run();

        om = new ObjectMapper();
        om.findAndRegisterModules();
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

        hoteldto[] hotels = RestAssured.get("api/hotel").then().extract().body().jsonPath().getObject("", hoteldto[].class);
        assertThat(hotels.length, equalTo(0));

    }

    @Test @DisplayName("create")
    void testCreate() throws Exception
    {

        String json = """
        {
            "name":"TEST",

                "address":"Apt. 486 31908 Beer Manor, South Misha, MN 67044-1117",

                "rooms":[
                {"number":328,"price":221.72222407247364},
                {"number":232,"price":132.4974261368166},
                {"number":218,"price":680.0698942399426} ]
        }
        """;

        hoteldto DTOFromJson = om.readValue(json, hoteldto.class);
        hotel EntFromJson = Mapping.Hotel_ToEnt(DTOFromJson);
        hoteldto[] hotels = RestAssured.get("api/hotel").then().extract().body().jsonPath().getObject("", hoteldto[].class);

        assertThat(hotels.length, equalTo(0));

        RestAssured.given().header("Content-Type", "application/json").body(json).put("/api/hotel");
        hotels = RestAssured.get("api/hotel").then().extract().body().jsonPath().getObject("", hoteldto[].class);

        assertThat(hotels.length, equalTo(1));
        assertThat(hotels[0].name, equalTo(DTOFromJson.name));


    }


}



















