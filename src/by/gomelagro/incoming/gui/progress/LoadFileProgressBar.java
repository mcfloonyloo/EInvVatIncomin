package by.gomelagro.incoming.gui.progress;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Dialog.ModalExclusionType;

public class LoadFileProgressBar extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JProgressBar mainProgressBar;
	private JButton cancelButton;
	private JLabel mainLabel;
	
	private int currentProgress;
	private int getCurrentProgress(){return this.currentProgress;}
	private void setCurrentProgress(int currentProgress){this.currentProgress = currentProgress;}
	
	private int maxProgress;
	private int getMaxProgress(){return this.maxProgress;}
	private void setMaxProgress(int maxProgress){this.maxProgress = maxProgress;}
	
	private boolean cancelled = false;
	private JLabel progressLabel;
	public boolean isCancelled(){return this.cancelled;}
	private void setCancelled(boolean cancelled){this.cancelled = cancelled;}

	/**
	 * Create the frame.
	 */
	public LoadFileProgressBar(int max) {
		setMaxProgress(max);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setResizable(false);
		setTitle("Загрузка файла");
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 650, 175);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 125, 300, 50, 14, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{10, 40, 10, 30, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		mainLabel = new JLabel("Всего ЭСЧФ загружено");
		GridBagConstraints gbc_mainLabel = new GridBagConstraints();
		gbc_mainLabel.anchor = GridBagConstraints.WEST;
		gbc_mainLabel.insets = new Insets(0, 0, 5, 5);
		gbc_mainLabel.gridx = 1;
		gbc_mainLabel.gridy = 1;
		contentPane.add(mainLabel, gbc_mainLabel);
		
		mainProgressBar = new JProgressBar();
		mainProgressBar.setMinimum(0);
		mainProgressBar.setMaximum(max);
		GridBagConstraints gbc_mainProgressBar = new GridBagConstraints();
		gbc_mainProgressBar.gridwidth = 2;
		gbc_mainProgressBar.insets = new Insets(0, 0, 5, 5);
		gbc_mainProgressBar.fill = GridBagConstraints.BOTH;
		gbc_mainProgressBar.gridx = 2;
		gbc_mainProgressBar.gridy = 1;
		contentPane.add(mainProgressBar, gbc_mainProgressBar);
		
		cancelButton = new JButton("Отмена");
		cancelButton.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent evt){
				setCancelled(true);
			}
		});
		
		progressLabel = new JLabel("");
		GridBagConstraints gbc_progressLabel = new GridBagConstraints();
		gbc_progressLabel.gridwidth = 2;
		gbc_progressLabel.insets = new Insets(0, 0, 5, 5);
		gbc_progressLabel.gridx = 4;
		gbc_progressLabel.gridy = 1;
		contentPane.add(progressLabel, gbc_progressLabel);
		GridBagConstraints gbc_cancelButton = new GridBagConstraints();
		gbc_cancelButton.gridwidth = 3;
		gbc_cancelButton.fill = GridBagConstraints.BOTH;
		gbc_cancelButton.insets = new Insets(0, 0, 0, 5);
		gbc_cancelButton.gridx = 3;
		gbc_cancelButton.gridy = 3;
		contentPane.add(cancelButton, gbc_cancelButton);
	
	}
	
	public void setProgress(int main){
		mainProgressBar.setValue(main);
		setCurrentProgress(main);
		progressLabel.setText("("+getCurrentProgress()+" из "+getMaxProgress()+")");
	}
	
	public LoadFileProgressBar activated(){
		this.setVisible(true);
		return this;
	}
	
	public void disactivated(){
		this.setVisible(false);
		super.dispose();
	}

}
