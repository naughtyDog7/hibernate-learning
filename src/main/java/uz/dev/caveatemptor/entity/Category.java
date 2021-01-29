package uz.dev.caveatemptor.entity;

import uz.dev.caveatemptor.util.Constants;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
