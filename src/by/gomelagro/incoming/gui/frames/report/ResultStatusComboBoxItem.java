package by.gomelagro.incoming.gui.frames.report;

public class ResultStatusComboBoxItem {
	private String text;
	private String sql;
	
	public String getText(){return this.text;}
	public String getSql(){return this.sql;}
	
	public ResultStatusComboBoxItem(String text, String sql){
		this.text = text;
		this.sql = sql;
	}
	
	public String toString(){return this.getText();}
}
