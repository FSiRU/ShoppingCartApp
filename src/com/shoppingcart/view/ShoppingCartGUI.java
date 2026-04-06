package com.shoppingcart.view;

import main.java.com.shoppingcart.model.Cart;
import main.java.com.shoppingcart.model.Item;
import main.java.com.shoppingcart.view.ReceiptDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;

public class ShoppingCartGUI extends JFrame {
    private Cart cart;
    private JTable cartTable;
    private DefaultTableModel tableModel;
    private JLabel totalLabel;
    private NumberFormat currencyFormat;

    // Color scheme
    private static final Color PRIMARY_COLOR = new Color(46, 204, 113);
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);

    public ShoppingCartGUI() {
        cart = new Cart();
        currencyFormat = NumberFormat.getCurrencyInstance();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Shopping Cart Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);

        createMenuBar();
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createCartPanel(), BorderLayout.CENTER);
        add(createInputPanel(), BorderLayout.SOUTH);

        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem newCartItem = new JMenuItem("New Cart");
        newCartItem.addActionListener(e -> newCart());
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(newCartItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAboutDialog());
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("🛒 My Shopping Cart");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);

        headerPanel.add(titleLabel);
        return headerPanel;
    }

    private JPanel createCartPanel() {
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        cartPanel.setBackground(BACKGROUND_COLOR);

        // Create table
        String[] columns = {"Item Name", "Quantity", "Price", "Total"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        cartTable = new JTable(tableModel);
        cartTable.setRowHeight(30);
        cartTable.setFont(new Font("Arial", Font.PLAIN, 14));
        cartTable.setForeground(Color.BLACK);  // FIX: Ensure text is black
        cartTable.setBackground(Color.WHITE);   // FIX: Ensure background is white
        cartTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        cartTable.getTableHeader().setBackground(SECONDARY_COLOR);
        cartTable.getTableHeader().setForeground(Color.BLACK);  // FIX: Header text black

        // Set column widths
        cartTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        cartTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        cartTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        cartTable.getColumnModel().getColumn(3).setPreferredWidth(120);

        JScrollPane scrollPane = new JScrollPane(cartTable);
        cartPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom panel with total and buttons
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        bottomPanel.setBackground(BACKGROUND_COLOR);

        totalLabel = new JLabel("Total: " + currencyFormat.format(0));
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalLabel.setForeground(PRIMARY_COLOR);
        totalLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        JButton removeButton = createStyledButton("Remove Selected", DANGER_COLOR);
        removeButton.addActionListener(e -> removeSelectedItem());

        JButton checkoutButton = createStyledButton("Checkout", PRIMARY_COLOR);
        checkoutButton.addActionListener(e -> checkout());

        buttonPanel.add(removeButton);
        buttonPanel.add(checkoutButton);

        bottomPanel.add(totalLabel, BorderLayout.WEST);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);
        cartPanel.add(bottomPanel, BorderLayout.SOUTH);

        return cartPanel;
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                "Add New Item",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                PRIMARY_COLOR
        ));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Item Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Item Name:");
        nameLabel.setForeground(Color.BLACK);  // FIX: Label text black
        inputPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField nameField = new JTextField(15);
        nameField.setForeground(Color.BLACK);   // FIX: Input text black
        nameField.setBackground(Color.WHITE);   // FIX: Input background white
        inputPanel.add(nameField, gbc);

        // Price
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setForeground(Color.BLACK);  // FIX: Label text black
        inputPanel.add(priceLabel, gbc);

        gbc.gridx = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField priceField = new JTextField(10);
        priceField.setForeground(Color.BLACK);   // FIX: Input text black
        priceField.setBackground(Color.WHITE);   // FIX: Input background white
        inputPanel.add(priceField, gbc);

        // Quantity
        gbc.gridx = 4;
        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setForeground(Color.BLACK);  // FIX: Label text black
        inputPanel.add(quantityLabel, gbc);

        gbc.gridx = 5;
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
        quantitySpinner.setPreferredSize(new Dimension(70, 25));
        // Fix spinner text color
        JComponent spinnerEditor = quantitySpinner.getEditor();
        if (spinnerEditor instanceof JSpinner.DefaultEditor) {
            JTextField spinnerTextField = ((JSpinner.DefaultEditor) spinnerEditor).getTextField();
            spinnerTextField.setForeground(Color.BLACK);
            spinnerTextField.setBackground(Color.WHITE);
        }
        inputPanel.add(quantitySpinner, gbc);

        // Add Button
        gbc.gridx = 6;
        JButton addButton = createStyledButton("Add to Cart", SECONDARY_COLOR);
        addButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                showError("Please enter an item name");
                return;
            }

            try {
                double price = Double.parseDouble(priceField.getText().trim());
                if (price <= 0) {
                    showError("Price must be greater than 0");
                    return;
                }

                int quantity = (int) quantitySpinner.getValue();

                Item item = new Item(name, price, quantity);
                cart.addItem(item);
                updateCartDisplay();

                // Clear fields
                nameField.setText("");
                priceField.setText("");
                quantitySpinner.setValue(1);
                nameField.requestFocus();

            } catch (NumberFormatException ex) {
                showError("Please enter a valid price");
            }
        });
        inputPanel.add(addButton, gbc);

        return inputPanel;
    }

    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(120, 35));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });

        return button;
    }

    private void updateCartDisplay() {
        tableModel.setRowCount(0);

        for (Item item : cart.getItems()) {
            Object[] row = {
                    item.getName(),
                    item.getQuantity(),
                    currencyFormat.format(item.getPrice()),
                    currencyFormat.format(item.getTotal())
            };
            tableModel.addRow(row);
        }

        totalLabel.setText("Total: " + currencyFormat.format(cart.getTotal()));
    }

    private void removeSelectedItem() {
        int selectedRow = cartTable.getSelectedRow();
        if (selectedRow >= 0) {
            String itemName = (String) tableModel.getValueAt(selectedRow, 0);
            cart.removeItem(itemName);
            updateCartDisplay();
            showMessage("Removed " + itemName + " from cart");
        } else {
            showError("Please select an item to remove");
        }
    }

    private void checkout() {
        if (cart.isEmpty()) {
            showError("Your cart is empty!");
            return;
        }

        new ReceiptDialog(this, cart);
        int result = JOptionPane.showConfirmDialog(this,
                "Checkout complete!\nDo you want to start a new cart?",
                "Checkout Complete",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            newCart();
        }
    }

    private void newCart() {
        cart.clearCart();
        updateCartDisplay();
        showMessage("Started a new shopping cart!");
    }

    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this,
                "Shopping Cart Application\nVersion 2.0\n\nA complete GUI shopping cart application\nwith real-time updates and receipt generation.",
                "About",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Force dark text on light backgrounds BEFORE setting look and feel
                UIManager.put("Label.foreground", Color.BLACK);
                UIManager.put("TextField.foreground", Color.BLACK);
                UIManager.put("TextField.background", Color.WHITE);
                UIManager.put("Table.foreground", Color.BLACK);
                UIManager.put("Table.background", Color.WHITE);
                UIManager.put("TableHeader.foreground", Color.BLACK);
                UIManager.put("TableHeader.background", new Color(52, 152, 219));
                UIManager.put("Spinner.foreground", Color.BLACK);
                UIManager.put("Spinner.background", Color.WHITE);
                UIManager.put("Button.foreground", Color.BLACK);
                UIManager.put("Panel.background", Color.WHITE);
                UIManager.put("OptionPane.background", Color.WHITE);
                UIManager.put("OptionPane.messageForeground", Color.BLACK);

                // Use cross-platform look and feel for consistency
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

            } catch (Exception e) {
                e.printStackTrace();
            }
            new ShoppingCartGUI();
        });
    }
}