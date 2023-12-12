/**
 * @author Nicolo' Milo Varese 741297  
 * @author Niccolo' Gonzato Varese 741455 
 * @author Daniel Castelli Varese 740477 
 */
package Cittadini;

/**
* Classe che contiene le informazioni di un evento avverso e i metodi per recuperarle
*/
public class EventoAvverso {
	
	private String nome;
	private Double casi;
	private Double media;
	/**
	 * Costruttore della classe
	 * @param n tipo di evento
	 * @param c numero di casi dell'evento
	 * @param m intendita' media dell'evento
	 */
	public EventoAvverso(String n, Double c, Double m) {
		nome = n;
		casi = c;
		media = m;
	}
	/**
	  * Metodo per l'ottenimento del tipo di evento
	  * @return la stringa contenente il tipo di evento
	  */
	public String getNome() {
		return nome;
	}

	/**
	  * Metodo per l'ottenimento del numero di casi dell'evento
	  * @return il numero reale contenente il numero di casi dell'evento
	  */
	public Double getCasi() {
		return casi;
	}

	/**
	  * Metodo per l'ottenimento del intensita' media dell'evento
	  * @return il numero reale contenente l'intensita' media dell'evento
	  */
	public Double getMedia() {
		return media;
	}
}