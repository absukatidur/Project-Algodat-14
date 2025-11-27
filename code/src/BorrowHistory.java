import java.util.Stack;

public class BorrowHistory {
    Stack<String> history = new Stack<>();

    // FIX 4: Mengubah push menjadi addHistory untuk mencocokkan penggunaan di Main.java
    public void addHistory(String type, String id) {
        String historyEntry = String.format("[%s] ID Buku: %s", type, id);
        history.push(historyEntry);
    }

    public void showHistory() {
        if (history.isEmpty()) {
            System.out.println("History kosong!");
            return;
        }

        System.out.println("=== HISTORY PEMINJAMAN ===");
        for (String h : history)
            System.out.println(h);
    }
}