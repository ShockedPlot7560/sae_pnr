package controleur;

import java.util.ArrayList;
import java.util.Arrays;

import controleur.dashboard.*;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modele.donnee.EspeceObservee;
import modele.donnee.Observateur;
import utils.Utils;

public class Dashboard {

    private modele.ihm.Dashboard model;
    private Observateur observateur;

    @FXML
    private Label usernameLabel;
    @FXML
    public BorderPane rootPane;
    @FXML
    public Button newMeasureButton;
    @FXML
    public Button viewEspeceButton;
    @FXML
    public Button carteAnalyseButton;
    @FXML
    public Button exportDataButton;
    @FXML
    public Button gestionUserButton;
    @FXML
    public Button disconnectButton;
    @FXML
    public VBox adminHBox;

    public Dashboard(Observateur observateur) {
        this.model = new modele.ihm.Dashboard(observateur);
        this.observateur = observateur;
    }

    @FXML
    public void initialize() {
        this.usernameLabel.textProperty().bindBidirectional(this.model.usernameProperty());
        Parent root = Utils.loadFXMLWithController(this, "/fxml/dashboard/index.fxml", new Index(this));
        this.rootPane.setCenter(root);
        if(this.observateur.getUsername().equals("admin")){
            this.adminHBox.setVisible(true);
        }else{
            this.adminHBox.setVisible(false);
        }
    }

    @FXML
    private void newMeasure() {
        this.newMeasure(null);
    }

    private void newMeasure(EspeceObservee espece) {
        NewMeasure controller = new NewMeasure(this, this.model.getObservateur(), espece);
        Parent root = Utils.loadFXMLWithController(this, "/fxml/dashboard/new-measure.fxml", controller);
        this.rootPane.setCenter(root);
    }

    @FXML
    private void viewEspece() {
        this.initialize();
    }

    @FXML
    private void exportDonnees() {
        Parent root = Utils.loadFXMLWithController(this, "/fxml/dashboard/export.fxml", new ExportData(this));
        this.rootPane.setCenter(root);
    }

    @FXML
    public void manageUser() {
        Parent root = Utils.loadFXMLWithController(this, "/fxml/dashboard/manage-users.fxml", new ManageUsers(this));
        this.rootPane.setCenter(root);
    }

    @FXML
    private void disconnect() {
        App.setCurrentView(new vue.Login());
    }

    @FXML
    public void addUser() {
        Parent root = Utils.loadFXMLWithController(this, "/fxml/dashboard/add-user.fxml", new AddUser(this));
        this.rootPane.setCenter(root);
    }

    @FXML
    public void viewMeasure() {
        Parent root = Utils.loadFXMLWithController(this, "/fxml/dashboard/cart-analize.fxml", new CarteAnalyse(this));
        this.rootPane.setCenter(root);
    }

    public void viewEspece(EspeceObservee espece) {
        Parent root = Utils.loadFXMLWithController(this, "/fxml/dashboard/cart-analize.fxml",
                new CarteAnalyse(this, new ArrayList<>(Arrays.asList(espece))));
        this.rootPane.setCenter(root);
    }
}
