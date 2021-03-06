package models.Dao.Interfaces;

import models.Post;

import java.util.List;

public interface InterfacePost {

        //Implement CRUD

    //Create
    void add (Post post);

    //Read
    List<Post> getAll();

    //Update
    /*void update (int id, int userId, String createdBy, String Content, long createdat);*/

    //Delete
    Object deleteById(int id);
    void clearAll();

    Post findById(int id);
}
