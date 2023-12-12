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
* Classe che crea il dialog utile alla registrazione del cittadino
*/
public class RegisterDialog extends Dialog<Void>{
	/**
	 * Costruttore del Dialog
	 */
	public RegisterDialog() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RegisterDialog.fxml"));
        Parent root = loader.load();
        getDialogPane().setContent(root);
        this.setTitle("Inserisci i dati per la registrazione");
        
        Stage stage = (Stage) this.getDialogPane().getScene().getWindow();
        stage.setOnCloseRequest(event -> stage.hide());
        Image icon = new Image("icon.png");
        stage.getIcons().add(icon);
        
        this.showAndWait();
    }
}