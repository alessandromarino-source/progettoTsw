package it.unisa.ProgettoTsw.dao;

import java.sql.SQLException;
import java.util.List;
import it.unisa.ProgettoTsw.model.OrderBean;
import it.unisa.ProgettoTsw.model.OrderDetailBean;

public interface OrderDao {

	public void doSave(OrderBean order, List<OrderDetailBean> dettagli) throws SQLException;

    public OrderBean doRetrieveByKey(int idOrdine) throws SQLException;

    // Recupera tutti gli ordini di un singolo utente (storico ordini)
    public List<OrderBean> doRetrieveByUser(int idUtente) throws SQLException;

    public List<OrderBean> doRetrieveAll(String order) throws SQLException;
    
    // Solitamente gli ordini non si cancellano o modificano, ma per completezza:
    public boolean doDelete(int idOrdine) throws SQLException;
}