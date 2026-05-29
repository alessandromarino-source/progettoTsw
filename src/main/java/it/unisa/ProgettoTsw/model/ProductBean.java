package it.unisa.ProgettoTsw.model;

import java.io.Serializable;

public class ProductBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code; 
    private String name;
    private String description;
    private double price;
    private double quantity;
    private String category;
    private String certifications;
    private int idProducer;
    private boolean active;
    private String path;       
    private String mimeType;   

    public ProductBean() {
        this.code = 0;
        this.name = "";
        this.description = "";
        this.price = 0.0;
        this.quantity = 0.0;
        this.category = "";
        this.certifications = "";
        this.idProducer = 0;
        this.active = true;
        this.path = ""; //path dell'immagine del prodotto nel server, cioò legge legge il percorso dal database e pesca l'immagine sul server nel mio pc
        this.mimeType = ""; //specifica esattamente il tipo di file in cui è contenuta l'immagine del prodotto nel database
    }

    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getCertifications() { return certifications; }
    public void setCertifications(String certifications) { this.certifications = certifications; }

    public int getIdProducer() { return idProducer; }
    public void setIdProducer(int idProducer) { this.idProducer = idProducer; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }

    @Override
    public String toString() {
        return "ProductBean [code=" + code + ", name=" + name + ", price=" + price + ", path=" + path + "]";
    }
}
