package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modello.Carrello;
import modello.ElementoCarrello;
import modello.Ordine;
import util.ConnessioneDatabase;

public class OrdineDAO {

    public boolean salvaOrdine(Ordine ordine, Carrello carrello) {
        String sqlOrdine = "INSERT INTO ordini "
                + "(id_utente, totale, email_consegna, note_consegna, metodo_pagamento, stato) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        String sqlDettaglio = "INSERT INTO dettagli_ordine "
                + "(id_ordine, id_prodotto, nome_prodotto, prezzo, quantita) "
                + "VALUES (?, ?, ?, ?, ?)";

        String sqlAggiornaQuantita = "UPDATE prodotti SET quantita = quantita - ? WHERE id = ?";

        Connection connessione = null;

        try {
            connessione = ConnessioneDatabase.getConnessione();
            connessione.setAutoCommit(false);

            int idOrdineCreato;

            try (
                PreparedStatement statementOrdine = connessione.prepareStatement(
                        sqlOrdine,
                        PreparedStatement.RETURN_GENERATED_KEYS
                )
            ) {
                statementOrdine.setInt(1, ordine.getIdUtente());
                statementOrdine.setDouble(2, ordine.getTotale());
                statementOrdine.setString(3, ordine.getEmailConsegna());
                statementOrdine.setString(4, ordine.getNoteConsegna());
                statementOrdine.setString(5, ordine.getMetodoPagamento());
                statementOrdine.setString(6, ordine.getStato());

                statementOrdine.executeUpdate();

                try (ResultSet chiavi = statementOrdine.getGeneratedKeys()) {
                    if (chiavi.next()) {
                        idOrdineCreato = chiavi.getInt(1);
                    } else {
                        connessione.rollback();
                        return false;
                    }
                }
            }

            try (
                PreparedStatement statementDettaglio = connessione.prepareStatement(sqlDettaglio);
                PreparedStatement statementQuantita = connessione.prepareStatement(sqlAggiornaQuantita)
            ) {
                for (ElementoCarrello elemento : carrello.getElementi()) {
                    statementDettaglio.setInt(1, idOrdineCreato);
                    statementDettaglio.setInt(2, elemento.getProdotto().getId());
                    statementDettaglio.setString(3, elemento.getProdotto().getNome());
                    statementDettaglio.setDouble(4, elemento.getProdotto().getPrezzo());
                    statementDettaglio.setInt(5, elemento.getQuantita());
                    statementDettaglio.executeUpdate();

                    statementQuantita.setInt(1, elemento.getQuantita());
                    statementQuantita.setInt(2, elemento.getProdotto().getId());
                    statementQuantita.executeUpdate();
                }
            }

            connessione.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();

            if (connessione != null) {
                try {
                    connessione.rollback();
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }

        } finally {
            if (connessione != null) {
                try {
                    connessione.setAutoCommit(true);
                    connessione.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }
    
    public ArrayList<Ordine> trovaOrdiniPerUtente(int idUtente) {
        ArrayList<Ordine> ordini = new ArrayList<>();

        String sql = "SELECT * FROM ordini WHERE id_utente = ? ORDER BY data_ordine DESC";

        try (
            Connection connessione = ConnessioneDatabase.getConnessione();
            PreparedStatement statement = connessione.prepareStatement(sql)
        ) {
            statement.setInt(1, idUtente);

            try (ResultSet risultato = statement.executeQuery()) {
                while (risultato.next()) {
                    Ordine ordine = new Ordine();

                    ordine.setId(risultato.getInt("id"));
                    ordine.setIdUtente(risultato.getInt("id_utente"));
                    ordine.setTotale(risultato.getDouble("totale"));
                    ordine.setEmailConsegna(risultato.getString("email_consegna"));
                    ordine.setNoteConsegna(risultato.getString("note_consegna"));
                    ordine.setMetodoPagamento(risultato.getString("metodo_pagamento"));
                    ordine.setStato(risultato.getString("stato"));
                    ordine.setDataOrdine(risultato.getString("data_ordine"));

                    ordini.add(ordine);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ordini;
    }
}