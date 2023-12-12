/**
 * @author Nicolo' Milo Varese 741297  
 * @author Niccolo' Gonzato Varese 741455 
 * @author Daniel Castelli Varese 740477 
 */
package CentriVaccinali;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
/**
 * Controller javafx della classe Home
 * 
 */
public class HomeController {
	
	@FXML
	private ImageView homeImage;
	
	private Parent root;
	private Stage stage;
	private Scene scene;
	

/**
* Metodo che cambia il contenuto dell'immagine quando si passa con il mouse sul bottone "Operatore Vaccinale"
*/ 
	public void hoverOperatoreVaccinale(MouseEvent e) {
		Image image = new Image("doctor.png");
		homeImage.setImage(image);
	}
	

/**
* Metodo che cambia il contenuto dell'immagine quando si sfiora con il mouse il bottone "Cittadini"
*/
	public void hoverCittadino(MouseEvent e) {
        Image image = new Image("citizen.png");
		homeImage.setImage(image);
	}
	
	/**
	* Metodo che cambia scena in quella che dovrebbe essere vista dall'OperatoreVaccinale
	*/
	public void switchToOperatoreVaccinale(ActionEvent e) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/fxml/CentriVaccinali.fxml"));
		stage = (Stage)((Node) e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	* Metodo che cambia scena in quella che dovrebbe essere vista dal cittadino
	*/
	public void switchToCittadini(ActionEvent e) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/fxml/Cittadini.fxml"));
		stage = (Stage)((Node) e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}