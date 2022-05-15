package models.Dao.Sql2o;

import models.Dao.Interfaces.InterfaceUser;
import models.Post;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class SqlUser implements InterfaceUser {
    private final Sql2o sql2o;

    public SqlUser(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(User user) {
        String sql = "INSERT INTO users (name, position, role, departmentid) VALUES (:name, :position, :role, :departmentId)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(user)
                    .executeUpdate()
                    .getKey();
            user.setId(id);
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }


    @Override
    public List<User> getAll() {
        try(Connection con = sql2o.open()){
           return con.createQuery("SELECT * FROM users")
                   .executeAndFetch(User.class);
        }
    }

    @Override
    public List<Post> getUserPosts(int userId) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM posts WHERE userid = :userId")
                    .addParameter("userId", userId)
                    .executeAndFetch(Post.class);
        }
    }

    @Override
    public List<User> getUserByDepartment(int departmentId) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM users WHERE departmentid = :departmentId")
                    .addParameter("departmentId", departmentId)
                    .executeAndFetch(User.class);
        }
    }

    @Override
    public void update(int id, String name, String position, String role, int departmentId) {
        String sql = "UPDATE users SET (name, position, role, departmentid) = (SELECT :name, :position, :role, :departmentId FROM users) WHERE id = :id";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("name", name)
                    .addParameter("position", position)
                    .addParameter("role", role)
                    .addParameter("departmentId", departmentId)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM users WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE FROM users";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public User findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM users WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(User.class);
        }
    }
}
