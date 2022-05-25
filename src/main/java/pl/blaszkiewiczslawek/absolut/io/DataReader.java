package pl.blaszkiewiczslawek.absolut.io;

import pl.blaszkiewiczslawek.absolut.model.Bottle;
import pl.blaszkiewiczslawek.exception.InvalidCapacityException;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class DataReader {

    private final Scanner input;
    private final ConsolePrinter consolePrinter;

    public DataReader(ConsolePrinter consolePrinter, Scanner input) {
        this.consolePrinter = consolePrinter;
        this.input = input;
    }

    public Bottle createBottle() {

        consolePrinter.printLine("Nazwa edycji: ");
        String edition = input.nextLine();
        consolePrinter.printLine("Pojemność: ");
        double capacity = getCapacity();
        consolePrinter.printLine("Data wydania: ");
        String releasedDate = input.nextLine();
        consolePrinter.printLine("Ilość wyprodukowanych: ");
        String quantityReleased = input.nextLine();
        consolePrinter.printLine("Dodatkowe informacje: ");
        String additionalInfo = input.nextLine();

        return new Bottle(edition, capacity, releasedDate, quantityReleased, additionalInfo);
    }

    public int getInt() {
        try {
            return input.nextInt();
        } finally {
            input.nextLine();
        }
    }

    public double getCapacity() {
        boolean isCorrect = false;
        double capacity = -1;
        do {
            try {
                input.useLocale(Locale.US);
                capacity = input.nextDouble();
                if (capacity > 0) {
                    isCorrect = true;
                } else if (capacity < 0) {
                    throw new InvalidCapacityException("Pojemność nie może być ujemna. Spróbuj ponownie:");
                }
            } catch (InputMismatchException e) {
                consolePrinter.printLine("Błędny format objętości. Spróbuj ponownie: ");
                input.nextLine();
            } catch (InvalidCapacityException e) {
                consolePrinter.printLine(e.getMessage());
            }
        } while (!isCorrect);
        input.nextLine();
        return capacity;
    }

    public String getString() {
        return input.nextLine();
    }
}