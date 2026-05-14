import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class untuk mengelola koneksi ke database SQLite.
 *
 * File database disimpan di folder 'data/' yang letaknya
 * selalu relatif terhadap working directory saat aplikasi dijalankan,
 * sehingga berjalan konsisten di Mac, Linux, maupun Windows.
 */
public class DBConnection {

    private static final String URL;

    static {
        // Ambil working directory saat aplikasi dijalankan
        String workDir = System.getProperty("user.dir");
        File dataFolder = new File(workDir, "data");

        // Buat folder 'data/' jika belum ada
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        File dbFile = new File(dataFolder, "mahasiswa.db");
        URL = "jdbc:sqlite:" + dbFile.getAbsolutePath();

        // Tampilkan path database di terminal (membantu debugging)
        System.out.println("Database: " + dbFile.getAbsolutePath());
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}