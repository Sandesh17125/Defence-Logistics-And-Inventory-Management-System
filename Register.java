import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class Register extends JFrame {

    JTextField user;
    JPasswordField pass;
    JButton register;

    Register() {
        setTitle("Register");
        setSize(300, 250);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel l1 = new JLabel("Username:");
        l1.setBounds(30, 30, 80, 20);
        add(l1);

        user = new JTextField();
        user.setBounds(120, 30, 120, 20);
        add(user);

        JLabel l2 = new JLabel("Password:");
        l2.setBounds(30, 70, 80, 20);
        add(l2);

        pass = new JPasswordField();
        pass.setBounds(120, 70, 120, 20);
        add(pass);

        register = new JButton("Register");
        register.setBounds(90, 120, 100, 30);
        add(register);

        register.addActionListener(e -> {
            try {
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("INSERT INTO login VALUES (?,?)");

                ps.setString(1, user.getText());
                ps.setString(2, pass.getText());

                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Registered Successfully");

            } catch (Exception ex) {
                System.out.println(ex);
            }
        });

        setVisible(true);
    }
}