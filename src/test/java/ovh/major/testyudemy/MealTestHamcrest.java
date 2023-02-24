package ovh.major.testyudemy;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class MealTestHamcrest {


    @Test
    void whenDiscountIsHigherThanPriceShouldBeThrownException() {
        //given
        Meal meal = new Meal(49);

        //when
        //then
        assertThrows(IllegalArgumentException.class,() -> meal.getDiscountedPrice(50));
    }

    @Test
    void shouldReturnDiscountedPrice() {
        //given
        Meal meal = new Meal(48);

        //when
        int discountedPrice = meal.getDiscountedPrice(7);

        //then
        assertEquals(41, discountedPrice);
        assertThat(discountedPrice, equalTo(41)); //hamcrest
    }

    @Test
    void referencesToDifferentObjectShouldBeEqual() {
        //given
        Meal meal1 = new Meal(48);
        Meal meal2 = meal1;

        //then
        assertSame(meal1, meal2);
        assertThat(meal1, sameInstance(meal2)); //hamcrest
    }

    @Test
    void referencesToDifferentObjectShouldNotBeEqual() {
        //given
        Meal meal1 = new Meal(48);
        Meal meal2 = new Meal(49);

        //then
        assertNotSame(meal1, meal2);
        assertThat(meal1, not(sameInstance(meal2))); //hamcrest
    }

    @Test
    void twoMealsShouldBeEqualWhenPriceAndNameAreTheSame() {
        //given
        Meal meal1 = new Meal(10, "Pizza");
        Meal meal2 = new Meal(10, "Pizza");

        //then
        assertEquals(meal1, meal2, "checking if two meals are equal");
    }
}