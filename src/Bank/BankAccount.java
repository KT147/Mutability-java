package Bank;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class BankAccount {
    public enum Type {CHECKING, SAVINGS}

    private final Type type;
    private double balance;
    private Map<Long, Transaction> transactions = new HashMap<>();

    BankAccount(Type type, double balance) {
        this.type = type;
        this.balance = balance;
    }

    public Type getType() {
        return type;
    }

    public double getBalance() {
        return balance;
    }

    public Map<Long, Transaction> getTransactions() {
        return transactions;
    }

    public void commitTransaction(int routingNumber, long transactionId, int customerId, double amount) {
        Transaction t = new Transaction(amount, transactionId, customerId, routingNumber);
        transactions.put(transactionId, t);
        balance += amount;
        System.out.println("Action done with amount " + amount + " and the balance is " + balance );
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "type=" + type +
                ", balance=" + balance +
                '}';
    }
}
