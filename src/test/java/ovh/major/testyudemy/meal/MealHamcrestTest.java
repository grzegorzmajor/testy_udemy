package ovh.major.testyudemy.meal;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ovh.major.testyudemy.order.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class MealHamcrestTest {

    @Spy
    private Meal mealSpy;

    private static Stream<Arguments> createMealsWithNameAndPrice() {
        return Stream.of(Arguments.of("Hamburger", 10), Arguments.of("Cheseburger", 12));
    }

    private static Stream<String> createCakeName() {
        List<String> cakeNames = Arrays.asList("Cheesecake", "Fruitcake", "Cupcake");
        return cakeNames.stream();
    }

    @Test
    void whenDiscountIsHigherThanPriceShouldBeThrownException() {
        //given
        Meal meal = new Meal(49);

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> meal.getDiscountedPrice(50));
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

    @ParameterizedTest
    @ValueSource(ints = {5, 10, 15, 18})
    void mealPricesShouldBeLowerThan20(int price) {
        assertThat(price, lessThan(20));

    }

    @ParameterizedTest
    @MethodSource("createMealsWithNameAndPrice")
    void burgersShouldHAveCorrectNameAndPrice(String name, int price) {
        assertThat(name, containsString("burger"));
        assertThat(price, greaterThanOrEqualTo(10));
    }

    @ParameterizedTest
    @MethodSource("createCakeName")
    void cakeNamesShouldEndWithCake(String name) {
        assertThat(name, notNullValue());
        assertThat(name, endsWith("cake"));
    }

    @TestFactory
    Collection<DynamicTest> calculateMealPrices() {
        Order order = new Order();
        order.addMealToOrder(new Meal(10, 2, "Hamburger"));
        order.addMealToOrder(new Meal(7, 4, "Fries"));
        order.addMealToOrder(new Meal(22, 3, "Pizza"));

        Collection<DynamicTest> dynamicTests = new ArrayList<>();
        for (int i = 0; i < order.getMeals().size(); i++) {
            int price = order.getMeals().get(i).getPrice();
            int quantity = order.getMeals().get(i).getQuantity();

            Executable executable = () -> {
                assertThat(calculatePrice(price, quantity), lessThan(67));
            };
            String name = "Test name: " + i;
            DynamicTest dynamicTest = DynamicTest.dynamicTest(name, executable);
            dynamicTests.add(dynamicTest);

        }
        return dynamicTests;
    }

    //mo??na wywo??a?? prawdziw?? metod?? za pomoc??
    @Test
    void testMealSumPrice() {

        //given
        Meal meal = mock(Meal.class);

        given(meal.getPrice()).willReturn(15);
        given(meal.getQuantity()).willReturn(3);

        given(meal.sumPrice()).willCallRealMethod();

        //when
        int result = meal.sumPrice();

        //then
        assertThat(result, equalTo(45));
    }

    @Test
    @ExtendWith(MockitoExtension.class)
    void testMealSumPriceWithSpy() {

        //given
        //przeniosiono - adnotacja @Spy
        // Meal meal = spy(Meal.class);
        // gdy chcemy weryfikowa?? prawdziwe metody z klasy, wymaga konstruktora bez parametrowego
        // mo??na wcze??niej stworzy?? instancj?? tego obiektu z konstruktorem z parametrami
        // i przekaza?? t?? instancj?? do metody spy()

        given(mealSpy.getPrice()).willReturn(15);
        given(mealSpy.getQuantity()).willReturn(3);

        //given(meal.sumPrice()).willCallRealMethod();

        //when
        int result = mealSpy.sumPrice();

        //then
        then(mealSpy).should().getPrice();
        then(mealSpy).should().getQuantity();
        assertThat(result, equalTo(45));
    }

    private int calculatePrice(int price, int quantity) {
        return price * quantity;
    }


}