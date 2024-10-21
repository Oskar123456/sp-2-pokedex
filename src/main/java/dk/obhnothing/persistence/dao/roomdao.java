package dk.obhnothing.persistence.dao;

import java.util.ArrayList;
import java.util.List;

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

public class roomdao
{

    private static EntityManagerFactory EMF;
    public static void init(EntityManagerFactory e) { EMF = e; }

    public static List<room> getAll()
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            List<room> l = EM.createQuery("from room", room.class).getResultList();
            return l;
        }

        catch (Exception e)
        {
            return new ArrayList<>();
        }
    }

    public static room create(room ent)
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            EM.persist(ent);
            EM.getTransaction().commit();
        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return ent;
    }

    public static room delete(Integer key)
    {
        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            room r = EM.find(room.class, key);
            EM.remove(r);
            EM.getTransaction().commit();
            return r;
        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

}

