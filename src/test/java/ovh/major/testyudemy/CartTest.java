package ovh.major.testyudemy;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTimeout;

public class CartTest {

    @Test
    void simulationLargeOrder() {
        //given
        Cart cart = new Cart();

        //When
        //Then
        assertTimeout(Duration.ofMillis(100), cart::simulateLargeOrder);
    }
}
