package by.gomelagro.incoming.gui.frames.list;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import by.gomelagro.incoming.gui.frames.list.data.MonthPanelItem;

public class JMonthPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public JMonthPanel(MonthPanelItem item) {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{10, 0, 50, 0, 50, 0, 50, 0, 0, 10, 0};
		gridBagLayout.rowHeights = new int[]{8, 0, 0, 7, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblMonthLabel = new JLabel("Период: ");
		GridBagConstraints gbc_lblMonthLabel = new GridBagConstraints();
		gbc_lblMonthLabel.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblMonthLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblMonthLabel.gridx = 1;
		gbc_lblMonthLabel.gridy = 1;
		add(lblMonthLabel, gbc_lblMonthLabel);
				
		JLabel monthLabel = new JLabel(new SimpleDateFormat("dd.MM.yyyy").format(item.getFirstDay())+" - "+new SimpleDateFormat("dd.MM.yyyy").format(item.getLastDay()));
		monthLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_monthLabel = new GridBagConstraints();
		gbc_monthLabel.anchor = GridBagConstraints.WEST;
		gbc_monthLabel.gridwidth = 7;
		gbc_monthLabel.insets = new Insets(0, 0, 5, 5);
		gbc_monthLabel.gridx = 2;
		gbc_monthLabel.gridy = 1;
		add(monthLabel, gbc_monthLabel);
		
		JLabel lblCompletedLabel = new JLabel("подписано: ");
		GridBagConstraints gbc_lblCompletedLabel = new GridBagConstraints();
		gbc_lblCompletedLabel.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblCompletedLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblCompletedLabel.gridx = 1;
		gbc_lblCompletedLabel.gridy = 2;
		add(lblCompletedLabel, gbc_lblCompletedLabel);
		
		JLabel completedLabel = new JLabel(String.valueOf(item.getCompleted()));
		completedLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_completedLabel = new GridBagConstraints();
		gbc_completedLabel.anchor = GridBagConstraints.SOUTHWEST;
		gbc_completedLabel.insets = new Insets(0, 0, 5, 5);
		gbc_completedLabel.gridx = 2;
		gbc_completedLabel.gridy = 2;
		add(completedLabel, gbc_completedLabel);
		
		JLabel lblUncompletedLabel = new JLabel("не подписано: ");
		GridBagConstraints gbc_lblUncompletedLabel = new GridBagConstraints();
		gbc_lblUncompletedLabel.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblUncompletedLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblUncompletedLabel.gridx = 3;
		gbc_lblUncompletedLabel.gridy = 2;
		add(lblUncompletedLabel, gbc_lblUncompletedLabel);
		
		JLabel uncompletedLabel = new JLabel(String.valueOf(item.getUncompleted()));
		uncompletedLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_uncompletedLabel = new GridBagConstraints();
		gbc_uncompletedLabel.anchor = GridBagConstraints.SOUTHWEST;
		gbc_uncompletedLabel.insets = new Insets(0, 0, 5, 5);
		gbc_uncompletedLabel.gridx = 4;
		gbc_uncompletedLabel.gridy = 2;
		add(uncompletedLabel, gbc_uncompletedLabel);
		
		JLabel lblCancelledLabel = new JLabel("аннулировано: ");
		GridBagConstraints gbc_lblCancelledLabel = new GridBagConstraints();
		gbc_lblCancelledLabel.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblCancelledLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblCancelledLabel.gridx = 5;
		gbc_lblCancelledLabel.gridy = 2;
		add(lblCancelledLabel, gbc_lblCancelledLabel);
		
		JLabel cancelledLabel = new JLabel(String.valueOf(item.getCancelled()));
		cancelledLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_cancelledLabel = new GridBagConstraints();
		gbc_cancelledLabel.anchor = GridBagConstraints.SOUTHWEST;
		gbc_cancelledLabel.insets = new Insets(0, 0, 5, 5);
		gbc_cancelledLabel.gridx = 6;
		gbc_cancelledLabel.gridy = 2;
		add(cancelledLabel, gbc_cancelledLabel);
		
		JLabel lblUndetedminedLabel = new JLabel("не определено: ");
		GridBagConstraints gbc_lblUndetedminedLabel = new GridBagConstraints();
		gbc_lblUndetedminedLabel.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblUndetedminedLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblUndetedminedLabel.gridx = 7;
		gbc_lblUndetedminedLabel.gridy = 2;
		add(lblUndetedminedLabel, gbc_lblUndetedminedLabel);
		
		JLabel undeterminedLabel = new JLabel(String.valueOf(item.getUndetermined()));
		undeterminedLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_undeterminedLabel = new GridBagConstraints();
		gbc_undeterminedLabel.anchor = GridBagConstraints.SOUTHWEST;
		gbc_undeterminedLabel.insets = new Insets(0, 0, 5, 5);
		gbc_undeterminedLabel.gridx = 8;
		gbc_undeterminedLabel.gridy = 2;
		add(undeterminedLabel, gbc_undeterminedLabel);
	}
}
