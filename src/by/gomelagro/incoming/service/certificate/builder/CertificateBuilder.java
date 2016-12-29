package by.gomelagro.incoming.service.certificate.builder;

import java.io.IOException;

import javax.swing.JOptionPane;

import by.avest.edoc.client.EVatService;

public class CertificateBuilder{
	private String orgName;
	private String unp2;
	private String unp101;
	private String lastName;
	private String firstMiddleName;
	private String dov;
	private String country;
	private String region;
	private String locality;
	private String subdivision;
	private String post;
	private String place;
	private String passport;
	private String address;
	private String others;
	private String email;
	
	public String getOrgName(){return this.orgName;}
	public String getUnp2(){return this.unp2;}
	public String getUnp101(){return this.unp101;}
	public String getLastName(){return this.lastName;}
	public String getFirstMiddleName(){return this.firstMiddleName;}
	public String getDov(){return this.dov;}
	public String getCountry(){return this.country;}
	public String getRegion(){return this.region;}
	public String getLocality(){return this.locality;}
	public String getSubdivision(){return this.subdivision;}
	public String getPost(){return this.post;}
	public String getPlace(){return this.place;}
	public String getPassport(){return this.passport;}
	public String getAddress(){return this.address;}
	public String getOthers(){return this.others;}
	public String getEmail(){return this.email;}
	
	private EVatService service;
	
	public CertificateBuilder(EVatService service){
		this.service = service;
	}
	
	private String setPosition(String oid){
		try {
			return service.getMyCertProperty(oid);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
			return "";
		}
	}
	
	public CertificateBuilder build(){
		this.orgName = setPosition("2.5.4.10");
		this.unp2 = setPosition("1.2.112.1.2.1.1.1.1.2");
		this.unp101 = setPosition("1.3.6.1.4.1.12656.106.101");
		this.lastName = setPosition("2.5.4.4");
		this.firstMiddleName = setPosition("2.5.4.41");
		this.dov = setPosition("1.3.6.1.5.5.7.9.1");
		this.country = setPosition("2.5.4.6");
		this.region = setPosition("2.5.4.8");
		this.locality = setPosition("2.5.4.7");
		this.subdivision = setPosition("2.5.4.11");
		this.post = setPosition("2.5.4.12");
		this.place = setPosition("1.3.6.1.4.1.12656.5.1");
		this.passport = setPosition("1.3.6.1.4.1.12656.5.3");
		this.address = setPosition("2.5.4.9");
		this.others = setPosition("2.5.4.3");
		this.email = setPosition("1.2.840.113549.1.9.1");
		return this;
	}
	
}
