package it.unisa.ProgettoTsw.listener;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class MainContextListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		DataSource ds = null;
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			
			// NOTA PER IL TUO DATABASE: 
			// Il prof nel suo esempio usa "jdbc/storage". 
			// Assicurati che nel tuo file 'context.xml' il name della risorsa corrisponda a questo,
			// altrimenti cambialo con il nome che hai dato al tuo database (es. "jdbc/ecommerce").
			ds = (DataSource) envCtx.lookup("jdbc/storage");
		} catch (NamingException e) {
			System.out.println("Error:" + e.getMessage());
		}
		context.setAttribute("DataSource", ds);
	}

	public void contextDestroyed(ServletContextEvent sce) {
	}
}