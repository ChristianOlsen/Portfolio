public abstract class Resept {
    private int ID;
    private static int teller = 0;
    private Legemiddel legemiddel;
    private Lege utskrivendeLege;
    private Pasient pasient;
    private int reit;

    public Resept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient pasient, int reit) {
        this.ID = teller++;
        this.legemiddel = legemiddel;
        this.utskrivendeLege = utskrivendeLege;
        this.pasient = pasient;
        this.reit = reit;
        utskrivendeLege.leggTilResept(this);
        pasient.leggTilResept(this);
    }

    public int hentReseptID() {
        return ID;
    }

    public Legemiddel hentLegemiddel() {
        return legemiddel;
    }

    public Lege hentUtskrivendeLege() {
        return utskrivendeLege;
    }

    public Pasient hentPasient() {
        return pasient;
    }

    public int hentReit() {
        return reit;
    }
    //forsøker å bruke resepten én gang. Returner false om resepten alt
    //er oppbrukt, ellers returnerer den true
    public boolean bruk(){
        if (this.reit > 0){
            this.reit = this.reit - 1;
            return true;
        }
        return false;

    }
    //returnerer reseptens farge
    public abstract String farge();

    //returnerer prisen pasienten må betale.
    abstract public double prisAaBetale();

    public String hentType() {
        if (this instanceof MilitaerResept) {
            return "militaer";
        }
        if (this instanceof PResept) {
            return "p";
        }
        if (this instanceof HvitResept) {
            return "hvit";
        }
        if (this instanceof BlaaResept) {
            return "blaa";
        }
        return "Feil: ukjent type";
    }
}
