package modele.bdd.table;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import controleur.dashboard.InsertFailedException;
import modele.bdd.DatabaseEnum;
import modele.donnee.Chouette;
import modele.donnee.Lieu;
import modele.donnee.ObsChouette;
import modele.donnee.Observateur;
import modele.donnee.Observation;
import modele.donnee.TypeObservation;

public class ObsChouetteTable extends Table {
    private ObservateurTable observateurTable;
    
    public ObsChouetteTable(){
        super();
        this.observateurTable = new ObservateurTable(this.databaseEnum);
    }

    public ObsChouetteTable(DatabaseEnum databaseEnum) {
        super(databaseEnum);
        this.observateurTable = new ObservateurTable(this.databaseEnum);
    }
    
    public ArrayList<ObsChouette> getAllObsChouette() throws SQLException {
        ArrayList<ObsChouette> ret = new ArrayList<ObsChouette>(0);
        try (PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM Obs_Chouette, Observation WHERE idObs = numObs;")) {
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


    /**
     * Retourne toutes les observations pour la chouette donnée
     * 
     * @param chouette la chouette dont on veut les observations
     * @return la liste des observations
     */
    public ArrayList<ObsChouette> getObsForChouette(Chouette chouette) throws SQLException {
        ArrayList<ObsChouette> ret = new ArrayList<ObsChouette>(0);
        try (PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM Obs_Chouette, Observation WHERE idObs = numObs AND leNumIndividu = ?;")) {
            stmt.setString(1, chouette.getIdChouette());
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

    public ObsChouette parseFromResultSet(ResultSet rs) throws SQLException {
        ObsChouette ret = null;
        String typeObservation = rs.getString("typeObs").toLowerCase();
        TypeObservation type = null;
        switch (typeObservation) {
            case "sonore et visuel":
                type = TypeObservation.SONORE_VISUELLE;
                break;
            case "sonore":
                type = TypeObservation.SONORE;
                break;
            case "visuel":
                type = TypeObservation.VISUELLE;
                break;
        }
        ArrayList<Observateur> observateurs = new ArrayList<Observateur>(0);
        ret = new ObsChouette(
                rs.getInt("idObs"),
                rs.getDate("dateObs"),
                rs.getTime("heureObs"),
                new Lieu(rs.getDouble("lieu_Lambert_X"), rs.getDouble("lieu_Lambert_Y"), true),
                observateurs,
                type,
                rs.getString("leNumIndividu"));
        ret.setLesObservateurs(this.observateurTable.getAllObservateurOfObservation(ret));
        return ret;
    }

    private String convertTypeObservation(TypeObservation type){
        String ret = null;
        switch (type) {
            case SONORE_VISUELLE:
                ret = "Sonore et visuel";
                break;
            case SONORE:
                ret = "Sonore";
                break;
            case VISUELLE:
                ret = "Visuel";
                break;
        }
        return ret;
    }

    public void insertObservation(Date date, Time time, Lieu lieu, ArrayList<Observateur> observateurs, TypeObservation type, Chouette chouette) throws InsertFailedException{
        try {
            new LieuTable().insert(lieu);
        } catch (SQLException e) {
            throw new InsertFailedException("Lieu insertion failed: " + e.getMessage());
        }
        int idObs = new ObservationTable().insert(date, time, lieu);
        try {
            this.insert(1, type, chouette, idObs);
        } catch (Exception e) {
            throw new InsertFailedException("ObsChouette insert failed: " + e.getMessage());
        }
        ObservateurTable observateurTable = new ObservateurTable(this.databaseEnum);
        for (Observateur observateur : observateurs) {
            observateurTable.addObservateurToObs(idObs, observateur.getIdObservateur());
        }
    }

    private void insert(int protocole, TypeObservation type, Chouette chouette, int idObs) throws SQLException {
        try (PreparedStatement stmt = this.connection.prepareStatement("INSERT INTO Obs_Chouette VALUES (?, ?, ?, ?);")) {
            stmt.setInt(1, protocole);
            stmt.setString(2, this.convertTypeObservation(type));
            stmt.setString(3, chouette.getIdChouette());
            stmt.setInt(4, idObs);
            stmt.executeUpdate();
        }
    }
}
