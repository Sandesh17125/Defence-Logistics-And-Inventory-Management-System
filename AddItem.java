import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddItem extends JFrame {

    JTextField id, name, qty;
    JTextField acv, ifv, mtv, apc, ust, ftv, hfg, ads;
    JButton save, back;

    AddItem() {
        setTitle("Add Defence Item");
        setSize(500, 600);
        setLocationRelativeTo(null);

        // 🔥 Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(13, 2, 10, 10));

        // 🔙 BACK BUTTON (TOP)
        back = new JButton("Back");
        panel.add(back);
        panel.add(new JLabel(""));

        back.addActionListener(e -> {
            new Dashboard();
            dispose(); // 🔥 close AddItem
        });

        // Fields
        panel.add(new JLabel("Item ID:"));
        id = new JTextField();
        panel.add(id);

        panel.add(new JLabel("Item Name:"));
        name = new JTextField();
        panel.add(name);

        panel.add(new JLabel("Quantity:"));
        qty = new JTextField();
        panel.add(qty);

        panel.add(new JLabel("Armoured Combat Vehicles:"));
        acv = new JTextField();
        panel.add(acv);

        panel.add(new JLabel("Infantry Fighting Vehicles:"));
        ifv = new JTextField();
        panel.add(ifv);

        panel.add(new JLabel("Misc Tracked Vehicles:"));
        mtv = new JTextField();
        panel.add(mtv);

        panel.add(new JLabel("Armoured Personnel Carriers:"));
        apc = new JTextField();
        panel.add(apc);

        panel.add(new JLabel("Utility & Staff Transport:"));
        ust = new JTextField();
        panel.add(ust);

        panel.add(new JLabel("Field Transport Vehicles:"));
        ftv = new JTextField();
        panel.add(ftv);

        panel.add(new JLabel("Howitzers & Field Guns:"));
        hfg = new JTextField();
        panel.add(hfg);

        panel.add(new JLabel("Air Defence Systems:"));
        ads = new JTextField();
        panel.add(ads);

        // Save Button
        save = new JButton("Save");
        panel.add(save);
        panel.add(new JLabel(""));

        JScrollPane scroll = new JScrollPane(panel);
        add(scroll);

        // 🔥 SAVE ACTION
        save.addActionListener(e -> {
            try {

                if (id.getText().isEmpty() || name.getText().isEmpty() || qty.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill required fields");
                    return;
                }

                int itemId = Integer.parseInt(id.getText().trim());
                int quantity = Integer.parseInt(qty.getText().trim());

                Connection con = DBConnection.getConnection();

                String sql = "INSERT INTO items VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(sql);

                ps.setInt(1, itemId);
                ps.setString(2, name.getText());
                ps.setInt(3, quantity);

                ps.setString(4, acv.getText());
                ps.setString(5, ifv.getText());
                ps.setString(6, mtv.getText());
                ps.setString(7, apc.getText());
                ps.setString(8, ust.getText());
                ps.setString(9, ftv.getText());
                ps.setString(10, hfg.getText());
                ps.setString(11, ads.getText());

                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Item Added Successfully ✅");

                // Clear fields
                id.setText("");
                name.setText("");
                qty.setText("");
                acv.setText("");
                ifv.setText("");
                mtv.setText("");
                apc.setText("");
                ust.setText("");
                ftv.setText("");
                hfg.setText("");
                ads.setText("");

                con.close();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new AddItem();
    }
}