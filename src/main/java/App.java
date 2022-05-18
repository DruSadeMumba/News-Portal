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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        port(4040);
        String connectionString = "jdbc:postgresql://localhost:5432/news_portal_test";
        Sql2o sql2o = new Sql2o(connectionString, null, null);

        Gson gson = new Gson();
        SqlUser user = new SqlUser(sql2o);
        SqlDepartment department = new SqlDepartment(sql2o);
        SqlDepartmentPost departmentPost = new SqlDepartmentPost(sql2o);
        SqlPost post = new SqlPost(sql2o);
        Connection conn;
        conn = sql2o.open();



                //DEPARTMENTS
        //create new department
        post("/departments/new", "application/json", (req, res)->{
            Department newDepartment = gson.fromJson(req.body(), Department.class);
            department.add(newDepartment);
            res.status(201);
            return gson.toJson(newDepartment);
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

        //get dept by id
        get("/departments/:departmentId", "application/json", (req, res)->{
            int departmentId = Integer.parseInt(req.params("departmentId"));
            if (department.findById(departmentId) == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("departmentId")));
            }
            return gson.toJson(department.findById(departmentId));
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

            //delete department
        delete("/departments/:id/delete", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params(":id"));
            res.status(200);
            return gson.toJson(department.deleteById(departmentId));
        });



                //USERS
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

        //get user by id
        get("/users/:userId", "application/json", (req, res)->{
            int userId = Integer.parseInt(req.params("userId"));
            if (user.findById(userId) == null){
                throw new ApiException(404, String.format("No user with the id: \"%s\" exists", req.params("userId")));
            }
            return gson.toJson(user.findById(userId));
        });

        //delete user
        delete("/users/:userId/delete", "application/json", (req, res) -> {
            int userId = Integer.parseInt(req.params("userId"));
            res.status(200);
            return gson.toJson(user.deleteById(userId));
        });



                    //POSTS
                //create new post
        post("/users/:userId/posts/new", "application/json", (req, res)->{
            int userId = Integer.parseInt(req.params("userId"));
            Post newPost = gson.fromJson(req.body(), Post.class);
            newPost.setUserId(userId);
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

        //delete posts
        delete("/posts/:id/delete", "application/json", (req, res) -> {
            int postId = Integer.parseInt(req.params(":id"));
            res.status(200);
            return gson.toJson(post.deleteById(postId));
        });

                //create new department post
        post("/users/:userId/departments/:departmentId/departmentPosts/new", "application/json", (req, res)->{ //not working
            int departmentId = Integer.parseInt(req.params("departmentId"));
            int userId = Integer.parseInt(req.params("userId"));
            DepartmentPost newDepartmentPost = gson.fromJson(req.body(), DepartmentPost.class);
            newDepartmentPost.setCreatedat();
            newDepartmentPost.setFormattedCreatedAt();
            newDepartmentPost.setDepartmentId(userId);
            newDepartmentPost.setDepartmentId(departmentId);
            departmentPost.add(newDepartmentPost);
            res.status(201);
            return gson.toJson(newDepartmentPost);
        });

                //get department posts
        get("/departments/:id/departmentPosts", "application/json", (req, res)->{//not working
            int departmentId = Integer.parseInt(req.params(":id"));
            Department department2 = department.findById(departmentId);
            List<DepartmentPost> allDepartmentPost;
            if (department2 == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }
            allDepartmentPost = departmentPost.getAll(departmentId);
            return gson.toJson(allDepartmentPost);
        });

        //delete dept post
        delete("/departmentPosts/:id/delete", "application/json", (req, res) -> {
            int departmentPostId = Integer.parseInt(req.params(":id"));
            res.status(200);
            return gson.toJson(departmentPost.deleteById(departmentPostId));
        });

            //Filters
        exception(ApiException.class, (exception, req, res) -> {
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", ((ApiException) exception).getStatusCode());
            jsonMap.put("errorMessage", exception.getMessage());
            res.type("application/json");
            res.status(((ApiException) exception).getStatusCode());
            res.body(gson.toJson(jsonMap));
        });

        after((req, res) ->{
            res.type("application/json");
        });
    }
}





//edit user
        /*put("/users/:userId", "application/json", (req, res)->{
            User newUser = gson.fromJson(req.body(), User.class);

        });*/

        /*put("/news/:id", (req, res) -> {
            Gson gson = new Gson();
            News news = gson.fromJson(req.body(), News.class);
            news.setId(Integer.parseInt(req.params(":id")));
            res.status(200);
            res.type("application/json");
            return gson.toJson(new NewsDao().updateNews(connection, news));
        });*/
