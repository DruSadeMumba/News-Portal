package models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DepartmentTest {

    Department testDepartment = setUpDepartment();

    @BeforeEach
    public void setUp() throws Exception {
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    @Test
    public void getName(){
        assertEquals("Human Resources", testDepartment.getName());
    }

    //helper
    public Department setUpDepartment(){
        return new Department("Human Resources");
    }
}