package ovh.major.testyudemy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountTest {

    @Test
    void newAccountActivationTest_mustBeNotActivated() {
        Account newAccount = new Account();
        assertFalse(newAccount.isActive());
    }
    @Test
    void activationAccountTest_mustBeActivated() {
        Account newAccount = new Account();
        newAccount.activate();
        assertTrue(newAccount.isActive());
    }
}
