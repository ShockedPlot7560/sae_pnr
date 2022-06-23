package modele.ihm.dashboard;

import javafx.beans.property.*;
import modele.donnee.EspeceObservee;
import modele.donnee.Observateur;

public class NewMeasure {
    /* Attributes */

    private final Observateur observateur;
    private final StringProperty username;
    private final EspeceObservee especeObservee;

    /* Constructors */

    public NewMeasure(Observateur observateur) {
        this(observateur, null);
    }

    public NewMeasure(Observateur observateur, EspeceObservee espece){
        this.username = new SimpleStringProperty(observateur.toString());
        this.especeObservee = espece;
        this.observateur = observateur;
    }

    /* Getters/Setters */

    public String getUsername() {
        return this.username.get();
    }

    public StringProperty usernameProperty() {
        return this.username;
    }

    public EspeceObservee getEspeceObservee() {
        return this.especeObservee;
    }

    public Observateur getObservateur() {
        return this.observateur;
    }

    /* Methods */
}
