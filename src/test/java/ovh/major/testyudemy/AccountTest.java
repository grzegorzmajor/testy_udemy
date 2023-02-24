package ovh.major.testyudemy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountTest {

    @Test
    void newAccountShouldNotBeActiveAfterCreation() {
        //given+when
        Account newAccount = new Account();

        //then
        assertFalse(newAccount.isActive());
    }

    @Test
    void newAccountShouldBeActiveAfterActivation() {
        //given
        Account newAccount = new Account();

        //when
        newAccount.activate();

        //then
        assertTrue(newAccount.isActive());
    }
}
