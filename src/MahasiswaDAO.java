import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) – semua operasi SQL untuk tabel mahasiswa.
 * Pisahkan logika database dari logika tampilan (UI).
 */
public class MahasiswaDAO {

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
             Statement   st   = conn.createStatement();
             ResultSet   rs   = st.executeQuery(sql)) {
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
