import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewItems extends JFrame {

    JTable table;
    DefaultTableModel model;

    ViewItems() {
        setTitle("View Items");
        setSize(1200, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 🔙 TOP PANEL (TITLE + BACK BUTTON)
        JPanel topPanel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Defence Inventory System", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JButton back = new JButton("Back");

        topPanel.add(back, BorderLayout.WEST);
        topPanel.add(title, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // 🔙 BACK ACTION
        back.addActionListener(e -> {
            new Dashboard();
            dispose(); // 🔥 close current window
        });

        // Table Model
        model = new DefaultTableModel();
        table = new JTable(model);

        // 🔥 ALL 11 COLUMNS
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Qty");
        model.addColumn("ACV");
        model.addColumn("IFV");
        model.addColumn("MTV");
        model.addColumn("APC");
        model.addColumn("UST");
        model.addColumn("FTV");
        model.addColumn("HFG");
        model.addColumn("ADS");

        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM items");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("item_id"),
                        rs.getString("item_name"),
                        rs.getInt("quantity"),
                        rs.getString("armoured_combat_vehicles"),
                        rs.getString("infantry_fighting_vehicles"),
                        rs.getString("miscellaneous_tracked_vehicles"),
                        rs.getString("armoured_personnel_carriers"),
                        rs.getString("utility_and_staff_transport"),
                        rs.getString("field_transport_vehicles"),
                        rs.getString("howitzers_and_fieldguns"),
                        rs.getString("air_defence_systems")
                });
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}