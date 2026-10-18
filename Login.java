import javax.swing.*;
import java.awt.*;
import java.sql.*;
public class Login extends JFrame {

    JTextField user;
    JPasswordField pass;
    JButton btn, registerBtn, forgotBtn;
    JCheckBox showPass, darkMode;

    Login() {
        setTitle("Defence Login");
        setSize(420, 420);
        setLayout(null);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(20, 20, 360, 350);
        panel.setBackground(Color.WHITE);
        add(panel);

        JLabel title = new JLabel("Login System");
        title.setBounds(120, 20, 200, 25);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(title);

        user = new JTextField();
        user.setBounds(80, 70, 200, 25);
        panel.add(user);

        pass = new JPasswordField();
        pass.setBounds(80, 110, 200, 25);
        panel.add(pass);

        showPass = new JCheckBox("Show Password");
        showPass.setBounds(80, 140, 150, 20);
        panel.add(showPass);

        showPass.addActionListener(e -> {
            if (showPass.isSelected()) pass.setEchoChar((char) 0);
            else pass.setEchoChar('*');
        });

        btn = new JButton("Login");
        btn.setBounds(130, 170, 120, 35);
        btn.setBackground(Color.BLUE);
        btn.setForeground(Color.WHITE);
        panel.add(btn);

        JProgressBar bar = new JProgressBar();
        bar.setBounds(80, 210, 200, 20);
        bar.setVisible(false);
        panel.add(bar);

        btn.addActionListener(e -> {
            bar.setVisible(true);

            new Thread(() -> {
                try {
                    for (int i = 0; i <= 100; i += 20) {
                        Thread.sleep(100);
                        bar.setValue(i);
                    }

                    Connection con = DBConnection.getConnection();

                    String sql = "SELECT * FROM login WHERE username=? AND password=?";
                    PreparedStatement ps = con.prepareStatement(sql);

                    ps.setString(1, user.getText().trim());
                    ps.setString(2, pass.getText().trim());

                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {

                        // 🔥 GREEN SUCCESS UI
                        JFrame successFrame = new JFrame();
                        successFrame.setSize(350, 200);
                        successFrame.setLocationRelativeTo(null);
                        successFrame.setUndecorated(true);

                        JPanel successPanel = new JPanel();
                        successPanel.setBackground(new Color(40, 167, 69)); // GREEN
                        successPanel.setLayout(new BorderLayout());

                        JLabel msg = new JLabel("✔ Login Successful", JLabel.CENTER);
                        msg.setFont(new Font("Segoe UI", Font.BOLD, 18));
                        msg.setForeground(Color.WHITE);

                        successPanel.add(msg, BorderLayout.CENTER);
                        successFrame.add(successPanel);

                        successFrame.setVisible(true);

                        new Thread(() -> {
                            try {
                                Thread.sleep(2000);
                                successFrame.dispose();
                                new Dashboard();
                                dispose(); // close login
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }).start();

                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Login ❌");
                    }

                    con.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }).start();
        });

        forgotBtn = new JButton("Forgot Password");
        forgotBtn.setBounds(80, 240, 200, 25);
        panel.add(forgotBtn);

        forgotBtn.addActionListener(e -> {
            String uname = JOptionPane.showInputDialog("Enter Username:");
            try {
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT password FROM login WHERE username=?");
                ps.setString(1, uname);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Password: " + rs.getString(1));
                } else {
                    JOptionPane.showMessageDialog(null, "User not found");
                }

                con.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        registerBtn = new JButton("Register");
        registerBtn.setBounds(80, 270, 200, 25);
        panel.add(registerBtn);

        registerBtn.addActionListener(e -> {
            new Register();
            dispose();
        });

        darkMode = new JCheckBox("Dark Mode");
        darkMode.setBounds(80, 300, 200, 25);
        panel.add(darkMode);

        darkMode.addActionListener(e -> {
            if (darkMode.isSelected()) {
                panel.setBackground(Color.DARK_GRAY);
                title.setForeground(Color.WHITE);
            } else {
                panel.setBackground(Color.WHITE);
                title.setForeground(Color.BLACK);
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Login();
    }
}