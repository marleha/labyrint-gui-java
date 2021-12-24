
class Aapning extends HvitRute {

    public Aapning(int kolonne, int rad, Labyrint labyrint) {
        super(kolonne, rad, labyrint);
    }

    @Override
    public void gaa(String forelopigVeiKoordinater, Rute ruteViKomFra) {
        forelopigVeiKoordinater = leggTilKoordinateneTilDenneRuten(forelopigVeiKoordinater);
        Liste<String> utveiene = labyrint.hentUtveier();
        utveiene.leggTil(forelopigVeiKoordinater);
    }
}

// Når du har funnet en utvei, skal strengen med utveien legges inn i en Liste<String> (du kan
// selv velge hvilken av subklassene fra obligatorisk oppgave 4 som skal brukes). 

