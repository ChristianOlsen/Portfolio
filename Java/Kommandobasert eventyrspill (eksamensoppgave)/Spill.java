import java.io.File;
import java.util.Scanner;

public class Spill {

    public static void main(String[] args) {
        File fil = new File("src/steder.txt");

        System.out.println(
            "Velkommen til spillet!" +
            "\nHvordan onsker du aa spille?" +
            "\n 1: En vei" +
            "\n 2: Flere veier"
        );
        if (valg() == 1) { // en vei
            Terreng t = new Terreng(fil);
            System.out.println(
                "\nVelg spillvariant" +
                "\n 1: Spill selv" +
                "\n 2: la robot spille"
            );
            if (valg() == 1) { // spill selv med en vei
                Spiller spiller = new Spiller(t.hentStartSted(), new Terminal(new Scanner("1")));
            } else { // robot spiller med en vei
                Spiller robot = new Spiller(t.hentStartSted(), new Robot(1));
            }
        } else { // flere veier
            Terreng t = new VeivalgTerreng(fil);
            System.out.println(
                "\nVelg spillvariant" +
                "\n 1: Spill selv" +
                "\n 2: la robot spille"
            );
            if (valg() == 1) { // spill selv med flere veier
                Spiller spiller = new VeivalgSpiller(t.hentStartSted(), new Terminal(new Scanner("1")));
            } else { // robot spiller med flere veier
                Spiller robot = new VeivalgSpiller(t.hentStartSted(), new Robot(1));
            }
        }
    }

    public static int valg() {
        Scanner scanner = new Scanner(System.in);
        String valg = scanner.next();
        while (!(valg.equals("1") || valg.equals("2"))) {
            System.out.println("Vennligst sett inn et gyldig valg");
            valg = scanner.next();
        }
        return Integer.parseInt(valg);
    }
}
