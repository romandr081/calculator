import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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



public class LoginWindow {




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
        VBox loginBox = new VBox(10);
        loginBox.setAlignment(Pos.CENTER);

        TextField loginField = new TextField();
        loginField.setPromptText("Ваш логин");
        loginField.getStyleClass().add("Field");

        TextField passwordField = new TextField();
        passwordField.setPromptText("Ваш пароль");
        passwordField.getStyleClass().add("Field");

            //Отступ
        Region space = new Region();
        space.setPrefHeight(20);
            //Кнопки
        Button loginBtn = new Button("Войти");
        loginBtn.getStyleClass().add("button-primary");
        loginBtn.setOnAction(e -> {
            String login = loginField.getText();
            String password = passwordField.getText();
            if (login.isEmpty() || password.isEmpty()) {
                showAlert("Ошибка", "Введите логин и пароль!");
                return;}
            UserDao dao = new UserDao();
            User user = dao.findByLogin(login);
            if (user==null){
                showAlert("Ошибка", "Пользователь с таким логином не найден");
            }
            String enteredHash = PasswordHasher.hash(password);
            if (enteredHash.equals(user.getPasswordHash())) {
                new ActionLogDao().log(user.getId(), "LOGIN");
                new MainWindow().show(stage, user);
            }else{
                showAlert("Ошибка", "Пароль неверный");
            }





        });

        Button registerBtn = new Button("Регистрация");
        registerBtn.getStyleClass().add("button-primary");
        registerBtn.setOnAction(e -> {
            new RegisterWindow().show(stage);
        });

        loginBox.getChildren().addAll(loginField, passwordField, space, loginBtn, registerBtn);




        root.getStyleClass().add("Main-background");
        root.setTop(titleBox);
        root.setCenter(loginBox);






        // 3. Создаем сцену
        Scene scene = new Scene(root, 640, 480);
        scene.getStylesheets().add(
                getClass().getResource("/resources/style.css").toExternalForm()
        );
        // 4. Настраиваем и показываем окно
        stage.setTitle("Вход");
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