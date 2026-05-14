# CRUD Mahasiswa – JavaFX + MySQL (Tanpa Maven)

## Struktur Folder

```
crud-mahasiswa/
├── src/
│   ├── App.java           ← UI JavaFX (tampilan + event)
│   ├── Mahasiswa.java     ← Model/data class
│   ├── MahasiswaDAO.java  ← Semua query SQL (CRUD)
│   └── DBConnection.java  ← Koneksi ke MySQL
├── lib/
│   ├── legal/             ← Lisensi JavaFX SDK (otomatis dari extract)
│   ├── lib/               ← File .jar JavaFX SDK
│   └── mysql-connector-j-9.7.0.jar  ← driver MySQL
└── bin/                   ← hasil compile (dibuat otomatis)
```

---

## LANGKAH 1 – Siapkan Database MySQL

Buka MySQL / phpMyAdmin, jalankan SQL berikut:

```sql
CREATE DATABASE IF NOT EXISTS oopjava;
USE oopjava;

CREATE TABLE mahasiswa (
    id      INT AUTO_INCREMENT PRIMARY KEY,
    nim     VARCHAR(20)  NOT NULL,
    nama    VARCHAR(100) NOT NULL,
    jurusan VARCHAR(50)  NOT NULL
);
```

---

## LANGKAH 2 – Download JavaFX SDK

1. Buka: https://gluonhq.com/products/javafx/
2. Pilih versi **21 (LTS)**, OS sesuai komputer kamu (Windows/Mac/Linux)
3. Extract → pindahkan **isi folder** hasil extract langsung ke `lib/`
4. Setelah extract, di dalam `lib/` akan ada folder `legal/` dan `lib/` — itu yang dipakai
5. Pastikan di dalam `lib/lib/` ada file-file `.jar` JavaFX

> **Jangan** buat subfolder `javafx-sdk/` di dalam `lib/`. Langsung taruh isinya ke `lib/`.

---

## LANGKAH 3 – Siapkan MySQL Connector

File `mysql-connector-j-9.7.0.jar` sudah ada di project kamu (folder `lib/`).
Kalau belum, download dari: https://dev.mysql.com/downloads/connector/j/

---

## LANGKAH 4 – Konfigurasi VS Code

Buka file `.vscode/settings.json`, isi seperti ini:

```json
{
    "java.project.sourcePaths": ["src"],
    "java.project.outputPath": "bin",
    "java.project.referencedLibraries": [
        "lib/**/*.jar",
        "lib/lib/**/*.jar"
    ]
}
```

---

## LANGKAH 5 – Test Koneksi Database

Sebelum compile aplikasi utama, test dulu koneksi ke MySQL.
Jalankan dari folder root `crud-mahasiswa/`:

### Mac / Linux
```bash
javac -cp "lib/mysql-connector-j-9.7.0.jar" src/DBConnection.java src/TestKoneksi.java
java -cp "src:lib/mysql-connector-j-9.7.0.jar" TestKoneksi
```

### Windows (CMD)
```bat
javac -cp "lib\mysql-connector-j-9.7.0.jar" src\DBConnection.java src\TestKoneksi.java
java -cp "src;lib\mysql-connector-j-9.7.0.jar" TestKoneksi
```

Jika berhasil akan muncul:
```
Koneksi BERHASIL!
Database: oopjava
```

> `DBConnection.java` tidak bisa dijalankan langsung karena tidak punya `main` method.
> Gunakan `TestKoneksi.java` untuk mengecek koneksi.

---

## LANGKAH 6 – Compile (di Terminal)

Jalankan dari folder root `crud-mahasiswa/` (bukan dari dalam `src/`):

### Mac / Linux
```bash
javac --module-path lib/lib \
      --add-modules javafx.controls \
      -cp "lib/mysql-connector-j-9.7.0.jar" \
      -d bin \
      src/*.java
```

### Windows (CMD)
```bat
javac --module-path lib\lib ^
      --add-modules javafx.controls ^
      -cp "lib\mysql-connector-j-9.7.0.jar" ^
      -d bin ^
      src\*.java
```

> Pesan `Note: uses deprecated API` dan `unchecked operations` adalah **peringatan biasa, bukan error**.
> Compile tetap berhasil dan aplikasi dapat dijalankan.

---

## LANGKAH 7 – Jalankan Aplikasi

### Mac / Linux
```bash
java --module-path lib/lib \
     --add-modules javafx.controls \
     -cp "bin:lib/mysql-connector-j-9.7.0.jar" \
     App
```

### Windows (CMD)
```bat
java --module-path lib\lib ^
     --add-modules javafx.controls ^
     -cp "bin;lib\mysql-connector-j-9.7.0.jar" ^
     App
```

---

## Cara Pakai Aplikasi

| Aksi   | Cara                                                              |
|--------|-------------------------------------------------------------------|
| Tambah | Isi form NIM/Nama/Jurusan → klik **Simpan**                       |
| Edit   | Klik baris di tabel → data otomatis masuk form → ubah → **Simpan** |
| Hapus  | Klik baris di tabel → klik **Hapus** → konfirmasi                 |
| Batal  | Klik **Batal** untuk reset form                                   |

---

## Troubleshooting

| Masalah | Solusi |
|---------|--------|
| `Koneksi GAGAL` | Cek MySQL aktif, nama DB `oopjava`, password di `DBConnection.java` |
| `No suitable driver found` | Pastikan `mysql-connector-j-*.jar` disertakan di `-cp` saat compile **dan** run |
| `module not found: javafx.controls` | Cek `--module-path` mengarah ke `lib/lib/` yang berisi file `.jar` JavaFX |
| `Main method not found in DBConnection` | Normal! Jalankan `TestKoneksi` untuk test koneksi, bukan `DBConnection` |
| `file not found: *.java` | Pastikan perintah dijalankan dari folder `crud-mahasiswa/`, bukan dari `src/` |
| `Error: JavaFX runtime components missing` | Pastikan `--module-path` dan `--add-modules` sudah benar |
| Tabel kosong padahal data ada | Cek nama kolom di SQL sesuai dengan `PropertyValueFactory` di `App.java` |
# JavaFXCRUD_SI61
