package models;

import java.util.Objects;

public class User {
    private int id;
    private String name;
    private String position;
    private String role;
    private int departmentId;

    //getters
    public int getId() {return id;}
    public String getName() {return name;}
    public String getPosition() {return position;}
    public String getRole() {return role;}
    public int getDepartmentId() {return departmentId;}

    //setters
    public void setId(int id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setPosition(String position) {this.position = position;}
    public void setRole(String role) {this.role = role;}
    public void setDepartmentId(int departmentId) {this.departmentId = departmentId;}

    public User(String name, String position, String role, int departmentId) {
        this.name = name;
        this.position = position;
        this.role = role;
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && departmentId == user.departmentId && name.equals(user.name) && position.equals(user.position) && role.equals(user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, position, role, departmentId);
    }
}
