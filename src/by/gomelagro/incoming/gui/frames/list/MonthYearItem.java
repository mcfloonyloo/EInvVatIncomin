package by.gomelagro.incoming.gui.frames.list;

public class MonthYearItem {
	private String  year;
	private String month;
	
	public String getYear(){return this.year;}
	public String getMonth(){return this.month;}
	
	public MonthYearItem(Builder build){
		this.year  = build.year;
		this.month = build.month;
	}
	
	public static class Builder{
		private String  year;
		private String month;
				
		public Builder(){}
		
		public Builder setYear(String year){
			this.year = year;
			return this;
		}
		
		public Builder setMonth(String month){
			this.month = month;
			return this;
		}
		
		public MonthYearItem build(){
			return new MonthYearItem(this);
		}
	}
}
