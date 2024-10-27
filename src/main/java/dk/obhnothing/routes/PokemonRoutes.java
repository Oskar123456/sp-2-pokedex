package dk.obhnothing.routes;

import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Context;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.obhnothing.persistence.dao.PokeDAO;
import dk.obhnothing.persistence.ent.Pokemon;
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

        jav.get("pokemon/all", PokemonRoutes::pokedexLookUp_All);
        jav.get("pokemon", PokemonRoutes::pokedexLookUp_Id);
        jav.put("pokemon", PokemonRoutes::pokedex_Add);
        jav.post("pokemon", PokemonRoutes::pokedex_Add);
        jav.update("pokemon", PokemonRoutes::pokedex_Update);
        jav.delete("pokemon", PokemonRoutes::pokedex_Delete);

        return jav;
    }

    public static void pokedex_Update(Context ctx)
    {
        try
        {
            Pokemon pokemon = ctx.bodyAsClass(Pokemon.class);
            pokemon = PokeDAO.update(pokemon);
            ctx.json(pokemon);
            ctx.status(200);
        }

        catch (Exception e)
        {
            throw new ApiException(404, "pokemon not updated");
        }
    }

    public static void pokedex_Add(Context ctx)
    {
        try
        {
            Pokemon pokemon = ctx.bodyAsClass(Pokemon.class);
            pokemon = PokeDAO.create(pokemon);
            ctx.json(pokemon);
            ctx.status(200);
        }

        catch (Exception e)
        {
            throw new ApiException(404, "pokemon not found");
        }
    }

    public static void pokedex_Delete(Context ctx)
    {
        try
        {
            Pokemon pokemon = PokeDAO.remove(Integer.parseInt(ctx.queryParam("id")));
            if (pokemon == null)
                throw new ApiException(404, "pokemon not found");
            ctx.json(pokemon);
            ctx.status(200);
        }

        catch (Exception e)
        {
            throw new ApiException(404, "pokemon not found");
        }
    }


    public static void pokedexLookUp_All(Context ctx)
    {
        try
        {
            List<Pokemon> pokemons = PokeDAO.getAll();
            ctx.json(pokemons);
            ctx.status(200);
        }

        catch (Exception e)
        {
            throw new ApiException(404, "pokemon not found");
        }
    }

    public static void pokedexLookUp_Id(Context ctx)
    {
        try
        {
            int id = Integer.parseInt(ctx.queryParam("id"));
            Pokemon pokemon = PokeDAO.findById(id);
            ctx.json(pokemon);
            ctx.status(200);
        }

        catch (Exception e)
        {
            throw new ApiException(404, "pokemon not found");
        }
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
