import java.util.ArrayList;
import java.util.List;

public class Aapning extends  HvitRute {

    public Aapning(int ypos, int xpos) {
        super(ypos, xpos);
    }

    public char tilTegn() {
        if (erBlindvei()) {
            return "@".charAt(0);
        } else if (erBesokt()) {
            return "x".charAt(0);
        }
        return "O".charAt(0);
    }

    public boolean erVei() {
        return true;
    }

    public boolean aapning() {
        return true;
    }

    public void gaa(List<Rute> aktivVei) {
        besok();
        //Rute startrute = aktivVei.get(0);
        List<Rute> nyAktivVei = new ArrayList<>(aktivVei);
        nyAktivVei.add(this);
        synchronized (nyAktivVei.get(0)) {
            // Monitor som sikrer at threads ikke kan legge til i listen
            // over utveier samtidig
            nyAktivVei.get(0).leggTilUtvei(nyAktivVei);
        }
        if (hentLabyrint().erDetaljert()) {
            System.out.println(hentLabyrint());
        }

        System.out.println("Traad [" + traadFarge() + Veifinnertraad.currentThread().getName() + Farge.ANSI_RESET
                + "] fant utvei med " + (nyAktivVei.size()-1)
                + " steg: " + nyAktivVei);
    }
}
