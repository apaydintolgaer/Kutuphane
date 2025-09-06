public class Admin extends User {

    public Admin() {
        super("admin@kutuphane.com", "admin1234");
    }

    @Override
    public boolean isApproved() {
        return true;
    }
}
