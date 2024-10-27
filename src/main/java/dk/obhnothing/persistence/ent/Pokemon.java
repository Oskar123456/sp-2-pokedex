package dk.obhnothing.persistence.ent;

import java.util.Set;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.NaturalId;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode.Exclude;

@ToString
@Entity
@NoArgsConstructor
@EqualsAndHashCode
public class Pokemon
{

    @Id @NaturalId public Integer id;
    public String name;
    public Integer base_experience;
    public Integer height;
    public Boolean is_default;
    public Integer order_number;
    public Integer weight;
    public String location_area_encounters;

    public Integer hp;
    public Integer attack;
    public Integer defense;
    public Integer speed;

    public String specie_name;
    public String specie_url;

    @ManyToOne(fetch = FetchType.EAGER,  cascade = CascadeType.MERGE)  @Exclude public Sprite sprite;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE) @Exclude public Set<Type> types;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE) @Exclude public Set<Ability> abilities;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE) @Exclude public Set<Form> forms;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE) @Exclude public Set<Move> moves;

}
