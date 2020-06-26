import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HvitRute extends Rute {

    public HvitRute(int ypos, int xpos) {
        super(ypos, xpos);
    }

    public char tilTegn() {
        if (erBlindvei()) {
            return "@".charAt(0);
        } else if (erBesokt()) {
            return "x".charAt(0);
        } else
        return ".".charAt(0);
    }

    public boolean erVei() {
        return true;
    }

    public boolean aapning() {
        return false;
    }

    public void gaa(List<Rute> aktivVei) {
        besok();
        Rute neste;
        List<Rute> nyAktivVei = new ArrayList<>(aktivVei);
        nyAktivVei.add(this);

        List<Rute> veier = new LinkedList<>();
        for (Rute nabo : naboer()) {
            if (nabo.erVei() && !nabo.erBesokt() && !nabo.erBlindvei()) {
                veier.add(nabo);
            }
        }
        if (veier.size() == 0) {
            return;
        }
        neste = veier.get(0);
        veier.remove(0);
        List<Veifinnertraad> subTraader = new ArrayList<>();
        for (Rute vei : veier) {
            Veifinnertraad nyTraad = new Veifinnertraad(vei, nyAktivVei);
            nyTraad.start();
            subTraader.add(nyTraad);
        }
        if (hentLabyrint().erDetaljert()) {
            System.out.println(hentLabyrint());
        }
        neste.gaa(nyAktivVei);
        for (Veifinnertraad traad : subTraader) {
            try {
                traad.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
