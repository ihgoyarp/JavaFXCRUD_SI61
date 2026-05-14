import java.sql.Connection;

public class TestKoneksi {
    public static void main(String[] args) {
        try {
            Connection conn = DBConnection.getConnection();
            if (conn != null) {
                System.out.println("Koneksi BERHASIL!");
                System.out.println("Database: " + conn.getCatalog());
                conn.close();
            }
        } catch (Exception e) {
            System.out.println("Koneksi GAGAL: " + e.getMessage());
        }

    }
}