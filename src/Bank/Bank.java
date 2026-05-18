package Bank;

import java.util.HashMap;
import java.util.Map;

public class Bank {

    public static long lastTransactionId = 0;
    private int routingNumber;

    private Map<String, BankCustomer> customers = new HashMap<>();

    public BankCustomer getCustomer(String id) {
        return customers.get(id);
    }

    public int getRoutingNumber() {
        return routingNumber;
    }

    public void addCustomer(String name, double checkingInitialDeposit, double savingsInitialDeposit) {
        BankCustomer customer = new BankCustomer(name, checkingInitialDeposit, savingsInitialDeposit);
        customers.put(name, customer);
        System.out.println("New customer added " + customer);
    }

    public void doTransaction(String id, BankAccount.Type type, double amount) {
        lastTransactionId++;

        BankCustomer customer = getCustomer(id);
        if (customer == null) return;

        BankAccount account = customer.getAccount(type);
        if (account == null) return;

        account.commitTransaction(
                routingNumber,
                lastTransactionId,
                Integer.parseInt(customer.getId()),
                amount
        );

        System.out.println("Balance is " + account.getBalance());
    }
}
