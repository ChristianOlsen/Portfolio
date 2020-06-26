import java.util.Scanner;

public class Hovedprogram {

    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        Legesystem legesystemet = new Legesystem("src/InnlesingEksempel2.txt");

        boolean avslutt = false;
        int valg = 0;

        while (!avslutt) {
            printValg();

            if (input.hasNextInt()) {

                valg = input.nextInt();

                switch (valg) {

                    case 0:
                        avslutt = true;
                        break;
                    case 1:
                        // Oversikt over alle instanser i legesystemet
                        legesystemet.printOversikt();
                        break;
                    case 2:
                        // Legge til instanser fra meny
                        legesystemet.leggTilInstans();
                        break;
                    case 3:
                        legesystemet.brukResept();
                        break;
                    case 4:
                        legesystemet.visStatistikk();
                        break;
                    case 5:
                        legesystemet.skrivTilNyFil();
                        break;
                    default:
                        System.out.println("Tallet du satt inn var ikke mellom 0-5");
                }
            } else {
                System.out.println("Vennligst sett inn et tall mellom 0-5");
            }
            input.nextLine();
        }
    }

    private static void printValg() {
        System.out.println();
        System.out.println("============================================");
        System.out.println("Sett inn tallet for valgmuligheten:");
        System.out.println("[1] Fullstendig oversikt over pasienter, leger, legemidler og resepter");
        System.out.println("[2] Opprette og legge til nye elementer i systemet");
        System.out.println("[3] Bruke en gitt resept fra listen til en pasient");
        System.out.println("[4] Skrive ut forskjellige former for statistikk");
        System.out.println("[5] Skrive alle data til fil ");
        System.out.println("[0] Avslutt programmet");
    }
}
