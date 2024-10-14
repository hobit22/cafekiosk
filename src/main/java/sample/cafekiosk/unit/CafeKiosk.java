package sample.cafekiosk.unit;

import lombok.Getter;
import sample.cafekiosk.unit.beverage.Beverage;
import sample.cafekiosk.unit.order.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Getter
public class CafeKiosk {

    private final List<Beverage> beverages = new ArrayList<>();

    public void add(Beverage beverage) {
        beverages.add(beverage);
    }

    public void add(Beverage beverage, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        IntStream.range(0, quantity).forEach(i -> add(beverage));
    }

    public void remove(Beverage beverage) {
        beverages.remove(beverage);
    }

    public int calculateTotalPrice() {
        return beverages.stream().mapToInt(Beverage::getPrice).sum();
    }

    public Order createOrder() {
        return new Order(LocalDateTime.now(), beverages);
    }

    public void clear() {
        beverages.clear();

    }
}
