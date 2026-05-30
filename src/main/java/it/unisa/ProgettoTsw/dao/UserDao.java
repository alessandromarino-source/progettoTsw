package it.unisa.ProgettoTsw.dao;

import java.sql.SQLException;
import java.util.List;
import it.unisa.ProgettoTsw.model.UserBean;

public interface UserDao {

    public void doSave(UserBean user) throws SQLException;

    public UserBean doRetrieveByKey(int idUtente) throws SQLException;
    
    // Questo metodo è fondamentale per gestire la Servlet di Login!
    public UserBean doRetrieveByEmail(String email) throws SQLException;

    public boolean doDelete(int idUtente) throws SQLException;

    public List<UserBean> doRetrieveAll(String order) throws SQLException;
    
    public boolean doUpdate(UserBean user) throws SQLException;
}