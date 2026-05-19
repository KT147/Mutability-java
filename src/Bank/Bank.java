package Bank;

import java.util.HashMap;
import java.util.Map;

public class Bank {

    public final int routingNumber;
    private long lastTransactionId = 1;
    private final Map<String, BankCustomer> customers;

    public Bank(int routingNumber) {
        this.routingNumber = routingNumber;
        customers = new HashMap<>();
    }

    public BankCustomer getCustomer(String id) {
        return customers.get(id);
    }

//    public int getRoutingNumber() {
//        return routingNumber;
//    }

    public void addCustomer(String name, double checkingInitialDeposit, double savingsInitialDeposit) {
        BankCustomer customer = new BankCustomer(name, checkingInitialDeposit, savingsInitialDeposit);
        customers.put(customer.getId(), customer);
        System.out.println("New customer added " + customer);
    }

    public boolean doTransaction(String id, BankAccount.Type type, double amount) {

        BankCustomer customer = getCustomer(id);
        if (customer != null) {
            BankAccount account = customer.getAccount(type);
            if (account != null) {
                if ((account.getBalance() + amount) < 0) {
                    System.out.println("Not enough funds");
                } else {
                    account.commitTransaction(
                            routingNumber,
                            lastTransactionId++,
                            id,
                            amount
                    );
                    return true;
                }
            }
        } else {
            System.out.println("Invalid customer id");
        }
        return false;
    }
}
