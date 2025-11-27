public class BookTree {

    class Node {
        Book data;
        Node left, right;

        Node(Book b) { data = b; }
    }

    Node root;

    public void insert(Book b) {
        root = insertRec(root, b);
    }

    private Node insertRec(Node r, Book b) {
        if (r == null) return new Node(b);

        // FIX 5: Menggunakan String.compareTo() untuk membandingkan ID String
        if (b.id.compareTo(r.data.id) < 0)
            r.left = insertRec(r.left, b);
        else
            r.right = insertRec(r.right, b);

        return r;
    }

    // FIX 5: Mengubah parameter search menjadi String id
    public Book search(String id) {
        return searchRec(root, id);
    }

    // FIX 5: Mengubah parameter searchRec menjadi String id dan menggunakan String.compareTo()
    private Book searchRec(Node r, String id) {
        if (r == null) return null;

        int compareResult = id.compareTo(r.data.id);

        if (compareResult == 0) return r.data;

        return (compareResult < 0) ? searchRec(r.left, id) : searchRec(r.right, id);
    }
}