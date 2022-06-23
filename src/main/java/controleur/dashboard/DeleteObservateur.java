package controleur.dashboard;

import javafx.fxml.FXML;
import modele.donnee.Observateur;

public class DeleteObservateur {

    private NewMeasure model;
    private Observateur observateur;

    public DeleteObservateur(NewMeasure model, Observateur observateur) {
        if (observateur == null) {
            throw new IllegalArgumentException("Observateur cannot be null");
        }
        this.model = model;
        this.observateur = observateur;
    }

    @FXML
    public void deleteObservateur() {
        this.model.deleteObservateur(this.observateur);
    }

}
