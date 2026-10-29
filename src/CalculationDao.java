import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CalculationDao {

    // Сохранение расчета в БД
    public void save(Calculation calc) {
        String sql = "INSERT INTO calculations (user_id, calc_type, input_data, result_data) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, calc.getUserId());
            stmt.setString(2, calc.getType());
            stmt.setString(3, calc.getInputData());
            stmt.setString(4, calc.getResultData());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка сохранения расчета: " + e.getMessage());
        }
    }

    // 2. Получение истории для конкретного пользователя
    public List<Calculation> getHistory(int userId) {
        List<Calculation> list = new ArrayList<>();
        String sql = "SELECT c.id, u.login, c.calc_type, c.input_data, c.result_data, c.created_at " +
                "FROM calculations c " +
                "JOIN users u ON c.user_id = u.id " +
                "WHERE c.user_id = ?"+
                "ORDER BY c.created_at DESC"
                ;
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Calculation(
                        rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("calc_type"),
                        rs.getString("input_data"),
                        rs.getString("result_data"),
                        rs.getString("created_at")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Ошибка загрузки истории: " + e.getMessage());
        }
        return list;
    }

    // 3. Получение ВСЕЙ истории (только для админа)
    public List<Calculation> getAllHistory() {
        List<Calculation> list = new ArrayList<>();
        String sql = "SELECT c.id, u.login, c.calc_type, c.input_data, c.result_data, c.created_at " +
                "FROM calculations c " +
                "JOIN users u ON c.user_id = u.id " +
                "ORDER BY c.created_at DESC";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Calculation(
                        rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("calc_type"),
                        rs.getString("input_data"),
                        rs.getString("result_data"),
                        rs.getString("created_at")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Ошибка загрузки всей истории: " + e.getMessage());
        }
        return list;
    }
}