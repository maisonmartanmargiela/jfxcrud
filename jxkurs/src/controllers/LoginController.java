package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.App;
import database.DBfunctional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private ResourceBundle resources;
    @FXML
    private TabPane formLogin;
    @FXML
    private Tab log;
    @FXML
    private Tab reg;
    @FXML
    private URL location;
    @FXML
    private TextField authLogin;
    @FXML
    private PasswordField authPass;
    @FXML
    private TextField regLogin;
    @FXML
    private PasswordField regPass;
    @FXML
    private PasswordField regPass1;

    // Инициализация класса DBConnect
    DBfunctional db = new DBfunctional();

    // Функция для показа ошибок
    void showAlert(Alert.AlertType typeError, String headerText, String contentText) {
        Alert alert = new Alert(typeError);
        alert.setTitle("ERROR");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    // Действие при нажатии на кнопку логин
    @FXML
    void auth(ActionEvent event) throws ClassNotFoundException {
        if (!(authLogin.getText().trim().isEmpty() || authPass.getText().trim().isEmpty())) {
            if (db.authenticate(authLogin.getText().trim(), authPass.getText().trim())) {
                openMainForm();
                JOptionPane.showMessageDialog(null, "Авторизация прошла успешно!", "Успех", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Такого пользователя не существует \n Проверьте введенные данные", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Вы не заполнили какое-то поле!", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Действие при нажатии на кнопку регистрации
    @FXML
    void register(ActionEvent event) throws ClassNotFoundException {
        if (!(regLogin.getText().trim().isEmpty() || regPass.getText().trim().isEmpty() || regPass1.getText().trim().isEmpty())) {
            if (regPass1.getText().trim().equals(regPass.getText().trim())) {
                // Вызов функции регистрации
                db.register(regLogin.getText().trim(), regPass.getText().trim());
            } else {
                JOptionPane.showMessageDialog(null, "Поля пароля не совпадают!", "Предупреждение", JOptionPane.WARNING_MESSAGE);
            }
        } else {
                JOptionPane.showMessageDialog(null, "Заполните все поля!", "Предупреждение", JOptionPane.WARNING_MESSAGE);
        }
    }
    

    // Функция открытия формы Main
    private void openMainForm() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("main.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        regLogin.getScene().getWindow().hide();
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.setTitle("Главная");
        stage.show();
    }

    @FXML
    void initialize() {
    }
}
