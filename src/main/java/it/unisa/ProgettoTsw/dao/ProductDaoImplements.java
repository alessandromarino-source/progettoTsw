package it.unisa.ProgettoTsw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;

import it.unisa.ProgettoTsw.model.ProductBean;

public class ProductDaoImplements implements ProductDao {

    private static final String TABLE_NAME = "PRODOTTO";
    private DataSource ds = null;

    public ProductDaoImplements(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public synchronized void doSave(ProductBean product) throws SQLException {
        String insertSQL = "INSERT INTO " + TABLE_NAME
                + " (Nome, Descrizione, Prezzo_Unitario, Quantita_Disponibile, Categoria, Certificazioni, ID_Produttore, path, mime_type) "
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = ds.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setDouble(4, product.getQuantity());
            preparedStatement.setString(5, product.getCategory());
            preparedStatement.setString(6, product.getCertifications());
            preparedStatement.setInt(7, product.getIdProducer());
            preparedStatement.setString(8, product.getPath());
            preparedStatement.setString(9, product.getMimeType());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public synchronized boolean doUpdate(ProductBean product) throws SQLException {
        String updateSQL = "UPDATE " + TABLE_NAME 
                + " SET Nome = ?, Descrizione = ?, Prezzo_Unitario = ?, Quantita_Disponibile = ?, Categoria = ?, Certificazioni = ?, ID_Produttore = ? "
                + " WHERE ID_Prodotto = ?";
        
        try (Connection connection = ds.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setDouble(4, product.getQuantity());
            preparedStatement.setString(5, product.getCategory());
            preparedStatement.setString(6, product.getCertifications());
            preparedStatement.setInt(7, product.getIdProducer());
            preparedStatement.setInt(8, product.getCode());
            
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated != 0;
        }
    }

    @Override
    public synchronized boolean doUpdateImage(ProductBean product) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET path = ?, mime_type = ? WHERE ID_Prodotto = ?";
        try (Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, product.getPath());
            ps.setString(2, product.getMimeType());
            ps.setInt(3, product.getCode());
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated != 0;
        }
    }

    @Override
    public synchronized ProductBean doRetrieveByKey(int idProdotto) throws SQLException {
        ProductBean bean = new ProductBean();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE ID_Prodotto = ?";
        
        try (Connection connection = ds.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, idProdotto);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                	bean.setCode(rs.getInt("ID_Prodotto"));
                    bean.setName(rs.getString("Nome"));
                    bean.setDescription(rs.getString("Descrizione"));
                    bean.setPrice(rs.getDouble("Prezzo_Unitario"));
                    bean.setQuantity(rs.getDouble("Quantita_Disponibile"));
                    bean.setCategory(rs.getString("Categoria"));
                    bean.setCertifications(rs.getString("Certificazioni"));
                    bean.setIdProducer(rs.getInt("ID_Produttore"));
                    bean.setActive(rs.getBoolean("Attivo"));
                    bean.setPath(rs.getString("path"));
                    bean.setMimeType(rs.getString("mime_type"));
                }
            }
        }
        return bean;
    }

    @Override
    public synchronized List<ProductBean> doRetrieveAll(String order) throws SQLException {
        List<ProductBean> products = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE Attivo = TRUE";
        
        if (order != null && !order.isEmpty()) {
            selectSQL += " ORDER BY " + order;
        }
        
        try (Connection connection = ds.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
                ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                ProductBean bean = new ProductBean();
                bean.setCode(rs.getInt("ID_Prodotto"));
                bean.setName(rs.getString("Nome"));
                bean.setDescription(rs.getString("Descrizione"));
                bean.setPrice(rs.getDouble("Prezzo_Unitario"));
                bean.setQuantity(rs.getDouble("Quantita_Disponibile"));
                bean.setCategory(rs.getString("Categoria"));
                bean.setCertifications(rs.getString("Certificazioni"));
                bean.setIdProducer(rs.getInt("ID_Produttore"));
                bean.setActive(rs.getBoolean("Attivo"));
                bean.setPath(rs.getString("path"));
                bean.setMimeType(rs.getString("mime_type"));
                products.add(bean);
            }
        }
        return products;
    }

    @Override
    public synchronized List<ProductBean> doRetrieveByCategory(String category) throws SQLException {
        List<ProductBean> products = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE Categoria = ? AND Attivo = TRUE";
        
        try (Connection connection = ds.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, category);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    ProductBean bean = new ProductBean();
                    bean.setCode(rs.getInt("ID_Prodotto"));
                    bean.setName(rs.getString("Nome"));
                    bean.setDescription(rs.getString("Descrizione"));
                    bean.setPrice(rs.getDouble("Prezzo_Unitario"));
                    bean.setQuantity(rs.getDouble("Quantita_Disponibile"));
                    bean.setCategory(rs.getString("Categoria"));
                    bean.setCertifications(rs.getString("Certificazioni"));
                    bean.setIdProducer(rs.getInt("ID_Produttore"));
                    bean.setActive(rs.getBoolean("Attivo"));
                    bean.setPath(rs.getString("path"));
                    bean.setMimeType(rs.getString("mime_type"));
                    products.add(bean);
                }
            }
        }
        return products;
    }

    @Override
    public synchronized List<ProductBean> doRetrieveByProducer(int idProduttore) throws SQLException {
        List<ProductBean> products = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE ID_Produttore = ? AND Attivo = TRUE";
        
        try (Connection connection = ds.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, idProduttore);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    ProductBean bean = new ProductBean();
                    bean.setCode(rs.getInt("ID_Prodotto"));
                    bean.setName(rs.getString("Nome"));
                    bean.setDescription(rs.getString("Descrizione"));
                    bean.setPrice(rs.getDouble("Prezzo_Unitario"));
                    bean.setQuantity(rs.getDouble("Quantita_Disponibile"));
                    bean.setCategory(rs.getString("Categoria"));
                    bean.setCertifications(rs.getString("Certificazioni"));
                    bean.setIdProducer(rs.getInt("ID_Produttore"));
                    bean.setActive(rs.getBoolean("Attivo"));
                    bean.setPath(rs.getString("path"));
                    bean.setMimeType(rs.getString("mime_type"));
                    products.add(bean);
                }
            }
        }
        return products;
    }

    @Override
    public synchronized boolean doDelete(int idProdotto) throws SQLException {
        String deleteSQL = "UPDATE " + TABLE_NAME + " SET Attivo = FALSE WHERE ID_Prodotto = ?";
        try (Connection connection = ds.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, idProdotto);
            int result = preparedStatement.executeUpdate();
            return result != 0;
        }
    }
}