
class SortRute extends Rute {

    public SortRute(int kolonne, int rad, Labyrint labyrint) {
        super(kolonne, rad, labyrint);
    }

    public char tilTegn() {
        // Disse må implementere char tilTegn() som 
        // deklareres i Rute.
        return '#';
    }

    // Hvis vi kommer til ruten sin svarte rute nabo, fortsetter vi bare uten
    // aa gjore noe.
    @Override
    public void gaa(String forelopigVeiKoordinater, Rute ruteViKomFra) {
        return;
    }
}
