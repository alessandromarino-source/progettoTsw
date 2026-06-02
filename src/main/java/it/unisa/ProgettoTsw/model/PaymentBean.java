package it.unisa.ProgettoTsw.model;

import java.io.Serializable;

public class PaymentBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idPagamento;
    private int numeroOrdine;       // FK verso ORDINE
    private String tipo;            // es. "Carta", "PayPal", "Contrassegno"
    private String statoPagamento;  // es. "Completato", "In attesa"

    public PaymentBean() {
        this.idPagamento = 0;
        this.numeroOrdine = 0;
        this.tipo = "";
        this.statoPagamento = "";
    }

    public int getIdPagamento() { return idPagamento; }
    public void setIdPagamento(int idPagamento) { this.idPagamento = idPagamento; }

    public int getNumeroOrdine() { return numeroOrdine; }
    public void setNumeroOrdine(int numeroOrdine) { this.numeroOrdine = numeroOrdine; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getStatoPagamento() { return statoPagamento; }
    public void setStatoPagamento(String statoPagamento) { this.statoPagamento = statoPagamento; }

    @Override
    public String toString() {
        return "PaymentBean [idPagamento=" + idPagamento + ", numeroOrdine=" + numeroOrdine
                + ", tipo=" + tipo + ", statoPagamento=" + statoPagamento + "]";
    }
}