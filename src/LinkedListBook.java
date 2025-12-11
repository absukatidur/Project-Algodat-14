public class LinkedListBook {

    class Node {
        Book data;
        Node next;
        Node(Book b) { data = b; }
    }

    Node head;

    public void addBook(Book b) {

        // Cek apakah ID sudah ada
        if (isIdExists(b.id)) {
            System.out.println("Kode buku '" + b.id + "' sudah ada! Buku tidak ditambahkan.");
            return; // hentikan
        }

        Node n = new Node(b);

        if (head == null) {
            head = n;
        } else {
            Node cur = head;
            while (cur.next != null)
                cur = cur.next;
            cur.next = n;
        }

        System.out.println("Buku dengan ID '" + b.id + "' berhasil ditambahkan!");
    }

    // Mengecek apakah ID sudah ada dalam list
    private boolean isIdExists(String id) {
        Node cur = head;
        while (cur != null) {
            if (cur.data.id.equals(id)) {
                return true; // ditemukan
            }
            cur = cur.next;
        }
        return false; // tidak ada
    }

    // --- UI ENHANCEMENT: Tampilan buku lebih terstruktur ---
    public void showBooks() {
        if (head == null) {
            System.out.println("Tidak ada buku saat ini!");
            return;
        }

        Node cur = head;
        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║            DAFTAR SEMUA BUKU DI PERPUSTAKAAN      ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        
        int count = 1;
        while (cur != null) {
            Book b = cur.data;
            System.out.println("------------------------------------------------------");
            System.out.println(count++ + ". ID    : " + b.id);
            System.out.println("   Judul : " + b.title);
            System.out.println("   Penulis: " + b.author);
            System.out.println("   Tahun : " + b.year + " | Stok: " + b.stock);
            cur = cur.next;
        }
        System.out.println("------------------------------------------------------");
    }

    public void searchByTitle(String title) {
        Node cur = head;
        boolean found = false;

        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.printf("║      HASIL PENCARIAN UNTUK JUDUL: %-18s ║\n", title.toUpperCase());
        System.out.println("╚════════════════════════════════════════════════════╝");
        
        while (cur != null) {
            if (cur.data.title.equalsIgnoreCase(title)) {
                Book b = cur.data;
                if (!found) {
                    found = true;
                }
                System.out.println("------------------------------------------------------");
                System.out.println("ID    : " + b.id);
                System.out.println("Judul : " + b.title);
                System.out.println("Penulis: " + b.author);
                System.out.println("Tahun : " + b.year + " | Stok: " + b.stock);
            }
            cur = cur.next;
        }
        System.out.println("------------------------------------------------------");


        if (!found) {
            System.out.println("Buku tidak ditemukan!");
        }
    }
    
    public Book searchBook(String id) {
        Node cur = head;

        while (cur != null) {
            if (cur.data.id.equals(id)) {
                return cur.data;
            }
            cur = cur.next;
        }

        return null;
    }

    public void sortByTitle() {
        if (head == null) return;

        boolean swapped;

        do {
            swapped = false;
            Node cur = head;

            while (cur.next != null) {
                if (cur.data.title.compareToIgnoreCase(cur.next.data.title) > 0) {
                    Book temp = cur.data;
                    cur.data = cur.next.data;
                    cur.next.data = temp;
                    swapped = true;
                }
                cur = cur.next;
            }
        } while (swapped);
        
        System.out.println("Daftar buku berhasil diurutkan berdasarkan Judul!");
        showBooks(); // Panggilan BARU: tampilkan setelah diurutkan
    }
    
    public void sortById() {
        if (head == null) return;

        boolean swapped;

        do {
            swapped = false;
            Node cur = head;

            while (cur.next != null) {
                // Sorting logic menggunakan perbandingan String ID (Bubble Sort)
                if (cur.data.id.compareTo(cur.next.data.id) > 0) {
                    Book temp = cur.data;
                    cur.data = cur.next.data;
                    cur.next.data = temp;
                    swapped = true;
                }
                cur = cur.next;
            }
        } while (swapped);
        
        System.out.println("Daftar buku berhasil diurutkan berdasarkan ID Buku!");
        showBooks(); // Panggilan BARU: tampilkan setelah diurutkan
    }
}