package pl.blaszkiewiczslawek.absolut.io;

import pl.blaszkiewiczslawek.absolut.model.Bottle;

import java.util.Collection;

public class ConsolePrinter {

    public void printNumberOfBottles(Collection<Bottle> bottles) {
        int numberOfBottles = bottles.size();
        printLine("Akualnie w systemie znajduje się " + numberOfBottles + " unikalnych pozycji.");
    }

    public void printBottles(Collection<Bottle> bottles) {
        if (bottles.size() == 0) {
            printLine("Brak butelek w spisie.");
        } else {
            bottles.stream()
                    .map(Bottle::toString)
                    .forEach(this::printLine);
        }
    }

    public void printBottlesByEdition(String edition, Collection<Bottle> bottles) {
        long count = bottles.stream()
                .filter(bottle -> bottle.getEdition().equalsIgnoreCase(edition))
                .map(Bottle::toString)
                .peek(this::printLine)
                .count();
        if (count == 0) {
            printLine("Brak pozycji \"" + edition + "\" w kolekcji.");
        }
    }

    public void printLine(String text) {
        System.out.println(text);
    }

}
