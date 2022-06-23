package controleur.dashboard;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import modele.donnee.Observateur;
import utils.Utils;

public class ManageUsersWidget {
    @FXML
    private Label utilisateurLabel;
    @FXML
    private ImageView utilisateurImage;
    @FXML
    private Button button;

    private Observateur linkedObservateur;
    private ManageUsers parent;

    public ManageUsersWidget(ManageUsers parent, Observateur observateur) {
        if (observateur == null) {
            throw new IllegalArgumentException("Observateur cannot be null");
        }
        if (parent == null) {
            throw new IllegalArgumentException("Parent cannot be null");
        }
        this.parent = parent;
        this.linkedObservateur = observateur;
    }

    @FXML
    public void initialize() {
        this.utilisateurLabel.setText(this.linkedObservateur.getNom() + " " + this.linkedObservateur.getPrenom());
    }

    @FXML
    public void edit() {
        Parent root = Utils.loadFXMLWithController(this, "/fxml/dashboard/add-user.fxml",
                new EditUser(parent.mainControleur, this.linkedObservateur));
        parent.mainControleur.rootPane.setCenter(root);
    }
}
