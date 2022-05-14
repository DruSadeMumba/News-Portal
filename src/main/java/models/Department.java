package models;

import java.util.Objects;

public class Department {
    private int id;
    private String name;

    //getters
    public int getId() {return id;}
    public String getName() {return name;}

    //setters
    public void setId(int id) {this.id = id;}
    public void setName(String name) {this.name = name;}

    public Department(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
