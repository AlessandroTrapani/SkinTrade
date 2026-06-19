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

        String sql = "SELECT * FROM prodotti WHERE stato = 'DISPONIBILE'";

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

                prodotti.add(prodotto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prodotti;
    }
}