# Centri Vaccinali🏥
## Progetto Laboratorio B

### Requirements

Per testare e/o utilizzare l'applicazione è prima necessario disporre di un server PostgreSQL.
Dopodichè sarà necessario creare un database nominato `centri_vaccinali`

### Setup

#### Server
1. Eseguire nella cartella ServerCV i comandi in questo ordine
   - `mvn compile`
   - `mvn javafx:run`
2. Inserire i propri dati del DBMS PostgreSQL e connettersi al database

#### Client
1. Eseguire nella cartella ClientCV i comandi in questo ordine
   - `mvn compile`
   - `mvn javafx:run`
2. in entrambe le cartelle è possibile eseguire il comando
   - `mvn javadoc:javadoc`
   
   per generare la documentazione

### Test
Effettuati test sui seguenti sistemi operativi:
- Ubuntu 16.04.7
- Windows 10