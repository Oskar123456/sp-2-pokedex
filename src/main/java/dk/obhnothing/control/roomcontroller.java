package dk.obhnothing.control;

import dk.obhnothing.persistence.dao.roomdao;
import dk.obhnothing.persistence.dto.*;
import dk.obhnothing.persistence.entities.*;
import dk.obhnothing.persistence.service.*;
import io.javalin.http.Context;
import java.util.List;

/*
 * Web development....
 * -------------------
 * Oskar Bahner Hansen
 * ......obhnothing.dk
 * .........2024-09-29
 * -------------------
 */

public class roomcontroller
{

    public static void getRoom(Context ctx)
    {
        String idStr = ctx.queryParam("id");
        try {
            if (idStr == null) {
                List<room> rooms = roomdao.getAll();
                List<roomdto> roomdtos = rooms.stream().map(Mapping::Room_ToDTO).toList();
                ctx.json(roomdtos);
            }
            else {
                ctx.status(404);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            ctx.status(404);
        }
    }

    public static void deleteRoom(Context ctx)
    {
        String idStr = ctx.queryParam("id");
        try
        {
            Integer id = Integer.parseInt(idStr);
            room r = roomdao.delete(id);
            ctx.json(Mapping.Room_ToDTO(r));
        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());
            ctx.status(404);
        }
    }

}















