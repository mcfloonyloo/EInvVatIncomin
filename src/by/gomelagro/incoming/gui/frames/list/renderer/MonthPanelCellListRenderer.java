package by.gomelagro.incoming.gui.frames.list.renderer;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import by.gomelagro.incoming.gui.frames.list.JMonthPanel;

public class MonthPanelCellListRenderer extends DefaultListCellRenderer  implements ListCellRenderer<Object>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		if(value instanceof JMonthPanel){
			JMonthPanel panel = (JMonthPanel) value;
			setBorder(panel.getBorder());
			return panel;
		}else{
			return super.getListCellRendererComponent(list, value.getClass().getName(), index, isSelected, cellHasFocus);
		}
		
	}
	
}
