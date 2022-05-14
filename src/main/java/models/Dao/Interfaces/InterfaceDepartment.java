package models.Dao.Interfaces;

import models.Department;


import java.util.List;

public interface InterfaceDepartment {

            //Implement CRUD
    
    //Create
    void add (Department department);

    //Read
    List<Department> getAll();

    //Update
    void update (int id, String name);

    //Delete
    void deleteById(int id);
    void clearAll();

    Department findById(int id);
}
