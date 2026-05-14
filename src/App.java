import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Entry point aplikasi JavaFX – CRUD Mahasiswa.
 * Tidak menggunakan Maven; cukup compile & run secara manual (lihat README).
 */
public class App extends Application {

    // --- DAO & Data ---
    private final MahasiswaDAO dao  = new MahasiswaDAO();
    private final ObservableList<Mahasiswa> dataList = FXCollections.observableArrayList();

    // --- Komponen Form ---
    private final TextField tfNim     = new TextField();
    private final TextField tfNama    = new TextField();
    private final TextField tfJurusan = new TextField();

    // --- Tabel ---
    private final TableView<Mahasiswa> table = new TableView<>();

    // Menyimpan ID mahasiswa yang sedang diedit (0 = mode tambah baru)
    private int editId = 0;

    // ─────────────────────────────────────────────────────────────────────────
    @Override
    public void start(Stage stage) {
        // ── Judul ──────────────────────────────────────────────────────────
        Label lblJudul = new Label("Data Mahasiswa");
        lblJudul.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // ── Form Input ─────────────────────────────────────────────────────
        tfNim.setPromptText("NIM");
        tfNama.setPromptText("Nama Lengkap");
        tfJurusan.setPromptText("Jurusan");

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(8);
        form.addRow(0, new Label("NIM     :"), tfNim);
        form.addRow(1, new Label("Nama    :"), tfNama);
        form.addRow(2, new Label("Jurusan :"), tfJurusan);

        // ── Tombol ─────────────────────────────────────────────────────────
        Button btnSimpan = new Button("Simpan");
        Button btnHapus  = new Button("Hapus");
        Button btnBatal  = new Button("Batal");

        btnSimpan.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-min-width: 80px;");
        btnHapus.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-min-width: 80px;");
        btnBatal.setStyle("-fx-min-width: 80px;");

        HBox tombol = new HBox(10, btnSimpan, btnHapus, btnBatal);
        tombol.setAlignment(Pos.CENTER_LEFT);

        VBox kiriPanel = new VBox(12, form, tombol);
        kiriPanel.setPadding(new Insets(10));
        kiriPanel.setPrefWidth(320);

        // ── Tabel ──────────────────────────────────────────────────────────
        TableColumn<Mahasiswa, Integer> colId      = new TableColumn<>("ID");
        TableColumn<Mahasiswa, String>  colNim     = new TableColumn<>("NIM");
        TableColumn<Mahasiswa, String>  colNama    = new TableColumn<>("Nama");
        TableColumn<Mahasiswa, String>  colJurusan = new TableColumn<>("Jurusan");

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNim.setCellValueFactory(new PropertyValueFactory<>("nim"));
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colJurusan.setCellValueFactory(new PropertyValueFactory<>("jurusan"));

        colId.setPrefWidth(50);
        colNim.setPrefWidth(110);
        colNama.setPrefWidth(160);
        colJurusan.setPrefWidth(150);

        table.getColumns().addAll(colId, colNim, colNama, colJurusan);
        table.setItems(dataList);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // ── Layout Utama ───────────────────────────────────────────────────
        HBox konten = new HBox(16, kiriPanel, table);
        konten.setPadding(new Insets(10));
        HBox.setHgrow(table, Priority.ALWAYS);

        VBox root = new VBox(10, lblJudul, konten);
        root.setPadding(new Insets(14));

        // ─── Event Handler ─────────────────────────────────────────────────

        // Klik baris tabel → isi form untuk edit
        table.getSelectionModel().selectedItemProperty().addListener((obs, lama, baru) -> {
            if (baru != null) {
                editId = baru.getId();
                tfNim.setText(baru.getNim());
                tfNama.setText(baru.getNama());
                tfJurusan.setText(baru.getJurusan());
            }
        });

        // Simpan (Tambah ATAU Update)
        btnSimpan.setOnAction(e -> {
            String nim     = tfNim.getText().trim();
            String nama    = tfNama.getText().trim();
            String jurusan = tfJurusan.getText().trim();

            if (nim.isEmpty() || nama.isEmpty() || jurusan.isEmpty()) {
                tampilAlert(Alert.AlertType.WARNING, "Peringatan", "Semua field wajib diisi!");
                return;
            }

            try {
                if (editId == 0) {
                    // Mode TAMBAH
                    dao.tambah(new Mahasiswa(0, nim, nama, jurusan));
                    tampilAlert(Alert.AlertType.INFORMATION, "Sukses", "Mahasiswa berhasil ditambahkan.");
                } else {
                    // Mode UPDATE
                    dao.update(new Mahasiswa(editId, nim, nama, jurusan));
                    tampilAlert(Alert.AlertType.INFORMATION, "Sukses", "Data berhasil diperbarui.");
                }
                bersihkanForm();
                muatData();
            } catch (Exception ex) {
                tampilAlert(Alert.AlertType.ERROR, "Error", "Gagal menyimpan: " + ex.getMessage());
            }
        });

        // Hapus data yang dipilih
        btnHapus.setOnAction(e -> {
            Mahasiswa dipilih = table.getSelectionModel().getSelectedItem();
            if (dipilih == null) {
                tampilAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih data yang ingin dihapus!");
                return;
            }
            Alert konfirmasi = new Alert(Alert.AlertType.CONFIRMATION,
                "Hapus mahasiswa \"" + dipilih.getNama() + "\"?",
                ButtonType.YES, ButtonType.NO);
            konfirmasi.setTitle("Konfirmasi Hapus");
            konfirmasi.showAndWait().ifPresent(bt -> {
                if (bt == ButtonType.YES) {
                    try {
                        dao.hapus(dipilih.getId());
                        bersihkanForm();
                        muatData();
                        tampilAlert(Alert.AlertType.INFORMATION, "Sukses", "Data berhasil dihapus.");
                    } catch (Exception ex) {
                        tampilAlert(Alert.AlertType.ERROR, "Error", "Gagal menghapus: " + ex.getMessage());
                    }
                }
            });
        });

        // Batal / reset form
        btnBatal.setOnAction(e -> bersihkanForm());

        // ── Tampilkan Window ───────────────────────────────────────────────
        muatData();
        Scene scene = new Scene(root, 740, 420);
        stage.setTitle("Aplikasi Data Mahasiswa");
        stage.setScene(scene);
        stage.show();
    }

    // ─────────────────────────────────────────────────────────────────────────
    /** Muat ulang semua data dari database ke tabel. */
    private void muatData() {
        try {
            dataList.setAll(dao.getAll());
        } catch (Exception e) {
            tampilAlert(Alert.AlertType.ERROR, "Error DB", "Gagal memuat data: " + e.getMessage());
        }
    }

    /** Reset form & kembalikan ke mode tambah baru. */
    private void bersihkanForm() {
        editId = 0;
        tfNim.clear();
        tfNama.clear();
        tfJurusan.clear();
        table.getSelectionModel().clearSelection();
    }

    /** Helper menampilkan dialog alert. */
    private void tampilAlert(Alert.AlertType tipe, String judul, String pesan) {
        Alert alert = new Alert(tipe, pesan, ButtonType.OK);
        alert.setTitle(judul);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
