import java.util.ArrayList;

class HvitRute extends Rute {

    public HvitRute(int kolonne, int rad, Labyrint labyrint) {
        super(kolonne, rad, labyrint);
    }

    // char er en primitiv. 
    public char tilTegn() {
        // Disse må implementere char tilTegn() som 
        // deklareres i Rute.
        return '.';
    }

    // Gaar videre i hvite rute sin nabo. Naar vi er inne i neste rute, lagrer vi referanse
    // til forrige rute.
    @Override
    public void gaa(String forelopigVeiKoordinater, Rute rutenViKomFra) {
        gaaTilNaboRuter(leggTilKoordinateneTilDenneRuten(forelopigVeiKoordinater), rutenViKomFra);
        return;
    }

	private void gaaTilNaboRuter(String forelopigVeiKoordinater, Rute rutenViKomFra) {
		ArrayList<Rute> naboruter = hentNaboruter();
        for (Rute naborute : naboruter) {
            if (naborute == rutenViKomFra) {
                continue;
            }
            // this for aa sende koordinatene i denne ruten vi er i, ikke forrige
            naborute.gaa(forelopigVeiKoordinater, this);
        }
	}

	protected String leggTilKoordinateneTilDenneRuten(String forelopigVeiKoordinater) {
		return forelopigVeiKoordinater + " --> (" + getKolonne() + "," + getRad() + ")";
	}
}