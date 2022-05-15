package models.Dao.Sql2o;

import models.Post;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class SqlPostTest {
    static Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/news_portal_test", null, null);
    private static Connection conn;
    private static  SqlPost post = new SqlPost(sql2o);
    Post testPost = setupNewPost();
    Post testPost1 = setupNewPost();
    Post testPost2 = setupNewPost();

    @BeforeClass
    public static void setUp() throws Exception {
        Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/news_portal_test", null, null);
        post = new SqlPost(sql2o);
        conn = (Connection) sql2o.open();
    }

    @AfterEach
    public void tearDown() throws Exception {
        post.clearAll();
        System.out.println("Deleting from database");
    }

    @AfterClass
    public static void shutDown() throws Exception{
        conn.close();
        System.out.println("connection closed");
    }


    @Test
    public void addingPostSetsId() throws Exception {
        int initialPostId = testPost.getId();
        post.add(testPost);
        assertNotEquals(initialPostId, testPost.getId());
    }

    @Test
    public void addedPostsAreReturnedFromGetAll() throws Exception{
        post.add(testPost);
        assertEquals(1, post.getAll().size());
    }

    @Test
    public void noPostsReturnsEmptyList() throws Exception {
        assertEquals(0, post.getAll().size());
    }

    @Test
    public void existingPostCanBeFoundById() throws Exception {
        post.add(testPost);
        assertEquals(testPost.getId(), post.findById(testPost.getId()).getId());
    }

    @Test
    void deleteByIdDeletesCorrectPost() throws Exception{
        post.add(testPost);
        post.add(testPost1);
        post.add(testPost2);
        post.deleteById(testPost.getId());
        post.deleteById(testPost1.getId());
        assertEquals(1, post.getAll().size());
    }

    @Test
    public void clearAllDeletesAllPosts() {
        post.add(testPost);
        post.add(testPost1);
        post.add(testPost2);
        post.clearAll();
        assertEquals(0, post.getAll().size());
    }
    
    //helper
    public Post setupNewPost(){
        return new Post(1, "Jane", "Wonderful news");
    }
}