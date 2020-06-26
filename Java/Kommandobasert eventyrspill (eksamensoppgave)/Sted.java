import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Sted {

    private String beskrivelse;
    private Kiste kiste;
    private List<Sted> utganger;

    public Sted(String beskrivelse) {
        this.beskrivelse = beskrivelse;
        utganger = new LinkedList<>();
        this.kiste = new Kiste((int) (1 + Math.random() * (5 - 1))); // Setter kapasitet mellom 1 og 5
        this.kiste.settVerdiDivergens(0.5 + Math.random() * (1.5 - 0.5)); // Tilfeldig pris per kiste
        this.kiste.giTilfeldigeGjenstander();
    }

    @Override
    public String toString() {
        return "\nSted{" +
                "beskrivelse='" + beskrivelse + '\'' +
                ", kiste=" + kiste +
                ", utganger=" + utganger.size() +
                '}';
    }

    public void leggTilUtgang(Sted utgang) {
        utganger.add(utgang);
    }

    public String hentBeskrivelse() {
        return beskrivelse;
    }

    public Kiste hentKiste() {
        return kiste;
    }

    public List<Sted> hentUtganger() {
        return utganger;
    }
}
