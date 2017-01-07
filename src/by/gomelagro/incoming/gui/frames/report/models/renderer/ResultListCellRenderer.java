package by.gomelagro.incoming.gui.frames.report.models.renderer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.StrokeBorder;

import by.gomelagro.incoming.gui.frames.report.ResultElementList;
import by.gomelagro.incoming.gui.frames.report.ResultFont;

public class ResultListCellRenderer extends JLabel implements ListCellRenderer<ResultElementList>{

	private static final long serialVersionUID = 1L;

	@Override
	public Component getListCellRendererComponent(JList<? extends ResultElementList> list, ResultElementList element,
			int index, boolean isSelected, boolean cellHasFocus) {
			
		
		if(isSelected){
			this.setText(element.getFormatted());
			this.setForeground(element.getColor());
			this.setFont(ResultFont.getBoldFont());
			this.setBorder(new CompoundBorder(new LineBorder(new Color(255,255,255), 1),new StrokeBorder(new BasicStroke())));
		}else{
			this.setText(element.getFormatted());
			this.setForeground(element.getColor());
			this.setFont(ResultFont.getFont());
			this.setBorder(new LineBorder(new Color(255,255,255), 2));
		}
		
		return this;
	}

}
