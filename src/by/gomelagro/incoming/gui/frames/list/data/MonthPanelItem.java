package by.gomelagro.incoming.gui.frames.list.data;

import java.text.ParseException;
import java.util.Date;

import javax.swing.JOptionPane;

import by.gomelagro.incoming.format.date.InvoiceDateFormat;

public class MonthPanelItem {
	private Date    firstDay;
	private Date     lastDay;
	
	private int    completed;
	private int  uncompleted;
	private int    cancelled;
	private int undetermined;
	
	public Date getFirstDay(){return this.firstDay;}
	public Date getLastDay(){return this.lastDay;}
	
	public int getCompleted(){return this.completed;}
	public int getUncompleted(){return this.uncompleted;}
	public int getCancelled(){return this.cancelled;}
	public int getUndetermined(){return this.undetermined;}

	public MonthPanelItem(Builder build){
		this.firstDay     = build.firstDay;
		this.lastDay      = build.lastDay;
		
		this.completed    = build.completed;
		this.uncompleted  = build.uncompleted;
		this.cancelled    = build.cancelled;
		this.undetermined = build.undetermined;
	}
	
	public static class Builder{
		private Date    firstDay;
		private Date     lastDay;
		
		private int    completed;
		private int  uncompleted;
		private int    cancelled;
		private int undetermined;
		
		public Builder(){
			try {
				this.firstDay     = InvoiceDateFormat.string2DateSmallDot("01.01.1999");
				this.lastDay      = InvoiceDateFormat.string2DateSmallDot("31.01.1999");
				
				this.completed    = 0;
				this.uncompleted  = 0;
				this.cancelled    = 0;
				this.undetermined = 0;
			} catch (ParseException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
			}
		}
		
		public Builder setFirstDay(Date firstDay){
			this.firstDay = firstDay;
			return this;
		}
		
		public Builder setLastDay(Date lastDay){
			this.lastDay = lastDay;
			return this;
		}
		
		public Builder setCompleted(int completed){
			this.completed = completed;
			return this;
		}
		
		public Builder setUncompleted(int uncompleted){
			this.uncompleted = uncompleted;
			return this;
		}
		
		public Builder setCancelled(int cancelled){
			this.cancelled = cancelled;
			return this;
		}
		
		public Builder setUndetermined(int undetermined){
			this.undetermined = undetermined;
			return this;
		}
		
		public MonthPanelItem build(){
			return new MonthPanelItem(this);
		}
	}
	
}
