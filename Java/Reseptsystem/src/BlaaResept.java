public class BlaaResept extends Resept{
    private int rabatt = 75;

    //Valgte å sette lege som spesialist i dette tilfellet, da jeg tolket oppgaven slik.
    //Det står derimot ingenting om hvordan kontrollID til spesialist skal implementeres.
    public BlaaResept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient, int reit) {
        super(legemiddel, utskrivendeLege, pasient, reit);
    }
    public String farge(){
        return "Blaa";
    }
    public double prisAaBetale(){
        //finner avslag og returnerer den nye prisen.
        Legemiddel legemiddel = this.hentLegemiddel();
        double pris = legemiddel.hentPris();
        double avslag = (pris * this.rabatt)/100;
        return pris - avslag;
    }

    @Override
    public String toString() {
        return "BlaaResept{" + "Legemiddel: " + this.hentLegemiddel() + " Lege: " + this.hentUtskrivendeLege()
                + " pasientID: " + this.hentPasient() + " Reit igjen: " + this.hentReit() + " Rabatt: " + this.rabatt + "%" +"}";
    }
}
