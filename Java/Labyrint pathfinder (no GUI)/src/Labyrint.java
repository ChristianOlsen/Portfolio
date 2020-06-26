import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Labyrint {

    private Rute[][] rutenett;
    private boolean detaljert;
    private boolean farger;
    private List<Veifinnertraad> traader;

    private Labyrint(Rute[][] rutenett) {
        this.rutenett = rutenett;
        this.detaljert = false;
        this.farger = false;
        _settLabyrintRef();
        _settNaboRef();
        this.traader = new LinkedList<>();
    }

    public boolean erDetaljert() {
        return detaljert;
    }

    public List<Veifinnertraad> hentTraader() {
        return traader;
    }

    public void velgFargerik(boolean haFarger) {
        this.farger = haFarger;
    }

    public boolean harFarger() {
        return farger;
    }

    public void leggTilTraad(Veifinnertraad nyTraad) {
        traader.add(nyTraad);
    }

    public void settDetaljert(boolean detaljert) {
        this.detaljert = detaljert;
    }

    public int hoyde() {
        return rutenett.length;
    }

    public int bredde() {
        return rutenett[0].length;
    }

    public static Labyrint lesFraFil(File fil) throws FileNotFoundException {
        Scanner scanner = new Scanner(fil);
        int hoyde = scanner.nextInt();
        int bredde = scanner.nextInt();
        Rute[][] ruter = new Rute[hoyde][bredde];
        scanner.nextLine();

        int ypos = 0;
        while (scanner.hasNextLine()) {
            int xpos = 0;
            String[] tegn = scanner.nextLine().split("");

            for (String s : tegn) {
                boolean aapen = s.equals(".");
                Rute nyRute;
                if (aapen) {
                    if (ypos == 0 || xpos == 0 || ypos == hoyde-1 || xpos == bredde-1) {
                        nyRute = new Aapning(ypos, xpos);
                    } else {
                        nyRute = new HvitRute(ypos,xpos);
                    }
                } else {
                    nyRute = new SortRute(ypos,xpos);
                }
                ruter[ypos][xpos] = nyRute;

                xpos++;
            }
            ypos++;
        }
        scanner.close();
        return new Labyrint(ruter);
    }

    public Rute hentRute(int xPos, int yPos) {
        return rutenett[yPos][xPos];
    }

    public void printAlleNaboer() {
        System.out.println("(x,y)");

        for (int y = 0; y < hoyde(); y++) {
            for (int x = 0; x < bredde(); x++) {
                hentRute(x,y).printNaboer();
            }
        }

    }

    private void _settLabyrintRef() {
        for (int y = 0; y < hoyde(); y++) {
            for (int x = 0; x < bredde(); x++) {
                rutenett[y][x].settLabyrintReferanse(this);
            }
        }
    }

    private void _settNaboRef() {
        for (int y = 0; y<hoyde(); y++) {
            for (int x = 0; x<bredde(); x++) {

                Rute[] naboer = new Rute[4];
                int naboY = y;
                int naboX = x;

                for (int i=0; i<naboer.length; i++) {
                    switch (i) {
                        case 0:
                            naboY = y;
                            naboX = x+1;
                            break;
                        case 1:
                            naboY = y+1;
                            naboX = x;
                            break;
                        case 2:
                            naboY = y;
                            naboX = x-1;
                            break;
                        case 3:
                            naboY = y-1;
                            naboX = x;
                            break;
                    }
                    try {
                        naboer[i] = hentRute(naboX, naboY);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        naboer[i] = null;
                    }
                }

                hentRute(x,y).settNaboer(naboer);
            }
        }
    }

    @Override
    public String toString() {
        String utskrift = "";
        for (int y = 0; y < hoyde(); y++) {
            for (int x = 0; x < bredde(); x++) {
                if (harFarger()) {
                    utskrift += rutenett[y][x].traadFarge() + rutenett[y][x].tilTegn() + " ";
                } else {
                    utskrift += rutenett[y][x].tilTegn() + " ";
                }
            }
            utskrift += "\n";
        }
        return utskrift;
    }

    public void resetRutenett() {
        for (int y = 0; y < hoyde(); y++) {
            for (int x = 0; x < bredde(); x++) {
                rutenett[y][x].resetRute();
                rutenett[y][x].resetTraadFarge();
            }
        }
    }

    public void resetBesok() {
        for (int y = 0; y < hoyde(); y++) {
            for (int x = 0; x < bredde(); x++) {
                rutenett[y][x].resetBesok();
            }
        }
    }

    public Rute[][] hentRutenett() {
        return rutenett;
    }

    public List<String> finnUtveiFra(int startX, int startY) {
        List<List<Rute>> utveier;
        List<String> utveierString = new ArrayList<>();
        if (startX < 0 || startX >= hoyde() || startY < 0 || startY >= bredde()) {
            utveierString.add("Ugyldig startposisjon: utenfor brettet");

            resetRutenett();
            return null;
        }

        Rute startRute = rutenett[startY][startX];
        if (!startRute.erVei()) {
            utveierString.add("Ugyldig startposisjon: vegg");
            return null;
        } else {


            startRute.finnUtvei();
            for (Veifinnertraad traad : hentTraader()) {
                try {
                    traad.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }




            utveier = startRute.hentUtveier();
            int antUtveier = 0;
            for (List<Rute> utvei : utveier) {
                antUtveier++;
                String utveiString = antUtveier + ". utvei med " + utvei.size() + " steg: ";
                for (Rute vei : utvei) {
                    if (harFarger()) {
                        utveiString += " --> " + vei.traadFarge() + vei + Farge.ANSI_RESET;
                    } else {
                        utveiString += " --> " + vei;
                    }

                }
                utveierString.add(utveiString);
            }
        }
        resetRutenett();
        return utveierString;
    }
}
