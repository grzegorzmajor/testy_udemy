package ovh.major.testyudemy;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class OrderTest {

    @Test
    void mealListShouldBeEmptyAfterCreationOfOrder() {
        //given
        Order order = new Order();

        //then
        assertThat(order.getMeals(),empty());
        assertThat(order.getMeals(),emptyCollectionOf(Meal.class));
    }
    @Test
    void addingMealToOrderShouldIncreaseOrderSize() {
        //given
        Order order = new Order();
        Meal meal = new Meal(18,"Parówki");

        //when
        order.addMealToOrder(meal);

        //then
        assertThat(order.getMeals(),hasSize(1));
    }

    @Test
    void removingMealFromOrderShouldDecreaseOrderSize() {
        //given
        Order order = new Order();
        Meal meal = new Meal(127,"Zestaw obiadowy grozy");

        //when
        order.addMealToOrder(meal);
        order.removeMealFromOrder(meal);

        //then
        assertThat(order.getMeals(),empty());
        assertThat(order.getMeals(),emptyCollectionOf(Meal.class));
        assertThat(order.getMeals(),not(contains(meal)));

    }

    @Test
    void mealsShouldBeInCorrectOrderAfterAddingThemToOrder() {
        Order order = new Order();
        Meal meal1 = new Meal(18,"Parówki");
        Meal meal2 = new Meal(127,"Zestaw obiadowy grozy");

        //when
        order.addMealToOrder(meal1);
        order.addMealToOrder(meal2);

        //then
        assertThat(order.getMeals(), contains(meal1,meal2));
        assertThat(order.getMeals(), containsInAnyOrder(meal2,meal1));
    }

    @Test
    void testIfTwoMealsAreTheSame() {
        //given
        Order order = new Order();
        Meal meal1 = new Meal(18,"Parówki");
        Meal meal2 = new Meal(127,"Zestaw obiadowy grozy");
        Meal meal3 = new Meal(127,"Ryba prosto z kutra - jeszcze żywa");

        List<Meal> meals1 = Arrays.asList(meal1, meal2);
        List<Meal> meals2 = Arrays.asList(meal1, meal2);

        //then
        assertThat(meals1, is(meals2));
    }
    @Test
    void testIfTwoMealsAreNotTheSame() {
        //given
        Order order = new Order();
        Meal meal1 = new Meal(18,"Parówki");
        Meal meal2 = new Meal(127,"Zestaw obiadowy grozy");
        Meal meal3 = new Meal(127,"Ryba prosto z kutra - jeszcze żywa");

        List<Meal> meals1 = Arrays.asList(meal1, meal2);
        List<Meal> meals2 = Arrays.asList(meal1, meal3);

        //then
        assertThat(meals1, not(is(meals2)));
    }

}
