package controleur.dashboard;

import controleur.Dashboard;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import modele.donnee.EspeceObservee;

public class Index {
    private Dashboard main;
    
    @FXML
    public Button loutreView;
    @FXML
    public Button hippocampeView;
    @FXML
    public Button gciView;
    @FXML
    public Button chouetteView;
    @FXML
    public Button batracienView;

    public Index(Dashboard main) {
        if(main == null) {
            throw new IllegalArgumentException("Dashboard main cannot be null");
        }
        this.main = main;
    }
    
    @FXML public void initialize(){
        this.loutreView.setOnAction(event -> {
            this.viewEspece(EspeceObservee.LOUTRE);
        });
        this.hippocampeView.setOnAction(event -> {
            this.viewEspece(EspeceObservee.HIPPOCAMPE);
        });
        this.gciView.setOnAction(event -> {
            this.viewEspece(EspeceObservee.GCI);
        });
        this.chouetteView.setOnAction(event -> {
            this.viewEspece(EspeceObservee.CHOUETTE);
        });
        this.batracienView.setOnAction(event -> {
            this.viewEspece(EspeceObservee.BATRACIEN);
        });
    }

    public void viewEspece(EspeceObservee espece) {
        this.main.viewEspece(espece);
    }
}
