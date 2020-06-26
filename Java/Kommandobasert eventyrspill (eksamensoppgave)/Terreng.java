import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Terreng{

    private List<Sted> steder;
    private Sted startSted;
    private int antUtganger;

    public Terreng(File stederFil) {
        this.steder = new LinkedList<>();
        settAntUtganger(1);
        _lesFraFil(stederFil);
        _leggTilUtganger();
        this.startSted = tilfeldigSted();
    }

    private void _lesFraFil(File stederFil) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(stederFil);
        } catch (FileNotFoundException e) {
            System.out.println("Fant ikke filen " + stederFil.getName());
            e.printStackTrace();
        }
        while (scanner.hasNextLine()) {
            leggTilSted(scanner.nextLine());
        }
        scanner.close();
    }

    public void leggTilSted(String beskrivelse) {
        leggTilSted(new Sted(beskrivelse));
    }

    public void leggTilSted(Sted nyttSted) {
        steder.add(nyttSted);
    }

    @Override
    public String toString() {
        return "Terreng{" +
                "steder=" + steder +
                '}';
    }

    public Sted tilfeldigSted() {
        Sted tilfeldigSted = steder.get((int) (Math.random() * steder.size()));
        return tilfeldigSted;
    }

    public void _leggTilUtganger() {
        for (Sted sted : hentSteder()) {
            while (sted.hentUtganger().size() <= antUtganger+1) {
                boolean gyldigUtgang = true;
                Sted tilfeldigUtgang = tilfeldigSted();
                if (tilfeldigUtgang == sted) {
                    gyldigUtgang = false;
                }
                for (Sted utgang : sted.hentUtganger()) {
                    if (tilfeldigUtgang == utgang) {
                        gyldigUtgang = false;
                        break;
                    }
                }
                if (gyldigUtgang) {
                    sted.leggTilUtgang(tilfeldigUtgang);
                }
            }
        }
    }

    public Sted hentStartSted() {
        return startSted;
    }

    public List<Sted> hentSteder() {
        return steder;
    }

    public void settAntUtganger(int antUtganger) {
        this.antUtganger = antUtganger;
    }
}
