package by.gomelagro.incoming.gui.frames;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import by.gomelagro.incoming.format.date.InvoiceDateFormat;
import by.gomelagro.incoming.gui.db.WorkingIncomingTable;
import by.gomelagro.incoming.gui.db.files.data.UnloadedInvoice;
import by.gomelagro.incoming.gui.db.files.data.UnloadedInvoiceComparators;
import by.gomelagro.incoming.gui.frames.report.ResultStatusComboBoxItem;
import by.gomelagro.incoming.gui.frames.report.ResultElementList;
import by.gomelagro.incoming.gui.frames.report.ResultFont;
import by.gomelagro.incoming.gui.frames.report.ResultSortComboBoxItem;
import by.gomelagro.incoming.gui.frames.report.models.ResultListModel;
import by.gomelagro.incoming.gui.frames.report.models.renderer.ResultListCellRenderer;
import by.gomelagro.incoming.properties.ApplicationProperties;

import java.awt.Dialog.ModalExclusionType;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.ScrollPaneConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JList;
import java.awt.Font;
import javax.swing.ListSelectionModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.AbstractListModel;

/**
 * 
 * @author mcfloonyloo
 * @version 0.1
 *
 */

public class ReportOneDayFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JDateChooser dateChooser;
	private JList<ResultElementList> resultList;
	private ResultListModel listModel;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem saveMenuItem;
	private JMenuItem saveAsMenuItem;
	private JLabel onDateLabel;
	private JLabel generatedReportLabel;
	private JLabel statusLabel;
	private JLabel sortedLabel;
	private JComboBox<ResultStatusComboBoxItem> statusComboBox;
	private JComboBox<ResultSortComboBoxItem> sortedComboBox;
	private JList<String> titleList;

	/**
	 * Create the frame.
	 */
	public ReportOneDayFrame() {
		initialize();
	}

	private void initialize(){		
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setType(Type.UTILITY);
		setTitle("Отчет по ЭСЧФ за один день");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 800, 520);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		fileMenu = new JMenu("Файл");
		menuBar.add(fileMenu);
		
		saveMenuItem = new JMenuItem("Сохранить");
		saveMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				FileWriter writer = null;
				try {
					writer = new FileWriter(ApplicationProperties.getInstance().getFilePath());
					for(int index=0;index<listModel.size();index++){
						writer.write(listModel.getElementAt(index).getTrimmed()+System.lineSeparator());
					}
					writer.flush();
					JOptionPane.showMessageDialog(null, "Отчет сохранен в файл "+ApplicationProperties.getInstance().getFilePath(),"Информация",JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				} finally {
					if(writer != null){
						try{
							writer.close();
						}catch(IOException e){
							JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		fileMenu.add(saveMenuItem);
		
		saveAsMenuItem = new JMenuItem("Сохранить как...");
		fileMenu.add(saveAsMenuItem);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{20, 48, 0, 120, 112, 0, 20, 0};
		gbl_contentPane.rowHeights = new int[]{12, 24, 0, 0, 0, 20, 0, 20, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		generatedReportLabel = new JLabel("СФОРМИРОВАТЬ ОТЧЕТ");
		GridBagConstraints gbc_generatedReportLabel = new GridBagConstraints();
		gbc_generatedReportLabel.anchor = GridBagConstraints.WEST;
		gbc_generatedReportLabel.gridwidth = 4;
		gbc_generatedReportLabel.insets = new Insets(0, 0, 5, 5);
		gbc_generatedReportLabel.gridx = 1;
		gbc_generatedReportLabel.gridy = 1;
		contentPane.add(generatedReportLabel, gbc_generatedReportLabel);
		
		onDateLabel = new JLabel("на дату ");
		GridBagConstraints gbc_onDateLabel = new GridBagConstraints();
		gbc_onDateLabel.anchor = GridBagConstraints.WEST;
		gbc_onDateLabel.gridwidth = 2;
		gbc_onDateLabel.insets = new Insets(0, 0, 5, 5);
		gbc_onDateLabel.gridx = 1;
		gbc_onDateLabel.gridy = 2;
		contentPane.add(onDateLabel, gbc_onDateLabel);
		
		dateChooser = new JDateChooser();
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser.gridx = 3;
		gbc_dateChooser.gridy = 2;
		contentPane.add(dateChooser, gbc_dateChooser);
		
		JButton generateButton = new JButton("Сформировать");
		generateButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				listModel.clear();
				if(dateChooser.getDate() == null){
					JOptionPane.showMessageDialog(null, "Не выбрана дата","Внимание",JOptionPane.WARNING_MESSAGE);
				}else{
					generated();	
				}
			}
		});
		GridBagConstraints gbc_generateButton = new GridBagConstraints();
		gbc_generateButton.insets = new Insets(0, 0, 5, 5);
		gbc_generateButton.gridx = 4;
		gbc_generateButton.gridy = 2;
		contentPane.add(generateButton, gbc_generateButton);
		
		listModel = new ResultListModel();
		
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
		statusComboBox.addItem(new ResultStatusComboBoxItem("Не подписан"," AND (STATUSINVOICEEN = 'COMPLETED' OR STATUSINVOICEEN = 'ON_AGREEMENT' OR STATUSINVOICEEN = 'IN_PROGRESS' OR STATUSINVOICEEN = 'NOT_FOUND')"));
		statusComboBox.setSelectedIndex(0);
		GridBagConstraints gbc_statusComboBox = new GridBagConstraints();
		gbc_statusComboBox.gridwidth = 2;
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
		sortedComboBox.addItem(new ResultSortComboBoxItem("Дата совершения",UnloadedInvoiceComparators.compareToDate));
		sortedComboBox.addItem(new ResultSortComboBoxItem("Статус",UnloadedInvoiceComparators.compareToStatus));
		GridBagConstraints gbc_sortedComboBox = new GridBagConstraints();
		gbc_sortedComboBox.gridwidth = 2;
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
			String[] values = new String[] {"   УНП   ;    ДАТА   ;         НОМЕР ЭСЧФ       ;   СТАТУС   ;   БЕЗ НДС  ;     НДС    ;    ВСЕГО"};
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		GridBagConstraints gbc_titleList = new GridBagConstraints();
		gbc_titleList.gridwidth = 5;
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
		gbc_resultTextPane.gridwidth = 5;
		gbc_resultTextPane.insets = new Insets(0, 0, 5, 5);
		gbc_resultTextPane.fill = GridBagConstraints.BOTH;
		gbc_resultTextPane.gridx = 1;
		gbc_resultTextPane.gridy = 6;
		contentPane.add(scroll_resultTextPane, gbc_resultTextPane);
	}
	
	private void generated(){
		List<UnloadedInvoice> list = new ArrayList<UnloadedInvoice>();
		try {
			list = WorkingIncomingTable.selectSignedNumbersInvoiceAtDate(InvoiceDateFormat.string2DateSmallDot(InvoiceDateFormat.dateSmallDot2String(dateChooser.getDate())), ((ResultSortComboBoxItem) sortedComboBox.getSelectedItem()).getComparator(), 
					((ResultStatusComboBoxItem) statusComboBox.getSelectedItem()).getSql());
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
		}
		if(list != null){
			for(UnloadedInvoice invoice : list){
				listModel.addElement(invoice.toString(), invoice.toTrimString(), invoice.getColor(), ResultFont.getFont());
			}
		}else{
			JOptionPane.showMessageDialog(null, "Невозможно обработать неинициализированный список","Ошибка",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/*private void disposeFrame(){
		this.dispose();
	}*/
	
	public ReportOneDayFrame open(){
		this.setVisible(true);
		return this;
	}
}
