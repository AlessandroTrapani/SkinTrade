package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Utente;
import util.ConnessioneDatabase;

/**
 * DAO dedicato alla gestione degli utenti.
 * Gestisce login, registrazione e verifica dell'esistenza di un'email.
 */
public class UtenteDAO {

	public Utente trovaPerEmailEPassword(String email, String password) {
		Utente utente = null;

		String sql = "SELECT * FROM utenti WHERE email = ? AND password = ?";

		try (Connection connessione = ConnessioneDatabase.getConnessione();
				PreparedStatement statement = connessione.prepareStatement(sql)) {
			statement.setString(1, email);
			statement.setString(2, password);

			try (ResultSet risultato = statement.executeQuery()) {
				if (risultato.next()) {
					utente = new Utente();

					utente.setId(risultato.getInt("id"));
					utente.setNome(risultato.getString("nome"));
					utente.setCognome(risultato.getString("cognome"));
					utente.setEmail(risultato.getString("email"));
					utente.setPassword(risultato.getString("password"));
					utente.setRuolo(risultato.getString("ruolo"));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return utente;
	}

	public boolean emailEsistente(String email) {
		String sql = "SELECT id FROM utenti WHERE email = ?";

		try (Connection connessione = ConnessioneDatabase.getConnessione();
				PreparedStatement statement = connessione.prepareStatement(sql)) {
			statement.setString(1, email);

			try (ResultSet risultato = statement.executeQuery()) {
				return risultato.next();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean salva(Utente utente) {
		String sql = "INSERT INTO utenti (nome, cognome, email, password, ruolo) VALUES (?, ?, ?, ?, ?)";

		try (Connection connessione = ConnessioneDatabase.getConnessione();
				PreparedStatement statement = connessione.prepareStatement(sql)) {
			statement.setString(1, utente.getNome());
			statement.setString(2, utente.getCognome());
			statement.setString(3, utente.getEmail());
			statement.setString(4, utente.getPassword());
			statement.setString(5, utente.getRuolo());

			int righeInserite = statement.executeUpdate();

			return righeInserite > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
}