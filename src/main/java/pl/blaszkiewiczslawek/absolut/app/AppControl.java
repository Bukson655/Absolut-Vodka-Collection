package pl.blaszkiewiczslawek.absolut.app;

import pl.blaszkiewiczslawek.absolut.io.ConsolePrinter;
import pl.blaszkiewiczslawek.absolut.io.DataReader;
import pl.blaszkiewiczslawek.absolut.io.file.FileManager;
import pl.blaszkiewiczslawek.absolut.io.file.FileManagerBuilder;
import pl.blaszkiewiczslawek.absolut.model.Bottle;
import pl.blaszkiewiczslawek.absolut.model.BottlesCollection;
import pl.blaszkiewiczslawek.exception.BottleAlreadyExistsException;
import pl.blaszkiewiczslawek.exception.DataExportException;
import pl.blaszkiewiczslawek.exception.DataImportException;
import pl.blaszkiewiczslawek.exception.NoSuchOptionException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AppControl {

    private static final String DELETE_CONFIRMATION = "tak";

    private final ConsolePrinter printer = new ConsolePrinter();
    private final DataReader reader = new DataReader(printer, new Scanner(System.in));
    private final FileManager fileManager;

    private BottlesCollection bottlesCollection;

    public AppControl() {
        fileManager = new FileManagerBuilder(printer, reader).build();
        try {
            bottlesCollection = fileManager.importData();
            printer.printLine("Zaimportowano dane z pliku.");
        } catch (DataImportException | NumberFormatException e) {
            printer.printLine(e.getMessage());
            printer.printLine("Zainicjalizowano nową bazę.");
            bottlesCollection = new BottlesCollection();
        }
    }

    public void controlLoop() {
        Option option;
        do {
            printOptions();
            option = getOption();
            switch (option) {
                case PRINT_OPTION -> printingOptionsLoop();
                case ADD_BOTTLE -> addBottle();
                case FIND_BOTTLE -> findBottle();
                case REMOVE_BOTTLE -> removeBottle();
                case EXIT -> exit();
                default -> printer.printLine("Coś poszło nie tak. Spróbuj ponownie:");
            }

        } while (option != Option.EXIT);
    }

    private void printingOptionsLoop() {
        PrintingOption printingOption;
        do {
            printPrintingOptions();
            printingOption = getPrintingOption();
            switch (printingOption) {
                case PRINT_NUMBER_OF_BOTTLES -> printNumberOfBottles();
                case PRINT_BOTTLES -> printAllBottles();
                case PRINT_BOTTLES_BY_EDITION -> printBottlesByEdition();
                case GO_BACK -> goBack();
                default -> printer.printLine("Coś poszło nie tak. Spróbuj ponownie:");
            }
        } while (printingOption != PrintingOption.GO_BACK);
    }

    private void printPrintingOptions() {
        printer.printLine("Wybierz opcję:");
        for (PrintingOption printingOption : PrintingOption.values()) {
            printer.printLine(printingOption.toString());
        }
    }

    private void printOptions() {
        printer.printLine("Wybierz opcję:");
        for (Option option : Option.values()) {
            printer.printLine(option.toString());
        }
    }

    private void goBack() {
        printer.printLine("Wyszedłeś z opcji wyświetlania danych.");
    }

    private Option getOption() {
        boolean optionOk = false;
        Option option = null;
        while (!optionOk) {
            try {
                option = Option.createFromInt(reader.getInt());
                optionOk = true;
            } catch (NoSuchOptionException e) {
                printer.printLine(e.getMessage());
            } catch (InputMismatchException e) {
                printer.printLine("Nie wprowadziłeś liczby. Spróbuj ponowie:");
            }
        }
        return option;
    }

    private PrintingOption getPrintingOption() {
        boolean optionOk = false;
        PrintingOption printingOption = null;
        while (!optionOk) {
            try {
                printingOption = PrintingOption.createFromInt(reader.getInt());
                optionOk = true;
            } catch (NoSuchOptionException e) {
                printer.printLine(e.getMessage());
            } catch (InputMismatchException e) {
                printer.printLine("Nie wprowadziłeś liczby. Spróbuj ponowie:");
            }
        }
        return printingOption;
    }

    private void printNumberOfBottles() {
        printer.printNumberOfBottles(bottlesCollection.getBottles().values());
    }

    private void printAllBottles() {
        printer.printBottles(bottlesCollection.getBottles().values());
    }

    private void printBottlesByEdition() {
        printer.printLine("Wpisz nazwę edycji:");
        String edition = reader.getString();
        printer.printBottlesByEdition(edition, bottlesCollection.getBottles().values());
    }

    private void addBottle() {
        try {
            Bottle bottle = reader.createBottle();
            bottlesCollection.addBottle(bottle);
        } catch (BottleAlreadyExistsException e) {
            printer.printLine(e.getMessage());
        }
    }

    private void findBottle() {
        try {
            printer.printLine("Wpisz nazwę edycji:");
            String edition = reader.getString().toUpperCase();
            printer.printLine("Objętość szukanej butelki:");
            double capacity = reader.getCapacity();
            String bottleNotFound = "Brak \"" + edition + " " + capacity + "\" w kolekcji.";
            bottlesCollection.findBottleByEditionAndCapacity(edition, capacity)
                    .map(Bottle::toString)
                    .ifPresentOrElse(printer::printLine, () -> printer.printLine(bottleNotFound));
        } catch (InputMismatchException e) {
            printer.printLine("Wprowadziłeś błędnie pojemność.");
        }
    }

    private void removeBottle() {
        try {
            printer.printLine("Wpisz nazwę edycji butelki, którą chcesz usunąć: ");
            String edition = reader.getString();
            printer.printLine("Wpisz objętość butelki, którą chcesz usunąć: ");
            double capacity = reader.getCapacity();
            Bottle bottleToDelete = new Bottle(edition, capacity);
            boolean delete = deleteConfirmation(bottleToDelete);
            if (delete) {
                deleteBottleIfExists(bottleToDelete);
            } else {
                printer.printLine("Anulowałeś usuwanie pozycji: \"" + edition + " o objętości " + capacity + "\".");
            }
        } catch (InputMismatchException e) {
            printer.printLine("Wprowadziłeś błędnie pojemność.");
        }
    }

    private void deleteBottleIfExists(Bottle bottle) {
        if (bottlesCollection.removeBottle(bottle)) {
            printer.printLine("Usunięto pozycję: \"" + bottle.getEdition()
                    + " o objętości " + bottle.getCapacity() + "\".");
        } else {
            printer.printLine("Brak wskazanego egzemplarza o nazwie \"" + bottle.getEdition()
                    + " o objętości " + bottle.getCapacity() + "\".");
        }
    }

    private boolean deleteConfirmation(Bottle bottle) {
        boolean delete = false;
        printer.printLine("Czy na pewno chcesz usunąć pozycję \"" + bottle.getEdition()
                + " o objętości " + bottle.getCapacity() + "\"?  Wpisz " +
                DELETE_CONFIRMATION + " lub cokolwiek innego, aby anulować usuwanie.");
        String confirmation = reader.getString();
        if (confirmation.equalsIgnoreCase(DELETE_CONFIRMATION)) {
            delete = true;
        }
        return delete;
    }

    private void exit() {
        try {
            fileManager.exportData(bottlesCollection);
            printer.printLine("Eksport danych do pliku zakończony powodzeniem.");
        } catch (DataExportException e) {
            printer.printLine(e.getMessage());
        }
        printer.printLine("Wyszedłeś z programu.");
    }

    private enum Option {

        EXIT(0, "Wyjdź z programu."),
        PRINT_OPTION(1, "Wyświetlanie danych."),
        ADD_BOTTLE(2, "Dodaj butelkę."),
        FIND_BOTTLE(3, "Znajdź butelkę."),
        REMOVE_BOTTLE(4, "Usuń butelkę.");

        private final int value;
        private final String description;

        Option(int value, String description) {
            this.value = value;
            this.description = description;
        }

        @Override
        public String toString() {
            return value + " - " + description;
        }

        static Option createFromInt(int option) throws NoSuchOptionException {
            try {
                return Option.values()[option];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchOptionException("Brak opcji o numerze \"" + option + "\". Wybierz opcję ponownie:");
            }
        }
    }

    private enum PrintingOption {

        GO_BACK(0, "Wyjdź z opcji wyświetlania."),
        PRINT_NUMBER_OF_BOTTLES(1, "Wyświetl ilość butelek w kolekcji."),
        PRINT_BOTTLES(2, "Wyświetl spis butelek."),
        PRINT_BOTTLES_BY_EDITION(3, "Wyświetl wszystkie butelki z edycji.");

        private final int value;
        private final String description;

        PrintingOption(int value, String description) {
            this.value = value;
            this.description = description;
        }

        @Override
        public String toString() {
            return value + " - " + description;
        }

        static PrintingOption createFromInt(int option) throws NoSuchOptionException {
            try {
                return PrintingOption.values()[option];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchOptionException("Brak opcji o numerze \"" + option + "\". Wybierz opcję ponownie:");
            }
        }
    }
}

