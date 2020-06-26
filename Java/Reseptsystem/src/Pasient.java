public class Pasient implements Comparable<Pasient> {
    private String navn;
    private String foedselsnummer;
    private int ID;
    private static int teller = 0;
    private Lenkeliste<Resept> resepter;

    public Lenkeliste<Resept> hentResepter() {
        return resepter;
    }

    public void leggTilResept(Resept nyResept) {
        resepter.leggTil(nyResept);
    }

    //konstruktør
    public Pasient(String navn, String foedselsnummer) {
        this.navn = navn;
        this.foedselsnummer = foedselsnummer;
        this.ID = teller++;
        resepter = new Lenkeliste<>();
    }
    /*
    Siden pasienten ofte vi bruke en resept kort tid etter at den er utskrevet,
     bruker vi en Stabel<Resept> til å lagre pasientens resepter.
      Det skal både være mulig å legge til nye resepter og hente ut hele reseptlisten.
     */

    @Override
    public String toString() {
        return "Pasient{" +
                "navn:'" + navn + '\'' +
                ", foedselsnummer:'" + foedselsnummer + '\'' +
                ", ID:" + ID +
                '}';
    }

    public String hentNavn() {
        return navn;
    }

    public String hentFoedselsnummer() {
        return foedselsnummer;
    }

    public int hentID() {
        return ID;
    }

    @Override
    public int compareTo(Pasient pasient) {
        return navn.compareTo(pasient.navn);
    }
}
