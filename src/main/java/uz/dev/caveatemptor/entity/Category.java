package uz.dev.caveatemptor.entity;

import uz.dev.caveatemptor.util.Constants;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Category {
    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    private long id;

    private String name;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }
}
