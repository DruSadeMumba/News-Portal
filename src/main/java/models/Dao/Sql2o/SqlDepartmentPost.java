package models.Dao.Sql2o;

import models.Dao.Interfaces.InterfaceDepartmentPost;
import models.DepartmentPost;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

import static java.lang.System.out;


public class SqlDepartmentPost implements InterfaceDepartmentPost {

    private final Sql2o sql2o;
    public SqlDepartmentPost(Sql2o sql2o) { this.sql2o = sql2o; }

    @Override
    public void add(DepartmentPost departmentPost) {
        String sql = "INSERT INTO posts (userid, createdby, content, createdat, type, departmentid) VALUES (:userId, :createdBy, :content, :createdat, :type, :departmentId)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(departmentPost)
                    .executeUpdate()
                    .getKey();
            departmentPost.setId(id);
        } catch (Sql2oException ex) {
            out.println(ex);
        }
    }

    @Override
    public List<DepartmentPost> getAll(int departmentId) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM posts WHERE departmentid = :departmentId;")
                    .addParameter("departmentId", departmentId)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(DepartmentPost.class);
        }
    }

    @Override
    public void deleteById(int id) {
        try (Connection con = sql2o.open()) {
            con.createQuery("DELETE FROM posts WHERE id=:id")
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        try (Connection con = sql2o.open()) {
            con.createQuery("DELETE FROM posts WHERE type = 'departmentnews'").executeUpdate();
        } catch (Sql2oException ex) {
            out.println(ex);
        }
    }

    @Override
    public DepartmentPost findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM posts WHERE id = :id")
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(DepartmentPost.class);
        }
    }

}
