import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    JButton addItem, viewItems, issueItem, returnItem, transBtn;

    Dashboard() {
        setTitle("Defence Dashboard");
        setSize(500, 500);
        setLayout(null);

        // Center window
        setLocationRelativeTo(null);

        // Background color
        getContentPane().setBackground(new Color(230, 230, 230));

        // Window icon
        setIconImage(new ImageIcon("DefenseLogo.jpg").getImage());

        // Logo
        ImageIcon icon = new ImageIcon("DefenseLogo.jpg");
        Image img = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(img));
        logo.setBounds(20, 10, 70, 70);
        add(logo);

        // Title
        JLabel title = new JLabel("Defence Inventory System");
        title.setBounds(110, 30, 320, 30);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.BLUE);
        add(title);

        Font btnFont = new Font("Arial", Font.BOLD, 14);

        // Add Item
        addItem = new JButton("Add Item");
        addItem.setBounds(150, 100, 180, 40);
        addItem.setBackground(new Color(144, 238, 144));
        addItem.setFont(btnFont);
        addItem.setFocusPainted(false);
        addItem.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        add(addItem);

        addItem.addActionListener(e -> {
            new AddItem();
            dispose(); // 🔥 FIX
        });

        // View Items
        viewItems = new JButton("View Items");
        viewItems.setBounds(150, 150, 180, 40);
        viewItems.setBackground(new Color(173, 216, 230));
        viewItems.setFont(btnFont);
        viewItems.setFocusPainted(false);
        viewItems.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        add(viewItems);

        viewItems.addActionListener(e -> {
            new ViewItems();
            dispose(); // 🔥 FIX
        });

        // Issue Item
        issueItem = new JButton("Issue Item");
        issueItem.setBounds(150, 200, 180, 40);
        issueItem.setBackground(new Color(255, 200, 150));
        issueItem.setFont(btnFont);
        issueItem.setFocusPainted(false);
        issueItem.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        add(issueItem);

        issueItem.addActionListener(e -> {
            new IssueItem();
            dispose(); // 🔥 FIX
        });

        // Return Item
        returnItem = new JButton("Return Item");
        returnItem.setBounds(150, 250, 180, 40);
        returnItem.setBackground(new Color(255, 182, 193));
        returnItem.setFont(btnFont);
        returnItem.setFocusPainted(false);
        returnItem.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        add(returnItem);

        returnItem.addActionListener(e -> {
            new ReturnItem();
            dispose(); // 🔥 FIX
        });

        // Transactions
        transBtn = new JButton("Transactions");
        transBtn.setBounds(150, 300, 180, 40);
        transBtn.setBackground(new Color(255, 255, 153));
        transBtn.setFont(btnFont);
        transBtn.setFocusPainted(false);
        transBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        add(transBtn);

        transBtn.addActionListener(e -> {
            new ViewTransactions();
            dispose(); 
        });

        JLabel footer = new JLabel("Developed by Defense");
        footer.setBounds(170, 400, 200, 20);
        footer.setFont(new Font("Arial", Font.ITALIC, 12));
        add(footer);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}