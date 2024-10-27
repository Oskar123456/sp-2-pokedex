package dk.obhnothing.routes;

import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Context;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.obhnothing.security.controllers.AccessController;
import dk.obhnothing.security.controllers.SecurityController;
import dk.obhnothing.security.exceptions.ApiException;
import dk.obhnothing.security.routes.SecurityRoutes;
import dk.obhnothing.utilities.Utils;

public class PokemonRoutes {

    private static SecurityController securityController = SecurityController.getInstance();
    private static Logger logger = LoggerFactory.getLogger(PokemonRoutes.class);

    public static EndpointGroup getRoutes() {
        return () -> {
        };
    }

    public static Javalin setup()
    {

        Javalin jav = Javalin.create(config -> {
            config.showJavalinBanner = false;
            config.bundledPlugins.enableRouteOverview("/routes");
            config.router.contextPath = "/api"; // base path for all endpoints
            config.router.apiBuilder(SecurityRoutes.getSecuredRoutes());
            config.router.apiBuilder(SecurityRoutes.getSecurityRoutes());
        });

        AccessController accessController = new AccessController();
        jav.beforeMatched(accessController::accessHandler);
        jav.exception(Exception.class, PokemonRoutes::generalExceptionHandler);
        jav.exception(ApiException.class, PokemonRoutes::apiExceptionHandler);
        jav.get("pokemon", PokemonRoutes::searchPokedex);

        return jav;
    }

    public static void searchPokedex(Context ctx)
    {
        ctx.json(new String("pokedex"));
    }

    private static void generalExceptionHandler(Exception e, Context ctx) {
        logger.error("An unhandled exception occurred", e.getMessage());
        ctx.json(Utils.convertToJsonMessage(ctx, "error", e.getMessage()));
    }

    public static void apiExceptionHandler(ApiException e, Context ctx) {
        ctx.status(e.getCode());
        logger.warn("An API exception occurred: Code: {}, Message: {}", e.getCode(), e.getMessage());
        ctx.json(Utils.convertToJsonMessage(ctx, "warning", e.getMessage()));
    }

}
