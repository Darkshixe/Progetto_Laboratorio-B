/**
 * @author Nicolo' Milo Varese 741297  
 * @author Niccolo' Gonzato Varese 741455 
 * @author Daniel Castelli Varese 740477 
 */
package RMI;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import ServerDatabase.DBManager;
/**
 * Classe principale per la realizzazione del server RMI, il cui oggetto remoto verr� utilizzato dai client
 * per accedere ai servizi del server stesso
 *
 */
public class ServerRMI extends UnicastRemoteObject implements ServerRMIInterface{
	private static final long serialVersionUID = 1L;

	private DBManager dbManager;
/**
 * Costruttore della classe
 * @param registro registro su cui il server salva il suo oggetto remoto accessibile dai client
 * @param user nome utente per l'accesso al database da parte di {@code dbManager}
 * @param psw password per l'accesso al database da parte di {@code dbManager}
 */
	public ServerRMI(Registry registro, String host, String port, String user, String psw) throws RemoteException, ClassNotFoundException, SQLException {
		super();
		registro.rebind("SERVER", this);
		
		dbManager = new DBManager(host, port, user, psw);
		dbManager.exec();
	}
/**
 * Metodo per registrare un nuovo centro vaccinale
 * @param Nome_centro Nome del centro da inserire
 * @param Tipo_centro Tipologia del centro da inserire
 * @param Qualificatore Qualificatore dell'indirizzo del centro da inserire
 * @param Nome_via Nome principale dell'indirizzo del centro da inserire
 * @param Numero_civico Numero civico dell'indirizzo del centro da inserire
 * @param Comune Comune del centro da inserire
 * @param Provincia Provincia del centro da inserire
 * @param CAP Codice postale del centro da inserire
 */
	public void registraCentroVaccinale(String Nome_centro, String Tipo_centro, String Qualificatore, String Nome_via, String Numero_civico, String Comune, String Provincia, int CAP) throws RemoteException, SQLException, ClassNotFoundException {
		dbManager.insertCV(Nome_centro, Tipo_centro, Qualificatore, Nome_via, Numero_civico, Comune, Provincia, CAP);
	}
	/**
	 * Metodo per ottenere i nomi di tutti i centri vaccinali nel database
	 * @return la lista dei nomi di tutti i centri vaccinali
	 */
	public ArrayList<String> cercaCentri() throws SQLException {
	    ArrayList<String> lista_centri = new ArrayList<String>();
		ResultSet result = dbManager.queryCVOnlyNome();
		while(result.next()) {
			String tmp = result.getString(1).replaceAll("_", " ");
			lista_centri.add(tmp);
		}
		return lista_centri;
	}
	/**
	 * Metodo per registrare un vaccinato nel database
	 * @param nome_centro Nome del centro in cui � avvenuta la vaccinazione
	 * @param Nome Nome del vaccinato
	 * @param Cognome Cognome del vaccinato
	 * @param Codice_fiscale Codice Fiscale del vaccinato
	 * @param vaccino Vaccino somministrato
	 * @param data Data della vaccinazione	
	 * @param id ID della vaccinazione 
	 */
	public void registraVaccinato(String Nome, String Cognome, String Codice_fiscale, Date data, String vaccino, long id, String nome_centro) throws RemoteException, SQLException {
		dbManager.insertVaccinato(nome_centro, Nome, Cognome, Codice_fiscale, vaccino, data, id);
	}
/**
 * Metodo per la ricerca per nome dei centri vaccinali
 * @param nome_centro stringa di ricerca
 * @return lista dei risultati della ricerca
 */
	public ArrayList<String> cercaCentroVaccinale(String nome_centro) throws RemoteException, SQLException {
		ArrayList<String> lista_centri = new ArrayList<String>();
		ResultSet result = dbManager.queryCVNome(nome_centro);
		ResultSetMetaData rsmd = result.getMetaData();
		int columns = rsmd.getColumnCount();
		while(result.next()) {
			String row = "";
			for(int i = 1; i <= columns; i++) {
				row = row + result.getString(i).replaceAll("_", " ") + ",";
			}
			lista_centri.add(row);
		}
		return lista_centri;
	}
/**
 * Metodo per la ricerca per comune e tipologia dei centri vaccinali
 * @param comune stringa di ricerca del comune
 * @param tipo tipologia del centro vaccinale
 * @return lista dei risultati della ricerca
 */
	public ArrayList<String> cercaCentroVaccinale(String comune, String tipo) throws RemoteException, SQLException {
		ArrayList<String> lista_centri = new ArrayList<String>();
		ResultSet result = dbManager.queryCVComuneTipo(comune, tipo);
		ResultSetMetaData rsmd = result.getMetaData();
		int columns = rsmd.getColumnCount();
		while(result.next()) {
			String row = "";
			for(int i = 1; i <= columns; i++) {
				row = row + result.getString(i).replaceAll("_", " ") + ",";
			}
			lista_centri.add(row);
		}
		return lista_centri;
	}
/**
 * Metodo per registrare un cittadino nel database
 * @param Nome Nome del cittadino
 * @param Cognome Cognome del cittadino
 * @param CodiceFiscale Codice Fiscale del cittadino
 * @param Mail Indirizzo di posta elettronica del cittadino
 * @param Userid Nome utente del cittadino inserito al momento della registrazione
 * @param Password Password del cittaino inserita al momento della registrazione
 * @param id_vaccinazione ID della vaccinazione fornito al momento della vaccinazione
 */
	public void registraCittadino(String Nome, String Cognome, String CodiceFiscale, String Mail, String Userid, String Password, long id_vaccinazione) throws RemoteException, SQLException {
		dbManager.insertCittadino(Nome, Cognome, CodiceFiscale, Mail, Userid, Password, id_vaccinazione);
	}
	/**
	 * Metodo per l'autenticazione del cittadino
	 * @param user nome utente inserito per l'autenticazione
	 * @param psw password inserita per l'autenticazione
	 * @return validita' dell'autenticazione, {@code true} se l'autenticazione e' corretta, {@code false} in caso contrario
	 */
	public boolean loginCittadino(String user, String psw) throws SQLException {
		ResultSet result = dbManager.queryLogin(user);
		String correctPsw = null;
		while(result.next()) {
			correctPsw = result.getString(1);
		}
		if(psw.equals(correctPsw) && psw != null)
			return true;
		else return false;
	}
	/**
	 * Metodo per Metodo per ottenere tutti gli id di vaccinazione di un dato centro vaccinale
	 * @param s centro vaccinale di cui si vogliono ottenere gli id di vaccinazione
	 * @return lista degli id di vaccinazione del centro {@code s}
	 */
	public ArrayList<Long> cercaID(String s) throws SQLException {
		ArrayList<Long> lista_id = new ArrayList<Long>();
		ResultSet result = dbManager.queryID(s);
		while(result.next()) {
			long tmp = result.getLong(1);
			lista_id.add(tmp);
		}
		return lista_id;
	}
	/**
	 * Metodo per ottenere l'id di vaccinazione di un dato utente
	 * @param u utente di cui si vuole ottenere l'id di vaccinazione
	 * @return la lista contenente l'id ricercato
	 */
	public ArrayList<Long> cercaIDFromUser(String u) throws SQLException {
		ArrayList<Long> lista_id = new ArrayList<Long>();
		ResultSet result = dbManager.queryIDFromUser(u);
		while(result.next()) {
			long tmp = result.getLong(1);
			lista_id.add(tmp);
		}
		return lista_id;
	}
	/**
	 * Metodo per ottenere il vaccino somministrato all'utente loggato
	 * @param nome_centro centro di vaccinazione dell'utente loggato
	 * @param loggedID id di vaccinazione dell'utente loggato
	 * @return la lista contenente il vaccino somministrato
	 */
	public ArrayList<String> cercaNomeEvaccinoFromVaccinati(String nome_centro, long loggedID) throws SQLException{
		ArrayList<String> vaccino = new ArrayList<String>();
		ResultSet result = dbManager.queryNome_VaccinoFromVaccinati(nome_centro, loggedID);
		while(result.next()) {
			String row = result.getString(1).replaceAll("_", " ");
			vaccino.add(row);
		}
		return vaccino;
	}
	/**
	 * Metodo per inserire un evento avverso nel database
	 * @param Evento Tipologia dell'evento da inserire
	 * @param Intensita Intensita' dell'evento
	 * @param Vaccino Vaccino somministrato
	 * @param Nome_centro Nome del centro vaccinale in cui e' stato somministrato il vaccino
	 * @param Note Eventuali note aggiuntive alla notifica dell'evento avverso
	 * @param UserID Nome utente del segnalatore dell'evento avverso
	 */
	public void inserisciEventiAvversi(String Evento, int Intensita, String Vaccino, String Nome_centro, String Note, String UserID) throws RemoteException, SQLException {
		dbManager.insertEventoAvverso(Evento, Intensita, Vaccino, Nome_centro, Note, UserID);
	}
	/**
	 * Metodo per visualizzare i dati relativi ai casi e all'intensita' media di un dato evento in un dato centro vaccinale
	 * @param nome_centro centro di cui si voglione visualizzare i dati
	 * @param evento di cui si vogliono visualizzare i dati
	 * @return la lista contenente i dati richiesti
	 */
	public ArrayList<Double> visualizzaInfoEvento(String nome_centro, String evento) throws SQLException {
		ArrayList<Double> infoEvento = new ArrayList<Double>();
		ResultSet result = dbManager.queryEventiAvversi(nome_centro, evento);
		ResultSetMetaData rsmd = result.getMetaData();
		int columns = rsmd.getColumnCount();
		while(result.next()) {
			for(int i = 1; i <= columns; i++) {
				infoEvento.add(result.getDouble(i));
			}
		}
		return infoEvento;
	}
}
