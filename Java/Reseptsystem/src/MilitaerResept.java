public class MilitaerResept extends HvitResept {
    //rabatt i prosent
    private int rabatt = 100;

    public MilitaerResept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient, int reit) {
        super(legemiddel, utskrivendeLege, pasient, reit);
    }
    public double prisAaBetale(){
        //Teknisk sett blir pris uansett 0, men tar med utregning likevel.
        Legemiddel legemiddel = this.hentLegemiddel();
        double pris = legemiddel.hentPris();
        double avslag = (pris * this.rabatt)/100;
        return pris - avslag;
    }

    @Override
    public String toString() {
        return "Militærresept{" + "Legemiddel: " + this.hentLegemiddel() + " Lege: " + this.hentUtskrivendeLege()
                + " pasientID: " + this.hentPasient() + " Reit igjen: " + this.hentReit() + " Rabatt: " + this.rabatt + "%" +"}";
    }
}
