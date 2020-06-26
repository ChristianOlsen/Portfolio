import java.util.Scanner;

public class Terminal implements Brukergrensesnitt {

    private int valg;

    public Terminal(Scanner input) {
        while (!input.hasNextInt()) {
            System.out.println("Vennligst sett inn et tall");
            input = new Scanner(System.in);
        }
        this.valg = input.nextInt();
        giStatus("");
    }

    public int hentValg() {
        return valg;
    }

    @Override
    public void giStatus(String status) {
        System.out.println(status);
    }

    @Override
    public int beOmKommando(String spoersmaal, String[] alternativer) {
        String info = spoersmaal;
        for (String alternativ : alternativer) {
            info += "\n " + alternativ;
        }
        System.out.println(info);
        return new Terminal(new Scanner(System.in)).hentValg();
    }
}