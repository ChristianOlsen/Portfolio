import org.w3c.dom.ls.LSOutput;

public class PResept extends HvitResept {
    //rabatt i kr, pasient kan ikke betale mindre enn 0,-
    public static int rabatt = 108;

    public PResept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient) {
        super(legemiddel, utskrivendeLege, pasient, 3);

    }
    public double prisAaBetale(){
        //Sjekker om rabatten er større en pris. Hvis rabatten er større, er produktet gratis.
        Legemiddel legemiddel = this.hentLegemiddel();
        double pris = legemiddel.hentPris();
        if (pris <= rabatt){
            return 0;
        } else {
            return pris - rabatt;
        }
    }

    @Override
    public String toString() {
        return "P-Resept{" + "Legemiddel: " + this.hentLegemiddel() + " Lege: " + this.hentUtskrivendeLege()
                + " pasientID: " + this.hentPasient() + " Reit igjen: " + this.hentReit() + " Rabatt: " + rabatt + "kr" +"}";
    }
}
