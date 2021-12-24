import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

class Labyrint {
    //private Felt[][] brett;
    private Rute[][] ruteArray;
    private int antallKolonner;
    private int antallRader;
    private Liste<String> utveier;
    public LabyrintGUI labyrintgui;

            // konstruktoren skal vaere private
    private Labyrint(Rute[][] ruteArray, int antallKolonner, int antallRader) {
        this.ruteArray = ruteArray;
        this.antallKolonner = antallKolonner;
        this.antallRader = antallRader;
    }

    // Lager labyrinten i en dobbel array med antall rader og antall kolonner
    public static Labyrint lagLabyrint(int antKolonner, int antRader) {
        // Rute[][] ruteArray = new Rute[antRader][antKolonner];
        Rute[][] ruteArray = new Rute[antKolonner][antRader];
    
        Labyrint l = new Labyrint(ruteArray, antKolonner, antRader);
        return l;
    }

        /**
     * Konverterer losning-String fra oblig 5 til en boolean[][]-representasjon
     * av losningstien.
     * @param losningString String-representasjon av utveien
     * @param bredde        bredde til labyrinten
     * @param hoyde         hoyde til labyrinten
     * @return              2D-representasjon av rutene der true indikerer at
     *                      ruten er en del av utveien.
     */
    static boolean[][] losningStringTilTabell(String losningString, int bredde, int hoyde) {
        boolean[][] losning = new boolean[hoyde][bredde];
        java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\(([0-9]+),([0-9]+)\\)");
        java.util.regex.Matcher m = p.matcher(losningString.replaceAll("\\s",""));
        while (m.find()) {
            int x = Integer.parseInt(m.group(1) );
            int y = Integer.parseInt(m.group(2));
            losning[y][x] = true;
        }
        return losning;
    }


    public String toString() {
        String s = "";

        for (int i = 0; i < antallRader; i++) {
            for (int j = 0; j < antallKolonner; j++) {
                s += ruteArray[j][i];
            }
            s += '\n';
        }
        return s;
    }

    //STATISK: tilhorer klassen, ikke objektene
    /**
     * 1. Lese inn alle linjene fra filen og opprette Ruter basert på fildata (du kan anta at input-filen er gyldig).
     * 2. Opprette selve Labyrint-objektet på bakgrunn av data fra filen.
     * 3. Returnerer Labyrint-objektet med all datastruktur ferdig oppsatt.
     */
    public static Labyrint lesFraFil(File fil) throws FileNotFoundException {

        Scanner scanneren;
        scanneren = new Scanner(fil);
        int antallRader = scanneren.nextInt();
        int antallKolonner = scanneren.nextInt();
        Labyrint l = lagLabyrint(antallKolonner, antallRader);

        // Gaar forbi 0D og 0A
        scanneren.nextLine();
        int tmpRad = 0;
        int tmpKolonne = 0;
        while (scanneren.hasNextLine()) {
            String symbol = scanneren.nextLine();
            String symboler[] = symbol.split("");
            for (String tegn : symboler) {
                // sjekker om to stringer er like
                if (tegn.equals(".")) {
                    //maa skrive l sin erAapning, fordi metoden er statisk
                    if (l.erAapning(tmpKolonne, tmpRad)) {
                        l.ruteArray[tmpKolonne][tmpRad] = new Aapning(tmpKolonne, tmpRad, l);
                    } else {
                        // Ikke aapning
                        l.ruteArray[tmpKolonne][tmpRad] = new HvitRute(tmpKolonne, tmpRad, l);
                    }
                } else if (tegn.equals("#")) {
                    l.ruteArray[tmpKolonne][tmpRad] = new SortRute(tmpKolonne, tmpRad, l);
                }
                tmpKolonne++;
            }
            tmpKolonne = 0;
            tmpRad++;
        }
        for (Rute[] kolonne : l.ruteArray) {
            for (Rute rute : kolonne) {
                rute.settNaboruter();
            }
        }
        return l;
    }

    public boolean erAapning(int kolonne, int rad) {
        return (rad == 0 || kolonne == 0 || kolonne == antallKolonner - 1 || rad == antallRader - 1);
    }

    public ArrayList<Rute> finnNaboruter(int kolonne, int rad) {

        ArrayList<Rute> naboer = new ArrayList<Rute>();

        // sjekke om ikke til venstre
        if (kolonne == 0) {
        } else {
            naboer.add(ruteArray[kolonne - 1][rad]);
        }
        //sjekke om ikke til hoyre
        if (kolonne == antallKolonner - 1) {
        } else {
            naboer.add(ruteArray[kolonne + 1][rad]);
        }
        // sjekke om ikke nederst
        if (rad == antallRader - 1) {
        } else {
            naboer.add(ruteArray[kolonne][rad + 1]);
        }
        // sjekke om ikke overst
        if (rad == 0) {
        } else {
            naboer.add(ruteArray[kolonne][rad - 1]);
        }
        return naboer;
    }
    

    public static void main(String[] args) throws FileNotFoundException {
        String filen = "2.in";
        File fil = new File(filen);
        // Designmonster, objektet blir laget i LesFraFil metoden.
        // Dette kalles en factorymethod
        Labyrint l = Labyrint.lesFraFil(fil);
        System.out.println(l.toString());


        // GUI 
        // launch(args);
    }

    // Hver gang starte aa finne utvei/utveier fra kolonne og rad, skal man starte med aa
    // lage en ny lenkeliste med utvei/utveiene.
    public Liste<String> finnUtveiFra(int kol, int rad) {
        utveier = new Lenkeliste<String>();
        Rute derViSkalBegynne = ruteArray[kol][rad];
        derViSkalBegynne.finnUtvei();
        return utveier;
    }

    public Liste<String> hentUtveier() {
        return utveier;
    }

    public Rute[][] hentRuteArray() {
        return ruteArray;
    }

    public int hentAntallRader() {
        return antallRader;
    }

    public int hentAntallKolonner() {
        return antallKolonner;
    }

    public void settGui(LabyrintGUI labyrintgui) {
        this.labyrintgui = labyrintgui;
    }
}




