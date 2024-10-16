package sample.cafekiosk.unit;

import sample.cafekiosk.unit.beverage.Americano;
import sample.cafekiosk.unit.beverage.Latte;

public class CafekioskRunner {

    public static void main(String[] args) {
        CafeKiosk cafekiosk = new CafeKiosk();
        cafekiosk.add(new Americano());
        System.out.println(">>> 아메리카노 추가");

        cafekiosk.add(new Latte());
        System.out.println(">>> 라떼 추가");

        int totalPrice = cafekiosk.calculateTotalPrice();
        System.out.println("totalPrice = " + totalPrice);
    }

}
