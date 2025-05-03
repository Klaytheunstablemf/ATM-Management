import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


class Deposit {
    public void perform(Account account, int amount, Runnable saveAccounts) {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

       
        account.deposit(amount);

        String transactionDetails =
            "******************************************" +
            "\n Date: " + date +
            "\n-----------------------------------------" +
            "\nTransaction Receipt" +
            "\n-----------------------------------------" +
            "\n\nAccount Number: " + account.getAccountNumber() +
            "\nTransaction: Cash Deposit" + 
            "\nAmount Deposited: ₱" + amount +
            "\n\n******************************************" +    
            "\nNew Balance: ₱" + account.getBalance() +
            "\n\n******************************************" + 
            "\nThank You For Banking With Us";

        int option = JOptionPane.showConfirmDialog(null, "Do you want to print a receipt?", 
                "Print Receipt", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            File receiptFile = TransactionReceipt.createReceipt(transactionDetails);
            if (receiptFile != null) {
                JOptionPane.showMessageDialog(null, "Receipt saved at: " + receiptFile.getAbsolutePath());
            }
        }
        JOptionPane.showMessageDialog(null, transactionDetails);
        saveAccounts.run();
    }
}

class Withdraw {

    public void perform(Account account, int amount, Runnable saveAccounts, JDialog parentFrame) {
        
       
            if (account.getBalance() >= amount) {
                account.withdraw(amount);
                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                String transactionDetails =
                    "******************************************" +
                    "\n Date: " + date +
                    "\n-----------------------------------------" +
                    "\nTransaction Receipt" +
                    "\n-----------------------------------------" +
                    "\n\nAccount Number: " + account.getAccountNumber() +
                    "\nTransaction: Cash Withdrawal" + 
                    "\nAmount Withdrawn: ₱" + amount +
                    "\n\n******************************************" +    
                    "\nNew Balance: ₱" + account.getBalance() +
                    "\n\n******************************************" + 
                    "\nThank You For Banking With Us";

                int option = JOptionPane.showConfirmDialog(parentFrame, "Do you want to print a receipt?", "Print Receipt", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    File receiptFile = TransactionReceipt.createReceipt(transactionDetails);
                    if (receiptFile != null) {
                        JOptionPane.showMessageDialog(parentFrame, "Receipt saved at: " + receiptFile.getAbsolutePath());
                    }
                }

                JOptionPane.showMessageDialog(parentFrame, transactionDetails);
                saveAccounts.run();
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Insufficient balance for withdrawal.");
            }
        } 
    
    }
   


class BalanceInquiry {
    public void perform(Account account) {
        JOptionPane.showMessageDialog(null, "Current Balance: ₱" + account.getBalance());
    }
}

class Transfer {
    private static final int MAX_PIN_ATTEMPTS = 4;

    public void perform(Account sender, Account receiver, double amount, Runnable saveAccounts) {
        try {
            
            boolean transferSuccessful = sender.transfer(receiver, amount);

            if (!transferSuccessful) {
                JOptionPane.showMessageDialog(null, "Transfer failed. Please check your balance or account details.");
                return;
            }
            
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String transactionDetails =
                    "******************************************" +
                    "\n Date: " + date +
                    "\n-----------------------------------------" +
                    "\nTransaction Receipt" +
                    "\n-----------------------------------------" +
                    "\n\nAccount Number: " + sender.getAccountNumber() +
                    "\nTransaction: Cash Transfer" +
                    "\nAmount Transferred: ₱" + amount +
                    "\nTransferred to account: " + receiver.getAccountNumber() +
                    "\n\n******************************************" +
                    "\nNew Balance: ₱" + sender.getBalance() +
                    "\n\n******************************************" +
                    "\nThank You For Banking With Us";
            
            
            int option = JOptionPane.showConfirmDialog(null, "Do you want to print a receipt?", "Print Receipt", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                File receiptFile = TransactionReceipt.createReceipt(transactionDetails);
                if (receiptFile != null) {
                    JOptionPane.showMessageDialog(null, "Receipt saved at: " + receiptFile.getAbsolutePath());
                }
            }
            JOptionPane.showMessageDialog(null, transactionDetails);
            saveAccounts.run(); 

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error processing transfer: " + ex.getMessage());
        }
    }
}


class TransactionReceipt {
    public static File createReceipt(String transactionDetails) {
        BufferedImage receiptImage = new BufferedImage(300, 500, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = receiptImage.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, receiptImage.getWidth(), receiptImage.getHeight());
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("WeServe BANK", 100, 30);

        String[] detailsLines = transactionDetails.split("\n");
        int yPosition = 60;
        for (String line : detailsLines) {
            g2d.drawString(line, 20, yPosition);
            yPosition += 20;
        }

        g2d.dispose();

        try {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File outputfile = new File("receipt_" + timestamp + ".png");
            ImageIO.write(receiptImage, "png", outputfile);
            System.out.println("Receipt saved as: " + outputfile.getAbsolutePath());
            return outputfile;
        } catch (IOException e) {
            System.err.println("Error saving receipt: " + e.getMessage());
            return null;
        }
    }
}