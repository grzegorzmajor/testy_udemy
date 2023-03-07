package ovh.major.testyudemy.meal;

import java.util.ArrayList;
import java.util.List;

public class MealRepository {

    private  List<Meal> meals = new ArrayList<>();
    public void add(Meal meal) {
        this.meals.add(meal);
    }

    public List<Meal> getAllMeals() {
        return this.meals;
    }

    public void delete(Meal meal) {
        this.meals.remove(meal);
    }
}
