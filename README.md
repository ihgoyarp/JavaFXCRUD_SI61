# CRUD Mahasiswa – JavaFX + SQLite (Tanpa Maven)

> Aplikasi desktop CRUD data mahasiswa menggunakan JavaFX untuk tampilan
> dan SQLite sebagai database lokal. Tidak memerlukan XAMPP, MySQL, atau server apapun.
> Data tersimpan otomatis di file `data/mahasiswa.db` di dalam folder project.

---

## Struktur Folder

```
crud-mahasiswa/
├── src/
│   ├── App.java           ← UI JavaFX (tampilan + event)
│   ├── Mahasiswa.java     ← Model data (Encapsulation)
│   ├── MahasiswaDAO.java  ← Semua query SQL (Abstraction)
│   └── DBConnection.java  ← Koneksi ke SQLite (Abstraction)
├── lib/
│   ├── lib/               ← File .jar JavaFX SDK
│   ├── legal/             ← Lisensi JavaFX (otomatis dari extract)
│   └── sqlite-jdbc-3.53.1.0.jar
├── bin/                   ← Hasil compile (otomatis dibuat)
├── data/                  ← File database (otomatis dibuat saat pertama run)
│   └── mahasiswa.db
└── .vscode/
    └── settings.json
```

---

## LANGKAH 1 – Download JavaFX SDK

1. Buka: https://gluonhq.com/products/javafx/
2. Pilih versi **21 (LTS)**, sesuaikan OS kamu (Windows / Mac / Linux), tipe **SDK**
3. Extract hasil download
4. Pindahkan **isi folder** hasil extract langsung ke `lib/`
5. Pastikan di dalam `lib/lib/` sudah ada file-file `.jar` JavaFX

> Setelah extract, di dalam `lib/` akan ada 2 folder: `legal/` dan `lib/` — itu yang dipakai.

---

## LANGKAH 2 – Download SQLite JDBC

1. Buka: https://github.com/xerial/sqlite-jdbc/releases
2. Cari versi terbaru, download file **`sqlite-jdbc-3.53.1.0.jar`**
   - Pilih yang **tanpa** embel-embel (`-natives`, `-sources`, `-android`, dll)
   - Cukup file `sqlite-jdbc-3.53.1.0.jar` saja
3. Taruh di folder `lib/`

---

## LANGKAH 3 – Konfigurasi VS Code

Buat file `.vscode/settings.json` dengan isi:

```json
{
    "java.project.sourcePaths": ["src"],
    "java.project.outputPath": "bin",
    "java.project.referencedLibraries": [
        "lib/*.jar",
        "lib/lib/*.jar"
    ]
}
```

---

## LANGKAH 4 – Compile

Jalankan dari folder root **`crud-mahasiswa/`** (bukan dari dalam `src/`):

### Mac / Linux
```bash
javac --module-path lib/lib \
      --add-modules javafx.controls \
      -cp "lib/sqlite-jdbc-3.53.1.0.jar" \
      -d bin \
      src/*.java
```

### Windows (CMD)
```bat
javac --module-path lib\lib ^
      --add-modules javafx.controls ^
      -cp "lib\sqlite-jdbc-3.53.1.0.jar" ^
      -d bin ^
      src\*.java
```

> Pesan `Note: uses deprecated API` dan `unchecked operations` adalah **peringatan biasa, bukan error**.
> Compile tetap berhasil dan aplikasi dapat dijalankan.

---

## LANGKAH 5 – Jalankan Aplikasi

### Mac / Linux
```bash
java --module-path lib/lib \
     --add-modules javafx.controls \
     -cp "bin:lib/sqlite-jdbc-3.53.1.0.jar" \
     App
```

### Windows (CMD)
```bat
java --module-path lib\lib ^
     --add-modules javafx.controls ^
     -cp "bin;lib\sqlite-jdbc-3.53.1.0.jar" ^
     App
```

Saat pertama kali dijalankan, folder `data/` dan file `mahasiswa.db` akan **otomatis dibuat**.
Tidak perlu setup database apapun.

---

## Cara Pakai Aplikasi

| Aksi   | Cara                                                                |
|--------|---------------------------------------------------------------------|
| Tambah | Isi form NIM / Nama / Jurusan → klik **Simpan**                     |
| Edit   | Klik baris di tabel → data otomatis masuk form → ubah → **Simpan** |
| Hapus  | Klik baris di tabel → klik **Hapus** → konfirmasi                   |
| Batal  | Klik **Batal** untuk reset form ke mode tambah baru                 |

---

## Konsep OOP dalam Project Ini

| File | Konsep OOP | Penjelasan |
|------|------------|------------|
| `Mahasiswa.java` | **Encapsulation** | Properti `private`, akses hanya lewat getter/setter |
| `DBConnection.java` | **Abstraction** | Detail koneksi disembunyikan, cukup panggil `getConnection()` |
| `MahasiswaDAO.java` | **Abstraction** | Semua SQL dikumpulkan di sini, UI tidak perlu tahu cara kerjanya |
| `App.java` | **Inheritance & Polymorphism** | `extends Application`, override method `start()` |

---

## Troubleshooting

| Masalah | Solusi |
|---------|--------|
| `file not found: *.java` | Pastikan perintah dijalankan dari folder `crud-mahasiswa/`, bukan dari `src/` |
| `module not found: javafx.controls` | Cek `--module-path` mengarah ke `lib/lib/` yang berisi file `.jar` JavaFX |
| `No suitable driver found for jdbc:sqlite` | Pastikan `sqlite-jdbc-3.53.1.0.jar` ada di folder `lib/` dan disertakan di `-cp` |
| `no such table: mahasiswa` | Hapus file `data/mahasiswa.db` lalu jalankan ulang — tabel dibuat otomatis |
| `Note: uses deprecated API` | Bukan error, abaikan saja — aplikasi tetap berjalan normal |
| Tombol Run VS Code tidak jalan | Jangan pakai tombol Run VS Code untuk JavaFX — selalu jalankan lewat terminal |