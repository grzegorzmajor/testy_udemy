package ovh.major.testyudemy;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MealTestAssertJ {

    //in the class MealTestHamcrest are more methods

    @Test
    void shouldReturnDiscountedPrice() {
        //given
        Meal meal = new Meal(48);

        //when
        int discountedPrice = meal.getDiscountedPrice(7);

        //then
        assertEquals(41, discountedPrice);
        assertThat(discountedPrice).isEqualTo(41);
    }

    @Test
    void referencesToDifferentObjectShouldBeEqual() {
        //given
        Meal meal1 = new Meal(48);
        Meal meal2 = meal1;

        //then
        assertSame(meal1, meal2);
        assertThat(meal1).isEqualTo(meal2);
    }

    @Test
    void referencesToDifferentObjectShouldNotBeEqual() {
        //given
        Meal meal1 = new Meal(48);
        Meal meal2 = new Meal(49);

        //then
        assertNotSame(meal1, meal2);
        assertThat(meal1).isNotEqualTo(meal2);
    }

    @Test
    void twoMealsShouldBeEqualWhenPriceAndNameAreBeTheSame() {
        //given
        Meal meal1 = new Meal(10, "Pizza");
        Meal meal2 = new Meal(10, "Pizza");

        //then
        assertEquals(meal1, meal2, "checking if two meals are equal");
        assertThat(meal1).isEqualTo(meal2);
    }
}