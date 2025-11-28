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

    public void showQueue() {
        if (isEmpty()) {
            System.out.println("Antrian kosong");
            return;
        }

        Node temp = front;
        System.out.println("Daftar Antrian Peminjam:");
        while (temp != null) {
            System.out.println("- " + temp.data.toString());
            temp = temp.next;
        }
    }
}