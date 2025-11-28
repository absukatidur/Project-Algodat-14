public class LinkedListBook {

    class Node {
        Book data;
        Node next;
        Node(Book b) { data = b; }
    }

    Node head;

    public void addBook(Book b) {
        Node n = new Node(b);

        if (head == null) {
            head = n;
        } else {
            Node cur = head;
            while (cur.next != null)
                cur = cur.next;
            cur.next = n;
        }
    }

    public void showBooks() {
        if (head == null) {
            System.out.println("Tidak ada buku!");
            return;
        }

        Node cur = head;
        System.out.println("=== DAFTAR SEMUA BUKU ===");
        while (cur != null) {
            Book b = cur.data;
            System.out.println("ID: " + b.id + " | " + b.title + " | "
                    + b.author + " | Tahun: " + b.year + " | Stok: " + b.stock);
            cur = cur.next;
        }
    }

    public void searchByTitle(String title) {
        Node cur = head;
        boolean found = false;

        while (cur != null) {
            if (cur.data.title.equalsIgnoreCase(title)) {
                Book b = cur.data;
                if (!found) {
                    System.out.println("Buku ditemukan:");
                    found = true;
                }
                System.out.println("ID: " + b.id + " | " + b.title + " | "
                        + b.author + " | Tahun: " + b.year + " | Stok: " + b.stock);
            }
            cur = cur.next;
        }

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
    }
}