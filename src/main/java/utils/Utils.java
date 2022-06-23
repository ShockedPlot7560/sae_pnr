package utils;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

public class Utils {
    public static Parent loadFXML(Object thisObject, String path) {
        FXMLLoader loader = new FXMLLoader(thisObject.getClass().getResource(path));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            root = new Label(String.format("Failed to load %s", path));
            e.printStackTrace();
        }

        return root;
    }

    public static Parent loadFXMLWithController(Object thisObject, String path, Object controller) {
        FXMLLoader loader = new FXMLLoader(thisObject.getClass().getResource(path));
        loader.setController(controller);
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            root = new Label(String.format("Failed to load %s", path));
            e.printStackTrace();
        }

        return root;
    }
}
