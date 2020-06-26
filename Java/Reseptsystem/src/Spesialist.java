public class Spesialist extends Lege implements Godkjenningsfritak {
    private int kontrollID;

    public Spesialist(String navn, int kontrollID) {
        super(navn);
        this.kontrollID = kontrollID;
    }

    @Override
    public int hentKontrollID() {
        return this.kontrollID;
    }

    @Override
    public String toString() {
        return "Spesialist{" + "Navn: " + this.hentNavn() +
                " kontrollID:" + kontrollID +
                '}';
    }
    //denne legges her siden bare spesialister har lov til å skrive blaaresepter
    public BlaaResept skrivBlaaResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
        BlaaResept nyBlaaResept = new BlaaResept(legemiddel, this, pasient, reit);
        leggTilResept(nyBlaaResept);
        return nyBlaaResept;
    }
}
