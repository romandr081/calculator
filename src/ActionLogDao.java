import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ActionLogDao {

    public void log(int userId, String actionName) {
        String sql = "INSERT INTO action_log (user_id, action_name) VALUES (?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, actionName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка записи лога: " + e.getMessage());
        }
    }
}