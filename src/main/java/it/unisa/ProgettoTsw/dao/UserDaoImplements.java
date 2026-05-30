package it.unisa.ProgettoTsw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;

import it.unisa.ProgettoTsw.model.UserBean;

public class UserDaoImplements implements UserDao {

    private static final String TABLE_NAME = "UTENTE";
    private DataSource ds = null;

    public UserDaoImplements(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public synchronized void doSave(UserBean user) throws SQLException {
        // Uniformate le maiuscole con il DB
        String insertSQL = "INSERT INTO " + TABLE_NAME
                + " (Nome, Cognome, Email, Password, Indirizzo_Spedizione, Telefono, Ruolo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, user.getNome());
            preparedStatement.setString(2, user.getCognome());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPasswordHash());
            preparedStatement.setString(5, user.getIndirizzoSpedizione());
            preparedStatement.setString(6, user.getTelefono());
            preparedStatement.setString(7, user.getRuolo());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public synchronized UserBean doRetrieveByKey(int idUtente) throws SQLException {
        UserBean bean = new UserBean();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE ID_Utente = ?";
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, idUtente);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    bean.setIdUtente(rs.getInt("ID_Utente"));
                    bean.setNome(rs.getString("Nome"));
                    bean.setCognome(rs.getString("Cognome")); // CORRETTO: era "ognome"
                    bean.setEmail(rs.getString("Email"));
                    bean.setPasswordHash(rs.getString("Password"));
                    bean.setIndirizzoSpedizione(rs.getString("Indirizzo_Spedizione"));
                    bean.setTelefono(rs.getString("Telefono"));
                    bean.setRuolo(rs.getString("Ruolo"));
                }
            }
        }
        return bean;
    }

    @Override
    public synchronized UserBean doRetrieveByEmail(String email) throws SQLException {
        UserBean bean = new UserBean();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE Email = ?";
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, email);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    bean.setIdUtente(rs.getInt("ID_Utente"));
                    bean.setNome(rs.getString("Nome"));
                    bean.setCognome(rs.getString("Cognome"));
                    bean.setEmail(rs.getString("Email"));
                    bean.setPasswordHash(rs.getString("Password"));
                    bean.setIndirizzoSpedizione(rs.getString("Indirizzo_Spedizione"));
                    bean.setTelefono(rs.getString("Telefono"));
                    bean.setRuolo(rs.getString("Ruolo"));
                }
            }
        }
        return bean;
    }

    @Override
    public synchronized boolean doDelete(int idUtente) throws SQLException {
        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE ID_Utente = ?";
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, idUtente);
            int result = preparedStatement.executeUpdate();
            return result != 0;
        }
    }

    @Override
    public synchronized List<UserBean> doRetrieveAll(String order) throws SQLException {
        List<UserBean> users = new LinkedList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME;
        if (order != null && !order.isEmpty()) {
            selectSQL += " ORDER BY " + order;
        }
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                UserBean bean = new UserBean();
                bean.setIdUtente(rs.getInt("ID_Utente"));
                bean.setNome(rs.getString("Nome"));
                bean.setCognome(rs.getString("Cognome"));
                bean.setEmail(rs.getString("Email"));
                bean.setPasswordHash(rs.getString("Password"));
                bean.setIndirizzoSpedizione(rs.getString("Indirizzo_Spedizione"));
                bean.setTelefono(rs.getString("Telefono"));
                bean.setRuolo(rs.getString("Ruolo"));
                users.add(bean);
            }
        }
        return users;
    }

    @Override
    public synchronized boolean doUpdate(UserBean user) throws SQLException {
        String updateSQL = "UPDATE " + TABLE_NAME 
                + " SET Nome = ?, Cognome = ?, Email = ?, Password = ?, Indirizzo_Spedizione = ?, Telefono = ?, Ruolo = ? "
                + " WHERE ID_Utente = ?";
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, user.getNome());
            preparedStatement.setString(2, user.getCognome());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPasswordHash());
            preparedStatement.setString(5, user.getIndirizzoSpedizione());
            preparedStatement.setString(6, user.getTelefono());
            preparedStatement.setString(7, user.getRuolo());
            preparedStatement.setInt(8, user.getIdUtente());
            
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated != 0;
        }
    }
}