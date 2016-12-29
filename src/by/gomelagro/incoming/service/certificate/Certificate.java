package by.gomelagro.incoming.service.certificate;

import by.gomelagro.incoming.service.certificate.builder.CertificateBuilder;

public class Certificate {
	private static volatile Certificate instance;
	
	public static Certificate getInstance(){
		Certificate localInstance = instance;
		if(localInstance == null){
			synchronized (Certificate.class) {
				localInstance = instance;
				if(localInstance == null){
					instance = localInstance = new Certificate(); 
				}
			}
		}
		return localInstance;
	}
	
	public boolean isNull(){
		if(instance == null){
			return true;
		}else{
			return false;
		}
	}
	
	private Certificate(){
		this.orgName = "";
		this.unp2 = "";
		this.unp101 = "";
		this.lastName = "";
		this.firstMiddleName = "";
		this.dov = "";
		this.country = "";
		this.region = "";
		this.locality = "";
		this.subdivision = "";
		this.post = "";
		this.place = "";
		this.passport = "";
		this.address = "";
		this.others = "";
		this.email = "";
	}
	
	public void load(CertificateBuilder builder){
		this.orgName = builder.getOrgName();
		this.unp2 = builder.getUnp2();
		this.unp101 = builder.getUnp101();
		this.lastName = builder.getLastName();
		this.firstMiddleName = builder.getFirstMiddleName();
		this.dov = builder.getDov();
		this.country = builder.getCountry();
		this.region = builder.getRegion();
		this.locality = builder.getLocality();
		this.subdivision = builder.getSubdivision();
		this.post = builder.getPost();
		this.place = builder.getPlace();
		this.passport = builder.getPassport();
		this.address = builder.getAddress();
		this.others = builder.getOthers();
		this.email = builder.getEmail();
	}
	
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
}
