package dk.obhnothing.persistence.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.github.javafaker.Faker;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Exclude;

/*
 * Web development....
 * -------------------
 * Oskar Bahner Hansen
 * ......obhnothing.dk
 * .........2024-09-29
 * -------------------
 */

@Entity
@EqualsAndHashCode
public class hotel
{

    @Id @GeneratedValue public Integer id;
    public String name;
    public String address;
    @Exclude
    @OneToMany(mappedBy = "hotelid", cascade = CascadeType.ALL, fetch = FetchType.EAGER) public Set<room> rooms;

    public hotel()
    {
        Faker f = new Faker();
        name = f.funnyName().name();
        address = f.address().fullAddress();
        rooms = new HashSet<>();
    }

}
