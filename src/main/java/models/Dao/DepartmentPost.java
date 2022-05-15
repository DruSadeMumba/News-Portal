package models.Dao;

import models.Post;

public class DepartmentPost extends Post {
    public static final String DATABASE_TYPE = "departmentnews";
    private int departmentId;

    //getters
    public int getDepartmentId() {return departmentId;}

    //setters
    public void setDepartmentId(int departmentId) {this.departmentId = departmentId;}

    public DepartmentPost(int userId, String createdBy, String content, String type, int departmentId) {
        super(userId, createdBy, content, type);
        this.departmentId = departmentId;
        this.type = DATABASE_TYPE;
    }

}
