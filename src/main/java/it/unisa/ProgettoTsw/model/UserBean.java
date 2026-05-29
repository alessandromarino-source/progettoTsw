package it.unisa.ProgettoTsw.model;

import java.io.Serializable;

public class UserBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idUtente;
    private String nome;
    private String cognome;
    private String email;
    private String passwordHash;
    private String indirizzoSpedizione;
    private String telefono;
    private String ruolo; 

    public UserBean() {
        this.idUtente = 0;
        this.nome = "";
        this.cognome = "";
        this.email = "";
        this.passwordHash = "";
        this.indirizzoSpedizione = "";
        this.telefono = "";
        this.ruolo = "cliente";
    }

    public int getIdUtente() { return idUtente; }
    public void setIdUtente(int idUtente) { this.idUtente = idUtente; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getIndirizzoSpedizione() { return indirizzoSpedizione; }
    public void setIndirizzoSpedizione(String indirizzoSpedizione) { this.indirizzoSpedizione = indirizzoSpedizione; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getRuolo() { return ruolo; }
    public void setRuolo(String ruolo) { this.ruolo = ruolo; }

    @Override
    public String toString() {
        return "UserBean [idUtente=" + idUtente + ", email=" + email + ", ruolo=" + ruolo + "]";
    }
}