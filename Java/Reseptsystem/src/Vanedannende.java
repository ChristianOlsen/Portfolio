public class Vanedannende extends Legemiddel {
    private int vanedannendeStyrke;

    public Vanedannende(String navn, double pris, double virkestoff, int vanedannendeStyrke) {
        super(navn, pris, virkestoff);
        this.vanedannendeStyrke = vanedannendeStyrke;
    }

    public int hentStyrke() {
        return vanedannendeStyrke;
    }

    @Override
    public String toString() {
        return "Vanedannende{" + "Navn: " + this.hentNavn() + " Pris: " + this.hentPris() +
                " Virkestoff: " + this.hentVirkestoff() + " Styrke: " + this.hentStyrke() + " ID: " +
                this.hentID() +
                '}';
    }
}