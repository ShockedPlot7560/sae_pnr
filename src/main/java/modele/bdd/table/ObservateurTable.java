package modele.bdd.table;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controleur.dashboard.InsertFailedException;
import modele.bdd.DatabaseEnum;
import modele.donnee.Observateur;
import modele.donnee.Observation;

public class ObservateurTable extends Table {
    public ObservateurTable(){
        super();
    }

    public ObservateurTable(DatabaseEnum databaseEnum) {
        super(databaseEnum);
    }
    
    public ArrayList<Observateur> getAllObservateurOfObservation(int obsId) throws SQLException{
        ArrayList<Observateur> ret = new ArrayList<Observateur>(0);
        try (PreparedStatement stmt = this.connection.prepareStatement("SELECT Observateur.* FROM Observateur, AObserve WHERE lobservateur = idObservateur AND lobservation = ?;")) {
            stmt.setInt(1, obsId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                do {
                    Observateur observateur = new Observateur(
                        rs.getInt("idObservateur"), 
                        rs.getString("nom"), 
                        rs.getString("prenom"),
                        rs.getString("nomUtilisateur"),
                        rs.getString("passwordHash"));
                    ret.add(observateur);
                } while (rs.next());
            }
        } catch(NullPointerException e){
            throw new SQLException("Connection to database failed");
        }catch (Exception e) {
            throw e;
        }
        return ret;
    }

    public ArrayList<Observateur> getAllObservateurOfObservation(Observation obs) throws SQLException {
        if (obs == null) {
            throw new IllegalArgumentException("Observation is null");
        }
        return this.getAllObservateurOfObservation(obs.idObs());
    }

    public ArrayList<Observateur> getAllObservateur() throws SQLException{
        ArrayList<Observateur> ret = new ArrayList<Observateur>(0);
        try (PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM Observateur;")) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                do {
                    Observateur observateur = new Observateur(
                        rs.getInt("idObservateur"), 
                        rs.getString("nom"), 
                        rs.getString("prenom"),
                        rs.getString("nomUtilisateur"),
                        rs.getString("passwordHash"));
                    ret.add(observateur);
                } while (rs.next());
            }
        } catch(NullPointerException e){
            throw new SQLException("Connection to database failed");
        }catch (Exception e) {
            throw e;
        }
        return ret;
    }

    public Observateur getObservateur(String observateurName) throws SQLException{
        return this.getObservateur(observateurName, true);
    }

    public Observateur getObservateur(String observateurName, boolean caseSensitive) throws SQLException{
        if(observateurName == null){
            throw new IllegalArgumentException("Observateur name is null");
        }
        Observateur ret = null;
        try (PreparedStatement stmt = this.connection.prepareStatement(caseSensitive ? "SELECT * FROM Observateur WHERE nom = ?;" : "SELECT * FROM Observateur WHERE LOWER(nom) = LOWER(?);")) {
            stmt.setString(1, observateurName);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                ret = new Observateur(
                    rs.getInt("idObservateur"), 
                    rs.getString("nom"), 
                    rs.getString("prenom"),
                    rs.getString("nomUtilisateur"),
                    rs.getString("passwordHash"));
            }
        } catch(NullPointerException e){
            throw new SQLException("Connection to database failed");
        }catch (Exception e) {
            throw e;
        }
        return ret;
    }

    public Observateur getObservateurByUsername(String username) throws SQLException{
        if(username == null){
            throw new IllegalArgumentException("Username is null");
        }
        Observateur ret = null;
        try (PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM Observateur WHERE nomUtilisateur = ?;")) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                ret = new Observateur(
                    rs.getInt("idObservateur"), 
                    rs.getString("nom"), 
                    rs.getString("prenom"),
                    rs.getString("nomUtilisateur"),
                    rs.getString("passwordHash"));
            }
        } catch(NullPointerException e){
            throw new SQLException("Connection to database failed");
        }
        return ret;
    }

    /**
     * Ajoute un observateur à la table AObservee
     * @param idObs L'id de l'observation
     * @param idObservateur L'id de l'observateur
     * @throws InsertFailedException Si une erreur est survenue
     */
    public void addObservateurToObs(int idObs, int idObservateur) throws InsertFailedException{
        try (PreparedStatement stmt = this.connection.prepareStatement("INSERT INTO AObserve VALUES (?, ?);")) {
            stmt.setInt(1, idObservateur);
            stmt.setInt(2, idObs);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new InsertFailedException("Observateur: " + idObs + " insert failed: " + e.getMessage());
        }
    }

    /**
     * Ajoute un observateur à la table Observateur
     * @param nom nom de l'observateur
     * @param prenom prenom de l'observateur
     * @param username nom d'utilisateur de l'observateur
     * @param passwordHash hash du mot de passe de l'observateur
     * @throws InsertFailedException Si une erreur est survenue
     */
    public void insertObservateur(String nom, String prenom, String username, String passwordHash) throws InsertFailedException{
        try (PreparedStatement stmt = this.connection.prepareStatement("INSERT INTO Observateur VALUES ((SELECT MAX(bleble.idObservateur) + 1 FROM (SELECT * FROM Observateur) as bleble), ?, ?, ?, ?);")) {
            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            stmt.setString(3, username);
            stmt.setString(4, passwordHash);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new InsertFailedException("Observateur insert failed: " + e.getMessage());
        }
    }

    public void updateObservateur(Observateur observateur, String nom, String prenom, String username) throws InsertFailedException{
        try (PreparedStatement stmt = this.connection.prepareStatement("UPDATE Observateur SET nom = ?, prenom = ?, nomUtilisateur = ? WHERE idObservateur = ?;")) {
            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            stmt.setString(3, username);
            stmt.setInt(4, observateur.getIdObservateur());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new InsertFailedException("Observateur update failed: " + e.getMessage());
        }
    }

    public void updateObservateurPassword(Observateur observateur, String passwordHash) throws InsertFailedException{
        try (PreparedStatement stmt = this.connection.prepareStatement("UPDATE Observateur SET passwordHash = ? WHERE idObservateur = ?;")) {
            stmt.setString(1, passwordHash);
            stmt.setInt(2, observateur.getIdObservateur());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new InsertFailedException("Observateur update failed: " + e.getMessage());
        }
    }
}
