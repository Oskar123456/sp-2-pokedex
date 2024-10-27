package dk.obhnothing.persistence.service;

import java.util.HashSet;

import dk.obhnothing.persistence.dto.PokemonDTO;
import dk.obhnothing.persistence.dto.PokemonDTO.AbilityDetails;
import dk.obhnothing.persistence.dto.PokemonDTO.Form;
import dk.obhnothing.persistence.dto.PokemonDTO.MoveDetails;
import dk.obhnothing.persistence.dto.PokemonDTO.StatDetails;
import dk.obhnothing.persistence.dto.PokemonDTO.TypeDetails;
import dk.obhnothing.persistence.ent.Ability;
import dk.obhnothing.persistence.ent.Move;
import dk.obhnothing.persistence.ent.Pokemon;
import dk.obhnothing.persistence.ent.Sprite;
import dk.obhnothing.persistence.ent.Type;

public class Mapper
{

    public static Pokemon PokemonDTO_Pokemon(PokemonDTO p_dto)
    {

        Pokemon p_mon = new Pokemon();
        p_mon.abilities = new HashSet<>();
        p_mon.forms = new HashSet<>();
        p_mon.types = new HashSet<>();
        p_mon.moves = new HashSet<>();

        p_mon.id = p_dto.id;
        p_mon.name = p_dto.name;
        p_mon.base_experience = p_dto.base_experience;
        p_mon.height = p_dto.height;
        p_mon.is_default = p_dto.is_default;
        p_mon.order_number = p_dto.order;
        p_mon.weight = p_dto.weight;
        p_mon.location_area_encounters = p_dto.location_area_encounters;
        p_mon.specie_name = p_dto.species.name;
        p_mon.specie_url = p_dto.species.url;

        for (StatDetails s : p_dto.stats) {
            if (s.stat.name.equals("hp"))
                p_mon.hp = s.base_stat;
            if (s.stat.name.equals("attack"))
                p_mon.attack = s.base_stat;
            if (s.stat.name.equals("defense"))
                p_mon.defense = s.base_stat;
            if (s.stat.name.equals("speed"))
                p_mon.speed = s.base_stat;
        }

        for (TypeDetails t : p_dto.types) {
            Type type = new Type();
            type.name = t.type.name;
            type.url = t.type.url;
            p_mon.types.add(type);
        }

        for (MoveDetails m : p_dto.moves) {
            Move move = new Move();
            move.name = m.move.name;
            move.url = m.move.url;
            p_mon.moves.add(move);
        }

        for (Form t : p_dto.forms) {
            dk.obhnothing.persistence.ent.Form form = new dk.obhnothing.persistence.ent.Form();
            form.name = t.name;
            form.url = t.url;
            p_mon.forms.add(form);
        }

        for (AbilityDetails a : p_dto.abilities) {
            Ability ability = new Ability();
            ability.name = a.ability.name;
            ability.url = a.ability.url;
            p_mon.abilities.add(ability);
        }

        p_mon.sprite = new Sprite(
                null,
                p_dto.sprites.back_default,
                p_dto.sprites.back_female,
                p_dto.sprites.back_shiny,
                p_dto.sprites.back_shiny_female,
                p_dto.sprites.front_default,
                p_dto.sprites.front_female,
                p_dto.sprites.front_shiny,
                p_dto.sprites.front_shiny_female
                );

        return p_mon;

    }

}
