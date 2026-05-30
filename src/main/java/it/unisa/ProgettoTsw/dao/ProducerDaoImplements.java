package it.unisa.ProgettoTsw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;

import it.unisa.ProgettoTsw.model.ProducerBean;

public class ProducerDaoImplements implements ProducerDao {

    private static final String TABLE_NAME = "PRODUTTORE";
    private DataSource ds = null;

    public ProducerDaoImplements(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public synchronized void doSave(ProducerBean producer) throws SQLException {
        // CORRETTO: Partita_Iva -> Partita_IVA
        String insertSQL = "INSERT INTO " + TABLE_NAME 
                + " (Ragione_Sociale, Descrizione_Azienda, Partita_IVA, Sede_Legale) VALUES (?, ?, ?, ?)";
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, producer.getNome());
            preparedStatement.setString(2, producer.getDescrizione());
            preparedStatement.setString(3, producer.getPartitaIva());
            preparedStatement.setString(4, producer.getSedeLegale());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public synchronized ProducerBean doRetrieveByKey(int id) throws SQLException {
        ProducerBean bean = new ProducerBean();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE ID_Produttore = ?";
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    bean.setIdProduttore(rs.getInt("ID_Produttore"));
                    bean.setNome(rs.getString("Ragione_Sociale"));
                    bean.setDescrizione(rs.getString("Descrizione_Azienda"));
                    bean.setPartitaIva(rs.getString("Partita_IVA"));
                    bean.setSedeLegale(rs.getString("Sede_Legale"));
                }
            }
        }
        return bean;
    }

    @Override
    public synchronized boolean doDelete(int id) throws SQLException {
        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE ID_Produttore = ?";
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, id);
            int result = preparedStatement.executeUpdate();
            return result != 0;
        }
    }

    @Override
    public synchronized List<ProducerBean> doRetrieveAll(String order) throws SQLException {
        List<ProducerBean> producers = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME;
        if (order != null && !order.isEmpty()) {
            selectSQL += " ORDER BY " + order;
        }
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                ProducerBean bean = new ProducerBean();
                bean.setIdProduttore(rs.getInt("ID_Produttore"));
                bean.setNome(rs.getString("Ragione_Sociale"));
                bean.setDescrizione(rs.getString("Descrizione_Azienda"));
                bean.setPartitaIva(rs.getString("Partita_IVA"));
                bean.setSedeLegale(rs.getString("Sede_Legale"));
                producers.add(bean);
            }
        }
        return producers;
    }

    @Override
    public synchronized boolean doUpdate(ProducerBean producer) throws SQLException {
        // CORRETTO: Partita_Iva -> Partita_IVA
        String updateSQL = "UPDATE " + TABLE_NAME 
                + " SET Ragione_Sociale = ?, Descrizione_Azienda = ?, Partita_IVA = ?, Sede_Legale = ? "
                + " WHERE ID_Produttore = ?";
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, producer.getNome());
            preparedStatement.setString(2, producer.getDescrizione());
            preparedStatement.setString(3, producer.getPartitaIva());
            preparedStatement.setString(4, producer.getSedeLegale());
            preparedStatement.setInt(5, producer.getIdProduttore());
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated != 0;
        }
    }
}