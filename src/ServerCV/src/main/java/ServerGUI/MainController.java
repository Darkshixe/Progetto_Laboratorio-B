/**
 * @author Nicolo' Milo Varese 741297  
 * @author Niccolo' Gonzato Varese 741455 
 * @author Daniel Castelli Varese 740477 
 */
package ServerGUI;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ResourceBundle;

import RMI.ServerRMI;
import ServerDatabase.DBManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Classe adibita al controllo e alla gestione degli eventi della GUI del Server
 * 
 */

public class MainController implements Initializable{
	
	@FXML
	private TextField userText;
	@FXML
	private PasswordField pswText;
	@FXML
	private Circle serverState;
	@FXML
	private Button serverButton;
	
	private ServerRMI server;
	private DBManager dbStarter;
	private Registry registro;
	
	/**
	 * Metodo usato per la creazione del database relazionale e delle sue tabelle
	 */
	public void makeDatabase(ActionEvent e) throws ClassNotFoundException {
		boolean isValid = true;
		try {
			if(!userText.getText().isBlank() && !pswText.getText().isBlank()) {
				dbStarter = new DBManager(userText.getText(), pswText.getText());
				dbStarter.start();
			}
			else {
				Alert alert = new Alert(AlertType.WARNING, "Uno o piu' campi sono vuoti", ButtonType.OK);
				alert.showAndWait();
				isValid = false;
			}
		} catch (SQLException e1) {
			Alert alert = new Alert(AlertType.ERROR, e1.getMessage(), ButtonType.OK);
			alert.showAndWait();
			isValid = false;
		}
		if(isValid) {
			Alert alert = new Alert(AlertType.INFORMATION, "Database creato correttamente", ButtonType.OK);
			alert.showAndWait();
		}
	}
	/**
	 * Metodo usato per la creazione e avvio del server RMI
	 */
	public void startServer(ActionEvent e){
		boolean isValid = true;
		if(!userText.getText().isBlank() && !pswText.getText().isBlank()) {
			try {
				server = new ServerRMI(registro, userText.getText(), pswText.getText());
			} catch (RemoteException | ClassNotFoundException | SQLException e1) {
				Alert alert = new Alert(AlertType.ERROR, e1.getMessage(), ButtonType.OK);
				alert.showAndWait();
				isValid = false;
			}
			if(isValid) {
				serverState.setFill(Color.GREEN);
				Alert alert = new Alert(AlertType.INFORMATION, "Server avviato correttamente, per chiuderlo, chiudere l'applicazione", ButtonType.OK);
				alert.showAndWait();
				serverButton.setDisable(true);
			}
		}
		else {
			Alert alert = new Alert(AlertType.WARNING, "Uno o piu' campi sono vuoti", ButtonType.OK);
			alert.showAndWait();
		}		
	}
	/**
	 * Metodo usato per la corretta chiusura del server RMI
	 */
	public void shutdown() throws RemoteException, NotBoundException {
		registro = LocateRegistry.getRegistry(1099);
		registro.unbind("SERVER");
		UnicastRemoteObject.unexportObject(server, true);
		System.exit(0);
	}

	/**
	 * Riscrittura ed implementazione del metodo inizialize, che permette di recuperare i dati della schermata alla sua visualizzazione
	 * 
	 */
	public void initialize(URL location, ResourceBundle resources) {
		try {
			registro = LocateRegistry.createRegistry(1099);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}