package it.unisa.ProgettoTsw.dao;

import java.sql.SQLException;
import java.util.List;

import it.unisa.ProgettoTsw.model.ProductBean;

public interface ProductDao {

    // Cerca un prodotto specifico tramite la sua chiave primaria (il codice)
    public ProductBean doRetrieveByKey(int code) throws SQLException;

    // Recupera tutti i prodotti dal database, permettendo di ordinarli (es. per nome, per prezzo)
    public List<ProductBean> doRetrieveAll(String order) throws SQLException;

    // Inserisce un nuovo prodotto nel database (es. dal pannello di amministrazione)
    public void doSave(ProductBean product) throws SQLException;

    // Modifica i dati di un prodotto esistente
    public boolean doUpdate(ProductBean product) throws SQLException;

    // Elimina un prodotto dal database tramite il suo codice
    public boolean doDelete(int code) throws SQLException;
    
    // Metodo specifico per aggiornare il path e il mimeType dell'immagine del prodotto
    public boolean doUpdateImage(ProductBean product) throws SQLException;
    
    //Cerca i prodotti di una specifica categoria
    public List<ProductBean> doRetrieveByCategory(String category) throws SQLException;
    
    //Cerca i prodotti di un determinato produttore
    public List<ProductBean> doRetrieveByProducer(int idProduttore) throws SQLException;
}