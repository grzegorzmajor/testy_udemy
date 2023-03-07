package ovh.major.testyudemy.meal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MealRepository {

    private List<Meal> meals = new ArrayList<>();

    public void add(Meal meal) {
        this.meals.add(meal);
    }

    public List<Meal> getAllMeals() {
        return this.meals;
    }

    public void delete(Meal meal) {
        this.meals.remove(meal);
    }

    public List<Meal> findByName(String name, boolean exactName) {
        return meals.stream().filter(meal -> exactName ? meal.getName().equals(name) : meal.getName().startsWith(name))
                .collect(Collectors.toList());
    }

    public List<Meal> findByPrice(int price) {
        return findByPrice(price, PriceCondition.EXACT);
    }

    public List<Meal> findByPrice(int price, PriceCondition condition) {
        List<Meal> result = null;
        switch (condition) {
            case LOWER -> {
                result = meals.stream().filter(meal -> meal.getPrice() < price)
                            .collect(Collectors.toList());
            }
            case HIGHER -> {
                result = meals.stream().filter(meal -> meal.getPrice() > price)
                        .collect(Collectors.toList());
            }
            case EXACT -> {
                result = meals.stream().filter(meal -> meal.getPrice() == price)
                        .collect(Collectors.toList());
            }
        }
        return result;
    }
}
