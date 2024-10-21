/*
 * Web development....
 * -------------------
 * Oskar Bahner Hansen
 * ......obhnothing.dk
 * .........2024-09-02
 * -------------------
 */

package dk.obhnothing.control;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.net.httpserver.HttpServer;

import dk.obhnothing.handling.Fileserver;
import dk.obhnothing.utilities.PrettyPrinter;
import dk.obhnothing.utilities.PrettyPrinter.ANSIColorCode;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * master
 */
public class Master
{

    static Logger logger = LoggerFactory.getLogger(Master.class);
    static String resourceFolder = System.getenv("PWD") + "/src/main/resources";

    public static void Init(HttpServer server)
    {
        server.createContext("/", new Fileserver());
    }

    public static void Init(Javalin j)
    {
        j.before("/*", Master::logreq);
        j.after("/*", Master::logres);
        j.get("/", c -> index(c));
        j.get("/index", c -> index(c));
        j.get("/listall", c -> listall(c));
        j.error(404, c -> notfound(c));
        j.exception(IllegalStateException.class, (e, c) -> exc(e, c));
    }

    private static void logreq(Context c)
    {
        System.err.print(PrettyPrinter.ANSIColorMap.get(ANSIColorCode.ANSI_YELLOW));
        logger.info("Http request ({}): {} : {} {}{}",
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME).substring(0, 8),
                c.ip(), c.req().getMethod(), c.req().getRequestURI(),
                (c.req().getQueryString() != null) ? "?" + c.req().getQueryString() : "");
        System.err.print(" " + PrettyPrinter.ANSIColorMap.get(ANSIColorCode.ANSI_RESET));
    }

    private static void logres(Context c)
    {
        if (c.statusCode() >= 400)
            System.err.print(PrettyPrinter.ANSIColorMap.get(ANSIColorCode.ANSI_RED));
        else
            System.err.print(PrettyPrinter.ANSIColorMap.get(ANSIColorCode.ANSI_GREEN));

        logger.info("Http res ({}): {} : statuscode: {}, contenttype: {}",
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME).substring(0, 8),
                c.ip(), c.statusCode(), c.res().getContentType());

        System.err.print(" " + PrettyPrinter.ANSIColorMap.get(ANSIColorCode.ANSI_RESET));
    }

    private static void listall(@NotNull Context c) {
        c.redirect("/listall.html");
    }

    private static void index(@NotNull Context c) {
        c.redirect("/index.html");
    }

    private static void exc(Exception e, @NotNull Context c) {
        c.status(400);
        c.result("bad request");
    }

    private static void notfound(Context c) {
        byte[] responseContent = null;
        String contentFormat = "";
        int statusCode = 404;
        try
        {
            responseContent = Files.readAllBytes(Paths.get(resourceFolder + "/html" + "/notfound.html"));
            contentFormat = "text/html; charset=utf-8";
        }

        catch (Exception e) { }

        c.contentType(contentFormat);
        c.status(statusCode);
        c.result(responseContent);
    }

    private static String inject(String html, String inj)
    {
        String injPoint = "<!-- INJECT -->";
        int idx = html.indexOf(injPoint) + injPoint.length();
        return html.substring(0, idx) + inj + html.substring(idx);
    }

}





























