public class Gjenstand {

    private String navn;
    private double verdi;

    public Gjenstand(String navn, double verdi) {
        this.navn = navn;
        this.verdi = verdi;
    }

    @Override
    public String toString() {
        return "Gjenstand{" +
                "navn='" + navn + '\'' +
                ", verdi=" + verdi +
                '}';
    }

    public String hentNavn() {
        return navn;
    }

    public double hentVerdi() {
        return verdi;
    }
}
