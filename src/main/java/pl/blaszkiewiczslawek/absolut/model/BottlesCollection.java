package pl.blaszkiewiczslawek.absolut.model;

import pl.blaszkiewiczslawek.exception.BottleAlreadyExistsException;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class BottlesCollection implements Serializable {

    private final Map<String, Bottle> bottles = new TreeMap<>();

    public Map<String, Bottle> getBottles() {
        return bottles;
    }

    public void addBottle(Bottle bottle) {
        if (bottles.containsValue(bottle)) {
            throw new BottleAlreadyExistsException(bottle.getProducer() + " " + bottle.getEdition() + " " + bottle.getCapacity()
                    + " już znajduje się w systemie.");
        }
        String bottleKey = bottle.getEdition() + " " + bottle.getCapacity();
        bottles.put(bottleKey, bottle);
    }

    public Optional<Bottle> findBottleByEditionAndCapacity(String edition, double capacity) {
        return Optional.ofNullable(bottles.get(edition + " " + capacity));
    }

    public boolean removeBottle(Bottle bottle) {
        if (bottles.containsValue(bottle)) {
            bottles.remove(bottle.getEdition() + " " + bottle.getCapacity());
            return true;
        } else {
            return false;
        }
    }

}
