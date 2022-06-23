import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        controleur.App.stage = stage;
        controleur.App.setCurrentView(new vue.Login());
        // controleur.App.setCurrentView(new vue.Dashboard("Alexandre Borghi"));
    }

    public static void main(String[] args) {
        //ScenarioDonnee.main(args);
        launch();
    }

}
