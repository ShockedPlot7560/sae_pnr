package vue;

import javafx.scene.Parent;
import javafx.scene.Scene;
import modele.donnee.Observateur;
import utils.Utils;

public class Dashboard extends View {

    public Dashboard(Observateur observateur) {
        Parent root = Utils.loadFXMLWithController(this, "/fxml/dashboard.fxml",
                new controleur.Dashboard(observateur));
        this.scene = new Scene(root);
    }

}
