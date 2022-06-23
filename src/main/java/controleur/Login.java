package controleur;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import modele.donnee.Observateur;
import modele.exception.LoginException;
import vue.Dashboard;

public class Login {

    private modele.ihm.Login model;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorMessageLogin;

    @FXML
    private void initialize() {
        this.model = new modele.ihm.Login();
        this.usernameField.textProperty().bindBidirectional(this.model.usernameProperty());
        this.passwordField.textProperty().bindBidirectional(this.model.passwordProperty());
    }

    @FXML
    private void submitForm() {
        try {
            Observateur obs = this.model.submitForm();
            App.setCurrentView(new Dashboard(obs));
        } catch (LoginException e) {
            this.errorMessageLogin.setText(e.getMessage());
        } catch (SQLException e) {
            this.errorMessageLogin.setText("Une erreur est survenue : " + e.getMessage());
        }
    }
}
