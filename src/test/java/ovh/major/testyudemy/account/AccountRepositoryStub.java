package ovh.major.testyudemy.account;

import java.util.Arrays;
import java.util.List;

public class AccountRepositoryStub implements AccountRepository {
    @Override
    public List<Account> getAllAccounts() {

        Address address1 = new Address("Kościuszki", "31A/1");
        Account account1 = new Account(address1);

        Account account2 = new Account();

        Address address2 = new Address("Moniuszki", "35/2");
        Account account3 = new Account(address2);

        return Arrays.asList(account1, account2, account3);
    }
}
