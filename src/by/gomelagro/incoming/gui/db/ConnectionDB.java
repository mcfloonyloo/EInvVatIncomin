package by.gomelagro.incoming.gui.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import by.gomelagro.incoming.gui.db.ConnectionDB;
import by.gomelagro.incoming.properties.ApplicationProperties;

public class ConnectionDB {
	private static volatile ConnectionDB instance;
	
	private Connection connection = null;
	private Statement statement = null;
	private boolean connected = false;
	private String dbPath = "";
		
	public Connection getConnection(){return this.connection;}
	public Statement getStatement(){return this.statement;}
	public void setStatement(Statement statement){this.statement = statement;}
	public boolean isConnected(){return this.connected;}
	public String getDbPath(){return this.dbPath;}
	
	private ConnectionDB(){}
	
	public static ConnectionDB getInstance(){
		ConnectionDB localInstance = instance;
		if(localInstance == null){
			synchronized (ConnectionDB.class) {
				localInstance = instance;
				if(localInstance == null){
					try{
						instance = localInstance = new ConnectionDB().load();
					} catch (SQLException | ClassNotFoundException e) {
						JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
					}	
				}
			}
		}
		if(!instance.getConnection().equals(ApplicationProperties.getInstance().getDbPath())){
			try {
				instance.close();
				instance.load();
			} catch (SQLException | ClassNotFoundException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
			}
		}
		return localInstance;
	}
	
	public ConnectionDB load() throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");
		this.connection = DriverManager.getConnection("jdbc:sqlite:"+ApplicationProperties.getInstance().getDbPath());
		this.statement = this.connection.createStatement();
		this.dbPath = ApplicationProperties.getInstance().getDbPath();
		this.connected = true;
		return this;
	}
	
	public void close() throws SQLException{
		this.connection.close();
		this.connected = false;
	}
}