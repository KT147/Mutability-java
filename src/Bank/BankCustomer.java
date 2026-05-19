package Bank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BankCustomer {
    private static int lastInt = 10_000_000;
    private final int id;
    private final String name;
    private final List<BankAccount> accounts = new ArrayList<>();

    BankCustomer(String customer, double checkingAmount, double savingsAmount) {
        this.name = customer;
        this.id = lastInt++;
        accounts.add(new BankAccount(BankAccount.Type.CHECKING, checkingAmount));
        accounts.add(new BankAccount(BankAccount.Type.SAVINGS, savingsAmount));
    }

    public String getCustomer() {
        return name;
    }

    public String getId() {
        return String.format("%015d", id);
    }

    public List<BankAccount> getAccounts() {
        return List.copyOf(accounts);
    }

    public BankAccount getAccount(BankAccount.Type type) {
        for (BankAccount account : accounts) {
            if (account.getType() == type) {
                return account;
            }
        }
        return null;
    }

    @Override
    public String toString() {

        String[] accountStrings = new String[accounts.size()];
        Arrays.setAll(accountStrings, i -> accounts.get(i).toString());
        return "Customer: %s (id:%015d)%n\t%s%n".formatted(name, id,
                String.join("\n\t", accountStrings));
    }
}
