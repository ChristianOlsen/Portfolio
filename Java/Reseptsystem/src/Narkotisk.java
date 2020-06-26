public class Narkotisk extends Legemiddel {
    private int narkotiskStyrke;

    public Narkotisk(String navn, double pris, double virkestoff, int narkotiskStyrke) {
        super(navn, pris, virkestoff);
        this.narkotiskStyrke = narkotiskStyrke;
    }

    public int hentStyrke() {
        return narkotiskStyrke;
    }

    @Override
    public String toString() {
        return "Narkotisk{" + "Navn: " + this.hentNavn() + " Pris: " + this.hentPris() +
                " Virkestoff: " + this.hentVirkestoff() + " Styrke: " + this.hentStyrke() + " ID: " +
                this.hentID() +
                '}';
    }

}
