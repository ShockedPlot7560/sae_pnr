package vue;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

/**
 * Parent class for all views of the application.
 */
public abstract class View {

    protected Scene scene;

    public View() {

    }

    /**
     * Creates a new view.
     * 
     * @param name Name of the fxml file to load the scene from, without the
     *             extension. E.g., to load resources/fxml/login.fxml, put "login".
     */
    public View(String name) {
        String path = String.format("/fxml/%s.fxml", name);
        System.out.println("Loading path: " + path);
        Parent root;
        try {
            root = FXMLLoader.load(this.getClass().getResource(path));
        } catch (IOException e) {
            root = new Label(String.format("Failed to load %s", path));
        }

        this.scene = new Scene(root);
    }

    /**
     * Gets the scene of the view.
     * 
     * @return The scene.
     */
    public Scene getScene() {
        return this.scene;
    }

}
