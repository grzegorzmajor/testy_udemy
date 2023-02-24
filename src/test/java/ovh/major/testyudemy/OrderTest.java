package ovh.major.testyudemy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class OrderTest {

    private Order order;

    @BeforeEach //w junit4 bez after
    void initializeOrder() {
        order = new Order();
    }

    @AfterEach
    void cleanUp() {
        order.cancel();
    }

    @Test
    void mealListShouldBeEmptyAfterCreationOfOrder() {

        //then
        assertThat(order.getMeals(), empty());
        assertThat(order.getMeals(), emptyCollectionOf(Meal.class));
    }

    @Test
    void addingMealToOrderShouldIncreaseOrderSize() {
        //given
        Meal meal = new Meal(18, "Parówki");

        //when
        order.addMealToOrder(meal);

        //then
        assertThat(order.getMeals(), hasSize(1));
    }

    @Test
    void removingMealFromOrderShouldDecreaseOrderSize() {
        //given
        Meal meal = new Meal(127, "Zestaw obiadowy grozy");

        //when
        order.addMealToOrder(meal);
        order.removeMealFromOrder(meal);

        //then
        assertThat(order.getMeals(), empty());
        assertThat(order.getMeals(), emptyCollectionOf(Meal.class));
        assertThat(order.getMeals(), not(contains(meal)));

    }

    @Test
    void mealsShouldBeInCorrectOrderAfterAddingThemToOrder() {

        //given
        Meal meal1 = new Meal(18, "Parówki");
        Meal meal2 = new Meal(127, "Zestaw obiadowy grozy");

        //when
        order.addMealToOrder(meal1);
        order.addMealToOrder(meal2);

        //then
        assertThat(order.getMeals(), contains(meal1, meal2));
        assertThat(order.getMeals(), containsInAnyOrder(meal2, meal1));
    }

    @Test
    void testIfTwoMealsAreTheSame() {
        //given
        Meal meal1 = new Meal(18, "Parówki");
        Meal meal2 = new Meal(127, "Zestaw obiadowy grozy");
        Meal meal3 = new Meal(127, "Ryba prosto z kutra - jeszcze żywa");

        List<Meal> meals1 = Arrays.asList(meal1, meal2);
        List<Meal> meals2 = Arrays.asList(meal1, meal2);

        //then
        assertThat(meals1, is(meals2));
    }

    @Test
    void testIfTwoMealsAreNotTheSame() {
        //given
        Meal meal1 = new Meal(18, "Parówki");
        Meal meal2 = new Meal(127, "Zestaw obiadowy grozy");
        Meal meal3 = new Meal(127, "Ryba prosto z kutra - jeszcze żywa");

        List<Meal> meals1 = Arrays.asList(meal1, meal2);
        List<Meal> meals2 = Arrays.asList(meal1, meal3);

        //then
        assertThat(meals1, not(is(meals2)));
    }

}
