package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Koneksi {
    private static Connection conn;

    public static Connection getKoneksi(){
        String host = "jdbc:mysql://localhost/database_bookingapp",
               user = "root",
               pass = "";
        try {
            conn = (Connection) DriverManager.getConnection(host, user, pass);
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
        return conn;
    }
    public static Connection getConn() {
        return conn;
    }
    public static void setConn(Connection conn) {
        Koneksi.conn = conn;
    }
}