package controleur;

import javafx.stage.Stage;

public class App {
    public static Stage stage;

    public static void setCurrentView(vue.View newView) {
        stage.setScene(newView.getScene());
        stage.show();
    }
}
