public class BorrowQueue {

    private static class Node {
        String data;
        Node next;

        Node(String data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node front, rear;

    public BorrowQueue() {
        front = rear = null;
    }

    // Tambah ke antrian
    public void enqueue(String data) {
        Node newNode = new Node(data);
        if (rear == null) { 
            front = rear = newNode;
            return;
        }
        rear.next = newNode;
        rear = newNode;
    }

    // Keluarkan dari antrian
    public String dequeue() {
        if (front == null) {
            return null;
        }

        String data = front.data;
        front = front.next;

        if (front == null) {
            rear = null;
        }

        return data;
    }

    // Cek apakah queue kosong
    public boolean isEmpty() {
        return front == null;
    }

    public void showQueue() {
        if (isEmpty()) {
            System.out.println("Antrian kosong");
            return;
        }

        Node temp = front;
        System.out.println("Daftar Antrian Peminjam:");
        while (temp != null) {
            System.out.println("- " + temp.data);
            temp = temp.next;
        }
    }
}