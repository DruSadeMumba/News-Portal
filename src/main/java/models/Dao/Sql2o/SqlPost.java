package models.Dao.Sql2o;

import models.Dao.Interfaces.InterfacePost;
import models.Post;
import models.User;
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
        String sql = "INSERT INTO posts (userid, createdby, content, createdat) VALUES (:userId, :createdBy, :content, :createdat)";
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
            return con.createQuery("SELECT * FROM posts")
                    .executeAndFetch(Post.class);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM posts WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE FROM posts";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            out.println(ex);
        }
    }

    @Override
    public Post findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM posts WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Post.class);
        }
    }

}
