package by.gomelagro.incoming.gui.frames;

import java.awt.Dialog.ModalExclusionType;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import by.gomelagro.incoming.service.certificate.Certificate;

public class ShowCertificateFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField orgNameTextField;
	private JTextField unp2TextField;
	private JTextField unp101TextField;
	private JTextField lastNameTextField;
	private JTextField firstMiddleNameTextField;
	private JTextField dovTextField;
	private JTextField countryTextField;
	private JTextField regionTextField;
	private JTextField localityTextField;
	private JTextField subdivisionTextField;
	private JTextField postTextField;
	private JTextField placeTextField;
	private JTextPane passportTextPane;
	private JTextPane addressTextPane;
	private JTextPane othersTextPane;
	private JTextField emailTextField;

	/**
	 * Create the frame.
	 */
	public ShowCertificateFrame() {
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 710, 520);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("Информация о сертификате");
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 170, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 40, 40, 40, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel titleLabel = new JLabel("СВЕДЕНИЯ О ДЕРЖАТЕЛЕ СЕРТИФИКАТА");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_titleLabel = new GridBagConstraints();
		gbc_titleLabel.gridwidth = 2;
		gbc_titleLabel.insets = new Insets(0, 0, 5, 5);
		gbc_titleLabel.gridx = 1;
		gbc_titleLabel.gridy = 0;
		contentPane.add(titleLabel, gbc_titleLabel);
		
		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.gridwidth = 2;
		gbc_separator.fill = GridBagConstraints.BOTH;
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.gridx = 1;
		gbc_separator.gridy = 1;
		contentPane.add(separator, gbc_separator);
		
		JLabel orgNameTitleLabel = new JLabel("Наименование организации");
		GridBagConstraints gbc_orgNameTitleLabel = new GridBagConstraints();
		gbc_orgNameTitleLabel.anchor = GridBagConstraints.WEST;
		gbc_orgNameTitleLabel.insets = new Insets(0, 0, 5, 5);
		gbc_orgNameTitleLabel.gridx = 1;
		gbc_orgNameTitleLabel.gridy = 2;
		contentPane.add(orgNameTitleLabel, gbc_orgNameTitleLabel);
		
		orgNameTextField = new JTextField();
		orgNameTextField.setEditable(false);
		orgNameTextField.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_orgNameTextField = new GridBagConstraints();
		gbc_orgNameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_orgNameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_orgNameTextField.gridx = 2;
		gbc_orgNameTextField.gridy = 2;
		contentPane.add(orgNameTextField, gbc_orgNameTextField);
		orgNameTextField.setColumns(10);
		
		JLabel unp2TitleLabel = new JLabel("УНП организации");
		GridBagConstraints gbc_unp2TitleLabel = new GridBagConstraints();
		gbc_unp2TitleLabel.anchor = GridBagConstraints.WEST;
		gbc_unp2TitleLabel.insets = new Insets(0, 0, 5, 5);
		gbc_unp2TitleLabel.gridx = 1;
		gbc_unp2TitleLabel.gridy = 3;
		contentPane.add(unp2TitleLabel, gbc_unp2TitleLabel);
		
		unp2TextField = new JTextField();
		unp2TextField.setEditable(false);
		unp2TextField.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_unp2TextField = new GridBagConstraints();
		gbc_unp2TextField.insets = new Insets(0, 0, 5, 5);
		gbc_unp2TextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_unp2TextField.gridx = 2;
		gbc_unp2TextField.gridy = 3;
		contentPane.add(unp2TextField, gbc_unp2TextField);
		unp2TextField.setColumns(10);
		
		JLabel unp101TitleLabel = new JLabel("УНП организации");
		GridBagConstraints gbc_unp101TitleLabel = new GridBagConstraints();
		gbc_unp101TitleLabel.anchor = GridBagConstraints.WEST;
		gbc_unp101TitleLabel.insets = new Insets(0, 0, 5, 5);
		gbc_unp101TitleLabel.gridx = 1;
		gbc_unp101TitleLabel.gridy = 4;
		contentPane.add(unp101TitleLabel, gbc_unp101TitleLabel);
		
		unp101TextField = new JTextField();
		unp101TextField.setEditable(false);
		unp101TextField.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_unp101TextField = new GridBagConstraints();
		gbc_unp101TextField.insets = new Insets(0, 0, 5, 5);
		gbc_unp101TextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_unp101TextField.gridx = 2;
		gbc_unp101TextField.gridy = 4;
		contentPane.add(unp101TextField, gbc_unp101TextField);
		unp101TextField.setColumns(10);
		
		JLabel lastNameTitleLabel = new JLabel("Фамилия");
		GridBagConstraints gbc_lastNameTitleLabel = new GridBagConstraints();
		gbc_lastNameTitleLabel.anchor = GridBagConstraints.WEST;
		gbc_lastNameTitleLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lastNameTitleLabel.gridx = 1;
		gbc_lastNameTitleLabel.gridy = 5;
		contentPane.add(lastNameTitleLabel, gbc_lastNameTitleLabel);
		
		lastNameTextField = new JTextField();
		lastNameTextField.setEditable(false);
		lastNameTextField.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lastNameTextField = new GridBagConstraints();
		gbc_lastNameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_lastNameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_lastNameTextField.gridx = 2;
		gbc_lastNameTextField.gridy = 5;
		contentPane.add(lastNameTextField, gbc_lastNameTextField);
		lastNameTextField.setColumns(10);
		
		JLabel firstMiddleNameTitleLabel = new JLabel("Имя, Отчество");
		GridBagConstraints gbc_firstMiddleNameTitleLabel = new GridBagConstraints();
		gbc_firstMiddleNameTitleLabel.anchor = GridBagConstraints.WEST;
		gbc_firstMiddleNameTitleLabel.insets = new Insets(0, 0, 5, 5);
		gbc_firstMiddleNameTitleLabel.gridx = 1;
		gbc_firstMiddleNameTitleLabel.gridy = 6;
		contentPane.add(firstMiddleNameTitleLabel, gbc_firstMiddleNameTitleLabel);
		
		firstMiddleNameTextField = new JTextField();
		firstMiddleNameTextField.setEditable(false);
		firstMiddleNameTextField.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_firstMiddleNameTextField = new GridBagConstraints();
		gbc_firstMiddleNameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_firstMiddleNameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_firstMiddleNameTextField.gridx = 2;
		gbc_firstMiddleNameTextField.gridy = 6;
		contentPane.add(firstMiddleNameTextField, gbc_firstMiddleNameTextField);
		firstMiddleNameTextField.setColumns(10);
		
		JLabel dovTitleLabel = new JLabel("Дата рождения");
		GridBagConstraints gbc_dovTitleLabel = new GridBagConstraints();
		gbc_dovTitleLabel.anchor = GridBagConstraints.WEST;
		gbc_dovTitleLabel.insets = new Insets(0, 0, 5, 5);
		gbc_dovTitleLabel.gridx = 1;
		gbc_dovTitleLabel.gridy = 7;
		contentPane.add(dovTitleLabel, gbc_dovTitleLabel);
		
		dovTextField = new JTextField();
		dovTextField.setEditable(false);
		dovTextField.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_dovTextField = new GridBagConstraints();
		gbc_dovTextField.insets = new Insets(0, 0, 5, 5);
		gbc_dovTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_dovTextField.gridx = 2;
		gbc_dovTextField.gridy = 7;
		contentPane.add(dovTextField, gbc_dovTextField);
		dovTextField.setColumns(10);
		
		JLabel countryTitleLabel = new JLabel("Страна");
		GridBagConstraints gbc_countryTitleLabel = new GridBagConstraints();
		gbc_countryTitleLabel.anchor = GridBagConstraints.WEST;
		gbc_countryTitleLabel.insets = new Insets(0, 0, 5, 5);
		gbc_countryTitleLabel.gridx = 1;
		gbc_countryTitleLabel.gridy = 8;
		contentPane.add(countryTitleLabel, gbc_countryTitleLabel);
		
		countryTextField = new JTextField();
		countryTextField.setEditable(false);
		countryTextField.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_countryTextField = new GridBagConstraints();
		gbc_countryTextField.insets = new Insets(0, 0, 5, 5);
		gbc_countryTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_countryTextField.gridx = 2;
		gbc_countryTextField.gridy = 8;
		contentPane.add(countryTextField, gbc_countryTextField);
		countryTextField.setColumns(10);
		
		JLabel regionTitleLabel = new JLabel("Область");
		GridBagConstraints gbc_regionTitleLabel = new GridBagConstraints();
		gbc_regionTitleLabel.anchor = GridBagConstraints.WEST;
		gbc_regionTitleLabel.insets = new Insets(0, 0, 5, 5);
		gbc_regionTitleLabel.gridx = 1;
		gbc_regionTitleLabel.gridy = 9;
		contentPane.add(regionTitleLabel, gbc_regionTitleLabel);
		
		regionTextField = new JTextField();
		regionTextField.setEditable(false);
		regionTextField.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_regionTextField = new GridBagConstraints();
		gbc_regionTextField.insets = new Insets(0, 0, 5, 5);
		gbc_regionTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_regionTextField.gridx = 2;
		gbc_regionTextField.gridy = 9;
		contentPane.add(regionTextField, gbc_regionTextField);
		regionTextField.setColumns(10);
		
		JLabel localityTitleLabel = new JLabel("Населенный пункт");
		GridBagConstraints gbc_localityTitleLabel = new GridBagConstraints();
		gbc_localityTitleLabel.anchor = GridBagConstraints.WEST;
		gbc_localityTitleLabel.insets = new Insets(0, 0, 5, 5);
		gbc_localityTitleLabel.gridx = 1;
		gbc_localityTitleLabel.gridy = 10;
		contentPane.add(localityTitleLabel, gbc_localityTitleLabel);
		
		localityTextField = new JTextField();
		localityTextField.setEditable(false);
		localityTextField.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_localityTextField = new GridBagConstraints();
		gbc_localityTextField.insets = new Insets(0, 0, 5, 5);
		gbc_localityTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_localityTextField.gridx = 2;
		gbc_localityTextField.gridy = 10;
		contentPane.add(localityTextField, gbc_localityTextField);
		localityTextField.setColumns(10);
		
		JLabel subdivisionTitleLabel = new JLabel("Подразделение");
		GridBagConstraints gbc_subdivisionTitleLabel = new GridBagConstraints();
		gbc_subdivisionTitleLabel.anchor = GridBagConstraints.WEST;
		gbc_subdivisionTitleLabel.insets = new Insets(0, 0, 5, 5);
		gbc_subdivisionTitleLabel.gridx = 1;
		gbc_subdivisionTitleLabel.gridy = 11;
		contentPane.add(subdivisionTitleLabel, gbc_subdivisionTitleLabel);
		
		subdivisionTextField = new JTextField();
		subdivisionTextField.setEditable(false);
		subdivisionTextField.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_subdivisionTextField = new GridBagConstraints();
		gbc_subdivisionTextField.insets = new Insets(0, 0, 5, 5);
		gbc_subdivisionTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_subdivisionTextField.gridx = 2;
		gbc_subdivisionTextField.gridy = 11;
		contentPane.add(subdivisionTextField, gbc_subdivisionTextField);
		subdivisionTextField.setColumns(10);
		
		JLabel postTitleLabel = new JLabel("Должность");
		GridBagConstraints gbc_postTitleLabel = new GridBagConstraints();
		gbc_postTitleLabel.anchor = GridBagConstraints.WEST;
		gbc_postTitleLabel.insets = new Insets(0, 0, 5, 5);
		gbc_postTitleLabel.gridx = 1;
		gbc_postTitleLabel.gridy = 12;
		contentPane.add(postTitleLabel, gbc_postTitleLabel);
		
		postTextField = new JTextField();
		postTextField.setEditable(false);
		postTextField.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_postTextField = new GridBagConstraints();
		gbc_postTextField.insets = new Insets(0, 0, 5, 5);
		gbc_postTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_postTextField.gridx = 2;
		gbc_postTextField.gridy = 12;
		contentPane.add(postTextField, gbc_postTextField);
		postTextField.setColumns(10);
		
		JLabel placeTitleLabel = new JLabel("Место работы и должность");
		GridBagConstraints gbc_placeTitleLabel = new GridBagConstraints();
		gbc_placeTitleLabel.anchor = GridBagConstraints.WEST;
		gbc_placeTitleLabel.insets = new Insets(0, 0, 5, 5);
		gbc_placeTitleLabel.gridx = 1;
		gbc_placeTitleLabel.gridy = 13;
		contentPane.add(placeTitleLabel, gbc_placeTitleLabel);
		
		placeTextField = new JTextField();
		placeTextField.setEditable(false);
		placeTextField.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_placeTextField = new GridBagConstraints();
		gbc_placeTextField.insets = new Insets(0, 0, 5, 5);
		gbc_placeTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_placeTextField.gridx = 2;
		gbc_placeTextField.gridy = 13;
		contentPane.add(placeTextField, gbc_placeTextField);
		placeTextField.setColumns(10);
		
		JLabel passportTitleLabel = new JLabel("<html>Данные из документа,<br>удостоверяющего личность");
		GridBagConstraints gbc_passportTitleLabel = new GridBagConstraints();
		gbc_passportTitleLabel.anchor = GridBagConstraints.WEST;
		gbc_passportTitleLabel.insets = new Insets(0, 0, 5, 5);
		gbc_passportTitleLabel.gridx = 1;
		gbc_passportTitleLabel.gridy = 14;
		contentPane.add(passportTitleLabel, gbc_passportTitleLabel);
		
		passportTextPane = new JTextPane();
		passportTextPane.setEditable(false);
		JScrollPane passportScrollPane = new JScrollPane(passportTextPane);
		passportScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GridBagConstraints gbc_passportTextPane = new GridBagConstraints();
		gbc_passportTextPane.insets = new Insets(0, 0, 5, 5);
		gbc_passportTextPane.fill = GridBagConstraints.BOTH;
		gbc_passportTextPane.gridx = 2;
		gbc_passportTextPane.gridy = 14;
		contentPane.add(passportScrollPane, gbc_passportTextPane);
		
		JLabel addressTitleLabel = new JLabel("Адрес");
		GridBagConstraints gbc_addressTitleLabel = new GridBagConstraints();
		gbc_addressTitleLabel.anchor = GridBagConstraints.WEST;
		gbc_addressTitleLabel.insets = new Insets(0, 0, 5, 5);
		gbc_addressTitleLabel.gridx = 1;
		gbc_addressTitleLabel.gridy = 15;
		contentPane.add(addressTitleLabel, gbc_addressTitleLabel);
		
		addressTextPane = new JTextPane();
		addressTextPane.setEditable(false);
		JScrollPane addressScrollPane = new JScrollPane(addressTextPane);
		addressScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GridBagConstraints gbc_addressTextPane = new GridBagConstraints();
		gbc_addressTextPane.insets = new Insets(0, 0, 5, 5);
		gbc_addressTextPane.fill = GridBagConstraints.BOTH;
		gbc_addressTextPane.gridx = 2;
		gbc_addressTextPane.gridy = 15;
		contentPane.add(addressScrollPane, gbc_addressTextPane);
		
		JLabel othersTitleLabel = new JLabel("Общие данные");
		GridBagConstraints gbc_othersTitleLabel = new GridBagConstraints();
		gbc_othersTitleLabel.anchor = GridBagConstraints.WEST;
		gbc_othersTitleLabel.insets = new Insets(0, 0, 5, 5);
		gbc_othersTitleLabel.gridx = 1;
		gbc_othersTitleLabel.gridy = 16;
		contentPane.add(othersTitleLabel, gbc_othersTitleLabel);
		
		othersTextPane = new JTextPane();
		JScrollPane othersScrollPane = new JScrollPane(othersTextPane);
		othersScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		othersTextPane.setEditable(false);
		GridBagConstraints gbc_othersTextPane = new GridBagConstraints();
		gbc_othersTextPane.insets = new Insets(0, 0, 5, 5);
		gbc_othersTextPane.fill = GridBagConstraints.BOTH;
		gbc_othersTextPane.gridx = 2;
		gbc_othersTextPane.gridy = 16;
		contentPane.add(othersScrollPane, gbc_othersTextPane);
		
		JLabel emailTitleLabel = new JLabel("Данные электронной почты");
		GridBagConstraints gbc_emailTitleLabel = new GridBagConstraints();
		gbc_emailTitleLabel.anchor = GridBagConstraints.WEST;
		gbc_emailTitleLabel.insets = new Insets(0, 0, 0, 5);
		gbc_emailTitleLabel.gridx = 1;
		gbc_emailTitleLabel.gridy = 17;
		contentPane.add(emailTitleLabel, gbc_emailTitleLabel);
		
		emailTextField = new JTextField();
		emailTextField.setEditable(false);
		emailTextField.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_emailTextField = new GridBagConstraints();
		gbc_emailTextField.insets = new Insets(0, 0, 0, 5);
		gbc_emailTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_emailTextField.gridx = 2;
		gbc_emailTextField.gridy = 17;
		contentPane.add(emailTextField, gbc_emailTextField);
		emailTextField.setColumns(10);
	}

	public ShowCertificateFrame open(){
		this.setVisible(true);
		if(!Certificate.getInstance().isNull()){
			this.orgNameTextField.setText(Certificate.getInstance().getOrgName());
			this.unp2TextField.setText(Certificate.getInstance().getUnp2());
			this.unp101TextField.setText(Certificate.getInstance().getUnp101());
			this.lastNameTextField.setText(Certificate.getInstance().getLastName());
			this.firstMiddleNameTextField.setText(Certificate.getInstance().getFirstMiddleName());
			this.dovTextField.setText(Certificate.getInstance().getDov());
			this.countryTextField.setText(Certificate.getInstance().getCountry());
			this.regionTextField.setText(Certificate.getInstance().getRegion());
			this.localityTextField.setText(Certificate.getInstance().getLocality());
			this.subdivisionTextField.setText(Certificate.getInstance().getSubdivision());
			this.postTextField.setText(Certificate.getInstance().getPost());
			this.placeTextField.setText(Certificate.getInstance().getPlace());
			this.passportTextPane.setText(Certificate.getInstance().getPassport());
			this.addressTextPane.setText(Certificate.getInstance().getAddress());
			this.othersTextPane.setText(Certificate.getInstance().getOthers());
			this.emailTextField.setText(Certificate.getInstance().getEmail());
		}
		return this;
	}
	
	public void close(){
		dispose();
	}
}
