package by.gomelagro.incoming.gui.frames;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.PreparedStatement;
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

import by.gomelagro.incoming.gui.console.JConsole;
import by.gomelagro.incoming.gui.db.ConnectionDB;
import by.gomelagro.incoming.gui.db.WorkingIncomingTable;
import by.gomelagro.incoming.gui.db.files.WorkingFiles;
import by.gomelagro.incoming.gui.frames.models.IncomingTableModel;
import by.gomelagro.incoming.gui.frames.table.renderer.IncomingTableHeaderRenderer;
import by.gomelagro.incoming.properties.ApplicationProperties;
import by.gomelagro.incoming.service.EVatServiceSingleton;

public class MainFrame {

	private JFrame frame;
	private JTextPane console;
	private JMenuItem authMenuItem;
	private JMenuItem connectMenuItem;
	private JMenuItem disconnectMenuItem;
	
	private final String title = "Приложение для обработки входящих ЭСЧФ v0.1";
	private static ApplicationProperties properties;
	private JTable table;
	
	static{
		properties = ApplicationProperties.Builder.getInstance().build();
		System.setProperty("by.avest.loader.shared","true");
		System.setProperty("java.library.path",properties.getLibraryPath().trim());
		System.setProperty("classpath", properties.getClassPath().trim());
	}
	
	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Methods autherization and connection to service
	 */
	private void autherization(){
		EVatServiceSingleton.getInstance().autherization(properties);
		if(EVatServiceSingleton.getInstance().isAuthorized()){
			System.out.println("Авторизация пройдена");
			authMenuItem.setEnabled(false);
			connectMenuItem.setEnabled(true);
			disconnectMenuItem.setEnabled(false);
			try {
				frame.setTitle(title+" ["+EVatServiceSingleton.getInstance().getService().getMyCertProperty("2.5.4.10")+"]");	
			} catch (IOException e) {
				System.err.println("Ошибка чтения параметра ключа: "+e.getLocalizedMessage());
			}
		}
	}
	
	private void connect(){
		if(EVatServiceSingleton.getInstance().isAuthorized()){
			EVatServiceSingleton.getInstance().connect();
			if(EVatServiceSingleton.getInstance().isConnected()){
				console.setText("");
				System.out.println("Авторизация пройдена");
				System.out.println("Подключение к сервису "+properties.getUrlService()+" выполнено успешно");
				connectMenuItem.setEnabled(false);
				disconnectMenuItem.setEnabled(true);
			}else{
				System.err.println("Ошибка подключения к сервису "+properties.getUrlService());
			}
		}
	}
	
	private void disconnect(){
		if(EVatServiceSingleton.getInstance().isAuthorized()){
			if(EVatServiceSingleton.getInstance().isConnected()){
				EVatServiceSingleton.getInstance().disconnect();
				if(!EVatServiceSingleton.getInstance().isConnected()){
					System.out.println("Отключение от сервиса "+properties.getUrlService()+" выполнено успешно");
					connectMenuItem.setEnabled(true);
					disconnectMenuItem.setEnabled(false);
				}else{
					System.err.println("Ошибка отключения от сервиса "+properties.getUrlService());
				}
			}
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			ConnectionDB.getInstance().load(properties);
		} catch (ClassNotFoundException e1) {
			System.err.println(e1.getLocalizedMessage());
		} catch (SQLException e1) {
			System.err.println(e1.getLocalizedMessage());
		}
		
		frame = new JFrame();
		frame.setTitle(title);
		frame.setResizable(false);
		frame.setBounds(100, 100, 1065, 630);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{400, 129, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
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
		frame.getContentPane().add(scrollPane_table, gbc_table);
		
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
		frame.getContentPane().add(scrollPane_console, gbc_console);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
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
		
		JMenuItem loadFileMenuItem = new JMenuItem("Загрузить из файла...");
		loadFileMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
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
								for(int index=0; index<lines.size();index++){
									String[] fields = lines.get(index).split(";");
								//	JOptionPane.showMessageDialog(null, "Всего записей - "+fields[0],"Ошибка",JOptionPane.INFORMATION_MESSAGE);
								switch(WorkingIncomingTable.getCountRecord(fields[8])){
									case -1: JOptionPane.showMessageDialog(null, "Ошибка проверки наличия записи ЭСЧФ "+fields[8]+" в таблице","Ошибка",JOptionPane.ERROR_MESSAGE); errorCount++; break;
									case  0: if(WorkingIncomingTable.insertIncoming(fields)) {notavialCount++;}else{errorCount++;} break;
									default: System.out.println("ЭСЧФ "+fields[8]+" в базе данных"); avialCount++; break;
									}
								}
								JOptionPane.showMessageDialog(null, "Добавлено "+notavialCount+" ЭСЧФ"+System.lineSeparator()+
									"Не добавлено из-за их наличия "+avialCount+" ЭСЧФ"+System.lineSeparator()+
										"Не добавлено из-за ошибок "+errorCount+" ЭСЧФ","Информация",JOptionPane.INFORMATION_MESSAGE); errorCount++;
							}else{
								JOptionPane.showMessageDialog(null, "Загружен файл неверной структуры"+System.lineSeparator()+
										"Выберите другой файл","Ошибка",JOptionPane.ERROR_MESSAGE);
							}
						}
						return null;		
					}					
				};	
				worker.execute();
			}
		});
		listMenu.add(loadFileMenuItem);
		
		JSeparator listSeparator = new JSeparator();
		listMenu.add(listSeparator);
		
		JMenuItem updateStatusMenuItem = new JMenuItem("Обновить статусы");
		listMenu.add(updateStatusMenuItem);
	}

	public void insert(String name) {
        String sql = "INSERT INTO TEST(NUMBERINVOICE) VALUES(?)";
 
        try(PreparedStatement pstmt = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public void update(String name, String id) {
        String sql = "UPDATE TEST SET STATUS = ? "
                
                + "WHERE NUMBERINVOICE = ?";
 
        try (PreparedStatement pstmt = ConnectionDB.getInstance().getConnection().prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, name);            
            pstmt.setString(2, id);
            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
