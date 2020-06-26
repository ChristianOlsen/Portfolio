/*
OBLIG SPØRSMÅL:
Hva skjer om den gamle tråden først går videre til neste rute og så etterpå
starter opp nye tråder?

Om jeg ikke starter en ny tråd før den gamle tråden har gått videre
til neste rute, kan det hende at disse trådene undersøker sammme vei
parallellt. I min implementasjon av løsningen ville én av disse trådene
stoppet, for hver eneste tråd "flagger" rutene de har vært i som "besøkt".
De beveger seg altså ikke i forhold til en referanse til forrive rute,
men sjekker istedet en boolean i labyrint-instansen om ruten
i potensiell vei er "besøkt".
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

class Oblig6 {
    public static void main(String[] args) {

        String filnavn = null;

        if (args.length > 0) {
            filnavn = args[0];
        } else {
            System.out.println("FEIL! Riktig bruk av programmet: "
                    +"java Oblig5 <labyrintfil>");
            return;
        }
        File fil = new File(filnavn);
        Labyrint l = null;
        try {
            l = Labyrint.lesFraFil(fil);
        } catch (FileNotFoundException e) {
            System.out.printf("FEIL: Kunne ikke lese fra '%s'\n", filnavn);
            System.exit(1);
        }

        // les start-koordinater fra standard input
        Scanner inn = new Scanner(System.in);
        System.out.println("Skriv inn koordinater <kolonne> <rad>");
        System.out.println("Ett ekstra parameter for labyrint-utskrift, to ekstra parameter for farger på traader");
        System.out.println("('a' for aa avslutte)");
        String[] ord = inn.nextLine().split(" ");
        while (!ord[0].equals("a")) {
            l.resetRutenett();
            l.settDetaljert(false);
            l.velgFargerik(false);

            try {
                int startKol = Integer.parseInt(ord[0]);
                int startRad = Integer.parseInt(ord[1]);
                if (ord.length > 2) {
                    l.settDetaljert(true);
                }
                if (ord.length > 3) {
                    l.velgFargerik(true);
                }

                List<String> utveier = l.finnUtveiFra(startKol, startRad);
                if (utveier.size() != 0) {
                    for (String s : utveier) {
                        System.out.println(s);
                    }
                    printInfo();
                } else {
                    System.out.println("Ingen utveier.");
                }
                System.out.println();
                l.hentRute(startKol, startRad).resetRute();
            } catch (NumberFormatException e) {
                System.out.println("Ugyldig input!");
            }


            System.out.println("Skriv inn nye koordinater ('a' for aa avslutte)");
            ord = inn.nextLine().split(" ");
        }
    }

    public static void printInfo() {
        System.out.println("\nNOTASJON:");
        System.out.println("\u2588 = vegg");
        System.out.println("O = åpning");
        System.out.println(". = vei");
        System.out.println("x = tidligere gått");
        System.out.println("@ = blindvei");
        System.out.println("[" + Farge.ANSI_YELLOW+"main" + Farge.ANSI_RESET + "] traad er gul");
        System.out.println("[" + Farge.ANSI_PURPLE+"Thread-n" + Farge.ANSI_RESET + "] får nye farger for hver nye traad.");
    }
}