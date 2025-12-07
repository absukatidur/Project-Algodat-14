import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner; 

public class Main {

    // --- Helper Class (BorrowData) ---
    static class BorrowData {
        String bookId;
        String borrowerName;
        String borrowerId;
        String borrowDate;

        public BorrowData(String bookId, String borrowerName, String borrowerId, String borrowDate) {
            this.bookId = bookId;
            this.borrowerName = borrowerName;
            this.borrowerId = borrowerId;
            this.borrowDate = borrowDate;
        }

        @Override
        public String toString() {
            return "ID Buku: " + bookId + " | Peminjam: " + borrowerName + " (ID: " + borrowerId + ") | Tanggal Pinjam: " + borrowDate;
        }
    }
    // ---------------------------------

    // Scanner global
    static Scanner input = new Scanner(System.in);

    // Struktur data project
    static LinkedListBook bookList = new LinkedListBook();
    static BorrowQueue borrowQueue = new BorrowQueue();
    static BorrowHistory history = new BorrowHistory();
    static BookTree tree = new BookTree();
    
    // Mapping untuk Menu (Opsi 1 sampai 9, Opsi 0 = Keluar)
    private static final String[] MENU_OPTIONS = {
        "Tambah Buku", 
        "Lihat Semua Buku", 
        "Pinjam Buku", 
        "Kembalikan Buku", 
        "Lihat Antrian Peminjaman", 
        "Lihat Riwayat Peminjaman", 
        "Cari Buku (Tree Search)",
        "Urutkan Daftar Buku (Sort)", 
        "Keluar" 
    };

    public static void main(String[] args) {
        initializeData(); 
        runMenu();
    }
    
    // ============================== //
    //     FUNGSI INISIALISASI DATA   //
    // ============================== //
    private static void initializeData() {
        System.out.println("Memuat 10 buku awal ke dalam sistem...");
        
        Book[] initialBooks = {
            new Book("B001", "Algoritma Dasar", "Rina Sari", 2020, 5),
            new Book("B002", "Struktur Data Lanjut", "Budi Santoso", 2021, 3),
            new Book("B003", "Pengenalan Java", "Citra Dewi", 2018, 8),
            new Book("B004", "Kecerdasan Buatan", "Dwi Haryono", 2023, 2),
            new Book("B005", "Matematika Diskrit", "Eka Fitri", 2017, 6),
            new Book("B006", "Basis Data Modern", "Fajar Setiawan", 2019, 4),
            new Book("B007", "Jaringan Komputer", "Gita Paramita", 2022, 7),
            new Book("B008", "Pengembangan Web", "Hadi Wijaya", 2020, 5),
            new Book("B009", "Analisis Sistem", "Ira Kusuma", 2016, 1),
            new Book("B010", "Keamanan Informasi", "Joko Pramono", 2024, 3)
        };
        
        // Memasukkan 10 buku ke Linked List dan Tree
        for (Book book : initialBooks) {
            bookList.addBook(book);
            tree.insert(book);
        }
        System.out.println("buku berhasil dimuat ke daftar buku dan tree.\n");
    }
    
    // ============================== //
    //       TYPING EFFECT METHOD     //
    // ============================== //
    private static void typeWriterEffect(String text, long delay) {
        try {
            for (char c : text.toCharArray()) {
                System.out.print(c);
                Thread.sleep(delay);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(); // Newline setelah teks selesai
    }
    
    // ============================== //
    //        NAVIGASI MENU           //
    // ============================== //
    private static void displayMenu(int selectedOption) {
        // Mencoba clear console
        System.out.print("\033[H\033[2J"); 
        System.out.flush(); 
        
        // --- UI ENHANCEMENT: Menu Border ---
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║              NODE LIBRARY              ║"); 
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ SISTEM MANAJEMEN PERPUSTAKAAN (ALGODAT)║");
        System.out.println("╠════════════════════════════════════════╣");

        for (int i = 0; i < MENU_OPTIONS.length; i++) {
            String prefix = (i + 1 == selectedOption) ? ">> " : "   ";
            System.out.printf("║ %s%-34s ║\n", prefix, (i + 1) + ". " + MENU_OPTIONS[i]);
        }
        System.out.println("╚════════════════════════════════════════╝");
        System.out.print("Pilih menu (W/S/Enter): ");
    }
    
    private static void runMenu() {
        // --- PENJELASAN PEMBUKAAN DENGAN EFEK KETIK ---
        
        System.out.println("\n");
        // --- UI ENHANCEMENT: Opening Border ---
        System.out.println("╔════════════════════════════════════════════════════════════════════════════╗");
        typeWriterEffect("║ Selamat datang di NODE LIBRARY!                                            ║", 20);
        System.out.println("╠════════════════════════════════════════════════════════════════════════════╣");
        typeWriterEffect("║ Sebuah sistem manajemen perpustakaan yang dibangun menggunakan konsep      ║", 15);
        typeWriterEffect("║ Algoritma dan Struktur Data (Algodat).                                     ║", 20);
        typeWriterEffect("║ Efisiensi sistem didukung oleh implementasi:                               ║", 20);
        typeWriterEffect("║ - Linked List (untuk daftar buku)                                          ║", 20);
        typeWriterEffect("║ - Queue (untuk antrian peminjaman)                                         ║", 20);
        typeWriterEffect("║ - Stack (untuk riwayat transaksi)                                          ║", 20);
        typeWriterEffect("║ - Binary Search Tree (untuk pencarian cepat)                               ║", 20);
        System.out.println("╚════════════════════════════════════════════════════════════════════════════╝");
        pause(); // LINE INI MEMASTIKAN JEDA SEBELUM MASUK MENU

        // --- START MENU LOOP ---
        int selectedOption = 1;
        final int MAX_OPTION = MENU_OPTIONS.length;

        while (true) {
            displayMenu(selectedOption);

            String command = input.nextLine().toLowerCase().trim();
            int pilihan = -1;

            if (command.equals("w")) {
                selectedOption = Math.max(1, selectedOption - 1);
                continue;
            } else if (command.equals("s")) {
                selectedOption = Math.min(MAX_OPTION, selectedOption + 1);
                continue;
            } else if (command.isEmpty()) {
                pilihan = (selectedOption == MAX_OPTION) ? 0 : selectedOption;
            } else {
                System.out.println("Input tidak valid. Silakan gunakan W, S, atau Enter.");
                pause();
                continue;
            }

            switch (pilihan) {
                case 1 -> tambahBuku();
                case 2 -> { bookList.showBooks(); pause(); }
                case 3 -> pinjamBuku();
                case 4 -> kembalikanBuku();
                case 5 -> { borrowQueue.showQueue(); pause(); }
                case 6 -> { history.showHistory(); pause(); }
                case 7 -> cariBukuTree();
                case 8 -> menuUrutkanBuku(); 
                case 0 -> {
                    System.out.println("Terima kasih telah menggunakan NODE LIBRARY!");
                    return;
                }
                default -> { /* error */ }
            }
        }
    }
    
    private static void pause() {
        System.out.println("\nTekan ENTER untuk melanjutkan...");
        input.nextLine();
    }
    
    // ============================== //
    //       FUNGSI SORTING MENU      //
    // ============================== //
    public static void menuUrutkanBuku() {
        while(true) {
            System.out.print("\033[H\033[2J"); 
            System.out.flush(); 
            
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║            MENU SORTING BUKU           ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║ 1. Urutkan berdasarkan Judul           ║");
            System.out.println("║ 2. Urutkan berdasarkan ID Buku         ║");
            System.out.println("║ 0. Kembali ke Menu Utama               ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.print("Pilih opsi: ");

            String command = input.nextLine().trim();
            int pilihan = -1;
            try {
                pilihan = Integer.parseInt(command);
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Silakan masukkan angka.");
                pause();
                continue;
            }

            switch(pilihan) {
                case 1 -> {
                    bookList.sortByTitle();
                    pause(); // Pause tetap ada karena showBooks dipanggil dari dalam sortByTitle
                    return;
                }
                case 2 -> {
                    bookList.sortById(); 
                    pause(); // Pause tetap ada karena showBooks dipanggil dari dalam sortById
                    return;
                }
                case 0 -> {
                    return;
                }
                default -> {
                    System.out.println("Pilihan tidak ada.");
                    pause();
                }
            }
        }
    }
    
    public static void tambahBuku() {
        // --- UI ENHANCEMENT: Header ---
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║           FORM TAMBAH BUKU             ║");
        System.out.println("╠════════════════════════════════════════╣");
        
        System.out.print("ID Buku: ");
        // [Perubahan 1]: Menggunakan .trim() untuk menghilangkan spasi di awal/akhir
        String id = input.nextLine().trim(); 

        System.out.print("Judul: ");
        String judul = input.nextLine().trim();

        System.out.print("Author: ");
        String author = input.nextLine().trim();
        
        // ==========================================================
        //         [FUNGSI CHECK VALIDASI DATA KOSONG DITAMBAHKAN DI SINI]
        // ==========================================================
        
        // Pengecekan apakah ID, Judul, atau Author kosong
        if (id.isEmpty() || judul.isEmpty() || author.isEmpty()) {
            System.out.println("----------------------------------------");
            System.out.println("Gagal: ID Buku, Judul, dan Author wajib diisi!");
            System.out.println("----------------------------------------");
            System.out.println("╚════════════════════════════════════════╝");
            pause();
            return; // Hentikan proses dan kembali ke menu utama
        }
        // ==========================================================


        System.out.print("Tahun Terbit: ");
        int tahun = 0;
        try {
            tahun = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Input tahun tidak valid. Menggunakan 0.");
        }

        System.out.print("Stok Buku: ");
        int stok = 0;
        try {
            stok = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Input stok tidak valid. Menggunakan 0.");
        }

        Book newBook = new Book(id, judul, author, tahun, stok);
        bookList.addBook(newBook);
        tree.insert(newBook); 
        
        System.out.println("╚════════════════════════════════════════╝");
        pause();
    }


    public static void pinjamBuku() {
        // --- UI ENHANCEMENT: Header ---
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║            FORM PINJAM BUKU            ║");
        System.out.println("╠════════════════════════════════════════╣");
        
        System.out.print("Masukkan ID Buku yang ingin dipinjam: ");
        String id = input.nextLine();

        Book book = bookList.searchBook(id);

        if (book == null) {
            System.out.println("Buku tidak ditemukan.");
            System.out.println("╚════════════════════════════════════════╝");
            pause();
            return;
        }
        
        System.out.print("Nama Peminjam: ");
        String borrowerName = input.nextLine();
        System.out.print("ID Peminjam: ");
        String borrowerId = input.nextLine();
        
        String borrowDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        
        BorrowData data = new BorrowData(id, borrowerName, borrowerId, borrowDate);
        
        System.out.println("╠════════════════════════════════════════╣"); // Separator
        
        if (book.getStock() <= 0) { 
            System.out.println("Stok habis, Anda masuk antrian peminjaman.");
            borrowQueue.enqueue(data); 
            System.out.println("╚════════════════════════════════════════╝");
            pause();
            return;
        }

        book.setStock(book.getStock() - 1); 
        history.addHistory("PINJAM", data); 

        System.out.println("Buku berhasil dipinjam oleh " + borrowerName + "!");
        System.out.println("╚════════════════════════════════════════╝");
        pause();
    }

    public static void kembalikanBuku() {
        // --- UI ENHANCEMENT: Header ---
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║           FORM KEMBALIKAN BUKU         ║");
        System.out.println("╠════════════════════════════════════════╣");
        
        System.out.print("Masukkan ID Buku yang dikembalikan: ");
        String id = input.nextLine();

        Book book = bookList.searchBook(id);

        if (book == null) {
            System.out.println("Buku tidak ditemukan.");
            System.out.println("╚════════════════════════════════════════╝");
            pause();
            return;
        }

        if (!borrowQueue.isEmpty()) {
            BorrowData nextBorrowerData = borrowQueue.dequeue();
            
            history.addHistory("OTOMATIS PINJAM (ANTRIAN)", nextBorrowerData); 
            System.out.println("Buku langsung dipinjam oleh antrian:");
            System.out.println(">>> " + nextBorrowerData.borrowerName + " (ID Buku: " + nextBorrowerData.bookId + ")");
            System.out.println("╚════════════════════════════════════════╝");
            pause();
            return;
        }

        book.setStock(book.getStock() + 1); 
        
        String returnDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        
        history.addHistorySimple("KEMBALIKAN", id, returnDate); 

        System.out.println("Buku berhasil dikembalikan!");
        System.out.println("╚════════════════════════════════════════╝");
        pause();
    }
    
    public static void cariBukuTree() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         PENCARIAN BUKU (BY ID)         ║");
        System.out.println("╠════════════════════════════════════════╣");
        
        System.out.print("Masukkan ID Buku: ");
        String id = input.nextLine();

        Book foundBook = tree.search(id);

        System.out.println("╠════════════════════════════════════════╣"); 

        if (foundBook != null) {
            System.out.println("Buku dengan ID " + id + " ditemukan di tree!");
            System.out.println("Detail Buku:");
            System.out.println("ID    : " + foundBook.id);
            System.out.println("Judul : " + foundBook.title);
            System.out.println("Penulis: " + foundBook.author);
            System.out.println("Tahun : " + foundBook.year + " | Stok: " + foundBook.stock);
        } else {
            System.out.println("Buku tidak ada dalam tree.");
        }
        
        System.out.println("╚════════════════════════════════════════╝");
        pause();
    }
}