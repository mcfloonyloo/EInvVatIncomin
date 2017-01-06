package by.gomelagro.incoming.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.JOptionPane;

import by.gomelagro.incoming.gui.db.ConnectionDB;

/**
 * 
 * @author mcfloonyloo
 * @version 0.3
 *
 */

public class ApplicationProperties {//паттерн Singleton
	
	//блок Singleton
	private static volatile ApplicationProperties instance;
	
	public static void setInstance(ApplicationProperties instance){ApplicationProperties.instance = instance;}
		
	private ApplicationProperties(){}
	
	public static ApplicationProperties getInstance() {
		ApplicationProperties localInstance = instance;
		if(localInstance == null){
			synchronized (ConnectionDB.class) {
				localInstance = instance;
				if(localInstance == null){
					File file = new File(PROPFILENAME);
					if(!file.exists()){
						instance = localInstance = ApplicationProperties.Builder.getInstance().build();
						instance.saveProperties();
					}else{
						instance = localInstance = ApplicationProperties.Builder.getInstance().build();
					} 
				}
			}
		}
		return localInstance;
	}
	
	//блок ApplicationProperties
	public static final String PROPFILENAME = "resources/application.properties";
	
	private String libraryPath; 	//путь к файлам dll папки Avest Java Provider
	private String classPath;   	//путь к файлам .class проекта
	private String filePath;		//путь к файлу выгрузки списка
	private String dbPath;			//путь к базе данных
	
	private String urlService;		//сетевой путь к сервису ЭСЧФ
	
	public String getLibraryPath(){return this.libraryPath;}
	public void setLibraryPath(String libraryPath){this.libraryPath = libraryPath;}
	public String getClassPath(){return this.classPath;}
	public void setClassPath(String classPath){this.classPath = classPath;}
	
	public String getFilePath(){return this.filePath;}
	public void setFilePath(String filePath){this.filePath = filePath;}
	public String getDbPath(){return this.dbPath;}
	public void setDbPath(String dbPath){this.dbPath = dbPath;}
	
	public String getUrlService(){return this.urlService;}
	public void setUrlService(String urlService){this.urlService = urlService;}
	
	private ApplicationProperties(Builder build){
		this.libraryPath = build.libraryPath;
		this.classPath = build.classPath;
		this.filePath = build.filePath;
		this.dbPath = build.dbPath;
		
		this.urlService = build.urlService;
	}
	
	public void saveProperties(){
		try{
			Properties properties = new Properties();
			properties.setProperty("path.library", this.libraryPath);
			properties.setProperty("path.class", this.classPath);
			properties.setProperty("path.file", this.filePath);
			properties.setProperty("path.db", this.dbPath);
			properties.setProperty("url.service", this.urlService);
			File file = new File(PROPFILENAME);
			OutputStream out = new FileOutputStream(file);
			properties.store(out, "");
			JOptionPane.showMessageDialog(null, "Изменения настроек завершены","Информация",JOptionPane.INFORMATION_MESSAGE);
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public final static class Builder{
		private final static Builder instance = new Builder();
		
		private String libraryPath;
		private String classPath;
		private String filePath;
		private String dbPath;
		
		private String urlService;
		
		public static Builder getInstance(){
			return instance;
		}
		
		private Builder(){/*Singleton*/}
		
		private Builder loadProperties() throws FileNotFoundException{
			Properties prop = new Properties();
			String propFileName = PROPFILENAME;
			try {
				File file = new File(propFileName);
				if(file.exists()){
					InputStream inputStream = new FileInputStream(file);
					prop.load(inputStream);
					
					this.libraryPath = prop.getProperty("path.library");
					this.classPath = prop.getProperty("path.class");				
					this.filePath = prop.getProperty("path.file");
					this.dbPath = prop.getProperty("path.db");
					
					this.urlService = prop.getProperty("url.service");
					return this;
				}
				else{

					JOptionPane.showMessageDialog(null, "Файл настроек не обнаружен."+System.lineSeparator()+"Будут загружены стандартные настройки","Внимание",JOptionPane.WARNING_MESSAGE);
					this.libraryPath = "C:\\Program Files\\Avest\\AvJCEProv\\win32;";
					this.classPath = ".\\jar\\*;C:\\Program Files\\Avest\\AvJCEProv\\*;";				
					this.filePath = "output.txt";
					this.dbPath = "database.sqlite ";
					
					this.urlService = "https://ws.vat.gov.by:443/InvoicesWS/services/InvoicesPort?wsdl";
					return this;
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				this.libraryPath = "";
				this.classPath = "";
				this.filePath = "";
				this.dbPath = "";
				this.urlService = "";
				return this;
			}			
		}
		
		public ApplicationProperties build(){
			try {
				loadProperties();
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
			}
			return new ApplicationProperties(this);
		}
	}
	
	public boolean equals(ApplicationPropertiesTemp temp){
		if(temp == null){return false;}
		
		if(!ApplicationPropertiesTemp.class.isAssignableFrom(temp.getClass())){return false;}
				
		if((this.getLibraryPath() == null)?(temp.getLibraryPath() != null):!this.getLibraryPath().trim().equals(temp.getLibraryPath().trim().trim())){return false;}
		if((this.getClassPath() == null)?(temp.getClassPath() != null):!this.getClassPath().trim().equals(temp.getClassPath().trim())){return false;}
		if((this.getFilePath() == null)?(temp.getFilePath() != null):!this.getFilePath().trim().equals(temp.getFilePath().trim())){return false;}
		if((this.getDbPath() == null)?(temp.getDbPath() != null):!this.getDbPath().trim().equals(temp.getDbPath().trim())){return false;}
		if((this.getUrlService() == null)?(temp.getUrlService() != null):!this.getUrlService().trim().equals(temp.getUrlService().trim())){return false;}
		return true;
	}
}

