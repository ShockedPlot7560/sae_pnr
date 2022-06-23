package modele.bdd.table;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import modele.bdd.DatabaseEnum;
import modele.donnee.NidGCI;
import modele.donnee.RaisonArretObservation;

public class NidGciTable extends Table {
    public NidGciTable(){
        super();
    }

    public NidGciTable(DatabaseEnum databaseEnum) {
        super(databaseEnum);
    }
    
    public ArrayList<String> getAllPlage() throws SQLException{
        ArrayList<String> ret = new ArrayList<String>(0);
        try (Statement stmt = this.connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT nomPlage FROM Nid_GCI;");
            if (rs.next()) {
                do {
                    ret.add(rs.getString("nomPlage"));
                } while (rs.next());
            }
        } catch(NullPointerException e){
            throw new SQLException("Connection to database failed");
        }catch (Exception e) {
            throw e;
        }
        return ret;
    }
    
    public ArrayList<String> getAllNidString() throws SQLException{
        ArrayList<String> ret = new ArrayList<String>(0);
        try (Statement stmt = this.connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM Nid_GCI;");
            if (rs.next()) {
                do {
                    ret.add(rs.getString("idNid") + ": " + rs.getString("nomPlage"));
                } while (rs.next());
            }
        } catch(NullPointerException e){
            throw new SQLException("Connection to database failed");
        }catch (Exception e) {
            throw e;
        }
        return ret;
    }

    public NidGCI getNid(int idNid) throws SQLException{
        NidGCI ret = null;
        try (PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM Nid_GCI WHERE idNid = ?;")) {
            stmt.setInt(1, idNid);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ret = new NidGCI(
                    idNid, 
                    rs.getString("raisonArretObservation"), 
                    rs.getInt("nbEnvol"), 
                    rs.getBoolean("protection"), 
                    rs.getString("bagueMale"), 
                    rs.getString("bagueFemelle"), 
                    rs.getString("nomPlage"));
            }
        } catch(NullPointerException e){
            throw new SQLException("Connection to database failed");
        }
        return ret;
    }

    public int insert(String nomPlage, RaisonArretObservation raisonArretObservation, int nbEnvol, int protection, String bagueMale, String bagueFemelle) throws SQLException {
        int ret = 0;
        if(nomPlage == null){
            throw new IllegalArgumentException("Plage null");
        }
        try (PreparedStatement stmt = this.connection.prepareStatement("INSERT INTO Nid_GCI VALUES ((SELECT MAX(bleble.idNid) + 1 FROM (SELECT * FROM Nid_GCI) as bleble), ?, ?, ?, ?, ?, ?);")) {
            stmt.setString(1, nomPlage);
            stmt.setString(2, raisonArretObservation == null ? null : raisonArretObservation.toString());
            stmt.setInt(3, nbEnvol);
            stmt.setInt(4, protection);
            stmt.setString(5, bagueMale);
            stmt.setString(6, bagueFemelle);
            ret = stmt.executeUpdate();
        } catch(NullPointerException e){
            throw new SQLException("Connection to database failed: " + e.getMessage());
        }
        //get the nid corresponding
        try (PreparedStatement stmt = this.connection.prepareStatement("SELECT idNid FROM Nid_GCI WHERE nomPlage = ? AND raisonArretObservation = ? AND nbEnvol = ? AND protection = ? AND bagueMale = ? AND bagueFemelle = ?;")) {
            stmt.setString(1, nomPlage);
            stmt.setString(2, raisonArretObservation == null ? null : raisonArretObservation.toString());
            stmt.setInt(3, nbEnvol);
            stmt.setInt(4, protection);
            stmt.setString(5, bagueMale);
            stmt.setString(6, bagueFemelle);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("idNid");
            }
        } catch(NullPointerException e){
            throw new SQLException("Connection to database failed: " + e.getMessage());
        }
        return ret;
    }
}
