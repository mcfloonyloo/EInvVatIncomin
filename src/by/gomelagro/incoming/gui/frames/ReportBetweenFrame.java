package by.gomelagro.incoming.gui.frames;

import java.awt.Dialog.ModalExclusionType;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.toedter.calendar.JDateChooser;

import by.gomelagro.incoming.base.ApplicationConstants;
import by.gomelagro.incoming.format.date.InvoiceDateFormat;
import by.gomelagro.incoming.gui.db.WorkingIncomingTable;
import by.gomelagro.incoming.gui.db.files.data.UnloadedInvoice;
import by.gomelagro.incoming.gui.db.files.data.UnloadedInvoiceComparators;
import by.gomelagro.incoming.gui.frames.report.ResultElementList;
import by.gomelagro.incoming.gui.frames.report.ResultFont;
import by.gomelagro.incoming.gui.frames.report.ResultSortComboBoxItem;
import by.gomelagro.incoming.gui.frames.report.ResultStatusComboBoxItem;
import by.gomelagro.incoming.gui.frames.report.models.ResultListModel;
import by.gomelagro.incoming.gui.frames.report.models.renderer.ResultListCellRenderer;
import by.gomelagro.incoming.properties.ApplicationProperties;

public class ReportBetweenFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JDateChooser fromDateChooser;
	private JList<ResultElementList> resultList;
	private ResultListModel listModel;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem saveMenuItem;
	private JMenuItem saveAsMenuItem;
	private JMenuItem saveAsLayoutMenuItem;
	private JLabel fromDateLabel;
	private JLabel generatedReportLabel;
	private JLabel statusLabel;
	private JLabel sortedLabel;
	private JComboBox<ResultStatusComboBoxItem> statusComboBox;
	private JComboBox<ResultSortComboBoxItem> sortedComboBox;
	private JList<String> titleList;
	private JDateChooser toDateChooser;
	private JLabel toLabel;

	/**
	 * Create the frame.
	 */
	public ReportBetweenFrame() {
		initialize();
	}
	
	private void initialize(){
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setType(Type.UTILITY);
		setTitle(ApplicationConstants.APP_REPORTBETWEENFRAME_TITLE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 920, 520);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		fileMenu = new JMenu("Файл");
		menuBar.add(fileMenu);
		
		saveMenuItem = new JMenuItem("Сохранить");
		saveMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				saveFile(ApplicationProperties.getInstance().getFilePath());
			}
		});
		saveMenuItem.setEnabled(false);
		fileMenu.add(saveMenuItem);
		
		saveAsMenuItem = new JMenuItem("Сохранить как...");
		saveAsMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				JFileChooser chooser = new JFileChooser("Сохранить как...");
				chooser.setMultiSelectionEnabled(false);
				chooser.addChoosableFileFilter(new FileNameExtensionFilter("Text files (.txt)", "txt"));
				chooser.setAcceptAllFileFilterUsed(false);
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
					saveFile(chooser.getSelectedFile().getAbsolutePath().trim()+".txt");
				}
			}
		});
		saveAsMenuItem.setEnabled(false);
		fileMenu.add(saveAsMenuItem);
		
		fileMenu.addSeparator();
		
		saveAsLayoutMenuItem = new JMenuItem("Сохранить с оформлением как...");
		saveAsLayoutMenuItem.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent evt) {
				JFileChooser chooser = new JFileChooser("Сохранить как...");
				chooser.setMultiSelectionEnabled(false);
				chooser.addChoosableFileFilter(new FileNameExtensionFilter("Text files (.txt)", "txt"));
				chooser.setAcceptAllFileFilterUsed(false);
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
					saveFileLayout(chooser.getSelectedFile().getAbsolutePath().trim()+".txt");
				}
			}
		});
		saveAsLayoutMenuItem.setEnabled(false);
		fileMenu.add(saveAsLayoutMenuItem);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{20, 48, 0, 120, 0, 120, 0, 0, 20, 0};
		gbl_contentPane.rowHeights = new int[]{12, 24, 0, 0, 0, 20, 0, 20, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		generatedReportLabel = new JLabel("СФОРМИРОВАТЬ ОТЧЕТ");
		GridBagConstraints gbc_generatedReportLabel = new GridBagConstraints();
		gbc_generatedReportLabel.anchor = GridBagConstraints.WEST;
		gbc_generatedReportLabel.gridwidth = 5;
		gbc_generatedReportLabel.insets = new Insets(0, 0, 5, 5);
		gbc_generatedReportLabel.gridx = 1;
		gbc_generatedReportLabel.gridy = 1;
		contentPane.add(generatedReportLabel, gbc_generatedReportLabel);
		
		fromDateLabel = new JLabel("за период с ");
		GridBagConstraints gbc_fromDateLabel = new GridBagConstraints();
		gbc_fromDateLabel.anchor = GridBagConstraints.WEST;
		gbc_fromDateLabel.gridwidth = 2;
		gbc_fromDateLabel.insets = new Insets(0, 0, 5, 5);
		gbc_fromDateLabel.gridx = 1;
		gbc_fromDateLabel.gridy = 2;
		contentPane.add(fromDateLabel, gbc_fromDateLabel);
		
		fromDateChooser = new JDateChooser();
		GridBagConstraints gbc_fromDateChooser = new GridBagConstraints();
		gbc_fromDateChooser.fill = GridBagConstraints.HORIZONTAL;
		gbc_fromDateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_fromDateChooser.gridx = 3;
		gbc_fromDateChooser.gridy = 2;
		contentPane.add(fromDateChooser, gbc_fromDateChooser);
		
		listModel = new ResultListModel();
		
		toLabel = new JLabel(" по ");
		GridBagConstraints gbc_toLabel = new GridBagConstraints();
		gbc_toLabel.insets = new Insets(0, 0, 5, 5);
		gbc_toLabel.gridx = 4;
		gbc_toLabel.gridy = 2;
		contentPane.add(toLabel, gbc_toLabel);
		
		toDateChooser = new JDateChooser();
		GridBagConstraints gbc_toDateChooser = new GridBagConstraints();
		gbc_toDateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_toDateChooser.fill = GridBagConstraints.HORIZONTAL;
		gbc_toDateChooser.gridx = 5;
		gbc_toDateChooser.gridy = 2;
		contentPane.add(toDateChooser, gbc_toDateChooser);
		
		JButton generateButton = new JButton("Сформировать");
		generateButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				listModel.clear();
				if((fromDateChooser.getDate() == null) || (toDateChooser.getDate() == null)){
					JOptionPane.showMessageDialog(null, "Не выбрана дата","Внимание",JOptionPane.WARNING_MESSAGE);
				}else{
					if(fromDateChooser.getDate().getTime()>=toDateChooser.getDate().getTime()){
						JOptionPane.showMessageDialog(null, "Выбран неверный диапазон дат","Внимание",JOptionPane.WARNING_MESSAGE);
					}else{
						saveMenuItem.setEnabled(true);
						saveAsMenuItem.setEnabled(true);
						saveAsLayoutMenuItem.setEnabled(true);
						generated();
					}
				}
			}
		});	
		GridBagConstraints gbc_generateButton = new GridBagConstraints();
		gbc_generateButton.insets = new Insets(0, 0, 5, 5);
		gbc_generateButton.gridx = 6;
		gbc_generateButton.gridy = 2;
		contentPane.add(generateButton, gbc_generateButton);
		
		statusLabel = new JLabel("статусы");
		GridBagConstraints gbc_statusLabel = new GridBagConstraints();
		gbc_statusLabel.anchor = GridBagConstraints.WEST;
		gbc_statusLabel.gridwidth = 2;
		gbc_statusLabel.insets = new Insets(0, 0, 5, 5);
		gbc_statusLabel.gridx = 1;
		gbc_statusLabel.gridy = 3;
		contentPane.add(statusLabel, gbc_statusLabel);
		
		statusComboBox = new JComboBox<ResultStatusComboBoxItem>();
		statusComboBox.addItem(new ResultStatusComboBoxItem("Все",""));
		statusComboBox.addItem(new ResultStatusComboBoxItem("Подписан"," AND STATUSINVOICEEN = 'COMPLETED_SIGNED'"));
		statusComboBox.addItem(new ResultStatusComboBoxItem("Не подписан"," AND (STATUSINVOICEEN = 'COMPLETED'"
																		+ " OR STATUSINVOICEEN = 'ON_AGREEMENT'"
																		+ " OR STATUSINVOICEEN = 'IN_PROGRESS'"
																		+ " OR STATUSINVOICEEN = 'NOT_FOUND')"));
		statusComboBox.addItem(new ResultStatusComboBoxItem("Аннулирован"," AND (STATUSINVOICEEN = 'CANCELLED'"
																		+ " OR STATUSINVOICEEN = 'ON_AGREEMENT_CANCELLED')"));
		statusComboBox.setSelectedIndex(0);
		GridBagConstraints gbc_statusComboBox = new GridBagConstraints();
		gbc_statusComboBox.gridwidth = 4;
		gbc_statusComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_statusComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_statusComboBox.gridx = 3;
		gbc_statusComboBox.gridy = 3;
		contentPane.add(statusComboBox, gbc_statusComboBox);
		
		sortedLabel = new JLabel("сортировать по ");
		GridBagConstraints gbc_sortedLabel = new GridBagConstraints();
		gbc_sortedLabel.anchor = GridBagConstraints.WEST;
		gbc_sortedLabel.gridwidth = 2;
		gbc_sortedLabel.insets = new Insets(0, 0, 5, 5);
		gbc_sortedLabel.gridx = 1;
		gbc_sortedLabel.gridy = 4;
		contentPane.add(sortedLabel, gbc_sortedLabel);
		
		sortedComboBox = new JComboBox<ResultSortComboBoxItem>();
		sortedComboBox.addItem(new ResultSortComboBoxItem("УНП",UnloadedInvoiceComparators.compareToUnp));
		sortedComboBox.addItem(new ResultSortComboBoxItem("Дата совершения (по возрастанию)",UnloadedInvoiceComparators.compareToDateAsc));
		sortedComboBox.addItem(new ResultSortComboBoxItem("Дата совершения (по убыванию)",UnloadedInvoiceComparators.compareToDateDesc));
		sortedComboBox.addItem(new ResultSortComboBoxItem("Статус",UnloadedInvoiceComparators.compareToStatus));
		GridBagConstraints gbc_sortedComboBox = new GridBagConstraints();
		gbc_sortedComboBox.gridwidth = 4;
		gbc_sortedComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_sortedComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_sortedComboBox.gridx = 3;
		gbc_sortedComboBox.gridy = 4;
		contentPane.add(sortedComboBox, gbc_sortedComboBox);
		
		titleList = new JList<String>();
		titleList.setEnabled(false);
		titleList.setFont(new Font("Courier New", Font.BOLD, 11));
		titleList.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			String[] values = new String[] {"    УНП   ;"
										  + "    ДАТА   ;"
										  + "         НОМЕР ЭСЧФ       ;"
										  + "   СТАТУС   ;"
										  + "   БЕЗ НДС  ;"
										  + "     НДС    ;"
										  + "    ВСЕГО   ;"
										  + " ДАТА ДОКУМЕНТА"};
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		GridBagConstraints gbc_titleList = new GridBagConstraints();
		gbc_titleList.gridwidth = 7;
		gbc_titleList.insets = new Insets(0, 0, 5, 5);
		gbc_titleList.fill = GridBagConstraints.BOTH;
		gbc_titleList.gridx = 1;
		gbc_titleList.gridy = 5;
		contentPane.add(titleList, gbc_titleList);
		
		resultList = new JList<ResultElementList>(listModel);
		resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultList.setFont(new Font("Courier New", Font.PLAIN, 11));
		resultList.setCellRenderer(new ResultListCellRenderer());
		JScrollPane scroll_resultTextPane = new JScrollPane(resultList);
		scroll_resultTextPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll_resultTextPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_resultTextPane = new GridBagConstraints();
		gbc_resultTextPane.gridwidth = 7;
		gbc_resultTextPane.insets = new Insets(0, 0, 5, 5);
		gbc_resultTextPane.fill = GridBagConstraints.BOTH;
		gbc_resultTextPane.gridx = 1;
		gbc_resultTextPane.gridy = 6;
		contentPane.add(scroll_resultTextPane, gbc_resultTextPane);
	}
	
	private void generated(){
		List<UnloadedInvoice> list = null;
		try {
			list = WorkingIncomingTable.Report.selectSignedNumbersInvoiceAtBetween(
					Date.valueOf(InvoiceDateFormat.dateReverseSmallDash2String(fromDateChooser.getDate())),
					Date.valueOf(InvoiceDateFormat.dateReverseSmallDash2String(toDateChooser.getDate())),
					((ResultSortComboBoxItem) sortedComboBox.getSelectedItem()).getComparator(), 
					((ResultStatusComboBoxItem) statusComboBox.getSelectedItem()).getSql());
			if(list != null){
				for(UnloadedInvoice invoice : list){
					listModel.addElement(invoice.toString(), invoice.toTrimString(), invoice.getColor(), ResultFont.getFont());
				}
			}else{
				JOptionPane.showMessageDialog(null, "Невозможно обработать неинициализированный список","Ошибка",JOptionPane.ERROR_MESSAGE);
			}
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
		}finally{
			if(list != null){
				list = null;
			}
		}		
	}
	
	private void saveFile(String filePath){
		try (FileWriter writer = new FileWriter(filePath)){
			for(int index=0;index<listModel.size();index++){
				writer.write(listModel.getElementAt(index).getTrimmed()+System.lineSeparator());
			}
			writer.flush();
			JOptionPane.showMessageDialog(null, "Отчет сохранен в файл "+filePath.trim(),"Информация",JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void saveFileLayout(String filePath){
		try (FileWriter writer = new FileWriter(filePath)){
			for(int index=0;index<listModel.size();index++){
				writer.write(listModel.getElementAt(index).getFormatted()+System.lineSeparator());
			}
			writer.flush();
			JOptionPane.showMessageDialog(null, "Отчет сохранен в файл "+filePath.trim(),"Информация",JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public ReportBetweenFrame open(){
		this.setVisible(true);
		return this;
	}
	
	private void menuDispose(){
		menuBar = null;
		fileMenu = null;
		saveMenuItem = null;
		saveAsMenuItem = null;
		saveAsLayoutMenuItem = null;
	}
	
	private void comboBoxDispose(){
		statusComboBox = null;
		sortedComboBox = null;
	}
	
	@Override
	public void dispose(){
		contentPane = null;
		listModel = null;
		titleList = null;
		menuDispose();
		comboBoxDispose();
		super.dispose();
	}

}
