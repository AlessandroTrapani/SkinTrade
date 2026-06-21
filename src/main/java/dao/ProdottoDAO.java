package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modello.Prodotto;
import util.ConnessioneDatabase;

public class ProdottoDAO {

    public ArrayList<Prodotto> trovaTutti() {
        ArrayList<Prodotto> prodotti = new ArrayList<>();

        String sql = "SELECT * FROM prodotti WHERE stato = 'DISPONIBILE' AND quantita > 0";

        try (
            Connection connessione = ConnessioneDatabase.getConnessione();
            PreparedStatement statement = connessione.prepareStatement(sql);
            ResultSet risultato = statement.executeQuery()
        ) {
            while (risultato.next()) {
                Prodotto prodotto = new Prodotto();

                prodotto.setId(risultato.getInt("id"));
                prodotto.setNome(risultato.getString("nome"));
                prodotto.setGioco(risultato.getString("gioco"));
                prodotto.setCategoria(risultato.getString("categoria"));
                prodotto.setRarita(risultato.getString("rarita"));
                prodotto.setCondizione(risultato.getString("condizione"));
                prodotto.setPrezzo(risultato.getDouble("prezzo"));
                prodotto.setQuantita(risultato.getInt("quantita"));
                prodotto.setImmagine(risultato.getString("immagine"));
                prodotto.setDescrizione(risultato.getString("descrizione"));
                prodotto.setStato(risultato.getString("stato"));
                prodotti.add(prodotto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prodotti;
    }

    public Prodotto trovaPerId(int id) {
        Prodotto prodotto = null;

        String sql = "SELECT * FROM prodotti WHERE id = ?";

        try (
            Connection connessione = ConnessioneDatabase.getConnessione();
            PreparedStatement statement = connessione.prepareStatement(sql)
        ) {
            statement.setInt(1, id);

            try (ResultSet risultato = statement.executeQuery()) {
                if (risultato.next()) {
                    prodotto = new Prodotto();

                    prodotto.setId(risultato.getInt("id"));
                    prodotto.setNome(risultato.getString("nome"));
                    prodotto.setGioco(risultato.getString("gioco"));
                    prodotto.setCategoria(risultato.getString("categoria"));
                    prodotto.setRarita(risultato.getString("rarita"));
                    prodotto.setCondizione(risultato.getString("condizione"));
                    prodotto.setPrezzo(risultato.getDouble("prezzo"));
                    prodotto.setQuantita(risultato.getInt("quantita"));
                    prodotto.setImmagine(risultato.getString("immagine"));
                    prodotto.setDescrizione(risultato.getString("descrizione"));
                    prodotto.setStato(risultato.getString("stato"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prodotto;
    }
    
    public ArrayList<Prodotto> trovaTuttiAdmin() {
        ArrayList<Prodotto> prodotti = new ArrayList<>();

        String sql = "SELECT * FROM prodotti ORDER BY id DESC";

        try (
            Connection connessione = ConnessioneDatabase.getConnessione();
            PreparedStatement statement = connessione.prepareStatement(sql);
            ResultSet risultato = statement.executeQuery()
        ) {
            while (risultato.next()) {
                Prodotto prodotto = new Prodotto();

                prodotto.setId(risultato.getInt("id"));
                prodotto.setNome(risultato.getString("nome"));
                prodotto.setGioco(risultato.getString("gioco"));
                prodotto.setCategoria(risultato.getString("categoria"));
                prodotto.setRarita(risultato.getString("rarita"));
                prodotto.setCondizione(risultato.getString("condizione"));
                prodotto.setPrezzo(risultato.getDouble("prezzo"));
                prodotto.setQuantita(risultato.getInt("quantita"));
                prodotto.setImmagine(risultato.getString("immagine"));
                prodotto.setDescrizione(risultato.getString("descrizione"));
                prodotto.setStato(risultato.getString("stato"));
                prodotti.add(prodotto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prodotti;
    }
    
    public boolean salva(Prodotto prodotto) {
        String sql = "INSERT INTO prodotti "
                + "(nome, gioco, categoria, rarita, condizione, prezzo, quantita, immagine, descrizione, stato) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection connessione = ConnessioneDatabase.getConnessione();
            PreparedStatement statement = connessione.prepareStatement(sql)
        ) {
            statement.setString(1, prodotto.getNome());
            statement.setString(2, prodotto.getGioco());
            statement.setString(3, prodotto.getCategoria());
            statement.setString(4, prodotto.getRarita());
            statement.setString(5, prodotto.getCondizione());
            statement.setDouble(6, prodotto.getPrezzo());
            statement.setInt(7, prodotto.getQuantita());
            statement.setString(8, prodotto.getImmagine());
            statement.setString(9, prodotto.getDescrizione());
            if (prodotto.getQuantita() > 0) {
                statement.setString(10, "DISPONIBILE");
            } else {
                statement.setString(10, "NON_DISPONIBILE");
            }

            int righeInserite = statement.executeUpdate();

            return righeInserite > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public boolean modifica(Prodotto prodotto) {
    	String sql = "UPDATE prodotti SET "
    	        + "nome = ?, "
    	        + "gioco = ?, "
    	        + "categoria = ?, "
    	        + "rarita = ?, "
    	        + "condizione = ?, "
    	        + "prezzo = ?, "
    	        + "quantita = ?, "
    	        + "immagine = ?, "
    	        + "descrizione = ?, "
    	        + "stato = ? "
    	        + "WHERE id = ?";

        try (
            Connection connessione = ConnessioneDatabase.getConnessione();
            PreparedStatement statement = connessione.prepareStatement(sql)
        ) {
            statement.setString(1, prodotto.getNome());
            statement.setString(2, prodotto.getGioco());
            statement.setString(3, prodotto.getCategoria());
            statement.setString(4, prodotto.getRarita());
            statement.setString(5, prodotto.getCondizione());
            statement.setDouble(6, prodotto.getPrezzo());
            statement.setInt(7, prodotto.getQuantita());
            statement.setString(8, prodotto.getImmagine());
            statement.setString(9, prodotto.getDescrizione());
            if (prodotto.getQuantita() > 0) {
                statement.setString(10, "DISPONIBILE");
            } else {
                statement.setString(10, "NON_DISPONIBILE");
            }

            statement.setInt(11, prodotto.getId());

            int righeModificate = statement.executeUpdate();

            return righeModificate > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


	public boolean elimina(int idProdotto) {
        String sql = "UPDATE prodotti SET stato = 'ELIMINATO' WHERE id = ?";

        try (
            Connection connessione = ConnessioneDatabase.getConnessione();
            PreparedStatement statement = connessione.prepareStatement(sql)
        ) {
            statement.setInt(1, idProdotto);

            int righeModificate = statement.executeUpdate();

            return righeModificate > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public ArrayList<Prodotto> cercaProdotti(String ricerca, String gioco, String categoria) {
        ArrayList<Prodotto> prodotti = new ArrayList<>();

        String sql = "SELECT * FROM prodotti "
                + "WHERE stato = 'DISPONIBILE' "
                + "AND quantita > 0 "
                + "AND (? IS NULL OR nome LIKE ?) "
                + "AND (? IS NULL OR gioco = ?) "
                + "AND (? IS NULL OR categoria = ?) "
                + "ORDER BY nome";

        try (
            Connection connessione = ConnessioneDatabase.getConnessione();
            PreparedStatement statement = connessione.prepareStatement(sql)
        ) {
            if (ricerca == null || ricerca.trim().equals("")) {
                statement.setString(1, null);
                statement.setString(2, null);
            } else {
                statement.setString(1, ricerca.trim());
                statement.setString(2, "%" + ricerca.trim() + "%");
            }

            if (gioco == null || gioco.trim().equals("")) {
                statement.setString(3, null);
                statement.setString(4, null);
            } else {
                statement.setString(3, gioco.trim());
                statement.setString(4, gioco.trim());
            }

            if (categoria == null || categoria.trim().equals("")) {
                statement.setString(5, null);
                statement.setString(6, null);
            } else {
                statement.setString(5, categoria.trim());
                statement.setString(6, categoria.trim());
            }

            try (ResultSet risultato = statement.executeQuery()) {
                while (risultato.next()) {
                    Prodotto prodotto = new Prodotto();

                    prodotto.setId(risultato.getInt("id"));
                    prodotto.setNome(risultato.getString("nome"));
                    prodotto.setGioco(risultato.getString("gioco"));
                    prodotto.setCategoria(risultato.getString("categoria"));
                    prodotto.setRarita(risultato.getString("rarita"));
                    prodotto.setCondizione(risultato.getString("condizione"));
                    prodotto.setPrezzo(risultato.getDouble("prezzo"));
                    prodotto.setQuantita(risultato.getInt("quantita"));
                    prodotto.setImmagine(risultato.getString("immagine"));
                    prodotto.setDescrizione(risultato.getString("descrizione"));
                    prodotto.setStato(risultato.getString("stato"));
                    prodotti.add(prodotto);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prodotti;
    }
}
