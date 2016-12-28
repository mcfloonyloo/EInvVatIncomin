package by.gomelagro.incoming.gui.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import by.gomelagro.incoming.gui.db.ConnectionDB;
import by.gomelagro.incoming.properties.ApplicationProperties;

public class ConnectionDB {
	private static volatile ConnectionDB instance;
	
	private Connection connection = null;
	private Statement statement = null;
	private boolean connected = false;
		
	public Connection getConnection(){return this.connection;}
	public Statement getStatement(){return this.statement;}
	public void setStatement(Statement statement){this.statement = statement;}
	public boolean isConnected(){return this.connected;}
	
	private ConnectionDB(){}
	
	public static ConnectionDB getInstance(){
		ConnectionDB localInstance = instance;
		if(localInstance == null){
			synchronized (ConnectionDB.class) {
				localInstance = instance;
				if(localInstance == null){
					instance = localInstance = new ConnectionDB(); 
				}
			}
		}
		return localInstance;
	}
	
	public void load(ApplicationProperties properties) throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");
		this.connection = DriverManager.getConnection("jdbc:sqlite:"+properties.getDbPath());
		this.statement = this.connection.createStatement();
		this.connected = true;
	}
	
	public void close() throws SQLException{
		this.connection.close();
		this.connected = false;
	}
}