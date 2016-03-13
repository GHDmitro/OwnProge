package HomeWorks.Task1;


import java.sql.*;
import java.util.Scanner;

/**
 * Created by macbookair on 04.03.16.
 */
public class Mainsql {
    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3307/mydbpros";
    private static final String ROOT = "root";
    private static final String PASSWORD = "root";
    static Connection conn;


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(DB_CONNECTION, ROOT, PASSWORD);


        Scanner scanner = new Scanner(System.in);
        String n = scanner.next();
        addRegion(n);

//        был ли добавлен герион
        if (findRegionByName(n)) {
            System.out.println("Регион был добавлен");
        }

//        добаляем квартиры в список
        addFlate(n,"Kaddr1", 1234, 3 , 203.3); //
        addFlate(n,"Addr2", 12345, 3 , 203.3);
        addFlate(n,"Addr3", 1234, 3 , 203.3); //
        addFlate(n,"Addr4", 123454, 4 , 223.3);
        addFlate(n,"Addr1", 1234, 4 , 203.3);

        String str[] = findAdressesByParam(n, 1234, 3, 203.3);

        //выводит все адреса, по заданым параметрам сортируя по названию адреса
        for (int i = 0; i < str.length; i++) {
            System.out.println(i+".->"+str[i]);
        }

        allFlatesToUpperCost();
        allInthisRegion(n);
        conn.close();
    }


    private static void addRegion(String name) throws SQLException {

        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO Region (name) VALUES (?)")) {
            ps.setString(1, name);
            ps.executeUpdate();
        }

    }

    private static boolean findRegionByName(String name) throws SQLException {

        try (PreparedStatement ps = conn.prepareStatement("SELECT name FROM Region WHERE name = '"+name+"';")) {

            try (ResultSet rs = ps.executeQuery()) {
                StringBuilder sb = new StringBuilder();
                while (rs.next()) {
                    sb.append(rs.getString("name")).append("=");
                }
                String[] c = sb.toString().split("=");
                return (c.length == 1) && (name.equalsIgnoreCase(c[0]));
            }
        }


    }

    private static void addFlate(String regName, String address, double cost, int roomAmount, double square) throws SQLException {

        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO Flate(address, cost, roomAmount, square, Region_name) " +
                "VALUES (?,?,?,?,?) ")) {

            ps.setString(1, address);
            ps.setDouble(2, cost);
            ps.setInt(3, roomAmount);
            ps.setDouble(4, square);
            ps.setString(5, regName);
            ps.executeUpdate();
        }
    }

    private static String[] findAdressesByParam(String regName, double cost, int roomAmount, double square) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT address FROM Flate f , Region r where f.Region_name='" + regName + "'AND " +
                "f.Region_name=r.name AND f.cost=" + cost + " AND f.roomAmount=" + roomAmount + " AND f.square=" + square + " ORDER by address; ")) {

            StringBuilder sb = new StringBuilder();
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    sb.append(rs.getString("address")).append("=");
                }
                if (sb.toString().equalsIgnoreCase(null)){
                    return null;
                }
                String str[] = sb.toString().split("=");
                return str;
            }

        }

    }
    private static void allFlatesToUpperCost() throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement("SELECT address FROM Flate ORDER by cost;")) {
            try (ResultSet s = statement.executeQuery()) {
                ResultSetMetaData sc = statement.getMetaData();
                while (s.next()) {
                    for (int i = 1; i <= sc.getColumnCount(); i++) {
                        System.out.print(s.getString(i) + "\t\t");
                    }
                    System.out.println();
                }
            }
        }
    }

    private static void allInthisRegion(String name) throws SQLException {

       try( PreparedStatement statement = conn.prepareStatement("SELECT * FROM Flate f, Region r WHERE f.Region_name=r.name AND " +
               "f.Region_name = '"+name+"'")) {
           try(ResultSet s = statement.executeQuery()) {
               ResultSetMetaData sc = statement.getMetaData();
               while (s.next()) {
                   for (int i = 2; i <= sc.getColumnCount(); i++) {
                       System.out.print(s.getString(i) + "\t\t");
                   }
                   System.out.println();
               }
           }
       }
    }
}
