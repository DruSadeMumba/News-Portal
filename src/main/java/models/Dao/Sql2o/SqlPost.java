package models.Dao.Sql2o;

import models.Dao.Interfaces.InterfacePost;
import models.Post;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

import static java.lang.System.*;

public class SqlPost implements InterfacePost {

    private final Sql2o sql2o;
    public SqlPost(Sql2o sql2o) { this.sql2o = sql2o; }

    @Override
    public void add(Post post) {
        String sql = "INSERT INTO posts (userid, createdby, content, createdat, type) VALUES (:userId, :createdBy, :content, :createdat, :type)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(post)
                    .executeUpdate()
                    .getKey();
            post.setId(id);
        } catch (Sql2oException ex) {
            out.println(ex);
        }
    }

    @Override
    public List<Post> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM posts WHERE type = 'general';")
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Post.class);
        }
    }


    @Override
    public Object deleteById(int id) {
        try (Connection con = sql2o.open()) {
            con.createQuery("DELETE FROM posts WHERE id=:id")
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeUpdate();
        } catch (Sql2oException ex){
            out.println(ex);
        }
        return "Post deleted";
    }

    @Override
    public void clearAll() {
        try (Connection con = sql2o.open()) {
            con.createQuery("DELETE FROM posts").executeUpdate();
        } catch (Sql2oException ex) {
            out.println(ex);
        }
    }

    @Override
    public Post findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM posts WHERE id = :id")
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(Post.class);
        }
    }

}
