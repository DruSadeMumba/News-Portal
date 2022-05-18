package models.Dao.Interfaces;

import models.Post;
import models.User;

import java.util.List;

public interface InterfaceUser {

        //Implement CRUD

    //Create
    void add (User user);

    //Read
    List<User> getAll();
    List<Post> getUserPosts(int userId);
    List<User> getUserByDepartment(int departmentId);

    //Update
    void update (int id, String name, String position, String role, int departmentId);

    //Delete
    Object deleteById(int id);
    void clearAll();

    User findById(int id);
}
