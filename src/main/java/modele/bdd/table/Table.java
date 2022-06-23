package modele.bdd.table;

import java.sql.Connection;

import modele.bdd.DatabaseEnum;
import modele.bdd.DatabaseSingleton;

public abstract class Table {
    protected Connection connection;
    protected DatabaseEnum databaseEnum;

    public Table(){
        this(DatabaseEnum.PNR);
    }

    public Table(DatabaseEnum databaseEnum){
        this.connection = DatabaseSingleton.getInstance(databaseEnum);
        this.databaseEnum = databaseEnum;
    }
}
