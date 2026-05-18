package Bank;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        BankCustomer joe = new BankCustomer("Joe", 500.00, 1000.00);
//        System.out.println(joe);
//
//        List<BankAccount> accounts = joe.getAccounts();
//        accounts.clear();
//        System.out.println(joe);
//
//        accounts.add(new BankAccount(BankAccount.Type.CHECKING, 150000));
//        System.out.println(joe);

        Bank bank = new Bank(joe);


    }
}
