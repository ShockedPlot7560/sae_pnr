package modele.ihm;

import java.sql.SQLException;

import javafx.beans.property.*;
import modele.StaticBcrypt;
import modele.bdd.table.ObservateurTable;
import modele.donnee.Observateur;
import modele.exception.LoginException;

public class Login {
    /* Attributes */

    private final StringProperty username;
    private final StringProperty password;

    /* Constructors */

    public Login() {
        this("");
    }

    public Login(String defaultUsername) {
        this.username = new SimpleStringProperty(defaultUsername);
        this.password = new SimpleStringProperty("");
    }

    /* Getters/Setters */

    public String getUsername() {
        return this.username.get();
    }

    public StringProperty usernameProperty() {
        return this.username;
    }

    public String getPassword() {
        return this.password.get();
    }

    public StringProperty passwordProperty() {
        return this.password;
    }

    /* Methods */

    public Observateur submitForm() throws LoginException, SQLException {
        System.out.println(StaticBcrypt.hash("I_L0v3_PNR"));
        if (this.getUsername().isEmpty() || this.getPassword().isEmpty()) {
            throw new LoginException("Veuillez remplir tous les champs");
        }
        Observateur obs = new ObservateurTable().getObservateurByUsername(this.username.get());
        if (obs == null) {
            throw new LoginException("Cet utilisateur n'existe pas");
        }
        if (obs.getPasswordHash() == null) {
            throw new LoginException("Cet utilisateur n'a pas de mot de passe, contacter un administrateur");
        }
        System.out.println(obs.getPasswordHash());
        if (!StaticBcrypt.verifyHash(this.password.get(), obs.getPasswordHash())) {
            throw new LoginException("Mot de passe incorrect");
        }
        return obs;
    }
}
