package ovh.major.testyudemy;

import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.io.IOException;

public class OrderBackupTest {

    private static OrderBackup orderBackup;

    @BeforeAll // metoda z tą adnotacją musi być static.
    static void setup() throws FileNotFoundException {
        orderBackup = new OrderBackup();
        orderBackup.createFile();
    }
    @BeforeEach
    void appendAtTheStartOfTheLine() throws IOException {
        orderBackup.getWriter().append("New order: ");
    }

    @AfterEach
    void appendAtTheEndOfTheLine() throws IOException {
        orderBackup.getWriter().append(" backed up.");
    }

    @Test
    void backupOrderWithOneMeal() throws IOException {
        //given
        Meal meal =  new Meal(12, "Coffee");
        Order order = new Order();
        order.addMealToOrder(meal);

        //when
        orderBackup.backupOrder(order);

        //then
        System.out.println(order.toString());

    }
    @AfterAll
    static void tearDown() throws IOException {
        orderBackup.closeFile();
    }
}
