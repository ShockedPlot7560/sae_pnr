package controleur.dashboard;

import java.sql.SQLException;
import java.util.ArrayList;

import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.Marker;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import modele.bdd.table.*;
import modele.donnee.*;

public class TabViewEspece {
    private CarteAnalyse main;
    private EspeceObservee espece;

    @FXML
    public CheckBox checkBox;
    @FXML
    public Tab tab;
    @FXML
    public TableView table;
    @FXML
    public AnchorPane anchorPane;

    private ChouetteThreadAnalyse chouetteThread;
    private GCIThreadAnalyse gciThread;
    private BatracienThreadAnalyse batracienThread;
    private LoutreThreadAnalyse loutreThread;
    private HippocampeThreadAnalyse hippocampeThread;

    public ArrayList<Marker> markers;

    public boolean generatedTable = false;

    public TabViewEspece(CarteAnalyse main, EspeceObservee espece) {
        this.main = main;
        this.espece = espece;
        this.markers = new ArrayList<>();
    }

    public String getColor() {
        switch (espece) {
            case BATRACIEN:
                return "#aed6f1";
            case LOUTRE:
                return " #76d7c4";
            case GCI:
                return " #f1948a";
            case HIPPOCAMPE:
                return " #f7dc6f";
            case CHOUETTE:
                return " #bb8fce";
            default:
                return "#aaa";
        }
    }

    @SuppressWarnings("unchecked")
    @FXML
    public void initialize() {
        if (main.especesPrinted.contains(espece)) {
            checkBox.setSelected(true);
        }
        this.tab.setStyle("-fx-background-color: " + this.getColor());
        this.tab.setText(espece.toString().toLowerCase());
        this.checkBox.setOnAction(event -> {
            System.out.println("Checkbox clicked: " + checkBox.isSelected());
            if (!checkBox.isSelected()) {
                this.unload();
            } else {
                this.load();
            }
        });
        this.anchorPane.setStyle("-fx-background-color: " + this.getColor());

        TableColumn idColumn = new TableColumn<>("#");
        idColumn.setCellValueFactory(new Callback<CellDataFeatures<Observation, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Observation, String> param) {
                return new ReadOnlyObjectWrapper<String>(String.valueOf(param.getValue().idObs()));
            }
        });
        table.getColumns().add(idColumn);
        TableColumn date = new TableColumn<>("date");
        date.setCellValueFactory(new Callback<CellDataFeatures<Observation, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Observation, String> param) {
                return new ReadOnlyObjectWrapper<String>(
                        String.valueOf(((Observation) param.getValue()).dateObs() == null ? ""
                                : ((Observation) param.getValue()).dateObs().toString()));
            }
        });
        table.getColumns().add(date);
        TableColumn heure = new TableColumn<>("heure");
        heure.setCellValueFactory(new Callback<CellDataFeatures<Observation, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Observation, String> param) {
                return new ReadOnlyObjectWrapper<String>(
                        String.valueOf(((Observation) param.getValue()).heureObs() == null ? ""
                                : ((Observation) param.getValue()).heureObs().toString()));
            }
        });
        table.getColumns().add(heure);
        TableColumn lambertX = new TableColumn<>("lambert_X");
        lambertX.setCellValueFactory(new Callback<CellDataFeatures<Observation, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Observation, String> param) {
                return new ReadOnlyObjectWrapper<String>(
                        String.valueOf(((Observation) param.getValue()).lieuObs().getxCoord()));
            }
        });
        table.getColumns().add(lambertX);
        TableColumn lambertY = new TableColumn<>("lambert_Y");
        lambertY.setCellValueFactory(new Callback<CellDataFeatures<Observation, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Observation, String> param) {
                return new ReadOnlyObjectWrapper<String>(
                        String.valueOf(((Observation) param.getValue()).lieuObs().getyCoord()));
            }
        });
        table.getColumns().add(lambertY);
        TableColumn observateurs = new TableColumn<>("observateurs");
        observateurs
                .setCellValueFactory(new Callback<CellDataFeatures<Observation, String>, ObservableValue<String>>() {
                    public String observateurToString(ArrayList<Observateur> observateurs) {
                        String observateursString = "";

                        for (int i = 0; i < observateurs.size(); i++) {
                            observateursString += observateurs.get(i).toString();

                            if (i != observateurs.size() - 1) {
                                observateursString += ", ";
                            }
                        }
                        return observateursString;
                    }

                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Observation, String> param) {
                        return new ReadOnlyObjectWrapper<String>(
                                observateurToString(((Observation) param.getValue()).lesObservateurs()));
                    }
                });
        table.getColumns().add(observateurs);

        this.load();
    }

    public void unload() {
        this.checkBox.setSelected(false);
        for (Marker marker : markers) {
            this.main.map.removeMarker(marker);
        }
        try {
            switch (espece) {
                case CHOUETTE:
                    if (chouetteThread != null) {
                        chouetteThread.stop();
                        chouetteThread.interrupt();
                        chouetteThread = null;
                    }
                    table.setItems(null);
                    table.setPlaceholder(new Label("Aucune observation"));
                    break;
                case HIPPOCAMPE:
                    if (hippocampeThread != null) {
                        hippocampeThread.stop();
                        hippocampeThread.interrupt();
                        hippocampeThread = null;
                    }
                    table.setItems(null);
                    table.setPlaceholder(new Label("Aucune observation"));
                    break;
                case GCI:
                    if (gciThread != null) {
                        gciThread.stop();
                        gciThread.interrupt();
                        gciThread = null;
                    }
                    table.setItems(null);
                    table.setPlaceholder(new Label("Aucune observation"));
                    break;
                case LOUTRE:
                    if (loutreThread != null) {
                        loutreThread.stop();
                        loutreThread.interrupt();
                        loutreThread = null;
                    }
                    table.setItems(null);
                    table.setPlaceholder(new Label("Aucune observation"));
                    break;
                case BATRACIEN:
                    if (batracienThread != null) {
                        batracienThread.stop();
                        batracienThread.interrupt();
                        batracienThread = null;
                    }
                    table.setItems(null);
                    table.setPlaceholder(new Label("Aucune observation"));
                    break;
            }
        } catch (Exception e) {
            System.out.println("ThreadDeath");
        }
    }

    public void load() {
        switch (espece) {
            case CHOUETTE:
                if (chouetteThread == null) {
                    chouetteThread = new ChouetteThreadAnalyse(main, this);
                }
                switch (chouetteThread.getState()) {
                    case NEW:
                        chouetteThread.start();
                        break;
                    case TERMINATED:
                        chouetteThread = new ChouetteThreadAnalyse(main, this);
                        chouetteThread.start();
                        break;
                    default:
                        break;
                }
                break;
            case HIPPOCAMPE:
                if (hippocampeThread == null) {
                    hippocampeThread = new HippocampeThreadAnalyse(main, this);
                }
                switch (hippocampeThread.getState()) {
                    case NEW:
                        hippocampeThread.start();
                        break;
                    case TERMINATED:
                        hippocampeThread = new HippocampeThreadAnalyse(main, this);
                        hippocampeThread.start();
                        break;
                    default:
                        break;
                }
                break;
            case GCI:
                if (gciThread == null) {
                    gciThread = new GCIThreadAnalyse(main, this);
                }
                switch (gciThread.getState()) {
                    case NEW:
                        gciThread.start();
                        break;
                    case TERMINATED:
                        gciThread = new GCIThreadAnalyse(main, this);
                        gciThread.start();
                        break;
                    default:
                        break;
                }
                break;
            case LOUTRE:
                if (loutreThread == null) {
                    loutreThread = new LoutreThreadAnalyse(main, this);
                }
                switch (loutreThread.getState()) {
                    case NEW:
                        loutreThread.start();
                        break;
                    case TERMINATED:
                        loutreThread = new LoutreThreadAnalyse(main, this);
                        loutreThread.start();
                        break;
                    default:
                        break;
                }
                break;
            case BATRACIEN:
                if (batracienThread == null) {
                    batracienThread = new BatracienThreadAnalyse(main, this);
                }
                switch (batracienThread.getState()) {
                    case NEW:
                        batracienThread.start();
                        break;
                    case TERMINATED:
                        batracienThread = new BatracienThreadAnalyse(main, this);
                        batracienThread.start();
                        break;
                    default:
                        break;
                }
                break;
        }
    }
}

abstract class AnalyseThread extends Thread {
    public ArrayList<? extends Observation> list;

    protected CarteAnalyse carteAnalyse;
    protected TabViewEspece tab;

    public AnalyseThread(CarteAnalyse carteAnalyse, TabViewEspece tab) {
        this.carteAnalyse = carteAnalyse;
        this.tab = tab;
        this.list = new ArrayList<>();
    }

    abstract protected EspeceObservee getEspece();

    abstract protected ArrayList<? extends Observation> getObservations() throws SQLException;

    public void run() {
        System.out.println("AnalyseThread " + getEspece().toString() + " started");
        Platform.runLater(() -> {
            this.tab.table.setPlaceholder(new Label("Chargement en cours..."));
        });
        try {
            try {
                this.list = this.getObservations();
                Platform.runLater(() -> {
                    if(!this.tab.generatedTable){
                        this.loadTable(this.tab.table);
                        this.tab.generatedTable = true;
                    }
                    this.tab.table.setItems(FXCollections.observableArrayList(this.list));

                    for (Observation observation : this.list) {
                        Lieu lieu = new Lieu(observation.lieuObs());
                        lieu.setEstLambert(true);
                        lieu.toLatLong();
                        Marker marker = new Marker(getClass().getResource(
                                "/img/marker-" + this.getEspece().toString().toLowerCase() + ".png"), -11, -30)
                                .setPosition(new Coordinate(lieu.getyCoord(), lieu.getxCoord()).normalize())
                                .setVisible(true);
                        this.tab.markers.add(marker);
                        this.carteAnalyse.map.addMarker(marker);
                        marker.setVisible(true);
                    }

                    if (this.list.size() == 0) {
                        this.tab.table.setPlaceholder(new Label("Aucune observation"));
                    }

                    System.out.println("AnalyseThread " + getEspece().toString() + " finished");
                });
            } catch (SQLException e) {
                System.out.println(e.getCause());
                if (e.getCause().getClass().equals(java.lang.ThreadDeath.class)) {
                    this.list = new ArrayList<>();
                    Platform.runLater(() -> {
                        this.tab.table.setPlaceholder(new Label("Aucune observation"));
                    });
                    System.out.println("AnalyseThread " + getEspece().toString() + " stopped successfuly");
                } else {
                    throw e;
                }
            }
        } catch (Exception e) {
            this.list = new ArrayList<>();
            Platform.runLater(() -> {
                this.tab.table.setPlaceholder(new Label("Une erreur est survenue"));
            });
            System.out.println("AnalyseThread " + getEspece().toString() + " error");
            e.printStackTrace();
        }
    }

    public void loadTable(TableView table) {
        // To surcharge
    }
}

class ChouetteThreadAnalyse extends AnalyseThread {
    public ChouetteThreadAnalyse(CarteAnalyse carteAnalyse, TabViewEspece tab) {
        super(carteAnalyse, tab);
    }

    @Override
    protected EspeceObservee getEspece() {
        return EspeceObservee.CHOUETTE;
    }

    @Override
    protected ArrayList<? extends Observation> getObservations() throws SQLException {
        ArrayList<? extends Observation> list = new ArrayList<>();
        if (this.carteAnalyse.especesPrinted.contains(EspeceObservee.CHOUETTE)) {
            list = new ObsChouetteTable().getAllObsChouette();
        }
        return list;
    }

    @Override
    public void loadTable(TableView table) {
        TableColumn<ObsChouette, String> especeChouette = new TableColumn<>("Espece");
        especeChouette.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ObsChouette, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObsChouette, String> param) {
                        return new ReadOnlyObjectWrapper<>(
                                ((ObsChouette) param.getValue()).getTypeObservation() == null ? ""
                                        : ((ObsChouette) param.getValue()).getTypeObservation().toString());
                    }
                });
        table.getColumns().add(especeChouette);
        TableColumn<ObsChouette, String> individu = new TableColumn<>("Individu");
        individu.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ObsChouette, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObsChouette, String> param) {
                        return new ReadOnlyObjectWrapper<>(
                                ((ObsChouette) param.getValue()).getNumIndividu() == null ? ""
                                        : ((ObsChouette) param.getValue()).getNumIndividu());
                    }
                });
        table.getColumns().add(individu);
    }

};

class HippocampeThreadAnalyse extends AnalyseThread {
    public HippocampeThreadAnalyse(CarteAnalyse carteAnalyse, TabViewEspece tab) {
        super(carteAnalyse, tab);
    }

    @Override
    protected EspeceObservee getEspece() {
        return EspeceObservee.HIPPOCAMPE;
    }

    @Override
    protected ArrayList<? extends Observation> getObservations() throws SQLException {
        ArrayList<? extends Observation> list = new ArrayList<>();
        if (this.carteAnalyse.especesPrinted.contains(EspeceObservee.HIPPOCAMPE)) {
            list = new ObsHippocampeTable().getAllObsHippocampe();
        }
        return list;
    }

    @Override
    public void loadTable(TableView table) {
        TableColumn<ObsHippocampe, String> especeHippocampe = new TableColumn<>("Espece");
        especeHippocampe.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ObsHippocampe, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObsHippocampe, String> param) {
                        return new ReadOnlyObjectWrapper<>(
                                param.getValue().getEspece() == null ? "" : param.getValue().getEspece().toString());
                    }
                });
        table.getColumns().add(especeHippocampe);
        TableColumn<ObsHippocampe, String> typePecheHippocampe = new TableColumn<>("Type Peche");
        typePecheHippocampe.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ObsHippocampe, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObsHippocampe, String> param) {
                        return new ReadOnlyObjectWrapper<>(param.getValue().getTypePeche() == null ? ""
                                : param.getValue().getTypePeche().toString());
                    }
                });
        table.getColumns().add(typePecheHippocampe);

        // sexeHippocampe
        TableColumn<ObsHippocampe, String> sexeHippocampe = new TableColumn<>("Sexe");
        sexeHippocampe.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ObsHippocampe, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObsHippocampe, String> param) {
                        return new ReadOnlyObjectWrapper<>(
                                param.getValue().getSexe() == null ? "" : param.getValue().getSexe().toString());
                    }
                });
        table.getColumns().add(sexeHippocampe);
        // taille
        TableColumn<ObsHippocampe, String> tailleHippocampe = new TableColumn<>("Taille");
        tailleHippocampe.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ObsHippocampe, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObsHippocampe, String> param) {
                        return new ReadOnlyObjectWrapper<>(Double.valueOf(param.getValue().getTaille()).toString());
                    }
                });
        table.getColumns().add(tailleHippocampe);
        // temperature
        TableColumn<ObsHippocampe, String> temperatureHippocampe = new TableColumn<>("Temperature");
        temperatureHippocampe.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ObsHippocampe, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObsHippocampe, String> param) {
                        return new ReadOnlyObjectWrapper<>(
                                Double.valueOf(param.getValue().getTemperature()).toString());
                    }
                });
        table.getColumns().add(temperatureHippocampe);
    }
};

class GCIThreadAnalyse extends AnalyseThread {
    public GCIThreadAnalyse(CarteAnalyse carteAnalyse, TabViewEspece tab) {
        super(carteAnalyse, tab);
    }

    @Override
    protected EspeceObservee getEspece() {
        return EspeceObservee.GCI;
    }

    @Override
    protected ArrayList<? extends Observation> getObservations() throws SQLException {
        ArrayList<? extends Observation> list = new ArrayList<>();
        if (this.carteAnalyse.especesPrinted.contains(EspeceObservee.GCI)) {
            list = new ObsGCITable().getAllObsGCI();
        }
        return list;
    }

    @Override
    public void loadTable(TableView table) {
        TableColumn<ObsGCI, String> natureObs = new TableColumn<>("Nature Observation");
        natureObs.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ObsGCI, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObsGCI, String> param) {
                        return new ReadOnlyObjectWrapper<>(param.getValue().getNatureObs() == null ? ""
                                : param.getValue().getNatureObs().toString());
                    }
                });
        table.getColumns().add(natureObs);

        TableColumn<ObsGCI, Integer> nombreGCI = new TableColumn<>("Nombre");
        nombreGCI.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ObsGCI, Integer>, ObservableValue<Integer>>() {
                    @Override
                    public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ObsGCI, Integer> param) {
                        return new ReadOnlyObjectWrapper<>(param.getValue().getNombre());
                    }
                });
        table.getColumns().add(nombreGCI);
        TableColumn<ObsGCI, String> GCIpresentMaisPasObs = new TableColumn<>("Present mais pas obs");
        GCIpresentMaisPasObs.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ObsGCI, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObsGCI, String> param) {
                        return new ReadOnlyObjectWrapper<>(
                                Boolean.valueOf(param.getValue().getPresentMaisPasObs()).toString());
                    }
                });
        table.getColumns().add(GCIpresentMaisPasObs);
    }
};

class LoutreThreadAnalyse extends AnalyseThread {
    public LoutreThreadAnalyse(CarteAnalyse carteAnalyse, TabViewEspece tab) {
        super(carteAnalyse, tab);
    }

    @Override
    protected EspeceObservee getEspece() {
        return EspeceObservee.LOUTRE;
    }

    @Override
    protected ArrayList<? extends Observation> getObservations() throws SQLException {
        ArrayList<? extends Observation> list = new ArrayList<>();
        if (this.carteAnalyse.especesPrinted.contains(EspeceObservee.LOUTRE)) {
            list = new ObsLoutreTable().getAllObsLoutre();
        }
        return list;
    }

    @Override
    public void loadTable(TableView table) {
        TableColumn<ObsLoutre, String> indiceLoutre = new TableColumn<>("Indice");
        indiceLoutre.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ObsLoutre, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObsLoutre, String> param) {
                        return new ReadOnlyObjectWrapper<>(
                                param.getValue().getIndice() == null ? "" : param.getValue().getIndice().toString());
                    }
                });
        table.getColumns().add(indiceLoutre);
    }

};

class BatracienThreadAnalyse extends AnalyseThread {
    public BatracienThreadAnalyse(CarteAnalyse carteAnalyse, TabViewEspece tab) {
        super(carteAnalyse, tab);
    }

    @Override
    protected EspeceObservee getEspece() {
        return EspeceObservee.BATRACIEN;
    }

    @Override
    protected ArrayList<? extends Observation> getObservations() throws SQLException {
        ArrayList<? extends Observation> list = new ArrayList<>();
        if (this.carteAnalyse.especesPrinted.contains(EspeceObservee.BATRACIEN)) {
            list = new ObsBatracienTable().getAllObsBatracien();
        }
        return list;
    }

    @Override
    public void loadTable(TableView table) {
        TableColumn<ObsBatracien, String> nomBatracien = new TableColumn<>("Espèce");
        nomBatracien.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ObsBatracien, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObsBatracien, String> param) {
                        return new ReadOnlyObjectWrapper<>(param.getValue().getEspece().toString());
                    }
                });
        table.getColumns().add(nomBatracien);
        TableColumn<ObsBatracien, Integer> nombreAdultes = new TableColumn<>("Nombre Adultes");
        nombreAdultes.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ObsBatracien, Integer>, ObservableValue<Integer>>() {
                    @Override
                    public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ObsBatracien, Integer> param) {
                        return new ReadOnlyObjectWrapper<>(param.getValue().getNombreAdultes());
                    }
                });
        table.getColumns().add(nombreAdultes);
        TableColumn<ObsBatracien, Integer> nombreAmplexus = new TableColumn<>("Nombre Amplexus");
        nombreAmplexus.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ObsBatracien, Integer>, ObservableValue<Integer>>() {
                    @Override
                    public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ObsBatracien, Integer> param) {
                        return new ReadOnlyObjectWrapper<>(param.getValue().getNombreAmplexus());
                    }
                });
        table.getColumns().add(nombreAmplexus);
        TableColumn<ObsBatracien, Integer> nombreTetard = new TableColumn<>("Nombre Tetards");
        nombreTetard.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ObsBatracien, Integer>, ObservableValue<Integer>>() {
                    @Override
                    public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ObsBatracien, Integer> param) {
                        return new ReadOnlyObjectWrapper<>(param.getValue().getNombreTetard());
                    }
                });
        table.getColumns().add(nombreTetard);
        TableColumn<ObsBatracien, Integer> nombrePonte = new TableColumn<>("Nombre Pontes");
        nombrePonte.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ObsBatracien, Integer>, ObservableValue<Integer>>() {
                    @Override
                    public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ObsBatracien, Integer> param) {
                        return new ReadOnlyObjectWrapper<>(param.getValue().getNombrePonte());
                    }
                });
        table.getColumns().add(nombrePonte);
        TableColumn<ObsBatracien, Double> temperature = new TableColumn<>("Temperature");
        temperature.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ObsBatracien, Double>, ObservableValue<Double>>() {
                    @Override
                    public ObservableValue<Double> call(TableColumn.CellDataFeatures<ObsBatracien, Double> param) {
                        return new ReadOnlyObjectWrapper<>(Double.valueOf(param.getValue().getTemperature()));
                    }
                });
        table.getColumns().add(temperature);
        TableColumn<ObsBatracien, String> ciel = new TableColumn<>("Meteo Ciel");
        ciel.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ObsBatracien, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObsBatracien, String> param) {
                        return new ReadOnlyObjectWrapper<>(
                                param.getValue().getCiel() == null ? "" : param.getValue().getCiel().toString());
                    }
                });
        table.getColumns().add(ciel);
        TableColumn<ObsBatracien, String> meteoTemp = new TableColumn<>("Meteo Temp");
        meteoTemp.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ObsBatracien, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObsBatracien, String> param) {
                        return new ReadOnlyObjectWrapper<>(
                                param.getValue().getTemp() == null ? "" : param.getValue().getTemp().toString());
                    }
                });
        table.getColumns().add(meteoTemp);
        TableColumn<ObsBatracien, String> meteoVent = new TableColumn<>("Meteo Vent");
        meteoVent.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ObsBatracien, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObsBatracien, String> param) {
                        return new ReadOnlyObjectWrapper<>(
                                param.getValue().getVent() == null ? "" : param.getValue().getVent().toString());
                    }
                });
        table.getColumns().add(meteoVent);
        TableColumn<ObsBatracien, String> meteoPluie = new TableColumn<>("Meteo Pluie");
        meteoPluie.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ObsBatracien, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObsBatracien, String> param) {
                        return new ReadOnlyObjectWrapper<>(
                                param.getValue().getPluie() == null ? "" : param.getValue().getPluie().toString());
                    }
                });
        table.getColumns().add(meteoPluie);
        TableColumn<ObsBatracien, String> lieuVege = new TableColumn<>("Lieu Vegetation");
        lieuVege.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ObsBatracien, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObsBatracien, String> param) {
                        return new ReadOnlyObjectWrapper<>(param.getValue().getLieuVege() == null ? ""
                                : param.getValue().getLieuVege().toString());
                    }
                });
        table.getColumns().add(lieuVege);
        TableColumn<ObsBatracien, String> zoneHumide = new TableColumn<>("Zone Humide");
        zoneHumide.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ObsBatracien, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObsBatracien, String> param) {
                        return new ReadOnlyObjectWrapper<>(
                                param.getValue().getZone() == null ? "" : param.getValue().getZone().toString());
                    }
                });
        table.getColumns().add(zoneHumide);
    }
};