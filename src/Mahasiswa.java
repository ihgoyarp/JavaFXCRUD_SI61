/**
 * Model class yang merepresentasikan satu baris data mahasiswa.
 * Properti disesuaikan dengan kolom di tabel `mahasiswa`.
 */
public class Mahasiswa {
    private int    id;
    private String nim;
    private String nama;
    private String jurusan;

    public Mahasiswa(int id, String nim, String nama, String jurusan) {
        this.id      = id;
        this.nim     = nim;
        this.nama    = nama;
        this.jurusan = jurusan;
    }

    // --- Getter ---
    public int    getId()      { return id; }
    public String getNim()     { return nim; }
    public String getNama()    { return nama; }
    public String getJurusan() { return jurusan; }

    // --- Setter ---
    public void setId(int id)           { this.id      = id; }
    public void setNim(String nim)      { this.nim     = nim; }
    public void setNama(String nama)    { this.nama    = nama; }
    public void setJurusan(String j)    { this.jurusan = j; }

    @Override
    public String toString() {
        return nama + " (" + nim + ")";
    }
}
