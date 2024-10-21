package dk.obhnothing.persistence.entities;

import java.util.Random;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.ToString;
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
@ToString
public class room
{

    @Id @GeneratedValue public Integer id;
    public Integer number;
    public Double price;
    @Exclude
    @ManyToOne @JoinColumn(name = "hotelid") public hotel hotelid;

    public room()
    {
        Random r = new Random();
        number = r.nextInt(100, 400);
        price = r.nextDouble(100, 1000);
    }

}















