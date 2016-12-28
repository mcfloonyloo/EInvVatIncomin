package by.gomelagro.incoming.properties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @author mcfloonyloo
 * @version 0.1
 *
 */

public class ApplicationProperties {//паттерн Singleton
	
	private String libraryPath; 	//путь к файлам dll папки Avest Java Provider
	private String classPath;   	//путь к файлам .class проекта
	private String inPath;  	//путь к папке с Ё—„‘
	private String outPath;
	private String dbPath;			//путь к базе данных
	
	private String urlService;		//сетевой путь к сервису Ё—„‘
	//private boolean showUploaded;	//флаг "ѕоказать отправленные"
	
	public String getLibraryPath(){return this.libraryPath;}
	public String getClassPath(){return this.classPath;}
	
	public String getInPath(){return this.inPath;}
	public String getOutPath(){return this.outPath;}
	public String getDbPath(){return this.dbPath;}
	
	public String getUrlService(){return this.urlService;}
	//public boolean isShowUploaded(){return this.showUploaded;}
	
	private ApplicationProperties(Builder build){
		this.libraryPath = build.libraryPath;
		this.classPath = build.classPath;
		this.inPath = build.inPath;
		this.outPath = build.outPath;
		this.dbPath = build.dbPath;
		
		this.urlService = build.urlService;
		//this.showUploaded = build.showUploaded;
	}
	
	public final static class Builder{
		private final static Builder instance = new Builder();
		
		private String libraryPath;
		private String classPath;
		private String inPath;
		private String outPath;
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
				inPath = prop.getProperty("path.in");
				outPath = prop.getProperty("path.out");
				dbPath = prop.getProperty("path.db");
				
				urlService = prop.getProperty("url.service");
				//showUploaded = Boolean.parseBoolean(prop.getProperty("showuploaded"));
				
			} catch (IOException e) {
				e.printStackTrace();//
			}
			
		}
		
		public ApplicationProperties build(){
			try {
				loadProperties();
			} catch (FileNotFoundException e) {
				System.err.println(e.getMessage());
			}
			return new ApplicationProperties(this);
		}
	}
	
	
}

