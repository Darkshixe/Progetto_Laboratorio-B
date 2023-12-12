/**
 * @author Nicolo' Milo Varese 741297  
 * @author Niccolo' Gonzato Varese 741455 
 * @author Daniel Castelli Varese 740477 
 */
package Cittadini;

/**
* Classe che contiene le informazioni del cittadino
*/
public class Cittadino {
	
	private String nome;
	private String cognome;
	private String codice_fiscale;
	private String email;
	private String username;
	private String password;
	private String id;
	
	/**
	*Costruttore della Classe
	* @param String nome del cittadino
	* @param String cognome del cittadino
	* @param String codice fiscale del cittadino
	* @param String email del cittadino
	* @param String username del cittadino
	* @param String password del cittadino
	* @param String id del cittadino
	*/
	public Cittadino(String n, String cog, String cod, String e, String u, String p, String i) {
		nome = n;
		cognome = cog;
		codice_fiscale = cod;
		email = e;
		username = u;
		password = p;
		id = i;
	}
	
	/**
	  * Metodo per l'ottenimento del nome del cittadino
	  * @return la stringa contenente il nome del cittadino
	  */
	public String getNome() {
		return nome;
	}

	/**
	  * Metodo per l'ottenimento del cognome del cittadino
	  * @return la stringa contenente il cognome del cittadino
	  */
	public String getCognome() {
		return cognome;
	}

	/**
	  * Metodo per l'ottenimento del codice fiscale del cittadino
	  * @return la stringa contenente il codice fiscale del cittadino
	  */
	public String getCodice_fiscale() {
		return codice_fiscale;
	}

	/**
	  * Metodo per l'ottenimento dell' email del cittadino
	  * @return la stringa contenente l'email del cittadino
	  */
	public String getEmail() {
		return email;
	}

	/**
	  * Metodo per l'ottenimento del nome utente del cittadino
	  * @return la stringa contenente il nome utente del cittadino
	  */
	public String getUsername() {
		return username;
	}

	/**
	  * Metodo per l'ottenimento della password del cittadino
	  * @return la stringa contenente la password del cittadino
	  */
	public String getPassword() {
		return password;
	}
	
	/**
	  * Metodo per l'ottenimento dell' id di vaccinazione del cittadino
	  * @return la stringa contenente l' id di vaccinazione del cittadino
	  */
	public String getId() {
		return id;
	}
	
	/**
	* Metodo che determina se i campi della classe sono nulli
	* @return {@code true} se anche solo uno dei campi e' nullo o vuoto 
	* {@code false} altrimenti
	*/
	public boolean isNull() {
		if(nome == null || nome == "" || cognome == null || cognome == "" || codice_fiscale == null || codice_fiscale == "" || email == null || email == "" || username == null || username == "" || password == null || password == "" || id == null || id == "")
			return true;
		else return false;
	}

	
	
}
