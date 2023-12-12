/**
 * @author Nicolo' Milo Varese 741297  
 * @author Niccolo' Gonzato Varese 741455 
 * @author Daniel Castelli Varese 740477 
 */
package CentriVaccinali;

/**
 * Classe che identifica un centro vaccinale
 *
 */
public class CentroVaccinale {
	
	private String nome;
	private String tipo;
	private String qualificatore_indirizzo;
	private String nome_indirizzo;
	private String civico;
	private String comune;
	private String provincia;
	private String CAP;
	
	/**
	 * Costruttore della Classe
	 * @param n nome del centro vaccinale
	 * @param t tipo del centro vaccinale
	 * @param q qualificatore dell'indirizzo del centro vaccinale
	 * @param n_ind nome dell'indirizzo del centro vaccinale
	 * @param civ civico dell'indirizzo del centro vaccinale
	 * @param com comune dell'indirizzo del centro vaccinale
	 * @param p provincia dell'indirizzo del centro vaccinale
	 * @param cap CAP dell'indirizzo del centro vaccinale
	 */
	public CentroVaccinale(String n, String t, String q, String n_ind, String civ, String com, String p, String cap) {
		nome = n;
		tipo = t;
		qualificatore_indirizzo = q;
		nome_indirizzo = n_ind;
		civico = civ;
		comune = com;
		provincia = p;
		CAP = cap;
	}
	 /**
	  * Metodo per l'ottenimento del nome del centro vaccinale
	  * @return la stringa contenente il nome del centro vaccinale
	  */
	public String getNome() {
		return nome;
	}
	/**
	  * Metodo per l'ottenimento della tipologia del centro vaccinale
	  * @return la stringa contenente la tipologia del centro vaccinale
	  */
	public String getTipo() {
		return tipo;
	}
	/**
	  * Metodo per l'ottenimento del qualificatore dell'indirizzo del centro vaccinale
	  * @return la stringa contenente il qualificatore dell'indirizzo del centro vaccinale
	  */
	public String getQualificatoreIndirizzo() {
		return qualificatore_indirizzo;
	}
	/**
	  * Metodo per l'ottenimento del nome dell'indirizzo del centro vaccinale
	  * @return la stringa contenente il nome dell'indirizzo del centro vaccinale
	  */
	public String getNomeIndirizzo() {
		return nome_indirizzo;
	}
	/**
	  * Metodo per l'ottenimento del civico dell'indirizzo del centro vaccinale
	  * @return la stringa contenente il civico dell'indirizzo del centro vaccinale
	  */
	public String getCivicoIndirizzo() {
		return civico;
	}
	/**
	  * Metodo per l'ottenimento del comune del centro vaccinale
	  * @return la stringa contenente il comune del centro vaccinale
	  */
	public String getComuneIndirizzo() {
		return comune;
	}
	/**
	  * Metodo per l'ottenimento della provincia del centro vaccinale
	  * @return la stringa contenente la provincia del centro vaccinale
	  */
	public String getProvinciaIndirizzo() {
		return provincia;
	}
	/**
	  * Metodo per l'ottenimento del CAP del centro vaccinale
	  * @return la stringa contenente il CAP del centro vaccinale
	  */
	public String getCAPIndirizzo() {
		return CAP;
	}
	/**
	 * Metodo per l'ottenimento di nome, tipo, comune e provincia del centro vaccinale
	 * @return la stringa contenente nome, tipo, comune e provincia del centro vaccinale
	 */
	public String getInfo() {
		return nome + ", "+ tipo + ", " + comune + ", " + provincia;
	}
	/**
	 * Metodo che determina se i campi della classe sono nulli
	 * @return {@code true} se anche solo uno dei campi e' nullo o vuoto 
	 * {@code false} altrimenti
	 */
	public boolean isNull() {
		if(nome == null || nome == "" || tipo == null  || tipo == "" || qualificatore_indirizzo == null || qualificatore_indirizzo == "" || nome_indirizzo == null || nome_indirizzo == "" || civico == null || civico == "" || comune == null || comune == "" || provincia == null || provincia == "" || CAP == null || CAP == "") {
			return true;
		}
		else return false;
	}
}
