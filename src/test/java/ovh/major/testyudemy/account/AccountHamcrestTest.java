package ovh.major.testyudemy.account;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;

class AccountHamcrestTest {

    //@Test
    @RepeatedTest(2)
    void newAccountShouldNotBeActiveAfterCreation() {
        //given+when
        Account newAccount = new Account();

        //then
        assertFalse(newAccount.isActive());
        assertThat(newAccount.isActive(), equalTo(false)); //hamcrest
        assertThat(newAccount.isActive(), is(false)); //hamcrest
    }

    @Test
    void newAccountShouldBeActiveAfterActivation() {
        //given
        Account newAccount = new Account();

        //when
        newAccount.activate();

        //then
        assertTrue(newAccount.isActive());
        assertThat(newAccount.isActive(), is(true));
    }

    @Test
    void newlyCreatedAccountShouldBeNotHaveDefaultDeliveryAddress() {
        //given
        Account account = new Account();

        //when
        Address address = account.getDefaultDeliveryAddress();

        //then
        assertNull(address);
        assertThat(address, is(nullValue()));
    }

    @Test
    void defaultDeliveryAddressShouldNotBeNullAfterBeingSet() {
        //given
        Address address = new Address("Kościuszki", "31A/1");
        Account account = new Account();
        account.setDefaultDeliveryAddress(address);

        //when
        Address defaultAddress = account.getDefaultDeliveryAddress();

        //then
        assertNotNull(defaultAddress);
        assertThat(defaultAddress, is(notNullValue()));
    }

    @Test
    void newAccountWithNotNullAddressShouldBeActive() {

        // to jest przykład na zależności dla asercji czyli
        // jesli asercja może byc spełniona tylko przy określonych warunkach
        // tutaj na przykładzie wypełnionego adresu
        // this test just for example

        //given
        Address address = new Address("Kościuszki", "31A/1");

        //when
        Account account = new Account(address);

        //then
        assumingThat(address != null, () -> {
            assertTrue(account.isActive());
        });
    }

    @Test
    void invalidEmailShouldThrowException() {

        //given
        Account account = new Account();

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> account.setEmail("wrongEmail"));
    }


    @Test
    void validEmailShouldThrowException() {

        //given
        Account account = new Account();

        //when
        account.setEmail("ludzik@domena.pl");

        //then
        assertThat(account.getEmail(), is(equalTo("ludzik@domena.pl")));
    }
}

