/**
 * @author Nicolo' Milo Varese 741297  
 * @author Niccolo' Gonzato Varese 741455 
 * @author Daniel Castelli Varese 740477 
 */
package ServerDatabase;

import java.io.Serializable;
import java.sql.*;

/**
 * Classe di utilità adibita alla effettiva comunicazione con il Database dal Server tramite JDBC per la creazione del database e delle sue tabelle,
 * per l'inserimento di nuovi dati e le varie query utili alla raccolta di questi ultimi
 *
 */
public class DBManager implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * Costruttore della classe
	 * @param h host del database relazionale salvato in {@code host}
	 * @param pt porta del database relazionale salvata in {@code port}
	 * @param u username del database relazionale salvato in {@code user}
	 * @param pw password del database relazionale salvata in {@code psw}
	 */
	public DBManager(String h, String pt, String u, String pw) {
		host = h.isBlank() ? "localhost" : h;
		port = pt.isBlank() ? "5432" : pt;
		user = u;
		psw = pw;
	}
	
	private Connection dbConnection;
	private String host;
	private String port;
	private String user;
	private String psw;
	
	/**
	* Metodo per la creazione delle tabelle non dinamiche
	*/
	private void createTables() throws SQLException {
		Statement st = dbConnection.createStatement();
		st.execute(SQLCommands.CREATETABLE_CV);
		st.execute(SQLCommands.CREATETABLE_CITTADINI);
		st.execute(SQLCommands.CREATETABLE_EVENTI_AVVERSI);
		st.close();
	}
	/**
	 * Metodo per l'inserimento di un centro vaccinale nel database
	 * @param Nome_centro Nome del centro da inserire
	 * @param Tipo_centro Tipologia del centro da inserire
	 * @param Qualificatore Qualificatore dell'indirizzo del centro da inserire
	 * @param Nome_via Nome principale dell'indirizzo del centro da inserire
	 * @param Numero_civico Numero civico dell'indirizzo del centro da inserire
	 * @param Comune Comune del centro da inserire
	 * @param Provincia Provincia del centro da inserire
	 * @param CAP Codice postale del centro da inserire
	 */
	public void insertCV(String Nome_centro, String Tipo_centro, String Qualificatore, String Nome_via, String Numero_civico, String Comune, String Provincia, int CAP) throws SQLException {
		Statement st = dbConnection.createStatement();
		Nome_centro = Nome_centro.replaceAll(" ", "_");
		st.execute(SQLCommands.InsertCentro(Nome_centro, Tipo_centro, Qualificatore, Nome_via, Numero_civico, Comune, Provincia, CAP));
		st.execute(SQLCommands.CreateTable_Vaccinati(Nome_centro));
		st.close();
	}
	/**
	 * Metodo per l'inserimento di un vaccinato nel database
	 * @param Nome_centro Nome del centro in cui è avvenuta la vaccinazione
	 * @param Nome Nome del vaccinato
	 * @param Cognome Cognome del vaccinato
	 * @param CodiceFiscale Codice Fiscale del vaccinato
	 * @param Vaccino_somministrato Vaccino somministrato
	 * @param Data_vaccinazione Data della vaccinazione	
	 * @param id ID della vaccinazione 
	 */
	public void insertVaccinato(String Nome_centro, String Nome, String Cognome, String CodiceFiscale, String Vaccino_somministrato, Date Data_vaccinazione, long id) throws SQLException {
		Statement st = dbConnection.createStatement();
		Nome_centro = Nome_centro.replaceAll(" ", "_");
		st.execute(SQLCommands.InsertVaccinato(Nome_centro, Nome, Cognome, CodiceFiscale, Vaccino_somministrato, id, Data_vaccinazione));
		st.close();
	}
	/**
	 * Metodo per l'inserimento di un cittadino nel database
	 * @param Nome Nome del cittadino
	 * @param Cognome Cognome del cittadino
	 * @param CodiceFiscale Codice Fiscale del cittadino
	 * @param Mail Indirizzo di posta elettronica del cittadino
	 * @param Userid Nome utente del cittadino inserito al momento della registrazione
	 * @param Password Password del cittaino inserita al momento della registrazione
	 * @param id_vaccinazione ID della vaccinazione fornito al momento della vaccinazione
	 */
	public void insertCittadino(String Nome, String Cognome, String CodiceFiscale, String Mail, String Userid, String Password, long id_vaccinazione) throws SQLException {
		Statement st = dbConnection.createStatement();
		st.execute(SQLCommands.InsertCittadino(Nome, Cognome, CodiceFiscale, Mail, Userid, Password, id_vaccinazione));
		st.close();
	}
	/**
	 * Metodo per l'inserimento di un evento avverso 
	 * @param Evento Tipologia dell'evento da inserire
	 * @param Intensita Intensita' dell'evento
	 * @param Vaccino Vaccino somministrato
	 * @param Nome_centro Nome del centro vaccinale in cui e' stato somministrato il vaccino
	 * @param Note Eventuali note aggiuntive alla notifica dell'evento avverso
	 * @param UserID Nome utente del segnalatore dell'evento avverso
	 */
	public void insertEventoAvverso(String Evento, int Intensita, String Vaccino, String Nome_centro, String Note, String UserID) throws SQLException {
		Statement st = dbConnection.createStatement();
		st.execute(SQLCommands.InsertEvento(Evento, Intensita, Vaccino, Nome_centro, Note, UserID));
		st.close();
	}
	/**
	 * Metodo per eseguire la query per l'autenticazione dei cittadini
	 * @param userID Nome utente del cittadino da autenticare
	 * @return risultato della query
	 */
	public ResultSet queryLogin(String userID) throws SQLException {
		Statement st = dbConnection.createStatement();
		ResultSet rs = st.executeQuery(SQLCommands.QueryLogin(userID));
		return rs;
	}
	/**
	 * Metodo per eseguire la query di ricerca per nome dei centri vaccinali
	 * @param nome stringa di ricerca del nome
	 * @return risultato della query
	 */
	public ResultSet queryCVNome(String nome) throws SQLException {
		Statement st = dbConnection.createStatement();
		ResultSet rs = st.executeQuery(SQLCommands.QueryCVNome(nome));
		return rs;
	}
	/**
	 * Metodo per eseguire la query per ottenere i nomi di tutti i centri vaccinali presenti nel database
	 * @return risultato della query
	 */
	public ResultSet queryCVOnlyNome() throws SQLException {
		Statement st = dbConnection.createStatement();
		ResultSet rs = st.executeQuery(SQLCommands.QUERYCVONLYNAME);
		return rs;
	}
	/**
	 * Metodo per eseguire la query di ricerca per comune e tipologia dei centri vaccinali
	 * @param comune stringa di ricerca del comune
	 * @param tipo tipologia di centro vaccinale
	 * @return risultato della query
	 */
	public ResultSet queryCVComuneTipo(String comune, String tipo) throws SQLException {
		Statement st = dbConnection.createStatement();
		ResultSet rs = st.executeQuery(SQLCommands.QueryCVComuneTipo(comune, tipo));
		return rs;
	}
	/**
	 * Metodo per eseguire la query per ottenere tutti gli id di vaccinazione di un dato centro vaccinale
	 * @param nome_centro centro di cui ottenere gli id
	 * @return risultato della query
	 */
	public ResultSet queryID(String nome_centro) throws SQLException {
		Statement st = dbConnection.createStatement();
		ResultSet rs = st.executeQuery(SQLCommands.QueryID(nome_centro));
		return rs;
	}
	/**
	 * Metodo per eseguire la query per ottenere l'id di vaccinazione di un dato utente
	 * @param user utente di cui si vuole ottenere l'id della vaccinazione
	 * @return risultato della query
	 */
	public ResultSet queryIDFromUser(String user) throws SQLException {
		Statement st = dbConnection.createStatement();
		ResultSet rs = st.executeQuery(SQLCommands.QueryIDFromUser(user));
		return rs;
	}
	/**
	 * Metodo per eseguire la query per ottenere il vaccino somministrato all'utente loggato
	 * @param nome_centro centro di vaccinazione dell'utente
	 * @param loggedID id della vaccinazione dell'utente
	 * @return risultato della query
	 */
	public ResultSet queryNome_VaccinoFromVaccinati(String nome_centro, long loggedID) throws SQLException {
		Statement st = dbConnection.createStatement();
		ResultSet rs = st.executeQuery(SQLCommands.QueryForEvento(nome_centro, loggedID));
		return rs;
	}
	/**
	 * Metodo per eseguire la query per ottenere i dati inerenti il numero di casi e l'intensita' media di un dato evento in un dato centro vaccinale
	 * @param nome_centro centro vaccinale di cui si vogliono ottenere i dati
	 * @param evento tipologia di evento di cui si vogliono ottenere i dati
	 * @return risultato della query
	 */
	public ResultSet queryEventiAvversi(String nome_centro, String evento) throws SQLException {
		Statement st = dbConnection.createStatement();
		ResultSet rs = st.executeQuery(SQLCommands.QueryEventiAvversi(nome_centro, evento));
		return rs;
	}
	/**
	 * Metodo utilizzato per inizializzare i driver JDBC e il database
	 */
	public void start() throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
		String url = "jdbc:postgresql://" + host + ":" + port + "/centri_vaccinali";
		dbConnection = DriverManager.getConnection(url, user, psw);
		createTables();
	}
	/**
	 * Metodo per connettersi al database
	 */
	public void exec() throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
		String url = "jdbc:postgresql://" + host + ":" + port + "/centri_vaccinali";
		dbConnection = DriverManager.getConnection(url, user, psw);
	}
}