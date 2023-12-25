package database;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import objects.*;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class DBfunctional {
    public static java.sql.Connection connect() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jfxkurs", "root", "root");
		System.out.println("connection created");
		
		return conn;
	}

    //авторизация и регистрация

    public boolean isPasswordValid(String password) {
    // Проверка длины пароля
    if (password.length() < 4 || password.length() > 16) {
        return false;
    }
    
    // Проверка наличия запрещенных символов
    if (password.matches(".*[\\*\\&\\{\\}\\|\\+].*")) {
        return false;
    }
    
    // Проверка наличия заглавных букв и цифр
    boolean hasUpperCase = false;
    boolean hasDigit = false;
    for (char ch : password.toCharArray()) {
        if (Character.isUpperCase(ch)) {
            hasUpperCase = true;
        } else if (Character.isDigit(ch)) {
            hasDigit = true;
        }
    }
    
        return hasUpperCase && hasDigit;
    }

    public boolean userExists(String login_user) throws ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM users WHERE login_user = '" + login_user + "'";
    
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    
        return false;
    }

    public boolean register(String login_user, String password_user) throws ClassNotFoundException{
        if (userExists(login_user)) {
            JOptionPane.showMessageDialog(null, "Пользователь с таким именем уже существует", "Предупреждение", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    
        if (!isPasswordValid(password_user)) {
            JOptionPane.showMessageDialog(null, "Пароль содержит некорректные символы", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String sql = "INSERT INTO users (login_user, password_user) VALUES (?,? )";

        try (Connection conn = DBfunctional.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, login_user);
            pstmt.setString(2, password_user);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        JOptionPane.showMessageDialog(null, "Регистрация прошла успешно!", "Успех", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }
	

    public boolean authenticate(String login_user, String password_user) throws ClassNotFoundException {
        String sql = "SELECT * FROM users WHERE login_user = ? AND password_user = ?";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            pstmt.setString(1, login_user);
            pstmt.setString(2, password_user);
    
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Возвращает true, если найдена запись с указанным логином и паролем
            }
    
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    
        return false;
    }
    
////////////////////////////////////////////функции для таблиц

//////////////////////вывод данных таблиц

    public ObservableList<Auto> selectAllAutos() throws ClassNotFoundException {
        ObservableList<Auto> autos = FXCollections.observableArrayList();
        String sql = "SELECT * FROM auto";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                Auto auto = new Auto(
                		rs.getInt("id_auto"),
                        rs.getString("firm_auto"),
                        rs.getString("brand_auto"),
                        rs.getString("license_plate_auto"),
                        rs.getString("gas_grade_auto")
                        );

                autos.add(auto);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return autos;
    }

    public ObservableList<Autobase> selectAllBases() throws ClassNotFoundException {
        ObservableList<Autobase> autobases = FXCollections.observableArrayList();
        String sql = "SELECT * FROM autobase";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                Autobase autobase = new Autobase(
                		rs.getInt("id_autobase"),
                        rs.getString("number_autobase"),
                        rs.getString("address_autobase"),
                        rs.getInt("id_auto"),
                        rs.getInt("id_gas")
                        );

                autobases.add(autobase);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return autobases;
    }

    public ObservableList<Gas_station> selectAllGasStations() throws ClassNotFoundException {
        ObservableList<Gas_station> gas_stations = FXCollections.observableArrayList();
        String sql = "SELECT * FROM gas_station";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
            	Gas_station gas_station = new Gas_station(
                		rs.getInt("id_gas"),
                        rs.getString("name_gas"),
                        rs.getString("address_gas"),
                        rs.getString("gas_grade_gas")
                        );

            	gas_stations.add(gas_station);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return gas_stations;
    }
    public List<Integer> getAllGasStationIds() {
        List<Integer> gasStationIds = new ArrayList<>();
        String sql = "SELECT id_gas FROM gas_station";
    
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
    
            while (rs.next()) {
                int id = rs.getInt("id_gas");
                gasStationIds.add(id);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    
        return gasStationIds;
    }
    
    public List<Integer> getAllAutoIds() {
        List<Integer> autoIds = new ArrayList<>();
        String sql = "SELECT id_auto FROM auto";
    
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
    
            while (rs.next()) {
                int id = rs.getInt("id_auto");
                autoIds.add(id);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    
        return autoIds;
    }
        


//////////////////////добавление данных

public void insertAuto(String firm_auto, String brand_auto, String license_plate_auto, String gas_grade_auto) throws ClassNotFoundException {
    String sql = "INSERT INTO auto (firm_auto, brand_auto, license_plate_auto, gas_grade_auto) VALUES (?,?,?,?)";

    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, firm_auto);
        pstmt.setString(2, brand_auto);
        pstmt.setString(3, license_plate_auto);
        pstmt.setString(4, gas_grade_auto);
        pstmt.executeUpdate();
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}

public static void insertAutobase(String number_autobase, String address_autobase, Integer id_auto, Integer id_gas) throws ClassNotFoundException {
    String sql = "INSERT INTO autobase (number_autobase, address_autobase, id_auto, id_gas) VALUES (?,?,?,?)";

    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, number_autobase);
        pstmt.setString(2, address_autobase);
        pstmt.setInt(3, id_auto);
        pstmt.setInt(4, id_gas);
        pstmt.executeUpdate();
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}

public void insertGasStation(String name_gas, String address_gas, String gas_grade_gas) throws ClassNotFoundException {
    String sql = "INSERT INTO gas_station (name_gas, address_gas, gas_grade_gas) VALUES (?,?,?)";

    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, name_gas);
        pstmt.setString(2, address_gas);
        pstmt.setString(3, gas_grade_gas);
        pstmt.executeUpdate();
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}

//////////////////////удаление данных

public void deleteAutoById(int id_auto) throws ClassNotFoundException {
    String sql = "DELETE FROM auto WHERE id_auto = ?";

    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        // set the corresponding param
        pstmt.setInt(1, id_auto);
        // execute the delete statement
        pstmt.executeUpdate();

    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}

public void deleteAutobaseById(int id_autobase) throws ClassNotFoundException {
    String sql = "DELETE FROM autobase WHERE id_autobase = ?";

    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        // set the corresponding param
        pstmt.setInt(1, id_autobase);
        // execute the delete statement
        pstmt.executeUpdate();

    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}
public void deleteGasStationById(int id_gas) throws ClassNotFoundException {
    String sql = "DELETE FROM gas_station WHERE id_gas = ?";

    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        // set the corresponding param
        pstmt.setInt(1, id_gas);
        // execute the delete statement
        pstmt.executeUpdate();

    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}

//////////////////////редактирование данных

public void updateAuto(int id_auto, String firm_auto, String brand_auto, String license_plate_auto, String gas_grade_auto) throws ClassNotFoundException {
    String sql = "UPDATE auto SET id_auto = ?, firm_auto = ?, brand_auto = ?, license_plate_auto = ?, gas_grade_auto = ? WHERE id_auto = ?";

    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        // set the corresponding params
        pstmt.setInt(1, id_auto);
        pstmt.setString(2, firm_auto);
        pstmt.setString(3, brand_auto);
        pstmt.setString(4, license_plate_auto);
        pstmt.setString(5, gas_grade_auto);
        pstmt.setInt(6, id_auto); // assuming id_auto is the primary key
        // update
        pstmt.executeUpdate();
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}
public void updateAutobase(int id_autobase, String number_autobase, String address_autobase, int id_auto, int id_gas) throws ClassNotFoundException {
    String sql = "UPDATE autobase SET number_autobase = ?, address_autobase = ?, id_auto = ?, id_gas = ? WHERE id_autobase = ?";

    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        // set the corresponding params
        pstmt.setString(1, number_autobase);
        pstmt.setString(2, address_autobase);
        pstmt.setInt(3, id_auto);
        pstmt.setInt(4, id_gas);
        pstmt.setInt(5, id_autobase);

        // update
        pstmt.executeUpdate();
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}

public void updateGasStation(int id_gas, String name_gas, String address_gas, String gas_grade_gas) throws ClassNotFoundException {
    String sql = "UPDATE gas_station SET name_gas = ?, address_gas = ?, gas_grade_gas = ? WHERE id_gas = ?";

    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        // set the corresponding params
        pstmt.setString(1, name_gas);
        pstmt.setString(2, address_gas);
        pstmt.setString(3, gas_grade_gas);
        pstmt.setInt(4, id_gas);

        // update
        pstmt.executeUpdate();
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}

///////вывод в текстовой файл таблиц

    public static void exportAutoTableToFile(String filePath) throws ClassNotFoundException {
        try {
            // Открываем файл для записи
            FileWriter writer = new FileWriter(filePath);

            // Выполняем SQL-запрос для выборки данных из таблицы auto
            String sql = "SELECT * FROM auto";

            try (Connection conn = connect();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                // Проходимся по результатам запроса и записываем в файл
                while (rs.next()) {
                    int id_auto = rs.getInt("id_auto");
                    String firm_auto = rs.getString("firm_auto");
                    String brand_auto = rs.getString("brand_auto");
                    String license_plate_auto = rs.getString("license_plate_auto");
                    String gas_grade_auto = rs.getString("gas_grade_auto");

                    // Записываем данные в файл
                    writer.write(String.format("%d, %s, %s, %s, %s%n", id_auto, firm_auto, brand_auto, license_plate_auto, gas_grade_auto));
                }

                System.out.println("Данные сохранены в: " + filePath);

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            // Закрываем файл после записи
            writer.close();

        } catch (IOException e) {
            System.out.println("Ошибка записи файла: " + e.getMessage());
        }
    }
    
    public static void exportAutobaseTableToFile(String filePath) throws ClassNotFoundException {
        try {
            // Открываем файл для записи
            FileWriter writer = new FileWriter(filePath);

            // Выполняем SQL-запрос для выборки данных из таблицы autobase
            String sql = "SELECT * FROM autobase";

            try (Connection conn = connect();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                // Проходимся по результатам запроса и записываем в файл
                while (rs.next()) {
                    int id_autobase = rs.getInt("id_autobase");
                    String number_autobase = rs.getString("number_autobase");
                    String address_autobase = rs.getString("address_autobase");
                    int id_auto = rs.getInt("id_auto");
                    int id_gas = rs.getInt("id_gas");

                    // Записываем данные в файл
                    writer.write(String.format("%d, %s, %s, %d, %d%n", id_autobase, number_autobase, address_autobase, id_auto, id_gas));
                }

                System.out.println("Данные сохранены в: " + filePath);

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            // Закрываем файл после записи
            writer.close();

        } catch (IOException e) {
            System.out.println("Ошибка записи файла: " + e.getMessage());
        }
    }

    public static void exportGasStationTableToFile(String filePath) throws ClassNotFoundException {
        try {
            // Открываем файл для записи
            FileWriter writer = new FileWriter(filePath);

            // Выполняем SQL-запрос для выборки данных из таблицы gas_station
            String sql = "SELECT * FROM gas_station";

            try (Connection conn = connect();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                // Проходимся по результатам запроса и записываем в файл
                while (rs.next()) {
                    int id_gas = rs.getInt("id_gas");
                    String name_gas = rs.getString("name_gas");
                    String address_gas = rs.getString("address_gas");
                    String gas_grade_gas = rs.getString("gas_grade_gas");

                    // Записываем данные в файл
                    writer.write(String.format("%d, %s, %s, %s%n", id_gas, name_gas, address_gas, gas_grade_gas));
                }

                System.out.println("Данные сохранены в: " + filePath);

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            // Закрываем файл после записи
            writer.close();

        } catch (IOException e) {
            System.out.println("Ошибка записи файла: " + e.getMessage());
        }
    }

/////////////////////сортировка таблиц

public ObservableList<Autobase> orderByAutobase(String orderColumn) throws ClassNotFoundException {
    ObservableList<Autobase> autobases = FXCollections.observableArrayList();
    String sql = "SELECT * FROM autobase ORDER BY " + orderColumn;

    try (Connection conn = connect();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        // loop through the result set
        while (rs.next()) {
            Autobase autobase = new Autobase(
                    rs.getInt("id_autobase"),
                    rs.getString("number_autobase"),
                    rs.getString("address_autobase"),
                    rs.getInt("id_auto"),
                    rs.getInt("id_gas")
            );

            autobases.add(autobase);
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return autobases;
}

    public ObservableList<Gas_station> orderByGasStation(String orderColumn) throws ClassNotFoundException {
        ObservableList<Gas_station> gasStations = FXCollections.observableArrayList();
        String sql = "SELECT * FROM gas_station ORDER BY " + orderColumn;

        try (Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                Gas_station gas_station = new Gas_station(
                        rs.getInt("id_gas"),
                        rs.getString("name_gas"),
                        rs.getString("address_gas"),
                        rs.getString("gas_grade_gas")
                );

                gasStations.add(gas_station);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return gasStations;
    }

    public ObservableList<Auto> orderByAuto(String orderColumn) throws ClassNotFoundException {
        ObservableList<Auto> autos = FXCollections.observableArrayList();
        String sql = "SELECT * FROM auto ORDER BY " + orderColumn;

        try (Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                Auto auto = new Auto(
                        rs.getInt("id_auto"),
                        rs.getString("firm_auto"),
                        rs.getString("brand_auto"),
                        rs.getString("license_plate_auto"),
                        rs.getString("gas_grade_auto")
                );

                autos.add(auto);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return autos;
}



}
