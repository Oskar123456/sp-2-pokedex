package dk.obhnothing.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.obhnothing.persistence.ent.Ability;
import dk.obhnothing.persistence.ent.Form;
import dk.obhnothing.persistence.ent.Move;
import dk.obhnothing.persistence.ent.Pokemon;
import dk.obhnothing.persistence.ent.Type;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class PokeDAO
{

    private static EntityManagerFactory EMF;
    public static void Init(EntityManagerFactory e) {EMF = e;}
    private static Logger logger = LoggerFactory.getLogger(PokeDAO.class);

    public static Pokemon create(Pokemon p)
    {

        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();

            if (EM.find(Pokemon.class, p.id) != null) {
                EM.getTransaction().commit();
                update(p);
                EM.getTransaction().begin();
                return EM.find(Pokemon.class, p.id);
            }

            EM.persist(p.sprite);
            for (Form f : p.forms)
                if (EM.find(Form.class, f.name) == null)
                    EM.persist(f);
            for (Move f : p.moves)
                if (EM.find(Move.class, f.name) == null)
                    EM.persist(f);
            for (Ability f : p.abilities)
                if (EM.find(Ability.class, f.name) == null)
                    EM.persist(f);
            for (Type f : p.types)
                if (EM.find(Type.class, f.name) == null)
                    EM.persist(f);

            EM.persist(p);
            EM.getTransaction().commit();
            logger.info("created pokemon id: " + p.id);
        }

        catch (Exception e)
        {
            logger.warn("failed to create " + p.id + " " + e.getMessage());
        }

        return p;

    }

    public static List<Pokemon> getAll()
    {

        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            return EM.createQuery("from Pokemon", Pokemon.class).getResultList();
        }

        catch (Exception e)
        {
            logger.warn("failed to get all pokemon" + " " + e.getMessage());
        }

        return new ArrayList<Pokemon>();

    }

    public static Pokemon findById(int id)
    {

        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            return EM.find(Pokemon.class, id);
        }

        catch (Exception e)
        {
            logger.warn("failed to get pokemon " + id + " " + e.getMessage());
        }

        return null;

    }

    public static Pokemon update(Pokemon p)
    {

        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            p = EM.merge(p);
            EM.getTransaction().commit();
        }

        catch (Exception e)
        {
            logger.warn("failed to update pokemon " + p.id + " " + e.getMessage());
        }

        return p;

    }

    public static Pokemon remove(int id)
    {

        try (EntityManager EM = EMF.createEntityManager())
        {
            EM.getTransaction().begin();
            Pokemon p = EM.find(Pokemon.class, id);
            EM.remove(p);
            EM.getTransaction().commit();
            return p;
        }

        catch (Exception e)
        {
            logger.warn("failed to remove pokemon " + id + " " + e.getMessage());
        }

        return null;

    }

}
