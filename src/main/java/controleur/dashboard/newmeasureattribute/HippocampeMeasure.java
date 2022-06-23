package controleur.dashboard.newmeasureattribute;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import controleur.dashboard.InsertFailedException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import modele.bdd.table.ObsHippocampeTable;
import modele.donnee.EspeceHippocampe;
import modele.donnee.Lieu;
import modele.donnee.Observateur;
import modele.donnee.Peche;
import modele.donnee.Sexe;

public class HippocampeMeasure implements IMeasure {

    @FXML
    private ComboBox<EspeceHippocampe> especeComboBox;
    @FXML
    private ComboBox<Sexe> sexeComboBox;
    @FXML
    private ComboBox<Peche> pecheCombobox;
    @FXML
    private TextField temperatureInput;
    @FXML
    private TextField tailleInput;
    @FXML
    private CheckBox gestantCheckBox;

    @FXML
    private void initialize() {
        ObservableList<EspeceHippocampe> especeList = FXCollections.observableArrayList(EspeceHippocampe.values());
        this.especeComboBox.setItems(especeList);
        ObservableList<Sexe> sexeList = FXCollections.observableArrayList(Sexe.values());
        this.sexeComboBox.setItems(sexeList);
        ObservableList<Peche> pecheList = FXCollections.observableArrayList(Peche.values());
        this.pecheCombobox.setItems(pecheList);
    }

    @Override
    public void addMesure(Date date, Time heure, Lieu lieu, ArrayList<Observateur> observateurs)
            throws InsertFailedException {
        ObsHippocampeTable table = new ObsHippocampeTable();
        if (this.especeComboBox.getValue() == null) {
            throw new InsertFailedException("Veuillez choisir une espèce d'hippocampe");
        }
        if (this.sexeComboBox.getValue() == null) {
            throw new InsertFailedException("Veuillez choisir un sexe");
        }
        if (this.pecheCombobox.getValue() == null) {
            throw new InsertFailedException("Veuillez choisir une pêche");
        }
        if (this.temperatureInput.getText().isEmpty()) {
            throw new InsertFailedException("Veuillez entrer une température");
        }
        if (this.tailleInput.getText().isEmpty()) {
            throw new InsertFailedException("Veuillez entrer une taille");
        }
        int temperature;
        try {
            temperature = Integer.parseInt(this.temperatureInput.getText());
        } catch (NumberFormatException e) {
            throw new InsertFailedException("Veuillez entrer une température valide");
        }
        double taille;
        try {
            taille = Double.parseDouble(this.tailleInput.getText());
        } catch (NumberFormatException e) {
            throw new InsertFailedException("Veuillez entrer une taille valide");
        }
        try {
            table.insertObservation(date, heure, lieu, observateurs, this.especeComboBox.getValue(),
                    this.sexeComboBox.getValue(), temperature, this.pecheCombobox.getValue(), taille,
                    this.gestantCheckBox.isSelected());
        } catch (Exception e) {
            throw new InsertFailedException("Une erreur est survenue: " + e.getMessage());
        }
    }
}
