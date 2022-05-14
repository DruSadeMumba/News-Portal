package models.Dao.Sql2o;

import models.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class SqlUserTest {
    static Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/news_portal_test", null, null);
    private static Connection conn;
    private static  SqlUser user = new SqlUser(sql2o);
    User testUser = setupNewUser();
    User testUser1 = setupNewUser();
    User testUser2 = setupNewUser();


    @BeforeClass
    public static void setUp() throws Exception {
        Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/news_portal_test", null, null);
        user = new SqlUser(sql2o);
        conn = (Connection) sql2o.open();
    }

    @AfterEach
    public void tearDown() throws Exception {
        user.clearAll();
        System.out.println("Deleting from database");
    }

    @AfterClass
    public static void shutDown() throws Exception{
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void addingUserSetsId() throws Exception {
        int initialUserId = testUser.getId();
        user.add(testUser);
        assertNotEquals(initialUserId, testUser.getId());
    }

    @Test
    public void addedUsersAreReturnedFromGetAll() throws Exception{
        user.add(testUser);
        assertEquals(1, user.getAll().size());
    }

    @Test
    public void noUsersReturnsEmptyList() throws Exception {
        assertEquals(0, user.getAll().size());
    }

    @Test
    public void existingUserCanBeFoundById() throws Exception {//failed
        user.add(testUser);
        assertEquals(1, user.findById(testUser.getId()).getId());
    }

    @Test
    public void updateChangesUserName() throws Exception{//failed
        user.add(testUser);
        user.update(testUser.getId(), "Jack", "intern", "IT specialist", "1");
        assertNotEquals(testUser.getName(), user.findById(testUser.getId()).getName());
    }

    @Test
    void deleteByIdDeletesCorrectUser() throws Exception{
        user.add(testUser);
        user.add(testUser1);
        user.add(testUser2);
        user.deleteById(testUser.getId());
        user.deleteById(testUser1.getId());
        assertEquals(1, user.getAll().size());
    }

    @Test
    public void clearAllDeletesAllUsers() {
        user.add(testUser);
        user.add(testUser1);
        user.add(testUser2);
        user.clearAll();
        assertEquals(0, user.getAll().size());
    }

    //helpers

    public User setupNewUser(){
        return new User("John", "intern", "IT specialist", 1);
    }
}