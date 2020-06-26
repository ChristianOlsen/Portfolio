import java.util.Scanner;

public class Robot extends Terminal {

    private int valg;

    public Robot(int valg) {
        super(new Scanner(Integer.toString(valg)));
        /*
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

         */
        giStatus(Integer.toString(valg));
        this.valg = valg;
    }

    @Override
    public int beOmKommando(String spoersmaal, String[] alternativer) {
        String info = spoersmaal;
        for (String alternativ : alternativer) {
            info += "\n " + alternativ;
        }
        System.out.println(info);
        int tilfeldigValg = (int)(Math.random() * (alternativer.length));
        return new Robot(tilfeldigValg).valg;
    }
}
