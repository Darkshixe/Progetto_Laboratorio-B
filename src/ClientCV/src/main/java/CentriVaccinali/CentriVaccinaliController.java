/**
 * @author Nicolo' Milo Varese 741297  
 * @author Niccolo' Gonzato Varese 741455 
 * @author Daniel Castelli Varese 740477 
 */

package CentriVaccinali;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
/**
 * Controller javafx per la classe centrivaccinali
 *
 */
public class CentriVaccinaliController implements Initializable{

	private Parent root;
	private Stage stage;
	private Scene scene;
	
	@FXML
	private ImageView imgCentro;
	@FXML
	private ImageView imgVaccino;
	/**
	 * Metodo che permette di ritornare alla Home dell'applicazione
	 */
	public void backToHome(ActionEvent e) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));
		stage = (Stage)((Node) e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	/**
	* Metodo che permette di accedere alla scena di creazione per inserire un centro vaccinale
    * @param e evento Mouse generato dalla GUI
	*/
	public void toInsertCentre(MouseEvent e) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/fxml/InserimentoCentro.fxml"));
		stage = (Stage)((Node) e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	/** 
	 * Metodo che permette di accedere alla scena di creazione per inserire un cittadino
     * @param e evento Mouse generato dalla GUI
	 */
	public void toInsertVaccinated(MouseEvent e) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/fxml/InserimentoVaccinato.fxml"));
		stage = (Stage)((Node) e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
/**
 * Riscrittura ed implementazione del metodo inizialize, che permette di recuperare i dati della schermata alla sua visualizzazione
 */
	public void initialize(URL location, ResourceBundle resources) {
		Image imgC = new Image("centre_icon.png");
		imgCentro.setImage(imgC);
		Tooltip.install(imgCentro, new Tooltip("Inserisci un centro vaccinale"));
		Image imgV = new Image("vaccine_icon.png");
		imgVaccino.setImage(imgV);
		Tooltip.install(imgVaccino, new Tooltip("Inserisci un vaccinato"));
	}
	
}