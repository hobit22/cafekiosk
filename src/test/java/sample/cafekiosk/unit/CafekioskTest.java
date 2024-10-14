package sample.cafekiosk.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.unit.beverage.Americano;
import sample.cafekiosk.unit.beverage.Latte;

import static org.assertj.core.api.Assertions.assertThat;

class CafekioskTest {

    @DisplayName("add_manual_test")
    @Test
    void add_manual_test() {
        // given
        Cafekiosk cafekiosk = new Cafekiosk();

        // when
        cafekiosk.add(new Americano());

        // then
        System.out.println("담긴 음료 수 : " + cafekiosk.getBeverages().size());
        System.out.println("담긴 음료 : " + cafekiosk.getBeverages().get(0).getName());

    }

    @DisplayName("add")
    @Test
    void add() {
        // given
        Cafekiosk cafekiosk = new Cafekiosk();

        // when
        cafekiosk.add(new Americano());

        // then
        assertThat(cafekiosk.getBeverages().size()).isEqualTo(1);
        assertThat(cafekiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @DisplayName("remove")
    @Test
    void remove() {
        // given
        Cafekiosk cafekiosk = new Cafekiosk();
        Americano americano = new Americano();
        cafekiosk.add(americano);
        assertThat(cafekiosk.getBeverages().size()).isEqualTo(1);

        // when
        cafekiosk.remove(americano);

        // then
        assertThat(cafekiosk.getBeverages().size()).isEqualTo(0);
    }

    @DisplayName("claer")
    @Test
    void clear() {
        // given
        Cafekiosk cafekiosk = new Cafekiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        // when
        cafekiosk.add(americano);
        cafekiosk.add(latte);
        assertThat(cafekiosk.getBeverages().size()).isEqualTo(2);

        // then
        cafekiosk.clear();
        assertThat(cafekiosk.getBeverages()).isEmpty();

    }

}