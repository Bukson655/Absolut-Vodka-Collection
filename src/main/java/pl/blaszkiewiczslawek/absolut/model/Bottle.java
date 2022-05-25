package pl.blaszkiewiczslawek.absolut.model;

import java.io.Serializable;
import java.util.Objects;

public class Bottle implements Serializable, Comparable<Bottle> {

    private final String producer = "Absolut";
    private String edition;
    private double capacity;
    private String releaseDate;
    private String quantityReleased;
    private String additionalInfo;

    public Bottle(String edition, double capacity) {
        this.edition = edition.toUpperCase();
        this.capacity = capacity;
    }

    public Bottle(String edition, double capacity, String releaseDate) {
        this(edition, capacity);
        this.releaseDate = releaseDate;
    }

    public Bottle(String edition, double capacity, String releaseDate, String quantityReleased) {
        this(edition, capacity, releaseDate);
        this.quantityReleased = quantityReleased;
    }

    public Bottle(String edition, double capacity, String releaseDate, String quantityReleased, String additionalInfo) {
        this(edition, capacity, releaseDate, quantityReleased);
        this.additionalInfo = additionalInfo;
    }

    public String getProducer() {
        return producer;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getQuantityReleased() {
        return quantityReleased;
    }

    public void setQuantityReleased(String quantityReleased) {
        this.quantityReleased = quantityReleased;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String toCsv() {
        String information = producer + ";" + edition.toUpperCase() + ";" + capacity;
        if (!Objects.equals(releaseDate, "")) {
            information = information + ";" + releaseDate;
        } else if (releaseDate.equals("")) {
            releaseDate = "b/d";
            information += ";" + releaseDate;
        }
        if (!Objects.equals(quantityReleased, "")) {
            information += ";" + quantityReleased;
        } else if (quantityReleased.equals("")) {
            quantityReleased = "b/d";
            information += ";" + quantityReleased;
        }
        if (!Objects.equals(additionalInfo, "")) {
            information += ";" + additionalInfo;
        } else if (additionalInfo.equals("")) {
            additionalInfo = "b/d";
            information += ";" + additionalInfo;
        }
        return information;
    }

    public static String csvDescription() {
        return "Producent;Edycja;Objętość;Data Wydania;Ilość wydanych; Dodatkowe informacje";
    }

    @Override
    public String toString() {
        String information = producer + " " + edition + ", objętość: " + capacity;
        if (!Objects.equals(releaseDate, "")) {
            information = information + ", data produkcji: " + releaseDate;
        } else if (releaseDate.equals("")) {
            releaseDate = "b/d";
            information += ", data produkcji: " + releaseDate;
        }
        if (!Objects.equals(quantityReleased, "")) {
            information += ", wyprodukowano: " + quantityReleased;
        } else if (quantityReleased.equals("")) {
            quantityReleased = "b/d";
            information += ", wyprodukowano: " + quantityReleased;
        }
        if (!Objects.equals(additionalInfo, "")) {
            information += ", dodatkowe informacje: " + additionalInfo;
        } else if (additionalInfo.equals("")) {
            additionalInfo = "b/d";
            information += ", dodatkowe informacje: " + additionalInfo;
        }
        return information;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bottle bottle = (Bottle) o;
        return Double.compare(bottle.capacity, capacity) == 0 && Objects.equals(edition, bottle.edition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(producer, edition, capacity, releaseDate, quantityReleased);
    }

    @Override
    public int compareTo(Bottle bottle) {
        return edition.compareToIgnoreCase(bottle.edition);
    }

}
