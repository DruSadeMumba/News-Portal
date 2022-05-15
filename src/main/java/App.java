import com.google.gson.Gson;
import models.Dao.Sql2o.SqlDepartment;
import models.Dao.Sql2o.SqlUser;
import models.Department;
import models.DepartmentPost;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {

        String connectionString = "jdbc:postgresql://localhost:5432/news_portal_test";
        Sql2o sql2o = new Sql2o(connectionString, null, null);

        Gson gson = new Gson();
        SqlUser user = new SqlUser(sql2o);
        SqlDepartment department = new SqlDepartment(sql2o);

                // get all users
        get("/users", "application/json", (req, res)->{
            if (user.getAll().size() > 0) {
                return gson.toJson(user.getAll());
            }
            else {
                return "{\"message\":\"I'm sorry, but no users are currently listed in the database.\"}";
            }
        });

                //create new user
        post("/departments/:departmentId/users/new", "application/json", (req, res)->{
            int departmentId = Integer.parseInt(req.params("departmentId"));
            User newUser = gson.fromJson(req.body(), User.class);
            newUser.setDepartmentId(departmentId);
            user.add(newUser);
            res.status(201);
            return gson.toJson(newUser);
        });

                //get all departments
        get("/departments", "application/json", (req, res)->{
            if (department.getAll().size() > 0) {
                return gson.toJson(department.getAll());
            }
            else {
                return "{\"message\":\"I'm sorry, but no departments are currently listed in the database.\"}";
            }
        });

                //create new department
        post("/departments/new", "application/json", (req, res)->{
            Department newDepartment = gson.fromJson(req.body(), Department.class);
            department.add(newDepartment);
            res.status(201);
            return gson.toJson(newDepartment);
        });



    }
}
