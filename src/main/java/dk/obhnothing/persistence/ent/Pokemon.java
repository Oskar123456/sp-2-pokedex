package dk.obhnothing.persistence.ent;

import java.util.Set;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@Entity
public class Pokemon
{

    @Id @NaturalId public Integer id;
    public String name;
    public Integer base_experience;
    public Integer height;
    public Boolean is_default;
    public Integer order;
    public Integer weight;
    public String location_area_encounters;

    public Integer hp;
    public Integer attack;
    public Integer defense;
    public Integer speed;

    public String specie_name;
    public String specie_url;

    @ManyToOne public Sprite sprite;
    @ManyToMany public Set<Type> types;
    @ManyToMany public Set<Ability> abilities;
    @ManyToMany public Set<Form> forms;
    @ManyToMany public Set<Move> moves;

}
