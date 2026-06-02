package it.unisa.ProgettoTsw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CartBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<CartItem> items;

    public CartBean() {
        this.items = new ArrayList<>();
    }

    // Aggiunge un prodotto al carrello.
    // Se il prodotto e' gia' presente, ne incrementa la quantita' invece di duplicarlo.
    public void addProduct(ProductBean product, int quantity) {
        if (quantity <= 0) {
            quantity = 1;
        }
        for (CartItem item : items) {
            if (item.getProduct().getCode() == product.getCode()) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new CartItem(product, quantity));
    }

    // Modifica la quantita' di una riga. Se la nuova quantita' e' <= 0, la riga viene rimossa.
    public void updateQuantity(int productCode, int quantity) {
        Iterator<CartItem> it = items.iterator();
        while (it.hasNext()) {
            CartItem item = it.next();
            if (item.getProduct().getCode() == productCode) {
                if (quantity <= 0) {
                    it.remove();
                } else {
                    item.setQuantity(quantity);
                }
                return;
            }
        }
    }

    // Rimuove completamente un prodotto dal carrello
    public void removeProduct(int productCode) {
        items.removeIf(item -> item.getProduct().getCode() == productCode);
    }

    // Svuota il carrello (richiesto dalla traccia e usato dopo il checkout)
    public void clear() {
        items.clear();
    }

    public List<CartItem> getItems() {
        return items;
    }

    // Totale complessivo del carrello
    public double getTotal() {
        double total = 0.0;
        for (CartItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    // Numero totale di pezzi (comodo per il "badge" del carrello nell'header)
    public int getItemCount() {
        int count = 0;
        for (CartItem item : items) {
            count += item.getQuantity();
        }
        return count;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}