package controleur.dashboard.newmeasureattribute;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import controleur.dashboard.InsertFailedException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import modele.bdd.table.ObsLoutreTable;
import modele.donnee.IndiceLoutre;
import modele.donnee.Lieu;
import modele.donnee.Observateur;

public class LoutreMeasure implements IMeasure {

    @FXML
    private ComboBox<IndiceLoutre> indiceComboBox;
    @FXML
    private ComboBox<String> communeComboBox;
    @FXML
    private Button switchButtonCommune;
    @FXML
    private HBox addCommuneBox;
    @FXML
    private TextField nomCommune;
    @FXML
    private ComboBox<String> lieuDitComboBox;
    @FXML
    private Button switchButtonLieuDit;
    @FXML
    private HBox addLieuDitBox;
    @FXML
    private TextField nomLieuDit;

    @FXML
    private void initialize() throws SQLException {
        ObservableList<IndiceLoutre> indiceList = FXCollections.observableArrayList(IndiceLoutre.values());
        this.indiceComboBox.setItems(indiceList);
        this.indiceComboBox.setValue(IndiceLoutre.NON_PROSPECTION);
        this.addCommuneBox.setVisible(false);
        this.addLieuDitBox.setVisible(false);
        this.communeComboBox.setItems(FXCollections.observableArrayList(new ObsLoutreTable().getAllCommune()));
        this.lieuDitComboBox.setItems(FXCollections.observableArrayList(new ObsLoutreTable().getAllLieuDit()));
    }

    @FXML
    public void enableEditingCommune() {
        this.switchButtonCommune.setVisible(false);
        this.communeComboBox.setValue(null);
        this.addCommuneBox.setVisible(true);
    }

    @FXML
    public void disableEditingCommune() {
        this.switchButtonCommune.setVisible(true);
        this.addCommuneBox.setVisible(false);
    }

    @FXML
    public void enableEditingLieuDit() {
        this.switchButtonLieuDit.setVisible(false);
        this.lieuDitComboBox.setValue(null);
        this.addLieuDitBox.setVisible(true);
    }

    @FXML
    public void disableEditingLieuDit() {
        this.addLieuDitBox.setVisible(false);
        this.switchButtonLieuDit.setVisible(true);
    }

    @Override
    public void addMesure(Date date, Time heure, Lieu lieu, ArrayList<Observateur> observateurs)
            throws InsertFailedException {
        IndiceLoutre indice;
        if (this.indiceComboBox.getValue() == null) {
            indice = IndiceLoutre.NON_PROSPECTION;
        } else {
            indice = this.indiceComboBox.getValue();
        }
        String commune;
        if (this.communeComboBox.getValue() == null) {
            commune = this.nomCommune.getText();
        } else {
            commune = this.communeComboBox.getValue();
        }
        String lieuDit;
        if (this.lieuDitComboBox.getValue() == null) {
            lieuDit = this.nomLieuDit.getText();
        } else {
            lieuDit = this.lieuDitComboBox.getValue();
        }
        try {
            new ObsLoutreTable().insertObservation(date, heure, lieu, observateurs, commune, lieuDit, indice);
        } catch (Exception e) {
            throw new InsertFailedException("Une erreur est survenue: " + e.getMessage());
        }
    }
}
