public class Book {
    String id;
    String title;
    String author;
    int year;
    int stock;

    public Book(String id, String title, String author, int year, int stock) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.stock = stock;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}