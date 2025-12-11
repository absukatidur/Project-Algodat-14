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
        if (b.id.compareTo(r.data.id) < 0)
            r.left = insertRec(r.left, b);
        else
            r.right = insertRec(r.right, b);
        return r;
    }

    public Book search(String id) {
        return searchRec(root, id);
    }

    private Book searchRec(Node r, String id) {
        if (r == null) return null;
        int val = id.compareTo(r.data.id);
        if (val == 0) return r.data;
        if (val < 0) return searchRec(r.left, id);
        else return searchRec(r.right, id);
    }
}