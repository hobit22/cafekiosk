package sample.cafekiosk.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.unit.beverage.Americano;
import sample.cafekiosk.unit.beverage.Latte;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CafeKioskTest {

    @DisplayName("add_manual_test")
    @Test
    void add_manual_test() {
        // given
        CafeKiosk cafekiosk = new CafeKiosk();

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
        CafeKiosk cafekiosk = new CafeKiosk();

        // when
        cafekiosk.add(new Americano());

        // then
        assertThat(cafekiosk.getBeverages().size()).isEqualTo(1);
        assertThat(cafekiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @DisplayName("add several beverages")
    @Test
    void addSeveralBeverages() {
        // given
        CafeKiosk cafekiosk = new CafeKiosk();
        Americano americano = new Americano();

        // when
        cafekiosk.add(americano, 2);

        // then
        assertThat(cafekiosk.getBeverages().get(0)).isEqualTo(americano);
        assertThat(cafekiosk.getBeverages().get(1)).isEqualTo(americano);
    }

    @DisplayName("add 0 beverages")
    @Test
    void addZeroBeverages() {
        // given
        CafeKiosk cafekiosk = new CafeKiosk();
        Americano americano = new Americano();

        // when
        // then
        assertThatThrownBy(() -> cafekiosk.add(americano, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Quantity must be greater than 0");

    }

    @DisplayName("remove")
    @Test
    void remove() {
        // given
        CafeKiosk cafekiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafekiosk.add(americano);
        assertThat(cafekiosk.getBeverages().size()).isEqualTo(1);

        // when
        cafekiosk.remove(americano);

        // then
        assertThat(cafekiosk.getBeverages().size()).isEqualTo(0);
    }

    @DisplayName("clear")
    @Test
    void clear() {
        // given
        CafeKiosk cafekiosk = new CafeKiosk();
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