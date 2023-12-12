/**
 * @author Nicolo' Milo Varese 741297  
 * @author Niccolo' Gonzato Varese 741455 
 * @author Daniel Castelli Varese 740477 
 */
package CentriVaccinali;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import RMI.ServerRMIInterface;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;


/**
* Controller Javafx di InserimentoVaccinato.fxml
*/
public class InserimentoVaccinatiController implements Initializable{
	private Parent root;
	private Stage stage;
	private Scene scene;

	Registry reg;
	ServerRMIInterface server;
	
	@FXML
	private TextField nome;
	@FXML
	private TextField cognome;
	@FXML
	private TextField codice_fiscale;
	@FXML
	private DatePicker data;
	@FXML 
	private ChoiceBox<String> boxVaccino;
	@FXML
	private ChoiceBox<String> boxCentri;
	
	private String[] tipoVaccino = {"Pfizer", "Astrazeneca", "Moderna", "J&J"};
	private ArrayList<String> lista_centri;

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
			e.printStackTrace();
		}
		
		try {
			lista_centri = server.cercaCentri();
		} catch (RemoteException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boxCentri.getItems().addAll(lista_centri);
		boxVaccino.getItems().addAll(tipoVaccino);
		
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
	* Metodo che permette di ritornare alla schermata di selezione dell'applicazione CentriVaccinali
	*/
	public void backToSelection(ActionEvent e) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/fxml/CentriVaccinali.fxml"));
		stage = (Stage)((Node) e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void addVaccinato(ActionEvent e) {
		String noSpaceCenter = null;
		Date tmpDate = null;
		boolean isValid = true;
		try {
			noSpaceCenter = boxCentri.getValue().replaceAll(" ", "_");
		}
		catch(NullPointerException e1) {
			Alert alert = new Alert(AlertType.ERROR, "Non e' stato selezionato alcun centro", ButtonType.OK);
			alert.showAndWait();
			isValid = false;
		}
		try {
			tmpDate = Date.valueOf(data.getValue());
		}
		catch(NullPointerException e1) {
			Alert alert = new Alert(AlertType.ERROR, "La data inserita e' vuota o non valida", ButtonType.OK);
			alert.showAndWait();
			isValid = false;
		}
		if (isValid) {
			Vaccinato vaccinato = new Vaccinato(nome.getText(), cognome.getText(), codice_fiscale.getText(), tmpDate, boxVaccino.getValue(), noSpaceCenter);
			Task<Void> task;
			if (!vaccinato.isNull() && codice_fiscale.getText().length() == 16) {
				task = new Task<Void>() {
					long idLong;
					boolean isValid;
					@Override
					public Void call() throws RemoteException, SQLException {
						do {
							isValid = true;
							idLong = generateID();
								for(String tuttiCentri: server.cercaCentri()) {
									tuttiCentri = tuttiCentri.replaceAll(" ", "_");
									for(Long i: server.cercaID(tuttiCentri)) {
										if(idLong == i) {
											isValid = false;
											break;
										}
									}
									if(!isValid)
										break;
								}
						}while(!isValid);
						server.registraVaccinato(vaccinato.getNome(), vaccinato.getCognome(), vaccinato.getCodice_fiscale(), vaccinato.getData_somministrazione(), vaccinato.getVaccino(), idLong, vaccinato.getCentro());
								Platform.runLater(() -> {
									TextField textID = new TextField(String.valueOf(idLong));
									textID.setEditable(false);

									Alert alert = new Alert(AlertType.INFORMATION);
									alert.getDialogPane().setContent(textID);
									alert.setHeaderText("Vaccinato inserito correttamente con ID:");
									alert.showAndWait();
									reset();
								});
						return null;
					}

				};
				new Thread(task).start();
			} else {
				Platform.runLater(() -> {
					Alert alert = new Alert(AlertType.WARNING, "Uno o piu' campi sono vuoti/errati", ButtonType.OK);
					alert.showAndWait();
				});
			} 
		}

	}
	
	private long generateID() {
		long rng =  (long) ((Math.random() + 1) * 1000000000000000L);
		return rng;
	}
	
	private void reset() {
		nome.setText("");
		cognome.setText("");
		codice_fiscale.setText("");
		data.setValue(null);
		boxVaccino.setValue(null);
		boxCentri.setValue(null);
	}
}
