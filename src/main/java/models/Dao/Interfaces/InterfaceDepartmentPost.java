package models.Dao.Interfaces;

import models.DepartmentPost;

import java.util.List;

public interface InterfaceDepartmentPost {
    //Implement CRUD

    //Create
    void add (DepartmentPost DepartmentPost);

    //Read
    List<DepartmentPost> getAll(int departmentId);

    //Update
    /*void update (int id, int userId, String createdBy, String Content, long createdat, int departmentId);*/

    //Delete
    void deleteById(int id);
    void clearAll();

    DepartmentPost findById(int id);

}
