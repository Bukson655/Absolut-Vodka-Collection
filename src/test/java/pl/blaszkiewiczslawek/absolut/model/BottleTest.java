package pl.blaszkiewiczslawek.absolut.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BottleTest {

    @Test
    @DisplayName("Should recognize same bottles just by name and capacity")
    void shouldRecognizeTheSameBottlesJustByNameAndCapacity() {
        //given
        Bottle first = new Bottle("Miami", 0.7, "2015", "200000", "Important one");
        Bottle second = new Bottle("Miami", 0.7, "2010", "150000");
        //when
        boolean areBottlesEqual = first.equals(second);
        //then
        assertThat(areBottlesEqual).isTrue();
    }

    @Test
    @DisplayName("Should recognize different bottles just by capacity")
    void shouldRecognizeTheDifferentBottlesByCapacity() {
        //given
        Bottle first = new Bottle("Miami", 0.7, "2015", "200000");
        Bottle second = new Bottle("Miami", 1, "2015", "200000");
        //when
        boolean areBottlesEqual = first.equals(second);
        //then
        assertThat(areBottlesEqual).isFalse();
    }

    @Test
    @DisplayName("Should recognize different bottles just by name")
    void shouldRecognizeTheDifferentBottlesByName() {
        //given
        Bottle first = new Bottle("Citron", 1, "2010", "200000", "Important one");
        Bottle second = new Bottle("Citron v2", 1, "2010", "200000", "Important one");
        //when
        boolean areBottlesEqual = first.equals(second);
        //then
        assertThat(areBottlesEqual).isFalse();
    }

}