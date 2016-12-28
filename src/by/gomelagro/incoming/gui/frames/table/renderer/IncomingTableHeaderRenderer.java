package by.gomelagro.incoming.gui.frames.table.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

public class IncomingTableHeaderRenderer extends JLabel implements TableCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
		setFont(new Font("Tahoma", Font.BOLD, 11));
		setHorizontalAlignment(JLabel.LEFT);
		setText(value.toString());
		setForeground(new Color(0, 0, 160));
		setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		return this;
	}

}
