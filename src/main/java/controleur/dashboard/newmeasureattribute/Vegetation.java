package controleur.dashboard.newmeasureattribute;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import modele.donnee.NatureVege;

public class Vegetation {

    @FXML
    private ComboBox<NatureVege> vegetationComboBox;

    @FXML
    private void initialize() {
        this.vegetationComboBox.setItems(FXCollections.observableArrayList(NatureVege.values()));
    }
}
