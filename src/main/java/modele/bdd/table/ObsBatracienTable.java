package modele.bdd.table;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;

import controleur.dashboard.InsertFailedException;
import modele.bdd.DatabaseEnum;
import modele.donnee.EspeceBatracien;
import modele.donnee.Lieu;
import modele.donnee.LieuVegetation;
import modele.donnee.MeteoCiel;
import modele.donnee.MeteoPluie;
import modele.donnee.MeteoTemp;
import modele.donnee.MeteoVent;
import modele.donnee.NatureVege;
import modele.donnee.ObsBatracien;
import modele.donnee.Observateur;
import modele.donnee.Observation;
import modele.donnee.Ouverture;
import modele.donnee.Pente;
import modele.donnee.TypeMare;
import modele.donnee.Vegetation;
import modele.donnee.ZoneHumide;

public class ObsBatracienTable extends Table {
    private ObservateurTable observateurTable;

    public ObsBatracienTable(){
        super();
        this.observateurTable = new ObservateurTable(this.databaseEnum);
    }

    public ObsBatracienTable(DatabaseEnum databaseEnum) {
        super(databaseEnum);
        this.observateurTable = new ObservateurTable(this.databaseEnum);
    }

    public ArrayList<ObsBatracien> getAllObsBatracien() throws SQLException {
        ArrayList<ObsBatracien> ret = new ArrayList<ObsBatracien>(0);
        try (PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM Obs_Batracien, Observation, ZoneHumide, Vegetation WHERE idObs = obsB AND concerne_ZH = zh_id AND concernes_vege = idVege;")) {
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

    public ObsBatracien parseFromResultSet(ResultSet rs) throws SQLException {
        ObsBatracien ret = null;
        String ciel = rs.getString("meteo_ciel");
        String temp = rs.getString("meteo_temp");
        String vent = rs.getString("meteo_vent");
        String pluie = rs.getString("meteo_pluie");
        String nature = rs.getString("natureVege");
        String typeMare = rs.getString("zh_typeMare");
        String pente = rs.getString("zh_pente");
        String ouverture = rs.getString("zh_ouverture");
        String espece = rs.getString("espece");
        
        MeteoCiel meteoCiel = null;
        MeteoTemp meteoTemp = null;
        MeteoVent meteoVent = null;
        MeteoPluie meteoPluie = null;
        NatureVege natureVege = null;
        TypeMare zoneTypeMare = null;
        Pente zonePente = null;
        Ouverture zoneOuverture = null;
        EspeceBatracien especeBatracien = null;

        if (ciel != null) {
            switch (ciel.toLowerCase()) {
                case "nuageux":
                    meteoCiel = MeteoCiel.NUAGEUX;
                    break;
                case "semi-dégagé":
                    meteoCiel = MeteoCiel.SEMI_DEGAGE;
                    break;
                case "dégagé":
                    meteoCiel = MeteoCiel.DEGAGE;
                    break;
            }
        }
        if (temp != null) {
            switch (temp.toLowerCase()) {
                case "froid":
                    meteoTemp = MeteoTemp.FROID;
                    break;
                case "moyen":
                    meteoTemp = MeteoTemp.MOYEN;
                    break;
                case "chaud":
                    meteoTemp = MeteoTemp.CHAUD;
                    break;
            }
        }
        if (vent != null) {
            switch (vent.toLowerCase()) {
                case "non":
                    meteoVent = MeteoVent.NON;
                    break;
                case "léger":
                    meteoVent = MeteoVent.LEGER;
                    break;
                case "moyen":
                    meteoVent = MeteoVent.MOYEN;
                    break;
                case "fort":
                    meteoVent = MeteoVent.FORT;
                    break;
            }
        }
        if (pluie != null) {
            switch (pluie.toLowerCase()) {
                case "non":
                    meteoPluie = MeteoPluie.NON;
                    break;
                case "légere":
                    meteoPluie = MeteoPluie.LEGERE;
                    break;
                case "moyenne":
                    meteoPluie = MeteoPluie.MOYENNE;
                    break;
                case "forte":
                    meteoPluie = MeteoPluie.FORTE;
                    break;
            }
        }
        if (nature != null) {
            switch (nature.toLowerCase()) {
                case "environnement":
                    natureVege = NatureVege.ENVIRONNEMENT;
                    break;
                case "bordure":
                    natureVege = NatureVege.BORDURE;
                    break;
                case "ripisyle":
                    natureVege = NatureVege.RIPISYLE;
                    break;
            }
        }
        if (typeMare != null) {
            switch (typeMare.toLowerCase()) {
                case "prairie":
                    zoneTypeMare = TypeMare.PRAIRIE;
                    break;
                case "etang":
                    zoneTypeMare = TypeMare.ETANG;
                    break;
                case "marais":
                    zoneTypeMare = TypeMare.MARAIS;
                    break;
                case "mare":
                    zoneTypeMare = TypeMare.MARE;
                    break;
                case "fossé":
                    zoneTypeMare = TypeMare.FOSSE;
                    break;
            }
        }
        if (pente != null) {
            switch (pente.toLowerCase()) {
                case "raide":
                    zonePente = Pente.RAIDE;
                    break;
                case "abrupte":
                    zonePente = Pente.ABRUPTE;
                    break;
                case "douce":
                    zonePente = Pente.DOUCE;
                    break;
            }
        }
        if (ouverture != null) {
            switch (ouverture.toLowerCase()) {
                case "ouverte":
                    zoneOuverture = Ouverture.OUVERTE;
                    break;
                case "semi-abritee":
                    zoneOuverture = Ouverture.SEMI_ABRITEE;
                    break;
                case "abritee":
                    zoneOuverture = Ouverture.ABRITEE;
                    break;
            }
        }
        if (espece != null) {    
            switch (espece.toLowerCase()) {
                case "calamite":
                    especeBatracien = EspeceBatracien.CALAMITE;
                    break;
                case "pelodyte":
                    especeBatracien = EspeceBatracien.PELODYTE;
                    break;
            }
        }
        ArrayList<Observateur> observateurs = new ArrayList<Observateur>(0);
        ret = new ObsBatracien(
                rs.getInt("idObs"),
                rs.getDate("dateObs"),
                rs.getTime("heureObs"),
                new Lieu(rs.getDouble("lieu_Lambert_X"), rs.getDouble("lieu_Lambert_Y"), true),
                observateurs,
                new int[]{rs.getInt("nombreAdultes"), rs.getInt("nombreAmplexus"),
                    rs.getInt("nombreTetard"), rs.getInt("nombrePonte")},
                rs.getDouble("temperature"),
                meteoCiel,
                meteoTemp,
                meteoVent,
                meteoPluie,
                new LieuVegetation(rs.getInt("decrit_LieuVege"), new ArrayList<Vegetation>(Arrays.asList(
                    new Vegetation(rs.getInt("idVege"), natureVege, rs.getString("vegetation"))))),
                new ZoneHumide(rs.getInt("zh_id"), rs.getBoolean("zh_temporaire"),
                    rs.getDouble("zh_profondeur"), rs.getDouble("zh_surface"),
                    zoneTypeMare, zonePente, zoneOuverture),
                especeBatracien);
        ret.setLesObservateurs(this.observateurTable.getAllObservateurOfObservation(ret));
        return ret;
    }

    public void insertObservation(
            Date date, Time time, Lieu lieu, ArrayList<Observateur> observateurs, 
            EspeceBatracien especeBatracien, int[] nombre, double temperature, MeteoCiel meteoCiel, MeteoTemp meteoTemp, MeteoVent meteoVent, MeteoPluie meteoPluie, 
            ArrayList<Pair<String, NatureVege>> vegetations, 
            boolean temporaire, int profondeur, int surface, TypeMare typeMare, Pente pente, Ouverture ouverture) throws InsertFailedException {
        try {
            new LieuTable().insert(lieu);
        } catch (SQLException e) {
            throw new InsertFailedException("Lieu insertion failed: " + e.getMessage());
        }
        int idObs = new ObservationTable().insert(date, time, lieu);
        //Insert zoneHumide
        int idZoneHumide;
        try (PreparedStatement stmt = this.connection.prepareStatement(
                "INSERT INTO ZoneHumide VALUES ((SELECT MAX(bleble.zh_id) + 1 FROM (SELECT * FROM ZoneHumide) as bleble), ?, ?, ?, ?, ?, ?)")) {
            stmt.setBoolean(1, temporaire);
            stmt.setDouble(2, profondeur);
            stmt.setDouble(3, surface);
            stmt.setString(4, typeMare == null ? null : this.parseMare(typeMare));
            stmt.setString(5, pente == null ? null : this.parsePente(pente));
            stmt.setString(6, ouverture == null ? null : this.parseOuverture(ouverture));
            stmt.executeUpdate();
            //get the id of the zoneHumide
            ResultSet rs = this.connection.createStatement().executeQuery("SELECT MAX(bleble.zh_id) as max FROM (SELECT * FROM ZoneHumide) as bleble");
            rs.next();
            idZoneHumide = rs.getInt("max");
        }catch(SQLException e) {
            throw new InsertFailedException("ZoneHumide insertion failed: " + e.getMessage());
        }
        //insert a LieuVegetation
        int idLieuVege;
        try (PreparedStatement stmt = this.connection.prepareStatement(
                "INSERT INTO Lieu_Vegetation VALUES ((SELECT MAX(bleble.idVegeLieu) + 1 FROM (SELECT * FROM Lieu_Vegetation) as bleble))")) {
            stmt.executeUpdate();
            //get the id of the lieuVegetation
            ResultSet rs = this.connection.createStatement().executeQuery("SELECT MAX(bleble.idVegeLieu) as max FROM (SELECT * FROM Lieu_Vegetation) as bleble");
            rs.next();
            idLieuVege = rs.getInt("max");
        }catch(SQLException e) {
            throw new InsertFailedException("LieuVegetation insertion failed: " + e.getMessage());
        }
        for (Pair<String,NatureVege> hashMap : vegetations) {
            try (PreparedStatement stmt = this.connection.prepareStatement(
                    "INSERT INTO Vegetation VALUES ((SELECT MAX(bleble.idVege) + 1 FROM (SELECT * FROM Vegetation) as bleble), ?, ?, ?)")) {
                stmt.setString(1, hashMap.left.toString().toLowerCase());
                stmt.setString(2, hashMap.right.toString().toLowerCase());
                stmt.setInt(3, idLieuVege);
                stmt.executeUpdate();
            }catch(SQLException e) {
                throw new InsertFailedException("Vegetation insertion failed: " + e.getMessage());
            }
        }
        try {
            this.insert(nombre, temperature, meteoCiel, meteoTemp, meteoVent, meteoPluie, idLieuVege, idZoneHumide, especeBatracien, idObs);
        } catch (Exception e) {
            throw new InsertFailedException("ObsBatracien insert failed: " + e.getMessage());
        }
    }

    private void insert(int[] nombre, double temperature, MeteoCiel meteoCiel, MeteoTemp meteoTemp, MeteoVent meteoVent, MeteoPluie meteoPluie, int vege, int zoneHumide, EspeceBatracien especeBatracien, int idObs) throws SQLException {
        try (PreparedStatement stmt = this.connection.prepareStatement("INSERT INTO Obs_Batracien (obsB, espece, nombreAdultes, nombreAmplexus, nombreTetard, nombrePonte, temperature, meteo_ciel, meteo_temp, meteo_vent, meteo_pluie, concerne_ZH, concernes_vege) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);")) {
            stmt.setInt(1, idObs);
            stmt.setString(2, especeBatracien.toString());
            stmt.setInt(3, nombre[0]);
            stmt.setInt(4, nombre[1]);
            stmt.setInt(5, nombre[2]);
            stmt.setInt(6, nombre[3]);
            stmt.setDouble(7, temperature);
            stmt.setString(8, meteoCiel == null ? null : this.parseMeteoCiel(meteoCiel));
            stmt.setString(9, meteoTemp == null ? null : meteoTemp.toString().toLowerCase());
            stmt.setString(10, meteoVent == null ? null : this.parseMeteoVent(meteoVent));
            stmt.setString(11, meteoPluie == null ? null : this.parseMeteoPluie(meteoPluie));
            stmt.setInt(12, zoneHumide);
            stmt.setInt(13, vege);
            stmt.executeUpdate();
        }
    }

    private String parseMeteoCiel(MeteoCiel meteoCiel){
        String ret = null;
        switch (meteoCiel) {
            case NUAGEUX:
                ret = "nuageux";
                break;
            case SEMI_DEGAGE:
                ret = "semi-dégagé";
                break;
            case DEGAGE:
                ret = "dégagé";
                break;
        }
        return ret;
    }

    private String parseMeteoPluie(MeteoPluie meteoPluie){
        String ret = null;
        switch (meteoPluie) {
            case NON:
                ret = "non";
                break;
            case FORTE:
                ret = "forte";
                break;
            case LEGERE:
                ret = "légère";
                break;
            case MOYENNE:
                ret = "moyenne";
                break;
        }
        return ret;
    }

    private String parseMeteoVent(MeteoVent vent){
        String ret = null;
        switch (vent) {
            case LEGER:
                ret = "léger";
                break;
            case MOYEN:
                ret = "moyen";
                break;
            case FORT:
                ret = "fort";
                break;
            case NON:
                ret = "non";
                break;
        }
        return ret;
    }

    private String parsePente(Pente pente){
        String ret = null;
        switch (pente) {
            case RAIDE:
                ret = "Raide";
                break;
            case ABRUPTE:
                ret = "Abrupte";
                break;
            case DOUCE:
                ret = "Douce";
                break;
        }
        return ret;
    }

    private String parseOuverture(Ouverture ouv){
        String ret = null;
        switch (ouv) {
            case ABRITEE:
                ret = "Abritee";
                break;
            case SEMI_ABRITEE:
                ret = "Semi-Abritee";
                break;
            case OUVERTE:
                ret = "Ouverte";
                break;
        }
        return ret;
    }
    
    private String parseMare(TypeMare mare){
        String ret = null;
        switch (mare) {
            case PRAIRIE:
                ret = "Prairie";
                break;
            case ETANG:
                ret = "Etang";
                break;
            case MARAIS:
                ret = "Marais";
                break;
            case MARE:
                ret = "Mare";
                break;
        }
        return ret;
    }
}