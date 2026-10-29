import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    public boolean register(User user) {
        String sql = "INSERT INTO users (login, password_hash, first_name, last_name, father_name, role) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getFirstName());
            stmt.setString(4, user.getLastName());
            stmt.setString(5, user.getFatherName());
            stmt.setString(6, user.getRole());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Ошибка БД при регистрации: " + e.getMessage());
            return false;
        }
    }

    public User findByLogin(String login) {
        String sql = "SELECT * FROM users WHERE login = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setFatherName(rs.getString("father_name"));
                user.setRole(rs.getString("role"));
                return user;
            }
        } catch (SQLException e) {
            System.out.println("Ошибка БД при поиске: " + e.getMessage());
        }
        return null;
    }
}