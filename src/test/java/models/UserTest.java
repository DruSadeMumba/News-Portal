package models;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {
    User testUser = setUpUser();

    @Before
    public void setUp() throws Exception {

    }
    @After
    public void tearDown() throws Exception {

    }
    @Test
    public void getName(){
        assertEquals("Jane", testUser.getName());
    }

    @Test
    public void getPosition(){
        assertEquals("Manager", testUser.getPosition());
    }

    @Test
    public void getRole(){
        assertEquals("HR", testUser.getRole());
    }
    
    @Test
    public void getDepartmentId(){
        assertEquals(1, testUser.getDepartmentId());
    }


    //helper
    public User setUpUser(){
        return new User("Jane", "Manager", "HR", 1);
    }
}