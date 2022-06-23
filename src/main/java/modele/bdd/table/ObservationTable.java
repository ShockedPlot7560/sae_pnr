package modele.bdd.table;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import controleur.dashboard.InsertFailedException;
import modele.bdd.DatabaseEnum;
import modele.donnee.Lieu;
import modele.donnee.Observateur;
import modele.donnee.Observation;

public class ObservationTable extends Table {
    private ObservateurTable observateurTable;

    public ObservationTable(){
        super();
        this.observateurTable = new ObservateurTable(this.databaseEnum);
    }

    public ObservationTable(DatabaseEnum databaseEnum) {
        super(databaseEnum);
        this.observateurTable = new ObservateurTable(this.databaseEnum);
    }

    /**
     * @return Retourne l'id de l'observation si elle a bien été inséré, -1 sinon
     */
    public int insert(Date date, Time time, Lieu lieu) throws InsertFailedException{
        int ret = -1;
        try (PreparedStatement stmt = this.connection.prepareStatement("SELECT idObs FROM Observation ORDER BY idObs DESC LIMIT 1;");) {
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int id = rs.getInt("idObs") + 1;
            PreparedStatement stmt2 = this.connection.prepareStatement("INSERT INTO Observation(idObs, dateObs, heureObs, lieu_Lambert_X, lieu_Lambert_Y) VALUES (?, ?, ?, ?, ?);");
            stmt2.setInt(1, id);
            stmt2.setDate(2, date);
            stmt2.setTime(3, time);
            stmt2.setDouble(4, lieu.getxCoord());
            stmt2.setDouble(5, lieu.getyCoord());
            stmt2.executeUpdate();
            PreparedStatement stmt3 = this.connection.prepareStatement("SELECT idObs FROM Observation WHERE dateObs = ? AND heureObs = ? AND lieu_Lambert_X = ? AND lieu_Lambert_Y = ? ORDER BY idObs DESC LIMIT 1;");
            stmt3.setDate(1, date);
            stmt3.setTime(2, time);
            stmt3.setDouble(3, lieu.getxCoord());
            stmt3.setDouble(4, lieu.getyCoord());
            ResultSet rs2 = stmt3.executeQuery();
            rs2.next();
            ret = rs2.getInt(1);
        } catch (SQLException e) {
            throw new InsertFailedException("Observation insert failed: " + e.getMessage());
        }
        return ret;
    }

    public void addObservateurToObs(int idObs, ArrayList<Observateur> observateurs) throws InsertFailedException {
        for (Observateur observateur : observateurs) {
            this.addObservateurToObs(idObs, observateur);
        }
    }

    public void addObservateurToObs(int idObs, int idObservateur) throws InsertFailedException{
        this.observateurTable.addObservateurToObs(idObs, idObservateur);
    }

    public void addObservateurToObs(Observation obs, Observateur observateur) throws InsertFailedException{
        this.addObservateurToObs(obs.idObs(), observateur.getIdObservateur());
    }

    public void addObservateurToObs(int idObs, Observateur observateur) throws InsertFailedException{
        this.addObservateurToObs(idObs, observateur.getIdObservateur());
    }

    public void addObservateurToObs(Observation obs, int idObservateur) throws InsertFailedException{
        this.addObservateurToObs(obs.idObs(), idObservateur);
    }
}
