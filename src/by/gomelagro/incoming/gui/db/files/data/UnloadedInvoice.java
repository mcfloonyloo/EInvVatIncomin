package by.gomelagro.incoming.gui.db.files.data;

import java.awt.Color;

import by.gomelagro.incoming.format.date.InvoiceDateFormat;

public class UnloadedInvoice {
	private String unp;
	private String dateCommission;
	private String numberInvoice;
	private String statusinvoiceRu;
	private String totalCost;
	private String totalVat;
	private String totalAll;
	private Color color;
	
	public String getUnp(){return this.unp;}
	public String getDateCommission(){return this.dateCommission;}
	public String getNumberInvoice(){return this.numberInvoice;}
	public String getStatusInvoice(){return this.statusinvoiceRu;}
	public String getTotalCost(){return this.totalCost;}
	public String getTotalVat(){return this.totalVat;}
	public String getTotalAll(){return this.totalAll;}
	public Color getColor(){return this.color;}
	
	private UnloadedInvoice(Builder builder){
		this.unp = builder.unp;
		this.dateCommission = builder.dateCommission;
		this.numberInvoice = builder.numberInvoice;
		this.statusinvoiceRu = builder.statusinvoiceRu;
		this.totalCost = builder.totalCost;
		this.totalVat = builder.totalVat;
		this.totalAll = builder.totalAll;	
		this.color = builder.color;
	}
	
	public static class Builder{
		private String unp = "";
		private String dateCommission = "";
		private String numberInvoice = "";
		private String statusinvoiceRu = "";
		private String totalCost = "";
		private String totalVat = "";
		private String totalAll = "";
		private Color color = Color.BLACK;
		
		public Builder(){}
		
		public Builder setUnp(String unp){
			this.unp = unp;
			return this;
		}
		
		public Builder setDateCommission(String dateCommission){
			this.dateCommission = dateCommission;
			return this;
		}
		
		public Builder setNumberInvoice(String numberInvoice){
			this.numberInvoice = numberInvoice;
			return this;
		}
		
		public Builder setStatusInvoiceRu(String statusInvoiceRu){
			this.statusinvoiceRu = statusInvoiceRu;
			return this;
		}
		
		public Builder setTotalCost(String totalCost){
			this.totalCost = totalCost.replace(",", ".");
			return this;
		}
		
		public Builder setTotalVat(String totalVat){
			this.totalVat = totalVat.replace(",", ".");
			return this;
		}
		
		public Builder setTotalAll(String totalAll){
			this.totalAll = totalAll.replace(",", ".");
			return this;
		}
		
		public Builder setColor(Color color){
			this.color = color;
			return this;
		}
		
		public UnloadedInvoice build(){
			return new UnloadedInvoice(this);
		}
	}
	
	public String toTrimString(){
		return getUnp()+";"+getDateCommission()+";"+getNumberInvoice()+";"+getStatusInvoice()+";"+String.format("%.3f",Float.parseFloat(getTotalCost()))+";"+String.format("%.3f",Float.parseFloat(getTotalVat()))+";"+String.format("%.3f",Float.parseFloat(getTotalAll()));
	}
	
	public String toString(){
		return getUnp()+";"+String.format("%11s", getDateCommission())+";"+String.format("%26s",getNumberInvoice())+";"+String.format("%12s", getStatusInvoice())+";"+String.format("%12.3f",Float.parseFloat(getTotalCost()))+";"+String.format("%12.3f",Float.parseFloat(getTotalVat()))+";"+String.format("%12.3f",Float.parseFloat(getTotalAll()));
	}
}
