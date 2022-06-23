package modele.bdd.table;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import controleur.dashboard.InsertFailedException;
import modele.bdd.DatabaseEnum;
import modele.donnee.ContenuNid;
import modele.donnee.Lieu;
import modele.donnee.ObsGCI;
import modele.donnee.Observateur;
import modele.donnee.Observation;

public class ObsGCITable extends Table{
    private ObservateurTable observateurTable;

    public ObsGCITable(){
        super();
        this.observateurTable = new ObservateurTable(this.databaseEnum);
    }

    public ObsGCITable(DatabaseEnum databaseEnum) {
        super(databaseEnum);
        this.observateurTable = new ObservateurTable(this.databaseEnum);
    }

    public ArrayList<ObsGCI> getAllObsGCI() throws SQLException {
        ArrayList<ObsGCI> ret = new ArrayList<ObsGCI>(0);
        try (PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM Obs_GCI, Observation, " +
            "Nid_GCI WHERE idObs = obsG AND idNid = leNid;")) {
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

    public ObsGCI parseFromResultSet(ResultSet rs) throws SQLException {
        ObsGCI ret = null;
        String nature = rs.getString("nature").toLowerCase();
        //String raisonArret = rs.getString("raisonArretObservation").toLowerCase();
        
        ContenuNid natureNid = null;
        //RaisonArretObservation raisonArretObservation = null;
        switch (nature) {
            case "oeuf":
                natureNid = ContenuNid.OEUF;
                break;
            case "poussin":
                natureNid = ContenuNid.POUSSIN;
                break;
            case "nid":
                natureNid = ContenuNid.NID_SEUL;
                break;
        }
        /*
        switch (raisonArret) {
            case "envol":
                raisonArretObservation = RaisonArretObservation.ENVOL;
                break;
            case "inconnu":
                raisonArretObservation = RaisonArretObservation.INCONNU;
                break;
            case "maree":
                raisonArretObservation = RaisonArretObservation.MAREE;
                break;
            case "pietinement":
                raisonArretObservation = RaisonArretObservation.PIETINEMENT;
                break;
            case "predation":
                raisonArretObservation = RaisonArretObservation.PREDATION;
                break;
        }
        */
        ArrayList<Observateur> observateurs = this.observateurTable.getAllObservateurOfObservation(rs.getInt("idObs"));
        ret = new ObsGCI(
            rs.getInt("idObs"),
                rs.getDate("dateObs"),
                rs.getTime("heureObs"),
                new Lieu(rs.getDouble("lieu_Lambert_X"), rs.getDouble("lieu_Lambert_Y"), true),
                observateurs,
                natureNid,
                rs.getInt("nombre"),
                rs.getBoolean("presentMaisNonObs"));
        return ret;
    }

    public void insertObservation(Date date, Time time, Lieu lieu, ArrayList<Observateur> observateurs, ContenuNid nature, int nombre, boolean presentMaisNonObs, int leNid) throws InsertFailedException{
        try {
            new LieuTable().insert(lieu);
        } catch (SQLException e) {
            throw new InsertFailedException("Lieu insertion failed: " + e.getMessage());
        }
        int idObs = new ObservationTable().insert(date, time, lieu);
        try {
            this.insert(nature, nombre, presentMaisNonObs, leNid, idObs);
        } catch (Exception e) {
            throw new InsertFailedException("ObsGCI insert failed: " + e.getMessage());
        }
        ObservateurTable observateurTable = new ObservateurTable(this.databaseEnum);
        for (Observateur observateur : observateurs) {
            observateurTable.addObservateurToObs(idObs, observateur.getIdObservateur());
        }
    }

    private void insert(ContenuNid nature, int nombre, boolean presentMaisNonObs, int leNid, int idObs) throws SQLException {
        try (PreparedStatement stmt = this.connection.prepareStatement("INSERT INTO Obs_GCI VALUES (?, ?, ?, ?, ?);")) {
            stmt.setInt(1, idObs);
            stmt.setString(2, nature.toString());
            stmt.setInt(3, nombre);
            stmt.setBoolean(4, presentMaisNonObs);
            stmt.setInt(5, leNid);
            stmt.executeUpdate();
        }catch(NullPointerException e){
            throw new SQLException("Connection to database failed");
        }
    }
}
