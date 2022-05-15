package models.Dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DepartmentPostTest {

    DepartmentPost testDepartmentPost = setUpDepartmentPost();

    @BeforeEach
    public void setUp() throws Exception {
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    @Test
    void getDepartmentId() {
        assertEquals(1, testDepartmentPost.getDepartmentId());
    }

    //Helper
    public DepartmentPost setUpDepartmentPost(){
        return new DepartmentPost(1, "Jane", "Wonderful news", "departmentnews" , 1);
    }

}