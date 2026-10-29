import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.control.Alert;



public class RegisterWindow {






    public void show(Stage stage) {





        //TitleBox содержимое
        HBox titleBox = new HBox(30);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setMaxWidth(Double.MAX_VALUE);
        titleBox.setPadding(new Insets(10, 0, 0, 0));

        BorderPane root = new BorderPane();
        Label title = new Label("Расчет кислородной фурмы");
        title.getStyleClass().add("Main-title");

        Image logoMining = new Image(getClass().getResourceAsStream("/resources/images/logoMining.png"));
        ImageView logoView = new ImageView(logoMining);
        logoView.setFitWidth(60);
        logoView.setFitHeight(60);
        logoView.setPreserveRatio(true);
        logoView.setTranslateY(-6);
        titleBox.getChildren().addAll(logoView, title);



        //LoginBox содержимое

        Label RegLabel = new Label("Регистрация пользователя");
        RegLabel.getStyleClass().add("text-1");
        RegLabel.setMinWidth(Region.USE_PREF_SIZE);

        VBox loginBox = new VBox(10);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setTranslateY(-30);

        TextField firstNameField = new TextField();
        firstNameField.setPromptText("Введите имя");
        firstNameField.getStyleClass().add("Field");

        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Введите фамилию");
        lastNameField.getStyleClass().add("Field");

        TextField fatherNameField = new TextField();
        fatherNameField.setPromptText("Введите Отчество");
        fatherNameField.getStyleClass().add("Field");

        TextField loginField = new TextField();
        loginField.setPromptText("Ваш логин");
        loginField.getStyleClass().add("Field");

        TextField passwordField = new TextField();
        passwordField.setPromptText("Ваш пароль");
        passwordField.getStyleClass().add("Field");




        //Отступ
        Region space = new Region();
        space.setPrefHeight(10);

        //КНОПКИ

        //Кнопка Зарегистрироваться
        Button registerBtn = new Button("Зарегистрироваться");
        registerBtn.getStyleClass().add("button-primary");
        registerBtn.setOnAction(e ->{
            String firstName = firstNameField.getText();
            String fatherName = fatherNameField.getText();
            String lastName = lastNameField.getText();
            String login = loginField.getText();
            String password = passwordField.getText();
            // ПРОВЕРКА ПОЛЕЙ
            if (login.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
                showAlert("Ошибка", "Заполните все поля");
                return;
            }
            if (password.length() < 8) {
                showAlert("Ошибка", "Пароль должен быть минимум 8 символов!");
                return;
            }
            // Сохранение пользователя в User
            User newUser = new User();
            newUser.setLogin(login);
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setFatherName(fatherName.isEmpty() ? null : fatherName);
            newUser.setRole("USER");
            String hashedPassword = PasswordHasher.hash(password); //хеширование пароля
            newUser.setPasswordHash(hashedPassword);
            UserDao dao = new UserDao();
            boolean success = dao.register(newUser);
            if (success) {
                User registeredUser = new UserDao().findByLogin(login); //поиск логина
                new ActionLogDao().log(registeredUser.getId(), "REGISTER");
                showAlert("Успех", "Пользователь успешно зарегистрирован!");}
            else {
                showAlert("Ошибка БД", "Не удалось сохранить. Возможно, логин уже занят.");
            }


        });

        //Кнопка Назад
        Button loginBtn = new Button("Вернуться ко входу");
        loginBtn.getStyleClass().add("button-primary");
        loginBtn.setOnAction(e ->{new LoginWindow().show(stage);});

        loginBox.getChildren().addAll(RegLabel,firstNameField, lastNameField, fatherNameField, loginField, passwordField,space, registerBtn, loginBtn);




        root.getStyleClass().add("Main-background");
        root.setTop(titleBox);
        root.setCenter(loginBox);






        // 3. Создаем сцену
        Scene scene = new Scene(root, 640, 480);
        scene.getStylesheets().add(
                getClass().getResource("/resources/style.css").toExternalForm()
        );
        // 4. Настраиваем и показываем окно
        stage.setTitle("Регистрация");
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}