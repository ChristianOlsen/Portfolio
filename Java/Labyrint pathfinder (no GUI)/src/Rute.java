import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

abstract class Rute {

    private Labyrint labyrint;
    private int ypos;
    private int xpos;
    private List<Rute> naboer;
    private boolean besokt;
    private boolean blindvei;
    private List<List<Rute>> utveier;
    private String traadFarge;

    public Rute(int ypos, int xpos) {
        this.ypos = ypos;
        this.xpos = xpos;
        this.besokt = false;
        this.blindvei = false;
        this.naboer = new ArrayList<>();
        this.utveier = new ArrayList<>();
        traadFarge = Farge.ANSI_RESET;
    }

    public void settNaboer(Rute[] nyeNaboer) {
        for (Rute nabo : nyeNaboer) {
            if (nabo != null) {
                this.naboer.add(nabo);
            }
        }
    }

    public String traadFarge() {
        return traadFarge;
    }

    public void resetTraadFarge() {
        traadFarge = Farge.ANSI_RESET;
    }

    public void settTraadFarge() {
        if (!hentLabyrint().harFarger()) {
            traadFarge = "";
            return;
        }
        String farge;
        String[] farger = {Farge.ANSI_PURPLE,
                Farge.ANSI_CYAN,
                Farge.ANSI_BLUE,
                Farge.ANSI_RED,
                Farge.ANSI_GREEN};
        if (Thread.currentThread().getName().equals("main")) {
            farge = Farge.ANSI_YELLOW;
        } else {
            int fargeID = (int) (Thread.currentThread().getId()%10/2);
            farge = farger[fargeID];
        }
/*
        switch (Thread.currentThread().getId()) {
            case "main":
                farge = Farge.ANSI_YELLOW;
                break;
            case "Thread-0":
                farge = Farge.ANSI_PURPLE;
                break;
            case "Thread-1":
                farge = Farge.ANSI_CYAN;
                break;
            case "Thread-2":
                farge = Farge.ANSI_BLUE;
                break;
            case "Thread-3":
                farge = Farge.ANSI_RED;
                break;
            case "Thread-4":
                farge = Farge.ANSI_GREEN;
                break;
            default:
                farge = Farge.ANSI_RESET;
        }

 */
        if (!this.erVei() || !this.erBesokt()) {
            farge = Farge.ANSI_RESET;
        }
        traadFarge = farge;
    }

    public void resetBesok() {
        this.besokt = false;
        resetTraadFarge();
    }

    public void settLabyrintReferanse(Labyrint labyrint) {
        this.labyrint = labyrint;
    }

    public void resetRute() {
        besokt = false;
        blindvei = false;
        resetTraadFarge();
        utveier.clear();
    }

    abstract char tilTegn();

    abstract boolean erVei();

    abstract boolean aapning();

    public void besok() {
        besokt = true;
        settTraadFarge();
    }

    public boolean erBlindvei() {
        return blindvei;
    }

    public void settBlindvei() {
        blindvei = true;
    }

    public boolean erBesokt() {
        return besokt;
    }

    public int y() {
        return ypos;
    }

    public int x() {
        return xpos;
    }

    public Labyrint hentLabyrint() {
        return labyrint;
    }

    public void leggTilUtvei(List<Rute> utvei) {
        utveier.add(utvei);
    }

    public List<Rute> naboer() {
        return naboer;
    }

    private Rute naboHoyre() {
        return naboer.get(0);
    }

    private Rute naboUnder() {
        return naboer.get(1);
    }

    private Rute naboVenstre() {
        return naboer.get(2);
    }

    private Rute naboOver() {
        return naboer.get(3);
    }

    public List<List<Rute>> hentUtveier() {
        return utveier;
    }

    public void printNaboer() {
        String print = this + ":";

        try { print += " Hoyre:" + naboHoyre().tilTegn();}
        catch (NullPointerException e) { print += " Hoyre: null"; }

        try { print += " Under:" + naboUnder().tilTegn();}
        catch (NullPointerException e) { print += " Under: null"; }

        try { print += " Venstre:" + naboVenstre().tilTegn();}
        catch (NullPointerException e) { print += " Venstre: null"; }

        try { print += " Over:" + naboOver().tilTegn();}
        catch (NullPointerException e) { print += " Over: null"; }

        System.out.println(print);
    }

    @Override
    public String toString() {
        return "(" + x() + "," + y() + ")";
    }

    public void finnUtvei() {
        List<Rute> aktivVei = new LinkedList<>();
        gaa(aktivVei);
    }

    abstract void gaa(List<Rute> aktivVei);

}
