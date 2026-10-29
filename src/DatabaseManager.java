import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    // Путь к файлу базы данных. Программа создаст его автоматически, если файла нет.
    private static final String URL = "jdbc:sqlite:lance_calc.db";

    public static Connection getConnection() throws SQLException {
        try {
            // Явно загружаем драйвер SQLite
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Драйвер SQLite не найден!");
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL);
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("Подключение к SQLite успешно!");
        } catch (SQLException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}