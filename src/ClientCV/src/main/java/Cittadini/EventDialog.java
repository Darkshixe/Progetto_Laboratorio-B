/**
 * @author Nicolo' Milo Varese 741297  
 * @author Niccolo' Gonzato Varese 741455 
 * @author Daniel Castelli Varese 740477 
 */
package Cittadini;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
* Classe per la creazione della Dialog adibita all'inserimento di un evento avverso
*/
public class EventDialog extends Dialog<Void>{
	/** 
	* Costruttore della classe
	* @param loggedUser stringa dell'utente loggato
	* @param loggedID Id dell'utente loggato
	*/
	public EventDialog(String loggedUser, long loggedID) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EventDialog.fxml"));
        Parent root = loader.load();
        getDialogPane().setContent(root);
        this.setTitle("Inserisci gli eventuali eventi avversi");
        
        Stage stage = (Stage) this.getDialogPane().getScene().getWindow();
        stage.setOnCloseRequest(event -> stage.hide());
        Image icon = new Image("icon.png");
        stage.getIcons().add(icon);
        
        EventDialogController controller = loader.getController();
        controller.initData(loggedUser, loggedID);
        
        this.showAndWait();
    }
}