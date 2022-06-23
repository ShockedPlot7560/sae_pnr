package controleur.dashboard;

import controleur.Dashboard;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import modele.StaticBcrypt;
import modele.bdd.table.ObservateurTable;
import modele.donnee.Observateur;

public class EditUser {

    private Dashboard mainControleur;
    private Observateur observateur;

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
    @FXML
    private Button addUser;

    public EditUser(Dashboard mainControleur, Observateur observateur) {
        if (mainControleur == null) {
            throw new IllegalArgumentException("Dashboard cannot be null");
        }
        this.mainControleur = mainControleur;
        this.observateur = observateur;
    }

    @FXML public void initialize(){
        this.nom.setText(this.observateur.getNom() == null ? "" : this.observateur.getNom());
        this.prenom.setText(this.observateur.getPrenom() == null ? "" : this.observateur.getPrenom());
        this.username.setText(this.observateur.getUsername() == null ? "" : this.observateur.getUsername());
        this.addUser.setText("Modifier l'utilisateur");
    }

    @FXML
    public void submit() {
        if(this.nom.getText().isEmpty()){
            this.errorMessageAddUser.setText("Veuillez entrer un nom");
            return;
        }
        if(this.prenom.getText().isEmpty()){
            this.errorMessageAddUser.setText("Veuillez entrer un prénom");
            return;
        }
        if(this.username.getText().isEmpty()){
            this.errorMessageAddUser.setText("Veuillez entrer un nom d'utilisateur");
            return;
        }
        try {
            new ObservateurTable().updateObservateur(this.observateur, this.nom.getText(), this.prenom.getText(), this.username.getText());
            if(!this.mdp.getText().isEmpty()){
                if(this.mdp.getText().equals(this.mdpConfirm.getText())){
                    new ObservateurTable().updateObservateurPassword(this.observateur, StaticBcrypt.hash(this.mdp.getText()));
                }else{
                    this.errorMessageAddUser.setText("Les mots de passe ne correspondent pas");
                    return;
                }
            }
            this.back();
        } catch (InsertFailedException e) {
            this.errorMessageAddUser.setText("Erreur lors de la modification de l'utilisateur");
            return;
        }
    }

    @FXML
    public void back() {
        this.mainControleur.manageUser();
    }
}
