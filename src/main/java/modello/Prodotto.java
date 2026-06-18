package modello;

public class Prodotto {
    private int id;
    private String nome;
    private String gioco;
    private String categoria;
    private String rarita;
    private String condizione;
    private double prezzo;
    private int quantita;
    private String immagine;
    private String descrizione;

    public Prodotto() {
    }

    public Prodotto(int id, String nome, String gioco, String categoria, String rarita,
                    String condizione, double prezzo, int quantita, String immagine,
                    String descrizione) {
        this.id = id;
        this.nome = nome;
        this.gioco = gioco;
        this.categoria = categoria;
        this.rarita = rarita;
        this.condizione = condizione;
        this.prezzo = prezzo;
        this.quantita = quantita;
        this.immagine = immagine;
        this.descrizione = descrizione;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getGioco() {
        return gioco;
    }

    public void setGioco(String gioco) {
        this.gioco = gioco;
    }


    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


    public String getRarita() {
        return rarita;
    }

    public void setRarita(String rarita) {
        this.rarita = rarita;
    }


    public String getCondizione() {
        return condizione;
    }

    public void setCondizione(String condizione) {
        this.condizione = condizione;
    }


    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }


    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }


    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }


    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
