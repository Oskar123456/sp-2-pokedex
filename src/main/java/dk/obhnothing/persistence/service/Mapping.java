package dk.obhnothing.persistence.service;

import java.util.HashSet;

import dk.obhnothing.persistence.dao.hoteldao;
import dk.obhnothing.persistence.dao.roomdao;
import dk.obhnothing.persistence.dto.hoteldto;
import dk.obhnothing.persistence.dto.roomdto;
import dk.obhnothing.persistence.entities.hotel;
import dk.obhnothing.persistence.entities.room;

/*
 * Web development....
 * -------------------
 * Oskar Bahner Hansen
 * ......obhnothing.dk
 * .........2024-09-29
 * -------------------
 */

public class Mapping
{

    public static hotel Hotel_ToEnt(hoteldto dto)
    {
        hotel h = new hotel();
        h.id = dto.id;
        h.name = dto.name;
        h.address = dto.address;
        h.rooms = new HashSet<>();
        for (roomdto rdto : dto.rooms)
            h.rooms.add(Room_ToEnt(rdto));
        return h;
    }

    public static hoteldto Hotel_ToDTO(hotel ent)
    {
        hoteldto dto = new hoteldto();
        dto.id = ent.id;
        dto.name = ent.name;
        dto.address = ent.address;
        dto.rooms = new HashSet<>();
        for (room r : ent.rooms)
            dto.rooms.add(Room_ToDTO(r));
        //System.out.printf("%s has %d rooms %n", dto.name, ent.rooms.size());
        return dto;
    }


    public static room Room_ToEnt(roomdto dto)
    {
        hoteldao hdao = new hoteldao();
        room r = new room();
        r.id = dto.id;
        r.number = dto.number;
        r.price = dto.price;
        r.hotelid = hdao.getById(dto.id);
        return r;
    }

    public static roomdto Room_ToDTO(room ent)
    {
        roomdto r = new roomdto();
        r.id = ent.id;
        r.number = ent.number;
        r.price = ent.price;
        r.hotelid = (ent.hotelid != null) ? ent.hotelid.id : -1;
        return r;
    }

}




































