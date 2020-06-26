import java.util.List;

public class SortRute extends Rute {

    public SortRute(int ypos, int xpos) {
        super(ypos, xpos);
    }

    public char tilTegn() {
        if (erBesokt()) {
            return "x".charAt(0);
        }
        return "\u2588".charAt(0);
    }

    public boolean erVei() {
        return false;
    }

    public boolean aapning() {
        return false;
    }

    public void gaa(List<Rute> aktivVei) {
        System.out.println("Her skjedde det noe galt. gaa() ble kjørt fra SortRute.");
    }
}
