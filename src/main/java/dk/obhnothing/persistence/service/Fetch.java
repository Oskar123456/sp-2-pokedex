package dk.obhnothing.persistence.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;

import dk.obhnothing.persistence.dao.PokeDAO;
import dk.obhnothing.persistence.dto.PokemonDTO;
import dk.obhnothing.persistence.ent.Pokemon;
import dk.obhnothing.utilities.Utils;

public class Fetch
{

    private static ObjectMapper jsonMapper = Utils.getObjectMapper();

    public static Pokemon pokemonById(int id)
    {
        try
        {
            if (PokeDAO.findById(id) != null) {
                System.out.println("fetching cached pokemon (" + id + ")");
                return PokeDAO.findById(id);
            }
            else
                System.out.println("fetching new pokemon (" + id + ")");

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create("https://pokeapi.co/api/v2/pokemon/" + id))
                .build();
            HttpResponse<String> res = client.send(req, BodyHandlers.ofString());

            String res_str = res.body();

            PokemonDTO pokDTO = jsonMapper.readValue(res_str, PokemonDTO.class);
            Pokemon pokEnt = Mapper.PokemonDTO_Pokemon(pokDTO);

            return PokeDAO.create(pokEnt);
        }

        catch (Exception e)
        {
            return null;
        }
    }
}



























