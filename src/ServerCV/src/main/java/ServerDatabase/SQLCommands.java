/**
 * @author Nicolo' Milo Varese 741297  
 * @author Niccolo' Gonzato Varese 741455 
 * @author Daniel Castelli Varese 740477 
 */
package ServerDatabase;

import java.sql.Date;
/**
 * Classe di utilit� contenente gli script SQL usati dal Server per la creazione del database e delle sue tabelle,
 * per l'inserimento di nuovi dati e le varie query utili alla raccolta di questi ultimi
 *
 */
public class SQLCommands {
	
	/**
	 * Script SQL usato per la creazione della tabella CentriVaccinali
	 */
	protected static String CREATETABLE_CV = "CREATE TABLE IF NOT EXISTS centrivaccinali ( \r\n"
										   + " Nome_centro varchar(40) PRIMARY KEY, \r\n"
										   + " Tipo_centro varchar(20) NOT NULL, \r\n"
										   + " Qualificatore varchar(20) NOT NULL, \r\n"
										   + " Nome_via varchar(40) NOT NULL, \r\n"
										   + " Numero_civico varchar(10) NOT NULL, \r\n"
										   + " Comune varchar (30) NOT NULL, \r\n"
										   + " Provincia varchar (20) NOT NULL, \r\n"
										   + " CAP int NOT NULL \r\n"
										   + "); ";
	
	/**
	 * Script SQL usato per la creazione delle varie tabelle dei vaccinati per ogni centro vaccinale
	 * @param Nome_centro Nome del centro vaccinale usato per creare dinamicamente le tabelle
	 */
	protected static String CreateTable_Vaccinati(String Nome_centro) {
		return "CREATE TABLE IF NOT EXISTS vaccinati_" + Nome_centro + "( \r\n"
			 + " Nome_centro varchar(40) NOT NULL REFERENCES centrivaccinali ON DELETE RESTRICT, \r\n"
			 + " Nome varchar (20) NOT NULL, \r\n"
			 + " Cognome varchar(20) NOT NULL, \r\n"
			 + " CodiceFiscale varchar(16) UNIQUE NOT NULL, \r\n"
			 + " Vaccino_somministrato varchar(20) NOT NULL, \r\n"
			 + " ID_vaccinazione bigint PRIMARY KEY, \r\n"
			 + " Data_vaccinazione date NOT NULL \r\n"
			 + ");";
	}
	
	/**
	 * Script SQL usato per la creazione della tabella Cittadini
	 */
	protected static String CREATETABLE_CITTADINI = "CREATE TABLE IF NOT EXISTS cittadini( \r\n"
												  + "Nome varchar (20) NOT NULL, \r\n"
												  + " Cognome varchar(20) NOT NULL, \r\n"
												  + " CodiceFiscale varchar(16) NOT NULL , \r\n"
												  + " Mail varchar(30) NOT NULL, \r\n"
												  + " UserID varchar(30) PRIMARY KEY , \r\n"
												  + " Password varchar(20) UNIQUE NOT NULL, \r\n"
												  + " ID_vaccinazione bigint UNIQUE NOT NULL \r\n"
												  + ");";
	
	/**
	 * Script SQL usato per la creazione della tabella Eventi_avversi
	 */
	protected static String CREATETABLE_EVENTI_AVVERSI = "CREATE TABLE IF NOT EXISTS eventi_avversi( \r\n"
													   + " Evento varchar(20) NOT NULL, \r\n"
													   + " Intensita int NOT NULL, \r\n"
													   + " Vaccino varchar (20) NOT NULL, \r\n"
													   + " Nome_centro varchar(40) REFERENCES centrivaccinali, \r\n"
													   + " Note varchar(256), \r\n"
													   + " UserID varchar(30) REFERENCES Cittadini, \r\n"
													   + " PRIMARY KEY (UserID, Evento) \r\n"
													   + ");";
	
	/**
	 * Script SQL usato per l'inserimento di un nuovo centro vaccinale nella tabella CentriVaccinali
	 * @param Nome_centro Nome del centro da inserire nella tabella
	 * @param Tipo_centro Tipologia del centro da inserire nella tabella
	 * @param Qualificatore Qualificatore dell'indirizzo del centro da inserire nella tabella
	 * @param Nome_via Nome principale dell'indirizzo del centro da inserire nella tabella
	 * @param Numero_civico Numero civico dell'indirizzo del centro da inserire nella tabella
	 * @param Comune Comune del centro da inserire nella tabella
	 * @param Provincia Provincia del centro da inserire nella tabella
	 * @param CAP Codice Postale del centro da inserire nella tabella
	 */
	protected static String InsertCentro(String Nome_centro, String Tipo_centro, String Qualificatore, String Nome_via, String Numero_civico, String Comune, String Provincia, int CAP) {
		return "INSERT INTO centrivaccinali (Nome_centro, Tipo_centro, Qualificatore, Nome_via, Numero_civico, Comune, Provincia, CAP)"
			 + "VALUES ('" + Nome_centro + "', '" + Tipo_centro + "', '" + Qualificatore + "', '" + Nome_via + "', '" + Numero_civico + "', '" + Comune + "', '" + Provincia + "', '" + CAP + "');";
	}
	/**
	 * Script SQL usato per l'inserimento di un nuovo vaccinato nella tabella del suo centro di riferimento
	 * @param Nome_centro Nome del centro in cui inserire il vaccinato
	 * @param Nome Nome del vaccinato
	 * @param Cognome Cognome del vaccinato
	 * @param CodiceFiscale Codice Fiscale del vaccinato
	 * @param Vaccino_somministrato Vaccino somministrato
	 * @param id ID numerico univoco a 16 bit del vaccinato
	 * @param Data_vaccinazione Data della vaccinazione
	 */
	protected static String InsertVaccinato(String Nome_centro, String Nome, String Cognome, String CodiceFiscale, String Vaccino_somministrato, long id, Date Data_vaccinazione) {
		return "INSERT INTO vaccinati_"+ Nome_centro +" (Nome_centro, Nome, Cognome, CodiceFiscale, Vaccino_somministrato, id_vaccinazione, Data_vaccinazione)"
			 + "VALUES ('" + Nome_centro + "', '" + Nome + "', '" + Cognome + "', '" + CodiceFiscale + "', '" + Vaccino_somministrato + "', " + id + ", '" + Data_vaccinazione + "'); ";
	}
	/**
	 * Script SQL usato per l'inserimento di un nuovo cittadino vaccinato nella tabella Cittadini
	 * @param Nome Nome del cittadino
	 * @param Cognome Cognome del cittadino
	 * @param CodiceFiscale Codice Fiscale del cittadino
	 * @param Mail Indirizzo di posta elettronica del cittadino
	 * @param Userid Nome utente del cittadino inserito al momento della registrazione
	 * @param Password Password del cittadino inserita al momento della registrazione
	 * @param id_vaccinazione ID numerico univoco a 16 bit fornito al momento della vaccinazione
	 */
	protected static String InsertCittadino(String Nome, String Cognome, String CodiceFiscale, String Mail, String Userid, String Password, long id_vaccinazione) {
		return "INSERT INTO cittadini (Nome, Cognome, CodiceFiscale, Mail, Userid, Password, ID_vaccinazione)"
			 + "VALUES ('" + Nome + "', '" + Cognome + "', '" + CodiceFiscale + "', '" + Mail + "', '" + Userid + "', '" + Password + "', " + id_vaccinazione + ");"; 
	}
	/**
	 * Script SQL usato per l'inserimento di un nuovo evento avverso al vaccino da parte di un cittadino
	 * @param Evento Tipologia di evento avverso
	 * @param Intensita Intensita' dell'evento
	 * @param Vaccino Vaccino somministrato da cui e' causato l'evento
	 * @param Nome_centro Centro in cui e' stato somministrato il vaccino
	 * @param Note Eventuali note aggiuntive atte a descrivere ulteriormente l'evento avverso
	 * @param UserID Nome utente del cittadino che sta notificando l'evento
	 */
	protected static String InsertEvento(String Evento, int Intensita, String Vaccino, String Nome_centro, String Note, String UserID) { 
		return "INSERT INTO eventi_avversi (Evento, Intensita, Vaccino, Nome_centro, Note, UserID)" 
			 + "VALUES ('" + Evento + "', '" + Intensita + "', '" + Vaccino + "', '" + Nome_centro + "', '" + Note + "', '" + UserID + "');"; 
	}
	
	/**
	 * Script SQL per il processo di autenticazione
	 * @param userid Nome utente fornito per l'autenticazione
	 */
	protected static String QueryLogin(String userid) {
		return "SELECT Password\r\n"
			 + "FROM Cittadini\r\n"
			 + "WHERE UserID = '" + userid + "'";
	}
	/**
	 * Script SQL per la ricerca per nome dei centri vaccinali
	 * @param nome_centro stringa utilizzata per la ricerca per nome del centro vaccinale
	 */
	protected static String QueryCVNome(String nome_centro) {
		return "SELECT * \r\n"
			 + "FROM centrivaccinali \r\n"
			 + "WHERE Nome_centro ILIKE '%" + nome_centro + "%'";
	}
	/**
	 * Script SQL per ottenere tutti i nomi dei centri vaccinali presenti nel Database
	 */
	protected static String QUERYCVONLYNAME = "SELECT Nome_centro \r\n"
			 								+ "FROM centrivaccinali \r\n";
	
	/**
	 * Script SQL per la ricerca per comune e tipologia dei centri vaccinali
	 * @param comune stringa utilizzata per la ricerca per comune del centro vaccinale
	 * @param tipo tipologia del centro da ricercare
	 */
	protected static String QueryCVComuneTipo(String comune, String tipo) {
		return "SELECT * \r\n"
			 + "FROM centrivaccinali \r\n"
			 + "WHERE Comune ILIKE '%" + comune + "%' AND Tipo_centro = '" + tipo + "'";
	}
	/**
	 * Script SQL per ottenere tutti gli id delle vaccinazioni in un dato centro vaccinale
	 * @param nome_centro nome del centro di cui si vogliono ottenere gli id
	 */
	protected static String QueryID(String nome_centro) {
		return "SELECT id_vaccinazione \r\n"
			 + "FROM vaccinati_" + nome_centro;
	}
	/**
	 * Script SQL per ottenere il centro e il vaccino sommistrato dato l'id dell'utente attualmente loggato, in modo da poter inserire un eventuale evento avverso nel sistema
	 * @param nome_centro Nome del centro da cui ottenere il vaccino somministrato
	 * @param loggedID id della vaccinazione dell'utente loggato attualmente
	 */
	protected static String QueryForEvento(String nome_centro, long loggedID) {
		return "SELECT Vaccino_somministrato \r\n"
			 + "FROM vaccinati_" + nome_centro + " \r\n"
			 + "WHERE id_vaccinazione = " + loggedID;
	}
	/**
	 * Script SQL per ottenere l'id della vaccinazione dell'utente attualmente loggato
	 * @param user Nome utente del cittadino attualmente loggato
	 */
	protected static String QueryIDFromUser(String user) {
		return  "SELECT id_vaccinazione \r\n" +
				"FROM cittadini \r\n" +
				"WHERE userid = '" + user + "'";
	}
	/**
	 * Script SQL per ottenere i casi e l'intensita' media di un evento avverso in un dato centro vaccinale
	 * @param nome_centro Nome del centro vaccinale di cui calcolare i dati
	 * @param evento Tipologia di evento di cui calcolare i dati
	 */
	protected static String QueryEventiAvversi(String nome_centro, String evento) {
		return "SELECT COUNT (Evento) AS Casi, AVG(Intensita) AS IntensitaMedia \r\n"
	         + "FROM Eventi_Avversi \r\n"
	         + "WHERE Nome_centro = '"+ nome_centro +"' AND Evento = '" + evento + "'";
	}
}
