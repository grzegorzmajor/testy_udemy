package ovh.major.testyudemy.meal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class MealRepositoryTest {

    MealRepository mealRepository = new MealRepository();

    @BeforeEach
    void cleanUp() {
        mealRepository.getAllMeals().clear();
    }

    @Test
    void shouldBeAbleToAddMealToRepository() {

        //given
        Meal meal = new Meal(10, "Pizza");

        //when
        mealRepository.add(meal);

        //then
        assertThat(mealRepository.getAllMeals().get(0), is(meal));
    }

    @Test
    void shouldBeAbleToRemoveFromRepository() {

        //given
        Meal meal = new Meal(10, "Pizza");
        mealRepository.add(meal);

        //when
        mealRepository.delete(meal);

        //then
        assertThat(mealRepository.getAllMeals(), not(contains(meal)));

    }

    @Test
    void shouldBeAbleToFindMealByExactName() {

        //given
        Meal meal = new Meal(10, "Pizza");
        mealRepository.add(meal);
        Meal meal2 = new Meal(10, "Pi");
        mealRepository.add(meal2);

        //when
        List<Meal> result = mealRepository.findByName("Pizza", true);

        //then
        assertThat(result.size(), is(1));
    }

    @Test
    void shouldBeAbleToFindMealBeStartingLetters() {

        //given
        Meal meal = new Meal(10, "Pizza");
        mealRepository.add(meal);
        Meal meal2 = new Meal(10, "Pi");
        mealRepository.add(meal2);

        //when
        List<Meal> result = mealRepository.findByName("Pi", false);

        //then
        assertThat(result.size(), is(2));
    }

    @Test
    void shouldBeAbleToFindMealByExactPriceSingleArgumentMethod() {

        //given
        Meal meal = new Meal(10, "Pizza");
        mealRepository.add(meal);

        //when
        List<Meal> result = mealRepository.findByPrice(10);

        //then
        assertThat(result.size(), is(1));

    }

    @Test
    void shouldBeAbleToFindMealByLowerPriceTwoArgumentMethod() {

        //given
        Meal meal = new Meal(10, "Pizza");
        mealRepository.add(meal);
        Meal meal2 = new Meal(16, "Pizza XXL");
        mealRepository.add(meal2);
        Meal meal3 = new Meal(8, "Hod Dog");
        mealRepository.add(meal3);

        //when
        List<Meal> result = mealRepository.findByPrice(11, PriceCondition.LOWER);

        //then
        assertThat(result.size(), is(2));

    }

    @Test
    void shouldBeAbleToFindMealByHigherPriceTwoArgumentMethod() {

        //given
        Meal meal = new Meal(10, "Pizza");
        mealRepository.add(meal);
        Meal meal2 = new Meal(16, "Pizza XXL");
        mealRepository.add(meal2);
        Meal meal3 = new Meal(8, "Hod Dog");
        mealRepository.add(meal3);

        //when
        List<Meal> result = mealRepository.findByPrice(11, PriceCondition.HIGHER);

        //then
        assertThat(result.size(), is(1));

    }
    @Test
    void shouldBeAbleToFindMealByExactPriceTwoArgumentMethod() {

        //given
        Meal meal = new Meal(10, "Pizza");
        mealRepository.add(meal);
        Meal meal2 = new Meal(16, "Pizza XXL");
        mealRepository.add(meal2);
        Meal meal3 = new Meal(8, "Hod Dog");
        mealRepository.add(meal3);

        //when
        List<Meal> result = mealRepository.findByPrice(18, PriceCondition.EXACT);

        //then
        assertThat(result.size(), is(1));

    }

}