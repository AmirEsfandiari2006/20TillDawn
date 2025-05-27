package Models;


import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:game.db";

    public static void initialize() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            String sql = """
                CREATE TABLE IF NOT EXISTS users (
                    username TEXT PRIMARY KEY,
                    password TEXT NOT NULL,
                    email TEXT,
                    avatarIndex TEXT NOT NULL,
                    score INTEGER,
                    kills INTEGER,
                    maxSurviveTime INTEGER
                );
                """;

            stmt.execute(sql);
            System.out.println("Database initialized.");
        } catch (SQLException ignored) {

        }
    }

    public static void saveUser(User user) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = """
                INSERT INTO users (username, password, email, avatarIndex, score, kills, maxSurviveTime)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                ON CONFLICT(username) DO UPDATE SET
                    password = excluded.password,
                    email = excluded.email,
                    avatarIndex = excluded.avatarIndex,
                    score = excluded.score,
                    kills = excluded.kills,
                    maxSurviveTime = excluded.maxSurviveTime;
                """;

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getEmailAddress());
            pstmt.setString(4, user.getAvatarIndex());
            pstmt.setInt(5, user.getScore());
            pstmt.setInt(6, user.getKills());
            pstmt.setInt(7, user.getMaxSurviveTime());

            pstmt.executeUpdate();
        } catch (SQLException ignored) {
        }
    }

    public static ArrayList<User> loadUsers() {
        ArrayList<User> users = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {

            while (rs.next()) {
                User user = new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email")
                );
                user.setAvatarIndex(rs.getString("avatarIndex"));
                user.increaseScore(rs.getInt("score"));
                user.increaseKills(rs.getInt("kills"));
                user.setMaxSurviveTime(rs.getInt("maxSurviveTime"));

                users.add(user);
            }

        } catch (SQLException ignored) {

        }

        return users;
    }
}
