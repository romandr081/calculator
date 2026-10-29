public class Calculation {
    private int id;
    private int userId;
    private String userLogin;
    private String type;
    private String inputData;
    private String resultData;
    private String date;

    // 1. Конструктор для СОХРАНЕНИЯ (4 параметра)
    public Calculation(int userId, String type, String inputData, String resultData) {
        this.userId = userId;
        this.type = type;
        this.inputData = inputData;
        this.resultData = resultData;
    }

    // 2. Конструктор для ИСТОРИИ ПОЛЬЗОВАТЕЛЯ (5 параметров)
    public Calculation(int id, String type, String inputData, String resultData, String date) {
        this.id = id;
        this.type = type;
        this.inputData = inputData;
        this.resultData = resultData;
        this.date = date;
    }

    // 3. Конструктор для ИСТОРИИ АДМИНА (6 параметров)
    public Calculation(int id, String userLogin, String type, String inputData, String resultData, String date) {
        this.id = id;
        this.userLogin = userLogin;
        this.type = type;
        this.inputData = inputData;
        this.resultData = resultData;
        this.date = date;
    }

    // Геттеры (должны называться ИМЕННО ТАК для работы таблицы)
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getUserLogin() { return userLogin; }
    public String getType() { return type; }
    public String getInputData() { return inputData; }
    public String getResultData() { return resultData; }
    public String getDate() { return date; }
}