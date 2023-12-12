/**
 * @author Nicolo' Milo Varese 741297  
 * @author Niccolo' Gonzato Varese 741455 
 * @author Daniel Castelli Varese 740477 
 */
package Cittadini;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ResourceBundle;

import RMI.ServerRMIInterface;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

/**
* Controller javafx per la classe RegisterDialog che permette di registrare un nuovo cittadino nel database
*/
public class RegisterDialogController implements Initializable{
	
	private Registry reg;
	private ServerRMIInterface server;
	
	@FXML
	private TextField nome;
	@FXML
	private TextField cognome;
	@FXML
	private TextField email;
	@FXML
	private TextField codice_fiscale;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private TextField id;
	
	/**
	* Riscrittura ed implementazione del metodo inizialize, che permette di recuperare i dati della schermata alla sua visualizzazione
	*/
	public void initialize(URL location, ResourceBundle resources) {
		try {
			reg = LocateRegistry.getRegistry(1099);
			server = (ServerRMIInterface) reg.lookup("SERVER");
		} catch (RemoteException | NotBoundException e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
			e.printStackTrace();
		}
		
		int maxIDLength = 16;
		id.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
				if (!id.getText().matches("[0-9]+")) {
					if(id.getText().length() == 1) {
						String s = id.getText().substring(id.getLength(), id.getLength());
						id.setText(s);
					}
					else if(id.getText().length() > 1){
						String s = id.getText().substring(0, id.getLength() - 1);
						id.setText(s);
					} 
				}
				if (id.getText().length() > maxIDLength) {
					String s = id.getText().substring(0, maxIDLength);
					id.setText(s);
				}
			}
		});
		
		int maxCodiceFiscaleLength = 16;
		codice_fiscale.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
				if (codice_fiscale.getText().length() > maxCodiceFiscaleLength) {
					String s = codice_fiscale.getText().substring(0, maxCodiceFiscaleLength);
					codice_fiscale.setText(s);
				}
			}
		});
	}
	
	/**
	* Metodo utile ad aggiungere il cittadino al database
	*/
	public void addCittadino(ActionEvent e) {
		Cittadino cittadino = new Cittadino(nome.getText(), cognome.getText(), codice_fiscale.getText(), email.getText(), username.getText(), password.getText(), id.getText());
		Task<Void> task;
		if(!cittadino.isNull() && codice_fiscale.getText().length() == 16) {
			task = new Task<Void>() {
				@Override 
				public Void call() {
					long longID = Long.parseLong(cittadino.getId());
					try {
						server.registraCittadino(cittadino.getNome(), cittadino.getCognome(), cittadino.getCodice_fiscale(), cittadino.getEmail(), cittadino.getUsername(), cittadino.getPassword(), longID);
						Platform.runLater(() -> {
							Alert alert = new Alert(AlertType.INFORMATION, "Registrazione avvenuta con successo", ButtonType.OK);
							alert.showAndWait();
							Stage stage = (Stage) id.getScene().getWindow();
							stage.close();
						});
					} catch (RemoteException | SQLException e) {
						Platform.runLater(() -> {
							Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
							alert.showAndWait();
						});
					}
					return null;
				}

			};
			new Thread(task).start();
		}
		else {
			Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.WARNING, "Uno o piu' campi sono vuoti/errati", ButtonType.OK);
				alert.showAndWait();
            });
		}
	}
	
	/**
	* Metodo utile alla chiusura del dialog
	*/
	public void close(ActionEvent e) {
		Stage stage = (Stage) id.getScene().getWindow();
		stage.close();
	}

	
}
