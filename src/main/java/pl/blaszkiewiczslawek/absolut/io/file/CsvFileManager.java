package pl.blaszkiewiczslawek.absolut.io.file;

import pl.blaszkiewiczslawek.absolut.model.Bottle;
import pl.blaszkiewiczslawek.absolut.model.BottlesCollection;
import pl.blaszkiewiczslawek.exception.DataExportException;
import pl.blaszkiewiczslawek.exception.DataImportException;

import java.io.*;
import java.util.Collection;
import java.util.Scanner;

public class CsvFileManager implements FileManager {

    private static final String FILE_NAME = "AbsolutCollection.csv";

    @Override
    public BottlesCollection importData() {
        BottlesCollection bottlesCollection = new BottlesCollection();
        try (Scanner scan = new Scanner(new File(FILE_NAME))) {
            scan.nextLine();
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                Bottle bottle = createObjectFromString(line);
                bottlesCollection.addBottle(bottle);
            }
        } catch (FileNotFoundException e) {
            throw new DataImportException("Brak pliku \"" + FILE_NAME + "\"");
        }
        return bottlesCollection;
    }

    @Override
    public void exportData(BottlesCollection bottlesCollection) {
        Collection<Bottle> bottles = bottlesCollection.getBottles().values();
        try (
                var fileWriter = new FileWriter(FILE_NAME);
                var bufferedWriter = new BufferedWriter(fileWriter)
        ) {
            bufferedWriter.write(Bottle.csvDescription());
            bufferedWriter.newLine();
            for (Bottle bottle : bottles) {
                bufferedWriter.write(bottle.toCsv());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new DataExportException("Błąd zapisu danych do pliku \"" + FILE_NAME + "\"");
        }
    }

    private Bottle createObjectFromString(String line) {
        String[] split = line.split(";");
        try {
            String edition = split[1];
            double capacity = Double.parseDouble(split[2]);
            String releaseDate = split[3];
            String quantityReleased = split[4];
            String additionalInfo = split[5];
            return new Bottle(edition, capacity, releaseDate, quantityReleased, additionalInfo);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Nieprawidłowy format liczbowy w danych w pliku \"" + FILE_NAME + "\"");
        }

    }
}