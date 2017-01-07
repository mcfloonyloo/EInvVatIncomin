package by.gomelagro.incoming.gui.frames.report.models;

import java.awt.Color;
import java.awt.Font;

import javax.swing.DefaultListModel;

import by.gomelagro.incoming.gui.frames.report.ResultElementList;

public class ResultListModel extends DefaultListModel<ResultElementList> {

	private static final long serialVersionUID = 1L;
		
	public ResultElementList getSelectedItem(){
		return (ResultElementList) this.getSelectedItem();
	}
	
	public void addElement(String formatted, String trimmed, Color color, Font font){
		this.addElement(new ResultElementList.Builder().setFormatted(formatted).setTrimmed(trimmed).setColor(color).setFont(font).build());
	}
	
	

}
