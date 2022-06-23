package controleur.dashboard;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import com.sothawo.mapjfx.*;
import com.sothawo.mapjfx.event.MapViewEvent;

import controleur.Dashboard;
import controleur.dashboard.newmeasureattribute.*;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import modele.bdd.table.ObservateurTable;
import modele.donnee.*;
import utils.Utils;

public class NewMeasure {

    private modele.ihm.dashboard.NewMeasure model;
    private ObservateurTable observateurTable;
    private ArrayList<Observateur> observateurs;
    private Dashboard mainControleur;

    @FXML
    private ComboBox<EspeceObservee> especeComboBox;
    @FXML
    private ComboBox<Observateur> observateursComboBox;
    @FXML
    private FlowPane observateursFlowPane;
    @FXML
    private Pane attributPane;
    @FXML
    private MapView mapView;
    @FXML
    private TextField xCoordTextField;
    @FXML
    private TextField yCoordTextField;
    @FXML
    private DatePicker date;
    @FXML
    private ComboBox<Integer> heure;
    @FXML
    private ComboBox<Integer> minute;
    @FXML
    private Text errorMessage;

    private Marker coordsMarker;
    private Lieu coordsLieu;
    private IMeasure controller;

    public NewMeasure(Dashboard mainControleur, Observateur observateur) {
        this(mainControleur, observateur, null);
    }

    public NewMeasure(Dashboard mainControleur, Observateur observateur, EspeceObservee especeObservee) {
        if (observateur == null) {
            throw new IllegalArgumentException("Observateur cannot be null");
        }
        if (mainControleur == null) {
            throw new IllegalArgumentException("Dashboard cannot be null");
        }
        this.mainControleur = mainControleur;
        this.model = new modele.ihm.dashboard.NewMeasure(observateur, especeObservee);
        this.observateurTable = new ObservateurTable();

        this.coordsMarker = Marker.createProvided(Marker.Provided.GREEN).setVisible(false);
    }

    @FXML
    private void initialize() throws SQLException {
        this.observateurs = this.observateurTable.getAllObservateur();
        ObservableList<EspeceObservee> especeList = FXCollections.observableArrayList(EspeceObservee.values());
        this.especeComboBox.setItems(especeList);
        if (this.model.getEspeceObservee() != null) {
            this.especeComboBox.getSelectionModel().select(this.model.getEspeceObservee());
        }
        ObservableList<Observateur> observateurList = FXCollections.observableArrayList(this.observateurs);
        this.observateursComboBox.setItems(observateurList);
        this.addObservateur(this.model.getObservateur());
        if (this.model.getEspeceObservee() != null) {
            this.changeForEspece(this.model.getEspeceObservee());
        } else {
            this.attributPane.getChildren().setAll(new Label("Aucune espèce sélectionné"));
        }

        this.mapView.initialize(Configuration.builder()
                .projection(Projection.WEB_MERCATOR)
                .showZoomControls(true)
                .build());

        this.mapView.setZoom(11);
        this.mapView.setCenter(new Coordinate(47.6, -2.8));

        this.mapView.addEventHandler(MapViewEvent.MAP_CLICKED, event -> {
            Coordinate coord = event.getCoordinate().normalize();
            double lat = coord.getLatitude();
            double lon = coord.getLongitude();
            System.out.println("Clicked at : " + lat + ", " + lon);

            this.coordsLieu = new Lieu(lat, lon, false);
            this.coordsLieu.toLambert();
            this.coordsMarker.setPosition(new Coordinate(lat, lon).normalize());

            this.mapView.addMarker(this.coordsMarker);
            this.coordsMarker.setVisible(true);

            this.xCoordTextField.setText(String.format("%f", this.coordsLieu.getxCoord()));
            this.yCoordTextField.setText(String.format("%f", this.coordsLieu.getyCoord()));
        });
        this.errorMessage.setVisible(false);

        for (int i = 0; i < 60; i++) {
            if (i < 24) {
                this.heure.getItems().add(i);
            }
            this.minute.getItems().add(i);
        }
    }

    public void addObservateur(Observateur observateur) {
        this.observateursFlowPane.getChildren().remove(this.observateursComboBox);
        this.observateursFlowPane.getChildren().add(this.createHBOX(observateur));
        this.observateursFlowPane.getChildren().add(this.observateursComboBox);
    }

    public Parent createHBOX(Observateur observateur) {
        String path = "/fxml/dashboard/new-measure-observateur.fxml";
        HBox box = (HBox) Utils.loadFXMLWithController(this, path, new DeleteObservateur(this, observateur));
        Label label = (Label) box.getChildren().get(0);
        label.setText(observateur.toString());
        return box;
    }

    public void observateursSelect() {
        Observateur newObs = this.observateursComboBox.getValue();
        if (!this.getAllCurrentObservateur().contains(newObs)) {
            this.addObservateur(newObs);
        }
    }

    public ArrayList<Observateur> getAllCurrentObservateur() {
        ArrayList<Observateur> observateurs = new ArrayList<>();
        for (int i = 0; i < this.observateursFlowPane.getChildren().size(); i++) {
            if (this.observateursFlowPane.getChildren().get(i) instanceof HBox) {
                HBox hbox = (HBox) this.observateursFlowPane.getChildren().get(i);
                String hboxLabel = ((Label) hbox.getChildren().get(0)).getText();
                observateurs.add(this.getObservateur(hboxLabel));
            }
        }
        return observateurs;
    }

    public Observateur getObservateur(String string) {
        Observateur ret = null;
        Iterator<Observateur> iterator = this.observateurs.iterator();
        while (ret == null && iterator.hasNext()) {
            Observateur observateur = iterator.next();
            if (observateur.toString().equals(string)) {
                ret = observateur;
            }
        }
        return ret;
    }

    public void deleteObservateur(Observateur observateur) {
        ObservableList<Node> children = this.observateursFlowPane.getChildren();
        Iterator<Node> iterator = children.iterator();
        while (iterator.hasNext()) {
            Node node = iterator.next();
            if (node instanceof HBox) {
                HBox hbox = (HBox) node;
                String hboxLabel = ((Label) hbox.getChildren().get(0)).getText();
                if (hboxLabel.equals(observateur.toString())) {
                    this.observateursFlowPane.getChildren().remove(hbox);
                    break;
                }
            }
        }
    }

    public void changeForEspece(EspeceObservee espece) {
        String path = "/fxml/dashboard/new-measure-" + espece.toString().toLowerCase() + ".fxml";
        switch (espece) {
            case BATRACIEN:
                this.controller = new BatracienMeasure();
                break;
            case HIPPOCAMPE:
                this.controller = new HippocampeMeasure();
                break;
            case LOUTRE:
                this.controller = new LoutreMeasure();
                break;
            case CHOUETTE:
                this.controller = new ChouetteMeasure();
                break;
            case GCI:
                this.controller = new GCIMeasure();
                break;
        }
        Parent root = Utils.loadFXMLWithController(this, path, this.controller);
        this.attributPane.getChildren().setAll(root);
    }

    public void especeSelectionne() {
        this.changeForEspece(this.especeComboBox.getValue());
    }

    public void addObservation() {
        try {
            if (this.controller != null) {
                if (this.xCoordTextField.getText() == null || this.xCoordTextField.getText().isEmpty()) {
                    throw new InsertFailedException("Coordonnées lambert X non renseigné");
                }
                if (this.yCoordTextField.getText() == null || this.yCoordTextField.getText().isEmpty()) {
                    throw new InsertFailedException("Coordonnées lambert Y non renseigné");
                }
                if (this.date.getValue() == null) {
                    throw new InsertFailedException("Date non renseignée");
                }
                if (this.heure.getValue() == null) {
                    throw new InsertFailedException("Heure non renseignée");
                }
                if (this.minute.getValue() == null) {
                    throw new InsertFailedException("Minute non renseignée");
                }
                double x, y;
                try {
                    x = Double.valueOf(this.xCoordTextField.getText().replace(',', '.'));
                    y = Double.valueOf(this.yCoordTextField.getText().replace(',', '.'));
                } catch (Exception e) {
                    throw new InsertFailedException("Coordonnées lambert non valides");
                }
                Lieu lieu = new Lieu(y, x, true);
                Date date = Date.valueOf(this.date.getValue());
                System.out.println(heure + ":" + minute + ":" + ":00");
                Time time = new Time(this.heure.getValue(), this.minute.getValue(), 0);
                System.out.println(time);
                this.controller.addMesure(date, time, lieu, this.getAllCurrentObservateur());
                this.mainControleur.initialize();
            }
        } catch (InsertFailedException e) {
            this.errorMessage.setText(e.getMessage());
            this.errorMessage.setVisible(true);
        }
    }

    public void moveMarker(ActionEvent _e) {
        try {
            double x = Double.valueOf(this.xCoordTextField.getText().replace(',', '.'));
            double y = Double.valueOf(this.yCoordTextField.getText().replace(',', '.'));
            System.out.printf("New position: %f, %f%n", x, y);
            Lieu lieu = new Lieu(x, y, true);
            lieu.toLatLong();
            this.coordsMarker.setPosition(new Coordinate(lieu.getxCoord(), lieu.getyCoord()));
        } catch (NumberFormatException e) {
        }
    }
}
