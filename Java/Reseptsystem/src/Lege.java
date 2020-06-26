public class Lege implements Comparable<Lege>{
    private String navn;
    private Lenkeliste<Resept> utskrevedeResepter;

    public Lege(String navn) {
        this.navn = navn;
        this.utskrevedeResepter = new Lenkeliste<>();
    }

    public String hentNavn() {
        return navn;
    }

    @Override
    public String toString() {
        return "Lege{" +
                "navn: '" + navn + '\'' +
                '}';
    }

    @Override
    public int compareTo(Lege lege) {
        return navn.compareTo(lege.navn);
    }

    public void leggTilResept(Resept utskrevetResept) {
        utskrevedeResepter.leggTil(utskrevetResept);
    }

    public Lenkeliste<Resept> hentUtskrevedeResepter() {
        return utskrevedeResepter;
    }

    private boolean narkotiskInnhold(Resept sjekkResept) {
        if (sjekkResept.hentLegemiddel() instanceof Narkotisk) {
            return true;
        }
        return false;
    }

    private boolean erSpesialist(Lege utskrivendeLege) {
        if (utskrivendeLege instanceof Spesialist) {
            return true;
        }
        return false;
    }

    public HvitResept skrivHvitResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
        HvitResept nyHvitResept = new HvitResept(legemiddel, this, pasient, reit);
        if(narkotiskInnhold(nyHvitResept) && !erSpesialist(nyHvitResept.hentUtskrivendeLege())) {
            throw new UlovligUtskrift(this, nyHvitResept.hentLegemiddel());
        }
        leggTilResept(nyHvitResept);
        return nyHvitResept;
    }

    public MilitaerResept skrivMillitaerResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
        MilitaerResept nyMilitaerResept = new MilitaerResept(legemiddel, this, pasient, reit);
        if(narkotiskInnhold(nyMilitaerResept) && !erSpesialist(nyMilitaerResept.hentUtskrivendeLege())) {
            throw new UlovligUtskrift(this, nyMilitaerResept.hentLegemiddel());
        }
        leggTilResept(nyMilitaerResept);
        return nyMilitaerResept;
    }

    public PResept skrivPResept(Legemiddel legemiddel, Pasient pasient) throws UlovligUtskrift {
        PResept nyPResept = new PResept(legemiddel, this, pasient);
        if(narkotiskInnhold(nyPResept) && !erSpesialist(nyPResept.hentUtskrivendeLege())) {
            throw new UlovligUtskrift(this, nyPResept.hentLegemiddel());
        }
        leggTilResept(nyPResept);
        return nyPResept;
    }
}
