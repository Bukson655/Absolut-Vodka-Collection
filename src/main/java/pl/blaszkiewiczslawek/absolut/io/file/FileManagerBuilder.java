package pl.blaszkiewiczslawek.absolut.io.file;

import pl.blaszkiewiczslawek.absolut.io.ConsolePrinter;
import pl.blaszkiewiczslawek.absolut.io.DataReader;

public class FileManagerBuilder {

    private final ConsolePrinter printer;
    private final DataReader reader;

    public FileManagerBuilder(ConsolePrinter printer, DataReader reader) {
        this.printer = printer;
        this.reader = reader;
    }

    public FileManager build() {
        printer.printLine("Wybierz format danych do zapisu:");
        FileType fileType = getFileType();
        return switch (fileType) {
            case SERIAL -> new SerializableFileManager();
            case CSV -> new CsvFileManager();
        };
    }

    private FileType getFileType() {
        boolean typeOk = false;
        FileType result = null;
        do {
            printTypes();
            String type = reader.getString().toUpperCase();
            try {
                result = FileType.valueOf(type);
                typeOk = true;
            } catch (IllegalArgumentException e) {
                printer.printLine("Wprowadziłeś błędny format: \"" + type + "\".");
                printer.printLine("Wybierz ponownie format danych do zapisu: ");
            }
        } while (!typeOk);
        return result;
    }

    private void printTypes() {
        for (FileType value : FileType.values()) {
            printer.printLine(value.name());
        }
    }

    private enum FileType {
        SERIAL, CSV
    }
}
