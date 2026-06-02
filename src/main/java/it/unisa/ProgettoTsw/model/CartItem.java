package it.unisa.ProgettoTsw.model;

import java.io.Serializable;

public class CartItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private ProductBean product;   // il prodotto
    private int quantity;          // quantita' desiderata dal cliente (NON la giacenza)

    public CartItem() {
        this.product = null;
        this.quantity = 0;
    }

    public CartItem(ProductBean product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public ProductBean getProduct() { return product; }
    public void setProduct(ProductBean product) { this.product = product; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    // Subtotale della riga = prezzo unitario * quantita'
    public double getSubtotal() {
        if (product == null) return 0.0;
        return product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return "CartItem [prodotto=" + (product != null ? product.getName() : "null")
                + ", quantita=" + quantity + ", subtotale=" + getSubtotal() + "]";
    }
}