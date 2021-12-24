import java.io.File;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.text.Text;

/**
 * LabyrintGUI
 */
public class LabyrintGUI extends Application {
    public int antallLosninger = 0;
    Text antallLosningText;
    Rute[][] ruteArray;
    Labyrint labyrint;
    GridPane guiRuteNett;

    @Override
    public void start(Stage teater) throws Exception {
        teater.setTitle("YOlo");
        labyrint = Labyrint.lesFraFil(new File("2.in"));
        labyrint.settGui(this);
        ruteArray = labyrint.hentRuteArray();

        guiRuteNett = new GridPane();
        tegnBrett();
        GridPane alt = new GridPane();
        alt.add(guiRuteNett, 0, 0);
        antallLosningText = new Text("Antall losninger: " + antallLosninger);
        GridPane pilerogtekst = new GridPane();
        Text gjeldendelosning = new Text("Gjeldende losning: ");
        pilerogtekst.add(antallLosningText, 0, 2);
        pilerogtekst.add(gjeldendelosning, 0, 3);
        alt.add(pilerogtekst, 0, 1);
        // Scenen skal bestå av guiRutenett
        // Pane kulisser = new Pane();
        // kulisser.getChildren().add(guiRuteNett);
        
        // Teateret skal bruke scenen over
        Scene scene = new Scene(alt);
        teater.setScene(scene);
        // Viser teateret
        teater.show();
        antallLosningText.setText("Yolo");
    }

	private void tegnBrett() {
		for (int i = 0; i < ruteArray.length; i++) {
            for (int j = 0; j < ruteArray[i].length; j++) {
                if (ruteArray[i][j].toString().equals(".")) {
                    // En bakgrunn i GUI kan ha en fyll. Rute extends Button, som har Region, som har en setBackground
                    ruteArray[i][j].setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
                } else {
                    ruteArray[i][j].setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
                }
                // Her legges button pÃ¥ Gridpane
                guiRuteNett.add(ruteArray[i][j], i, j);
            }
        }
	}

    public void settAntallLosninger(int antallLosninger) {
        this.antallLosninger = antallLosninger;
        antallLosningText.setText("Antall losninger: " + antallLosninger);
    }

    public void tegnUtvei(Liste<String> utveier) {
        settAntallLosninger(utveier.stoerrelse());
        Scanner tastatur = new Scanner(System.in);
        System.out.println("Antall losninger: " + utveier.stoerrelse());
        System.out.print("Utvei aa vise:");
        int valg = Integer.parseInt(tastatur.nextLine());
        // for(String utvei : utveier) {
            String utvei = utveier.hent(valg);
            // Bruker metoden som vi har fatt i oppgaven
            boolean[][] losningArray = Labyrint.losningStringTilTabell(utvei, labyrint.hentAntallKolonner(), labyrint.hentAntallRader());
            for (int i = 0; i < losningArray.length; i++) {
                for (int j = 0; j < losningArray[i].length; j++) {
                    if (losningArray[i][j] == true){
                        System.out.println("i: " + i + " j:" + j);
                        ruteArray[j][i].setBackground(new Background(new BackgroundFill(Color.CYAN, null, null)));
                    }
                }
            }
        // }
    }

    public static void main(String[] args) {
        // Starter alt over
        launch(args);
    }
}