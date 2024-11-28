import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

class Account {
    private String accountNumber;
    private String accountHolderName;
    private double balance;

    public Account(String accountNumber, String accountHolderName, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialDeposit;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public String getDetails() {
        return "Account Number: " + accountNumber + "\n" +
                "Account Holder: " + accountHolderName + "\n" +
                "Balance: ₹" + balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}

public class BankManagementSystemGUI {
    private HashMap<String, Account> accounts = new HashMap<>();

    public BankManagementSystemGUI() {
        JFrame frame = new JFrame("Bank Management System");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));

        JButton createAccountButton = new JButton("Create Account");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton checkDetailsButton = new JButton("Check Account Details");
        JButton exitButton = new JButton("Exit");

        panel.add(createAccountButton);
        panel.add(depositButton);
        panel.add(withdrawButton);
        panel.add(checkDetailsButton);
        panel.add(exitButton);

        frame.add(panel);
        frame.setVisible(true);

        // Event Listeners
        createAccountButton.addActionListener(e -> createAccount());
        depositButton.addActionListener(e -> deposit());
        withdrawButton.addActionListener(e -> withdraw());
        checkDetailsButton.addActionListener(e -> checkDetails());
        exitButton.addActionListener(e -> System.exit(0));
    }

    private void createAccount() {
        JTextField accountNumberField = new JTextField();
        JTextField accountHolderField = new JTextField();
        JTextField initialDepositField = new JTextField();

        Object[] message = {
                "Account Number:", accountNumberField,
                "Account Holder Name:", accountHolderField,
                "Initial Deposit:", initialDepositField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Create Account", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String accountNumber = accountNumberField.getText();
            String accountHolder = accountHolderField.getText();
            double initialDeposit;

            try {
                initialDeposit = Double.parseDouble(initialDepositField.getText());
                if (initialDeposit < 0) throw new NumberFormatException();

                if (accounts.containsKey(accountNumber)) {
                    JOptionPane.showMessageDialog(null, "Account number already exists!");
                } else {
                    accounts.put(accountNumber, new Account(accountNumber, accountHolder, initialDeposit));
                    JOptionPane.showMessageDialog(null, "Account created successfully!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid deposit amount!");
            }
        }
    }

    private void deposit() {
        JTextField accountNumberField = new JTextField();
        JTextField depositAmountField = new JTextField();

        Object[] message = {
                "Account Number:", accountNumberField,
                "Deposit Amount:", depositAmountField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Deposit", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String accountNumber = accountNumberField.getText();
            Account account = accounts.get(accountNumber);

            if (account != null) {
                try {
                    double depositAmount = Double.parseDouble(depositAmountField.getText());
                    if (depositAmount <= 0) throw new NumberFormatException();

                    account.deposit(depositAmount);
                    JOptionPane.showMessageDialog(null, "₹" + depositAmount + " deposited successfully!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid deposit amount!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Account not found!");
            }
        }
    }

    private void withdraw() {
        JTextField accountNumberField = new JTextField();
        JTextField withdrawAmountField = new JTextField();

        Object[] message = {
                "Account Number:", accountNumberField,
                "Withdraw Amount:", withdrawAmountField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Withdraw", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String accountNumber = accountNumberField.getText();
            Account account = accounts.get(accountNumber);

            if (account != null) {
                try {
                    double withdrawAmount = Double.parseDouble(withdrawAmountField.getText());
                    if (withdrawAmount <= 0) throw new NumberFormatException();

                    if (account.withdraw(withdrawAmount)) {
                        JOptionPane.showMessageDialog(null, "₹" + withdrawAmount + " withdrawn successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Insufficient balance or invalid amount!");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid withdraw amount!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Account not found!");
            }
        }
    }

    private void checkDetails() {
        JTextField accountNumberField = new JTextField();

        Object[] message = {
                "Account Number:", accountNumberField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Check Account Details", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String accountNumber = accountNumberField.getText();
            Account account = accounts.get(accountNumber);

            if (account != null) {
                JOptionPane.showMessageDialog(null, account.getDetails());
            } else {
                JOptionPane.showMessageDialog(null, "Account not found!");
            }
        }
    }

    public static void main(String[] args) {
        new BankManagementSystemGUI();
    }
}
