package it.unisa.ProgettoTsw.model;

import java.io.Serializable;

public class ProducerBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idProduttore;
    private String nome;
    private String descrizione;
    private String sede;

    // Costruttore
    public ProducerBean() {
        this.idProduttore = 0;
        this.nome = "";
        this.descrizione = "";
        this.sede = "";
    }

    // Getter e Setter
    public int getIdProduttore() { return idProduttore; }
    public void setIdProduttore(int idProduttore) { this.idProduttore = idProduttore; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public String getSede() { return sede; }
    public void setSede(String sede) { this.sede = sede; }

    @Override
    public String toString() {
        return "ProducerBean [idProduttore=" + idProduttore + ", nome=" + nome + ", sede=" + sede + "]";
    }
}