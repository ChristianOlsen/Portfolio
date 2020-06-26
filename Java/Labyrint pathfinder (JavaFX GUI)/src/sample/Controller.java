package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    public Button filKnapp;
    @FXML
    public GridPane grid;
    @FXML
    public ComboBox<String> utveivelger;
    private Labyrint labyrint;
    private List<Button> veiruter = new ArrayList<>();
    private Rute finnVeiFra;
    private List<List<Rute>> utveier;
    private int utveivalg = 0;

    @FXML
    public void velgFil(ActionEvent mouseEvent) {
        FileChooser filvelger = new FileChooser();
        FileChooser.ExtensionFilter filtype = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt", "IN files (*.in)", "*.in");
        filvelger.getExtensionFilters().add(filtype);
        filvelger.setInitialDirectory(new File("src/sample"));
        File valgtFil = filvelger.showOpenDialog(null);
        if (valgtFil != null) {
            File filvalg = new File(valgtFil.getAbsolutePath());
            grid.getChildren().clear();
            opprettLabyrint(filvalg);
        } else {
            System.out.println("Vennligst velg en .in-fil");
        }
    }

    private void opprettLabyrint(File fil) {
        Labyrint l = null;
        try {
            l = Labyrint.lesFraFil(fil);
        } catch (FileNotFoundException e) {
            System.out.printf("FEIL: Kunne ikke lese fra '%s'\n", fil);
            System.exit(1);
        }
        labyrint = l;
        utveivelger.getItems().clear();
        tegnLabyrint();
    }

    @FXML
    private void tegnLabyrint() {
        for (int x=0; x<labyrint.bredde(); x++) {
            for (int y=0; y<labyrint.hoyde(); y++) {
                Rute rute = labyrint.hentRute(x, y);
                Button ruteKnapp = new Button();
                ruteKnapp.setPrefSize(40, 40);
                ruteKnapp.setMinSize(5, 5);
                if (rute.erVei()) {
                    ruteKnapp.setOnAction(e -> {
                        utveier = rute.hentLabyrint().finnUtveiFra(rute.x(),rute.y());
                        if (utveier != null && utveier.size() != 0) {
                            resetGrid();
                            visUtvei(utveivalg);
                            opprettUtveiValg();
                        } else {
                            System.out.println("Fant ingen utveier fra " + rute);
                        }
                    });

                    ruteKnapp.setStyle("-fx-background-color: "+rute.traadFarge());
                    rute.settKnapp(ruteKnapp);
                    veiruter.add(ruteKnapp);
                } else {
                    ruteKnapp.setStyle("-fx-background-color: black");
                }
                grid.add(ruteKnapp, x, y);
            }
        }
    }

    private void opprettUtveiValg() {
        utveivelger.getItems().clear();
        int antUtveier = 0;
        for (List<Rute> utvei : utveier) {
            antUtveier++;
            utveivelger.getItems().add(antUtveier + ". Utvei i " + utvei.get(utvei.size()-1) + ": " + (utvei.size()-1) + " steg");
        }
        utveivelger.setValue("1. Utvei i " + utveier.get(0).get(utveier.get(0).size()-1) + ": " + (utveier.get(0).size()-1) + " steg");
        utveivelger.setOnAction( e -> {
            if (utveivelger.getItems().size() > 0) {
                visUtvei(Character.getNumericValue(utveivelger.getValue().charAt(0))-1);
            }
        });

    }

    private void visUtvei(int utveiValg) {
        resetGrid();
        List<Rute> utvei = utveier.get(utveiValg);

        for (Rute rute : utvei) {
            rute.hentKnapp().setStyle("-fx-background-color: red");
        }
    }

    private void resetGrid() {
        if (veiruter != null) {
            for (Button veirute : veiruter) {
                veirute.setStyle("-fx-background-color: grey");
            }
        }
    }
}
