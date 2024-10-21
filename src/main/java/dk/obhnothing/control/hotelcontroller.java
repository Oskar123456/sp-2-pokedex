package dk.obhnothing.control;

import java.util.List;

import dk.obhnothing.persistence.dao.hoteldao;
import dk.obhnothing.persistence.dto.hoteldto;
import dk.obhnothing.persistence.entities.hotel;
import dk.obhnothing.persistence.service.Mapping;
import io.javalin.http.Context;

/*
 * Web development....
 * -------------------
 * Oskar Bahner Hansen
 * ......obhnothing.dk
 * .........2024-09-29
 * -------------------
 */

public class hotelcontroller
{

    public static void searchHotel(Context ctx)
    {
        String needle = ctx.queryParam("needle");
        if (needle == null)
            needle = "";
        try {
            List<hotel> hotels = hoteldao.search(needle);
            List<hoteldto> hoteldtos = hotels.stream().map(Mapping::Hotel_ToDTO).toList();
            ctx.json(hoteldtos);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            ctx.status(404);
        }
    }

    public static void getHotel(Context ctx)
    {
        String idStr = ctx.queryParam("id");
        try {
            if (idStr == null) {
                List<hotel> hotels = hoteldao.search("");
                List<hoteldto> hoteldtos = hotels.stream().map(Mapping::Hotel_ToDTO).toList();
                ctx.json(hoteldtos);
            }
            else {
                hotel r = hoteldao.getById(Integer.parseInt(idStr));
                if (r != null)
                    ctx.json(Mapping.Hotel_ToDTO(r));
                else
                    ctx.status(404);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            ctx.status(404);
        }
    }

    public static void deleteHotel(Context ctx)
    {
        String idStr = ctx.queryParam("id");
        try {
            Integer id = Integer.parseInt(idStr);
            hotel r = hoteldao.getById(id);
            if (r != null) {
                ctx.json(Mapping.Hotel_ToDTO(r));
                hoteldao.delete(id);
            }
            else
                ctx.status(404);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            ctx.status(404);
        }
    }

    public static void createHotel(Context ctx)
    {

        try
        {
            hoteldto h = ctx.bodyAsClass(hoteldto.class);
            h.id = null;
            h.rooms.forEach(r -> r.id = null);
            hotel hent = hoteldao.create(Mapping.Hotel_ToEnt(h));
            ctx.json(Mapping.Hotel_ToDTO(hent));
        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());
            ctx.status(404);
        }

    }

}









