public class VeivalgSpiller extends Spiller {

    public VeivalgSpiller(Sted startSted, Terminal terminal) {
        super(startSted, terminal);
    }

    @Override
    public void gaaVidere() {
        String[] alternativer = {
                "1: Gaa til venstre",
                "2: Gaa rett frem",
                "3: Gaa til hoyre",
                "0: Forlat kiste"
        };
        int valg = hentTermianl().beOmKommando("Hvilken retning vil du gaa?", alternativer);
        switch (valg) {
            case 1:
                gaaTil(hentUtganger().get(0));
                break;
            case 2:
                gaaTil(hentUtganger().get(1));
                break;
            case 3:
                gaaTil(hentUtganger().get(2));
                break;
            case 0:
                nyttTrekk();
                break;
            default:
                hentTermianl().giStatus("Vennligst sett inn et tall av valgmulighetene");
                gaaVidere();
                break;
        }
    }
}
