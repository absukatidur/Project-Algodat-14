public class BorrowQueue {

    private static class Node {
        Main.BorrowData data;
        Node next;

        Node(Main.BorrowData data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node front, rear;

    public BorrowQueue() {
        front = rear = null;
    }

    public void enqueue(Main.BorrowData data) {
        Node newNode = new Node(data);
        if (rear == null) { 
            front = rear = newNode;
            return;
        }
        rear.next = newNode;
        rear = newNode;
    }

    public Main.BorrowData dequeue() {
        if (front == null) {
            return null;
        }

        Main.BorrowData data = front.data;
        front = front.next;

        if (front == null) {
            rear = null;
        }

        return data;
    }

    public boolean isEmpty() {
        return front == null;
    }

    // --- UI ENHANCEMENT: Tampilan antrian dengan bingkai ---
    public void showQueue() {
        if (isEmpty()) {
            System.out.println("✅ Antrian peminjaman kosong.");
            return;
        }

        Node temp = front;
        System.out.println("╔═════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                 ⏳ DAFTAR ANTRIAN PEMINJAMAN (FIFO)                 ║");
        System.out.println("╠═════════════════════════════════════════════════════════════════════════╣");
        int count = 1;
        while (temp != null) {
            System.out.printf("║ %-3s %s\n", count++ + ".", temp.data.toString());
            temp = temp.next;
        }
        System.out.println("╚═════════════════════════════════════════════════════════════════════════╝");
    }
}