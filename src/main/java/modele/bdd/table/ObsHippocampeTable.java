package modele.bdd.table;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import controleur.dashboard.InsertFailedException;
import modele.bdd.DatabaseEnum;
import modele.donnee.EspeceHippocampe;
import modele.donnee.Lieu;
import modele.donnee.ObsHippocampe;
import modele.donnee.Observateur;
import modele.donnee.Observation;
import modele.donnee.Peche;
import modele.donnee.Sexe;

public class ObsHippocampeTable extends Table {
    private ObservateurTable observateurTable;
    
    public ObsHippocampeTable(){
        super();
        this.observateurTable = new ObservateurTable(this.databaseEnum);
    }

    public ObsHippocampeTable(DatabaseEnum databaseEnum) {
        super(databaseEnum);
        this.observateurTable = new ObservateurTable(this.databaseEnum);
    }

    public void insertObservation(Date date, Time time, Lieu lieu, ArrayList<Observateur> observateurs, EspeceHippocampe espece, Sexe sexe, int temperature, Peche typePeche, double taille, boolean gestant) throws InsertFailedException{
        try {
            new LieuTable().insert(lieu);
        } catch (SQLException e) {
            throw new InsertFailedException("Lieu insertion failed: " + e.getMessage());
        }
        int idObs = new ObservationTable().insert(date, time, lieu);
        try {
            this.insert(idObs, espece, sexe, temperature, typePeche, taille, gestant);
        } catch (Exception e) {
            throw new InsertFailedException("ObsHippocampe insert failed: " + e.getMessage());
        }
        for (Observateur observateur : observateurs) {
            this.observateurTable.addObservateurToObs(idObs, observateur.getIdObservateur());
        }
    }

    private void insert(int idObs, EspeceHippocampe espece, Sexe sexe, int temperature, Peche typePeche, double taille, boolean gestant) throws SQLException {
        try (PreparedStatement stmt = this.connection.prepareStatement("INSERT INTO Obs_Hippocampe VALUES (?, ?, ?, ?, ?, ?, ?);")) {
            stmt.setInt(1, idObs);
            stmt.setString(2, parseEspeceHippocampe(espece));
            stmt.setString(3, sexe.toString().toLowerCase());
            stmt.setInt(4, temperature);
            stmt.setString(5, parseTypePeche(typePeche));
            stmt.setDouble(6, taille);
            stmt.setBoolean(7, gestant);
            stmt.executeUpdate();
        }
    }

    public ArrayList<ObsHippocampe> getAllObsHippocampe() throws SQLException {
        ArrayList<ObsHippocampe> ret = new ArrayList<ObsHippocampe>(0);
        try (PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM Obs_Hippocampe, Observation WHERE idObs = obsH;")) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                do {
                    ret.add(this.parseFromResultSet(rs));
                } while (rs.next());
            }
        } catch(NullPointerException e){
            throw new SQLException("Connection to database failed");
        } catch (Exception e) {
            throw e;
        }
        return ret;
    }

    public ObsHippocampe parseFromResultSet(ResultSet rs) throws SQLException {
        ObsHippocampe ret = null;
        String espece = rs.getString("espece");
        String sexe = rs.getString("sexe").toLowerCase();
        String peche = rs.getString("typePeche");
        EspeceHippocampe especeHippocampe = null;
        Sexe sexeHippocampe = Sexe.INCONNU;
        Peche typePeche = Peche.NON_RENSEIGNE;
        if (espece != null) {
            switch(espece.toLowerCase()) { 
                case "syngnathus acus":
                    especeHippocampe = EspeceHippocampe.SYNGNATHUS_ACUS;
                    break;
                case "hippocampus guttulatus":
                    especeHippocampe = EspeceHippocampe.HIPPOCAMPUS_GUTTULATUS;
                    break;
                case "hippocampus hippocampus":
                    especeHippocampe = EspeceHippocampe.HIPPOCAMPUS_HIPPOCAMPUS;
                    break;
                case "enterurus aequoreus":
                    especeHippocampe = EspeceHippocampe.ENTERURUS_AEQUOREUS;
                    break;
            }
        }
        if (sexe != null) {
            switch(sexe.toLowerCase()) { 
                case "male":
                    sexeHippocampe = Sexe.MALE;
                    break;
                case "femelle":
                    sexeHippocampe = Sexe.FEMELLE;
                    break;
            }
        }
        if (peche != null) {
            switch(peche.toLowerCase()) {
                case "casiercrevettes":
                    typePeche = Peche.CASIER_CREVETTES;
                    break;
                case "casiermorgates":
                    typePeche = Peche.CASIER_MORGATES;
                    break;
                case "petitfilet":
                    typePeche = Peche.PETIT_FILET;
                    break;
                case "verveuxanguilles":
                    typePeche = Peche.VERVEUX_ANGUILLES;
                    break;
            }
        }
        ArrayList<Observateur> observateurs = new ArrayList<Observateur>(0);
        ret = new ObsHippocampe(
            rs.getInt("idObs"),
            rs.getDate("dateObs"),
            rs.getTime("heureObs"),
            new Lieu(rs.getDouble("lieu_Lambert_X"), rs.getDouble("lieu_Lambert_Y"), true),
            observateurs,
            rs.getDouble("taille"),
            rs.getInt("temperatureEau"),
            typePeche,
            especeHippocampe,
            sexeHippocampe);
        ret.setLesObservateurs(this.observateurTable.getAllObservateurOfObservation(ret));
        ret.setEstGestant(rs.getBoolean("gestant"));
        return ret;
    }

    public static String parseEspeceHippocampe(EspeceHippocampe espece){
        String ret = "";
        switch(espece){
            case ENTERURUS_AEQUOREUS:
                ret = "Entelurus aequoreus";
            case HIPPOCAMPUS_GUTTULATUS:
                ret = "Hippocampus guttulatus";
            case HIPPOCAMPUS_HIPPOCAMPUS:
                ret = "Hippocampus Hippocampus";
            case SYNGNATHUS_ACUS:
                ret = "Syngnathus acus";
        }
        return ret;
    }

    public static String parseTypePeche(Peche typePeche){
        String ret = "";
        switch(typePeche){
            case CASIER_CREVETTES:
                ret = "casierCrevette";
            case CASIER_MORGATES:
                ret = "casierMorgates";
            case NON_RENSEIGNE:
                ret = null;
            case PETIT_FILET:
                ret = "petitFilet";
            case VERVEUX_ANGUILLES:
                ret = "verveuxAnguilles";
        }
        return ret;
    }
}
