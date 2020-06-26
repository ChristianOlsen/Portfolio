public class HvitResept extends Resept {
    public HvitResept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient, int reit) {
        super(legemiddel, utskrivendeLege, pasient, reit);
    }
    public String farge(){
        return "Hvit";
    }

    public double prisAaBetale(){
        Legemiddel legemiddel = this.hentLegemiddel();
        return legemiddel.hentPris();
    }

    @Override
    public String toString() {
        return "HvitResept{" + "Legemiddel: " + this.hentLegemiddel() + " Lege: " + this.hentUtskrivendeLege()
        + " pasientID: " + this.hentPasient() + " Reit igjen: " + this.hentReit() + "}";
    }
}
