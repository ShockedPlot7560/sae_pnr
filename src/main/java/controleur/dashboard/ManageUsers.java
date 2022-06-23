package controleur.dashboard;

import controleur.Dashboard;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import modele.bdd.table.ObservateurTable;
import modele.donnee.Observateur;
import utils.Utils;

public class ManageUsers {

    public Dashboard mainControleur;

    @FXML
    private Button newUser;
    @FXML
    public FlowPane flowPane;

    public ManageUsers(Dashboard mainControleur) {
        if (mainControleur == null) {
            throw new IllegalArgumentException("Dashboard cannot be null");
        }
        this.mainControleur = mainControleur;
    }

    @FXML
    public void initialize() {
        ManageUserThread thread = new ManageUserThread(this);
        thread.start();
    }

    @FXML
    private void newUser() {
        this.mainControleur.addUser();
    }
}

class ManageUserThread extends Thread {

    private ManageUsers main;

    public ManageUserThread(ManageUsers main) {
        this.main = main;
    }

    @Override
    public void run() {
        try {
            for (Observateur observateur : new ObservateurTable().getAllObservateur()) {
                this.addObservateur(observateur);
            }
        } catch (Exception e) {
            Platform.runLater(() -> {
                this.main.flowPane.getChildren()
                        .setAll(new Label("Erreur lors de la récupération des utilisateurs: " + e.getMessage()));
            });
        }
    }

    public Parent createVBox(Observateur observateur) {
        String path = "/fxml/dashboard/manage-users-widget.fxml";
        return Utils.loadFXMLWithController(this, path, new ManageUsersWidget(main, observateur));
    }

    public void addObservateur(Observateur observateur) {
        Platform.runLater(() -> {
            this.main.flowPane.getChildren().add(this.createVBox(observateur));
        });
    }
}