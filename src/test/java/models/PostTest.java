package models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PostTest {

    Post testPost = setUpPost();

    @BeforeEach
    public void setUp() throws Exception {
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    @Test
    void getUserId() {
        assertEquals(1, testPost.getUserId());
    }

    @Test
    void getCreatedBy() {
        assertEquals("Jane", testPost.getCreatedBy());
    }

    @Test
    void getContent() {
        assertEquals("Wonderful news", testPost.getContent());
    }

    public Post setUpPost(){
        return new Post(1, "Jane", "Wonderful news");
    }

}