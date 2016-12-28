package by.gomelagro.incoming.service;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.cert.CertificateException;

import by.gomelagro.incoming.properties.ApplicationProperties;
import by.gomelagro.incoming.service.selectors.KeySelector;

import by.avest.certstore.AvCertStoreProvider;
import by.avest.crypto.pkcs11.provider.AvestProvider;
import by.avest.crypto.pkcs11.provider.ProviderFactory;
import by.avest.edoc.client.AvDocException;
import by.avest.edoc.client.EVatService;
import by.avest.edoc.tool.ToolException;
import by.avest.net.tls.AvTLSProvider;

/**
 * 
 * @author mcfloonyloo
 * @version 0.3
 *
 */

public class EVatServiceSingleton {
	private static volatile EVatServiceSingleton instance;
	
	private EVatService service = null;
	private AvestProvider prov = null;
	public EVatService getService(){return this.service;}
	
	public static void setNullInstance(){instance = null;}
	
	private boolean connect = false;
	private void setConnect(boolean connect){this.connect = connect;}
	public boolean isConnected(){return this.connect;}
	
	private boolean autherization = false;
	private void setAutherization(boolean autherization){this.autherization = autherization;}
	public boolean isAuthorized(){return this.autherization;}
	
	private EVatServiceSingleton(){
		prov = ProviderFactory.addAvUniversalProvider();
		Security.addProvider(new AvTLSProvider());
		Security.addProvider(new AvCertStoreProvider());
	}
	
	public static EVatServiceSingleton getInstance(){
		EVatServiceSingleton localInstance = instance;
		if(localInstance == null){
			synchronized (EVatServiceSingleton.class) {
				localInstance = instance;
				if(localInstance == null){
					instance = localInstance = new EVatServiceSingleton(); 
				}
			}
		}
		return localInstance;
	}
	
	public void autherization(ApplicationProperties properties){
		try {
			load(properties.getUrlService());
		} catch (ToolException e) {
			System.err.println(e.getLocalizedMessage());
		}
	}
	
	public void connect(){
		if(this.service != null){
			try {
				service.connect();
				setConnect(true);
			} catch (IOException e) {
				System.err.println(e.getLocalizedMessage());
			} catch (KeyManagementException e) {
				System.err.println(e.getLocalizedMessage());
			} catch (InvalidAlgorithmParameterException e) {
				System.err.println(e.getLocalizedMessage());
			} catch (NoSuchAlgorithmException e) {
				System.err.println(e.getLocalizedMessage());
			} catch (KeyStoreException e) {
				System.err.println(e.getLocalizedMessage());
			} catch (CertificateException e) {
				System.err.println(e.getLocalizedMessage());
			} catch (AvDocException e) {
				System.err.println(e.getLocalizedMessage());
			}
		}else{
			System.out.println("Внимание! Сервис не подключен");
		}
	}
	
	public void disconnect(){
		if(this.service != null){
			try {
				service.disconnect();
				setConnect(false);
			} catch (IOException e) {
				System.err.println(e.getLocalizedMessage());
			}
		}else{
			System.out.println("Внимание! Сервис не подключен");
		}
	}
	
	private void load(String url) throws ToolException{
		try {
			if(this.service == null){
				this.service = new EVatService(url, new KeySelector());
				this.service.login();
				setAutherization(true);
				setConnect(false);
			}else{
				this.service = null;
				load(url);
			}
		} catch ( Exception e) {
			setAutherization(false);
			throw new ToolException("Ошибка: "+e.getLocalizedMessage());
		}
	}
	
}