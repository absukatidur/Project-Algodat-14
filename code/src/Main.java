import java.util.Scanner;

public class Main {

    // Scanner global
    static Scanner input = new Scanner(System.in);

    // Struktur data project
    static LinkedListBook bookList = new LinkedListBook();
    static BorrowQueue borrowQueue = new BorrowQueue();
    static BorrowHistory history = new BorrowHistory();
    static BookTree tree = new BookTree();

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== MENU PERPUSTAKAAN =====");
            System.out.println("1. Tambah Buku");
            System.out.println("2. Lihat Semua Buku");
            System.out.println("3. Pinjam Buku");
            System.out.println("4. Kembalikan Buku");
            System.out.println("5. Lihat Antrian Peminjaman");
            System.out.println("6. Lihat Riwayat Peminjaman");
            System.out.println("7. Cari Buku (Tree Search)");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");

            int pilihan = input.nextInt();
            input.nextLine();

            switch (pilihan) {
                case 1 -> tambahBuku();
                // FIX 3: Menggunakan nama method yang benar
                case 2 -> bookList.showBooks();
                case 3 -> pinjamBuku();
                case 4 -> kembalikanBuku();
                case 5 -> borrowQueue.showQueue();
                case 6 -> history.showHistory();
                case 7 -> cariBukuTree();
                case 0 -> {
                    System.out.println("Terima kasih!");
                    return;
                }
                default -> System.out.println("Pilihan tidak valid!");
            }
        }
    }

    // ============================== //
    //        METHOD TAMBAH BUKU      //
    // ============================== //
    public static void tambahBuku() {
        System.out.print("ID Buku: ");
        String id = input.nextLine();

        System.out.print("Judul: ");
        String judul = input.nextLine();

        System.out.print("Author: ");
        String author = input.nextLine();

        System.out.print("Tahun Terbit: ");
        int tahun = input.nextInt();
        input.nextLine();

        System.out.print("Stok Buku: ");
        int stok = input.nextInt();
        input.nextLine();

        // FIX 1 & 6: Membuat objek Book dengan String ID dan memasukkannya ke tree
        Book newBook = new Book(id, judul, author, tahun, stok);
        bookList.addBook(newBook);
        tree.insert(newBook); // insert objek Book ke tree

        System.out.println("Buku berhasil ditambahkan!");
    }

    // ============================== //
    //         METHOD PINJAM BUKU     //
    // ============================== //
    public static void pinjamBuku() {
        System.out.print("Masukkan ID Buku yang ingin dipinjam: ");
        String id = input.nextLine();

        // FIX 3: Menggunakan method searchBook yang sudah ditambahkan
        Book book = bookList.searchBook(id);

        if (book == null) {
            System.out.println("Buku tidak ditemukan.");
            return;
        }
        // FIX 2: Menggunakan method getStock()
        if (book.getStock() <= 0) {
            System.out.println("Stok habis, anda masuk antrian peminjaman.");
            borrowQueue.enqueue(id);
            return;
        }

        // FIX 2: Menggunakan method setStock() dan getStock()
        book.setStock(book.getStock() - 1);
        // FIX 4: Menggunakan method addHistory yang sudah diperbaiki
        history.addHistory("PINJAM", id);

        System.out.println("Buku berhasil dipinjam!");
    }

    // ============================== //
    //      METHOD KEMBALIKAN BUKU    //
    // ============================== //
    public static void kembalikanBuku() {
        System.out.print("Masukkan ID Buku yang dikembalikan: ");
        String id = input.nextLine();

        // FIX 3: Menggunakan method searchBook yang sudah ditambahkan
        Book book = bookList.searchBook(id);

        if (book == null) {
            System.out.println("Buku tidak ditemukan.");
            return;
        }

        // jika ada antrian, orang pertama dapat buku dulu
        if (!borrowQueue.isEmpty()) {
            String nextBorrowerId = borrowQueue.dequeue();
            // FIX 4: Menggunakan method addHistory yang sudah diperbaiki
            history.addHistory("OTOMATIS PINJAM (ANTRIAN)", nextBorrowerId);
            System.out.println("Buku langsung dipinjam oleh antrian: " + nextBorrowerId);
            return;
        }

        // FIX 2: Menggunakan method setStock() dan getStock()
        book.setStock(book.getStock() + 1);
        // FIX 4: Menggunakan method addHistory yang sudah diperbaiki
        history.addHistory("KEMBALIKAN", id);

        System.out.println("Buku berhasil dikembalikan!");
    }

    // ============================== //
    //          TREE SEARCH           //
    // ============================== //
    public static void cariBukuTree() {
        System.out.print("Masukkan ID Buku: ");
        String id = input.nextLine();

        // FIX 5: tree.search sekarang mengembalikan objek Book (atau null)
        Book foundBook = tree.search(id);

        if (foundBook != null) {
            System.out.println("Buku dengan ID " + id + " ditemukan di tree!");
            // Menampilkan detail buku untuk konfirmasi
            System.out.println("Detail Buku:");
            System.out.println("ID: " + foundBook.id + " | " + foundBook.title + " | "
                    + foundBook.author + " | Tahun: " + foundBook.year + " | Stok: " + foundBook.stock);
        } else {
            System.out.println("Buku tidak ada dalam tree.");
        }
    }
}