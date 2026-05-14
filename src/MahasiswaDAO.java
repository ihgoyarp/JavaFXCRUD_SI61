import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) – semua operasi SQL untuk tabel mahasiswa.
 *
 * Perubahan dari versi MySQL:
 * - Konstruktor memanggil inisialisasi tabel secara otomatis.
 * - Tabel 'mahasiswa' dibuat jika belum ada (CREATE TABLE IF NOT EXISTS).
 * - Tidak ada perubahan pada method tambah, getAll, update, hapus.
 */
public class MahasiswaDAO {

    // Dipanggil saat aplikasi pertama kali dijalankan
    public MahasiswaDAO() {
        inisialisasiTabel();
    }

    /**
     * Buat tabel mahasiswa jika belum ada.
     * Dijalankan otomatis — user tidak perlu setup database manual.
     */
    private void inisialisasiTabel() {
        String sql = """
            CREATE TABLE IF NOT EXISTS mahasiswa (
                id      INTEGER PRIMARY KEY AUTOINCREMENT,
                nim     TEXT NOT NULL,
                nama    TEXT NOT NULL,
                jurusan TEXT NOT NULL
            )
            """;
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            System.err.println("Gagal inisialisasi tabel: " + e.getMessage());
        }
    }

    // ── CREATE ──────────────────────────────────────────────────────────────
    public void tambah(Mahasiswa m) throws SQLException {
        String sql = "INSERT INTO mahasiswa (nim, nama, jurusan) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getNim());
            ps.setString(2, m.getNama());
            ps.setString(3, m.getJurusan());
            ps.executeUpdate();
        }
    }

    // ── READ ALL ─────────────────────────────────────────────────────────────
    public List<Mahasiswa> getAll() throws SQLException {
        List<Mahasiswa> list = new ArrayList<>();
        String sql = "SELECT * FROM mahasiswa ORDER BY id";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Mahasiswa(
                    rs.getInt("id"),
                    rs.getString("nim"),
                    rs.getString("nama"),
                    rs.getString("jurusan")
                ));
            }
        }
        return list;
    }

    // ── UPDATE ───────────────────────────────────────────────────────────────
    public void update(Mahasiswa m) throws SQLException {
        String sql = "UPDATE mahasiswa SET nim=?, nama=?, jurusan=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getNim());
            ps.setString(2, m.getNama());
            ps.setString(3, m.getJurusan());
            ps.setInt(4, m.getId());
            ps.executeUpdate();
        }
    }

    // ── DELETE ───────────────────────────────────────────────────────────────
    public void hapus(int id) throws SQLException {
        String sql = "DELETE FROM mahasiswa WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
