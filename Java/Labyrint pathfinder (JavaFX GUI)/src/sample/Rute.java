package sample;

import javafx.scene.control.Button;

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
    private Button knapp;

    public Rute(int ypos, int xpos) {
        this.ypos = ypos;
        this.xpos = xpos;
        this.besokt = false;
        this.blindvei = false;
        this.naboer = new ArrayList<>();
        this.utveier = new ArrayList<>();
        traadFarge = "grey";
    }

    public void settNaboer(Rute[] nyeNaboer) {
        for (Rute nabo : nyeNaboer) {
            if (nabo != null) {
                this.naboer.add(nabo);
            }
        }
    }

    public void resetUtveier() {
        utveier.clear();
    }

    public String traadFarge() {
        return traadFarge;
    }

    public void settKnapp(Button knapp) {
        this.knapp = knapp;
    }

    public Button hentKnapp() {
        return knapp;
    }

    public void resetTraadFarge() {
        traadFarge = "grey";
    }

    public void settTraadFarge() {
        if (!hentLabyrint().harFarger()) {
            traadFarge = "";
            return;
        }
        String farge;
        String[] farger = {"purple",
                "cyan",
                "blue",
                "red",
                "green"};
        if (Thread.currentThread().getName().equals("main")) {
            farge = Farge.ANSI_YELLOW;
        } else {
            int fargeID = (int) (Thread.currentThread().getId()%10);
            farge = farger[fargeID];

        }
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
