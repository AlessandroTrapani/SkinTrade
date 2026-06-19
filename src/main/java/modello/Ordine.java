package modello;

public class Ordine {
    private int id;
    private int idUtente;
    private double totale;
    private String emailConsegna;
    private String noteConsegna;
    private String metodoPagamento;
    private String stato;
    private String dataOrdine;

    public Ordine() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }


    public double getTotale() {
        return totale;
    }

    public void setTotale(double totale) {
        this.totale = totale;
    }


    public String getEmailConsegna() {
        return emailConsegna;
    }

    public void setEmailConsegna(String emailConsegna) {
        this.emailConsegna = emailConsegna;
    }


    public String getNoteConsegna() {
        return noteConsegna;
    }

    public void setNoteConsegna(String noteConsegna) {
        this.noteConsegna = noteConsegna;
    }


    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }


    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }


    public String getDataOrdine() {
        return dataOrdine;
    }

    public void setDataOrdine(String dataOrdine) {
        this.dataOrdine = dataOrdine;
    }
}
