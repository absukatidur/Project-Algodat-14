public class BorrowHistory {
    class StackNode {
        String log;
        StackNode next;

        public StackNode(String log) {
            this.log = log;
            this.next = null;
        }
    }

    private StackNode top; // Elemen teratas tumpukan

    public BorrowHistory() {
        this.top = null;
    }

    // Push: Tambah data ke paling atas (Head)
    public void addHistory(String type, Main.BorrowData data) {
        String entry = "[" + type + "] " + data.toString();
        StackNode newNode = new StackNode(entry);
        
        if (top == null) {
            top = newNode;
        } else {
            newNode.next = top; // Tumpuk di atas head lama
            top = newNode;      // Update head jadi node baru
        }
    }
    
    public void addHistorySimple(String type, String bookId, String returnDate) {
        String entry = "[" + type + "] ID Buku: " + bookId + " | Tgl Kembali: " + returnDate;
        StackNode newNode = new StackNode(entry);
        
        if (top == null) {
            top = newNode;
        } else {
            newNode.next = top;
            top = newNode;
        }
    }

    public void showHistory() {
        if (top == null) {
            System.out.println("  (Riwayat Kosong)");
            return;
        }
        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║         RIWAYAT TRANSAKSI TERAKHIR (LIFO)          ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        
        // Iterasi dari Top ke Bawah
        StackNode cur = top;
        while (cur != null) {
             System.out.println(">> " + cur.log);
             cur = cur.next;
        }
    }
}