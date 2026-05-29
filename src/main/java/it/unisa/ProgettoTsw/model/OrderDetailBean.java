package it.unisa.ProgettoTsw.model;

import java.io.Serializable;

public class OrderDetailBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idOrdine;
    private int codeProdotto; // Codice del prodotto acquistato
    private int quantita;
    private double prezzoUnitario; // Il prezzo del prodotto al momento dell'acquisto

    // Costruttore vuoto obbligatorio
    public OrderDetailBean() {
        this.idOrdine = 0;
        this.codeProdotto = 0;
        this.quantita = 0;
        this.prezzoUnitario = 0.0;
    }

    // Getter e Setter
    public int getIdOrdine() { return idOrdine; }
    public void setIdOrdine(int idOrdine) { this.idOrdine = idOrdine; }

    public int getCodeProdotto() { return codeProdotto; }
    public void setCodeProdotto(int codeProdotto) { this.codeProdotto = codeProdotto; }

    public int getQuantita() { return quantita; }
    public void setQuantita(int quantita) { this.quantita = quantita; }

    public double getPrezzoUnitario() { return prezzoUnitario; }
    public void setPrezzoUnitario(double prezzoUnitario) { this.prezzoUnitario = prezzoUnitario; }

    @Override
    public String toString () {
        return "OrderDetailBean [idOrdine=" + idOrdine + ", codeProdotto=" + codeProdotto + ", quantita=" + quantita
                + ", prezzoUnitario=" + prezzoUnitario + "]";
    }
}