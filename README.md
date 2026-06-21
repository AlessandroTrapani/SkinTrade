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
* JavaScript base
* Apache Tomcat
* Eclipse IDE
* Git e GitHub

## Architettura del progetto

Il progetto segue la seguente struttura:

* **Model**: classi JavaBean che rappresentano le entità principali del progetto.
* **View**: pagine JSP che mostrano i dati all'utente.
* **Controller**: Servlet che ricevono le richieste, interagiscono con i DAO e inoltrano le risposte alle JSP.
* **DAO**: classi dedicate all'accesso al database tramite JDBC.
* **Filter**: classi usate per proteggere le pagine riservate agli utenti loggati e agli amministratori.

## Struttura principale

```text
src/main/java
├── controller
├── controller.admin
├── dao
├── filtro
├── modello
└── util

src/main/webapp
├── admin
├── css
├── immagini
├── js
├── pagine
├── WEB-INF
└── index.jsp
```

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

Il progetto utilizza due Filter principali.

### FiltroAdmin

Protegge le pagine dell'area admin:

```text
/admin/*
```

Solo gli utenti con ruolo `ADMIN` possono accedere a queste pagine.

### FiltroUtente

Protegge le pagine riservate agli utenti autenticati:

```text
/checkout
/storico-ordini
/dettaglio-ordine
```

Se un utente non autenticato tenta di accedere a queste pagine, viene reindirizzato alla pagina di login.

## Avvio del progetto

Per eseguire il progetto:

1. importare il progetto in Eclipse;
2. configurare Apache Tomcat;
3. creare il database MySQL `skintrade`;
4. creare le tabelle richieste;
5. configurare la password del database nella classe `ConnessioneDatabase`;
6. aggiungere il driver MySQL Connector/J al progetto;
7. avviare Tomcat da Eclipse;
8. aprire il sito nel browser.

Esempio URL:

```text
http://localhost/SkinTrade/index.jsp
```

oppure, se Tomcat usa la porta 8080:

```text
http://localhost:8080/SkinTrade/index.jsp
```
