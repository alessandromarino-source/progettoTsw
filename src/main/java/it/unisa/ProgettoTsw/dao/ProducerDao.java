package it.unisa.ProgettoTsw.dao;

import java.sql.SQLException;
import java.util.List;
import it.unisa.ProgettoTsw.model.ProducerBean;

public interface ProducerDao {

    public void doSave(ProducerBean producer) throws SQLException;

    public ProducerBean doRetrieveByKey(int id) throws SQLException;

    public boolean doDelete(int id) throws SQLException;

    public List<ProducerBean> doRetrieveAll(String order) throws SQLException;
    
    public boolean doUpdate(ProducerBean producer) throws SQLException;
}