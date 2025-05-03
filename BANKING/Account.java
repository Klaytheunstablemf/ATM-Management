import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Account {
    private String accountNumber; 
    private int pin;
    private double balance;
    private List<String> transactionHistory;

    public Account(String accountNumber, int pin, double initialBalance) {
        this.accountNumber = accountNumber; 
        this.pin = pin;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        addTransaction("Account Balance: ₱" + initialBalance);
    }

    public String getAccountNumber() {
        return accountNumber; 
    }

    public int getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            addTransaction("Deposited: ₱" + amount);
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            addTransaction("Withdrew: ₱" + amount);
        }
    }
    
    public boolean transfer(Account receiver, double amount) {
    if (amount >= 100 && amount <= 50000 && this.balance >= amount) {
        this.balance -= amount;   
        receiver.balance += amount; 
        addTransaction("Transferred Money: " + amount + " to Account Number: " + receiver.getAccountNumber());
        receiver.addTransaction("Received Money:"+amount + "from Account Number:"+ accountNumber);
        return true; 
    }
    return false; 
}

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    private void addTransaction(String transaction) {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        transactionHistory.add(date + " - " + transaction);
    }
}