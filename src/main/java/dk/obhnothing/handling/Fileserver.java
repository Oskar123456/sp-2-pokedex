package dk.obhnothing.handling;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map.Entry;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/*
 * Web development....
 * -------------------
 * Oskar Bahner Hansen
 * ......obhnothing.dk
 * .........2024-09-02
 * -------------------
 */

public class Fileserver implements HttpHandler
{

    static String favico = "/Debian-icon-1200704657.png";
    static String resourceFolder = System.getenv("PWD") + "/src/main/resources";

    @Override
    public void handle(HttpExchange exchange)
    {


        try {

            System.out.println(resourceFolder);
            System.out.println(resourceFolder);
            System.out.println(resourceFolder);
            URI uri = exchange.getRequestURI();
            String uriStr = (uri.getPath().equals("/")) ? "/index.html" : uri.getPath();
            String uriExt = (uriStr.lastIndexOf(".") > 0) ? uriStr.substring(uriStr.lastIndexOf(".") + 1) : "";
            String contentFormat = "";
            byte responseContent[] = null;
            int statusCode = 200;
            Headers reqHeaders = exchange.getRequestHeaders();
            System.out.printf("REQ URI: %s%n%n", exchange.getRequestURI().toString());
            for (Entry<String, List<String>> e : reqHeaders.entrySet()) {
                System.out.println(e.getKey());
            }

            try {
                switch (uriExt.toLowerCase()) {
                    case "html":
                        responseContent = Files.readAllBytes(Paths.get(resourceFolder + "/html" + uriStr));
                        contentFormat = "text/html; charset=utf-8";
                        break;
                    case "ico":
                        responseContent = Files.readAllBytes(Paths.get(resourceFolder + "/images" + favico));
                        contentFormat = favico.substring(favico.lastIndexOf('.') + 1);
                        break;
                    case "css":
                        responseContent = Files.readAllBytes(Paths.get(resourceFolder + "/css" + uriStr));
                        contentFormat = "text/css";
                        break;
                    case "png":
                        responseContent = Files.readAllBytes(Paths.get(resourceFolder + "/images" + uriStr));
                        contentFormat = "image/png";
                        break;
                    case "jpg":
                        responseContent = Files.readAllBytes(Paths.get(resourceFolder + "/images" + uriStr));
                        contentFormat = "image/jpg";
                        break;
                    case "jpeg":
                        responseContent = Files.readAllBytes(Paths.get(resourceFolder + "/images" + uriStr));
                        contentFormat = "image/jpeg";
                        break;
                    default:
                        responseContent = Files.readAllBytes(Paths.get(resourceFolder + "/html" + uriStr));
                        contentFormat = "text/html; charset=utf-8";
                        break;
                }
            }

            catch (NoSuchFileException e) {
                System.err.printf("(thread %d) couldnt find %s%n", Thread.currentThread().getId(), uri.getPath());
                responseContent = Files.readAllBytes(Paths.get(resourceFolder + "/html" + "/notfound.html"));
                contentFormat = "text/html; charset=utf-8";
                statusCode = 404;
            }

            Headers rHeaders = exchange.getResponseHeaders();
            rHeaders.add("Content-Type", contentFormat);
            System.err.printf("(thread %d) served %s to %s%n",
                    Thread.currentThread().getId(), uri.getPath(), exchange.getRemoteAddress().toString());

            exchange.sendResponseHeaders(statusCode, responseContent.length);
            exchange.getResponseBody().write(responseContent);

            exchange.close();

        }

        catch (Exception e) {

            System.err.println(e.getMessage());
            e.printStackTrace();

            exchange.close();

        }

    }

}

















