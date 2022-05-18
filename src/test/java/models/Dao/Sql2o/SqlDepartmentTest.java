package models.Dao.Sql2o;

import models.Department;
import models.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class SqlDepartmentTest {
    static Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/news_portal_test", null, null);
    private static Connection conn;
    private static  SqlDepartment department = new SqlDepartment(sql2o);
    Department testDepartment = setupNewDepartment();
    Department testDepartment1 = setupNewDepartment();
    Department testDepartment2 = setupNewDepartment();

    private static  SqlUser user = new SqlUser(sql2o);
    int departmentId = testDepartment.getId();
    User testUser = setupNewUser();
    User testUser1 = setupNewUser();
    User testUser2 = setupNewUser();

    @BeforeClass
    public static void setUp() throws Exception {
        Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/news_portal_test", null, null);
        department = new SqlDepartment(sql2o);
        user = new SqlUser(sql2o);
        conn = (Connection) sql2o.open();
    }

    @AfterEach
    public void tearDown() throws Exception {
        department.clearAll();
        System.out.println("Deleting from database");
    }

    @AfterClass
    public static void shutDown() throws Exception{
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void addingDepartmentSetsId() throws Exception {
        int initialDepartmentId = testDepartment.getId();
        department.add(testDepartment);
        assertNotEquals(initialDepartmentId, testDepartment.getId());
    }

    @Test
    public void addedDepartmentsAreReturnedFromGetAll() throws Exception{
        department.add(testDepartment);
        assertEquals(1, department.getAll().size());
    }

    @Test
    public void getUsersInDeptReturnsUsersCorrectly() throws Exception {
        department.add(testDepartment);
        user.add(testUser);
        user.add(testUser1);
        user.add(testUser2);
        assertTrue(department.getUsersInDept(departmentId).contains(testUser));
        assertTrue(department.getUsersInDept(departmentId).contains(testUser1));
        assertTrue(department.getUsersInDept(departmentId).contains(testUser2));
    }

    @Test
    public void noDepartmentsReturnsEmptyList() throws Exception {
        assertEquals(0, department.getAll().size());
    }

    @Test
    public void existingDepartmentCanBeFoundById() throws Exception {
        department.add(testDepartment);
        assertEquals(testDepartment.getId(), department.findById(testDepartment.getId()).getId());
    }

    @Test
    public void updateChangesDepartmentName() throws Exception{
        department.add(testDepartment);
        department.update(testDepartment.getId(), "Human Resource", "resourcing humans", 7);
        assertNotEquals(testDepartment.getName(), department.findById(testDepartment.getId()).getName());
    }

    @Test
    void deleteByIdDeletesCorrectDepartment() throws Exception{
        department.add(testDepartment);
        department.add(testDepartment1);
        department.add(testDepartment2);
        department.deleteById(testDepartment.getId());
        department.deleteById(testDepartment1.getId());
        assertEquals(1, department.getAll().size());
    }

    @Test
    public void clearAllDeletesAllDepartments() {
        department.add(testDepartment);
        department.add(testDepartment1);
        department.add(testDepartment2);
        department.clearAll();
        assertEquals(0, department.getAll().size());
    }
    
    //helper
    public Department setupNewDepartment(){
        return new Department("Human Resources", "resourcing humans", 7);
    }
    public User setupNewUser(){
        return new User("John", "intern", "IT specialist", departmentId);
    }


}