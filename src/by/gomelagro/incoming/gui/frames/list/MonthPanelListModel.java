package by.gomelagro.incoming.gui.frames.list;

import java.util.Date;

import javax.swing.DefaultListModel;

import by.gomelagro.incoming.gui.frames.list.data.MonthPanelItem;

public class MonthPanelListModel extends DefaultListModel<JMonthPanel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public JMonthPanel getSelectedItem(){
		return (JMonthPanel) this.getSelectedItem();
	}
	
	public void addElement(Date firstDay, Date lastDay, int completed, int uncompleted, int cancelled, int undetermined){
		this.addElement(new JMonthPanel(new MonthPanelItem.Builder()
												.setFirstDay(firstDay)
												.setLastDay(lastDay)
												.setCompleted(completed)
												.setUncompleted(uncompleted)
												.setCancelled(cancelled)
												.setUndetermined(undetermined)
												.build()));
	}

}
