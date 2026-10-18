import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;
import java.io.FileWriter;

public class ViewTransactions extends JFrame {

    JTable table;
    DefaultTableModel model;
    JTextField searchField;

    ViewTransactions() {
        setTitle("Transaction History");
        setSize(1300, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(25, 35, 55));
        add(panel);

        JPanel topBar = new JPanel(new BorderLayout());

        JButton back = new JButton("Back");
        topBar.add(back, BorderLayout.WEST);

        JLabel title = new JLabel("Inventory Transactions", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.BLACK);
        topBar.add(title, BorderLayout.CENTER);

        panel.add(topBar, BorderLayout.NORTH);

        back.addActionListener(e -> {
            new Dashboard();
            dispose();
        });

        JPanel bottomPanel = new JPanel();
        searchField = new JTextField(20);
        JButton searchBtn = new JButton("Search");
        JButton refreshBtn = new JButton("Refresh");
        JButton exportBtn = new JButton("Export CSV");

        bottomPanel.add(new JLabel("Search:"));
        bottomPanel.add(searchField);
        bottomPanel.add(searchBtn);
        bottomPanel.add(refreshBtn);
        bottomPanel.add(exportBtn);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        model = new DefaultTableModel();
        table = new JTable(model);

        String[] cols = {"TID","Item ID","Item Name","Qty","ACV","IFV","MTV",
                "APC","UST","FTV","HFG","ADS","Type","Date"};

        for (String c : cols) model.addColumn(c);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {

                Component c = super.getTableCellRendererComponent(t, val, sel, foc, row, col);

                String type = t.getValueAt(row, 12).toString();

                if (type.equals("ISSUE")) {
                    c.setBackground(new Color(255, 200, 200));
                } else {
                    c.setBackground(new Color(200, 255, 200));
                }

                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        panel.add(scroll, BorderLayout.CENTER);

        loadData("");

        searchBtn.addActionListener(e -> loadData(searchField.getText()));

        refreshBtn.addActionListener(e -> {
            searchField.setText("");
            loadData("");
        });

        exportBtn.addActionListener(e -> {
            try {
                FileWriter fw = new FileWriter("transactions.csv");

                for (int i = 0; i < model.getColumnCount(); i++) {
                    fw.write(model.getColumnName(i) + ",");
                }
                fw.write("\n");

                for (int i = 0; i < model.getRowCount(); i++) {
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        fw.write(model.getValueAt(i, j).toString() + ",");
                    }
                    fw.write("\n");
                }

                fw.close();
                JOptionPane.showMessageDialog(null, "Exported to transactions.csv");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    void loadData(String keyword) {
        try {
            model.setRowCount(0);

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM inventory_transactions WHERE item_name LIKE ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getTimestamp(14)
                });
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}