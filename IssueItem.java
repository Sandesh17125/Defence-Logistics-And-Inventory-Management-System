import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class IssueItem extends JFrame {

    JTextField id, qty;
    JButton issueBtn, back;

    IssueItem() {
        setTitle("Issue Item");
        setSize(420, 300);
        setLayout(null);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(30, 40, 60));

        // 🔙 BACK BUTTON
        back = new JButton("Back");
        back.setBounds(20, 20, 80, 30);
        add(back);

        back.addActionListener(e -> {
            new Dashboard();
            dispose(); // 🔥 close current window
        });

        // Title
        JLabel title = new JLabel("Issue Inventory Item");
        title.setBounds(110, 20, 250, 30);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        add(title);

        // Item ID
        JLabel l1 = new JLabel("Item ID:");
        l1.setBounds(50, 80, 100, 25);
        l1.setForeground(Color.WHITE);
        add(l1);

        id = new JTextField();
        id.setBounds(150, 80, 180, 25);
        add(id);

        // Quantity
        JLabel l2 = new JLabel("Quantity:");
        l2.setBounds(50, 120, 100, 25);
        l2.setForeground(Color.WHITE);
        add(l2);

        qty = new JTextField();
        qty.setBounds(150, 120, 180, 25);
        add(qty);

        // Button
        issueBtn = new JButton("Issue Item");
        issueBtn.setBounds(120, 180, 160, 40);
        issueBtn.setBackground(new Color(0, 153, 255));
        issueBtn.setForeground(Color.WHITE);
        add(issueBtn);

        // 🔥 MAIN LOGIC
        issueBtn.addActionListener(e -> {
            try {

                if (id.getText().isEmpty() || qty.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Fill all fields!");
                    return;
                }

                int itemId = Integer.parseInt(id.getText().trim());
                int issueQty = Integer.parseInt(qty.getText().trim());

                Connection con = DBConnection.getConnection();

                // 🔍 Check item
                String check = "SELECT * FROM items WHERE item_id=?";
                PreparedStatement psCheck = con.prepareStatement(check);
                psCheck.setInt(1, itemId);

                ResultSet rs = psCheck.executeQuery();

                if (rs.next()) {

                    int currentQty = rs.getInt("quantity");

                    if (issueQty > currentQty) {
                        JOptionPane.showMessageDialog(null, "Not enough stock!");
                        return;
                    }

                    // 🔥 Update quantity
                    String update = "UPDATE items SET quantity = quantity - ? WHERE item_id=?";
                    PreparedStatement ps = con.prepareStatement(update);
                    ps.setInt(1, issueQty);
                    ps.setInt(2, itemId);
                    ps.executeUpdate();

                    // 🔥 Transaction insert
                    String insert = "INSERT INTO inventory_transactions VALUES (" +
                            "trans_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

                    PreparedStatement ps2 = con.prepareStatement(insert);

                    ps2.setInt(1, rs.getInt("item_id"));
                    ps2.setString(2, rs.getString("item_name"));
                    ps2.setInt(3, issueQty);

                    ps2.setString(4, rs.getString("armoured_combat_vehicles"));
                    ps2.setString(5, rs.getString("infantry_fighting_vehicles"));
                    ps2.setString(6, rs.getString("miscellaneous_tracked_vehicles"));
                    ps2.setString(7, rs.getString("armoured_personnel_carriers"));
                    ps2.setString(8, rs.getString("utility_and_staff_transport"));
                    ps2.setString(9, rs.getString("field_transport_vehicles"));
                    ps2.setString(10, rs.getString("howitzers_and_fieldguns"));
                    ps2.setString(11, rs.getString("air_defence_systems"));

                    ps2.setString(12, "ISSUE");

                    ps2.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Item Issued + Transaction Saved ✅");

                    id.setText("");
                    qty.setText("");

                    con.close();

                } else {
                    JOptionPane.showMessageDialog(null, "Item Not Found ❌");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}