package controleur.dashboard.newmeasureattribute;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;

import controleur.dashboard.InsertFailedException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modele.bdd.table.ObsBatracienTable;
import modele.donnee.EspeceBatracien;
import modele.donnee.Lieu;
import modele.donnee.MeteoCiel;
import modele.donnee.MeteoPluie;
import modele.donnee.MeteoTemp;
import modele.donnee.MeteoVent;
import modele.donnee.NatureVege;
import modele.donnee.Observateur;
import modele.donnee.Ouverture;
import modele.donnee.Pente;
import modele.donnee.TypeMare;
import utils.Utils;

public class BatracienMeasure implements IMeasure {

    @FXML
    private ComboBox<EspeceBatracien> especeComboBox;
    @FXML
    private TextField temperatureInput;
    @FXML
    private TextField nbrAdulte;
    @FXML
    private TextField nbrAmplexus;
    @FXML
    private TextField nbrPonte;
    @FXML
    private TextField nbrTetard;
    @FXML
    private ComboBox<MeteoCiel> meteoCiel;
    @FXML
    private ComboBox<MeteoTemp> meteoTemp;
    @FXML
    private ComboBox<MeteoVent> meteoVent;
    @FXML
    private ComboBox<MeteoPluie> meteoPluie;

    @FXML
    private TextField zhProfondeur;
    @FXML
    private TextField zhSurface;
    @FXML
    private ComboBox<TypeMare> zhTypeMare;
    @FXML
    private ComboBox<Pente> zhTypePente;
    @FXML
    private ComboBox<Ouverture> zhOuverture;

    @FXML
    private VBox vegetationList;

    @FXML
    private void initialize() {
        ObservableList<EspeceBatracien> especeList = FXCollections.observableArrayList(EspeceBatracien.values());
        this.especeComboBox.setItems(especeList);
        this.meteoCiel.setItems(FXCollections.observableArrayList(MeteoCiel.values()));
        this.meteoTemp.setItems(FXCollections.observableArrayList(MeteoTemp.values()));
        this.meteoVent.setItems(FXCollections.observableArrayList(MeteoVent.values()));
        this.meteoPluie.setItems(FXCollections.observableArrayList(MeteoPluie.values()));

        this.zhTypeMare.setItems(FXCollections.observableArrayList(TypeMare.values()));
        this.zhTypePente.setItems(FXCollections.observableArrayList(Pente.values()));
        this.zhOuverture.setItems(FXCollections.observableArrayList(Ouverture.values()));
        this.vegetationList.getChildren().add(this.createHBOX(""));
    }

    @Override
    public void addMesure(Date date, Time heure, Lieu lieu, ArrayList<Observateur> observateurs)
            throws InsertFailedException {
        if (this.especeComboBox.getValue() == null) {
            throw new InsertFailedException("Espece non selectionnée");
        }
        if (this.temperatureInput.getText().isEmpty()) {
            throw new InsertFailedException("Temperature non renseignée");
        }
        try {
            new ObsBatracienTable().insertObservation(date, heure, lieu, observateurs, this.especeComboBox.getValue(),
                    new int[] {
                            Integer.parseInt(!this.nbrAdulte.getText().isEmpty() ? this.nbrAdulte.getText() : "0"),
                            Integer.parseInt(!this.nbrAmplexus.getText().isEmpty() ? this.nbrAmplexus.getText() : "0"),
                            Integer.parseInt(!this.nbrPonte.getText().isEmpty() ? this.nbrPonte.getText() : "0"),
                            Integer.parseInt(!this.nbrTetard.getText().isEmpty() ? this.nbrTetard.getText() : "0")
                    }, Integer.parseInt(this.temperatureInput.getText()), meteoCiel.getValue(), meteoTemp.getValue(),
                    meteoVent.getValue(), meteoPluie.getValue(),
                    this.getVegetations(),
                    false, Integer.parseInt(!this.zhProfondeur.getText().isEmpty() ? this.zhProfondeur.getText() : "0"),
                    Integer.parseInt(!this.zhSurface.getText().isEmpty() ? this.zhSurface.getText() : "0"),
                    this.zhTypeMare.getValue(), this.zhTypePente.getValue(), this.zhOuverture.getValue());
        } catch (NumberFormatException e) {
            throw new InsertFailedException("Vérifiez vos entrées");
        }
    }

    public ArrayList<Pair<String, NatureVege>> getVegetations() {
        ArrayList<Pair<String, NatureVege>> vegetations = new ArrayList<>();
        for (int i = 0; i < this.vegetationList.getChildren().size(); i++) {
            HBox hbox = (HBox) this.vegetationList.getChildren().get(i);
            if (hbox.getChildren().size() == 2) {
                TextField textField = (TextField) hbox.getChildren().get(0);
                ComboBox<NatureVege> comboBox = (ComboBox<NatureVege>) hbox.getChildren().get(1);
                if (!textField.getText().isEmpty() && comboBox.getValue() != null) {
                    vegetations.add(new Pair<>(textField.getText(), comboBox.getValue()));
                }
            }
        }
        return vegetations;
    }

    public void addAVegetation() {
        HBox hbox = (HBox) this.vegetationList.getChildren().get(this.vegetationList.getChildren().size() - 1);
        Button button = (Button) hbox.getChildren().get(2);
        button.setText("X");
        button.getStyleClass().add("btn-danger");
        button.setOnAction(e -> {
            this.vegetationList.getChildren().remove(hbox);
        });
        this.vegetationList.getChildren().add(this.createHBOX(""));
    }

    public Parent createHBOX(String stringConcerned) {
        String path = "/fxml/dashboard/new-measure-vegetation.fxml";
        HBox box = (HBox) Utils.loadFXMLWithController(this, path, new Vegetation());
        TextField label = (TextField) box.getChildren().get(0);
        ((Button) box.getChildren().get(2)).setOnAction(event -> {
            this.addAVegetation();
        });
        label.setText(stringConcerned);
        return box;
    }

}
