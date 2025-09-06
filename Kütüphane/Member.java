import java.util.ArrayList;

public class Member extends User {
    private boolean approved;
    private ArrayList<Book> borrowedBooks;

    public Member(String email, String password) {
        super(email, password);
        this.approved = false;
        this.borrowedBooks = new ArrayList<>();
    }

    @Override
    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    @Override
    public String toString() {
        return email;
    }
}
