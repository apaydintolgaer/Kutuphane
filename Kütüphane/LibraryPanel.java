import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LibraryPanel extends JPanel {
    private Member member;
    private Library library;
    private MainFrame frame;

    private JTextArea booksArea;
    private JTextField bookIdField;
    private JButton borrowBtn, returnBtn, logoutBtn;

    public LibraryPanel(Member member, Library library, MainFrame frame) {
        this.member = member;
        this.library = library;
        this.frame = frame;

        setLayout(new BorderLayout(10, 10));

        booksArea = new JTextArea();
        booksArea.setEditable(false);
        booksArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(booksArea);

        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Kitap ID:"));

        bookIdField = new JTextField(5);
        controlPanel.add(bookIdField);

        borrowBtn = new JButton("Kitap Al");
        returnBtn = new JButton("Kitap İade");
        logoutBtn = new JButton("Çıkış Yap");

        controlPanel.add(borrowBtn);
        controlPanel.add(returnBtn);
        controlPanel.add(logoutBtn);

        add(scrollPane, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        updateBookList();

        borrowBtn.addActionListener(e -> borrowBook());
        returnBtn.addActionListener(e -> returnBook());
        logoutBtn.addActionListener(e -> {
            frame.showNotification("Çıkış yapıldı.");
            frame.showLogin();
        });
    }

    public void updateBookList() {
        StringBuilder sb = new StringBuilder();
        sb.append("---- Kütüphane Kitapları ----\n");
        for (Book b : library.getBooks()) {
            sb.append(String.format("ID: %d | %-30s | Stok: %d\n", b.getId(), b.getTitle(), b.getStock()));
        }
        booksArea.setText(sb.toString());
    }

    private void borrowBook() {
        String input = bookIdField.getText().trim();
        if (input.isEmpty()) {
            frame.showNotification("Lütfen kitap ID girin.");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            frame.showNotification("Geçerli bir sayı girin.");
            return;
        }

        Book book = library.getBookById(id);
        if (book == null) {
            frame.showNotification("Kitap bulunamadı.");
            return;
        }

        if (book.getStock() == 0) {
            frame.showNotification("Kitap stokta yok.");
            return;
        }

        if (member.getBorrowedBooks().contains(book)) {
            frame.showNotification("Bu kitabı zaten aldınız.");
            return;
        }

        book.decreaseStock();
        member.getBorrowedBooks().add(book);
        updateBookList();
        frame.showNotification(book.getTitle() + " ödünç alındı.");
        bookIdField.setText("");
    }

    private void returnBook() {
        String input = bookIdField.getText().trim();
        if (input.isEmpty()) {
            frame.showNotification("Lütfen kitap ID girin.");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            frame.showNotification("Geçerli bir sayı girin.");
            return;
        }

        Book book = library.getBookById(id);
        if (book == null) {
            frame.showNotification("Kitap bulunamadı.");
            return;
        }

        if (!member.getBorrowedBooks().contains(book)) {
            frame.showNotification("Bu kitabı siz almadınız.");
            return;
        }

        book.increaseStock();
        member.getBorrowedBooks().remove(book);
        updateBookList();
        frame.showNotification(book.getTitle() + " iade edildi.");
        bookIdField.setText("");
    }
}
