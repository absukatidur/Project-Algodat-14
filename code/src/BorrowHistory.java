import java.util.Stack;

public class BorrowHistory {
    Stack<String> history = new Stack<>();

    public void addHistory(String type, Main.BorrowData data) {
        String historyEntry = String.format("[%s] %s", type, data.toString());
        history.push(historyEntry);
    }
    
    public void addHistorySimple(String type, String bookId, String returnDate) {
        String historyEntry = String.format("[%s] ID Buku: %s | Tanggal Kembali: %s", type, bookId, returnDate);
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