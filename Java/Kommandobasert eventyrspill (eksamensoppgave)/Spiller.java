import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;

public class Spiller {

    private double formue;
    private Terminal terminal;
    private Sted posisjon;
    private Kiste sekk;

    public Spiller(Sted startSted, Terminal terminal) {
        this.formue = 0;
        this.terminal = terminal;
        this.posisjon = startSted;
        this.sekk = new Kiste(3);
        terminal.giStatus(posisjon.hentBeskrivelse());
        nyttTrekk();
    }

    @Override
    public String toString() {
        return "Spiller{" +
                "formue=" + hentFormue() +
                ", kommandoValg=" + terminal +
                ", posisjon=" + posisjon +
                ", sekk=" + sekk +
                '}';
    }

    public Terminal hentTermianl() {
        return terminal;
    }

    public Kiste hentSekk() {
        return sekk;
    }

    public List<Sted> hentUtganger() {
        return posisjon.hentUtganger();
    }

    public void nyttTrekk() {
        String[] alternativer = {
                "1: Gaa videre",
                "2: Aapne kiste",
                "0: Avslutt spill"
        };
        int valg = terminal.beOmKommando("Hva vil du gjore?", alternativer);
        switch (valg) {
            case 1:
                gaaVidere();
                break;
            case 2:
                aapneKiste();
                break;
            case 0:
                avsluttSpill();
                break;
            default:
                terminal.giStatus("Vennligst sett inn et tall av valgmulighetene");
                nyttTrekk();
                break;
        }
    }

    public Sted hentPos() {
        return posisjon;
    }

    public void gaaVidere() {
        gaaTil(hentUtganger().get(0));
    }

    public void gaaTil(Sted nyPos) {
        this.posisjon = nyPos;
        terminal.giStatus(posisjon.hentBeskrivelse());
        nyttTrekk();
    }

    public void aapneKiste() {
        String[] alternativer = {
                "1: Ta ut gjenstand",
                "2: Selg gjenstand",
                "0: Forlat kiste"
        };
        int valg = terminal.beOmKommando("Hvordan vil du bruke kisten?", alternativer);
        switch (valg) {
            case 1:
                taGjenstand();
                break;
            case 2:
                selg();
                break;
            case 0:
                nyttTrekk();
                break;
            default:
                terminal.giStatus("Vennligst sett inn et tall av valgmulighetene");
                aapneKiste();
                break;
        }
    }

    public void taGjenstand() {
        if (sekk.erFull()) {
            terminal.giStatus("Sekken din er full!");
        } else {
            if (!hentPos().hentKiste().erTom()) {
                Gjenstand gjenstand = hentPos().hentKiste().taUtTilfeldig();
                sekk.leggNed(gjenstand);
                terminal.giStatus("Du fikk: " + gjenstand.hentNavn() + ", med verdi " + gjenstand.hentVerdi() + " gull!");
                terminal.giStatus("Sekken din er naa " + sekk.antallGjenstander() + "/" + sekk.hentKapasitet() + " full");
            } else {
                terminal.giStatus("Kisten er tom!");
            }
        }

        aapneKiste();
    }

    public void selg() {
        if (sekk.erTom()) {
            terminal.giStatus("Sekken din er tom!");
            aapneKiste();
        } else if (posisjon.hentKiste().erFull()) {
            terminal.giStatus("Kisten er full!");
            aapneKiste();
        } else {
            List<String> alternativer = new LinkedList<>();
            int gjenstandIndeks = 1;
            for (Gjenstand gjenstand : sekk.hentGjenstander()) {
                double verdi = gjenstand.hentVerdi()*posisjon.hentKiste().hentVerdiDivergens();
                double avrundetVerdi = Math.floor(verdi * 100) / 100;
                alternativer.add(gjenstandIndeks + ": " + gjenstand.hentNavn() + " - " + avrundetVerdi + " gull");
                gjenstandIndeks++;
            }
            alternativer.add("0: Avbryt salg");

            String[] alternativerStr = alternativer.toArray(String[]::new);
            int valg = terminal.beOmKommando("Velg gjenstand du oensker aa selge", alternativerStr);
            if (valg > alternativer.size()-1) {
                terminal.giStatus("Vennligst velg en gyldig gjenstand");
                selg();
            } else if (valg == 0) {
                aapneKiste();
            } else {
                selgGjenstand(valg-1);
            }
        }
    }

    public void selgGjenstand(int valg) {
        if (!sekk.erTom()) {
            Gjenstand gjenstand = sekk.taUt(valg);
            formue += hentPos().hentKiste().leggNed(gjenstand);
            double verdi = rundNed(gjenstand.hentVerdi()*posisjon.hentKiste().hentVerdiDivergens());
            terminal.giStatus("Du solgte " + gjenstand.hentNavn() + " for " + verdi + " gull");
            terminal.giStatus("Din formue er naa: " + hentFormue() + " gull!");
            selg();
        } else {
            terminal.giStatus("Du har ingenting å selge!");
            aapneKiste();
        }
    }

    private double rundNed(double verdi) {
        return Math.floor(verdi * 100) / 100;
    }

    public double hentFormue() {
        return rundNed(formue);
    }

    public void avsluttSpill() {
        String avskjed =
                "Du avslutter reisen din i mens " + posisjon.hentBeskrivelse() +
                "\nI lomma hadde du " + hentFormue() +
                " gull!";
        if (!sekk.erTom()) {
            avskjed += " \nI sekken din hadde du ";
            double gjenstanderVerdi = 0;
            for (Gjenstand gjenstand : sekk.hentGjenstander()) {
                avskjed += "\n - " + gjenstand.hentNavn();
                gjenstanderVerdi += gjenstand.hentVerdi();
            }
            avskjed += "\ntil en samlet verdi av " + rundNed(gjenstanderVerdi) + " gull!";
        }
        terminal.giStatus(avskjed);
        ResultatVindu resultatVindu = new ResultatVindu(avskjed);
        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            resultatVindu.lukkVindu();

        }).start();


    }
}
