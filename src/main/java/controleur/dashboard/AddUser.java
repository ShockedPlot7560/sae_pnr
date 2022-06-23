package controleur.dashboard;

import controleur.Dashboard;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import modele.StaticBcrypt;
import modele.bdd.table.ObservateurTable;

public class AddUser {

    private Dashboard mainControleur;

    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField username;
    @FXML
    private PasswordField mdp;
    @FXML
    private PasswordField mdpConfirm;
    @FXML
    private Label errorMessageAddUser;

    public AddUser(Dashboard mainControleur) {
        if (mainControleur == null) {
            throw new IllegalArgumentException("Dashboard cannot be null");
        }
        this.mainControleur = mainControleur;
    }

    @FXML
    public void submit() {
        System.out.println("Submit");

        if (!this.prenom.getText().isEmpty() && !this.username.getText().isEmpty() && !this.mdp.getText().isEmpty() && !this.mdpConfirm.getText().isEmpty()) {
            if (this.mdp.getText().equals(this.mdpConfirm.getText())) {
                String passwordHash = StaticBcrypt.hash(this.mdp.getText());
                try {
                    new ObservateurTable().insertObservateur(this.nom.getText(), this.prenom.getText(), this.username.getText(), passwordHash);
                    this.back();
                } catch (InsertFailedException e) {
                    System.out.println(e.getMessage());
                    this.errorMessageAddUser.setText("Une erreur est survenue: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                this.errorMessageAddUser.setText("Les mots de passe ne correspondent pas");
            }
        } else {
            this.errorMessageAddUser.setText("Veuillez remplir tous les champs nécessaires");
        }

    }

    @FXML
    public void back() {
        this.mainControleur.manageUser();
    }
}
