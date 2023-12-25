package controllers;

import java.io.File;
import java.util.List;
import java.util.Optional;

import database.DBfunctional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import objects.Auto;
import objects.Autobase;
import objects.Gas_station;

public class MainController {
    @FXML
    private TableView<Autobase> AutobaseTable;

    @FXML
    private TableColumn<Autobase, Integer> id_autobase;

    @FXML
    private TableColumn<Autobase, String> number_autobase;

    @FXML
    private TableColumn<Autobase, String> address_autobase;

    @FXML
    private TableColumn<Autobase, Integer> id_auto_autobase;

    @FXML
    private TableColumn<Autobase, Integer> id_gas_autobase;

    @FXML
    private Tab tabAutobase;

    @FXML
    private Tab tabGas_station;

    @FXML
    private Tab tabAuto;

    @FXML
    private ChoiceBox<String> orderAutobase;

    @FXML
    private TableView<Gas_station> GasTable;

    @FXML
    private TableColumn<Gas_station, Integer> id_gas;

    @FXML
    private TableColumn<Gas_station, String> name_gas;

    @FXML
    private TableColumn<Gas_station, String> address_gas;

    @FXML
    private TableColumn<Gas_station, String> gas_grade_gas;

    @FXML
    private ChoiceBox<String> orderGasStation;

    @FXML
    private TableView<Auto> AutoTable;

    @FXML
    private TableColumn<Auto, Integer> id_auto;

    @FXML
    private TableColumn<Auto, String> firm_auto;

    @FXML
    private TableColumn<Auto, String> brand_auto;

    @FXML
    private TableColumn<Auto, String> license_plate_auto;

    @FXML
    private TableColumn<Auto, String> gas_grade_auto;

    @FXML
    private ChoiceBox<String> orderAuto;

    @FXML
    private ChoiceBox<Integer> textidGasStationAuto1;

    @FXML
    private ChoiceBox<Integer> textidGasStationAuto;

    @FXML
    private TextField textNumber_autobase;

    @FXML
    private TextField textAddress_autobase;

    @FXML
    private ChoiceBox<Integer> textIdAutoAutobase;

    @FXML
    private ChoiceBox<Integer> textIdGasAutobase;

    @FXML
    private TextField textNamegas;

    @FXML
    private TextField textAddress_gas;

    @FXML
    private ChoiceBox<String> textGas_grade_gas;

    @FXML
    private TextField textFirmAuto;

    @FXML
    private TextField textBrandAuto;

    @FXML
    private ChoiceBox<String> textGas_grade_auto;

    @FXML
    private TextField textlicense_plate_auto;

    @FXML
    private ChoiceBox<String> orderByAutobase;

    @FXML
    private ChoiceBox<String> orderByGasStation;

    @FXML
    private ChoiceBox<String> orderByAuto;

    // списки для работы с запросами и базой данных
    ObservableList<String> orderAutoList = FXCollections.observableArrayList("id_auto", "firm_auto", "brand_auto", "license_plate_auto", "gas_grade_auto");
    ObservableList<String> orderAutobaseList = FXCollections.observableArrayList("id_autobase", "number_autobase", "address_autobase", "id_auto", "id_gas");
    ObservableList<String> orderGasStationList = FXCollections.observableArrayList("id_gas", "name_gas", "address_gas", "gas_grade_gas");
    ObservableList<String> GasSort = FXCollections.observableArrayList("A92", "A95", "A98", "ДТ", " ");

    private DBfunctional db = new DBfunctional();
        // функция для обновления всех таблиц и элементов управления
        void updateAllTables() throws ClassNotFoundException {
            // установка значений по умолчанию
            orderAuto.setItems(orderAutoList);
            orderAuto.setValue(orderAutoList.get(0));
            orderAutobase.setItems(orderAutobaseList);
            orderAutobase.setValue(orderAutobaseList.get(0));
            orderGasStation.setItems(orderGasStationList);
            orderGasStation.setValue(orderGasStationList.get(0));
        
            // получение Select для каждой таблицы
            AutoTable.setItems(FXCollections.observableArrayList(db.orderByAuto(orderAuto.getValue())));
            AutobaseTable.setItems(FXCollections.observableArrayList(db.orderByAutobase(orderAutobase.getValue())));
            GasTable.setItems(FXCollections.observableArrayList(db.orderByGasStation(orderGasStation.getValue())));
            
        }
    @FXML
    void initialize() throws ClassNotFoundException {
        id_autobase.setCellValueFactory(new PropertyValueFactory<Autobase, Integer>("id_autobase"));
        number_autobase.setCellValueFactory(new PropertyValueFactory<Autobase, String>("number_autobase"));
        address_autobase.setCellValueFactory(new PropertyValueFactory<Autobase, String>("address_autobase"));
        id_auto_autobase.setCellValueFactory(new PropertyValueFactory<Autobase, Integer>("id_auto"));
        id_gas_autobase.setCellValueFactory(new PropertyValueFactory<Autobase, Integer>("id_gas"));

        List<Integer> autoIds = db.getAllAutoIds();
        textIdAutoAutobase.setItems(FXCollections.observableArrayList(autoIds));
        
        if (!autoIds.isEmpty()) {
            textIdAutoAutobase.setValue(autoIds.get(0)); // Установка первого элемента по умолчанию
        }

        List<Integer> gasIds = db.getAllGasStationIds();
        textIdGasAutobase.setItems(FXCollections.observableArrayList(gasIds));

        if (!gasIds.isEmpty()) {
            textIdGasAutobase.setValue(gasIds.get(0)); // Установка первого элемента по умолчанию
        }
                
        textGas_grade_auto.setItems(GasSort);
        textGas_grade_auto.setValue(GasSort.get(0));
        
        textGas_grade_auto.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() >= 0) {
                textGas_grade_auto.setValue(GasSort.get(newValue.intValue()));
            }
        });

        textGas_grade_gas.setItems(GasSort);
        textGas_grade_gas.setValue(GasSort.get(0));
        
        textGas_grade_gas.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() >= 0) {
                textGas_grade_auto.setValue(GasSort.get(newValue.intValue()));
            }
        });

        orderAutobase.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, number2) -> {
            try {
                AutobaseTable.setItems(db.orderByAutobase(orderAutobase.getItems().get(number2.intValue())));
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

            // Для Gas_station
        id_gas.setCellValueFactory(new PropertyValueFactory<Gas_station, Integer>("id_gas"));
        name_gas.setCellValueFactory(new PropertyValueFactory<Gas_station, String>("name_gas"));
        address_gas.setCellValueFactory(new PropertyValueFactory<Gas_station, String>("address_gas"));
        gas_grade_gas.setCellValueFactory(new PropertyValueFactory<Gas_station, String>("gas_grade_gas"));

        orderGasStation.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, number2) -> {
            try {
                GasTable.setItems(db.orderByGasStation(orderGasStation.getItems().get(number2.intValue())));
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        // Для Auto
        id_auto.setCellValueFactory(new PropertyValueFactory<Auto, Integer>("id_auto"));
        firm_auto.setCellValueFactory(new PropertyValueFactory<Auto, String>("firm_auto"));
        brand_auto.setCellValueFactory(new PropertyValueFactory<Auto, String>("brand_auto"));
        license_plate_auto.setCellValueFactory(new PropertyValueFactory<Auto, String>("license_plate_auto"));
        gas_grade_auto.setCellValueFactory(new PropertyValueFactory<Auto, String>("gas_grade_auto"));

        orderAuto.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, number2) -> {
            try {
                AutoTable.setItems(db.orderByAuto(orderAuto.getItems().get(number2.intValue())));
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        updateAllTables();
    }

    @FXML
    void addAutobase() {
        try {
            // Получаем значения из полей ввода
            String number = textNumber_autobase.getText();
            String address = textAddress_autobase.getText();
            
            // Получаем выбранные значения из ChoiceBox для ID автомобиля и заправочной станции
            int selectedAutoId = textIdAutoAutobase.getValue();
            int selectedGasId = textIdGasAutobase.getValue();
    
            // Вызываем метод для вставки новой записи в таблицу Autobase
            DBfunctional.insertAutobase(number, address, selectedAutoId, selectedGasId);
    
            // Обновляем таблицу Autobase после добавления записи
            AutobaseTable.setItems(FXCollections.observableArrayList(db.selectAllBases()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    

    @FXML
    void deleteAutobase() {
        Autobase selectedAutobase = AutobaseTable.getSelectionModel().getSelectedItem();

        if (selectedAutobase != null) {
            int idToDelete = selectedAutobase.getId_autobase();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение удаления");
            alert.setHeaderText(null);
            alert.setContentText("Вы уверены, что хотите удалить запись с ID " + idToDelete + "?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    db.deleteAutobaseById(idToDelete);
                    updateAllTables(); // Обновить таблицы после удаления
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // Пользователь не выбрал запись для удаления, можешь добавить уведомление
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Выберите запись");
            alert.setHeaderText(null);
            alert.setContentText("Пожалуйста, выберите запись для удаления.");
            alert.showAndWait();
        }
    }


    @FXML
    void editAutobase() {
        Autobase selectedAutobase = AutobaseTable.getSelectionModel().getSelectedItem();
    
        if (selectedAutobase != null) {
            int idToUpdate = selectedAutobase.getId_autobase();
    
            // Создаем диалоговое окно редактирования
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Редактирование Автобазы");
            dialog.setHeaderText(null);
    
            // Создаем кнопки "Применить" и "Отмена"
            ButtonType applyButtonType = new ButtonType("Применить", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(applyButtonType, ButtonType.CANCEL);
    
            // Создаем поля ввода для номера и адреса
            TextField numberField = new TextField(selectedAutobase.getNumber_autobase());
            TextField addressField = new TextField(selectedAutobase.getAddress_autobase());
    
            // Создаем ChoiceBox для выбора id_auto и id_gas
            ChoiceBox<Integer> idAutoChoiceBox = new ChoiceBox<>();
            idAutoChoiceBox.setItems(FXCollections.observableArrayList(db.getAllAutoIds()));
            idAutoChoiceBox.setValue(selectedAutobase.getId_auto());
    
            ChoiceBox<Integer> idGasChoiceBox = new ChoiceBox<>();
            idGasChoiceBox.setItems(FXCollections.observableArrayList(db.getAllGasStationIds()));
            idGasChoiceBox.setValue(selectedAutobase.getId_gas());
    
            // Добавляем поля ввода и метки в диалоговое окно
            GridPane grid = new GridPane();
            grid.add(new Label("Номер:"), 0, 0);
            grid.add(numberField, 1, 0);
            grid.add(new Label("Адрес:"), 0, 1);
            grid.add(addressField, 1, 1);
            grid.add(new Label("ID Auto:"), 0, 2);
            grid.add(idAutoChoiceBox, 1, 2);
            grid.add(new Label("ID Gas:"), 0, 3);
            grid.add(idGasChoiceBox, 1, 3);
            dialog.getDialogPane().setContent(grid);
    
            // Привязываем кнопку "Применить" к условиям
            Node applyButton = dialog.getDialogPane().lookupButton(applyButtonType);
            applyButton.setDisable(true);
    
            // Слушаем изменения в полях ввода и включаем кнопку "Применить"
            numberField.textProperty().addListener((observable, oldValue, newValue) -> {
                applyButton.setDisable(newValue.trim().isEmpty());
            });
    
            // Настройка действий при нажатии "Применить"
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == applyButtonType) {
                    return new javafx.util.Pair<>(numberField.getText(), addressField.getText());
                }
                return null;
            });
    
            // Отображение диалогового окна и обработка результатов
            Optional<javafx.util.Pair<String, String>> result = dialog.showAndWait();
            result.ifPresent(pair -> {
                try {
                    // Вызываем метод обновления с новыми значениями
                    db.updateAutobase(idToUpdate, pair.getKey(), pair.getValue(), idAutoChoiceBox.getValue(), idGasChoiceBox.getValue());
                    updateAllTables(); // Обновляем таблицы после редактирования
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        } else {
            // Пользователь не выбрал запись для редактирования, можешь добавить уведомление
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Выберите запись");
            alert.setHeaderText(null);
            alert.setContentText("Пожалуйста, выберите запись для редактирования.");
            alert.showAndWait();
        }
    }    

    @FXML
    void exportAutobase() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Выберите файл для экспорта");
            FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("Текстовый файл (*.txt)", "*.txt");
            FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("CSV файл (*.csv)", "*.csv");
            FileChooser.ExtensionFilter excelFilter = new FileChooser.ExtensionFilter("Excel файл (*.xlsx)", "*.xlsx");
            fileChooser.getExtensionFilters().addAll(txtFilter, csvFilter, excelFilter);
    
            File file = fileChooser.showSaveDialog(null);
    
            if (file != null) {
                String filePath = file.getAbsolutePath();
    
                // Передаем путь к файлу и расширение в метод экспорта
                DBfunctional.exportAutobaseTableToFile(filePath);
    
                // Можешь добавить уведомление об успешном экспорте
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Экспорт завершен");
                alert.setHeaderText(null);
                alert.setContentText("Данные успешно экспортированы в файл: " + filePath);
                alert.showAndWait();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void addGas_station(ActionEvent event) {
        try {
            // Получаем значения из полей ввода
            String nameGas = textNamegas.getText();
            String addressGas = textAddress_gas.getText();
            String gasGradeGas = textGas_grade_gas.getValue(); // Получаем выбранное значение из ChoiceBox
    
            // Вызываем метод для вставки новой записи в таблицу Gas_station
            db.insertGasStation(nameGas, addressGas, gasGradeGas);
    
            // Обновляем таблицу Gas_station после добавления записи
            GasTable.setItems(FXCollections.observableArrayList(db.selectAllGasStations()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    

    @FXML
    void deleteGas_station(ActionEvent event) {
        Gas_station selectedGasStation = GasTable.getSelectionModel().getSelectedItem();
    
        if (selectedGasStation != null) {
            int idToDelete = selectedGasStation.getId_gas();
    
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение удаления");
            alert.setHeaderText(null);
            alert.setContentText("Вы уверены, что хотите удалить запись с ID " + idToDelete + "?");
    
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    db.deleteGasStationById(idToDelete);
                    updateAllTables(); // Обновить таблицы после удаления
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // Пользователь не выбрал запись для удаления, можешь добавить уведомление
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Выберите запись");
            alert.setHeaderText(null);
            alert.setContentText("Пожалуйста, выберите запись для удаления.");
            alert.showAndWait();
        }
    }
    
    @FXML
    void editGas_station(ActionEvent event) {
        Gas_station selectedGasStation = GasTable.getSelectionModel().getSelectedItem();
    
        if (selectedGasStation != null) {
            int idToUpdate = selectedGasStation.getId_gas();
    
            // Создаем диалоговое окно редактирования
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Редактирование Автозаправки");
            dialog.setHeaderText(null);
    
            // Создаем кнопки "Применить" и "Отмена"
            ButtonType applyButtonType = new ButtonType("Применить", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(applyButtonType, ButtonType.CANCEL);
    
            // Создаем поля ввода для названия и адреса
            TextField nameField = new TextField(selectedGasStation.getName_gas());
            TextField addressField = new TextField(selectedGasStation.getAddress_gas());
    
            // Создаем ChoiceBox для выбора gas_grade
            ChoiceBox<String> gasGradeChoiceBox = new ChoiceBox<>();
            gasGradeChoiceBox.setItems(GasSort);
            gasGradeChoiceBox.setValue(selectedGasStation.getGas_grade_gas());
    
            // Добавляем поля ввода и метки в диалоговое окно
            GridPane grid = new GridPane();
            grid.add(new Label("Название:"), 0, 0);
            grid.add(nameField, 1, 0);
            grid.add(new Label("Адрес:"), 0, 1);
            grid.add(addressField, 1, 1);
            grid.add(new Label("Тип топлива:"), 0, 2);
            grid.add(gasGradeChoiceBox, 1, 2);
            dialog.getDialogPane().setContent(grid);
    
            // Привязываем кнопку "Применить" к условиям
            Node applyButton = dialog.getDialogPane().lookupButton(applyButtonType);
            applyButton.setDisable(true);
    
            // Слушаем изменения в полях ввода и включаем кнопку "Применить"
            nameField.textProperty().addListener((observable, oldValue, newValue) -> {
                applyButton.setDisable(newValue.trim().isEmpty());
            });
    
            // Настройка действий при нажатии "Применить"
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == applyButtonType) {
                    return new javafx.util.Pair<>(nameField.getText(), addressField.getText());
                }
                return null;
            });
    
            // Отображение диалогового окна и обработка результатов
            Optional<javafx.util.Pair<String, String>> result = dialog.showAndWait();
            result.ifPresent(pair -> {
                try {
                    // Вызываем метод обновления с новыми значениями
                    db.updateGasStation(idToUpdate, pair.getKey(), pair.getValue(), gasGradeChoiceBox.getValue());
                    updateAllTables(); // Обновляем таблицы после редактирования
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        } else {
            // Пользователь не выбрал запись для редактирования, можешь добавить уведомление
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Выберите запись");
            alert.setHeaderText(null);
            alert.setContentText("Пожалуйста, выберите запись для редактирования.");
            alert.showAndWait();
        }
    }
    

    @FXML
    void exportGas_station(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Выберите файл для экспорта");
            FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("Текстовый файл (*.txt)", "*.txt");
            FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("CSV файл (*.csv)", "*.csv");
            FileChooser.ExtensionFilter excelFilter = new FileChooser.ExtensionFilter("Excel файл (*.xlsx)", "*.xlsx");
            fileChooser.getExtensionFilters().addAll(txtFilter, csvFilter, excelFilter);
    
            File file = fileChooser.showSaveDialog(null);
    
            if (file != null) {
                String filePath = file.getAbsolutePath();
    
                // Передаем путь к файлу и расширение в метод экспорта
                DBfunctional.exportGasStationTableToFile(filePath);
    
                // Можешь добавить уведомление об успешном экспорте
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Экспорт завершен");
                alert.setHeaderText(null);
                alert.setContentText("Данные успешно экспортированы в файл: " + filePath);
                alert.showAndWait();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addAuto(ActionEvent event) {
        try {
            // Получаем значения из полей ввода
            String firm = textFirmAuto.getText();
            String brand = textBrandAuto.getText();
            String licensePlate = textlicense_plate_auto.getText();
            String gasGrade = textGas_grade_auto.getValue(); // Предполагается, что ты используешь ChoiceBox для выбора градации топлива
    
            // Вызываем метод для вставки новой записи в таблицу Auto
            db.insertAuto(firm, brand, licensePlate, gasGrade);
    
            // Обновляем таблицу Auto после добавления записи
            AutoTable.setItems(FXCollections.observableArrayList(db.selectAllAutos()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    
    @FXML
    void deleteAuto(ActionEvent event) {
        Auto selectedAuto = AutoTable.getSelectionModel().getSelectedItem();
    
        if (selectedAuto != null) {
            int idToDelete = selectedAuto.getId_auto();
    
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение удаления");
            alert.setHeaderText(null);
            alert.setContentText("Вы уверены, что хотите удалить запись с ID " + idToDelete + "?");
    
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    db.deleteAutoById(idToDelete);
                    updateAllTables(); // Обновить таблицы после удаления
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // Пользователь не выбрал запись для удаления, можешь добавить уведомление
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Выберите запись");
            alert.setHeaderText(null);
            alert.setContentText("Пожалуйста, выберите запись для удаления.");
            alert.showAndWait();
        }
    }

    @FXML
    void editAuto(ActionEvent event) {
        Auto selectedAuto = AutoTable.getSelectionModel().getSelectedItem();
    
        if (selectedAuto != null) {
            int idToUpdate = selectedAuto.getId_auto();
    
            // Создаем диалоговое окно редактирования
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Редактирование Автомобиля");
            dialog.setHeaderText(null);
    
            // Создаем кнопки "Применить" и "Отмена"
            ButtonType applyButtonType = new ButtonType("Применить", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(applyButtonType, ButtonType.CANCEL);
    
            // Создаем поля ввода для фирмы, марки и гос. номера
            TextField firmField = new TextField(selectedAuto.getFirm_auto());
            TextField brandField = new TextField(selectedAuto.getBrand_auto());
            TextField licensePlateField = new TextField(selectedAuto.getLicense_plate_auto());
    
            // Создаем ChoiceBox для выбора gas_grade
            ChoiceBox<String> gasGradeChoiceBox = new ChoiceBox<>();
            gasGradeChoiceBox.setItems(GasSort);
            gasGradeChoiceBox.setValue(selectedAuto.getGas_grade_auto());
    
            // Добавляем поля ввода и метки в диалоговое окно
            GridPane grid = new GridPane();
            grid.add(new Label("Фирма:"), 0, 0);
            grid.add(firmField, 1, 0);
            grid.add(new Label("Марка:"), 0, 1);
            grid.add(brandField, 1, 1);
            grid.add(new Label("Гос. номер:"), 0, 2);
            grid.add(licensePlateField, 1, 2);
            grid.add(new Label("Тип топлива:"), 0, 3);
            grid.add(gasGradeChoiceBox, 1, 3);
            dialog.getDialogPane().setContent(grid);
    
            // Привязываем кнопку "Применить" к условиям
            Node applyButton = dialog.getDialogPane().lookupButton(applyButtonType);
            applyButton.setDisable(true);
    
            // Слушаем изменения в полях ввода и включаем кнопку "Применить"
            firmField.textProperty().addListener((observable, oldValue, newValue) -> {
                applyButton.setDisable(newValue.trim().isEmpty());
            });
    
            // Настройка действий при нажатии "Применить"
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == applyButtonType) {
                    return new Pair<>(firmField.getText(), brandField.getText());
                }
                return null;
            });
    
            // Отображение диалогового окна и обработка результатов
            Optional<Pair<String, String>> result = dialog.showAndWait();
            result.ifPresent(pair -> {
                try {
                    // Вызываем метод обновления с новыми значениями
                    db.updateAuto(idToUpdate, pair.getKey(), pair.getValue(), licensePlateField.getText(), gasGradeChoiceBox.getValue());
                    updateAllTables(); // Обновляем таблицы после редактирования
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        } else {
            // Пользователь не выбрал запись для редактирования, можешь добавить уведомление
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Выберите запись");
            alert.setHeaderText(null);
            alert.setContentText("Пожалуйста, выберите запись для редактирования.");
            alert.showAndWait();
        }
    }
    

    @FXML
    void exportAuto(ActionEvent event) {
            try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Выберите файл для экспорта");
            FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("Текстовый файл (*.txt)", "*.txt");
            FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("CSV файл (*.csv)", "*.csv");
            FileChooser.ExtensionFilter excelFilter = new FileChooser.ExtensionFilter("Excel файл (*.xlsx)", "*.xlsx");
            fileChooser.getExtensionFilters().addAll(txtFilter, csvFilter, excelFilter);
    
            File file = fileChooser.showSaveDialog(null);
    
            if (file != null) {
                String filePath = file.getAbsolutePath();
    
                // Передаем путь к файлу и расширение в метод экспорта
                DBfunctional.exportAutoTableToFile(filePath);
    
                // Можешь добавить уведомление об успешном экспорте
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Экспорт завершен");
                alert.setHeaderText(null);
                alert.setContentText("Данные успешно экспортированы в файл: " + filePath);
                alert.showAndWait();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}