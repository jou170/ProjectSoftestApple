# Project Software Testing: Automation Testing untuk apple.com

Proyek ini bertujuan untuk melakukan pengujian otomatis pada website **[apple.com](https://www.apple.com/)** sebagai bagian dari tugas akhir mata kuliah Software Testing.

Pada test class **SignUp**, digunakan **Tesseract OCR** yang telah disesuaikan untuk menyelesaikan captcha teks pada website ini. Kode dan algoritma untuk menyelesaikan captcha dapat ditemukan pada file `CaptchaSolver.java`. Namun, captcha solver ini memiliki keterbatasan dan peluang keberhasilannya kecil karena Tesseract OCR lebih efektif mendeteksi karakter yang terpisah, sedangkan karakter pada captcha teks dibuat saling menempel dan bertumpuk.

## **Anggota Kelompok**
- **222117007**: Billie Nathaniel Phalosa
- **222117025**: Hans Philipus Tiono
- **222117027**: Hansthrony Gianto Halim
- **222117038**: Joy Gemilang

## **Setup Tesseract OCR**
Agar Tesseract OCR dapat digunakan dalam proyek ini, ikuti langkah-langkah berikut:

1. **Pindahkan Folder tessdata**
   - Pindahkan folder `tessdata` beserta file `.traineddata` dari proyek ini ke direktori tujuan (contoh: `C:\Tools\Tesseract-OCR\tessdata`).

2. **Tambahkan System Environment Variable**
   - Tambahkan environment variable baru dengan nama `TESSDATA_PREFIX`.
   - Isi nilai dari `TESSDATA_PREFIX` dengan path ke folder `tessdata` (contoh: `C:\Tools\Tesseract-OCR\tessdata`).


## **Cara Mencetak Test Case**

### 1. Melihat Report dengan Allure
- Ketik perintah berikut di terminal dengan posisi berada di direktori `ProjectSoftestApple`:
  ```bash
  allure serve ./demoapp/allure-results
  ```

### 2. Jika Error Saat Menjalankan Allure
- Instal **Allure** menggunakan **Scoop** dengan perintah berikut:
  ```bash
  scoop install allure
  ```

### 3. Jika "Scoop Not Recognized"
- Setel Execution Policy terlebih dahulu, lalu instal Scoop:
  ```bash
  Set-ExecutionPolicy RemoteSigned -Scope CurrentUser
  irm get.scoop.sh | iex
  ```

### 4. Alternatif Cara Manual untuk Generate dan Membuka Report
- Jika Scoop sudah diinstal dan Allure sudah terpasang tetapi metode sebelumnya masih gagal, gunakan langkah-langkah berikut:
  ```bash
  allure generate allure-results --clean -o allure-report
  allure open allure-report
  ```


## **Hal yang Perlu Diperhatikan**

- Jika metode `@Test("Sign Up Berhasil")` tidak berjalan dengan baik karena semua percobaan `solveCaptcha()` gagal:
  1. Ganti kode `solveCaptcha()` dengan `Thread.sleep(durasi_secukupnya)`.
  2. Ketik teks captcha secara manual saat diminta.


Terima kasih telah membaca README ini! Jika ada kendala, silakan periksa kembali langkah-langkah di atas atau hubungi anggota kelompok.

