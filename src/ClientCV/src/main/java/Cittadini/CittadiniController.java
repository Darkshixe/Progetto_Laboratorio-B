/**
 * @author Nicolo' Milo Varese 741297  
 * @author Niccolo' Gonzato Varese 741455 
 * @author Daniel Castelli Varese 740477 
 */
package Cittadini;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import RMI.ServerRMIInterface;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
* Controller Javafx di Cittadini.fxml
*/
public class CittadiniController implements Initializable{
	
	private Parent root;
	private Stage stage;
	private Scene scene;
	
	private Registry reg;
	private ServerRMIInterface server;
	
	@FXML
	private ImageView avatar;
	@FXML
	private TextField fieldUsername;
	@FXML
	private PasswordField fieldPassword;
	@FXML
	private TextField fieldNome;
	@FXML
	private TextField fieldComune;
	@FXML
	private ChoiceBox<String> boxTipologia;
	@FXML
	private ListView<String> lista;
	@FXML
	private Button login_logout_button;
	@FXML
	private Label loggedLabel;
	
	
	@FXML
	private AnchorPane infoPanel;
	@FXML
	private Label labelNome;
	@FXML
	private Label labelTipo;
	@FXML
	private Label labelIndirizzo;
	@FXML
	private TextField numCasi;
	@FXML
	private TextField mediaInt;
	@FXML
	private ChoiceBox<String> boxEventi;
	@FXML
	private Button eventButton;
	
	
	private String loggedString = "Benvenuto, ";
	private boolean isLogged = false;
	private String loggedUser;
	private long loggedID;
	
	private String[] tipologie = {"Ospedaliero", "Aziendale", "Hub"};
	private String[] eventi = {"Mal di testa", "Febbre", "Dolori muscolari e articolari", "Linfoadenopatia", "Tachicardia", "Crisi ipertensiva", "Altro"};
	private ArrayList<String> lista_centri = new ArrayList<String>();
	
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
		
		Image img = new Image("login_icon.png");
		avatar.setImage(img);
		boxTipologia.getItems().addAll(tipologie);
		boxEventi.getItems().addAll(eventi);
		
		lista.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				eventButton.setVisible(false);
				if (newValue != null) {
					try {
						lista.setVisible(false);
						StringTokenizer stringaTmp = new StringTokenizer(lista.getSelectionModel().getSelectedItem(), ",");
						String[] tmpRow = new String[stringaTmp.countTokens()];
						int i = 0;
						while (stringaTmp.hasMoreTokens()) {
							tmpRow[i] = stringaTmp.nextToken();
							i++;
						}
						ArrayList<String> centroSelezionato = server.cercaCentroVaccinale(tmpRow[0].replaceAll(" ", "_"));

						StringTokenizer stringaCentroSelezionato = new StringTokenizer(centroSelezionato.get(0), ",");
						tmpRow = new String[stringaCentroSelezionato.countTokens()];
						i = 0;
						while (stringaCentroSelezionato.hasMoreTokens()) {
							tmpRow[i] = stringaCentroSelezionato.nextToken();
							i++;
						}
						labelNome.setText(tmpRow[0]);
						labelTipo.setText(tmpRow[1]);
						labelIndirizzo.setText(tmpRow[2] + " " + tmpRow[3] + " " + tmpRow[4] + ", " + tmpRow[5] + ", "
								+ tmpRow[6] + ", " + tmpRow[7]);

						if (isLogged) {
							loggedID = server.cercaIDFromUser(loggedUser).get(0);

							if (server.cercaID(tmpRow[0].replaceAll(" ", "_")).contains(loggedID)) {
								eventButton.setVisible(true);
							}
						}
						String s = tmpRow[0].replaceAll(" ", "_");
						
						EventoAvverso malditesta = new EventoAvverso("Mal di testa", server.visualizzaInfoEvento(s, "Mal di testa").get(0), server.visualizzaInfoEvento(s, "Mal di testa").get(1));
						EventoAvverso febbre = new EventoAvverso("Febbre", server.visualizzaInfoEvento(s, "Febbre").get(0), server.visualizzaInfoEvento(s, "Febbre").get(1));
						EventoAvverso dolori = new EventoAvverso("Dolori muscolari e articolari", server.visualizzaInfoEvento(s, "Dolori muscolari e articolari").get(0), server.visualizzaInfoEvento(s, "Dolori muscolari e articolari").get(1));
						EventoAvverso linfoadenopatia = new EventoAvverso("Linfoadenopatia", server.visualizzaInfoEvento(s, "Linfoadenopatia").get(0), server.visualizzaInfoEvento(s, "Linfoadenopatia").get(1));
						EventoAvverso tachicardia = new EventoAvverso("Tachicardia", server.visualizzaInfoEvento(s, "Tachicardia").get(0), server.visualizzaInfoEvento(s, "Tachicardia").get(1));
						EventoAvverso crisi_ipertensiva = new EventoAvverso("Crisi ipertensiva", server.visualizzaInfoEvento(s, "Crisi ipertensiva").get(0), server.visualizzaInfoEvento(s, "Crisi ipertensiva").get(1));
						EventoAvverso altro = new EventoAvverso("Altro", server.visualizzaInfoEvento(s, "Altro").get(0), server.visualizzaInfoEvento(s, "Altro").get(1));	
							
						boxEventi.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

							@Override
							public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
								numCasi.setText("");
								mediaInt.setText("");
								if(newValue != null) {
									switch(newValue) {
										case "Mal di testa":
											numCasi.setText(malditesta.getCasi().toString());
											mediaInt.setText(malditesta.getMedia().toString());
											break;
										case "Febbre":
											numCasi.setText(febbre.getCasi().toString());
											mediaInt.setText(febbre.getMedia().toString());
											break;
										case "Dolori muscolari e articolari":
											numCasi.setText(dolori.getCasi().toString());
											mediaInt.setText(dolori.getMedia().toString());
											break;
										case "Linfoadenopatia":
											numCasi.setText(linfoadenopatia.getCasi().toString());
											mediaInt.setText(linfoadenopatia.getMedia().toString());
											break;
										case "Tachicardia":
											numCasi.setText(tachicardia.getCasi().toString());
											mediaInt.setText(tachicardia.getMedia().toString());
											break;
										case "Crisi ipertensiva":
											numCasi.setText(crisi_ipertensiva.getCasi().toString());
											mediaInt.setText(crisi_ipertensiva.getMedia().toString());
											break;
										case "Altro":
											numCasi.setText(altro.getCasi().toString());
											mediaInt.setText(altro.getMedia().toString());
											break;
									}
								}
							}
							
						});
						
						infoPanel.setVisible(true);

					} catch (RemoteException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
			}
			
		});
		
	}
	
	/**
	* Metodo che crea un Dialog per la registrazione di un cittadino nel database
	*/
	public void register(ActionEvent e) throws IOException {
		new RegisterDialog();
	}
	
	/**
	* Metodo che gestisce il login ed il logout
	*/
	public void login_logout(ActionEvent e) {
		if(!isLogged) {
			try {
				isLogged = server.loginCittadino(fieldUsername.getText(), fieldPassword.getText());
				if(isLogged == true) {
					Platform.runLater(() -> {
						fieldUsername.setVisible(false);
						fieldPassword.setVisible(false);
						eventButton.setVisible(true);
						loggedLabel.setText(loggedString + fieldUsername.getText());
						loggedLabel.setVisible(true);
						login_logout_button.setText("Logout");
						Alert alert = new Alert(AlertType.INFORMATION, "Accesso eseguito correttamente!", ButtonType.OK);
						alert.showAndWait();
						loggedUser = fieldUsername.getText();
					});
				}
				else {
					Alert alert = new Alert(AlertType.ERROR, "Username o password errati", ButtonType.OK);
					alert.showAndWait();
				}
			} catch (SQLException | RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else{
			Platform.runLater(() -> {
				eventButton.setVisible(false);
				loggedLabel.setText(loggedString);
				loggedLabel.setVisible(false);
				fieldUsername.setVisible(true);
				fieldUsername.setText("");
				fieldPassword.setVisible(true);
				fieldPassword.setText("");
				login_logout_button.setText("Login");
				isLogged = false;
			});
		}
	}
	
	/**
	* Metodo che ricerca i centri vaccinali il cui nome contiene la stringa immessa dall'utente
	*/
	public void searchByName(ActionEvent e) throws RemoteException, SQLException {
		lista_centri.clear();
		lista.getItems().clear();
		ArrayList<String> tmpList = server.cercaCentroVaccinale(fieldNome.getText().replaceAll(" ", "_"));
		for(String row: tmpList) {
			StringTokenizer stringaCentro = new StringTokenizer(row, ",");
			String[] tmpRow = new String[stringaCentro.countTokens()];
			int i = 0;
			while(stringaCentro.hasMoreTokens()) {
				tmpRow[i] = stringaCentro.nextToken();
				i++;
			}
			String listObj = tmpRow[0] + ", " + tmpRow[1] + ", " + tmpRow[5] + ", " + tmpRow[6];
			lista_centri.add(listObj);
		}
		lista.getItems().addAll(lista_centri);
	}
	
	/**
	* Metodo che ricerca i centri vaccinali il cui comune contiene la stringa immessa dall'utente e il cui tipo sia quello selezionato dall'utente
	*/
	public void searchByComune_tipo(ActionEvent e) throws RemoteException, SQLException {
		lista_centri.clear();
		lista.getItems().clear();
		ArrayList<String> tmpList = server.cercaCentroVaccinale(fieldComune.getText().replaceAll(" ", "_"), boxTipologia.getValue());
		for(String row: tmpList) {
			StringTokenizer stringaCentro = new StringTokenizer(row, ",");
			String[] tmpRow = new String[stringaCentro.countTokens()];
			int i = 0;
			while(stringaCentro.hasMoreTokens()) {
				tmpRow[i] = stringaCentro.nextToken();
				i++;
			}
			String listObj = tmpRow[0] + ", " + tmpRow[1] + ", " + tmpRow[5] + ", " + tmpRow[6];
			lista_centri.add(listObj);
		}
		lista.getItems().addAll(lista_centri);
	}
	
	/**
	* Metodo che crea un Dialog per l'inserimento di un evento avverso
	*/
	public void addEventoAvverso(ActionEvent e) throws IOException {
		new EventDialog(loggedUser, loggedID);
	}
	
	/**
	* Metodo che permette di tornare alla home dell'applicazione
	*/
	public void backToHome(ActionEvent e) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));
		stage = (Stage)((Node) e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	* Metodo che resetta la visualizzazione della lista
	*/
	public void backToList(ActionEvent e) {
		infoPanel.setVisible(false);
		lista.getSelectionModel().clearSelection();
		lista.setVisible(true);
	}

	
}