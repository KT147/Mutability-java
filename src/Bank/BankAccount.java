package Bank;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;


public class BankAccount {
    public enum Type {CHECKING, SAVINGS}

    private final Type type;
    private double balance;
    private Map<Long, Transaction> transactions = new LinkedHashMap<>();

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

    public Map<Long, String> getTransactions() {
        Map<Long, String> txMap = new HashMap<>();
        for (var tx : transactions.entrySet()) {
            txMap.put(tx.getKey(), tx.getValue().toString());
        }
        return txMap;
    }

    void commitTransaction(int routingNumber, long transactionId, String customerId, double amount) {
        balance += amount;
        transactions.put(transactionId, (new Transaction(amount, transactionId, Integer.parseInt(customerId), routingNumber)));
        System.out.println("Action done with amount " + amount + " and the balance is " + balance);
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "type=" + type +
                ", balance=" + balance +
                '}';
    }

}
