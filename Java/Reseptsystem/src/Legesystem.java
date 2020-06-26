import java.io.*;
import java.util.Scanner;

public class Legesystem {

    //Lager lister som skal inneholde all informasjon fra fil og bruker
    private SortertLenkeliste<Pasient> pasienter = new SortertLenkeliste<>();
    private Lenkeliste<Legemiddel> legemidler = new Lenkeliste<>();
    private SortertLenkeliste<Lege> leger = new SortertLenkeliste<>();
    private Lenkeliste<Spesialist> spesialister = new Lenkeliste<>();

    //lager instansvariabler for å telle hvor mange instanser som lages
    private int forsoekedeInstanser = 0;
    private int ugyldigeInstanser = 0;

    int totVanedannendeResepter = 0;
    int totNarkotiskeResepter = 0;

    int instanserLagretIFil = 0;

    private int gyldigeInstanser() {
        int instanser = pasienter.stoerrelse() + legemidler.stoerrelse() + leger.stoerrelse() + spesialister.stoerrelse();
        for (Lege lege : leger) {
            for (Resept resept : lege.hentUtskrevedeResepter()) {
                instanser++;
            }
        }
        for (Spesialist spesialist : spesialister) {
            for (Resept resept : spesialist.hentUtskrevedeResepter()) {
                instanser++;
            }
        }
        return instanser;
    }

    //Metoder for behandling av informasjon om pasient

    //metoden henter pasienter
    public Lenkeliste<Pasient> hentPasienter() {
        return pasienter;
    }

    //metode for å legge til nye pasienter
    public void leggTilPasient(Pasient nyPasient) {
        pasienter.leggTil(nyPasient);
    }

    //metode for å gjøre om en string med informasjon om pasient til objektet pasient
    //Stringen må skrives i riktig format og skiller informasjonen i et array, som deretter settes inn til riktig
    //plass i opprettelse av objektet
    public Pasient stringTilPasient(String pasientInfo) {
        String[] pasientData = pasientInfo.split(",");
        if (pasientData.length == 2) {
            String pasientID = pasientData[0];
            String fnr = pasientData[1];
            Pasient nyPasient = new Pasient(pasientID, fnr);
            leggTilPasient(nyPasient);
            return nyPasient;
        } else {
            System.out.println("Ugyldig format satt inn");
            ugyldigeInstanser++;
            return null;
        }
    }

    //metode som henter lenkelisten legemidler
    public Lenkeliste<Legemiddel> hentLegemidler() {
        return legemidler;
    }

    //metode som legger til legemiddel i lenkelisten legemidler
    public void leggTilLegemiddel(Legemiddel nyttLegemiddel) {
        legemidler.leggTil(nyttLegemiddel);
    }

    //metode for å gjøre om en string med informasjon om legemiddel til objektet legemiddel
    //Stringen må skrives i riktig format og skiller informasjonen i et array, som deretter settes inn til riktig
    //plass i opprettelse av objektet
    public Legemiddel stringTilLegemiddel(String legemiddelInfo) {
        String[] legemiddelData = legemiddelInfo.split(",");

        //siden innlesingseksempelet som skal brukes har noe flere kommaer enn det skal sjekker vi om
        //Det er flere kommaer før type blir funnet. Hvis det er flere kommaer før type, slås de sammen med navn.
        // Sjekker om typen er på indeks 1
        if (!legemiddelData[1].equals("vanlig") && !legemiddelData[1].equals("vanedannende") &&
                !legemiddelData[1].equals("narkotisk")) {
            int index = 0;

            for (String string : legemiddelData) {
                // Finner ut hvilken indeks typen kommer på
                if (!string.equals("vanlig") && !string.equals("vanedannende") &&
                        !string.equals("narkotisk")) {
                    index++;
                } else {
                    break;
                }
            }

            String nyttNavn = legemiddelData[0];

            // Slår sammen alle indeksene som inneholder en del av navnet før indeksen til typen
            for (int i = 1; i < index; i++) {
                nyttNavn += "," + legemiddelData[i];
            }

            String[] gammelData = legemiddelData;

            // Oppretter et nytt String-array med riktige indekser
            legemiddelData = new String[5];

            legemiddelData[0] = nyttNavn;

            // Legger til dataene etter navn på riktig indeks
            for (int i = 0; i < gammelData.length - index; i++) {
                legemiddelData[i + 1] = gammelData[index + i];
            }
        }
        //gjør om informasjonen til type double
        double pris = Double.parseDouble(legemiddelData[2]);
        double virkestoff = Double.parseDouble(legemiddelData[3]);
        //sjekker om legemiddelet er enten narkotisk, vanedannende eller vanlig
        if (legemiddelData[1].contains("narkotisk")) {
            //konverterer verdiene
            String legeNavn = legemiddelData[0];
            int styrke = Integer.parseInt(legemiddelData[4]);
            Narkotisk narkotisk = new Narkotisk(legeNavn, pris, virkestoff, styrke);
            leggTilLegemiddel(narkotisk);
            totNarkotiskeResepter++;
            return narkotisk;
        }
        if (legemiddelData[1].contains("vanedannende")) {
            //konverterer verdiene
            String legeNavn = legemiddelData[0];
            int styrke = Integer.parseInt(legemiddelData[4]);
            Vanedannende vanedannende = new Vanedannende(legeNavn, pris, virkestoff, styrke);
            leggTilLegemiddel(vanedannende);
            totVanedannendeResepter++;
            return vanedannende;
        }
        if (legemiddelData[1].contains("vanlig")) {
            //konverterer verdiene
            String legeNavn = legemiddelData[0];
            Vanlig vanlig = new Vanlig(legeNavn, pris, virkestoff);
            leggTilLegemiddel(vanlig);
            return vanlig;
        }
        System.out.println("Satt inn ugyldig type legemiddel" + legemiddelData[1]);
        ugyldigeInstanser++;
        return null;

    }

    //metode som henter lenkelisten leger
    public Lenkeliste<Lege> hentLeger() {
        return leger;
    }

    //metode som legger til lege ovjekt i listen leger
    public void leggTilLege(Lege nyLege) {
        leger.leggTil(nyLege);
    }

    //metode for å gjøre om en string med informasjon om lege til objektet lege
    //Stringen må skrives i riktig format og skiller informasjonen i et array, som deretter settes inn til riktig
    //plass i opprettelse av objektet
    public Lege stringTilLege(String legeInfo) {
        String[] legeData = legeInfo.split(",");
        String nyLegeNavn = legeData[0];
        int kontrollID = Integer.parseInt(legeData[1]);

        //sjekker om det er lege (lege har ikke kontroll id, altså 0)
        if (kontrollID == 0) {
            Lege lege = new Lege(nyLegeNavn);
            leggTilLege(lege);
            return lege;
        }
        //sjekker om det er spesialist (Er spesialist hvis kontrollid er mer enn 0)
        if (kontrollID > 0) {
            Spesialist spesialist = new Spesialist(nyLegeNavn, kontrollID);
            leggTilSpesialist(spesialist);
            return spesialist;
        }
        System.out.println("Ugyldig kontrollID: " + kontrollID);
        ugyldigeInstanser++;
        return null;
    }

    //metode for å hente lenkeliste med spesialister
    public Lenkeliste<Spesialist> hentSpesialister() {
        return spesialister;
    }

    //metode for å legge til ny spesialist i lenkelisten spesialister
    public void leggTilSpesialist(Spesialist nySpesialist) {
        spesialister.leggTil(nySpesialist);
    }

    //metode for å gjøre om en string med informasjon om resept til objektet resept
    //Stringen må skrives i riktig format og skiller informasjonen i et array, som deretter settes inn til riktig
    //plass i opprettelse av objektet
    //resept skal legges til under legens utskrevede resepter (som gjøres automatisk i opprettelse av resept)
    public Resept stringTilResept(String reseptInfo) {

        // det er et "loophole" i formateringen av innlesingsfilen
        // En lege med samme navn en spesialist vil kunne skrive sette inn
        // en blå resept uten å måtte oppgi kontrollID, for det er ingen kontrollID
        // i opprettelsen av resept instanser

        String[] reseptData = reseptInfo.split(",");

        //konverterer verdiene til riktig type ut ifra strengen med informasjon
        int legemiddelID = Integer.parseInt(reseptData[0]);
        String legeNavn = reseptData[1];
        int pasID = Integer.parseInt(reseptData[2]);
        String reseptType = reseptData[3];
        int reit = 0;
        if (reseptData.length == 5) {
            reit = Integer.parseInt(reseptData[4]);
        }

        // Finner legemiddelinstansen
        Legemiddel legemiddel = null;
        //går igjennom listen med legemidler, og sjekker det opp mot id informasjonen vi har, hvis funn lagres legemiddel i variabel
        for (Legemiddel finnLegemiddel : hentLegemidler()) {
            if (finnLegemiddel.hentID() == legemiddelID) {
                legemiddel = finnLegemiddel;
            }
        }
        if (legemiddel == null) {
            ugyldigeInstanser++;
            System.out.println("Fant ikke legemiddel med ID: " + legemiddelID);
            return null;
        }

        //variabel for å sjekke senere om vi har en spesialist eller ikke
        boolean erSpesialist = false;
        Spesialist spesialist = null;
        //går igjennom listen med spesialister, hvis navnet finnes i listen, lagres spesialisten i variabel
        for (Spesialist finnSpesialist : hentSpesialister()) {
            if (finnSpesialist.hentNavn().contains(legeNavn)) {
                // Instans forberholdt spesialister
                spesialist = finnSpesialist;
                erSpesialist = true;
            }
        }

        Lege lege = null;
        //hvis vi ikke har funnet en spesialist leter vi etter en lege
        if (!erSpesialist) {
            for (Lege finnLege : hentLeger()) {
                if (finnLege.hentNavn().contains(legeNavn)) {
                    lege = finnLege;
                }
            }
        }
        //hvis verken lege eller spesialist ble funnet:
        if (lege == null && !erSpesialist) {
            ugyldigeInstanser++;
            System.out.println("Fant ikke legen med navn: " + legeNavn);
            return null;
        }

        // Finner nyPasient fra id
        Pasient pasient = null;
        for (Pasient finnPasient : hentPasienter()) {
            if (finnPasient.hentID() == pasID) {
                pasient = finnPasient;
            }
        }
        if (pasient == null) {
            ugyldigeInstanser++;
            System.out.println("Fant ikke pasient med ID: " + pasID);
            return null;
        }

        // Finner type resept og oppretter en instans da all informasjon er funnet
        //Det må først sjekkes om det er en lege eller spesialist som skriver ut resepten
        Resept nyResept = null;
        if (erSpesialist) {
            if (reseptType.contains("hvit")) {
                nyResept = new HvitResept(legemiddel, spesialist, pasient, reit);
            } else if (reseptType.contains("militaer")) {
                nyResept = new MilitaerResept(legemiddel, spesialist, pasient, reit);
            } else if (reseptType.contains("p")) {
                nyResept = new PResept(legemiddel, spesialist, pasient);
            } else if (reseptType.contains("blaa")) {
                nyResept = new BlaaResept(legemiddel, spesialist, pasient, reit);
            } else {
                ugyldigeInstanser++;
                System.out.println("Ugyldig type resept: " + reseptType);
                return null;
            }
        } else if (!erSpesialist) {
            if (legemiddel instanceof Narkotisk) {
                ugyldigeInstanser++;
                System.out.println("Denne legen har ikke lov til å skrive ut resept med narkotisk legemiddel" + lege);
                return null;
            }
            if (reseptType.contains("hvit")) {
                //legger til automatisk når man oppretter resept
                nyResept = new HvitResept(legemiddel, lege, pasient, reit);
            } else if (reseptType.contains("militaer")) {
                nyResept = new MilitaerResept(legemiddel, lege, pasient, reit);
            } else if (reseptType.contains("p")) {
                nyResept = new PResept(legemiddel, lege, pasient);
            } else if (reseptType.contains("blaa")) {
                nyResept = new BlaaResept(legemiddel, lege, pasient, reit);
            } else {
                System.out.println("Ugyldig type resept: " + reseptType);
                ugyldigeInstanser++;
                return null;
            }
        }
        return nyResept;
    }

    //printer oversikt (Oppgave E3)
    public void printOversikt() {
        System.out.println("=== Oversikt over legesystemet ===");
        printPasienter();
        System.out.println();
        printLegemidler();
        System.out.println();
        printLeger();
        System.out.println();
        printSpesialister();
        System.out.println();
    }

    //4 metoder som printer informasjon fra de fire lenkelistene:
    public void printPasienter() {
        System.out.println("Alle pasienter:");
        for (Pasient pasient : pasienter) {
            System.out.println("Pasient " + pasient.hentNavn() + " har id " + pasient.hentID() + " og fødselsnummer " + pasient.hentFoedselsnummer());
        }
    }

    public void printLegemidler() {
        System.out.println("Alle legemidler:");
        for (Legemiddel legemiddel : legemidler) {
            System.out.println(legemiddel.hentNavn() + " med id " + legemiddel.hentID() + " koster " + legemiddel.hentPris() + " og har " + legemiddel.hentVirkestoff() + " virkestoff");
        }

    }

    public void printLeger() {
        System.out.println("Alle leger:");
        for (Lege lege : leger) {
            System.out.println(lege.hentNavn() + " har skrevet ut følgende resepter");
            for (Resept resept : lege.hentUtskrevedeResepter()) {
                System.out.println(" - " + resept.getClass().getName() +
                        "resept med " + resept.hentLegemiddel().hentNavn() +
                        " og ID " + resept.hentReseptID() +
                        ", koster " + resept.prisAaBetale() +
                        " og har " + resept.hentReit() +
                        " reit igjen. Skrevet ut til " + resept.hentPasient().hentNavn());
            }
        }
    }

    public void printSpesialister() {
        System.out.println("Alle spesialister:");
        for (Spesialist spesialist : spesialister) {
            System.out.println(spesialist.hentNavn() + " har skrevet ut følgende resepter");
            for (Resept resept : spesialist.hentUtskrevedeResepter()) {
                System.out.println(" - " + resept.getClass().getName() +
                        "resept med " + resept.hentLegemiddel().hentNavn() +
                        " og ID " + resept.hentReseptID() +
                        ", koster " + resept.prisAaBetale() +
                        " og har " + resept.hentReit() +
                        " reit igjen. Skrevet ut til " + resept.hentPasient().hentNavn());
            }
        }
    }

    //metode som tar imot fil og lagrer informasjonen fra filen som objekt i lenkelistene
    Legesystem(String filnavn) {
        try {
            File fil = new File(filnavn);
            Scanner scanner = new Scanner(fil);

            int type = 0;
            while (scanner.hasNextLine()) {
                String linje = scanner.nextLine();
                if (linje.contains("#")) {
                    type++;
                    linje = scanner.nextLine();
                } else {
                    forsoekedeInstanser++;
                }
                switch (type) {
                    case 1:
                        // lager nyPasient insanser
                        stringTilPasient(linje);
                        break;

                    case 2:
                        // Lager Legemidler instanser
                        stringTilLegemiddel(linje);
                        break;

                    case 3:
                        // Lager lege instanser
                        stringTilLege(linje);
                        break;

                    case 4:
                        // Lager Resept instanser
                        stringTilResept(linje);
                        break;
                }
            }
            //gir informasjon om hva metoden har gjort
            System.out.println();
            System.out.println("============================================");
            System.out.println("Hentet data fra filen [\'" + filnavn + "']");
            System.out.println("Antall instanser prøvd å opprette: " + forsoekedeInstanser);
            System.out.println("Antall ugyldige instanser funnet: " + ugyldigeInstanser);
            System.out.println("Antall instanser opprettet: " + gyldigeInstanser());
            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        instanserLagretIFil = gyldigeInstanser();
    }



    //metode for å legge til nye instanser fra input
    public void leggTilInstans() {

        Scanner scanner = new Scanner(System.in);

        boolean avslutt = false;
        int type = 0;

        while (!avslutt) {
            printValg();
            Scanner info = new Scanner(System.in);

            if (scanner.hasNextInt()) {
                type = scanner.nextInt();

                switch (type) {

                    //forskjellige caser basert på om det er pasient, legemiddel, lege eller resept som skal legges til
                    case 0:
                        avslutt = true;
                        break;
                    case 1:
                        // Pasient
                        System.out.println("Skriv inn informasjon om pasienten på følgende format:");
                        System.out.println("navn,fnr");
                        System.out.println("Eksempel: Jens Hans Olsen,11111143521");
                        System.out.println("La til pasient: " + stringTilPasient(info.nextLine()));
                        break;
                    case 2:
                        // Legemiddel
                        System.out.println("Skriv inn informasjon om legemiddelet på følgende format:");
                        System.out.println("navn,type,pris,virkestoff,[styrke]");
                        System.out.println("Eksempel: Predizol,narkotisk,450,75,8");
                        System.out.println("La til legemiddel: " + stringTilLegemiddel(info.nextLine()));
                        break;
                    case 3:
                        // Lege
                        System.out.println("Skriv inn informasjon om legen på følgende format:");
                        System.out.println("navn,kontrollid / 0 hvis vanlig lege");
                        System.out.println("Eksempel: Dr. House,12345");
                        System.out.println("La til pasient: " + stringTilLege(info.nextLine()));
                        break;
                    case 4:
                        // Resept
                        System.out.println("Skriv inn informasjon om resepten på følgende format:");
                        String reseptString = "";
                        String reseptType = "";

                        System.out.println("Resepttype: ");
                        System.out.println("[1] Hvit");
                        System.out.println("[2] Militær");
                        System.out.println("[3] P-resept");
                        System.out.println("[4] Blå");
                        int reseptValg = Integer.parseInt(info.nextLine());
                        switch (reseptValg) {
                            case 1:
                                reseptType = "hvit";
                                break;
                            case 2:
                                reseptType = "militaer";
                                break;
                            case 3:
                                reseptType = "p";
                                break;
                            case 4:
                                reseptType = "blaa";
                                break;
                            default:
                                System.out.println("Vennligst sett inn et tall mellom 1-4 for å velge resepttype");
                                return;
                        }

                        System.out.println("Legemiddelnummer: ");
                        reseptString += info.nextLine() + ",";

                        System.out.println("Legenavn: ");
                        reseptString += info.nextLine() + ",";

                        System.out.println("PasientID: ");
                        reseptString += info.nextLine() + ",";

                        reseptString += reseptType + ",";

                        if (!reseptType.equals("p")) {
                            System.out.println("Reit: ");
                            reseptString += info.nextLine();
                        }


                        //System.out.println("legemiddelNummer,legeNavn,pasientID,type,[reit]");
                        //System.out.println("Eksempel: 1,Dr. Cox,2,hvit,7");

                        System.out.println(reseptString);
                        if (stringTilResept(reseptString) != null) {
                            System.out.println("La til resept: " + stringTilResept(reseptString));
                            break;
                        } else {
                            System.out.println("Ingen resept lagt til.");
                            break;
                        }
                    default:
                        System.out.println("Tallet du satt inn var ikke mellom 0-5");
                }
            } else {
                System.out.println("Vennligst sett inn et tall mellom 0-5");
            }
            scanner.nextLine();
        }
    }

    //Menyen for legesystem, printer ut valgmulighetene brukeren har
    private void printValg() {
        System.out.println();
        System.out.println("Sett inn tallet for hva du ønsker å legge til i legesystemet:");
        System.out.println("[1] Legg til pasient");
        System.out.println("[2] Legg til legemiddel");
        System.out.println("[3] Legg til lege");
        System.out.println("[4] Legg til resept");
        System.out.println("[0] Tilbake til hovedmenyen");
    }

    //metode for å bruke en resept (-1 reit)
    public void brukResept() {

        int pasientNr = 0;
        int reseptNr = 0;
        Pasient valgtPasient = null;
        Resept gjeldendeResept = null;
        //Lenkeliste<Resept> funnetResepter = new Lenkeliste<>();

        for (Pasient pasient : hentPasienter()){
            pasientNr++;
            System.out.println(pasientNr + ": " + pasient.hentNavn() + "(fnr: " + pasient.hentFoedselsnummer() + ")");
        }
        System.out.println("Hvilken pasient vil du se resepter for?");

        Scanner input = new Scanner(System.in);
        if (!input.hasNextInt()) {
            System.out.println("Vennligst sett inn et tall");
            return;
        }
        int valg = input.nextInt();

        if (valg < 1 || valg > hentPasienter().stoerrelse()) {
            System.out.println("Fant ikke pasient med nummer: " + valg);
            return;
        }
        valgtPasient = hentPasienter().hent(valg-1);

        if (valgtPasient.hentResepter().stoerrelse() == 0) {
            System.out.println("Beklager, det ble ikke funnet noen resepter tilhørende pasient: " + valgtPasient);
            return;
        }
        System.out.println("Her er reseptene til pasient: " + valgtPasient);
        System.out.println("Hvilken resept vil du bruke: ");
        for (Resept resept : valgtPasient.hentResepter()){
            reseptNr++;
            System.out.println(reseptNr + ": " + resept.hentLegemiddel().hentNavn() + "(Reit: " + resept.hentReit() +  ")");
        }
        valg = input.nextInt();

        if (valg < 1 || valg > valgtPasient.hentResepter().stoerrelse()) {
            System.out.println("Fant ikke resept med nummer: " + valg);
            return;
        }

        gjeldendeResept = valgtPasient.hentResepter().hent(valg-1);

        if (gjeldendeResept.bruk()) {
            System.out.println("Du brukte resepten: " + gjeldendeResept);
            System.out.println("Resepten har " + gjeldendeResept.hentReit() + " reit igjen.");
        } else {
            System.out.println("Resepten har ingen flere reit igjen, og kan ikke lenger brukes.");
        }
    }

    public void visStatistikk() {
        System.out.println("Hva ønsker du å se statistikk for?");
        System.out.println("[1] Totalt antall utskrevne resepter på vanedannende legemidler");
        System.out.println("[2] Totalt antall utskrevne resepter på narkotiske legemidler");
        System.out.println("[3] Statistikk om mulig misbruk av narkotika");

        Scanner input = new Scanner(System.in);
        if (!input.hasNextInt()) {
            System.out.println("Vennligst sett inn et tall");
            return;
        }
        int valg = input.nextInt();

        switch (valg) {
            case 1:
                System.out.println("Antall resepter med vanedannede legemidler: " + totVanedannendeResepter);
                break;
            case 2:
                System.out.println("Antall resepter med narkotiske legemidler: " + totNarkotiskeResepter);
                break;
            case 3:
                System.out.println("Alle leger som har skrevet ut minst én resept på narkotiske legemidler:");
                for (Spesialist spesialist : hentSpesialister()) {
                    int antNarkotiskeResepter = 0;
                    for (Resept resept: spesialist.hentUtskrevedeResepter()) {
                        if (resept.hentLegemiddel() instanceof Narkotisk) {
                            antNarkotiskeResepter++;
                        }
                    }
                    if (antNarkotiskeResepter != 0) {
                        System.out.println(spesialist.hentNavn() + " har skrevet ut " + antNarkotiskeResepter);
                    }
                }
                System.out.println();
                System.out.println("Alle pasienter som har minst én gyldig resept på narkotiske legemidler:");
                for (Pasient pasient : hentPasienter()) {
                    int antNarkotiskeResepter = 0;
                    for (Resept resept : pasient.hentResepter()) {
                        if (resept.hentLegemiddel() instanceof Narkotisk && resept.hentReit() > 0) {
                            antNarkotiskeResepter++;
                        }
                    }
                    if (antNarkotiskeResepter != 0) {
                        System.out.println(pasient.hentNavn() + " har " + antNarkotiskeResepter + " gyldige narkotiske resepter");
                    }
                }
                break;
            default:
                System.out.println("Tallet du satt inn var ikke mellom 0-5");
                break;
        }
    }

    public void skrivTilNyFil() {
        try {
            String filnavn = "src/oppdatert_legesystem.txt";
            File fil = new File(filnavn);
            if (!fil.exists()) {
                File nyFil = new File(filnavn);
            }
            PrintWriter skrivLegesystem = new PrintWriter(filnavn);

            skrivLegesystem.println("# Pasienter (navn, fnr)");
            for (Pasient pasient : hentPasienter()) {
                skrivLegesystem.println(
                    pasient.hentNavn() + "," +
                    pasient.hentFoedselsnummer()
                );
            }

            skrivLegesystem.println("# Legemidler (navn,type,pris,virkestoff,[styrke])");
            for (Legemiddel legemiddel : hentLegemidler()) {
                skrivLegesystem.print(
                    legemiddel.hentNavn() + "," +
                    legemiddel.hentType() + "," +
                    legemiddel.hentPris() + "," +
                    legemiddel.hentVirkestoff() + ","
                );
                if (legemiddel instanceof Narkotisk) {
                    skrivLegesystem.print(((Narkotisk) legemiddel).hentStyrke());
                } else if (legemiddel instanceof Vanedannende) {
                    skrivLegesystem.print(((Vanedannende) legemiddel).hentStyrke());
                }
                skrivLegesystem.println();
            }

            skrivLegesystem.println("# Leger (navn,kontrollid / 0 hvis vanlig lege)");
            for (Lege lege : hentLeger()) {
                skrivLegesystem.println(lege.hentNavn() + ",0");
            }
            for (Spesialist spesialist : hentSpesialister()) {
                skrivLegesystem.println(spesialist.hentNavn() + "," + spesialist.hentKontrollID());
            }

            skrivLegesystem.println("# Resepter (legemiddelNummer,legeNavn,pasientID,type,[reit])");
            for (Lege lege : hentLeger()) {
                for (Resept resept : lege.hentUtskrevedeResepter()) {
                    skrivLegesystem.print(
                        resept.hentReseptID() + "," +
                        resept.hentUtskrivendeLege().hentNavn() + "," +
                        resept.hentPasient().hentID() + "," +
                        resept.hentType() + ","
                    );
                    if (resept instanceof PResept) {
                        skrivLegesystem.println();
                    } else {
                        skrivLegesystem.println(resept.hentReit());
                    }
                }
            }
            for (Spesialist spesialist : hentSpesialister()) {
                for (Resept resept : spesialist.hentUtskrevedeResepter()) {
                    skrivLegesystem.print(
                            resept.hentReseptID() + "," +
                                    resept.hentUtskrivendeLege().hentNavn() + "," +
                                    resept.hentPasient().hentID() + "," +
                                    resept.hentType() + ","
                    );
                    if (resept instanceof PResept) {
                        skrivLegesystem.println();
                    } else {
                        skrivLegesystem.println(resept.hentReit());
                    }
                }
            }
            skrivLegesystem.close();
            int nyeLagredeInstanser = gyldigeInstanser() - instanserLagretIFil;
            instanserLagretIFil = gyldigeInstanser();

            int linjerSkrevet = 0;
            Scanner scanner = new Scanner(fil);
            while (scanner.hasNextLine()) {
                linjerSkrevet++;
                scanner.nextLine();
            }


            System.out.println(linjerSkrevet + " linjer skrevet til [" + filnavn + "]");
            System.out.println(nyeLagredeInstanser + " nye instanser lagret i oppdatert fil");

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
