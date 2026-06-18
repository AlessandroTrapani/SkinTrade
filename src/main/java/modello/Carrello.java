package modello;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Carrello {
    private List<ElementoCarrello> elementi;

    public Carrello() {
        elementi = new ArrayList<>();
    }

    public List<ElementoCarrello> getElementi() {
        return elementi;
    }

    public void aggiungiProdotto(Prodotto prodotto, int quantita) {
        if (quantita <= 0) {
            return;
        }

        if (quantita > prodotto.getQuantita()) {
            quantita = prodotto.getQuantita();
        }

        for (ElementoCarrello elemento : elementi) {
            if (elemento.getProdotto().getId() == prodotto.getId()) {
                int nuovaQuantita = elemento.getQuantita() + quantita;

                if (nuovaQuantita > prodotto.getQuantita()) {
                    nuovaQuantita = prodotto.getQuantita();
                }

                elemento.setQuantita(nuovaQuantita);
                return;
            }
        }

        elementi.add(new ElementoCarrello(prodotto, quantita));
    }

    public void aggiornaQuantita(int idProdotto, int quantita) {
        for (ElementoCarrello elemento : elementi) {
            if (elemento.getProdotto().getId() == idProdotto) {
                int quantitaDisponibile = elemento.getProdotto().getQuantita();

                if (quantita <= 0) {
                    quantita = 1;
                }

                if (quantita > quantitaDisponibile) {
                    quantita = quantitaDisponibile;
                }

                elemento.setQuantita(quantita);
                return;
            }
        }
    }

    public void rimuoviProdotto(int idProdotto) {
        Iterator<ElementoCarrello> iterator = elementi.iterator();

        while (iterator.hasNext()) {
            ElementoCarrello elemento = iterator.next();

            if (elemento.getProdotto().getId() == idProdotto) {
                iterator.remove();
                return;
            }
        }
    }

    public void svuota() {
        elementi.clear();
    }

    public double getTotale() {
        double totale = 0;

        for (ElementoCarrello elemento : elementi) {
            totale += elemento.getTotale();
        }

        return totale;
    }

    public boolean isVuoto() {
        return elementi.isEmpty();
    }
}