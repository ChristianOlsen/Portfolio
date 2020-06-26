import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

class Test {
    public static void main(String[] args) {
        String filnavn = "src/4.in";

        File fil = new File(filnavn);
        Labyrint l = null;
        try {
            l = Labyrint.lesFraFil(fil);
        } catch (FileNotFoundException e) {
            System.out.printf("FEIL: Kunne ikke lese fra '%s'\n", filnavn);
            System.exit(1);
        }

        l.settDetaljert(true);
        l.velgFargerik(true);
        List<String> utveier;
        int xPos = 1;
        int yPos = 1;
        Rute startrute = l.hentRute(xPos, yPos);

        utveier = l.finnUtveiFra(xPos, yPos);
        if (utveier.size() != 0) {
            for (String s : utveier) {
                System.out.println(s);
            }
            if (l.erDetaljert()) {
                printInfo();
            }
        } else {
            System.out.println("Ingen utveier.");
        }
        System.out.println();
        }

    public static void printInfo() {
        System.out.println("\nNOTASJON:");
        System.out.println("\u2588 = vegg");
        System.out.println("O = åpning");
        System.out.println(". = vei");
        System.out.println("x = tidligere gått");
        System.out.println("@ = blindvei");
        System.out.println("[" + Farge.ANSI_YELLOW+"main" + Farge.ANSI_RESET + "] traad er gul");
        System.out.println("[" + Farge.ANSI_PURPLE+"Thread-n" + Farge.ANSI_RESET + "] får nye farger for hver nye traad, opp til 5 nye traader");
    }

}