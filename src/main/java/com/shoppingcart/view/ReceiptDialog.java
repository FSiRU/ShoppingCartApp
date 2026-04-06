package main.java.com.shoppingcart.view;

import  main.java.com.shoppingcart.model.Cart;
import main.java.com.shoppingcart.model.Item;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReceiptDialog extends JDialog {
    private NumberFormat currencyFormat;

    public ReceiptDialog(JFrame parent, Cart cart) {
        super(parent, "Receipt", true);
        currencyFormat = NumberFormat.getCurrencyInstance();

        setLayout(new BorderLayout());
        setSize(400, 500);
        setLocationRelativeTo(parent);

        JPanel receiptPanel = createReceiptPanel(cart);
        add(receiptPanel, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        closeButton.setBackground(new Color(46, 204, 113));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createReceiptPanel(Cart cart) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        // Header
        JLabel titleLabel = new JLabel("SHOPPING CART RECEIPT");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel dateLabel = new JLabel(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(dateLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Separator
        panel.add(new JSeparator());
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Items
        for (Item item : cart.getItems()) {
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setBackground(Color.WHITE);
            itemPanel.setBorder(new EmptyBorder(5, 0, 5, 0));

            JLabel nameLabel = new JLabel(item.getQuantity() + " x " + item.getName());
            nameLabel.setFont(new Font("Arial", Font.PLAIN, 12));

            JLabel priceLabel = new JLabel(currencyFormat.format(item.getTotal()));
            priceLabel.setFont(new Font("Arial", Font.PLAIN, 12));

            itemPanel.add(nameLabel, BorderLayout.WEST);
            itemPanel.add(priceLabel, BorderLayout.EAST);
            panel.add(itemPanel);
        }

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(new JSeparator());
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Total
        JPanel totalPanel = new JPanel(new BorderLayout());
        totalPanel.setBackground(Color.WHITE);
        JLabel totalLabel = new JLabel("TOTAL:");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel totalAmountLabel = new JLabel(currencyFormat.format(cart.getTotal()));
        totalAmountLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalAmountLabel.setForeground(new Color(46, 204, 113));

        totalPanel.add(totalLabel, BorderLayout.WEST);
        totalPanel.add(totalAmountLabel, BorderLayout.EAST);
        panel.add(totalPanel);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Footer
        JLabel thankYouLabel = new JLabel("Thank you for shopping with us!");
        thankYouLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        thankYouLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(thankYouLabel);

        return panel;
    }
}