package dk.obhnothing.handling;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

import io.javalin.http.Context;

/*
 * Web development....
 * -------------------
 * Oskar Bahner Hansen
 * ......obhnothing.dk
 * .........2024-09-26
 * -------------------
 */

public class JavalinFileserver
{

    static String favico = "/Debian-icon-1200704657.png";
    static String resourceFolder = System.getenv("PWD") + "/src/main/resources";

    public static void handle(Context ctx)
    {

        try {

            String resource = (ctx.path().equals("/")) ? "/index.html" : ctx.path();
            String uriExt = (resource.lastIndexOf(".") > 0) ? resource.substring(resource.lastIndexOf(".") + 1) : "";
            String resourceFullPath = "";
            String contentFormat = "";
            byte responseContent[] = null;
            int statusCode = 200;
            //System.out.printf("REQ URI: %s%n%n", exchange.getRequestURI().toString());

            switch (uriExt.toLowerCase()) {
                case "html":
                    resourceFullPath = resourceFolder + "/html" + resource;
                    contentFormat = "text/html; charset=utf-8";
                    break;
                case "js":
                    resourceFullPath = resourceFolder + "/js" + resource;
                    contentFormat = "text/javascript";
                    break;
                case "ico":
                    resourceFullPath = resourceFolder + "/images" + favico;
                    contentFormat = favico.substring(favico.lastIndexOf('.') + 1);
                    break;
                case "css":
                    resourceFullPath = resourceFolder + "/css" + resource;
                    contentFormat = "text/css";
                    break;
                case "png":
                    resourceFullPath = resourceFolder + "/images" + resource;
                    contentFormat = "image/png";
                    break;
                case "jpg":
                    resourceFullPath = resourceFolder + "/images" + resource;
                    contentFormat = "image/jpg";
                    break;
                case "jpeg":
                    resourceFullPath = resourceFolder + "/images" + resource;
                    contentFormat = "image/jpeg";
                    break;
                default:
                    resourceFullPath = resourceFolder + "/html" + resource + ".html";
                    contentFormat = "text/html; charset=utf-8";
                    break;
            }

            try {
                responseContent = Files.readAllBytes(Paths.get(resourceFullPath));
            }

            catch (NoSuchFileException e) {
                System.err.printf("(thread %d) couldnt find %s%n", Thread.currentThread().getId(), ctx.path());
                responseContent = Files.readAllBytes(Paths.get(resourceFolder + "/html" + "/notfound.html"));
                contentFormat = "text/html; charset=utf-8";
                statusCode = 404;
            }


            System.err.printf("(thread %d) served %s to %s%n",
                    Thread.currentThread().getId(), ctx.path(), ctx.ip());

            ctx.contentType(contentFormat);
            ctx.status(statusCode);
            ctx.result(responseContent);

        }

        catch (Exception e) {

            System.err.println(e.getMessage());
            e.printStackTrace();

        }

    }

}

