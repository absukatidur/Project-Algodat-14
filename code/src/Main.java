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
    
    // Mapping untuk Menu (Opsi 1 sampai 7, Opsi 8 = Keluar/0)
    private static final String[] MENU_OPTIONS = {
        "Tambah Buku", 
        "Lihat Semua Buku", 
        "Pinjam Buku", 
        "Kembalikan Buku", 
        "Lihat Antrian Peminjaman", 
        "Lihat Riwayat Peminjaman", 
        "Cari Buku (Tree Search)",
        "Keluar" 
    };

    public static void main(String[] args) {
        runMenu();
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
        
        System.out.println("==========================================");
        System.out.println("||             NODE LIBRARY             ||"); 
        System.out.println("==========================================");
        System.out.println("||    SISTEM MANAJEMEN PERPUSTAKAAN     ||");
        System.out.println("==========================================");

        for (int i = 0; i < MENU_OPTIONS.length; i++) {
            String prefix = (i + 1 == selectedOption) ? ">> " : "   ";
            System.out.printf("|| %s%-34s ||\n", prefix, MENU_OPTIONS[i]);
        }
        System.out.println("==========================================");
        System.out.print("Pilih menu (W/S/Enter): ");
    }
    
    private static void runMenu() {
        // --- PENJELASAN PEMBUKAAN DENGAN EFEK KETIK ---
        
        System.out.println("\n");
        System.out.println("==========================================");
        typeWriterEffect("Selamat datang di NODE LIBRARY!", 50);
        typeWriterEffect("Sebuah sistem manajemen perpustakaan yang dibangun menggunakan konsep Algoritma dan Struktur Data (Algodat).", 50);
        typeWriterEffect("Inti dari sistem ini adalah 'Node' yang menghubungkan data buku, antrian, dan riwayat.", 50);
        typeWriterEffect("Efisiensi sistem didukung oleh implementasi:", 50);
        typeWriterEffect("- Linked List (untuk daftar buku)", 50);
        typeWriterEffect("- Queue (untuk antrian peminjaman)", 50);
        typeWriterEffect("- Stack (untuk riwayat transaksi)", 50);
        typeWriterEffect("- Binary Search Tree (untuk pencarian cepat)", 50);
        System.out.println("==========================================");
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
                case 0 -> {
                    System.out.println("Terima kasih!");
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
    
    public static void tambahBuku() {
        System.out.print("ID Buku: ");
        String id = input.nextLine();

        System.out.print("Judul: ");
        String judul = input.nextLine();

        System.out.print("Author: ");
        String author = input.nextLine();

        System.out.print("Tahun Terbit: ");
        int tahun = Integer.parseInt(input.nextLine());

        System.out.print("Stok Buku: ");
        int stok = Integer.parseInt(input.nextLine());

        Book newBook = new Book(id, judul, author, tahun, stok);
        bookList.addBook(newBook);
        tree.insert(newBook); 
        
        System.out.println("Buku berhasil ditambahkan!");
        pause();
    }


    public static void pinjamBuku() {
        System.out.print("Masukkan ID Buku yang ingin dipinjam: ");
        String id = input.nextLine();

        Book book = bookList.searchBook(id);

        if (book == null) {
            System.out.println("Buku tidak ditemukan.");
            pause();
            return;
        }
        
        System.out.print("Nama Peminjam: ");
        String borrowerName = input.nextLine();
        System.out.print("ID Peminjam: ");
        String borrowerId = input.nextLine();
        
        String borrowDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        
        BorrowData data = new BorrowData(id, borrowerName, borrowerId, borrowDate);

        if (book.getStock() <= 0) { 
            System.out.println("Stok habis, anda masuk antrian peminjaman.");
            borrowQueue.enqueue(data); 
            pause();
            return;
        }

        book.setStock(book.getStock() - 1); 
        history.addHistory("PINJAM", data); 

        System.out.println("Buku berhasil dipinjam oleh " + borrowerName + "!");
        pause();
    }

    public static void kembalikanBuku() {
        System.out.print("Masukkan ID Buku yang dikembalikan: ");
        String id = input.nextLine();

        Book book = bookList.searchBook(id);

        if (book == null) {
            System.out.println("Buku tidak ditemukan.");
            pause();
            return;
        }

        if (!borrowQueue.isEmpty()) {
            BorrowData nextBorrowerData = borrowQueue.dequeue();
            
            history.addHistory("OTOMATIS PINJAM (ANTRIAN)", nextBorrowerData); 
            System.out.println("Buku langsung dipinjam oleh antrian: " + nextBorrowerData.borrowerName + " (ID Buku: " + nextBorrowerData.bookId + ")");
            pause();
            return;
        }

        book.setStock(book.getStock() + 1); 
        
        String returnDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        
        history.addHistorySimple("KEMBALIKAN", id, returnDate); 

        System.out.println("Buku berhasil dikembalikan!");
        pause();
    }
    
    public static void cariBukuTree() {
        System.out.print("Masukkan ID Buku: ");
        String id = input.nextLine();

        Book foundBook = tree.search(id);

        if (foundBook != null) {
            System.out.println("Buku dengan ID " + id + " ditemukan di tree!");
            System.out.println("Detail Buku:");
            System.out.println("ID: " + foundBook.id + " | " + foundBook.title + " | "
                    + foundBook.author + " | Tahun: " + foundBook.year + " | Stok: " + foundBook.stock);
        } else {
            System.out.println("Buku tidak ada dalam tree.");
        }
        pause();
    }
}