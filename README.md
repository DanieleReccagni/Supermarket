# Supermarket
## Reminder
Ricordarsi di creare un nuovo database di nome "supermarket" su MySQL.

## Il backend
### Product.java
Product è una classe resa persistente dall'annotazione @Entity che rappresenta un generico prodotto di un supermercato, contraddistinto da:
- codice (stringa, chiave primaria, non nullo, non vuoto);
- descrizione (stringa, non nulla, non vuota);
- categoria (stringa, non nulla, non vuota);
- quantità (intera, non nulla, non negativa).

Sono presenti i metodi getter e setter per ogni attributo e, per rendere la classe completa, sono stati sovrascritti anche i metodi toString(), equals() e hashCode(). In particolare, il metodo equals() è stato utilizzato nel testSaveProduct di SupermarketControllerTests.

### SupermarketRepository.java
SupermarketRepository è un'interfaccia che definisce operazioni CRUD per l'entità Product.  
Essa estende JpaRepository, dalla quale eredita dei metodi predefiniti per Product con chiave String, e definisce un metodo find che tramite una query permette di cercare prodotti in base al valore degli attributi codice, descrizione e categoria. Esso funziona anche inserendo più filtri contemporaneamente, nonché inserendo parzialmente la parola con cui filtrare (es. se filtro per codice = "P00" verranno visualizzati tutti i prodotti con codice che inizia per P00).

### SupermarketService.java
SupermarketService è una classe che funge da intermediaria tra il Repository e il Controller. Gestisce varie operazioni relative agli oggetti Product, fornendo una buona separazione delle responsabilità.  
I metodi contenuti in questa classe sono relativamente semplici, in quanto richiamano dei metodi già definiti da JpaRepository nonché il metodo find da me definito.

### SupermarketController.java
SupermarketController è una classe che gestisce le interazioni utente relative alla gestione dei prodotti. Essa implementa vari metodi @GetMapping e @PostMapping per gestire le operazioni CRUD sui prodotti, utilizzando il Service per accedere ai dati ed eseguire le operazioni stesse.  
I metodi aggiungono o modificano attributi nel Model del MVC e restituiscono il nome delle viste (index, create, update, search) per la visualizzazione delle pagine HTML corrispondenti.  
Un'attenzione particolare per il metodo saveProduct: esso è utilizzato dalle pagine di inserimento di un prodotto (create.html e update.html) per salvare i dati nel database. In particolare, come verrà spiegato successivamente nelle sezioni "create.html" e "update.html", esso blocca tutti i casi in cui nel form di inserimento vengano inseriti dati in qualche modo errati.

## I test
Ho scelto di testare tutti i metodi che ho reputato "non banali" e non ereditati da JpaRepository, ossia quelli contenuti nel Controller e il metodo find da me definito nel Repository.

### SupermarketControllerTests.java
SupermarketControllerTests contiene un test per ogni funzionalità di SupermarketController.
Mi soffermo in particolare sui seguenti metodi da me utilizzati:
- Mockito.when: serve per stabilire il comportamento di un mock quando viene chiamato un metodo su di esso;
- Mockito.verify: serve per verificare che un metodo di un mock sia stato chiamato con certi parametri durante il test;
- perform di MockMvc: serve per simulare una richiesta HTTP e successivamente eseguire delle operazioni di controllo sul suo esito.

### SupermarketRepositoryTests.java
SupermarketRepositoryTests contiene dei test per alcune combinazioni di input del metodo find che ho definito in SupermarketRepository.

## Il frontend
### index.html
index è la homepage del progetto. Essa utilizza il CSS index.css e permette di eseguire le seguenti operazioni:
- creare un nuovo prodotto;
- cercare i dettagli di un prodotto dato il suo codice;
- visualizzare la lista completa dei prodotti contenuti nel database;
- filtrare i dati della lista dei prodotti in base al codice, alla descrizione e/o alla categoria;
- modificare i dettagli dei prodotti già inseriti;
- eliminare un prodotto.

### create.html
create è la pagina dedicata all'inserimento di un nuovo prodotto. Essa utilizza il CSS pages.css e permette di inserire codice, descrizione, categoria e quantità del nuovo prodotto.  
ATTENZIONE: la pagina bloccherà l'inserimento in caso di:
- codice già presente nel database;
- uno o più campi vuoti;
- quantità intera non positiva;
- quantità non intera.

### update.html
update è la pagina dedicata alla modifica di un prodotto già inserito. Essa utilizza il CSS pages.css e permette di modificare descrizione, categoria e quantità del prodotto selezionato.  
ATTENZIONE: la pagina bloccherà la modifica in caso di:
- uno o più campi vuoti;
- quantità intera non positiva;
- quantità non intera.

### search.html
search è la pagina dedicata alla ricerca di un prodotto presente nel database. Essa utilizza il CSS pages.css e permette l'inserimento del codice di un prodotto, stampandone successivamente i dettagli.  
ATTENZIONE: la pagina bloccherà la ricerca in caso di codice non presente nel database.
