package ovh.major.testyudemy;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTimeout;

@DisplayName("Test cases for Cart")
public class CartTest {

    @DisplayName("Cart is able to process 1000 orders in 100ms")
    @Disabled //ignorowanie testu, można też nad całą klasą, w Junit4 było @Ignore
    @Test
    void simulationLargeOrder() {
        //given
        Cart cart = new Cart();

        //When
        //Then
        assertTimeout(Duration.ofMillis(100), cart::simulateLargeOrder);
    }

    @Test
    void cartShouldNotBeEmptyAfterAddingOrderToCart() {
        //given
        Order order = new Order();
        Cart cart = new Cart();

        //when
        cart.addOrderToCart(order);

        //then
        assertThat(cart.getOrders(), anyOf(
                notNullValue(),
                hasSize(1),
                is(not(empty())),
                is(not(emptyCollectionOf(Order.class)))
        ));

        assertThat(cart.getOrders(), allOf(
                notNullValue(),
                hasSize(1),
                is(not(empty())),
                is(not(emptyCollectionOf(Order.class)))
        ));

        assertAll("This is group of assertion for cart",
                () -> assertThat(cart.getOrders(),notNullValue()),
                () -> assertThat(cart.getOrders(),hasSize(1)),
                () -> assertThat(cart.getOrders(),is(not(empty()))),
                () -> assertThat(cart.getOrders(),is(not(emptyCollectionOf(Order.class)))),
                () -> assertThat(cart.getOrders().get(0).getMeals(),empty()),
                ()-> {
                    List<Meal> mealList = cart.getOrders().get(0).getMeals();
                    assertThat(mealList, empty());
                }
        );
    }
}
