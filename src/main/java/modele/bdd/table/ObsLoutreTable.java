package modele.bdd.table;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import controleur.dashboard.InsertFailedException;
import modele.bdd.DatabaseEnum;
import modele.donnee.IndiceLoutre;
import modele.donnee.Lieu;
import modele.donnee.ObsLoutre;
import modele.donnee.Observateur;
import modele.donnee.Observation;

public class ObsLoutreTable extends Table {
    private ObservateurTable observateurTable;

    public ObsLoutreTable(){
        super();
        this.observateurTable = new ObservateurTable(this.databaseEnum);
    }

    public ObsLoutreTable(DatabaseEnum databaseEnum) {
        super(databaseEnum);
        this.observateurTable = new ObservateurTable(this.databaseEnum);
    }

    public ArrayList<ObsLoutre> getAllObsLoutre() throws SQLException {
        ArrayList<ObsLoutre> ret = new ArrayList<ObsLoutre>(0);
        try (PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM Obs_Loutre, Observation WHERE idObs = ObsL;")) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                do {
                    ret.add(this.parseFromResultSet(rs));
                } while (rs.next());
            }
        } catch(NullPointerException e){
            throw new SQLException("Connection to database failed");
        }catch (Exception e) {
            throw e;
        }
        return ret;
    }

    public ArrayList<String> getAllCommune() throws SQLException{
        ArrayList<String> ret = new ArrayList<String>(0);
        try (PreparedStatement stmt = this.connection.prepareStatement("SELECT DISTINCT commune FROM Obs_Loutre WHERE commune IS NOT NULL AND commune != '';")) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                do {
                    ret.add(rs.getString("commune"));
                } while (rs.next());
            }
        } catch(NullPointerException e){
            throw new SQLException("Connection to database failed");
        }
        return ret;
    }

    public ArrayList<String> getAllLieuDit() throws SQLException {
        ArrayList<String> ret = new ArrayList<String>(0);
        try (PreparedStatement stmt = this.connection.prepareStatement("SELECT DISTINCT lieuDit FROM Obs_Loutre WHERE lieuDit IS NOT NULL AND lieuDit != '';")) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                do {
                    ret.add(rs.getString("lieuDit"));
                } while (rs.next());
            }
        } catch(NullPointerException e){
            throw new SQLException("Connection to database failed");
        }catch (Exception e) {
            throw e;
        }
        return ret;
    }

    public ObsLoutre parseFromResultSet(ResultSet rs) throws SQLException {
        ObsLoutre ret = null;
        String indice = rs.getString("indice").toLowerCase();
        IndiceLoutre indiceLoutre = IndiceLoutre.NON_PROSPECTION;
        switch (indice) {
            case "positif":
                indiceLoutre = IndiceLoutre.POSITIF;
                break;
            case "negatif":
                indiceLoutre = IndiceLoutre.NEGATIF;
                break;
        }
        ArrayList<Observateur> observateurs = new ArrayList<Observateur>(0);
        ret = new ObsLoutre(
                rs.getInt("idObs"),
                rs.getDate("dateObs"),
                rs.getTime("heureObs"),
                new Lieu(rs.getDouble("lieu_Lambert_X"), rs.getDouble("lieu_Lambert_Y"), true),
                observateurs,
                indiceLoutre);
        ret.setLesObservateurs(this.observateurTable.getAllObservateurOfObservation(ret));
        return ret;
    }
    
    public void insertObservation(Date date, Time time, Lieu lieu, ArrayList<Observateur> observateurs, String commune, String lieuDit, IndiceLoutre indice) throws InsertFailedException{
        try {
            new LieuTable().insert(lieu);
        } catch (SQLException e) {
            throw new InsertFailedException("Lieu insertion failed: " + e.getMessage());
        }
        ObservationTable observationTable = new ObservationTable(this.databaseEnum);
        int idObs = observationTable.insert(date, time, lieu);
        try {
            this.insert(commune, lieuDit, indice, idObs);
        } catch (Exception e) {
            throw new InsertFailedException("ObsLoutre insert failed: " + e.getMessage());
        }
        observationTable.addObservateurToObs(idObs, observateurs);
    }

    private void insert(String commune, String lieuDit, IndiceLoutre indice, int idObs) throws SQLException {
        try (PreparedStatement stmt = this.connection.prepareStatement("INSERT INTO Obs_Loutre VALUES (?, ?, ?, ?);")) {
            stmt.setInt(1, idObs);
            stmt.setString(2, commune);
            stmt.setString(3, lieuDit);
            stmt.setString(4, parseIndiceLoutre(indice));
            stmt.executeUpdate();
        }
    }

    public static String parseIndiceLoutre(IndiceLoutre indice){
        String ret = "";
        switch (indice) {
            case POSITIF:
                ret = "Positif";
                break;
            case NEGATIF:
                ret = "Negatif";
                break;
            case NON_PROSPECTION:
                ret = "Non prospection";
                break;
        }
        return ret;
    }
}
