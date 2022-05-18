package models.Dao.Sql2o;

import models.Dao.Interfaces.InterfaceDepartment;
import models.Department;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class SqlDepartment implements InterfaceDepartment {

    private final Sql2o sql2o;

    public SqlDepartment(Sql2o sql2o) {
        this.sql2o = sql2o;
    }
    @Override
    public void add(Department department) {
        String sql = "INSERT INTO departments (name, description, number) VALUES (:name, :description, :number)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(department)
                    .executeUpdate()
                    .getKey();
            department.setId(id);
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Department> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM departments")
                    .executeAndFetch(Department.class);
        }
    }

    @Override
    public List<User> getUsersInDept(int departmentId) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM users WHERE departmentid = :departmentId")
                    .addParameter("departmentId", departmentId)
                    .executeAndFetch(User.class);
        }
    }

    @Override
    public void update(int id, String name, String description, int number) {
        String sql = "UPDATE departments SET (name, description, number) = (SELECT :name, :description, :number FROM departments) WHERE id = :id";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("name", name)
                    .addParameter("description", description)
                    .addParameter("number", number)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public Object deleteById(int id) {
        String sql = "DELETE FROM departments WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }

        return "Department Deleted";
    }

    @Override
    public void clearAll() {
        String sql = "DELETE FROM departments";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public Department findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM departments WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Department.class);
        }
    }
}
