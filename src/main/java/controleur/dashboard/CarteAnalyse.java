package controleur.dashboard;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.sothawo.mapjfx.Configuration;
import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.MapView;
import com.sothawo.mapjfx.Marker;
import com.sothawo.mapjfx.Projection;

import controleur.Dashboard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import modele.donnee.*;

public class CarteAnalyse {
    @FXML
    public MapView map;
    @FXML
    public Button backButton;

    private Dashboard dashboard;

    public ArrayList<Observation> observations;
    public HashMap<EspeceObservee, ArrayList<Marker>> markers;
    public ArrayList<EspeceObservee> especesPrinted;

    @FXML
    private TabPane tabPane;

    public CarteAnalyse(Dashboard dashboard, ArrayList<EspeceObservee> especesPrinted) {
        if (dashboard == null)
            throw new IllegalArgumentException("Dashboard is null");
        if (especesPrinted == null)
            throw new IllegalArgumentException("EspecesPrinted is null");
        this.dashboard = dashboard;
        this.especesPrinted = especesPrinted;
    }

    public CarteAnalyse(Dashboard mainDashboard) {
        this(mainDashboard, new ArrayList<EspeceObservee>(Arrays.asList(EspeceObservee.values())));
    }

    public void addTab(EspeceObservee espece) {
        TabViewEspece controller = new TabViewEspece(this, espece);
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/dashboard/carte-tab.fxml"));
        loader.setController(controller);
        Tab root;
        try {
            root = loader.load();
        } catch (IOException e) {
            root = null;
            e.printStackTrace();
        }
        this.tabPane.getTabs().add(root);
    }

    @FXML
    public void initialize() throws SQLException, InterruptedException {
        for (EspeceObservee especeObservee : EspeceObservee.values()) {
            this.addTab(especeObservee);
        }
        this.observations = new ArrayList<Observation>();

        this.map.initialize(Configuration.builder()
                .projection(Projection.WEB_MERCATOR)
                .showZoomControls(true)
                .build());

        this.map.setZoom(11);
        this.map.setCenter(new Coordinate(47.6, -2.8));
    }

    @FXML
    public void back() {
        this.dashboard.initialize();
    }
}