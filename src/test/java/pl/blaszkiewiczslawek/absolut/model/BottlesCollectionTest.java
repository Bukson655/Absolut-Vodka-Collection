package pl.blaszkiewiczslawek.absolut.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.blaszkiewiczslawek.exception.BottleAlreadyExistsException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BottlesCollectionTest {

    BottlesCollection bottlesCollection;
    Bottle bottleMiami;
    Bottle bottleCitron;

    @BeforeEach
    void init() {
        bottlesCollection = new BottlesCollection();
        bottleMiami = new Bottle("Miami", 0.7);
        bottleCitron = new Bottle("Citron", 1);
        bottlesCollection.addBottle(bottleMiami);
        bottlesCollection.addBottle(bottleCitron);
    }

    @Test
    @DisplayName("Should add unique bottle to database")
    void shouldAddBottleToDb() {
        // given
        Bottle bottle = new Bottle("Carnival", 1, "2014", "240 000");
        // when
        bottlesCollection.addBottle(bottle);
        // then
        System.out.println(bottlesCollection.getBottles());
        assertThat(bottlesCollection.getBottles()).containsValue(bottle);
    }

    @Test
    @DisplayName("Shouldn't add bottle that already exist in database and throw BottleAlreadyExist Exception")
    void shouldNotAddBottleThatAlreadyExistInDb() {
        // given
        Bottle bottle = new Bottle("Miami", 0.7, "2012");
        // when, then
        assertThrows(BottleAlreadyExistsException.class, () -> bottlesCollection.addBottle(bottle));
    }

    @Test
    @DisplayName("Should find bottle by edition and capacity")
    void shouldFindBottleByEditionAndCapacity() {
        // given
        String edition = bottleMiami.getEdition();
        double capacity = bottleMiami.getCapacity();
        // when
        Optional<Bottle> bottleByEditionAndCapacity = bottlesCollection.findBottleByEditionAndCapacity(edition, capacity);
        // then
        assertThat(bottleByEditionAndCapacity).isNotEmpty();
    }

    @Test
    @DisplayName("Shouldn't find bottle (by edition and capacity) that doesn't exist in database")
    void shouldNotFindBottleByEditionAndCapacityThatNotExistInDb() {
        // given
        String edition = "Random Edition Name";
        double capacity = 0.7;
        // when
        Optional<Bottle> bottleByEditionAndCapacity = bottlesCollection.findBottleByEditionAndCapacity(edition, capacity);
        // then
        assertThat(bottleByEditionAndCapacity).isEmpty();
    }

    @Test
    @DisplayName("Should return true if bottle is deleted")
    void shouldReturnTrueIfBottleIsDeleted() {
        // given, when
        boolean isBottleRemoved = bottlesCollection.removeBottle(bottleMiami);
        // then
        assertThat(isBottleRemoved).isTrue();
    }

    @Test
    @DisplayName("Should return false if bottle cannot be deleted")
    void shouldReturnFalseIfBottleCannotBeDeleted() {
        // given
        Bottle bottleNotAddedToCollection = new Bottle("Random Edition Name", 1);
        // when
        boolean isBottleRemoved = bottlesCollection.removeBottle(bottleNotAddedToCollection);
        // then
        assertThat(isBottleRemoved).isFalse();
    }
}