package pl.blaszkiewiczslawek.absolut.io.file;

import pl.blaszkiewiczslawek.absolut.model.BottlesCollection;

public interface FileManager {

    BottlesCollection importData();

    void exportData(BottlesCollection bottlesCollection);
}
