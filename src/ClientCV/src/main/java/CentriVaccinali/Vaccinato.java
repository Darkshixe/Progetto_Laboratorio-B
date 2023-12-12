/**
 * @author Nicolo' Milo Varese 741297  
 * @author Niccolo' Gonzato Varese 741455 
 * @author Daniel Castelli Varese 740477 
 */
package CentriVaccinali;

import java.sql.Date;

/**
* Classe per la creazione del vaccinato
*/
public class Vaccinato {
	
	private String nome;
	private String cognome;
	private String codice_fiscale;
	private Date data_somministrazione;
	private String vaccino;
	private String centro;
	
	/**
	* Costruttore della Classe
	* @param n String nome del vaccinato
	* @param c String cognome del vaccinato
	* @param cf String codice fiscale vaccinato
	* @param d Date data di somministrazione del vaccino
	* @param v String vaccino 
	* @param cv String centro vaccinale
	*/
	public Vaccinato(String n, String cog, String cod, Date d, String v, String cen) {
		nome = n;
		cognome = cog;
		codice_fiscale = cod;
		data_somministrazione = d;
		vaccino = v;
		centro = cen;
	}

	/**
	  * Metodo per l'ottenimento del nome del vaccinato
	  * @return la stringa contenente il nome del vaccinato
	  */
	public String getNome() {
		return nome;
	}

	/**
	  * Metodo per l'ottenimento del cognome del vaccinato
	  * @return la stringa contenente il cognome del vaccinato
	  */
	public String getCognome() {
		return cognome;
	}

	/**
	  * Metodo per l'ottenimento del codice fiscale del vaccinato
	  * @return la stringa contenente il codice fiscale del vaccinato
	  */
	public String getCodice_fiscale() {
		return codice_fiscale;
	}

	/**
	  * Metodo per l'ottenimento della data di somministrazione del vaccinato
	  * @return l'oggetto {@code Date} contenente la data di somministrazione del vaccinato
	  */
	public Date getData_somministrazione() {
		return data_somministrazione;
	}

	/**
	  * Metodo per l'ottenimento del vaccino del vaccinato
	  * @return la stringa contenente il vaccino del vaccinato
	  */
	public String getVaccino() {
		return vaccino;
	}
	
	/**
	  * Metodo per l'ottenimento del centro del vaccinato
	  * @return la stringa contenente il centro del vaccinato
	  */
	public String getCentro() {
		return centro;
	}
	
	/**
	* Metodo che determina se i campi della classe sono nulli
	* @return {@code true} se anche solo uno dei campi e' nullo o vuoto 
	* {@code false} altrimenti
	*/
	public boolean isNull() {
		if(nome == null || nome == "" || cognome == null || cognome == "" || codice_fiscale == null || codice_fiscale == "" || data_somministrazione == null || vaccino == null || centro == null || centro == "")
			return true;
		else return false;
	}
}
