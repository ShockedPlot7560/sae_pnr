package modele.ihm;

import javafx.beans.property.*;
import modele.donnee.Observateur;

public class Dashboard {
    /* Attributes */

    private final StringProperty username;
    private final Observateur observateur;

    /* Constructors */

    public Dashboard(Observateur observateur) {
        this.observateur = observateur;
        this.username = new SimpleStringProperty(observateur.toString());
    }

    /* Getters/Setters */

    public String getUsername() {
        return this.username.get();
    }

    public StringProperty usernameProperty() {
        return this.username;
    }

    public Observateur getObservateur() {
        return this.observateur;
    }

    /* Methods */
}
