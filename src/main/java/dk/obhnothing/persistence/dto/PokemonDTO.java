package dk.obhnothing.persistence.dto;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class PokemonDTO
{

    public Integer id;
    public String name;
    public Integer base_experience;
    public Integer height;
    public Boolean is_default;
    public Integer order;
    public Integer weight;
    public String location_area_encounters;
    public Sprite sprites;

    public TypeDetails[] types;
    public Specie species;
    public StatDetails[] stats;
    public AbilityDetails[] abilities;
    public Form[] forms;
    public MoveDetails[] moves;

    public static class StatDetails {
        public Integer base_stat;
        public Integer effort;
        public Stat stat;
    }

    public static class TypeDetails {
        public Integer slot;
        public Type type;
    }

    public static class MoveDetails {
        public Move move;
    }

    public static class AbilityDetails {
        public Boolean is_hidden;
        public Integer slot;
        public Ability ability;
    }

    public static class Sprite {
        public String back_default;
        public String back_female;
        public String back_shiny;
        public String back_shiny_female;
        public String front_default;
        public String front_female;
        public String front_shiny;
        public String front_shiny_female;
    }

    public static class Type {
        public String name;
        public String url;
    }

    public static class Ability {
        public String name;
        public String url;
    }

    public static class Stat {
        public String name;
        public String url;
    }

    public static class Specie {
        public String name;
        public String url;
    }

    public static class Form {
        public String name;
        public String url;
    }

    public static class Move {
        public String name;
        public String url;
    }

}
