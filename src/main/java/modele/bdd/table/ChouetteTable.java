package modele.bdd.table;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import modele.bdd.DatabaseEnum;
import modele.donnee.Chouette;
import modele.donnee.EspeceChouette;
import modele.donnee.Sexe;

public class ChouetteTable extends Table {
    private ObsChouetteTable obsChouetteTable;
    
    public ChouetteTable(){
        this(DatabaseEnum.PNR);
    }

    public ChouetteTable(DatabaseEnum databaseEnum) {
        super(databaseEnum);
        this.obsChouetteTable = new ObsChouetteTable(this.databaseEnum);
    }
    
    public ArrayList<Chouette> getAllChouette() throws SQLException {
        ArrayList<Chouette> ret = new ArrayList<Chouette>(0);
        try (Statement stmt = this.connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM Chouette;");
            if (rs.next()) {
                do {
                    Chouette chouette = new Chouette(
                            rs.getString("numIndividu"),
                            Sexe.valueOf(rs.getString("sexe").toUpperCase()),
                            EspeceChouette.valueOf(rs.getString("espece").toUpperCase()));
                    chouette.ajoutePlsObs(this.obsChouetteTable.getObsForChouette(chouette));
                    ret.add(chouette);
                } while (rs.next());
            }
        }  catch(NullPointerException e){
            throw new SQLException("Connection to database failed");
        }catch (Exception e) {
            throw e;
        }
        return ret;
    }

    public ArrayList<String> getAllIdChouette() throws SQLException{
        ArrayList<String> ret = new ArrayList<String>(0);
        try (Statement stmt = this.connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT numIndividu FROM Chouette;");
            if (rs.next()) {
                do {
                    ret.add(rs.getString("numIndividu"));
                } while (rs.next());
            }
        }  catch(NullPointerException e){
            throw new SQLException("Connection to database failed");
        }catch (Exception e) {
            throw e;
        }
        return ret;
    }

    public Chouette getChouette(String numIndividu) throws SQLException{
        if(numIndividu == null){
            throw new IllegalArgumentException("numIndividu ne peut pas être null");
        }
        Chouette ret = null;
        try (PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM Chouette WHERE numIndividu = ?;")) {
            stmt.setString(1, numIndividu);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                do {
                    EspeceChouette espece = null;
                    if(rs.getString("espece") != null){
                        espece = EspeceChouette.valueOf(rs.getString("espece").toUpperCase());
                    }
                    ret = new Chouette(
                            rs.getString("numIndividu"),
                            Sexe.valueOf(rs.getString("sexe").toUpperCase()),
                            espece);
                    ret.ajoutePlsObs(this.obsChouetteTable.getObsForChouette(ret));
                } while (rs.next());
            }
        } catch(NullPointerException e){
            throw new SQLException("Connection to database failed");
        } catch (Exception e) {
            throw e;
        }
        return ret;
    }

    public void insertChouette(String numIndividu, EspeceChouette especeEnum, Sexe sexe) throws SQLException{
        if(numIndividu == null){
            throw new IllegalArgumentException("numIndividu ne peut pas être null");
        }
        if(especeEnum == null){
            throw new IllegalArgumentException("espece ne peut pas être null");
        }
        if(sexe == null){
            throw new IllegalArgumentException("sexe ne peut pas être null");
        }
        try (PreparedStatement stmt = this.connection.prepareStatement("INSERT INTO Chouette VALUES (?, ?, ?);")) {
            stmt.setString(1, numIndividu);
            stmt.setString(2, especeEnum.toString());
            stmt.setString(3, sexe.toString());
            stmt.executeUpdate();
        } catch(NullPointerException e){
            throw new SQLException("Connection to database failed");
        } catch (Exception e) {
            throw e;
        }
    }
}
