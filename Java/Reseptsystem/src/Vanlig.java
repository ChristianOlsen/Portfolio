public class Vanlig extends Legemiddel {
    public Vanlig(String navn, double pris, double virkestoff) {
        super(navn, pris, virkestoff);
    }

    @Override
    public String toString() {
        return "Vanlig{" + "Navn: " + this.hentNavn() + " Pris: " + this.hentPris() +
                " Virkestoff: " + this.hentVirkestoff() +  " ID: " +
                this.hentID() +
                '}';
    }
}
