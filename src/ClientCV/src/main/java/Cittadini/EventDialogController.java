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
import java.util.StringTokenizer;

import RMI.ServerRMIInterface;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
* Controller javafx della classe EventDialog che permette di inserire eventi avversi
*/
public class EventDialogController implements Initializable {
	
	private Registry reg;
	private ServerRMIInterface server;
	
	@FXML
	private ChoiceBox<String> boxEventi;
	@FXML
	private Spinner<Integer> intensita;
	@FXML
	private TextArea textNote;
	
	private String[] eventi = {"Mal di testa", "Febbre", "Dolori muscolari e articolari", "Linfoadenopatia", "Tachicardia", "Crisi ipertensiva", "Altro"};
	
	private String loggedUser;
	private long loggedID;
	private boolean foundNome = false;
	
	/**
	* Riscrittura ed implementazione del metodo inizialize, che permette di recuperare i dati della schermata alla sua visualizzazione
	* e determinare le funzioni e l'aspetto dei bottoni
	*/
	public void initialize(URL location, ResourceBundle resources) {
		try {
			reg = LocateRegistry.getRegistry(1099);
			server = (ServerRMIInterface) reg.lookup("SERVER");
		} catch (RemoteException | NotBoundException e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
		}
		
		int maxNoteLength = 256;
		textNote.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
				if (textNote.getText().length() > maxNoteLength) {
					String s = textNote.getText().substring(0, maxNoteLength);
					textNote.setText(s);
				}
			}
		});
		
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5);
		valueFactory.setValue(1);
		intensita.setValueFactory(valueFactory);
		
		boxEventi.getItems().addAll(eventi);
		
	}
	
	/**
	* Metodo per aggiungere l'Evento avverso a partire dalle informazioni inserite dall'utente
	*/
	public void addEvento(ActionEvent e) throws RemoteException, SQLException {
		boolean isValid = true;
		if (boxEventi.getValue() != null) {
			for (String tuttiCentri : server.cercaCentri()) {
				tuttiCentri = tuttiCentri.replaceAll(" ", "_");
				for (Long i : server.cercaID(tuttiCentri)) {
					if (loggedID == i) {
						foundNome = true;
						break;
					}
				}
				if (foundNome) {
					StringTokenizer tmpString = new StringTokenizer(server.cercaNomeEvaccinoFromVaccinati(tuttiCentri, loggedID).get(0), ",");
					String[] tmpRow = new String[tmpString.countTokens()];
					int i = 0;
					while (tmpString.hasMoreTokens()) {
						tmpRow[i] = tmpString.nextToken();
						i++;
					}
					try {
					server.inserisciEventiAvversi(boxEventi.getValue(), intensita.getValue(), tmpRow[0], tuttiCentri.replaceAll(" ", "_"), textNote.getText(), loggedUser);
					}catch(SQLException e1) {
						Alert alert = new Alert(AlertType.ERROR, "Evento gia' inserito", ButtonType.OK);
						alert.showAndWait();
						isValid = false;
					}
					break;
				}
			}
			if (isValid) {
				Alert alert = new Alert(AlertType.INFORMATION, "Evento inserito con successo", ButtonType.OK);
				alert.showAndWait();
			}
		}
		else {
			Alert alert = new Alert(AlertType.ERROR, "Nessun evento selezionato", ButtonType.OK);
			alert.showAndWait();
		}
	}
	
	/**
	* Metodo per chiudere il dialog
	*/
	public void close(ActionEvent e) {
		Stage stage = (Stage) textNote.getScene().getWindow();
		stage.close();
	}

	/** 
	* Metodo per l'inizializzazione dei dati a partire dall'id dell'utente loggato
	* @param loggedUser username dell'utente loggato
	* @param loggedID id dell'utente loggato
	*/
	public void initData(String loggedUser, long loggedID) {
		this.loggedUser = loggedUser;
		this.loggedID = loggedID;
	}

}
