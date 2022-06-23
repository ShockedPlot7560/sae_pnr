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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import modele.bdd.table.NidGciTable;
import modele.bdd.table.ObsGCITable;
import modele.donnee.ContenuNid;
import modele.donnee.Lieu;
import modele.donnee.Observateur;
import modele.donnee.RaisonArretObservation;

public class GCIMeasure implements IMeasure {

    @FXML
    private ComboBox<ContenuNid> contenuNidComboBox;
    @FXML
    private TextField valeur;
    @FXML
    private CheckBox presentMaisPasObs;

    @FXML
    private ComboBox<String> nidComboBox;
    @FXML
    private Button switchButtonGCI;
    @FXML
    private VBox addGciBox;
    @FXML
    private ComboBox<String> plageComboBox;
    @FXML
    private TextField otherPlageInput;
    @FXML
    private TextField nbrEnvol;
    @FXML
    private TextField bagueMale;
    @FXML
    private TextField bagueFemelle;
    @FXML
    private ComboBox<RaisonArretObservation> raisonArretObs;

    @FXML
    private void initialize() throws SQLException {
        NidGciTable table = new NidGciTable();
        ObservableList<ContenuNid> obsList = FXCollections.observableArrayList(ContenuNid.values());
        this.contenuNidComboBox.setItems(obsList);
        this.addGciBox.setVisible(false);
        this.switchButtonGCI.setVisible(true);

        ObservableList<String> plageComboBox = FXCollections.observableArrayList(table.getAllPlage());
        this.plageComboBox.setItems(plageComboBox);

        ObservableList<String> nidComboBox = FXCollections.observableArrayList(table.getAllNidString());
        this.nidComboBox.setItems(nidComboBox);

        this.raisonArretObs.setItems(FXCollections.observableArrayList(RaisonArretObservation.values()));
    }

    @FXML
    public void switchGCI() {
        this.switchButtonGCI.setVisible(false);
        this.addGciBox.setVisible(true);
    }

    @Override
    public void addMesure(Date date, Time heure, Lieu lieu, ArrayList<Observateur> observateurs)
            throws InsertFailedException {
        NidGciTable nidGciTable = new NidGciTable();
        if (this.contenuNidComboBox.getValue() == null) {
            throw new InsertFailedException("Veuillez sélectionner un contenu de nid");
        }
        if (this.valeur.getText() == null || this.valeur.getText().isEmpty()) {
            throw new InsertFailedException("Veuillez entrer une valeur");
        }
        if (this.nidComboBox.getValue() == null && this.switchButtonGCI.isVisible()) {
            throw new InsertFailedException("Veuillez entrer un nid");
        }
        int nidId = -1;
        if (this.nidComboBox.getValue() == null) {
            String plage = this.plageComboBox.getValue();
            if (plage == null) {
                plage = this.otherPlageInput.getText();
                if (plage == null || plage.isEmpty()) {
                    throw new InsertFailedException("Veuillez entrer une plage");
                }
            }
            if (this.nbrEnvol.getText() == null || this.nbrEnvol.getText().isEmpty()) {
                throw new InsertFailedException("Veuillez entrer un nombre d'envol");
            }
            try {
                Integer.parseInt(this.nbrEnvol.getText());
            } catch (NumberFormatException e) {
                throw new InsertFailedException("Veuillez entrer un nombre d'envol valide");
            }
            try {
                nidId = nidGciTable.insert(plage, this.raisonArretObs.getValue(),
                        Integer.parseInt(this.nbrEnvol.getText()), 1, bagueMale.getText(), bagueFemelle.getText());
            } catch (Exception e) {
                throw new InsertFailedException(
                        "Une erreur est survenue lors de l'insertion du nid: " + e.getMessage());
            }
        } else {
            nidId = Integer.parseInt(this.nidComboBox.getValue().split(":")[0]);
        }
        if (nidId == -1) {
            throw new InsertFailedException("Une erreur est survenue lors de l'insertion du nid");
        }
        try {
            new ObsGCITable().insertObservation(
                    date, heure, lieu, observateurs,
                    this.contenuNidComboBox.getValue(),
                    Integer.valueOf(this.valeur.getText()),
                    this.presentMaisPasObs.isSelected(),
                    nidId);
        } catch (Exception e) {
            throw new InsertFailedException("Une erreur est survenue: " + e.getMessage());
        }
    }
}
