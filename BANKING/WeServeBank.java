import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeServeBank extends JFrame {
    private final Map<String, Account> accountRegistry = new HashMap<>();
    private Account currentUserAccount;
    private Color navyBlue = new Color(0, 32, 63);
    private Color white = Color.WHITE;
    private final int WINDOW_WIDTH = 900;
    private final int WINDOW_HEIGHT = 700;
    
    public WeServeBank() {
        String userHome = System.getProperty("user.home");
        filePath = userHome + File.separator + "Documents" + File.separator + "StorageDate.txt";
        loadAccounts();
        displayLoginScreen();
        setResizable(false);
    }

    private void displayMainMenu() {
            setTitle("WeServe BANK");
        getContentPane().removeAll();
        setLayout(new BorderLayout());
        
        // Header Panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        headerPanel.setBackground(navyBlue);
        headerPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, 80));
        
        JLabel logoSmall = new JLabel("WeServe BANK");
        logoSmall.setForeground(white);
        logoSmall.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(logoSmall);
        headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Main Content Panel
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(white);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        
        // Account Number Display
        JLabel accountLabel = new JLabel("Account Number: " + currentUserAccount.getAccountNumber());
        accountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        accountLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        // Transaction Buttons Panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 30, 30));
        buttonPanel.setBackground(white);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        JButton[] buttons = {
            createIconButton("💰", "DEPOSIT", 150),
            createIconButton("🏧", "WITHDRAW", 150),
            createIconButton("👝", "BALANCE", 150),
            createIconButton("📋", "TRANSACTION HISTORY", 150),
            createIconButton("🔐", "CHANGE PIN", 150),
            createIconButton("↔️", "TRANSFER", 150)
        };
        
        for (JButton button : buttons) {
            buttonPanel.add(button);
        }
        
        // Logout Button Panel
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.setBackground(white);
        
        JButton btnLogout = new JButton("LOGOUT");
        btnLogout.setPreferredSize(new Dimension(120, 40));
        btnLogout.setBackground(Color.RED);
        btnLogout.setForeground(white);
        btnLogout.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogout.setFocusPainted(false);
        logoutPanel.add(btnLogout);
        
        mainPanel.add(accountLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(logoutPanel, BorderLayout.SOUTH);
        
        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        
        // Add action listeners
        buttons[0].addActionListener(e -> displayDepositScreen());
        buttons[1].addActionListener(e -> displayWithdrawalScreen());
        buttons[2].addActionListener(e -> displayBalanceInquiry());
        buttons[3].addActionListener(e -> displayTransactionHistory());
        buttons[4].addActionListener(e -> displayChangePinScreen());
        buttons[5].addActionListener(e -> displayTransferScreen() );
        btnLogout.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Logged out successfully.");
            displayLoginScreen();
        });

        revalidate();
        repaint();
    }
    
    private void displayLoginScreen() {
    setTitle("WeServe BANK");
    getContentPane().removeAll();
    setLayout(new GridLayout(1, 2));

    JPanel leftPanel = new JPanel(new GridBagLayout());  
    leftPanel.setBackground(navyBlue);
    leftPanel.setPreferredSize(new Dimension(WINDOW_WIDTH / 2, WINDOW_HEIGHT));
    
    JPanel logoAndNamePanel = new JPanel();
    logoAndNamePanel.setLayout(new BoxLayout(logoAndNamePanel, BoxLayout.Y_AXIS));
    logoAndNamePanel.setBackground(navyBlue);
    
    
    ImageIcon originalLogoIcon = new ImageIcon("C:\\Users\\Admin\\Desktop\\2nd yr\\BANKING_OOP-copy\\doc\\logo.png");
    Image scaledImage = originalLogoIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);  // Adjusted size
    ImageIcon logoIcon = new ImageIcon(scaledImage);
    JLabel logoLabel = new JLabel(logoIcon);
    logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
    
    JLabel bankName = new JLabel("WeServe BANK");
    bankName.setFont(new Font("Arial", Font.BOLD, 40));  
    bankName.setForeground(white);
    bankName.setAlignmentX(Component.CENTER_ALIGNMENT);
    
    logoAndNamePanel.add(logoLabel);
    logoAndNamePanel.add(Box.createVerticalStrut(20));  
    logoAndNamePanel.add(bankName);
    
    
    leftPanel.add(logoAndNamePanel);

    
    JPanel rightPanel = new JPanel();
    rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
    rightPanel.setBorder(BorderFactory.createEmptyBorder(80, 60, 80, 60));
    rightPanel.setBackground(white);
    
    JLabel titleLabel = new JLabel("LOGIN");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
    JLabel accountLabel = new JLabel("Account Number");
    accountLabel.setFont(new Font("Arial", Font.PLAIN, 16));
    JTextField accountField = new JTextField(20);
    accountField.setMaximumSize(new Dimension(400, 40));
    accountField.setFont(new Font("Arial", Font.PLAIN, 16));
    
    JLabel pinLabel = new JLabel("PIN");
    pinLabel.setFont(new Font("Arial", Font.PLAIN, 16));
    JPasswordField pinField = new JPasswordField(20);
    pinField.setMaximumSize(new Dimension(400, 40));
    pinField.setFont(new Font("Arial", Font.PLAIN, 16));
    
    JButton loginButton = new JButton("Login");
    loginButton.setBackground(navyBlue);
    loginButton.setForeground(white);
    loginButton.setFont(new Font("Arial", Font.BOLD, 16));
    loginButton.setMaximumSize(new Dimension(400, 45));
    
    JLabel noAccountLabel = new JLabel("Do you have an Account?");
    noAccountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    noAccountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
    JButton registerButton = new JButton("Create a New Account");
    registerButton.setBackground(white);
    registerButton.setForeground(navyBlue);
    registerButton.setFont(new Font("Arial", Font.BOLD, 16));
    registerButton.setMaximumSize(new Dimension(400, 45));
    
    Component[] components = {
        titleLabel, Box.createVerticalStrut(50),
        accountLabel, Box.createVerticalStrut(10),
        accountField, Box.createVerticalStrut(30),
        pinLabel, Box.createVerticalStrut(10),
        pinField, Box.createVerticalStrut(40),
        loginButton, Box.createVerticalStrut(30),
        noAccountLabel, Box.createVerticalStrut(10),
        registerButton
    };
    
    for (Component comp : components) {
        rightPanel.add(comp);
        if (comp instanceof JComponent) {
            ((JComponent) comp).setAlignmentX(Component.LEFT_ALIGNMENT);
        }
    }
    
    add(leftPanel);
    add(rightPanel);
    
    // Add action listeners
    loginButton.addActionListener(e -> {
        String inputAccountNumber = accountField.getText().trim();
        if (!inputAccountNumber.matches("\\d{8}")) {
            JOptionPane.showMessageDialog(this, "Invalid Account Number. Please enter exactly 8 digits.");
            return;
        }

        int pin;
        try {
            pin = Integer.parseInt(new String(pinField.getPassword()));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid PIN format.");
            return;
        }

        Account userAccount = accountRegistry.get(inputAccountNumber);
        if (userAccount != null && userAccount.getPin() == pin) {
            currentUserAccount = userAccount;
            displayMainMenu();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Account Number or PIN.");
        }
    });

    registerButton.addActionListener(e -> displayRegisterScreen());

    revalidate();
    repaint();
}
    
    
    private void displayRegisterScreen() {
    setTitle("WeServe BANK");
    getContentPane().removeAll();
    setLayout(new GridLayout(1, 2));

   
    JPanel leftPanel = new JPanel(new GridBagLayout());  
    leftPanel.setBackground(navyBlue);
    leftPanel.setPreferredSize(new Dimension(WINDOW_WIDTH / 2, WINDOW_HEIGHT));
    
    JPanel logoAndNamePanel = new JPanel();
    logoAndNamePanel.setLayout(new BoxLayout(logoAndNamePanel, BoxLayout.Y_AXIS));
    logoAndNamePanel.setBackground(navyBlue);
    
    ImageIcon originalLogoIcon = new ImageIcon("C:\\Users\\Admin\\Desktop\\2nd yr\\BANKING_OOP-copy\\doc\\logo.png");
    Image scaledImage = originalLogoIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);  // Adjusted size
    ImageIcon logoIcon = new ImageIcon(scaledImage);
    JLabel logoLabel = new JLabel(logoIcon);
    logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
    JLabel bankName = new JLabel("WeServe BANK");
    bankName.setFont(new Font("Arial", Font.BOLD, 40));  
    bankName.setForeground(white);
    bankName.setAlignmentX(Component.CENTER_ALIGNMENT);
    
    logoAndNamePanel.add(logoLabel);
    logoAndNamePanel.add(Box.createVerticalStrut(20));  
    logoAndNamePanel.add(bankName);
    
    leftPanel.add(logoAndNamePanel);

    JPanel rightPanel = new JPanel();
    rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
    rightPanel.setBorder(BorderFactory.createEmptyBorder(80, 60, 80, 60));
    rightPanel.setBackground(white);

    JLabel titleLabel = new JLabel("REGISTER");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel newAccountLabel = new JLabel("Enter an 8-digit Account Number");
    newAccountLabel.setFont(new Font("Arial", Font.PLAIN, 16));
    JTextField newAccountField = new JTextField(20);
    newAccountField.setMaximumSize(new Dimension(400, 40));
    newAccountField.setFont(new Font("Arial", Font.PLAIN, 16));

    JLabel newPinLabel = new JLabel("Enter a 4-digit PIN");
    newPinLabel.setFont(new Font("Arial", Font.PLAIN, 16));
    JPasswordField newPinField = new JPasswordField(20);
    newPinField.setMaximumSize(new Dimension(400, 40));
    newPinField.setFont(new Font("Arial", Font.PLAIN, 16));

    JButton btnRegister = new JButton("Sign Up");
    btnRegister.setBackground(navyBlue);
    btnRegister.setForeground(white);
    btnRegister.setFont(new Font("Arial", Font.BOLD, 16));
    btnRegister.setMaximumSize(new Dimension(400, 45));

    JButton btnCancel= new JButton("Cancel");
    btnCancel.setBackground(white);
    btnCancel.setForeground(navyBlue);
    btnCancel.setFont(new Font("Arial", Font.BOLD, 16));
    btnCancel.setMaximumSize(new Dimension(400, 45));

    Component[] components = {
        titleLabel, Box.createVerticalStrut(50),
        newAccountLabel, Box.createVerticalStrut(10),
        newAccountField, Box.createVerticalStrut(30),
        newPinLabel, Box.createVerticalStrut(10),
        newPinField, Box.createVerticalStrut(40),
        btnRegister, Box.createVerticalStrut(30),
        btnCancel
    };

    for (Component comp : components) {
        rightPanel.add(comp);
        if (comp instanceof JComponent) {
            ((JComponent) comp).setAlignmentX(Component.LEFT_ALIGNMENT);
        }
    }

    add(leftPanel);
    add(rightPanel);

    btnRegister.addActionListener(e -> {
        String inputAccountNumber = newAccountField.getText().trim();
        String inputPin = new String(newPinField.getPassword());

        if (!inputAccountNumber.matches("\\d{8}") || !inputPin.matches("\\d{4}")) {
            JOptionPane.showMessageDialog(this, "Invalid input! Ensure account number has 8 digits and PIN has 4 digits.");
            return;
        }

        int pin = Integer.parseInt(inputPin);

        if (!accountRegistry.containsKey(inputAccountNumber)) {
            Account newAccount = new Account(inputAccountNumber, pin, 0.0);
            accountRegistry.put(inputAccountNumber, newAccount);
            saveAccounts();
            JOptionPane.showMessageDialog(this, "Account created successfully!");
            displayLoginScreen();  
        } else {
            JOptionPane.showMessageDialog(this, "Account Number already exists.");
        }
    });

    btnCancel.addActionListener(e -> displayLoginScreen());

    revalidate();
    repaint();
}
    
    private void displayDepositScreen() {
    JDialog depositDialog = new JDialog(this, "WeServe BANK", true);
    depositDialog.setLayout(new BorderLayout());
    
    
    JPanel headerPanel = new JPanel();
    headerPanel.setBackground(navyBlue);
    headerPanel.setPreferredSize(new Dimension(500, 60));
    
    JLabel headerLabel = new JLabel("DEPOSIT");
    headerLabel.setForeground(white);
    headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
    headerPanel.add(headerLabel);
    
    
    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
    contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
    
    JLabel amountLabel = new JLabel("Please Enter Amount to Deposit:");
    amountLabel.setFont(new Font("Arial", Font.PLAIN, 16));
    amountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
    JLabel rangeLabel = new JLabel("(₱ 100-10,000)");
    rangeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    rangeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
    JTextField amountField = new JTextField(20);
    amountField.setMaximumSize(new Dimension(400, 40));
    amountField.setFont(new Font("Arial", Font.PLAIN, 16));
    
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
    
    JButton submitButton = new JButton("Submit");
    submitButton.setPreferredSize(new Dimension(120, 40));
    submitButton.setBackground(navyBlue);
    submitButton.setForeground(white);
    submitButton.setFont(new Font("Arial", Font.BOLD, 14));
    
    JButton cancelButton = new JButton("Cancel");
    cancelButton.setPreferredSize(new Dimension(120, 40));
    cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
    
    buttonPanel.add(submitButton);
    buttonPanel.add(cancelButton);
    
    contentPanel.add(amountLabel);
    contentPanel.add(Box.createVerticalStrut(10));
    contentPanel.add(rangeLabel);
    contentPanel.add(Box.createVerticalStrut(20));
    contentPanel.add(amountField);
    contentPanel.add(Box.createVerticalStrut(40));
    contentPanel.add(buttonPanel);
    
    depositDialog.add(headerPanel, BorderLayout.NORTH);
    depositDialog.add(contentPanel, BorderLayout.CENTER);
    
    submitButton.addActionListener(e -> {
        String amountText = amountField.getText();
        if (amountText.trim().isEmpty()) {
            JOptionPane.showMessageDialog(depositDialog, "Amount field cannot be empty. Please enter a valid amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            int amount = Integer.parseInt(amountText);
            if (amount < 100) {
                JOptionPane.showMessageDialog(depositDialog, "Deposit amount must be at least ₱100.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } else if (amount > 10000) {
                JOptionPane.showMessageDialog(depositDialog, "Deposit amount must be no more than ₱10,000.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } else {
                
                new Deposit().perform(currentUserAccount, amount, this::saveAccounts);
                depositDialog.dispose();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(depositDialog, "Invalid input! Please enter a valid integer amount without decimals.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    });
    
    cancelButton.addActionListener(e -> depositDialog.dispose());
    
    depositDialog.setSize(500, 400);
    depositDialog.setLocationRelativeTo(this);
    depositDialog.setVisible(true);
}

 private void displayBalanceInquiry() {
    JDialog depositDialog = new JDialog(this, "WeServe BANK", true);
    depositDialog.setLayout(new BorderLayout());
    
    
    JPanel headerPanel = new JPanel();
    headerPanel.setBackground(navyBlue);
    headerPanel.setPreferredSize(new Dimension(500, 60));
    
    JLabel headerLabel = new JLabel("WeServe Bank");
    headerLabel.setForeground(white);
    headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
    headerPanel.add(headerLabel);
    
    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
    contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
    contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);  

    
    JLabel amountLabel = new JLabel("Balance:\n₱ " + currentUserAccount.getBalance());
    amountLabel.setFont(new Font("Arial", Font.PLAIN, 30));
    amountLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
    
    
    JButton okayButton = new JButton("Okay");
    okayButton.setPreferredSize(new Dimension(120, 40));
    okayButton.setBackground(navyBlue);
    okayButton.setForeground(white);
    okayButton.setFont(new Font("Arial", Font.BOLD, 14));
    
    okayButton.addActionListener(e -> depositDialog.dispose());
    okayButton.setAlignmentX(Component.CENTER_ALIGNMENT); 
    
    
    contentPanel.add(Box.createVerticalGlue()); 
    contentPanel.add(amountLabel); 
    contentPanel.add(okayButton); 
    contentPanel.add(Box.createVerticalGlue()); 

    
    depositDialog.add(headerPanel, BorderLayout.NORTH);
    depositDialog.add(contentPanel, BorderLayout.CENTER);

    depositDialog.setSize(500, 400);
    depositDialog.setLocationRelativeTo(this);
    depositDialog.setVisible(true);
}

 private void displayWithdrawalScreen() {
    JDialog parentFrame = new JDialog();
    
    parentFrame.setLayout(new BorderLayout());

    JPanel headerPanel = new JPanel();
    headerPanel.setBackground(navyBlue);
    headerPanel.setPreferredSize(new Dimension(500, 60));

    JLabel headerLabel = new JLabel("WITHDRAW");
    headerLabel.setForeground(white);
    headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
    headerPanel.add(headerLabel);

    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
    contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

    JLabel amountLabel = new JLabel("Please Enter Amount to Withdraw:");
    amountLabel.setFont(new Font("Arial", Font.PLAIN, 16));
    amountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel rangeLabel = new JLabel("(₱ 100-10,000)");
    rangeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    rangeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    JTextField amountField = new JTextField(20);
    amountField.setMaximumSize(new Dimension(400, 40));
    amountField.setFont(new Font("Arial", Font.PLAIN, 16));

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));

    JButton submitButton = new JButton("Submit");
    submitButton.setPreferredSize(new Dimension(120, 40));
    submitButton.setBackground(navyBlue);
    submitButton.setForeground(white);
    submitButton.setFont(new Font("Arial", Font.BOLD, 14));

    JButton cancelButton = new JButton("Cancel");
    cancelButton.setPreferredSize(new Dimension(120, 40));
    cancelButton.setFont(new Font("Arial", Font.BOLD, 14));

    buttonPanel.add(submitButton);
    buttonPanel.add(cancelButton);

    contentPanel.add(amountLabel);
    contentPanel.add(Box.createVerticalStrut(10));
    contentPanel.add(rangeLabel);
    contentPanel.add(Box.createVerticalStrut(20));
    contentPanel.add(amountField);
    contentPanel.add(Box.createVerticalStrut(40));
    contentPanel.add(buttonPanel);

    parentFrame.add(headerPanel, BorderLayout.NORTH);
    parentFrame.add(contentPanel, BorderLayout.CENTER);

    submitButton.addActionListener(e -> {
        String amountText = amountField.getText();
        if (amountText.trim().isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "Amount field cannot be empty. Please enter a valid amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int amount = Integer.parseInt(amountText);
            if (amount < 100) {
                JOptionPane.showMessageDialog(parentFrame, "Withdrawal amount must be at least ₱100.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (amount > 10000) {
                JOptionPane.showMessageDialog(parentFrame, "Withdrawal amount must be no more than ₱10,000.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (amount > currentUserAccount.getBalance()) {
                JOptionPane.showMessageDialog(parentFrame, "Insufficient balance for withdrawal.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            } 

            
            boolean isPinCorrect = verifyPin(currentUserAccount, parentFrame);
            if (isPinCorrect) {
                
                new Withdraw().perform(currentUserAccount, amount, this::saveAccounts, parentFrame);
                parentFrame.dispose();  
            } else {
                
                JOptionPane.showMessageDialog(parentFrame, "Maximum PIN attempts exceeded. Withdrawal canceled.");
                parentFrame.dispose();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(parentFrame, "Invalid input! Please enter a valid integer amount without decimals.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    });

    cancelButton.addActionListener(e -> parentFrame.dispose());

    parentFrame.setSize(500, 400);
    parentFrame.setLocationRelativeTo(this);
    parentFrame.setVisible(true);
}

private static final int MAX_PIN_ATTEMPTS = 4;

private boolean verifyPin(Account account, JDialog parentFrame) {
    int attempts = 0;

    while (attempts < MAX_PIN_ATTEMPTS) {
        JPasswordField pinField = new JPasswordField(4);
        pinField.setEchoChar('*');
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(new JLabel("Enter your 4-digit PIN:"));
        panel.add(pinField);

        int option = JOptionPane.showConfirmDialog(parentFrame, panel, "Enter PIN", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option != JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(parentFrame, "PIN verification canceled.");
            return false; 
        }

        char[] pinChars = pinField.getPassword();
        String pinText = new String(pinChars);

        if (pinText.length() != 4 || !pinText.matches("\\d{4}")) {
            JOptionPane.showMessageDialog(parentFrame, "Incorrect PIN. Attempts remaining: " + (MAX_PIN_ATTEMPTS - attempts - 1));
            attempts++;
            continue; 
        }

        int enteredPin = Integer.parseInt(pinText);

        if (enteredPin == account.getPin()) {
            return true; 
        } else {
            JOptionPane.showMessageDialog(parentFrame, "Incorrect PIN. Attempts remaining: " + (MAX_PIN_ATTEMPTS - attempts - 1));
            attempts++;
        }
    }

    return false; 
}



    
    private JButton createIconButton(String icon, String text, int size) {
        JButton button = new JButton("<html><center>" + icon + "<br>" + text + "</center></html>");
        button.setPreferredSize(new Dimension(size, size));
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(navyBlue);
        button.setForeground(white);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return button;
    }
    
    
    
    private void displayChangePinScreen() {
    JDialog pinDialog = new JDialog(this, "WeServe BANK", true);
    pinDialog.setLayout(new BorderLayout());
    
    
    JPanel headerPanel = new JPanel();
    headerPanel.setBackground(navyBlue);
    headerPanel.setPreferredSize(new Dimension(500, 60));
    
    JLabel headerLabel = new JLabel("CHANGE PIN");
    headerLabel.setForeground(white);
    headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
    headerPanel.add(headerLabel);

    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
    contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
    
    JLabel currentPinLabel = new JLabel("Enter Current PIN:");
    currentPinLabel.setFont(new Font("Arial", Font.PLAIN, 16));
    currentPinLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
    JPasswordField currentPinField = new JPasswordField(20);
    currentPinField.setMaximumSize(new Dimension(400, 40));
    currentPinField.setFont(new Font("Arial", Font.PLAIN, 16));
    
    JLabel newPinLabel = new JLabel("Enter New PIN:");
    newPinLabel.setFont(new Font("Arial", Font.PLAIN, 16));
    newPinLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
    JPasswordField newPinField = new JPasswordField(20);
    newPinField.setMaximumSize(new Dimension(400, 40));
    newPinField.setFont(new Font("Arial", Font.PLAIN, 16));
    
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
    
    JButton submitButton = new JButton("Submit");
    submitButton.setPreferredSize(new Dimension(120, 40));
    submitButton.setBackground(navyBlue);
    submitButton.setForeground(white);
    submitButton.setFont(new Font("Arial", Font.BOLD, 14));
    
    JButton cancelButton = new JButton("Cancel");
    cancelButton.setPreferredSize(new Dimension(120, 40));
    cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
    
    buttonPanel.add(submitButton);
    buttonPanel.add(cancelButton);
    
    contentPanel.add(currentPinLabel);
    contentPanel.add(Box.createVerticalStrut(10));
    contentPanel.add(currentPinField);
    contentPanel.add(Box.createVerticalStrut(20));
    contentPanel.add(newPinLabel);
    contentPanel.add(Box.createVerticalStrut(10));
    contentPanel.add(newPinField);
    contentPanel.add(Box.createVerticalStrut(40));
    contentPanel.add(buttonPanel);
    
    pinDialog.add(headerPanel, BorderLayout.NORTH);
    pinDialog.add(contentPanel, BorderLayout.CENTER);
    
    submitButton.addActionListener(e -> {
        String currentPinInput = new String(currentPinField.getPassword());
        String newPinInput = new String(newPinField.getPassword());

        if (!currentPinInput.matches("\\d{4}") || !newPinInput.matches("\\d{4}")) {
            JOptionPane.showMessageDialog(pinDialog, "Both PINs must be exactly 4 digits.");
            return;
        }

        int currentPin = Integer.parseInt(currentPinInput);
        if (currentPin != currentUserAccount.getPin()) {
            JOptionPane.showMessageDialog(pinDialog, "Incorrect current PIN.");
            return;
        }

        if (currentPinInput.equals(newPinInput)) {
            JOptionPane.showMessageDialog(pinDialog, "New PIN must be different from current PIN.");
            return;
        }

        currentUserAccount = new Account(currentUserAccount.getAccountNumber(), 
                                       Integer.parseInt(newPinInput), 
                                       currentUserAccount.getBalance());
        accountRegistry.put(currentUserAccount.getAccountNumber(), currentUserAccount);
        saveAccounts();

        JOptionPane.showMessageDialog(pinDialog, "PIN changed successfully!");
        pinDialog.dispose();
    });
    
    cancelButton.addActionListener(e -> pinDialog.dispose());
    
    pinDialog.setSize(500, 400);
    pinDialog.setLocationRelativeTo(this);
    pinDialog.setVisible(true);
}

    private void displayTransactionHistory() {
    JDialog historyDialog = new JDialog(this, "WeServe BANK", true);
    historyDialog.setLayout(new BorderLayout());
    
    JPanel headerPanel = new JPanel();
    headerPanel.setBackground(navyBlue);
    headerPanel.setPreferredSize(new Dimension(600, 60));
    
    JLabel headerLabel = new JLabel("TRANSACTION HISTORY");
    headerLabel.setForeground(white);
    headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
    headerPanel.add(headerLabel);
    
    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new BorderLayout());
    contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
    
    List<String> transactions = currentUserAccount.getTransactionHistory();
    StringBuilder historyText = new StringBuilder();
    for (String transaction : transactions) {
        historyText.append(transaction).append("\n");
    }
    
    JTextArea historyArea = new JTextArea(historyText.toString());
    historyArea.setEditable(false);
    historyArea.setFont(new Font("Arial", Font.PLAIN, 14));
    JScrollPane scrollPane = new JScrollPane(historyArea);
    
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JButton closeButton = new JButton("Close");
    closeButton.setPreferredSize(new Dimension(120, 40));
    closeButton.setFont(new Font("Arial", Font.BOLD, 14));
    buttonPanel.add(closeButton);
    
    contentPanel.add(scrollPane, BorderLayout.CENTER);
    contentPanel.add(buttonPanel, BorderLayout.SOUTH);
    
    historyDialog.add(headerPanel, BorderLayout.NORTH);
    historyDialog.add(contentPanel, BorderLayout.CENTER);
    
    closeButton.addActionListener(e -> historyDialog.dispose());
    
    historyDialog.setSize(600, 500);
    historyDialog.setLocationRelativeTo(this);
    historyDialog.setVisible(true);
}

private void displayTransferScreen() {
    JDialog transferDialog = new JDialog(this, "WeServe BANK", true);
    transferDialog.setLayout(new BorderLayout());

    JPanel headerPanel = new JPanel();
    headerPanel.setBackground(navyBlue);
    headerPanel.setPreferredSize(new Dimension(500, 60));

    JLabel headerLabel = new JLabel("TRANSFER");
    headerLabel.setForeground(white);
    headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
    headerPanel.add(headerLabel);

    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
    contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

    JLabel accountLabel = new JLabel("Recipient Account Number:");
    accountLabel.setFont(new Font("Arial", Font.PLAIN, 16)); 
    accountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    JTextField accountField = new JTextField(20);
    accountField.setMaximumSize(new Dimension(400, 40));
    accountField.setFont(new Font("Arial", Font.PLAIN, 16));

    JLabel amountLabel = new JLabel("Enter Amount to Transfer:");
    amountLabel.setFont(new Font("Arial", Font.PLAIN, 16));
    amountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel rangeLabel = new JLabel("(₱ 100-50,000)");
    rangeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    rangeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    JTextField amountField = new JTextField(20);
    amountField.setMaximumSize(new Dimension(400, 40));
    amountField.setFont(new Font("Arial", Font.PLAIN, 16));

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));

    JButton submitButton = new JButton("Submit");
    submitButton.setPreferredSize(new Dimension(120, 40));
    submitButton.setBackground(navyBlue);
    submitButton.setForeground(white);
    submitButton.setFont(new Font("Arial", Font.BOLD, 14));

    JButton cancelButton = new JButton("Cancel");
    cancelButton.setPreferredSize(new Dimension(120, 40));
    cancelButton.setFont(new Font("Arial", Font.PLAIN, 14));

    buttonPanel.add(submitButton);
    buttonPanel.add(cancelButton);

    contentPanel.add(accountLabel);
    contentPanel.add(Box.createVerticalStrut(10));
    contentPanel.add(accountField);
    contentPanel.add(Box.createVerticalStrut(20));
    contentPanel.add(amountLabel);
    contentPanel.add(Box.createVerticalStrut(10));
    contentPanel.add(rangeLabel);
    contentPanel.add(Box.createVerticalStrut(10));
    contentPanel.add(amountField);
    contentPanel.add(Box.createVerticalStrut(40));
    contentPanel.add(buttonPanel);

    transferDialog.add(headerPanel, BorderLayout.NORTH);
    transferDialog.add(contentPanel, BorderLayout.CENTER);

    submitButton.addActionListener(e -> {
    String recipientAccountNumber = accountField.getText().trim();
    String amountText = amountField.getText().trim();

    if (!recipientAccountNumber.matches("\\d{8}")) {
        showMessage("Invalid Account Number. Please enter an 8-digit account number.");
        return;
    }

    Account receiver = accountRegistry.get(recipientAccountNumber);
    if (receiver == null) {
        showMessage("Recipient account not found.");
        return;
    }

    if (recipientAccountNumber.equals(currentUserAccount.getAccountNumber())) {
        showMessage("You cannot transfer to your own account.");
        return;
    }

    
    double amount;
    try {
        amount = Double.parseDouble(amountText);
        if (amount < 100 || amount > 10000) {
            showMessage("Transfer amount must be between ₱100 and ₱10,000.");
            return;
        }

        if (amount > currentUserAccount.getBalance()) {
            showMessage("Insufficient balance for transfer.");
            return;
        }
    } catch (NumberFormatException ex) {
        showMessage("Invalid amount. Please enter a valid number.");
        return;
    }
    
    
    boolean isPinCorrect = verifyPin(currentUserAccount,transferDialog);
    if (isPinCorrect) {
        
        new Transfer().perform(currentUserAccount, receiver, amount, this::saveAccounts);
        transferDialog.dispose();  
    } else {
        JOptionPane.showMessageDialog(transferDialog, "Maximum PIN attempts exceeded. Transfer canceled.");
        transferDialog.dispose();
    }
});
    cancelButton.addActionListener(e -> transferDialog.dispose());

    transferDialog.setSize(500, 450);
    transferDialog.setLocationRelativeTo(this);
    transferDialog.setVisible(true);
}

private void showMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
}

private final String filePath;

private void saveAccounts() {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Account account : accountRegistry.values()) {
                writer.write(String.format("%s,%d,%.2f%n", account.getAccountNumber(), account.getPin(), account.getBalance()));
            }
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving accounts: " + ex.getMessage());
        }
    }

    private void loadAccounts() {
        File file = new File(filePath);
        if (!file.exists()) {
            System.err.println("No accounts file found at: " + filePath);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    String accountNumber = data[0];
                    int pin = Integer.parseInt(data[1]);
                    double balance = Double.parseDouble(data[2]);
                    accountRegistry.put(accountNumber, new Account(accountNumber, pin, balance));
                }
            }
            
        } catch (IOException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error loading accounts: " + ex.getMessage());
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WeServeBank app = new WeServeBank();
            app.setSize(900, 700);
            app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            app.setLocationRelativeTo(null);
            app.setVisible(true);
        });
    }
}