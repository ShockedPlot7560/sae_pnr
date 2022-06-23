package controleur.dashboard;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import controleur.App;
import controleur.Dashboard;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import modele.bdd.table.*;
import modele.donnee.*;

public class ExportData {

    private Dashboard mainControleur;

    @FXML
    private Button exportButton;

    @FXML
    private ProgressBar exportProgressBar;

    public ExportData(Dashboard mainControleur) {
        if (mainControleur == null) {
            throw new IllegalArgumentException("Dashboard cannot be null");
        }
        this.mainControleur = mainControleur;
    }

    public void exportData() {
        this.disableUI(true);
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel file (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(App.stage);

        if (file == null) {
            this.disableUI(false);
            return;
        }

        Thread bgThread = new Thread() {
            @Override
            public void run() {
                try {
                    exportBackground(file);
                } catch (Exception e) {
                    System.err.println("Erreur pendant l'export des données :");
                    e.printStackTrace();
                } finally {
                    disableUI(false);
                }
            }
        };
        bgThread.start();
    }

    public void exportBackground(File file) throws InterruptedException {
        CountDownLatch tablesLatch = new CountDownLatch(5);

        ChouetteThread chouetteThread = new ChouetteThread(tablesLatch, this);
        chouetteThread.start();

        HippocampeThread hippocampeThread = new HippocampeThread(tablesLatch, this);
        hippocampeThread.start();

        GCIThread gciThread = new GCIThread(tablesLatch, this);
        gciThread.start();

        LoutreThread loutreThread = new LoutreThread(tablesLatch, this);
        loutreThread.start();

        BatracienThread batracienThread = new BatracienThread(tablesLatch, this);
        batracienThread.start();

        tablesLatch.await();

        System.out.println("DB Pulled");
        Workbook workbook = new XSSFWorkbook();
        CreationHelper createHelper = workbook.getCreationHelper();

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);

        this.createSheet(workbook, headerFont, createHelper, "Chouette", new String[] { "type_Observation" },
                chouetteThread.list);
        this.createSheet(workbook, headerFont, createHelper, "Hippocampe", new String[] { "taille", "temperature",
                "type_Peche", "espece", "sexe" }, hippocampeThread.list);
        this.createSheet(workbook, headerFont, createHelper, "GCI", new String[] {
                "contenu_Nid", "nombre" },
                gciThread.list);
        this.createSheet(workbook, headerFont, createHelper, "Loutre", new String[] {
                "indice" },
                loutreThread.list);
        this.createSheet(workbook, headerFont, createHelper, "Batracien", new String[] { "nb_Adulte", "nb_Amplexus",
                "nb_Tetard", "nb_Ponte", "temperature", "ciel", "climat", "vent", "pluie",
                "nature", "vegetation",
                "profondeur",
                "surface", "type_Mare", "pente", "ouverture" }, batracienThread.list);

        if (file != null) {
            try {
                FileOutputStream fileOut = new FileOutputStream(file);
                workbook.write(fileOut);
                fileOut.close();
                workbook.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.disableUI(false);

        Platform.runLater(() -> {
            Alert success = new Alert(AlertType.INFORMATION);
            success.setTitle("Exportation des données");

            success.setHeaderText(null);
            success.setContentText("Les données ont été exportées avec succès");
            success.showAndWait();
        });
    }

    public void createSheet(Workbook workbook, Font headerFont, CreationHelper createHelper, String name,
            String[] header,
            ArrayList<Observation> listObservation) {

        Sheet sheet = workbook.createSheet(name);

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        String[] fixHeader = { "id", "date", "heure", "coord_X", "coord_Y", "observateurs" };
        int colLen = fixHeader.length + header.length;
        String[] colName = new String[colLen];
        for (int i = 0; i < colLen; i++) {
            if (i < fixHeader.length) {
                colName[i] = fixHeader[i];
            } else {
                colName[i] = header[i - fixHeader.length];
            }
        }

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < colName.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(colName[i]);
            cell.setCellStyle(headerCellStyle);
        }

        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));
        CellStyle timeCellStyle = workbook.createCellStyle();
        timeCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("HH:mm:ss"));

        int rowNum = 1;
        for (Observation obs : listObservation) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(obs.idObs());

            if (obs.dateObs() == null) {
                row.createCell(1).setCellValue("");
            } else {
                Cell cell = row.createCell(1);
                cell.setCellValue(obs.dateObs().toString());
                cell.setCellStyle(dateCellStyle);
            }

            if (obs.heureObs() == null) {
                row.createCell(2).setCellValue("");
            } else {
                Cell time = row.createCell(2);
                time.setCellValue(obs.heureObs().toString());
                time.setCellStyle(timeCellStyle);
            }

            row.createCell(3).setCellValue(obs.lieuObs().getxCoord());

            row.createCell(4).setCellValue(obs.lieuObs().getyCoord());

            row.createCell(5)
                    .setCellValue(this.observateurToString(obs.lesObservateurs()));

            if (obs instanceof ObsChouette) {
                row.createCell(6).setCellValue(((ObsChouette) obs).getTypeObservation().toString());
            } else if (obs instanceof ObsHippocampe) {
                row.createCell(6).setCellValue(((ObsHippocampe) obs).getTaille());
                row.createCell(7).setCellValue(((ObsHippocampe) obs).getTemperature());
                row.createCell(8).setCellValue(((ObsHippocampe) obs).getTypePeche().toString());
                if (((ObsHippocampe) obs).getEspece() == null) {
                    row.createCell(9).setCellValue("");
                } else {
                    row.createCell(9).setCellValue(((ObsHippocampe) obs).getEspece().toString());
                }
                row.createCell(10).setCellValue(((ObsHippocampe) obs).getSexe().toString());
            } else if (obs instanceof ObsGCI) {
                row.createCell(6).setCellValue(((ObsGCI) obs).getNatureObs().toString());
                row.createCell(7).setCellValue(((ObsGCI) obs).getNombre());
            } else if (obs instanceof ObsLoutre) {
                row.createCell(6).setCellValue(((ObsLoutre) obs).getIndice().toString());
            } else if (obs instanceof ObsBatracien) {
                row.createCell(6).setCellValue(((ObsBatracien) obs).getNombreAdultes());
                row.createCell(7).setCellValue(((ObsBatracien) obs).getNombreAmplexus());
                row.createCell(8).setCellValue(((ObsBatracien) obs).getNombreTetard());
                row.createCell(9).setCellValue(((ObsBatracien) obs).getNombrePonte());
                row.createCell(10).setCellValue(((ObsBatracien) obs).getTemperature());
                if (((ObsBatracien) obs).getCiel() == null) {
                    row.createCell(11).setCellValue("");
                } else {
                    row.createCell(11).setCellValue(((ObsBatracien) obs).getCiel().toString());
                }
                if (((ObsBatracien) obs).getTemp() == null) {
                    row.createCell(12).setCellValue("");
                } else {
                    row.createCell(12).setCellValue(((ObsBatracien) obs).getTemp().toString());
                }
                if (((ObsBatracien) obs).getVent() == null) {
                    row.createCell(13).setCellValue("");
                } else {
                    row.createCell(13).setCellValue(((ObsBatracien) obs).getVent().toString());
                }
                if (((ObsBatracien) obs).getPluie() == null) {
                    row.createCell(14).setCellValue("");
                } else {
                    row.createCell(14).setCellValue(((ObsBatracien) obs).getPluie().toString());
                }
                if (((ObsBatracien) obs).getLieuVege() == null
                        || ((ObsBatracien) obs).getLieuVege().getListeVegetation().isEmpty() ||
                        ((ObsBatracien) obs).getLieuVege().getListeVegetation().get(0) == null) {
                    row.createCell(15).setCellValue("");
                    row.createCell(16).setCellValue("");
                } else {
                    if (((ObsBatracien) obs).getLieuVege().getListeVegetation().get(0).getNatureVege() == null) {
                        row.createCell(15).setCellValue("");
                    } else {
                        row.createCell(15).setCellValue(((ObsBatracien) obs).getLieuVege().getListeVegetation().get(0)
                                .getNatureVege().toString());
                    }
                    if (((ObsBatracien) obs).getLieuVege().getListeVegetation().get(0).getVegetation() == null) {
                        row.createCell(16).setCellValue("");
                    } else {
                        row.createCell(16).setCellValue(((ObsBatracien) obs).getLieuVege().getListeVegetation().get(0)
                                .getVegetation().toString());
                    }
                }
                if (((ObsBatracien) obs).getZone() == null) {
                    row.createCell(17).setCellValue("");
                    row.createCell(18).setCellValue("");
                    row.createCell(19).setCellValue("");
                    row.createCell(20).setCellValue("");
                    row.createCell(21).setCellValue("");
                } else {
                    row.createCell(17).setCellValue(((ObsBatracien) obs).getZone().getProfondeur());
                    row.createCell(18).setCellValue(((ObsBatracien) obs).getZone().getSurface());
                    if (((ObsBatracien) obs).getZone().getTypeMare() == null) {
                        row.createCell(19).setCellValue("");
                    } else {
                        row.createCell(19).setCellValue(((ObsBatracien) obs).getZone().getTypeMare().toString());
                    }
                    if (((ObsBatracien) obs).getZone().getPente() == null) {
                        row.createCell(20).setCellValue("");
                    } else {
                        row.createCell(20).setCellValue(((ObsBatracien) obs).getZone().getPente().toString());
                    }
                    if (((ObsBatracien) obs).getZone().getOuverture() == null) {
                        row.createCell(21).setCellValue("");
                    } else {
                        row.createCell(21).setCellValue(((ObsBatracien) obs).getZone().getOuverture().toString());
                    }
                }
            }
        }

        for (int i = 0; i < colName.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

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

    public void increaseProgressBar(int i) {
        this.exportProgressBar.setProgress(this.exportProgressBar.getProgress() + (double) i / 100);
    }

    private void disableUI(boolean disable) {
        this.exportButton.setDisable(disable);
        this.mainControleur.newMeasureButton.setDisable(disable);
        this.mainControleur.viewEspeceButton.setDisable(disable);
        this.mainControleur.carteAnalyseButton.setDisable(disable);
        this.mainControleur.exportDataButton.setDisable(disable);
        this.mainControleur.gestionUserButton.setDisable(disable);
        this.mainControleur.disconnectButton.setDisable(disable);
        this.exportProgressBar.setProgress(0);
    }
}

abstract class TableThread extends Thread {

    public ArrayList<Observation> list;

    private CountDownLatch tablesLatch;
    private ExportData exportData;

    public TableThread(CountDownLatch tablesLatch, ExportData exportData) {
        this.tablesLatch = tablesLatch;
        this.exportData = exportData;
    }

    @Override
    public void run() {
        System.out.println("Pulling table...");
        this.list = new ArrayList<Observation>();

        try {
            this.list.addAll(this.getAllObs());
            this.exportData.increaseProgressBar(20);
        } catch (SQLException e) {
            this.list = null;
            System.err.println("Erreur dans la récupération de la table :");
            e.printStackTrace();
        }

        tablesLatch.countDown();
    }

    abstract ArrayList<? extends Observation> getAllObs() throws SQLException;

}

class ChouetteThread extends TableThread {

    public ChouetteThread(CountDownLatch tablesLatch, ExportData exportData) {
        super(tablesLatch, exportData);
    }

    @Override
    ArrayList<? extends Observation> getAllObs() throws SQLException {
        return new ObsChouetteTable().getAllObsChouette();
    }

};

class HippocampeThread extends TableThread {

    public HippocampeThread(CountDownLatch tablesLatch, ExportData exportData) {
        super(tablesLatch, exportData);
    }

    @Override
    ArrayList<? extends Observation> getAllObs() throws SQLException {
        return new ObsHippocampeTable().getAllObsHippocampe();
    }

};

class GCIThread extends TableThread {

    public GCIThread(CountDownLatch tablesLatch, ExportData exportData) {
        super(tablesLatch, exportData);
    }

    @Override
    ArrayList<? extends Observation> getAllObs() throws SQLException {
        return new ObsGCITable().getAllObsGCI();
    }

};

class LoutreThread extends TableThread {

    public LoutreThread(CountDownLatch tablesLatch, ExportData exportData) {
        super(tablesLatch, exportData);
    }

    @Override
    ArrayList<? extends Observation> getAllObs() throws SQLException {
        return new ObsLoutreTable().getAllObsLoutre();
    }

};

class BatracienThread extends TableThread {

    public BatracienThread(CountDownLatch tablesLatch, ExportData exportData) {
        super(tablesLatch, exportData);
    }

    @Override
    ArrayList<? extends Observation> getAllObs() throws SQLException {
        return new ObsBatracienTable().getAllObsBatracien();
    }

};
