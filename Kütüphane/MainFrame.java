import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    CardLayout cardLayout = new CardLayout();
    JPanel mainPanel = new JPanel(cardLayout);

    Library library = new Library();
    ArrayList<Member> pendingMembers = new ArrayList<>();
    ArrayList<Member> approvedMembers = new ArrayList<>();
    Admin admin = new Admin();

    private Member currentMember = null;

    private JLabel notificationLabel = new JLabel("Hoşgeldiniz!");

    LoginPanel loginPanel;
    AdminPanel adminPanel;
    LibraryPanel libraryPanel;

    public MainFrame() {
        setTitle("Kütüphane Yönetim Sistemi");
        setSize(550, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        library.initializeBooks();

        loginPanel = new LoginPanel();
        adminPanel = new AdminPanel();

        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(adminPanel, "ADMIN");

        add(mainPanel, BorderLayout.CENTER);

        notificationLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(notificationLabel, BorderLayout.SOUTH);

        cardLayout.show(mainPanel, "LOGIN");
    }

    public void showNotification(String message) {
        notificationLabel.setText(message);
    }

    public void showLogin() {
        currentMember = null;
        cardLayout.show(mainPanel, "LOGIN");
    }

    class LoginPanel extends JPanel {
        JTextField emailField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginBtn = new JButton("Giriş Yap");
        JButton registerBtn = new JButton("Kayıt Ol");

        public LoginPanel() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            gbc.gridx = 0; gbc.gridy = 0;
            add(new JLabel("E-posta:"), gbc);

            gbc.gridx = 1;
            add(emailField, gbc);

            gbc.gridx = 0; gbc.gridy = 1;
            add(new JLabel("Şifre:"), gbc);

            gbc.gridx = 1;
            add(passwordField, gbc);

            gbc.gridx = 0; gbc.gridy = 2;
            add(loginBtn, gbc);

            gbc.gridx = 1;
            add(registerBtn, gbc);

            loginBtn.addActionListener(e -> login());
            registerBtn.addActionListener(e -> register());
        }

        private void login() {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (email.equals(admin.getEmail()) && password.equals(admin.getPassword())) {
                showNotification("Admin olarak giriş yapıldı.");
                cardLayout.show(mainPanel, "ADMIN");
                adminPanel.updatePendingList();
                emailField.setText("");
                passwordField.setText("");
                return;
            }

            for (Member m : approvedMembers) {
                if (m.getEmail().equals(email) && m.getPassword().equals(password)) {
                    currentMember = m;
                    showNotification("Hoşgeldiniz, " + email);
                    if (libraryPanel != null) {
                        mainPanel.remove(libraryPanel);
                    }
                    libraryPanel = new LibraryPanel(currentMember, library, MainFrame.this);
                    mainPanel.add(libraryPanel, "LIBRARY");
                    cardLayout.show(mainPanel, "LIBRARY");
                    emailField.setText("");
                    passwordField.setText("");
                    return;
                }
            }
            showNotification("Giriş başarısız. Bilgileri kontrol edin veya üyeliğiniz onay bekliyor.");
        }

        private void register() {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                showNotification("Geçerli bir e-posta giriniz.");
                return;
            }
            if (password.length() < 4) {
                showNotification("Şifre en az 4 karakter olmalı.");
                return;
            }

            for (Member m : pendingMembers) {
                if (m.getEmail().equals(email)) {
                    showNotification("Bu e-posta ile kayıt talebiniz zaten var.");
                    return;
                }
            }

            for (Member m : approvedMembers) {
                if (m.getEmail().equals(email)) {
                    showNotification("Bu e-posta zaten kayıtlı.");
                    return;
                }
            }

            Member newMember = new Member(email, password);
            pendingMembers.add(newMember);
            showNotification("Kayıt talebiniz alındı, onay bekleniyor.");
            emailField.setText("");
            passwordField.setText("");
        }
    }

    class AdminPanel extends JPanel {
        DefaultListModel<Member> pendingModel = new DefaultListModel<>();
        JList<Member> pendingList = new JList<>(pendingModel);
        JButton approveBtn = new JButton("Onayla");
        JButton rejectBtn = new JButton("Reddet");
        JButton logoutBtn = new JButton("Çıkış Yap");

        public AdminPanel() {
            setLayout(new BorderLayout(10, 10));
            add(new JLabel("Onay Bekleyen Üyeler"), BorderLayout.NORTH);
            add(new JScrollPane(pendingList), BorderLayout.CENTER);

            JPanel btnPanel = new JPanel();
            btnPanel.add(approveBtn);
            btnPanel.add(rejectBtn);
            btnPanel.add(logoutBtn);
            add(btnPanel, BorderLayout.SOUTH);

            approveBtn.addActionListener(e -> approveMember());
            rejectBtn.addActionListener(e -> rejectMember());
            logoutBtn.addActionListener(e -> {
                cardLayout.show(mainPanel, "LOGIN");
                showNotification("Admin çıkış yaptı.");
            });
        }

        public void updatePendingList() {
            pendingModel.clear();
            for (Member m : pendingMembers) {
                pendingModel.addElement(m);
            }
        }

        private void approveMember() {
            Member selected = pendingList.getSelectedValue();
            if (selected == null) {
                showNotification("Bir üye seçmelisiniz.");
                return;
            }
            selected.setApproved(true);
            approvedMembers.add(selected);
            pendingMembers.remove(selected);
            updatePendingList();
            showNotification(selected.getEmail() + " onaylandı.");
        }

        private void rejectMember() {
            Member selected = pendingList.getSelectedValue();
            if (selected == null) {
                showNotification("Bir üye seçmelisiniz.");
                return;
            }
            pendingMembers.remove(selected);
            updatePendingList();
            showNotification(selected.getEmail() + " reddedildi.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
