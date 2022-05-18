package models;

import java.util.Objects;

public class Department {
    private int id;
    private String name;
    private String description;
    private int number;

    //getters
    public int getId() {return id;}
    public String getName() {return name;}
    public String getDescription() {return description;}
    public int getNumber() {return number;}

    //setters
    public void setId(int id) {this.id = id;}
    public void setName(String name) {this.name = name;}

    public void setDescription(String description) {this.description = description;}
    public void setNumber(int number) {this.number = number;}

    public Department(String name, String description, int number) {
        this.name = name;
        this.description = description;
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department department = (Department) o;
        return id == department.id && number == department.number &&
                Objects.equals(name, department.name) &&
                Objects.equals(description, department.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, number);
    }
}
