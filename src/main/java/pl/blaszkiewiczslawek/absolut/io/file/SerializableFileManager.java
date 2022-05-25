package pl.blaszkiewiczslawek.absolut.io.file;

import pl.blaszkiewiczslawek.absolut.model.BottlesCollection;
import pl.blaszkiewiczslawek.exception.DataExportException;
import pl.blaszkiewiczslawek.exception.DataImportException;

import java.io.*;

public class SerializableFileManager implements FileManager {

    public static final String FILE_NAME = "Absolut.bottles";

    @Override
    public BottlesCollection importData() {
        try (
                var fis = new FileInputStream(FILE_NAME);
                var ois = new ObjectInputStream(fis)
        ) {
            return (BottlesCollection) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new DataImportException("Brak pliku \"" + FILE_NAME + "\"");
        } catch (IOException e) {
            throw new DataImportException("Błąd odczytu pliku \"" + FILE_NAME + "\"");
        } catch (ClassNotFoundException e) {
            throw new DataImportException("Niezgodny typ danych w pliku \"" + FILE_NAME + "\"");
        }
    }

    @Override
    public void exportData(BottlesCollection bottlesCollection) {
        try (
                var fos = new FileOutputStream(FILE_NAME);
                var oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(bottlesCollection);
        } catch (FileNotFoundException e) {
            throw new DataExportException("Brak pliku \"" + FILE_NAME + "\"");
        } catch (IOException e) {
            throw new DataExportException("Błąd zapisu danych do pliku \"" + FILE_NAME + "\"");
        }
    }
}