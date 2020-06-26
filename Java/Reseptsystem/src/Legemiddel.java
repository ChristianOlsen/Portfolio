public abstract class Legemiddel {
    private int ID;
    private static int teller = 0;
    private String navn;
    private double pris;
    private double virkestoff;

    int antVanedannende = 0;

    public Legemiddel(String navn, double pris, double virkestoff) {
        //lager unik ID hver gang konstruktør kjøres
        this.ID = teller++;
        this.navn = navn;
        this.pris = pris;
        this.virkestoff = virkestoff;
    }

    public int hentID() {
        return ID;
    }

    public String hentNavn() {
        return navn;
    }

    public double hentPris() {
        return Math.round(pris*100)/100.0;
    }

    public double hentVirkestoff() {
        return virkestoff;
    }

    public void settNyPris(double pris) {
        this.pris = pris;
    }

    public String hentType() {
        if (this instanceof Vanlig) {
            return "vanlig";
        }
        if (this instanceof Vanedannende) {
            return "vanedannende";
        }
        if (this instanceof Narkotisk) {
            return "narkotisk";
        }
        return "Feil: ukjent type";
    }
}
