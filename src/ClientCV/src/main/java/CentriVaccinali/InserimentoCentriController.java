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
import java.sql.SQLException;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
* Controller Javafx di InserimentoCentro.fxml
*/
public class InserimentoCentriController implements Initializable{

	private Parent root;
	private Stage stage;
	private Scene scene;

	Registry reg;
	ServerRMIInterface server;

	@FXML
	private TextField Nome_centro;
	@FXML 
	private ChoiceBox<String> boxTipologia;
	@FXML
	private ChoiceBox<String> boxQualificatore;
	@FXML
	private TextField nome_indirizzo;
	@FXML
	private TextField provincia;
	@FXML
	private TextField textCAP;
	@FXML
	private TextField comune;
	@FXML
	private TextField civico;


	private String[] qualificatori = {"Via", "Viale", "Piazza"};
	private String[] tipologie = {"Ospedaliero", "Aziendale", "Hub"};


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

		boxQualificatore.getItems().addAll(qualificatori);
		boxTipologia.getItems().addAll(tipologie);
		int maxCAPLength = 5;
		textCAP.textProperty().addListener(new ChangeListener<String>() {
			
			public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
				if (!textCAP.getText().matches("[0-9]+")) {
					if(textCAP.getText().length() == 1) {
						String s = textCAP.getText().substring(textCAP.getLength(), textCAP.getLength());
						textCAP.setText(s);
					}
					else if(textCAP.getText().length() > 1){
						String s = textCAP.getText().substring(0, textCAP.getLength() - 1);
						textCAP.setText(s);
					} 
				}
				if (textCAP.getText().length() > maxCAPLength) {
					String s = textCAP.getText().substring(0, maxCAPLength);
					textCAP.setText(s);
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
	/**
	* Metodo che permette di aggiungere un centro vaccinale alla tabella CentriVaccinali
	*/
	public void addCentro(ActionEvent e) {
		CentroVaccinale centro = new CentroVaccinale(Nome_centro.getText(), boxTipologia.getValue(), boxQualificatore.getValue(), nome_indirizzo.getText(), civico.getText(), comune.getText(), provincia.getText(), textCAP.getText());
		Task<Void> task;
		if(!centro.isNull() && textCAP.getText().length() == 5) {
			task = new Task<Void>() {
				 
				public Void call() {
					int cap = Integer.parseInt(centro.getCAPIndirizzo());
					try {
						server.registraCentroVaccinale(centro.getNome(), centro.getTipo(), centro.getQualificatoreIndirizzo(), centro.getNomeIndirizzo(), centro.getCivicoIndirizzo(), centro.getComuneIndirizzo(), centro.getProvinciaIndirizzo(), cap);
						Platform.runLater(() -> {
							Alert alert = new Alert(AlertType.INFORMATION, "Centro vaccinale inserito correttamente", ButtonType.OK);
							alert.showAndWait();
							reset();
			            });
					} catch (RemoteException | SQLException | ClassNotFoundException e) {
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
	
	private void reset() {
		Nome_centro.setText("");
		boxTipologia.setValue(null);
		boxQualificatore.setValue(null);
		nome_indirizzo.setText("");
		provincia.setText("");
		comune.setText("");
		civico.setText("");
		textCAP.setText("");
	}

}
