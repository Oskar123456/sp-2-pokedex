package dk.obhnothing.persistence.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dk.obhnothing.persistence.entities.hotel;
import dk.obhnothing.persistence.entities.room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

/*
 * Web development....
 * -------------------
 * Oskar Bahner Hansen
 * ......obhnothing.dk
 * .........2024-09-29
 * -------------------
 */

public class hoteldao
{

    private static EntityManagerFactory EMF;
    public static void init(EntityManagerFactory e) { EMF = e; }

    public static List<hotel> search(String needle)
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            List<hotel> l = EM.createQuery("select h from hotel h where name ilike '%' || :needle || '%'", hotel.class).
                setParameter("needle", needle).getResultList();
            return l;
        }

        catch (Exception e)
        {
            return new ArrayList<>();
        }
    }

    public static hotel getById(Integer id)
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            return EM.createQuery("from hotel where id = :id", hotel.class).
                setParameter("id", id).getSingleResult();
        }

        catch (Exception e)
        {
            return null;
        }
    }


    public static List<room> allRooms(Integer hotelid)
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            return EM.createQuery("from room where hotel_id = :id", room.class).
                setParameter("id", hotelid).getResultList();
        }

        catch (Exception e)
        {
            return new ArrayList<>();
        }
    }

    public static hotel create(hotel ent)
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            for (room r : ent.rooms) {
                r.hotelid = ent;
                EM.persist(r);
            }
            EM.persist(ent);
            EM.getTransaction().commit();
        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return ent;
    }

    public static void delete(Integer key)
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            hotel h = EM.find(hotel.class, key);
            EM.remove(h);
            EM.getTransaction().commit();
        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

}



















