package by.gomelagro.incoming.gui.frames.models.data;

import java.text.ParseException;

import by.gomelagro.incoming.format.date.InvoiceDateFormat;

public class Incoming {
	private int id;
	private String unp;
	private String name;
	private String numInvoice;
	private String statusInvoiceRu;
	private String dateIssue;
	private String dateSignature;
	private String byInvoice;
	private String dateCancellation;
	private String totalVat;
	private String totalCost;
	
	public int getId(){return this.id;}
	public String getUnp(){return this.unp;}
	public String getName(){return this.name;}
	public String getNumInvoice(){return this.numInvoice;}
	public String getStatusInvoiceRu(){return this.statusInvoiceRu;}
	public String getDateIssue(){return this.dateIssue;}
	public String getDateSignature(){return this.dateSignature;}
	public String getByInvoice(){return this.byInvoice;}
	public String getDateCancellation(){return this.dateCancellation;}
	public String getTotalVat(){return this.totalVat;}
	public String getTotalCost(){return this.totalCost;}
	
	private Incoming(Builder builder){
		this.id = builder.id;
		this.unp = builder.unp;
		this.name = builder.name;
		this.numInvoice = builder.numInvoice;
		this.statusInvoiceRu = builder.statusInvoiceRu;
		this.dateIssue = builder.dateIssue;
		this.dateSignature = builder.dateSignature;
		this.byInvoice = builder.byInvoice;
		this.dateCancellation = builder.dateCancellation;
		this.totalVat = builder.totalVat;
		this.totalCost = builder.totalCost;
	}
	
	public static class Builder{
		private int id = -1;
		private String unp = "";
		private String name = "";
		private String numInvoice = "";
		private String statusInvoiceRu = "";
		private String dateIssue = "";
		private String dateSignature = "";
		private String byInvoice = "";
		private String dateCancellation = "";
		private String totalVat = "";
		private String totalCost = "";
		
		public Builder(){}
		
		public Builder setId(int id){
			this.id = id;
			return this;
		}
		
		public Builder setUnp(String unp){
			this.unp = unp;
			return this;
		}
		
		public Builder setName(String name){
			this.name = name;
			return this;
		}
		
		public Builder setNumInvoice(String numInvoice){
			this.numInvoice = numInvoice;
			return this;
		}
		
		public Builder setStatusInvoiceRu(String statusInvoiceRu){
			this.statusInvoiceRu = statusInvoiceRu;
			return this;
		}
		
		public Builder setDateIssue(String dateIssue){
			this.dateIssue = dateIssue;
			return this;
		}
		
		public Builder setDateSignature(String dateSignature){
			this.dateSignature = dateSignature;
			return this;
		}
		
		public Builder setByInvoice(String byInvoice){
			this.byInvoice = byInvoice;
			return this;
		}
		
		public Builder setDateCancellation(String dateCancellation){
			this.dateCancellation = dateCancellation;
			return this;
		}
		
		public Builder setTotalVat(String totalVat){
			this.totalVat = totalVat;
			return this;
		}
		
		public Builder setTotalCost(String totalCost){
			this.totalCost = totalCost;
			return this;
		}
		
		public Incoming build(){
			return new Incoming(this);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(int col){
		switch(col){
			case 0: return (T) this.getUnp();
			case 1: return (T) this.getName();
			case 2: return (T) this.getNumInvoice();
			case 3: return (T) this.getStatusInvoiceRu();
			case 4: return (T) this.getDateIssue();
			case 5: return (T) this.getDateSignature();
			case 6: return (T) this.getByInvoice();
			case 7: return (T) this.getDateCancellation();
			case 8: return (T) this.getTotalVat();
			case 9: return (T) this.getTotalCost();
			default: return null;
		}
	}
	
	public String getValue(int col) throws ParseException{
		switch(col){
			case 0: return this.getUnp();
			case 1: return this.getName();
			case 2: return this.getNumInvoice();
			case 3: return this.getStatusInvoiceRu();
			case 4: return InvoiceDateFormat.string2String(this.getDateIssue());
			case 5: return InvoiceDateFormat.string2String(this.getDateSignature());
			case 6: return this.getByInvoice();
			case 7: return InvoiceDateFormat.string2String(this.getDateCancellation());
			case 8: return this.getTotalVat();
			case 9: return this.getTotalCost();
			default: return null;
		}
	}
	
	public int getColumnCount(){
		return 10;
	}
}
