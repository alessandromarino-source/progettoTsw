package it.unisa.ProgettoTsw.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OrderBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idOrdine;
    private int idUtente;
    private Date dataOrdine;
    private double totale;
    private String stato; // es. "In elaborazione", "Spedito", "Consegnato"
    private List<OrderDetailBean> dettagli; // NUOVO: le righe dell'ordine

    // Costruttore
    public OrderBean() {
        this.idOrdine = 0;
        this.idUtente = 0;
        this.dataOrdine = new Date(System.currentTimeMillis());
        this.totale = 0.0;
        this.stato = "In elaborazione";
        this.dettagli = new ArrayList<>();
    }

    // Getter e Setter
    public int getIdOrdine() { return idOrdine; }
    public void setIdOrdine(int idOrdine) { this.idOrdine = idOrdine; }

    public int getIdUtente() { return idUtente; }
    public void setIdUtente(int idUtente) { this.idUtente = idUtente; }

    public Date getDataOrdine() { return dataOrdine; }
    public void setDataOrdine(Date dataOrdine) { this.dataOrdine = dataOrdine; }

    public double getTotale() { return totale; }
    public void setTotale(double totale) { this.totale = totale; }

    public String getStato() { return stato; }
    public void setStato(String stato) { this.stato = stato; }

    public List<OrderDetailBean> getDettagli() { return dettagli; }
    public void setDettagli(List<OrderDetailBean> dettagli) { this.dettagli = dettagli; }

    @Override
    public String toString() {
        return "OrderBean [idOrdine=" + idOrdine + ", idUtente=" + idUtente + ", totale=" + totale + ", stato=" + stato + "]";
    }
}