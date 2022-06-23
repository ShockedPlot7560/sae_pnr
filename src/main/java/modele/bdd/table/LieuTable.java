package modele.bdd.table;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.bdd.DatabaseEnum;
import modele.donnee.Lieu;

public class LieuTable extends Table {
    public LieuTable(){
        super();
    }

    public LieuTable(DatabaseEnum databaseEnum) {
        super(databaseEnum);
    }

    public void insert(double xCoord, double yCoord) throws SQLException {
        try (PreparedStatement stmt = this.connection.prepareStatement("INSERT INTO Lieu VALUES (?, ?) ON DUPLICATE KEY UPDATE coord_Lambert_X = ?, coord_Lambert_Y = ?;")) {
            for (int i = 0; i < 2; i++) {
                stmt.setDouble(1 + 2 * i, xCoord);
                stmt.setDouble(2 + 2 * i, yCoord);
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    public void insert(Lieu lieu) throws SQLException{
        this.insert(lieu.getxCoord(), lieu.getyCoord());
    }
}
