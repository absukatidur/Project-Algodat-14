import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner; 

public class Main {

    // ==========================================
    // 1. STRUKTUR LINKED LIST MANUAL UNTUK MENU
    // ==========================================
    
    // Node untuk menyimpan satu item menu (String)
    static class MenuNode {
        String data;
        MenuNode next;

        MenuNode(String data) {
            this.data = data;
            this.next = null;
        }
    }

    // Class LinkedList manual khusus Menu
    static class MenuLinkedList {
        MenuNode head;
        MenuNode tail;
        int size = 0;

        // Method untuk menambah menu ke belakang
        void add(String data) {
            MenuNode newNode = new MenuNode(data);
            if (head == null) {
                head = tail = newNode;
            } else {
                tail.next = newNode;
                tail = newNode;
            }
            size++;
        }

        // Method untuk mengambil jumlah menu
        int size() {
            return size;
        }
    }
    // ==========================================


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

        public String toString() {
            return "Buku: " + bookId + " | Peminjam: " + borrowerName + " (" + borrowerId + ") | Tgl: " + borrowDate;
        }
    }

    static Scanner input = new Scanner(System.in);
    static LinkedListBook bookList = new LinkedListBook();
    static BorrowQueue borrowQueue = new BorrowQueue();
    static BorrowHistory history = new BorrowHistory();
    static BookTree tree = new BookTree();
    
    // Ganti Array static dengan LinkedList Manual static
    static MenuLinkedList menuOptions = new MenuLinkedList();

    // Isi data menu ke dalam LinkedList manual
    static {
        menuOptions.add("Tambah Buku");
        menuOptions.add("Lihat Semua Buku");
        menuOptions.add("Pinjam Buku");
        menuOptions.add("Kembalikan Buku");
        menuOptions.add("Lihat Antrian Peminjaman");
        menuOptions.add("Lihat Riwayat Peminjaman");
        menuOptions.add("Cari Buku (Tree Search)");
        menuOptions.add("Urutkan Daftar Buku (Sort)");
        menuOptions.add("Keluar");
    }

    public static void main(String[] args) {
        initializeData(); 
        runMenu();
    }
    
    private static void initializeData() {
        addInitialBook("B001", "Algoritma Dasar", "Rina Sari", 2020, 5);
        addInitialBook("B002", "Struktur Data", "Budi Santoso", 2021, 3);
        addInitialBook("B003", "Java Pemula", "Citra Dewi", 2018, 8);
        addInitialBook("B004", "AI Modern", "Dwi Haryono", 2023, 2);
        addInitialBook("B005", "Matematika Diskrit", "Eka Fitri", 2017, 6);
    }
    
    private static void addInitialBook(String id, String title, String author, int year, int stock) {
        Book b = new Book(id, title, author, year, stock);
        bookList.addBook(b);
        tree.insert(b);
    }
    
    private static String inputString(String prompt) {
        System.out.print(prompt + " (0 utk Batal): ");
        String val = input.nextLine().trim();
        if (val.equals("0")) return null; 
        if (val.isEmpty()) {
            System.out.println("Error: Input tidak boleh kosong!");
            return inputString(prompt); 
        }
        return val;
    }

private static Integer inputNumber(String prompt) {
        System.out.print(prompt + " (0 utk Batal): ");
        String str = input.nextLine().trim();
        
        if (str.equals("0")) return null;
        if (str.matches("-?\\d+")) {
            int num = Integer.parseInt(str); // Aman diparsing karena format sudah dicek
            
            if (num < 0) {
                System.out.println("Error: Angka tidak boleh negatif!");
                return inputNumber(prompt);
            }
            
            return num;
        } else {
            System.out.println("Error: Input harus berupa angka!");
            return inputNumber(prompt);
        }
    }

    private static String inputID(String prompt) {
        String val = inputString(prompt);
        if (val == null) return null; 

        if (!val.matches("^B[0-9]+$")) {
            System.out.println("Error: Format ID salah! Harus diawali 'B' diikuti angka (Cth: B001).");
            return inputID(prompt);
        }
        return val;
    }

    private static void displayMenu(int selectedOption) {
        System.out.print("\033[H\033[2J"); 
        System.out.flush(); 
        
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║              NODE LIBRARY              ║"); 
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║   Gunakan W (Atas) / S (Bawah) + Enter ║");
        System.out.println("╠════════════════════════════════════════╣");

        // --- LOGIKA MENAMPILKAN MENU DARI LINKED LIST MANUAL ---
        MenuNode current = menuOptions.head;
        int i = 0;
        
        // Loop manual menggunakan pointer next (bukan index array)
        while (current != null) {
            // Cek apakah posisi (i+1) sama dengan yang dipilih user
            String prefix = (i + 1 == selectedOption) ? " >> " : "    ";
            
            System.out.printf("║ %s%-33s ║\n", prefix, current.data);
            
            current = current.next; // Geser ke node berikutnya
            i++;
        }
        // -------------------------------------------------------

        System.out.println("╚════════════════════════════════════════╝");
        System.out.print("Navigasi: ");
    }
    
    private static void runMenu() {
        int selectedOption = 1;
        // Mengambil ukuran dari linked list manual
        final int MAX_OPTION = menuOptions.size();

        while (true) {
            displayMenu(selectedOption);
    
            String command = input.nextLine().toLowerCase().trim();

            if (command.equals("w")) {
                selectedOption--;
                if (selectedOption < 1) selectedOption = MAX_OPTION;
            } else if (command.equals("s")) {
                selectedOption++;
                if (selectedOption > MAX_OPTION) selectedOption = 1;
            } else if (command.isEmpty()) {
                executeMenu(selectedOption);
                if (selectedOption == 9) return;
            } else {
    
                try {
                    int direct = Integer.parseInt(command);
                    if (direct >= 1 && direct <= MAX_OPTION) {
                        executeMenu(direct);
                        if (direct == 9) return;
                    }
                } catch(Exception e) {}
            }
        }
    }
    
    private static void executeMenu(int choice) {
        switch (choice) {
            case 1 -> tambahBuku();
            case 2 -> { bookList.showBooks(); pause(); }
            case 3 -> pinjamBuku();
            case 4 -> kembalikanBuku();
            case 5 -> { borrowQueue.showQueue(); pause(); }
            case 6 -> { history.showHistory(); pause(); }
            case 7 -> cariBukuTree();
            case 8 -> menuUrutkanBuku(); 
            case 9 -> System.out.println("Bye!"); 
        }
    }
    
    private static void pause() {
        System.out.println("\n[Tekan ENTER untuk kembali]");
        input.nextLine();
    }
    
    // ================= FITUR UTAMA =================
    
    public static void tambahBuku() {
        System.out.println("\n=== TAMBAH BUKU BARU ===");
        
        String id = inputID("Masukkan ID Buku (Cth: B001)");
        if (id == null) return; // User batal

        if (bookList.isIdExists(id)) {
            System.out.println("Gagal: ID Buku " + id + " sudah ada!");
            pause();
            return;
        }

        String judul = inputString("Judul Buku");
        if (judul == null) return;

        String author = inputString("Penulis");
        if (author == null) return;

        Integer tahun = inputNumber("Tahun Terbit");
        if (tahun == null) return;

        Integer stok = inputNumber("Stok Buku");
        if (stok == null) return;

        Book newBook = new Book(id, judul, author, tahun, stok);
        bookList.addBook(newBook);
        tree.insert(newBook); 
        
        System.out.println("Berhasil! Buku ditambahkan.");
        pause();
    }

    public static void pinjamBuku() {
        System.out.println("\n=== PINJAM BUKU ===");
        
        String id = inputID("Masukkan ID Buku yg dipinjam");
        if (id == null) return;

        Book book = bookList.searchBook(id);
        if (book == null) {
            System.out.println("Buku tidak ditemukan!");
            pause();
            return;
        }
        
        String nama = inputString("Nama Peminjam");
        if (nama == null) return;
        
        String idPinjam = inputString("ID Peminjam (Angka/Huruf Bebas)");
        if (idPinjam == null) return;
        
        String tgl = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        BorrowData data = new BorrowData(id, nama, idPinjam, tgl);
        
        if (book.getStock() <= 0) { 
            System.out.println("Stok habis! Anda masuk antrian.");
            borrowQueue.enqueue(data); 
        } else {
            book.setStock(book.getStock() - 1); 
            history.addHistory("PINJAM", data); 
            System.out.println("Buku berhasil dipinjam!");
        }
        pause();
    }

    public static void kembalikanBuku() {
        System.out.println("\n=== KEMBALIKAN BUKU ===");
        
        String id = inputID("Masukkan ID Buku yg dikembalikan");
        if (id == null) return;

        Book book = bookList.searchBook(id);
        if (book == null) {
            System.out.println("Buku tidak ditemukan di sistem!");
            pause();
            return;
        }

        // Cek Antrian
        if (!borrowQueue.isEmpty()) {
            BorrowData nextUser = borrowQueue.dequeue();
            history.addHistory("PINJAM-ANTRIAN", nextUser); 
            System.out.println("Buku langsung dipinjamkan ke antrian: " + nextUser.borrowerName);
        } else {
            book.setStock(book.getStock() + 1); 
            String tgl = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            history.addHistorySimple("KEMBALI", id, tgl); 
            System.out.println("Buku berhasil dikembalikan ke stok.");
        }
        pause();
    }
    
    public static void cariBukuTree() {
        System.out.println("\n=== CARI BUKU (TREE) ===");
        String id = inputID("Masukkan ID Buku");
        if (id == null) return;

        Book found = tree.search(id);
        if (found != null) {
            System.out.println("Ditemukan!");
            System.out.println(found.toString() + " | Stok: " + found.stock);
        } else {
            System.out.println("Buku tidak ditemukan di Tree.");
        }
        pause();
    }
    
    public static void menuUrutkanBuku() {
        System.out.println("\n=== URUTKAN BUKU ===");
        System.out.println("1. Berdasarkan Judul");
        System.out.println("2. Berdasarkan ID");
        
        Integer pil = inputNumber("Pilih (1/2)");
        if (pil == null) return;
        
        if (pil == 1) bookList.sortByTitle();
        else if (pil == 2) bookList.sortById();
        else System.out.println("Pilihan salah.");
        
        pause();
    }
}