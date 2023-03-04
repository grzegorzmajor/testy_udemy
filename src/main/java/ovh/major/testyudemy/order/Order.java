package ovh.major.testyudemy.order;

import ovh.major.testyudemy.Meal;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<Meal> meals = new ArrayList<>();

    public void addMealToOrder(Meal meal) {
        this.meals.add(meal);
    }

    public void removeMealFromOrder(Meal meal) {
        this.meals.remove(meal);

    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void cancel() {
        this.meals.clear();
    }

    int totalPrice() {
        int sum =  this.meals.stream().mapToInt(Meal::getPrice).sum();
        if (sum<0) {
            throw new IllegalArgumentException("Price Limit exceeded");
        } else {
            return sum;
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "meals=" + meals +
                '}';
    }
}
