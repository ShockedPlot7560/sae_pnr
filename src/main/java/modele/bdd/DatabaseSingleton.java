package modele.bdd;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class DatabaseSingleton {
    private static HashMap<DatabaseEnum, Connection> singleton;
    private static final String CONFIG_PATH = "./config.txt";
    private static final String[] PARAM = {"url=", "user=", "pass="};
    private static String dbURL = "";
    private static String dbUser = "";
    private static String dbPass = "";

    private DatabaseSingleton() {
    }

    public static Connection getInstance(DatabaseEnum databaseEnum){
        if(singleton == null) {
            singleton = new HashMap<DatabaseEnum, Connection>();
        }
        if(singleton == null || singleton.get(databaseEnum) == null){
            Connection con = null;
            init();
            try {
                String databaseName = databaseEnum.toString().toLowerCase();
                con = DriverManager.getConnection("jdbc:mysql://" + dbURL + "/" + databaseName + "?useSSL=false", dbUser, dbPass);
            } catch (SQLException e) {
                con = null;
            }
            singleton.put(databaseEnum, con);
        }
        return singleton.get(databaseEnum);
    }

    public static void init() {
        if (dbURL.length() == 0 || dbUser.length() == 0 || dbPass.length() == 0) {
            try {
                List<String> lines = Files.readAllLines(Paths.get(CONFIG_PATH));

                if (lines == null || lines.size() == 0) {
                    throw new IOException("Le fichier de configuration est vide");
                }
                for (int i = 0; i < lines.size(); i++) {
                    if (!lines.get(i).contains(PARAM[i])) {
                        throw new IOException("Erreur dans le fichier de configuration, ligne " + i);
                    }
                    String add = lines.get(i).substring(PARAM[i].length());
                    if (i == 0) {
                        add = add.replace("/", "");
                        dbURL = add;
                    }
                    else if (i == 1) {
                        dbUser = add;
                    }
                    else if (i == 2) {
                        dbPass = add;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
