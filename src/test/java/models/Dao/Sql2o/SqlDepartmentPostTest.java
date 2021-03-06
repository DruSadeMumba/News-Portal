package models.Dao.Sql2o;

import models.Department;
import models.DepartmentPost;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class SqlDepartmentPostTest {

    static Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/news_portal_test", null, null);
    private static Connection conn;
    private static  SqlDepartmentPost departmentPost = new SqlDepartmentPost(sql2o);

    Department department = setupNewDepartment();
    Department department1 = setupNewDepartment();
    DepartmentPost testDepartmentPost = setupDepartmentPost(department);
    DepartmentPost testDepartmentPost1 = setupDepartmentPost(department);
    DepartmentPost testDepartmentPost2 = setupDepartmentPost(department1);

    @BeforeClass
    public static void setUp() throws Exception {
        Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/news_portal_test", null, null);
        departmentPost = new SqlDepartmentPost(sql2o);
        conn = (Connection) sql2o.open();
    }

    @AfterEach
    public void tearDown() throws Exception {
        departmentPost.clearAll();
        System.out.println("Deleting from database");
    }

    @AfterClass
    public static void shutDown() throws Exception{
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void addingDepartmentPostSetsId() throws Exception {
        int initialDepartmentPostId = testDepartmentPost.getId();
        departmentPost.add(testDepartmentPost);
        assertNotEquals(initialDepartmentPostId, testDepartmentPost.getId());
    }

    @Test
    public void addedDepartmentPostsAreReturnedFromGetAll() throws Exception{
        departmentPost.add(testDepartmentPost);
        departmentPost.add(testDepartmentPost);
        departmentPost.add(testDepartmentPost);
        assertEquals(3, departmentPost.getAll(department.getId()).size());
    }

    @Test
    public void noDepartmentPostsReturnsEmptyList() throws Exception {
        assertEquals(0, departmentPost.getAll(department.getId()).size());
    }
    @Test
    void deleteByIdDeletesCorrectPost() throws Exception{
        departmentPost.add(testDepartmentPost);
        departmentPost.add(testDepartmentPost1);
        departmentPost.add(testDepartmentPost2);
        departmentPost.deleteById(testDepartmentPost.getId());
        departmentPost.deleteById(testDepartmentPost1.getId());
        assertEquals(1, departmentPost.getAll(department.getId()).size());
    }

    @Test
    public void clearAllDeletesAllPosts() {
        departmentPost.add(testDepartmentPost);
        departmentPost.add(testDepartmentPost1);
        departmentPost.add(testDepartmentPost2);
        departmentPost.clearAll();
        assertEquals(0, departmentPost.getAll(department.getId()).size());
    }

    @Test
    public void existingPostCanBeFoundById() throws Exception {
        departmentPost.add(testDepartmentPost);
        assertEquals(testDepartmentPost.getId(), departmentPost.findById(testDepartmentPost.getId()).getId());
    }



    //helper
    public DepartmentPost setupDepartmentPost(Department department){
        return new DepartmentPost(1, "Jane", "Wonderful news", "general", department.getId());
    }

    public DepartmentPost setupNewDepartmentPost(){
        return new DepartmentPost(1, "Jane", "Wonderful news", "general", 1);

    }
    public Department setupNewDepartment(){
        return new Department("Human Resources", "resourcing humans", 7);
    }

}