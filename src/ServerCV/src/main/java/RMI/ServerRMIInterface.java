/**
 * @author Nicolo' Milo Varese 741297  
 * @author Niccolo' Gonzato Varese 741455 
 * @author Daniel Castelli Varese 740477 
 */
package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * Interfaccia di {@link ServerRMI}
 *
 */
public interface ServerRMIInterface extends Remote{
	//funzioni CentroVaccinale
	void registraCentroVaccinale(String Nome_centro, String Tipo_centro, String Qualificatore, String Nome_via, String Numero_civico, String Comune, String Provincia, int CAP) throws RemoteException, SQLException, ClassNotFoundException;
	void registraVaccinato(String Nome, String Cognome, String Codice_fiscale, Date data, String vaccino, long id, String nome_centro) throws RemoteException, SQLException;
	ArrayList<String> cercaCentri() throws RemoteException, SQLException;
	//funzioni Cittadini
	ArrayList<String> cercaCentroVaccinale(String nome_centro) throws RemoteException, SQLException;
	ArrayList<String> cercaCentroVaccinale(String comune, String tipo) throws RemoteException, SQLException;
	void registraCittadino(String Nome, String Cognome, String CodiceFiscale, String Mail, String Userid, String Password, long id_vaccinazione) throws RemoteException, SQLException;
	boolean loginCittadino(String user, String password) throws RemoteException, SQLException;
	void inserisciEventiAvversi(String Evento, int Intensita, String Vaccino, String Nome_centro, String Note, String UserID) throws RemoteException, SQLException;
	ArrayList<Long> cercaID(String s) throws RemoteException, SQLException;
	ArrayList<Long> cercaIDFromUser(String u) throws RemoteException, SQLException;
	ArrayList<String> cercaNomeEvaccinoFromVaccinati(String nome_centro, long loggedID) throws RemoteException, SQLException;
	ArrayList<Double> visualizzaInfoEvento(String nome_centro, String evento) throws RemoteException, SQLException;
}