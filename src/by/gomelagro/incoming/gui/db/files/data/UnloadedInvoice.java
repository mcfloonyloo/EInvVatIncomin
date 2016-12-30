package by.gomelagro.incoming.gui.db.files.data;

public class UnloadedInvoice {
	private String unp;
	private String dateCommission;
	private String numberInvoice;
	private String statusinvoiceRu;
	private String totalCost;
	private String totalVat;
	private String totalAll;
	
	public String getUnp(){return this.unp;}
	public String getDateCommission(){return this.dateCommission;}
	public String getNumberInvoice(){return this.numberInvoice;}
	public String getStatusinvoice(){return this.statusinvoiceRu;}
	public String getTotalCost(){return this.totalCost;}
	public String getTotalVat(){return this.totalVat;}
	public String getTotalAll(){return this.totalAll;}
	
	private UnloadedInvoice(Builder builder){
		this.unp = builder.unp;
		this.dateCommission = builder.dateCommission;
		this.numberInvoice = builder.numberInvoice;
		this.statusinvoiceRu = builder.statusinvoiceRu;
		this.totalCost = builder.totalCost;
		this.totalVat = builder.totalVat;
		this.totalAll = builder.totalAll;	
	}
	
	public static class Builder{
		private String unp = "";
		private String dateCommission = "";
		private String numberInvoice = "";
		private String statusinvoiceRu = "";
		private String totalCost = "";
		private String totalVat = "";
		private String totalAll = "";
		
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
		
		public UnloadedInvoice build(){
			return new UnloadedInvoice(this);
		}
	}
	
	public String toString(){
		return getUnp()+";"+getDateCommission()+";"+getNumberInvoice()+";"+getStatusinvoice()+";"+String.format("%.3f",Float.parseFloat(getTotalCost()))+";"+String.format("%.3f",Float.parseFloat(getTotalVat()))+";"+String.format("%.3f",Float.parseFloat(getTotalAll()));
	}
}
