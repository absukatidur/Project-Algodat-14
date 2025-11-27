public class Book {
    // FIX 1: Mengubah id menjadi String
    String id;
    String title;
    String author;
    int year;
    int stock;

    // FIX 1: Mengubah konstruktor untuk menerima String id
    public Book(String id, String title, String author, int year, int stock) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.stock = stock;
    }

    // FIX 2: Menambahkan public getter untuk stock (dipanggil di Main.java)
    public int getStock() {
        return stock;
    }

    // FIX 2: Menambahkan public setter untuk stock (dipanggil di Main.java)
    public void setStock(int stock) {
        this.stock = stock;
    }
}