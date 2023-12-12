/**
 * @author Nicolo' Milo Varese 741297  
 * @author Niccolo' Gonzato Varese 741455 
 * @author Daniel Castelli Varese 740477 
 */
package ServerGUI;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Classe principale della GUI, adibita alla creazione della finestra
 * 
 */
public class Main extends Application {

	/**
	 * Metodo adibito alla creazione della GUI del Server, in particolare della scena principale applicata alla finestra
	 * @param primaryStage Stage usato per la generazione delle GUI
	 */
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		MainController controller = loader.getController();
		
		primaryStage.setTitle("Centri Vaccinali");
		primaryStage.setResizable(false);
		
		Image icon = new Image("icon.png");
		primaryStage.getIcons().add(icon);
		
		primaryStage.setScene(scene);
		primaryStage.setOnHidden(e -> {
		    try {
				controller.shutdown();
			} catch (RemoteException | NotBoundException e1) {
			}
		    Platform.exit();
		});
		primaryStage.show();
	}
	/**
	 * Metodo main per l'effettivo avvio dell'applicazione
	 * @param args Eventuali argomenti passati all'avvio
	 */
	public static void main(String[] args) {
		launch(args);
	}
}