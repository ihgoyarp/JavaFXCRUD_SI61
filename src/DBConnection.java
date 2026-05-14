import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class untuk mengelola koneksi ke database MySQL.
 * Gunakan DBConnection.getConnection() di setiap class yang butuh akses DB.
 */
public class DBConnection {
    private static final String URL  = "jdbc:mysql://localhost:3306/oopjava";
    private static final String USER = "root";
    private static final String PASS = ""; // kosong jika tanpa password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
