package dk.obhnothing.persistence.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.github.javafaker.Faker;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/*
 * Web development....
 * -------------------
 * Oskar Bahner Hansen
 * ......obhnothing.dk
 * .........2024-09-29
 * -------------------
 */

@EqualsAndHashCode
@NoArgsConstructor
public class roomdto
{

    public Integer id;
    public Integer number;
    public Double price;
    public Integer hotelid;

}























