import com.google.gson.Gson;
import models.Dao.Sql2o.SqlUser;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {

        String connectionString = "jdbc:postgresql://localhost:5432/news_portal_test";
        Sql2o sql2o = new Sql2o(connectionString, null, null);

        Connection conn;
        conn = sql2o.open();
        Gson gson = new Gson();
        SqlUser user = new SqlUser(sql2o);

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
        post("/users/new", "application/json", (req, res)->{
            User newUser = gson.fromJson(req.body(), User.class);
            user.add(newUser);
            res.status(201);
            return gson.toJson(newUser);
        });

    }
}
