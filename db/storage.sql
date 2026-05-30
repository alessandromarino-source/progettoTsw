-- Creazione del database se non esiste e selezione
CREATE DATABASE IF NOT EXISTS ecommerce_agroalimentare;
USE ecommerce_agroalimentare;

-- Rimozione delle tabelle se esistono già per evitare conflitti nei test
DROP TABLE IF EXISTS DETTAGLIO_ORDINE;
DROP TABLE IF EXISTS METODO_PAGAMENTO;
DROP TABLE IF EXISTS ORDINE;
DROP TABLE IF EXISTS PRODOTTO;
DROP TABLE IF EXISTS PRODUTTORE;
DROP TABLE IF EXISTS UTENTE;

-- 1. Tabella UTENTE (Con campo Ruolo per Admin/Clienti)
CREATE TABLE UTENTE (
    ID_Utente INT AUTO_INCREMENT,
    Nome VARCHAR(50) NOT NULL,
    Cognome VARCHAR(50) NOT NULL,
    Email VARCHAR(100) NOT NULL UNIQUE,
    Password VARCHAR(255) NOT NULL,
    Indirizzo_Spedizione VARCHAR(255) NOT NULL,
    Telefono VARCHAR(20),
    Ruolo VARCHAR(20) DEFAULT 'cliente', -- 'cliente' o 'admin'
    PRIMARY KEY (ID_Utente)
);

-- 2. Tabella PRODUTTORE
CREATE TABLE PRODUTTORE (
    ID_Produttore INT AUTO_INCREMENT,
    Ragione_Sociale VARCHAR(150) NOT NULL,
    Partita_IVA VARCHAR(11) NOT NULL UNIQUE,
    Sede_Legale VARCHAR(255) NOT NULL,
    Descrizione_Azienda TEXT,
    PRIMARY KEY (ID_Produttore)
);

-- 3. Tabella PRODOTTO (Aggiornata con i campi esatti della slide del prof)
CREATE TABLE PRODOTTO (
    ID_Prodotto INT AUTO_INCREMENT,
    Nome VARCHAR(100) NOT NULL,
    Descrizione TEXT,
    Prezzo_Unitario DECIMAL(10, 2) NOT NULL,
    Quantita_Disponibile DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    Categoria VARCHAR(50) NOT NULL,
    Certificazioni VARCHAR(100),
    ID_Produttore INT NOT NULL,
    Attivo BOOLEAN DEFAULT TRUE, -- Per la cancellazione logica (Soft Delete)
    path VARCHAR(255),           -- Nome esatto richiesto dalla slide del prof per l'immagine
    mime_type VARCHAR(50),       -- Nome esatto richiesto dalla slide del prof per il tipo di file
    PRIMARY KEY (ID_Prodotto),
    FOREIGN KEY (ID_Produttore) REFERENCES PRODUTTORE(ID_Produttore)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

-- 4. Tabella ORDINE
CREATE TABLE ORDINE (
    Numero_Ordine INT AUTO_INCREMENT,
    ID_Utente INT NOT NULL,
    Data_Ordine DATETIME DEFAULT CURRENT_TIMESTAMP,
    Stato_Ordine VARCHAR(30) DEFAULT 'In lavorazione',
    Totale_Importo DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (Numero_Ordine),
    FOREIGN KEY (ID_Utente) REFERENCES UTENTE(ID_Utente)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

-- 5. Tabella METODO_PAGAMENTO
CREATE TABLE METODO_PAGAMENTO (
    ID_Pagamento INT AUTO_INCREMENT,
    Numero_Ordine INT NOT NULL UNIQUE,
    Tipo VARCHAR(50) NOT NULL,
    Stato_Pagamento VARCHAR(30) NOT NULL,
    PRIMARY KEY (ID_Pagamento),
    FOREIGN KEY (Numero_Ordine) REFERENCES ORDINE(Numero_Ordine)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- 6. Tabella DETTAGLIO_ORDINE
CREATE TABLE DETTAGLIO_ORDINE (
    ID_Dettaglio INT AUTO_INCREMENT,
    Numero_Ordine INT NOT NULL,
    ID_Prodotto INT NOT NULL,
    Quantita_Acquistata DECIMAL(10, 2) NOT NULL,
    Prezzo_Vendita_Storico DECIMAL(10, 2) NOT NULL, -- Prezzo congelato al momento dell'acquisto
    PRIMARY KEY (ID_Dettaglio),
    UNIQUE KEY (Numero_Ordine, ID_Prodotto),
    FOREIGN KEY (Numero_Ordine) REFERENCES ORDINE(Numero_Ordine)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (ID_Prodotto) REFERENCES PRODOTTO(ID_Prodotto)
        ON DELETE RESTRICT ON UPDATE CASCADE
);