package by.gomelagro.incoming.properties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JOptionPane;

/**
 * 
 * @author mcfloonyloo
 * @version 0.1
 *
 */

public class ApplicationProperties {//паттерн Singleton
	
	private String libraryPath; 	//путь к файлам dll папки Avest Java Provider
	private String classPath;   	//путь к файлам .class проекта
	private String filePath;		//путь к файлу выгрузки списка
	private String dbPath;			//путь к базе данных
	
	private String urlService;		//сетевой путь к сервису ЭСЧФ
	//private boolean showUploaded;	//флаг "Показать отправленные"
	
	public String getLibraryPath(){return this.libraryPath;}
	public String getClassPath(){return this.classPath;}
	
	public String getFilePath(){return this.filePath;}
	public String getDbPath(){return this.dbPath;}
	
	public String getUrlService(){return this.urlService;}
	//public boolean isShowUploaded(){return this.showUploaded;}
	
	private ApplicationProperties(Builder build){
		this.libraryPath = build.libraryPath;
		this.classPath = build.classPath;
		this.filePath = build.filePath;
		this.dbPath = build.dbPath;
		
		this.urlService = build.urlService;
		//this.showUploaded = build.showUploaded;
	}
	
	public final static class Builder{
		private final static Builder instance = new Builder();
		
		private String libraryPath;
		private String classPath;
		private String filePath;
		private String dbPath;
		
		private String urlService;
		//private boolean showUploaded;
		
		public static Builder getInstance(){
			return instance;
		}
		
		private Builder(){/*Singleton*/}
		
		private void loadProperties() throws FileNotFoundException{
			Properties prop = new Properties();
			String propFileName = "application.properties";
			try {
				InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
				prop.load(inputStream);
				
				libraryPath = prop.getProperty("path.library");
				classPath = prop.getProperty("path.class");				
				filePath = prop.getProperty("path.file");
				dbPath = prop.getProperty("path.db");
				
				urlService = prop.getProperty("url.service");
				//showUploaded = Boolean.parseBoolean(prop.getProperty("showuploaded"));
				
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
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
	
	
}

