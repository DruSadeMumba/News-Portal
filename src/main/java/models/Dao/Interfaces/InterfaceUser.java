package models.Dao.Interfaces;

import models.User;

import java.util.List;

public interface InterfaceUser {

        //Implement CRUD

    //Create
    void add (User user);

    //Read
    List<User> getAll();

    //Update
    void update (int id, String name, String position, String role, String departmentId);

    //Delete
    void deleteById(int id);
    void clearAll();

    User findById(int id);
}
