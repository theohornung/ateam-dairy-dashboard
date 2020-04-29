package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**
 * Main class of GUI application
 */
public class Main extends Application {
	
	private static final int WINDOW_HEIGHT = 400;
	private static final int WINDOW_WIDTH = 700;
	
	/**
	 * Required method in Application subclasses that starts the GUI
	 */
	@Override
	public void start(Stage primaryStage) {
		Dashboard dashboard = new Dashboard(primaryStage);
		
		Scene mainScene = new Scene(dashboard, WINDOW_WIDTH, WINDOW_HEIGHT);
		primaryStage.setScene(mainScene);
		primaryStage.setTitle("CCFMAS");
		primaryStage.show();
	}
	
	/**
	 * Main method to start the application
	 * @param args any command line args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
