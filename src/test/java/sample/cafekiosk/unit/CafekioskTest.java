package sample.cafekiosk.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.unit.beverage.Americano;

import static org.junit.jupiter.api.Assertions.*;

class CafekioskTest {

    @DisplayName("add")
    @Test
    void add() {
        // given
        Cafekiosk cafekiosk = new Cafekiosk();

        // when
        cafekiosk.add(new Americano());

        // then
        System.out.println("담긴 음료 수 : " + cafekiosk.getBeverages().size());
        System.out.println("담긴 음료 : " + cafekiosk.getBeverages().get(0).getName());

    }

}