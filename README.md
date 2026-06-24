# SkinTrade

SkinTrade è un progetto web sviluppato per il corso di **Tecnologie Software per il Web**.

Il sito simula un e-commerce dedicato alla compravendita di oggetti digitali per videogiochi, come skin, adesivi e oggetti cosmetici.

## Tecnologie utilizzate

* Java
* Servlet
* JSP
* JDBC
* DAO
* JavaBean
* MySQL
* HTML
* CSS
* Apache Tomcat
* Eclipse IDE
* Git e GitHub
* AJAX
* JavaScript
* DataSource
* JNDI

## Architettura del progetto

Il progetto segue una struttura ispirata al pattern MVC:

- **Model**: classi JavaBean contenute nel package `model`, che rappresentano le entità principali del progetto.
- **View**: pagine JSP contenute in `WEB-INF/view`, non accessibili direttamente dal browser.
- **Controller**: Servlet contenute nei package `control` e `control.admin`, che ricevono le richieste, interagiscono con i DAO e inoltrano i dati alle JSP.
- **DAO**: classi contenute nel package `dao`, usate per incapsulare la logica di accesso al database.
- **Filter**: classi contenute nel package `filtro`, usate per proteggere le aree riservate agli utenti loggati e agli amministratori.
- **Util**: classi di supporto, tra cui la connessione al database tramite DataSource.

## Struttura principale


src/main/java
├── control
├── control.admin
├── dao
├── filtro
├── model
└── util

src/main/webapp
├── images
│   └── prodotti
├── scripts
├── styles
├── META-INF
├── WEB-INF
│   ├── lib
│   └── view
│       ├── admin
│       └── pagine
└── index.jsp

## Funzionalità utente

L'utente non autenticato può:

* visualizzare la homepage;
* consultare il catalogo prodotti;
* cercare prodotti per nome;
* filtrare prodotti per gioco e categoria;
* visualizzare il dettaglio di un prodotto;
* aggiungere prodotti al carrello;
* registrarsi;
* effettuare il login.

L'utente autenticato può:

* gestire il proprio carrello;
* modificare la quantità dei prodotti nel carrello;
* rimuovere prodotti dal carrello;
* procedere al checkout;
* confermare un ordine;
* visualizzare lo storico dei propri ordini;
* visualizzare il dettaglio di un ordine effettuato;
* effettuare il logout.

## Funzionalità amministratore

L'amministratore può accedere a un'area riservata e gestire il sito.

Funzionalità disponibili:

* visualizzazione dashboard admin;
* visualizzazione elenco prodotti;
* inserimento nuovo prodotto;
* modifica prodotto esistente;
* eliminazione logica prodotto;
* visualizzazione di tutti gli ordini;
* visualizzazione dettaglio ordine;
* aggiornamento dello stato di un ordine;
* filtro ordini per data, ID utente ed email di consegna.

## Gestione del carrello

Il carrello viene gestito tramite sessione HTTP.

Ogni utente può aggiungere prodotti al carrello anche prima del login.
Il checkout richiede invece che l'utente sia autenticato.

Il sistema controlla che la quantità inserita nel carrello non superi la quantità disponibile nel database.

## Gestione degli ordini

Quando un utente conferma un ordine:

1. viene creato un nuovo record nella tabella `ordini`;
2. vengono salvati i prodotti acquistati nella tabella `dettagli_ordine`;
3. viene aggiornata la quantità disponibile dei prodotti;
4. il carrello viene svuotato;
5. l'utente viene reindirizzato allo storico ordini.

Gli ordini mantengono lo storico dei prodotti acquistati anche se successivamente il prodotto viene modificato o eliminato logicamente dal catalogo.

## Gestione prodotti

I prodotti possiedono uno stato, ad esempio:

* `DISPONIBILE`
* `NON_DISPONIBILE`
* `ELIMINATO`

Nel catalogo pubblico vengono mostrati solo i prodotti con:

```text
stato = DISPONIBILE
quantita > 0
```

Nell'area admin, invece, vengono mostrati tutti i prodotti, compresi quelli eliminati logicamente o non disponibili.

## Database

Il database utilizzato è MySQL.

Nome database:

```sql
skintrade
```

Tabelle principali:

```text
prodotti
utenti
ordini
dettagli_ordine
```

### Tabella prodotti

Contiene le informazioni dei prodotti presenti nel catalogo:

* id
* nome
* gioco
* categoria
* rarità
* condizione
* prezzo
* quantità
* immagine
* descrizione
* stato
* data inserimento

### Tabella utenti

Contiene gli utenti registrati:

* id
* nome
* cognome
* email
* password
* ruolo
* data registrazione

I ruoli gestiti sono:

```text
UTENTE
ADMIN
```

### Tabella ordini

Contiene gli ordini effettuati dagli utenti:

* id
* id_utente
* totale
* email_consegna
* note_consegna
* metodo_pagamento
* stato
* data_ordine

### Tabella dettagli_ordine

Contiene i prodotti associati a ogni ordine:

* id
* id_ordine
* id_prodotto
* nome_prodotto
* prezzo
* quantità

## Credenziali di test

Utente normale:

```text
Email: utente@skintrade.it
Password: utente
```

Amministratore:

```text
Email: admin@skintrade.it
Password: admin
```

## Protezione delle pagine

Il progetto utilizza la sessione HTTP per mantenere le informazioni dell'utente autenticato.

Al momento del login o della registrazione viene salvato in sessione:

```text
utenteLoggato
tokenAccesso
```

Il token viene controllato dai Filter insieme al ruolo dell'utente.

### FiltroAdmin

Protegge le pagine dell'area admin:

```text
/admin/*
```

Solo gli utenti con ruolo `ADMIN` e token valido possono accedere a queste pagine.

### FiltroUtente

Protegge le pagine riservate agli utenti autenticati:

```text
/checkout
/storico-ordini
/dettaglio-ordine
```

Se un utente non autenticato o senza token valido tenta di accedere a queste pagine, viene reindirizzato alla pagina di login.

## Validazione JavaScript e AJAX

Il progetto utilizza JavaScript esterno, inserito nella cartella `scripts`, per validare alcuni form lato client.

Nella pagina di registrazione vengono validati:

- nome;
- cognome;
- email;
- password.

La validazione utilizza espressioni regolari e mostra i messaggi di errore direttamente nella pagina modificando il DOM, senza usare finestre `alert`.

La validazione viene eseguita:

- quando l'utente termina l'inserimento di un campo, tramite evento `change`;
- quando l'utente tenta di inviare il form, tramite evento `submit`.

Il progetto utilizza anche AJAX per verificare se l'email inserita nella registrazione è già presente nel database.  
La chiamata viene effettuata verso la Servlet:

```text
/verifica-email
```
La Servlet restituisce una risposta JSON e la pagina aggiorna dinamicamente il messaggio mostrato all'utente.

## Accesso al database

L'accesso al database MySQL viene gestito tramite DataSource configurato su Tomcat.

La risorsa JNDI utilizzata è:

```text
jdbc/skintrade
```
La classe ConnessioneDatabase recupera il DataSource tramite lookup JNDI e fornisce connessioni ai DAO.

Questa soluzione evita di creare manualmente le connessioni con DriverManager e rispetta il requisito di utilizzare un DataSource per la persistenza dei dati.

## Avvio del progetto

Per eseguire il progetto:

1. importare il progetto in Eclipse;
2. configurare Apache Tomcat;
3. creare il database MySQL `skintrade`;
4. creare le tabelle richieste;
5. configurare il DataSource `jdbc/skintrade` in `META-INF/context.xml`;
6. verificare che il driver MySQL Connector/J sia presente in `WEB-INF/lib` o nella cartella `lib` di Tomcat;
7. avviare Tomcat;
8. aprire il sito nel browser.

Esempio URL:

```text
http://localhost/SkinTrade/index.jsp
```

oppure, se Tomcat usa la porta 8080:

```text
http://localhost:8080/SkinTrade/index.jsp
```