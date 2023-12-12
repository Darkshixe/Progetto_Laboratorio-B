/**
 * @author Nicolo' Milo Varese 741297  
 * @author Niccolo' Gonzato Varese 741455 
 * @author Daniel Castelli Varese 740477 
 */
package CentriVaccinali;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Classe principale della GUI, adibita alla creazione della finestra
 * 
 */
public class Home extends Application {

	/**
	 * Metodo che determina la creazione della GUI della schermata di home dell'applicazione
	 * @param primaryStage Stage usato per la generazione delle GUI
	 */
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Home.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		
		primaryStage.setTitle("Centri Vaccinali");
		primaryStage.setResizable(false);
		
		Image icon = new Image("icon.png");
		primaryStage.getIcons().add(icon);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
/**
 * Metodo main dell'applicazione
 * @param args eventuali argomenti aggiuntivi
 */
	public static void main(String[] args) {
		launch(args);
	}
}