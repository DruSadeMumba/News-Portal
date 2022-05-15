import com.google.gson.Gson;
import exceptions.ApiException;
import models.Dao.Sql2o.SqlDepartment;
import models.Dao.Sql2o.SqlDepartmentPost;
import models.Dao.Sql2o.SqlPost;
import models.Dao.Sql2o.SqlUser;
import models.Department;
import models.DepartmentPost;
import models.Post;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {

        String connectionString = "jdbc:postgresql://localhost:5432/news_portal_test";
        Sql2o sql2o = new Sql2o(connectionString, null, null);

        Gson gson = new Gson();
        SqlUser user = new SqlUser(sql2o);
        SqlDepartment department = new SqlDepartment(sql2o);
        SqlDepartmentPost departmentPost = new SqlDepartmentPost(sql2o);
        SqlPost post = new SqlPost(sql2o);

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
            return gson.toJson(newUser.getName());
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

            //view users in departments
        get("/departments/:id/users", "application/json", (req, res)->{//not working
            int departmentId = Integer.parseInt(req.params(":id"));
            Department department2 = department.findById(departmentId);
            List<User> allUsers;
            if (department2 == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }
            allUsers = user.getUserByDepartment(departmentId);
            return gson.toJson(allUsers);
        });

                //create new post
        post("/posts/new", "application/json", (req, res)->{
            Post newPost = gson.fromJson(req.body(), Post.class);
            newPost.setCreatedat();
            newPost.setFormattedCreatedAt();
            post.add(newPost);
            res.status(201);
            return gson.toJson(newPost);
        });

                //get post
        get("/posts", "application/json", (req, res)->{
            if (post.getAll().size() > 0) {
                return gson.toJson(post.getAll());
            }
            else {
                return "{\"message\":\"I'm sorry, but no posts are currently listed in the database.\"}";
            }
        });

                //create new department post
        post("/departments/:departmentId/posts/new", "application/json", (req, res)->{ //not working
            int departmentId = Integer.parseInt(req.params("departmentId"));
            DepartmentPost newDepartmentPost = gson.fromJson(req.body(), DepartmentPost.class);
            newDepartmentPost.setCreatedat();
            newDepartmentPost.setFormattedCreatedAt();
            newDepartmentPost.setDepartmentId(departmentId);
            departmentPost.add(newDepartmentPost);
            res.status(201);
            return gson.toJson(newDepartmentPost);
        });

                //get department posts
        get("/departments/:id/posts", "application/json", (req, res)->{//not working
            int departmentId = Integer.parseInt(req.params(":id"));
            Department department2 = department.findById(departmentId);
            List<DepartmentPost> allDepartmentPost;
            if (department2 == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }
            allDepartmentPost = departmentPost.getAll();
            return gson.toJson(allDepartmentPost);
        });

    }
}
