package ovh.major.testyudemy.account;

import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class AccountServiceMockTest {

    @Test
    void getAllActiveAccounts() {

        //given
        List<Account> accounts = prepareAccountData();
        AccountRepository accountRepository = mock(AccountRepository.class);
        AccountService accountService = new AccountService(accountRepository);
        //when(accountRepository.getAllAccounts()).thenReturn(accounts); - poniżej bardziej zgodne z bdd
        given(accountRepository.getAllAccounts()).willReturn(accounts);

        //when
        List<Account> accountList = accountService.getAllActiveAccounts();

        //then
        assertThat(accountList, hasSize(2));

    }

    @Test
    void getNoActiveAccounts() {

        //given
        AccountRepository accountRepository = mock(AccountRepository.class);
        AccountService accountService = new AccountService(accountRepository);
        given(accountRepository.getAllAccounts()).willReturn(List.of());

        //when
        List<Account> accountList = accountService.getAllActiveAccounts();

        //then
        assertThat(accountList, hasSize(0));

    }


    private List<Account> prepareAccountData() {
        Address address1 = new Address("Kościuszki", "31A/1");
        Account account1 = new Account(address1);

        Account account2 = new Account();

        Address address2 = new Address("Moniuszki", "35/2");
        Account account3 = new Account(address2);

        return Arrays.asList(account1, account2, account3);
    }

    @Test
    void getAccountsByName() {

        //given
        AccountRepository accountRepository = mock(AccountRepository.class);
        AccountService accountService = new AccountService(accountRepository);
        given(accountRepository.getByName("dawid")).willReturn(List.of("nowak"));

        //when
        List<String> accountNames = accountService.findByName("dawid");

        //then
        assertThat(accountNames,contains("nowak"));

    }

}