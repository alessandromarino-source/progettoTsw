package it.unisa.ProgettoTsw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;

import it.unisa.ProgettoTsw.model.OrderBean;
import it.unisa.ProgettoTsw.model.OrderDetailBean;

public class OrderDaoImplements implements OrderDao {

    private static final String TABLE_NAME = "ORDINE";
    private DataSource ds = null;

    public OrderDaoImplements(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public synchronized void doSave(OrderBean order, List<OrderDetailBean> dettagli) throws SQLException {
        String insertOrderSQL = "INSERT INTO " + TABLE_NAME 
                + " (Data_Ordine, Totale_Importo, ID_Utente, Stato_Ordine) VALUES (?, ?, ?, ?)";
        
        String insertDetailsSQL = "INSERT INTO DETTAGLIO_ORDINE (Numero_Ordine, ID_Prodotto, Quantita_Acquistata, Prezzo_Vendita_Storico) VALUES (?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement orderStmt = null;
        PreparedStatement detailsStmt = null;
        ResultSet generatedKeys = null;

        try {
            connection = ds.getConnection();
            // 1. ATTIVIAMO LA TRANSAZIONE (Fondamentale per l'esame!)
            connection.setAutoCommit(false); 

            // 2. Prepariamo lo statement chiedendo indietro la chiave generata
            orderStmt = connection.prepareStatement(insertOrderSQL, java.sql.Statement.RETURN_GENERATED_KEYS);
            
            orderStmt.setTimestamp(1, new java.sql.Timestamp(order.getDataOrdine().getTime()));
            orderStmt.setDouble(2, order.getTotale());
            orderStmt.setInt(3, order.getIdUtente());
            orderStmt.setString(4, order.getStato()); 
            
            orderStmt.executeUpdate();

            // 3. Recuperiamo il Numero_Ordine appena generato
            generatedKeys = orderStmt.getGeneratedKeys();
            int newOrderId = -1;
            if (generatedKeys.next()) {
                newOrderId = generatedKeys.getInt(1);
                order.setIdOrdine(newOrderId); // Aggiorniamo il bean con il vero ID
            }

            // 4. Salvia mo i singoli prodotti dell'ordine (se presenti nel Bean)
         // Sostituisci il vecchio "if (order.getDettagli() != null)" con questo:
            if (dettagli != null && newOrderId != -1) {
                detailsStmt = connection.prepareStatement(insertDetailsSQL);
                for (OrderDetailBean detail : dettagli) {
                    detailsStmt.setInt(1, newOrderId);
                    detailsStmt.setInt(2, detail.getCodeProdotto());
                    detailsStmt.setInt(3, detail.getQuantita());
                    detailsStmt.setDouble(4, detail.getPrezzoUnitario());
                    detailsStmt.addBatch();
                }
                detailsStmt.executeBatch();
            }

            // 5. Se tutto è andato bene, salviamo definitivamente sul DB
            connection.commit();

        } catch (SQLException e) {
            // Se qualcosa fallisce, annulliamo tutto (niente ordini orfani)
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            // Chiudiamo tutte le risorse in sicurezza
            if (generatedKeys != null) generatedKeys.close();
            if (orderStmt != null) orderStmt.close();
            if (detailsStmt != null) detailsStmt.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }

    @Override
    public synchronized OrderBean doRetrieveByKey(int idOrdine) throws SQLException {
        OrderBean bean = new OrderBean();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE Numero_Ordine = ?";
        String detailsSQL = "SELECT * FROM DETTAGLIO_ORDINE WHERE Numero_Ordine = ?";
        
        // Apriamo un unico socket TCP per l'intera transazione di lettura
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
             PreparedStatement detailsStmt = connection.prepareStatement(detailsSQL)) {
            
            preparedStatement.setInt(1, idOrdine);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                // Sostituito 'while' con 'if' perché la chiave primaria restituisce al massimo un record
                if (rs.next()) { 
                    bean.setIdOrdine(rs.getInt("Numero_Ordine"));
                    bean.setDataOrdine(rs.getDate("Data_Ordine"));
                    bean.setTotale(rs.getDouble("Totale_Importo"));
                    bean.setIdUtente(rs.getInt("ID_Utente")); 
                    bean.setStato(rs.getString("Stato_Ordine")); 
                }
            }
            
            // Sfruttiamo la stessa sessione TCP (già ESTABLISHED) per la seconda query
            detailsStmt.setInt(1, idOrdine);
            try (ResultSet rsDetails = detailsStmt.executeQuery()) {
                List<OrderDetailBean> dettagli = new LinkedList<>();
                while (rsDetails.next()) {
                    OrderDetailBean detail = new OrderDetailBean();
                    detail.setCodeProdotto(rsDetails.getInt("ID_Prodotto"));
                    detail.setQuantita(rsDetails.getInt("Quantita_Acquistata")); 
                    detail.setPrezzoUnitario(rsDetails.getDouble("Prezzo_Vendita_Storico"));
                    dettagli.add(detail);
                } 
            }
        } 
        return bean;
    }

    @Override
    public synchronized List<OrderBean> doRetrieveByUser(int idUtente) throws SQLException {
        List<OrderBean> orders = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE ID_Utente = ? ORDER BY Data_Ordine DESC";
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, idUtente);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    OrderBean bean = new OrderBean();
                    bean.setIdOrdine(rs.getInt("Numero_Ordine"));
                    bean.setDataOrdine(rs.getDate("Data_Ordine"));
                    bean.setTotale(rs.getDouble("Totale_Importo"));
                    bean.setIdUtente(rs.getInt("ID_Utente")); 
                    bean.setStato(rs.getString("Stato_Ordine")); 
                    orders.add(bean);
                }
            }
        }
        return orders;
    }

    @Override
    public synchronized List<OrderBean> doRetrieveAll(String order) throws SQLException {
        List<OrderBean> orders = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME;
        if (order != null && !order.isEmpty()) {
            selectSQL += " ORDER BY " + order;
        }
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                OrderBean bean = new OrderBean();
                bean.setIdOrdine(rs.getInt("Numero_Ordine"));
                bean.setDataOrdine(rs.getDate("Data_Ordine"));
                bean.setTotale(rs.getDouble("Totale_Importo"));
                bean.setIdUtente(rs.getInt("ID_Utente"));
                bean.setStato(rs.getString("Stato_Ordine"));
                orders.add(bean);
            }
        }
        return orders;
    }

    @Override
    public synchronized boolean doDelete(int idOrdine) throws SQLException {
        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE Numero_Ordine = ?";
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, idOrdine);
            int result = preparedStatement.executeUpdate();
            return result != 0;
        }
    }
}