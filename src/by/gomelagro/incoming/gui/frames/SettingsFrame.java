package by.gomelagro.incoming.gui.frames;

import java.awt.Dialog.ModalExclusionType;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import by.gomelagro.incoming.base.ApplicationConstants;
import by.gomelagro.incoming.properties.ApplicationProperties;
import by.gomelagro.incoming.properties.ApplicationPropertiesTemp;

/**
 * 
 * @author mcfloonyloo
 * @version 0.1
 *
 */

public class SettingsFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField libraryPathTextField;
	private JTextField classPathTextField;
	private JTextField filePathTextField;
	private JTextField dbPathTextField;
	private JTextField urlServiceTextField;

	/**
	 * Create the frame.
	 */
	public SettingsFrame() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				requestClose();
			}
		});
		initialize();
		loadProperties();
	}
	
	private void initialize(){
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setType(Type.UTILITY);
		setTitle(ApplicationConstants.APP_SETTINGSFRAME_TITLE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 700, 310);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{20, 70, 340, 94, 40, 54, 20, 0};
		gbl_contentPane.rowHeights = new int[]{20, 0, 0, 0, 0, 20, 0, 0, 20, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel libraryPathLabel = new JLabel("Library path");
		GridBagConstraints gbc_libraryPathLabel = new GridBagConstraints();
		gbc_libraryPathLabel.anchor = GridBagConstraints.EAST;
		gbc_libraryPathLabel.insets = new Insets(0, 0, 5, 5);
		gbc_libraryPathLabel.gridx = 1;
		gbc_libraryPathLabel.gridy = 1;
		contentPane.add(libraryPathLabel, gbc_libraryPathLabel);
		
		libraryPathTextField = new JTextField();
		GridBagConstraints gbc_libraryPathTextField = new GridBagConstraints();
		gbc_libraryPathTextField.gridwidth = 3;
		gbc_libraryPathTextField.insets = new Insets(0, 0, 5, 5);
		gbc_libraryPathTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_libraryPathTextField.gridx = 2;
		gbc_libraryPathTextField.gridy = 1;
		contentPane.add(libraryPathTextField, gbc_libraryPathTextField);
		libraryPathTextField.setColumns(10);
		
		JButton libraryPathButton = new JButton("...");
		libraryPathButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setMultiSelectionEnabled(false);
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if(chooser.showDialog(null,"Выбрать папку") == JFileChooser.APPROVE_OPTION){
					libraryPathTextField.setText(chooser.getSelectedFile().getAbsolutePath().trim()+";");
				}
			}
		});
		GridBagConstraints gbc_libraryPathButton = new GridBagConstraints();
		gbc_libraryPathButton.anchor = GridBagConstraints.EAST;
		gbc_libraryPathButton.insets = new Insets(0, 0, 5, 5);
		gbc_libraryPathButton.gridx = 5;
		gbc_libraryPathButton.gridy = 1;
		contentPane.add(libraryPathButton, gbc_libraryPathButton);
		
		JLabel classPathLabel = new JLabel("Class path");
		GridBagConstraints gbc_classPathLabel = new GridBagConstraints();
		gbc_classPathLabel.anchor = GridBagConstraints.EAST;
		gbc_classPathLabel.insets = new Insets(0, 0, 5, 5);
		gbc_classPathLabel.gridx = 1;
		gbc_classPathLabel.gridy = 2;
		contentPane.add(classPathLabel, gbc_classPathLabel);
		
		classPathTextField = new JTextField();
		GridBagConstraints gbc_classPathTextField = new GridBagConstraints();
		gbc_classPathTextField.gridwidth = 3;
		gbc_classPathTextField.insets = new Insets(0, 0, 5, 5);
		gbc_classPathTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_classPathTextField.gridx = 2;
		gbc_classPathTextField.gridy = 2;
		contentPane.add(classPathTextField, gbc_classPathTextField);
		classPathTextField.setColumns(10);
		
		JButton classPathButton = new JButton("...");
		classPathButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setMultiSelectionEnabled(false);
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if(chooser.showDialog(null,"Выбрать папку") == JFileChooser.APPROVE_OPTION){
					classPathTextField.setText(".\\jar\\*;"+chooser.getSelectedFile().getAbsolutePath().trim()+"\\*;");
				}
			}
		});
		GridBagConstraints gbc_classPathButton = new GridBagConstraints();
		gbc_classPathButton.anchor = GridBagConstraints.EAST;
		gbc_classPathButton.insets = new Insets(0, 0, 5, 5);
		gbc_classPathButton.gridx = 5;
		gbc_classPathButton.gridy = 2;
		contentPane.add(classPathButton, gbc_classPathButton);
		
		JLabel filePathLabel = new JLabel("File path");
		GridBagConstraints gbc_filePathLabel = new GridBagConstraints();
		gbc_filePathLabel.anchor = GridBagConstraints.EAST;
		gbc_filePathLabel.insets = new Insets(0, 0, 5, 5);
		gbc_filePathLabel.gridx = 1;
		gbc_filePathLabel.gridy = 3;
		contentPane.add(filePathLabel, gbc_filePathLabel);
		
		filePathTextField = new JTextField();
		GridBagConstraints gbc_filePathTextField = new GridBagConstraints();
		gbc_filePathTextField.gridwidth = 3;
		gbc_filePathTextField.insets = new Insets(0, 0, 5, 5);
		gbc_filePathTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_filePathTextField.gridx = 2;
		gbc_filePathTextField.gridy = 3;
		contentPane.add(filePathTextField, gbc_filePathTextField);
		filePathTextField.setColumns(10);
		
		JButton filePathButton = new JButton("...");
		filePathButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setMultiSelectionEnabled(false);
				chooser.addChoosableFileFilter(new FileNameExtensionFilter("Text files (.txt)", "txt"));
				chooser.setAcceptAllFileFilterUsed(false);
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
					filePathTextField.setText(chooser.getSelectedFile().getAbsolutePath().trim()+".txt");
				}
			}
		});
		GridBagConstraints gbc_filePathButton = new GridBagConstraints();
		gbc_filePathButton.anchor = GridBagConstraints.EAST;
		gbc_filePathButton.insets = new Insets(0, 0, 5, 5);
		gbc_filePathButton.gridx = 5;
		gbc_filePathButton.gridy = 3;
		contentPane.add(filePathButton, gbc_filePathButton);
		
		JLabel dbPathLabel = new JLabel("DB path");
		GridBagConstraints gbc_dbPathLabel = new GridBagConstraints();
		gbc_dbPathLabel.anchor = GridBagConstraints.EAST;
		gbc_dbPathLabel.insets = new Insets(0, 0, 5, 5);
		gbc_dbPathLabel.gridx = 1;
		gbc_dbPathLabel.gridy = 4;
		contentPane.add(dbPathLabel, gbc_dbPathLabel);
		
		dbPathTextField = new JTextField();
		GridBagConstraints gbc_dbPathTextField = new GridBagConstraints();
		gbc_dbPathTextField.gridwidth = 3;
		gbc_dbPathTextField.insets = new Insets(0, 0, 5, 5);
		gbc_dbPathTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_dbPathTextField.gridx = 2;
		gbc_dbPathTextField.gridy = 4;
		contentPane.add(dbPathTextField, gbc_dbPathTextField);
		dbPathTextField.setColumns(10);
		
		JButton dbPathButton = new JButton("...");
		dbPathButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setMultiSelectionEnabled(false);
				chooser.addChoosableFileFilter(new FileNameExtensionFilter("SQLite Databases (.sqlite)", "sqlite"));
				chooser.setAcceptAllFileFilterUsed(false);
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if(chooser.showDialog(null,"Сохранить файл") == JFileChooser.APPROVE_OPTION){
					dbPathTextField.setText(chooser.getSelectedFile().getAbsolutePath().trim());
				}
			}
		});
		GridBagConstraints gbc_dbPathButton = new GridBagConstraints();
		gbc_dbPathButton.anchor = GridBagConstraints.EAST;
		gbc_dbPathButton.insets = new Insets(0, 0, 5, 5);
		gbc_dbPathButton.gridx = 5;
		gbc_dbPathButton.gridy = 4;
		contentPane.add(dbPathButton, gbc_dbPathButton);
		
		JLabel urlServiceLabel = new JLabel("URL service");
		GridBagConstraints gbc_urlServiceLabel = new GridBagConstraints();
		gbc_urlServiceLabel.anchor = GridBagConstraints.EAST;
		gbc_urlServiceLabel.insets = new Insets(0, 0, 5, 5);
		gbc_urlServiceLabel.gridx = 1;
		gbc_urlServiceLabel.gridy = 6;
		contentPane.add(urlServiceLabel, gbc_urlServiceLabel);
		
		urlServiceTextField = new JTextField();
		GridBagConstraints gbc_urlServiceTextField = new GridBagConstraints();
		gbc_urlServiceTextField.gridwidth = 4;
		gbc_urlServiceTextField.insets = new Insets(0, 0, 5, 5);
		gbc_urlServiceTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_urlServiceTextField.gridx = 2;
		gbc_urlServiceTextField.gridy = 6;
		contentPane.add(urlServiceTextField, gbc_urlServiceTextField);
		urlServiceTextField.setColumns(10);
		
		JButton OKButton = new JButton("ОК");
		OKButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {		
				requestClose();
			}
		});
		GridBagConstraints gbc_OKButton = new GridBagConstraints();
		gbc_OKButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_OKButton.anchor = GridBagConstraints.SOUTH;
		gbc_OKButton.insets = new Insets(0, 0, 5, 5);
		gbc_OKButton.gridx = 3;
		gbc_OKButton.gridy = 7;
		contentPane.add(OKButton, gbc_OKButton);
		
		JButton cancelButton = new JButton("Отмена");
		cancelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {		
				disposeFrame();
			}
		});
		GridBagConstraints gbc_cancelButton = new GridBagConstraints();
		gbc_cancelButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_cancelButton.anchor = GridBagConstraints.SOUTHWEST;
		gbc_cancelButton.gridwidth = 2;
		gbc_cancelButton.insets = new Insets(0, 0, 5, 5);
		gbc_cancelButton.gridx = 4;
		gbc_cancelButton.gridy = 7;
		contentPane.add(cancelButton, gbc_cancelButton);
	}
	
	public void loadProperties(){
		libraryPathTextField.setText(ApplicationProperties.getInstance().getLibraryPath());
		classPathTextField.setText(ApplicationProperties.getInstance().getClassPath());
		filePathTextField.setText(ApplicationProperties.getInstance().getFilePath());
		dbPathTextField.setText(ApplicationProperties.getInstance().getDbPath());
		urlServiceTextField.setText(ApplicationProperties.getInstance().getUrlService());
	}
	
	private void requestClose(){
		ApplicationPropertiesTemp temp = ApplicationPropertiesTemp.Builder.getInstance().setLibraryPath(libraryPathTextField.getText())
				.setClassPath(classPathTextField.getText())
				.setFilePath(filePathTextField.getText())
				.setDbPath(dbPathTextField.getText())						
				.setUrlService(urlServiceTextField.getText())
				.build();
		
		if(!ApplicationProperties.getInstance().equals(temp)){
			int res = JOptionPane.showConfirmDialog(null, "Сохранить изменения настроек?","Запрос на изменение", JOptionPane.YES_NO_OPTION);
			if(res == JOptionPane.YES_OPTION){
				ApplicationProperties.getInstance().setLibraryPath(temp.getLibraryPath());
				ApplicationProperties.getInstance().setClassPath(temp.getClassPath());
				ApplicationProperties.getInstance().setFilePath(temp.getFilePath());
				ApplicationProperties.getInstance().setDbPath(temp.getDbPath());
				ApplicationProperties.getInstance().setUrlService(temp.getUrlService());
				ApplicationProperties.getInstance().saveProperties();
				disposeFrame();
			}
			
		}else{
			disposeFrame();
		}
	}
	
	private void disposeFrame(){
		this.dispose();
	}

	public SettingsFrame open(){
		this.setVisible(true);
		return this;
	}
}
