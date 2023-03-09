package ovh.major.testyudemy.meal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;


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
    void shouldBeAbleToFindMealByExactPriceSingleArgumentFindByPriceMethod() {

        //given
        Meal meal = new Meal(10, "Pizza");
        mealRepository.add(meal);

        //when
        List<Meal> result = mealRepository.findByPrice(10);

        //then
        assertThat(result.size(), is(1));

    }

    @Test
    void shouldBeAbleToFindMealByLowerPriceTwoArgumentFindByPriceMethod() {

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
    void shouldBeAbleToFindMealByHigherPriceTwoArgumentFindByPriceMethod() {

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
    void shouldBeAbleToFindMealByExactPriceTwoArgumentFindByPriceMethod() {

        //given
        Meal meal = new Meal(10, "Pizza");
        mealRepository.add(meal);
        Meal meal2 = new Meal(16, "Pizza XXL");
        mealRepository.add(meal2);
        Meal meal3 = new Meal(8, "Hod Dog");
        mealRepository.add(meal3);

        //when
        List<Meal> result = mealRepository.findByPrice(8, PriceCondition.EXACT);

        //then
        assertThat(result.size(), is(1));

    }

    @ParameterizedTest
    @CsvSource({
            "10, 'Pizza', EXACT, 8, 0",
            "16, 'Pizza XXL', EXACT, 8, 0",
            "8, 'Hot Dog', EXACT, 8, 1",
            "10, 'Pizza', LOWER, 11, 1",
            "16, 'Pizza XXL', LOWER, 11, 0",
            "8, 'Hot Dog', LOWER, 11, 1",
            "10, 'Pizza', HIGHER, 8, 1",
            "16, 'Pizza XXL', HIGHER, 8, 1",
            "8, 'Hot Dog', HIGHER, 8, 0",

    })
    void shouldBeReturnedListWithMealIfItMatchesTheConditionTwoArgumentFindByPriceMethod(
            int price,
            String name,
            PriceCondition priceCondition,
            int searchPrice,
            int resultSize
    ) {

        //given
        Meal meal = new Meal(price, name);
        mealRepository.add(meal);

        //when
        List<Meal> result = mealRepository.findByPrice(searchPrice, priceCondition);

        //then
        assertAll(
                () -> assertThat(result.size(), is(resultSize)),
                () -> assertThat(result, resultSize == 1 ? contains(meal) : not(contains(meal)))
        );
    }

    @ParameterizedTest
    @CsvSource({
            "10, 'Pizza', EXACT, 10, 'Pi', false, 1"
    })
    void shouldBeReturnedListWithMealIfItMatchesTheConditionFindMethod(
            int price,
            String name,
            PriceCondition priceCondition,
            int searchPrice,
            String searchName,
            boolean exactName,
            int resultSize
    ) {

        //given
        Meal meal = new Meal(price, name);
        mealRepository.add(meal);

        //when
        List<Meal> result = mealRepository.find(searchName,exactName,searchPrice,priceCondition);

        //then
        assertAll(
                () -> assertThat(result.size(), is(resultSize)),
                () -> assertThat(result.size(), is(notNullValue()))
        );


    }


}