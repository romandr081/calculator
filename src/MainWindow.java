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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.control.ScrollPane;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.stage.FileChooser;
import javafx.scene.control.TableColumn;




public class MainWindow {
    private User currentUser;

    public void show(Stage stage, User user) {
        this.currentUser = user;


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

        // Приветствие
        Label userLabel = new Label("Добро пожаловать, "+ user.getFirstName() +" "+ user.getLastName() );
        userLabel.getStyleClass().add("text-2");
        userLabel.setMinWidth(Region.USE_PREF_SIZE);
        titleBox.getChildren().addAll(logoView, title);
        userLabel.setTranslateY(-10);


        VBox helloBox = new VBox();
        helloBox.getChildren().addAll(titleBox, userLabel);
        helloBox.setAlignment(Pos.CENTER);








        //ГЛАВНАЯ
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE); // Запрещает закрывать вкладки крестиком
        // Вкладка Расчеты
        Tab tabCalc = new Tab("Расчеты");
        VBox calcBox = new VBox(15);
        calcBox.setPadding(new Insets(20));
        calcBox.setAlignment(Pos.TOP_LEFT);

        //Радиокнопки
        ToggleGroup tg = new ToggleGroup();
        RadioButton rbManual = new RadioButton("Вручную");
        RadioButton rbRandom = new RadioButton("Случайно");
        RadioButton rbFile = new RadioButton("Из файла");
        rbManual.setToggleGroup(tg);
        rbRandom.setToggleGroup(tg);
        rbFile.setToggleGroup(tg);
        rbManual.setSelected(true);
        HBox radioBox = new HBox(30, rbManual, rbRandom, rbFile);
        radioBox.getStyleClass().add("RadioBox");


        // Поля ввода
        TextField fieldMass = new TextField();
        fieldMass.setPromptText("(т)");
        fieldMass.getStyleClass().add("Field-calc");
        TextField fieldIntensity = new TextField();
        fieldIntensity.setPromptText("(м3/т*мин)");
        fieldIntensity.getStyleClass().add("Field-calc");
        TextField fieldTheta = new TextField();
        fieldTheta.setPromptText("(град)");
        fieldTheta.getStyleClass().add("Field-calc");
        TextField fieldPressure = new TextField();
        fieldPressure.setPromptText("(атм)");
        fieldPressure.getStyleClass().add("Field-calc");
        TextField fieldTemp = new TextField();
        fieldTemp.setPromptText("(К)");
        fieldTemp.getStyleClass().add("Field-calc");
        TextField fieldSlag = new TextField();
        fieldSlag.setPromptText("(атм)");
        fieldSlag.getStyleClass().add("Field-calc");
        // Расположение полей в GridPane
        GridPane fieldsGrid = new GridPane();
        fieldsGrid.getStyleClass().add("FieldsGrid");
        fieldsGrid.setHgap(10); // Отступ по горизонтали между полями
        fieldsGrid.setVgap(10); // Отступ по вертикали между строками
        fieldsGrid.add(new Label("Масса плавки:"), 0, 0);
        fieldsGrid.add(fieldMass, 1, 0);
        fieldsGrid.add(new Label("Интенсивность:"), 2, 0);
        fieldsGrid.add(fieldIntensity, 3, 0);
        fieldsGrid.add(new Label("Угол раскрытия:"), 4, 0);
        fieldsGrid.add(fieldTheta, 5, 0);
        fieldsGrid.add(new Label("Давление в магистрали:"), 0, 1);
        fieldsGrid.add(fieldPressure, 1, 1);
        fieldsGrid.add(new Label("Температура О2:"), 2, 1);
        fieldsGrid.add(fieldTemp, 3, 1);
        fieldsGrid.add(new Label("Давление шлака:"), 4, 1);
        fieldsGrid.add(fieldSlag, 5, 1);
        fieldsGrid.setMaxWidth(Region.USE_PREF_SIZE);


        // Выпадающий список
        ComboBox<String> comboCalcType = new ComboBox<>();
        comboCalcType.getItems().addAll("Расчет фурмы", "Расчет сопла Лаваля");
        comboCalcType.setValue("Расчет фурмы");
        comboCalcType.getStyleClass().add("ComboCalcType");

        //Кнопки
        Button btnCalc = new Button("Рассчитать");
        btnCalc.getStyleClass().add("button-calc");
        Button btnClear = new Button("Очистить");
        btnClear.getStyleClass().add("button-exp");
        Button btnExport = new Button("Экспорт");
        btnExport.getStyleClass().add("button-exp");
        Button btnGenerate = new Button("Сгенерировать");
        btnGenerate.getStyleClass().add("button-calc");
        btnGenerate.setVisible(false); // Изначально скрыта
        btnGenerate.setManaged(false);   // Не занимает место
        Button btnFile = new Button("Выбрать файл...");
        btnFile.getStyleClass().add("button-calc");
        btnFile.setVisible(false);    // Изначально скрыта
        btnFile.setManaged(false);    // Не занимает место



        //Поле вывода
        TextArea resultArea = new TextArea(); resultArea.setEditable(false); resultArea.setPrefHeight(150);


        calcBox.getChildren().addAll(
                new Label("Режим:"), radioBox,
                fieldsGrid,
                new Label("Тип:"),
                new HBox(15, comboCalcType, btnFile, btnGenerate, btnCalc, btnClear, btnExport),

                new Label("Результат:"), resultArea
        );
        tabCalc.setContent(calcBox);

        // Вкладка История
        Tab tabHistory = new Tab("История");
        VBox historyBox = new VBox(10);
        historyBox.setPadding(new Insets(20));

        // Таблица
        TableView<Calculation> historyTable = new TableView<>();

        // Объявляем колонки
        TableColumn<Calculation, String> colUser = new TableColumn<>("Пользователь");
        colUser.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("userLogin"));
        TableColumn<Calculation, String> colDate = new TableColumn<>("Дата");
        colDate.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("date"));
        TableColumn<Calculation, String> colType = new TableColumn<>("Тип расчета");
        colType.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("type"));
        TableColumn<Calculation, String> colInput = new TableColumn<>("Входные данные");
        colInput.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("inputData"));
        TableColumn<Calculation, String> colResult = new TableColumn<>("Результат");
        colResult.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("resultData"));

        // колонки в таблице
        historyTable.getColumns().addAll(colUser, colDate, colType, colInput, colResult);
        historyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Кнопка обновления
        Button btnRefresh = new Button("Обновить историю");
        btnRefresh.getStyleClass().add("button-calc");
        btnRefresh.setOnAction(e -> {
            CalculationDao dao = new CalculationDao();
            if ("ADMIN".equals(currentUser.getRole())) {
                historyTable.getItems().setAll(dao.getAllHistory());
            } else {
                historyTable.getItems().setAll(dao.getHistory(user.getId()));
            }
        });
        historyBox.getChildren().addAll(btnRefresh, historyTable);
        tabHistory.setContent(historyBox);

        // Вкладка Справка
        Tab tabHelp = new Tab("Справка");
        TextFlow textFlow = new TextFlow();
        textFlow.setPadding(new Insets(20));
        textFlow.setLineSpacing(5);
        Text t1 = new Text("РУКОВОДСТВО ПОЛЬЗОВАТЕЛЯ\n");
        t1.getStyleClass().add("help-title");
        Text t2 = new Text("Добро пожаловать в приложение «Расчет кислородной фурмы»!\nДанная программа предназначена для выполнения инженерных расчетов параметров кислородной фурмы и сопла Лаваля.\n\n");
        t2.getStyleClass().add("help-text");
        Text t3 = new Text("1. Авторизация\n");
        t3.getStyleClass().add("help-section-header");
        Text t4 = new Text("Для начала работы с приложением вам необходимо пройти процедуру входа или создать новый аккаунт.\n• Введите ваш логин и пароль в соответствующие поля.\n• Если у вас нет учетной записи, нажмите кнопку «Регистрация» и заполните форму.\n\n");
        t4.getStyleClass().add("help-text");
        Text t5 = new Text("2. Вкладка «Расчеты»\n");
        t5.getStyleClass().add("help-section-header");
        Text t6 = new Text("Данный раздел предназначен для выполнения основных инженерных вычислений. Выберите один из трех режимов ввода данных:\n• Вручную — вы самостоятельно вводите все необходимые параметры в поля ввода.\n• Случайно — программа автоматически сгенерирует подходящие значения и выполнит расчет.\n• Из файла — данные загружаются из текстового файла формата .txt.\n\n");
        t6.getStyleClass().add("help-text");
        Text t7 = new Text("3. Вкладка «История»\n");
        t7.getStyleClass().add("help-section-header");
        Text t8 = new Text("В этом разделе сохраняются все произведенные вами расчеты.\n• Для просмотра списка нажмите кнопку «Обновить историю».\n• В таблице отображаются дата, тип расчета, введенные параметры и полученный результат.\n\n");
        t8.getStyleClass().add("help-text");
        Text t9 = new Text("4. Экспорт данных\n");
        t9.getStyleClass().add("help-section-header");
        Text t10 = new Text("Вы можете сохранить результаты текущего расчета на свой компьютер.\n• Нажмите кнопку «Экспорт».\n• Выберите папку и укажите имя файла. Данные сохранятся в формате CSV, который можно открыть в Excel.\n");
        t10.getStyleClass().add("help-text");
        textFlow.getChildren().addAll(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10);
        ScrollPane scrollPane = new ScrollPane(textFlow);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.getStyleClass().add("help-scroll");
        tabHelp.setContent(scrollPane);




        tabPane.getTabs().addAll(tabCalc, tabHistory, tabHelp);
        root.setCenter(tabPane);






// ДЕЙСТВИЯ
        // Переключатели
        tg.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {

            if (rbRandom.isSelected()) {
                fieldMass.setDisable(true);
                fieldIntensity.setDisable(true);
                fieldPressure.setDisable(true);
                fieldSlag.setDisable(true);
                fieldTemp.setDisable(true);
                fieldTheta.setDisable(true);
                btnGenerate.setVisible(true);
                btnGenerate.setManaged(true);
                btnFile.setVisible(false);
                btnFile.setManaged(false);


            }
            else if (rbFile.isSelected()) {
                fieldMass.setDisable(true);
                fieldIntensity.setDisable(true);
                fieldPressure.setDisable(true);
                fieldSlag.setDisable(true);
                fieldTemp.setDisable(true);
                fieldTheta.setDisable(true);
                btnGenerate.setVisible(false);
                btnGenerate.setManaged(false);
                btnFile.setVisible(true);
                btnFile.setManaged(true);

            }
            else {
                fieldMass.setDisable(false);
                fieldIntensity.setDisable(false);
                fieldPressure.setDisable(false);
                fieldSlag.setDisable(false);
                fieldTemp.setDisable(false);
                fieldTheta.setDisable(false);
                btnGenerate.setVisible(false);
                btnGenerate.setManaged(false);
                btnFile.setVisible(false);
                btnFile.setManaged(false);

            }


        });

        //РАСЧЕТЫ!!!
        btnCalc.setOnAction(e -> {
            try {
                double mass = Double.parseDouble(fieldMass.getText());
                double intensity = Double.parseDouble(fieldIntensity.getText());
                double theta = Double.parseDouble(fieldTheta.getText());

                if (mass <= 0 || intensity <= 0 || theta <= 0) {
                    showAlert("Ошибка", "Все значения должны быть больше нуля");
                    return; }

                String type = comboCalcType.getValue(); // какой расчет выбран

                if (type.equals("Расчет фурмы")) {
                    // Число сопел
                    double m = (intensity / (1.168 - 9.42 * mass * Math.pow(10, -4))) + 1;
                    int mInt = (int) Math.round(m); // Округляем до целого
                    // Угол наклона
                    double gammaRad = Math.asin(
                            Math.tan(Math.toRadians(theta / 2)) / Math.sin(Math.toRadians(180.0 / mInt))
                    );
                    double gammaDeg = Math.toDegrees(gammaRad); // Переводим радианы в градусы
                    // Вывод результата
                    resultArea.setText("РЕЗУЛЬТАТ РАСЧЕТА ФУРМЫ\n");
                    resultArea.appendText("\n");
                    resultArea.appendText("Число сопел m: " + mInt + "\n");
                    resultArea.appendText(String.format("Угол наклона γ: %.2f°\n", gammaDeg));

                } else if (type.equals("Расчет сопла Лаваля")) {

                    // Читаем дополнительные данные для Лаваля
                    double P_mag = Double.parseDouble(fieldPressure.getText());
                    double T1 = Double.parseDouble(fieldTemp.getText());
                    double dP = Double.parseDouble(fieldSlag.getText());
                    // Считаем число сопел
                    double m = (intensity / (1.168 - 9.42 * mass * Math.pow(10, -4))) + 1;
                    int mInt = (int) Math.round(m);
                    // Константы
                    double g = 9.81;
                    double k = 1.4;
                    double R = 26.5;
                    double rho = 1.43; // плотность кислорода при н.у.
                    // Давление перед соплом (формула 1.3)
                    double P1 = 0.85 * P_mag;
                    // Критические параметры (формулы 1.8 - 1.11)
                    double W_kr = Math.sqrt(2 * g * (k / (k + 1)) * R * T1);
                    double P_kr = P1 * Math.pow(2.0 / (k + 1), k / (k - 1));
                    double T_kr = T1 * (2.0 / (k + 1));
                    double rho_kr = P_kr / (R * T_kr * Math.pow(10, -4));
                    // Площадь и диаметр критического сечения (формулы 1.12, 1.13)
                    double F_kr = (intensity * mass * rho) / (0.95 * 60 * mInt * rho_kr * W_kr);
                    double d_kr = Math.sqrt(4 * F_kr / Math.PI) * 1000; // умножаем на 1000 для перевода в мм
                    // Параметры на выходе
                    double P2 = 1 + dP;
                    double W2 = 0.98 * Math.sqrt(2 * g * (k / (k - 1)) * R * T1 * (1 - Math.pow(P2 / P1, (k - 1) / k)));
                    double T2 = T1 * Math.pow(P2 / P1, (k - 1) / k);
                    double rho2 = P2 / (R * T2 * Math.pow(10, -4));
                    // Площадь и диаметр выходного сечения
                    double F2 = (intensity * mass * rho) / (0.95 * 60 * mInt * rho2 * W2);
                    double d2 = Math.sqrt(4 * F2 / Math.PI) * 1000; // в мм
                    // Длины
                    double alpha = 4.9;
                    double l2 = (d2 - d_kr) / (2 * Math.tan(Math.toRadians(alpha)));
                    double l1 = 0.7 * d_kr;
                    double d1 = 1.2 * d_kr;
                    // Диаметр трубы
                    double rho1 = P1 / (R * T1 * Math.pow(10, -4));
                    double F_kt = (intensity * mass * rho) / (60 * rho1 * 40); // W1 = 40 м/с
                    double D_kt = Math.sqrt(4 * F_kt / Math.PI) * 1000; // в мм
                    //Вывод результата
                    resultArea.setText("РЕЗУЛЬТАТ РАСЧЕТА СОПЛА ЛАВАЛЯ\n");
                    resultArea.appendText("\n");
                    resultArea.appendText("Число сопел m: " + mInt + "\n");
                    resultArea.appendText("Давление перед соплом P1: " + String.format("%.2f", P1) + " атм\n");
                    resultArea.appendText("Диаметр критического сечения d_кр: " + String.format("%.1f", d_kr) + " мм\n");
                    resultArea.appendText("Диаметр выходного сечения d2: " + String.format("%.1f", d2) + " мм\n");
                    resultArea.appendText("Длина расширяющейся части l2: " + String.format("%.1f", l2) + " мм\n");
                    resultArea.appendText("Длина сужающейся части l1: " + String.format("%.1f", l1) + " мм\n");
                    resultArea.appendText("Диаметр входного сечения d1: " + String.format("%.1f", d1) + " мм\n");
                    resultArea.appendText("Внутренний диаметр трубы D_кт: " + String.format("%.1f", D_kt) + " мм\n");

                }
                String inputStr = "Масса=" + mass + ", Инт=" + intensity + ", Угол=" + theta;
                String resultStr = resultArea.getText().replace("\n", " ");
                Calculation calc = new Calculation(user.getId(), type, inputStr, resultStr);
                CalculationDao dao = new CalculationDao();
                dao.save(calc); //сохранение в базу
                new CalculationDao().save(calc);
                new ActionLogDao().log(currentUser.getId(), "CALCULATE_" + type.toUpperCase().replace(" ", "_"));

            }


            catch (NumberFormatException ex) {
                // Если пользователь ввел буквы
                showAlert("Ошибка", "Введите корректные числа во все поля");
            }
        });

        btnClear.setOnAction(e -> {
            resultArea.clear();
        });
        //ЭКСПОРТ
        btnExport.setOnAction(e -> {
            // диалоговое окно для выбора места сохранения
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Сохранить результат расчета");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT файлы (*.txt)", "*.txt"));
            fileChooser.setInitialFileName("Расчет_фурмы_" + currentUser.getLastName() + ".txt");
            // Открываем окно сохранения
            File file = fileChooser.showSaveDialog(stage);

            //  пользователь выбрал файл
            if (file != null) {
                try (FileWriter writer = new FileWriter(file, java.nio.charset.StandardCharsets.UTF_8)) {
                   //  заголовок таблицы
                    writer.write("\uFEFF");
                    writer.write("Тип расчета:  " + comboCalcType.getValue() + "\n");
                    writer.write(" "+ "\n");
                    writer.write("ИСХОДНЫЕ ДАННЫЕ"+ "\n");
                    // Пишем входные данные из полей
                    writer.write("Масса плавки: " + fieldMass.getText() + " т."+"\n");
                    writer.write("Интенсивность: " + fieldIntensity.getText() + "\n");
                    writer.write("Угол раскрытия: " + fieldTheta.getText() +" град." + "\n");
                    writer.write("Давление: " + fieldPressure.getText() +" атм."+ "\n");
                    writer.write("Температура: " + fieldTemp.getText() + " К"+"\n");
                    writer.write("Давление шлака: " + fieldSlag.getText() + " атм."+ "\n");
                    writer.write(" "+ "\n");

                    String resultText = resultArea.getText();
                    String[] lines = resultText.split("\n");
                    for (String line : lines) {
                        line = line.trim();
                        if (line.isEmpty() || line.startsWith("---")) {
                            continue;
                        }
                        writer.write(line.replace(":", ";") + "\n");
                    }


                    showAlert("Успех", "Данные успешно сохранены в файл:\n" + file.getAbsolutePath());
                    new ActionLogDao().log(currentUser.getId(), "EXPORT_" + file.getName());


                } catch (IOException ex) {
                    showAlert("Ошибка", "Не удалось сохранить файл: " + ex.getMessage());
                }
            }
        });

        //СГЕНЕРИРОВАТЬ
        btnGenerate.setOnAction(e -> {
            if (rbRandom.isSelected()) {
                java.util.Random random = new java.util.Random();
                double randomMass = 100 + (400 - 100) * random.nextDouble();
                double randomIntensity = 2.0 + (6.0 - 2.0) * random.nextDouble();
                double randomTheta = 20 + (26 - 20) * random.nextDouble();
                double randomPressure = 15 + (18 - 15) * random.nextDouble();
                double randomTemp = 290 + (300 - 290) * random.nextDouble();
                double randomSlag = 0.1 + (0.6 - 0.1) * random.nextDouble();
                fieldMass.setText(String.format(java.util.Locale.US, "%.0f", randomMass));
                fieldIntensity.setText(String.format(java.util.Locale.US, "%.1f", randomIntensity));
                fieldTheta.setText(String.format(java.util.Locale.US, "%.0f", randomTheta));
                fieldPressure.setText(String.format(java.util.Locale.US, "%.0f", randomPressure));
                fieldTemp.setText(String.format(java.util.Locale.US, "%.0f", randomTemp));
                fieldSlag.setText(String.format(java.util.Locale.US, "%.1f", randomSlag));}
        });

        //ВХОД ФАЙЛА
        btnFile.setOnAction(e -> {
            javafx.stage.FileChooser fc = new javafx.stage.FileChooser();
            fc.getExtensionFilters().add(new javafx.stage.FileChooser.ExtensionFilter("Текстовые файлы (*.txt)", "*.txt"));
            java.io.File file = fc.showOpenDialog(stage);

            if (file != null) {
                try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file))) {
                    fieldMass.setText(reader.readLine());
                    fieldIntensity.setText(reader.readLine());
                    fieldTheta.setText(reader.readLine());
                    fieldPressure.setText(reader.readLine());
                    fieldTemp.setText(reader.readLine());
                    fieldSlag.setText(reader.readLine());

                } catch (Exception ex) {
                    showAlert("Ошибка файла", "Не удалось прочитать файл. Убедитесь, что в нем 6 строк с числами.");
                }
            }
        });






        root.getStyleClass().add("Main-background");
        root.setTop(helloBox);

        // 3. Создаем сцену
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(
                getClass().getResource("/resources/style.css").toExternalForm()
        );
        // 4. Настраиваем и показываем окно
        stage.setTitle("Главная");
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