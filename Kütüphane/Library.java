import java.util.ArrayList;

public class Library {
    private ArrayList<Book> books;

    public Library() {
        books = new ArrayList<>();
    }

    public void initializeBooks() {
        books.add(new Book(1, "Suç ve Ceza", 3));
        books.add(new Book(2, "Sefiller", 2));
        books.add(new Book(3, "Simyacı", 4));
        books.add(new Book(4, "Kürk Mantolu Madonna", 3));
        books.add(new Book(5, "1984", 5));
        books.add(new Book(6, "Hayvan Çiftliği", 2));
        books.add(new Book(7, "Tutunamayanlar", 1));
        books.add(new Book(8, "Beyaz Gemi", 2));
        books.add(new Book(9, "Bilinmeyen Bir Kadının Mektubu", 3));
        books.add(new Book(10, "Yeraltından Notlar", 2));
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public Book getBookById(int id) {
        for (Book b : books) {
            if (b.getId() == id) {
                return b;
            }
        }
        return null;
    }
}
