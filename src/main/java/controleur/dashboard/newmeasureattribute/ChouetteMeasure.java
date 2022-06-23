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
import modele.bdd.table.ChouetteTable;
import modele.bdd.table.ObsChouetteTable;
import modele.donnee.Chouette;
import modele.donnee.EspeceChouette;
import modele.donnee.Lieu;
import modele.donnee.Observateur;
import modele.donnee.Sexe;
import modele.donnee.TypeObservation;

public class ChouetteMeasure implements IMeasure {

    @FXML
    private ComboBox<TypeObservation> typeObsComboBox;
    @FXML
    private ComboBox<String> chouetteComboBox;
    @FXML
    private HBox addChouetteBox;
    @FXML
    private TextField identifiantChouette;
    @FXML
    private ComboBox<Sexe> sexeComboBox;
    @FXML
    private ComboBox<EspeceChouette> especeComboBox;
    @FXML
    private Button switchButtonChouette;

    @FXML
    private void initialize() throws SQLException {
        ObservableList<TypeObservation> obsList = FXCollections.observableArrayList(TypeObservation.values());
        this.typeObsComboBox.setItems(obsList);
        ObservableList<String> chouetteList = FXCollections.observableArrayList(new ChouetteTable().getAllIdChouette());
        this.chouetteComboBox.setItems(chouetteList);
        this.addChouetteBox.setVisible(false);
        this.switchButtonChouette.setVisible(true);
        ObservableList<Sexe> sexeList = FXCollections.observableArrayList(Sexe.values());
        this.sexeComboBox.setItems(sexeList);
        ObservableList<EspeceChouette> especeList = FXCollections.observableArrayList(EspeceChouette.values());
        this.especeComboBox.setItems(especeList);
    }

    @FXML
    public void switchChouette() {
        this.switchButtonChouette.setVisible(false);
        this.addChouetteBox.setVisible(true);
    }

    @Override
    public void addMesure(Date date, Time heure, Lieu lieu, ArrayList<Observateur> observateurs)
            throws InsertFailedException {
        ChouetteTable table = new ChouetteTable();
        Chouette chouette = null;
        if (this.typeObsComboBox.getValue() == null) {
            throw new InsertFailedException("Veuillez choisir un type d'observation");
        }
        if (this.identifiantChouette.getText() != null && this.sexeComboBox.getValue() != null
                && this.especeComboBox.getValue() != null) {
            try {
                table.insertChouette(this.identifiantChouette.getText(), this.especeComboBox.getValue(),
                        this.sexeComboBox.getValue());
                chouette = table.getChouette(this.identifiantChouette.getText());
            } catch (Exception e) {
                throw new InsertFailedException("Une erreur est survenue: " + e.getMessage());
            }
        } else {
            if (this.chouetteComboBox.getValue() != null) {
                try {
                    chouette = table.getChouette(this.chouetteComboBox.getValue());
                } catch (Exception e) {
                    throw new InsertFailedException("Une erreur est survenue: " + e.getMessage());
                }
            } else {
                throw new InsertFailedException("Veuillez choisir une chouette");
            }
        }
        ObsChouetteTable obsTable = new ObsChouetteTable();
        try {
            obsTable.insertObservation(date, heure, lieu, observateurs, this.typeObsComboBox.getValue(), chouette);
        } catch (Exception e) {
            throw new InsertFailedException("Une erreur est survenue: " + e.getMessage());
        }
    }
}
