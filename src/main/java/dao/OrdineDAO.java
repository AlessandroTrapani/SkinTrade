package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Carrello;
import model.DettaglioOrdine;
import model.ElementoCarrello;
import model.Ordine;
import util.ConnessioneDatabase;
import java.util.ArrayList;
/**
 * DAO dedicato alla gestione degli ordini.
 * Si occupa del salvataggio degli ordini, dei dettagli ordine
 * e delle interrogazioni usate da utente e amministratore.
 */
public class OrdineDAO {

/**
* Salva un ordine e i relativi dettagli in un'unica transazione.
* Se una delle operazioni fallisce, viene eseguito il rollback.
*/
	public boolean salvaOrdine(Ordine ordine, Carrello carrello) {
		String sqlOrdine = "INSERT INTO ordini "
				+ "(id_utente, totale, email_consegna, note_consegna, metodo_pagamento, stato) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";

		String sqlDettaglio = "INSERT INTO dettagli_ordine "
				+ "(id_ordine, id_prodotto, nome_prodotto, prezzo, quantita) " + "VALUES (?, ?, ?, ?, ?)";

		String sqlAggiornaQuantita = "UPDATE prodotti SET quantita = quantita - ? WHERE id = ?";

		Connection connessione = null;

		try {
			connessione = ConnessioneDatabase.getConnessione();
			connessione.setAutoCommit(false);

			int idOrdineCreato;

			try (PreparedStatement statementOrdine = connessione.prepareStatement(sqlOrdine,
					PreparedStatement.RETURN_GENERATED_KEYS)) {
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

			try (PreparedStatement statementDettaglio = connessione.prepareStatement(sqlDettaglio);
					PreparedStatement statementQuantita = connessione.prepareStatement(sqlAggiornaQuantita)) {
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

/**
 * Restituisce tutti gli ordini effettuati da uno specifico utente.
*/
	public ArrayList<Ordine> trovaOrdiniPerUtente(int idUtente) {
		ArrayList<Ordine> ordini = new ArrayList<>();

		String sql = "SELECT * FROM ordini WHERE id_utente = ? ORDER BY data_ordine DESC";

		try (Connection connessione = ConnessioneDatabase.getConnessione();
				PreparedStatement statement = connessione.prepareStatement(sql)) {
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

	public Ordine trovaOrdinePerIdEUtente(int idOrdine, int idUtente) {
		Ordine ordine = null;

		String sql = "SELECT * FROM ordini WHERE id = ? AND id_utente = ?";

		try (Connection connessione = ConnessioneDatabase.getConnessione();
				PreparedStatement statement = connessione.prepareStatement(sql)) {
			statement.setInt(1, idOrdine);
			statement.setInt(2, idUtente);

			try (ResultSet risultato = statement.executeQuery()) {
				if (risultato.next()) {
					ordine = new Ordine();

					ordine.setId(risultato.getInt("id"));
					ordine.setIdUtente(risultato.getInt("id_utente"));
					ordine.setTotale(risultato.getDouble("totale"));
					ordine.setEmailConsegna(risultato.getString("email_consegna"));
					ordine.setNoteConsegna(risultato.getString("note_consegna"));
					ordine.setMetodoPagamento(risultato.getString("metodo_pagamento"));
					ordine.setStato(risultato.getString("stato"));
					ordine.setDataOrdine(risultato.getString("data_ordine"));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ordine;
	}

	public ArrayList<DettaglioOrdine> trovaDettagliPerOrdine(int idOrdine) {
		ArrayList<DettaglioOrdine> dettagli = new ArrayList<>();

		String sql = "SELECT * FROM dettagli_ordine WHERE id_ordine = ?";

		try (Connection connessione = ConnessioneDatabase.getConnessione();
				PreparedStatement statement = connessione.prepareStatement(sql)) {
			statement.setInt(1, idOrdine);

			try (ResultSet risultato = statement.executeQuery()) {
				while (risultato.next()) {
					DettaglioOrdine dettaglio = new DettaglioOrdine();

					dettaglio.setId(risultato.getInt("id"));
					dettaglio.setIdOrdine(risultato.getInt("id_ordine"));
					dettaglio.setIdProdotto(risultato.getInt("id_prodotto"));
					dettaglio.setNomeProdotto(risultato.getString("nome_prodotto"));
					dettaglio.setPrezzo(risultato.getDouble("prezzo"));
					dettaglio.setQuantita(risultato.getInt("quantita"));

					dettagli.add(dettaglio);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dettagli;
	}

	public Ordine trovaOrdinePerId(int idOrdine) {
		Ordine ordine = null;

		String sql = "SELECT * FROM ordini WHERE id = ?";

		try (Connection connessione = ConnessioneDatabase.getConnessione();
				PreparedStatement statement = connessione.prepareStatement(sql)) {
			statement.setInt(1, idOrdine);

			try (ResultSet risultato = statement.executeQuery()) {
				if (risultato.next()) {
					ordine = new Ordine();

					ordine.setId(risultato.getInt("id"));
					ordine.setIdUtente(risultato.getInt("id_utente"));
					ordine.setTotale(risultato.getDouble("totale"));
					ordine.setEmailConsegna(risultato.getString("email_consegna"));
					ordine.setNoteConsegna(risultato.getString("note_consegna"));
					ordine.setMetodoPagamento(risultato.getString("metodo_pagamento"));
					ordine.setStato(risultato.getString("stato"));
					ordine.setDataOrdine(risultato.getString("data_ordine"));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ordine;
	}

	public ArrayList<Ordine> trovaTuttiOrdini() {
		ArrayList<Ordine> ordini = new ArrayList<>();

		String sql = "SELECT * FROM ordini ORDER BY data_ordine DESC";

		try (Connection connessione = ConnessioneDatabase.getConnessione();
				PreparedStatement statement = connessione.prepareStatement(sql);
				ResultSet risultato = statement.executeQuery()) {
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

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ordini;
	}

	public boolean aggiornaStatoOrdine(int idOrdine, String stato) {
		String sql = "UPDATE ordini SET stato = ? WHERE id = ?";

		try (Connection connessione = ConnessioneDatabase.getConnessione();
				PreparedStatement statement = connessione.prepareStatement(sql)) {
			statement.setString(1, stato);
			statement.setInt(2, idOrdine);

			int righeModificate = statement.executeUpdate();

			return righeModificate > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
	
/**
* Restituisce gli ordini filtrati per data, utente o email di consegna.
* Usato nella pagina di gestione ordini dell'amministratore.
*/
	public ArrayList<Ordine> cercaOrdiniAdmin(String dataInizio, String dataFine, String idUtenteParametro,
			String emailConsegna) {
		ArrayList<Ordine> ordini = new ArrayList<>();

		String sql = "SELECT * FROM ordini " + "WHERE (? IS NULL OR DATE(data_ordine) >= ?) "
				+ "AND (? IS NULL OR DATE(data_ordine) <= ?) " + "AND (? IS NULL OR id_utente = ?) "
				+ "AND (? IS NULL OR email_consegna LIKE ?) " + "ORDER BY data_ordine DESC";

		try (Connection connessione = ConnessioneDatabase.getConnessione();
				PreparedStatement statement = connessione.prepareStatement(sql)) {
			if (dataInizio == null || dataInizio.trim().equals("")) {
				statement.setString(1, null);
				statement.setString(2, null);
			} else {
				statement.setString(1, dataInizio.trim());
				statement.setString(2, dataInizio.trim());
			}

			if (dataFine == null || dataFine.trim().equals("")) {
				statement.setString(3, null);
				statement.setString(4, null);
			} else {
				statement.setString(3, dataFine.trim());
				statement.setString(4, dataFine.trim());
			}

			if (idUtenteParametro == null || idUtenteParametro.trim().equals("")) {
				statement.setString(5, null);
				statement.setString(6, null);
			} else {
				statement.setString(5, idUtenteParametro.trim());
				statement.setInt(6, Integer.parseInt(idUtenteParametro.trim()));
			}

			if (emailConsegna == null || emailConsegna.trim().equals("")) {
				statement.setString(7, null);
				statement.setString(8, null);
			} else {
				statement.setString(7, emailConsegna.trim());
				statement.setString(8, "%" + emailConsegna.trim() + "%");
			}

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
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return ordini;
	}

}