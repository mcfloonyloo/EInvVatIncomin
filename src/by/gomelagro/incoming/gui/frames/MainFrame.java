package by.gomelagro.incoming.gui.frames;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;

import by.avest.edoc.client.AvEStatus;
import by.gomelagro.incoming.gui.console.JConsole;
import by.gomelagro.incoming.gui.db.ConnectionDB;
import by.gomelagro.incoming.gui.db.WorkingIncomingTable;
import by.gomelagro.incoming.gui.db.files.WorkingFiles;
import by.gomelagro.incoming.gui.frames.models.IncomingTableModel;
import by.gomelagro.incoming.gui.frames.table.renderer.IncomingTableHeaderRenderer;
import by.gomelagro.incoming.gui.progress.LoadFileProgressBar;
import by.gomelagro.incoming.properties.ApplicationProperties;
import by.gomelagro.incoming.service.EVatServiceSingleton;
import by.gomelagro.incoming.service.certificate.Certificate;

public class MainFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	//private JFrame frame;
	private JTextPane console;
	private JMenuItem authMenuItem;
	private JMenuItem infoCertMenuItem;
	private JMenuItem connectMenuItem;
	private JMenuItem disconnectMenuItem;
	private JMenuItem loadFileMenuItem;
	private JMenuItem updateStatusMenuItem;
	private JMenuItem fastUpdateStatusMenuItem;
	private JMenuItem saveFileMenuItem;
	
	private final String title = "Приложение для обработки входящих ЭСЧФ v0.3";
	private JTable table;
	
	static{
		ApplicationProperties.getInstance();	
		System.setProperty("by.avest.loader.shared","true");
		System.setProperty("java.library.path",ApplicationProperties.getInstance().getLibraryPath().trim());
		System.setProperty("classpath", ApplicationProperties.getInstance().getClassPath().trim());
	}
	
	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
		setVisible(true);
	}

	/**
	 * Methods autherization and connection to service
	 */
	private void autherization(){
		EVatServiceSingleton.getInstance().autherization(ApplicationProperties.getInstance());
		if(EVatServiceSingleton.getInstance().isAuthorized()){
			System.out.println("Авторизация пройдена");
			authMenuItem.setEnabled(false);
			connectMenuItem.setEnabled(true);
			disconnectMenuItem.setEnabled(false);
			infoCertMenuItem.setEnabled(true);
			loadFileMenuItem.setEnabled(true);
			setTitle(title+" ["+ Certificate.getInstance().getOrgName().trim() +" " +Certificate.getInstance().getLastName().trim()+" "+Certificate.getInstance().getFirstMiddleName()+"]");
		}
	}
	
	private void connect(){
		if(EVatServiceSingleton.getInstance().isAuthorized()){
			EVatServiceSingleton.getInstance().connect();
			if(EVatServiceSingleton.getInstance().isConnected()){
				console.setText("");
				System.out.println("Авторизация пройдена");
				System.out.println("Подключение к сервису "+ApplicationProperties.getInstance().getUrlService()+" выполнено успешно");
				connectMenuItem.setEnabled(false);
				disconnectMenuItem.setEnabled(true);
				
				updateStatusMenuItem.setEnabled(true);
				fastUpdateStatusMenuItem.setEnabled(true);
				
				//saveFileMenuItem.setEnabled(true);
			}else{
				System.err.println("Ошибка подключения к сервису "+ApplicationProperties.getInstance().getUrlService());
			}
		}
	}
	
	private void disconnect(){
		if(EVatServiceSingleton.getInstance().isAuthorized()){
			if(EVatServiceSingleton.getInstance().isConnected()){
				EVatServiceSingleton.getInstance().disconnect();
				if(!EVatServiceSingleton.getInstance().isConnected()){
					System.out.println("Отключение от сервиса "+ApplicationProperties.getInstance().getUrlService()+" выполнено успешно");
					connectMenuItem.setEnabled(true);
					disconnectMenuItem.setEnabled(false);
					
					updateStatusMenuItem.setEnabled(false);
					fastUpdateStatusMenuItem.setEnabled(false);
					
					//saveFileMenuItem.setEnabled(false);
				}else{
					System.err.println("Ошибка отключения от сервиса "+ApplicationProperties.getInstance().getUrlService());
				}
			}
		}
	}
	
	private void loadFile(){
		if(EVatServiceSingleton.getInstance().isAuthorized()){
			JFileChooser chooser = new JFileChooser();
			int res = chooser.showDialog(null, "Открыть");
			SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
				
				@Override
				protected Void doInBackground() throws Exception {
					List<String> lines = null;
					if(res == JFileChooser.APPROVE_OPTION){
						try {
							lines = WorkingFiles.readCSVFile(chooser.getSelectedFile());
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
						}
						if(lines != null){
							int avialCount = 0;
							int errorCount = 0;
							int notavialCount = 0;
							LoadFileProgressBar progress = new LoadFileProgressBar(lines.size()).activated();
							for(int index=0; index<lines.size();index++){
								String[] fields = lines.get(index).split(";");
								if(fields[0].trim().equals(Certificate.getInstance().getUnp2())){//изменить на чтение сертификата
									JOptionPane.showMessageDialog(null, "Попытка чтения файла с исходящими ЭСЧФ","Внимание",JOptionPane.WARNING_MESSAGE);
									break;
								}
								switch(WorkingIncomingTable.getCountRecord(fields[8])){
								case -1: JOptionPane.showMessageDialog(null, "Ошибка проверки наличия записи ЭСЧФ "+fields[8]+" в таблице","Ошибка",JOptionPane.ERROR_MESSAGE); errorCount++; break;
								case  0: if(WorkingIncomingTable.insertIncoming(fields)) {notavialCount++;}else{errorCount++;} break;
								default: avialCount++; break;
								}
								progress.setProgress(index);		
								if(progress.isCancelled()){
									JOptionPane.showMessageDialog(null, "Чтение файла отменено","Внимание",JOptionPane.WARNING_MESSAGE);
									break;
								}
							}
							JOptionPane.showMessageDialog(null, "Добавлено "+notavialCount+" ЭСЧФ"+System.lineSeparator()+
									"Не добавлено из-за их наличия "+avialCount+" ЭСЧФ"+System.lineSeparator()+
									"Не добавлено из-за ошибок "+errorCount+" ЭСЧФ","Информация",JOptionPane.INFORMATION_MESSAGE);
							progress.disactivated();
						}else{
							JOptionPane.showMessageDialog(null, "Загружен файл неверной структуры"+System.lineSeparator()+
									"Выберите другой файл","Ошибка",JOptionPane.ERROR_MESSAGE);
						}
					}
					return null;		
				}			
			};	
			worker.execute();
		}else{
			JOptionPane.showMessageDialog(null, "Для обновления таблицы выставленных ЭСЧФ"+System.lineSeparator()+"необходима авторизация пользователя","Ошибка",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void updateStatus(){
		if(EVatServiceSingleton.getInstance().isAuthorized()){
			if(EVatServiceSingleton.getInstance().isConnected()){
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						List<String> list = WorkingIncomingTable.selectNumbersInvoice();
						if(list != null){
							int errorCount = 0;
							int mainCount = 0;
							LoadFileProgressBar progress = new LoadFileProgressBar(list.size()).activated();
							for(int index=0;index<list.size();index++){
								AvEStatus status = EVatServiceSingleton.getInstance().getService().getStatus(list.get(index));
								boolean isValid = status.verify();
								if(isValid){
									if(WorkingIncomingTable.updateStatus(status.getStatus(), list.get(index))){
										mainCount++;
									}else{
										errorCount++;
									}
								}
								progress.setProgress(index);		
								if(progress.isCancelled()){
									JOptionPane.showMessageDialog(null, "Обновление статусов отменено","Внимание",JOptionPane.WARNING_MESSAGE);
									break;
								}
							}
							JOptionPane.showMessageDialog(null, "Обновлены статусы у "+mainCount+" ЭСЧФ"+System.lineSeparator()+
									"Не обновлено из-за ошибок "+errorCount+" ЭСЧФ","Информация",JOptionPane.INFORMATION_MESSAGE);
							progress.disactivated();
						}else{
							JOptionPane.showMessageDialog(null, "Не загружен список ЭСЧФ для обновления статуса","Ошибка",JOptionPane.ERROR_MESSAGE);
						}

						return null;
					}

				};
				worker.execute();
			}else{
				JOptionPane.showMessageDialog(null, "Сервис не подключен","Ошибка",JOptionPane.ERROR_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(null, "Авторизация не пройдена","Ошибка",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void updateStatusFast(){
		if(EVatServiceSingleton.getInstance().isAuthorized()){
			if(EVatServiceSingleton.getInstance().isConnected()){
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						List<String> list = WorkingIncomingTable.selectNotSignedNumbersInvoice();
						if(list != null){
							int errorCount = 0;
							int mainCount = 0;
							LoadFileProgressBar progress = new LoadFileProgressBar(list.size()).activated();
							for(int index=0;index<list.size();index++){
								AvEStatus status = EVatServiceSingleton.getInstance().getService().getStatus(list.get(index));
								boolean isValid = status.verify();
								if(isValid){
									if(WorkingIncomingTable.updateStatus(status.getStatus(), list.get(index))){
										mainCount++;
									}else{
										errorCount++;
									}
								}
								progress.setProgress(index);		
								if(progress.isCancelled()){
									JOptionPane.showMessageDialog(null, "Чтение файла отменено","Внимание",JOptionPane.WARNING_MESSAGE);
									break;
								}
							}
							JOptionPane.showMessageDialog(null, "Обновлены статусы у "+mainCount+" ЭСЧФ"+System.lineSeparator()+
									"Не обновлено из-за ошибок "+errorCount+" ЭСЧФ","Информация",JOptionPane.INFORMATION_MESSAGE);
							progress.disactivated();
						}else{
							JOptionPane.showMessageDialog(null, "Не загружен список ЭСЧФ для обновления статуса","Ошибка",JOptionPane.ERROR_MESSAGE);
						}

						return null;
					}

				};
				worker.execute();
			}else{
				JOptionPane.showMessageDialog(null, "Сервис не подключен","Ошибка",JOptionPane.ERROR_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(null, "Авторизация не пройдена","Ошибка",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void showInfoCertificate(){
		new ShowCertificateFrame().open();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {	
		try {
			ConnectionDB.getInstance().load();
		} catch (ClassNotFoundException | SQLException e) {
			JOptionPane.showMessageDialog(null,e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
		}
		setTitle(title);
		setResizable(false);
		setBounds(100, 100, 1065, 630);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{400, 129, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		IncomingTableModel tableModel = new  IncomingTableModel();
		table = new JTable(tableModel);
		table.setFont(new Font("Tahoma", Font.PLAIN, 11));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		JScrollPane scrollPane_table = new JScrollPane(table);
		scrollPane_table.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_table.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.insets = new Insets(0, 0, 5, 0);
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 0;
		gbc_table.gridy = 0;
		table.getTableHeader().setReorderingAllowed(false);
		for(int index = 0; index<table.getColumnCount();index++){
			table.getColumnModel().getColumn(index).setHeaderRenderer(new IncomingTableHeaderRenderer());
		}
		//УНП
		table.getColumnModel().getColumn(0).setMinWidth(60);
		table.getColumnModel().getColumn(0).setMaxWidth(60);
		table.getColumnModel().getColumn(0).setPreferredWidth(60);
		//Наименование
		table.getColumnModel().getColumn(1).setMinWidth(0);
		table.getColumnModel().getColumn(1).setMaxWidth(0);
		table.getColumnModel().getColumn(1).setPreferredWidth(0);
		//Номер ЭСЧФ
		table.getColumnModel().getColumn(2).setMinWidth(150);
		table.getColumnModel().getColumn(2).setMaxWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		//Статус ЭСЧФ
		table.getColumnModel().getColumn(3).setMinWidth(210);
		table.getColumnModel().getColumn(3).setMaxWidth(210);
		table.getColumnModel().getColumn(3).setPreferredWidth(210);
		//Дата выставления
		table.getColumnModel().getColumn(4).setMinWidth(110);
		table.getColumnModel().getColumn(4).setMaxWidth(110);
		table.getColumnModel().getColumn(4).setPreferredWidth(110);
		//Дата подписания
		table.getColumnModel().getColumn(5).setMinWidth(110);
		table.getColumnModel().getColumn(5).setMaxWidth(110);
		table.getColumnModel().getColumn(5).setPreferredWidth(110);
		//К ЭСЧФ
		table.getColumnModel().getColumn(6).setMinWidth(150);
		table.getColumnModel().getColumn(6).setMaxWidth(150);
		table.getColumnModel().getColumn(6).setPreferredWidth(150);
		//Дата аннулирования
		table.getColumnModel().getColumn(7).setMinWidth(110);
		table.getColumnModel().getColumn(7).setMaxWidth(110);
		table.getColumnModel().getColumn(7).setPreferredWidth(110);
		//Сумма НДС
		table.getColumnModel().getColumn(8).setMinWidth(70);
		table.getColumnModel().getColumn(8).setMaxWidth(70);
		table.getColumnModel().getColumn(8).setPreferredWidth(70);
		//Общая сумма
		table.getColumnModel().getColumn(9).setMinWidth(70);
		table.getColumnModel().getColumn(9).setMaxWidth(70);
		table.getColumnModel().getColumn(9).setPreferredWidth(70);
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setPreferredScrollableViewportSize(Toolkit.getDefaultToolkit().getScreenSize());
		
		/*Incoming inc = new Incoming.Builder()
				.setUnp("400002583")
				.setName("Унитарное предприятие по оказанию услуг \"Гомельское отделение БелТПП\" негосударственной некоммерческой организации \"Белорусская торгово-промышленная палата\"")
				.setNumInvoice("400002583-2016-0000006785")
				.setStatusInvoiceRu("Выставлен. Аннулирован поставщиком")
				.setDateIssue("13.10.2016")		 //yyyy-MM-dd'T'HH:mm:ss		
				.setDateSignature("13.10.2016")   //yyyy-MM-dd'T'HH:mm:ss
				.setByInvoice("400088665-2016-0000000257")
				.setDateCancellation("13.10.2016")//yyyy-MM-dd'T'HH:mm:ss
				.setTotalVat("000000.000")
				.setTotalCost("000000.000")
				.build();
		tableModel.addRow(inc);*/
		getContentPane().add(scrollPane_table, gbc_table);
		
		console = new JConsole();
		console.setFont(new Font("Courier New", Font.PLAIN, 11));
		JScrollPane scrollPane_console = new JScrollPane(console);
		scrollPane_console.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_console.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_console = new GridBagConstraints();
		gbc_console.anchor = GridBagConstraints.NORTH;
		gbc_console.fill = GridBagConstraints.BOTH;
		gbc_console.gridx = 0;
		gbc_console.gridy = 1;
		getContentPane().add(scrollPane_console, gbc_console);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("Файл");
		menuBar.add(fileMenu);
		
	    authMenuItem = new JMenuItem("Авторизация");
		authMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e){
				if(authMenuItem.isEnabled())
					autherization();
			}
		});
		authMenuItem.setEnabled(true);
		fileMenu.add(authMenuItem);
		
		infoCertMenuItem = new JMenuItem("Информация о сертификате");
		infoCertMenuItem.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent evt){
				if(infoCertMenuItem.isEnabled())
					showInfoCertificate();
			}
		});
		infoCertMenuItem.setEnabled(false);
		fileMenu.add(infoCertMenuItem);
		
		JSeparator separator = new JSeparator();
		fileMenu.add(separator);
		
		connectMenuItem = new JMenuItem("Подключить");
		connectMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(!authMenuItem.isEnabled()&&connectMenuItem.isEnabled())
					connect();
			}
		});
		connectMenuItem.setEnabled(false);
		fileMenu.add(connectMenuItem);
		
		disconnectMenuItem = new JMenuItem("Отключить");
		disconnectMenuItem.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e){
				if(!authMenuItem.isEnabled()&&!connectMenuItem.isEnabled()&&disconnectMenuItem.isEnabled()){
					disconnect();
				}
			}
		});
		disconnectMenuItem.setEnabled(false);
		fileMenu.add(disconnectMenuItem);
		
		JSeparator separatorUp = new JSeparator();
		fileMenu.add(separatorUp);
		
		JMenuItem Settings = new JMenuItem("Настройки");
		Settings.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				new SettingsFrame().open();
			}
		});
		fileMenu.add(Settings);
		
		JSeparator separatorDown = new JSeparator();
		fileMenu.add(separatorDown);
		
		JMenuItem exitMenuItem = new JMenuItem("Выход");
		exitMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String textDialog;
				if(EVatServiceSingleton.getInstance().isAuthorized()){
					textDialog = "Завершить работу программы?"+System.lineSeparator()+"Авторизованный сеанс будет закрыт";
				}else{
					textDialog = "Завершить работу программы?";
				}
				
				if(JOptionPane.showConfirmDialog(null, textDialog,"Завершение работы",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
					System.exit(1);
				}
			}
		});
		fileMenu.add(exitMenuItem);
		
		JMenu listMenu = new JMenu("Список ЭСЧФ");
		menuBar.add(listMenu);
		
		loadFileMenuItem = new JMenuItem("Загрузить из файла...");
		loadFileMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				if(loadFileMenuItem.isEnabled()){
					loadFile();
				}
			}
		});
		loadFileMenuItem.setEnabled(false);
		listMenu.add(loadFileMenuItem);
		
		JSeparator upListSeparator = new JSeparator();
		listMenu.add(upListSeparator);
		
		updateStatusMenuItem = new JMenuItem("Полное обновление статусов");
		updateStatusMenuItem.addMouseListener(new MouseAdapter(){
			@Override 
			public void mousePressed(MouseEvent evt){
				if(updateStatusMenuItem.isEnabled()){
					updateStatus();
				}
			}
		});
		updateStatusMenuItem.setEnabled(false);
		listMenu.add(updateStatusMenuItem);
		
		fastUpdateStatusMenuItem = new JMenuItem("Быстрое обновление статусов");
		fastUpdateStatusMenuItem.addMouseListener(new MouseAdapter(){
			@Override 
			public void mousePressed(MouseEvent evt){
				if(fastUpdateStatusMenuItem.isEnabled()){
					updateStatusFast();
				}
			}
		});
		fastUpdateStatusMenuItem.setEnabled(false);
		listMenu.add(fastUpdateStatusMenuItem);
		
		JSeparator downListSeparator = new JSeparator();
		listMenu.add(downListSeparator);
		
		saveFileMenuItem = new JMenuItem("Выгрузить список в TXT");
		saveFileMenuItem.addMouseListener(new MouseAdapter(){
			@Override 
			public void mousePressed(MouseEvent evt){
				if(saveFileMenuItem.isEnabled()){
					new ReportOneDayFrame().open();
				}
			}
		});
		listMenu.add(saveFileMenuItem);
	}

}
