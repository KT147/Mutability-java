package Bank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

//        BankCustomer joe = new BankCustomer("Joe", 500.00, 1000.00);
//        System.out.println(joe);
//
//        List<BankAccount> accounts = joe.getAccounts();
//        accounts.clear();
//        System.out.println(joe);
//
//        accounts.add(new BankAccount(BankAccount.Type.CHECKING, 150000));
//        System.out.println(joe);

//        BankCustomer joe = new BankCustomer("Joe", 500.00, 1000.00);
//        Map<String, BankCustomer> map = new HashMap<>();
//        map.put(joe.getCustomer(), joe);


        Bank bank = new Bank(32145433);
        bank.addCustomer("Joe", 500.00, 1000.00);
        BankCustomer joe = bank.getCustomer("000000010000000");
        System.out.println(joe);

        if (bank.doTransaction(joe.getId(), BankAccount.Type.CHECKING, 35)) {
            System.out.println(joe);
        }

        if (bank.doTransaction(joe.getId(), BankAccount.Type.CHECKING, -535)) {
            System.out.println(joe);
        }

        BankAccount checking = joe.getAccount(BankAccount.Type.CHECKING);
        var transactions = checking.getTransactions();
        transactions.forEach((k, v) -> System.out.println(k + " : " + v));

//        List <BankAccount> joesAccounts = joe.getAccounts();
//        BankAccount joesSavingAccount = joe.getAccount(BankAccount.Type.SAVINGS);
//        BankAccount joesCheckingAccount = joe.getAccount(BankAccount.Type.CHECKING);
//
//        joesSavingAccount.commitTransaction(1, 00001, 1, 100);
//
//        System.out.println(joesSavingAccount.getTransactions());


    }

}
