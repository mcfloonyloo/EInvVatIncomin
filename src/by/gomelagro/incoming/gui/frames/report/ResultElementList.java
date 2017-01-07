package by.gomelagro.incoming.gui.frames.report;

import java.awt.Color;
import java.awt.Font;

public class ResultElementList {
	private String formatted;
	private String trimmed;
	private Color color;
	private Font font;
	
	public String getFormatted(){return this.formatted;}
	public String getTrimmed(){return this.trimmed;}
	public Color getColor(){return this.color;}
	public Font getFont(){return this.font;}
	
	private ResultElementList(Builder builder){
		this.formatted = builder.formatted;
		this.trimmed = builder.trimmed;
		this.color = builder.color;
		this.font = builder.font;
	}
	
	public static class Builder{
		private String formatted;
		private String trimmed;
		private Color color;
		private Font font;
		
		public Builder(){
			this.formatted = "";
			this.trimmed = "";
			this.color = Color.BLACK;
			this.font = new Font("Courier New", Font.PLAIN, 11);
		}
		
		public Builder setFormatted(String formatted){
			this.formatted = formatted;
			return this;
		}
		
		public Builder setTrimmed(String trimmed){
			this.trimmed = trimmed;
			return this;
		}
		
		public Builder setColor(Color color){
			this.color = color;
			return this;
		}
		
		public Builder setFont(Font font){
			this.font = font;
			return this;
		}
		
		public ResultElementList build(){
			return new ResultElementList(this);
		}
	}
	
	public String toString(){
		return this.formatted;
	}
}
