import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.util.jar.JarOutputStream;

public class Kiste {

    private LinkedList<Gjenstand> gjenstander;
    private double verdiDivergens;
    private int kapasitet;

    public Kiste(int kapasitet) {
        this.verdiDivergens = 1;
        this.kapasitet = kapasitet;
        gjenstander = new LinkedList<>();
    }

    @Override
    public String toString() {
        return "Kiste{" +
                "gjenstander=" + gjenstander +
                ", verdiDivergens=" + verdiDivergens +
                ", kapasitet=" + kapasitet +
                '}';
    }

    public void giTilfeldigeGjenstander() {
        try {
            File gjenstanderFil = new File("src/gjenstander.txt");
            Scanner scanner = new Scanner(gjenstanderFil);
            int antGjenstander = antallGjenstander(gjenstanderFil);
            while (scanner.hasNextLine()) {
                // 1/antallGjenstander/kapasitet-dels sannsynlighet for at gjenstanden legges til i kisten
                if (new Random().nextInt(antGjenstander/kapasitet) == 0) {
                    Gjenstand nyGjenstand = new Gjenstand(scanner.next(), scanner.nextInt());
                    gjenstander.add(nyGjenstand);
                } else {
                    scanner.nextLine();
                }
                if (erFull()) {
                    break;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private int antallGjenstander(File fil) throws FileNotFoundException {
        Scanner teller = new Scanner(fil);
        int antGjenstander = 0;
        while (teller.hasNextLine()) {
            antGjenstander++;
            teller.nextLine();
        }
        return antGjenstander;
    }

    public void settVerdiDivergens(double nyVerdiDivergens) {
        verdiDivergens = nyVerdiDivergens;
    }

    public LinkedList<Gjenstand> hentGjenstander() {
        return gjenstander;
    }

    public double hentVerdiDivergens() {
        return verdiDivergens;
    }

    public Gjenstand taUt(int valg) {
        Gjenstand gjenstand = gjenstander.get(valg);
        gjenstander.remove(gjenstand);
        return gjenstand;
    }

    public Gjenstand taUtTilfeldig() {
        int tilfeldigValg = (int) (Math.random() * gjenstander.size());
        return taUt(tilfeldigValg);
    }

    public double leggNed(Gjenstand gjenstand) {
        double fortjeneste = gjenstand.hentVerdi() * verdiDivergens;
        gjenstander.add(gjenstand);
        return fortjeneste;
    }

    public boolean erTom() {
        if (gjenstander.size() == 0) {
            return true;
        }
        return false;
    }

    public boolean erFull() {
        if (gjenstander.size() >= kapasitet) {
            return true;
        }
        return false;
    }

    public int hentKapasitet() {
        return kapasitet;
    }

    public int antallGjenstander() {
        return gjenstander.size();
    }
}
