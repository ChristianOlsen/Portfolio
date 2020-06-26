import java.util.List;

public class Veifinnertraad extends Thread {

    private Rute neste;
    private List<Rute> aktivVei;
    private List<Veifinnertraad> underTraader;

    public Veifinnertraad(Rute neste, List<Rute> aktivVei) {
        this.neste = neste;
        this.aktivVei = aktivVei;
    }

    public void leggTilUnderTraader(Veifinnertraad underTraader) {
        this.underTraader.add(underTraader);
    }

    @Override
    public void run () {
        neste.gaa(aktivVei);
        System.out.println("[" + Thread.currentThread().getName() + "] terminert.");
    }

}
