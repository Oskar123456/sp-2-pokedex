package dk.obhnothing.persistence.ent;

import java.util.Set;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Type
{

    @Id @NaturalId public String name;
    public String url;

    @ManyToMany(mappedBy = "types") public Set<Pokemon> pokemons;

}
