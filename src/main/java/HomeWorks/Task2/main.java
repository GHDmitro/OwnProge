package HomeWorks.Task2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by macbookair on 11.03.16.
 */
public class main {
    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3307/dbBooking";
    private static final String ROOT = "root";
    private static final String PASSWORD = "root";
    static Connection conn;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(DB_CONNECTION, ROOT, PASSWORD);

    }

    private static void addClient(String name, String surname, int age) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO Clients (name, surname, age) VALUES (?,?,?)")) {
            ps.setString(1,name);
            ps.setString(2,surname);
            ps.setInt(3,age);
            ps.executeUpdate();
        }catch (SQLException e){
            conn.rollback();
        }
    }

    private static void addGoods(String name, int capacity) throws SQLException {
        try(PreparedStatement ps = conn.prepareStatement("INSERT INTO Goods (name, capacity) VALUES (?,?)")){
            ps.setString(1,name);
            ps.setInt(2,capacity);
            ps.executeUpdate();
        }catch (SQLException e){
            conn.rollback();
        }
    }

    private static void addBooking(String codeOfBooking, int clientPass) throws SQLException {
        try(PreparedStatement ps = conn.prepareStatement("INSERT into Booking (codeOfBooking, clientPass)VALUES (?,?)")){
            ps.setString(1, codeOfBooking);
            ps.setInt(2,clientPass);
        }catch (SQLException e){
            conn.rollback();
        }

    }

}




















