package dk.obhnothing.persistence.ent;

import java.util.Set;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Form
{

    @Id @NaturalId public String name;
    public String url;

    @JsonIgnore @ManyToMany(mappedBy = "forms") public Set<Pokemon> pokemons;

}
