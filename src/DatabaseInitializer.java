import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() {
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {

            //Пользователи
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    login TEXT UNIQUE NOT NULL,
                    password_hash TEXT NOT NULL,
                    last_name TEXT,
                    first_name TEXT,
                    father_name TEXT,
                    role TEXT DEFAULT 'USER',
                    reg_date TEXT DEFAULT CURRENT_TIMESTAMP
                )
                """);

            // Оборудование
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS equipment (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    description TEXT,
                    image_path TEXT
                )
                """);

            // Расчёты
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS calculations (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_id INTEGER NOT NULL,
                    equipment_id INTEGER,
                    calc_type TEXT NOT NULL,
                    input_data TEXT,
                    result_data TEXT,
                    created_at TEXT DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                    FOREIGN KEY (equipment_id) REFERENCES equipment(id) ON DELETE SET NULL
                )
                """);

            // Журнал действий
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS action_log (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_id INTEGER NOT NULL,
                    action_name TEXT NOT NULL,
                    timestamp TEXT DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
                )
                """);

            System.out.println("Таблицы созданы");

        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблиц:" + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        initialize();
    }
}