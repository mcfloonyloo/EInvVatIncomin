package by.gomelagro.incoming.gui.frames.enstatus;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import by.avest.edoc.client.AvEStatus;
import by.gomelagro.incoming.gui.db.WorkingIncomingTable;
import by.gomelagro.incoming.gui.progress.LoadFileProgressBar;
import by.gomelagro.incoming.service.EVatServiceSingleton;

public class UpdateEnStatus {
	public static void updateFull(){
		if(EVatServiceSingleton.getInstance().isAuthorized()){
			if(EVatServiceSingleton.getInstance().isConnected()){
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
					@Override
					protected Void doInBackground() throws Exception {
						List<String> list = WorkingIncomingTable.Lists.selectNumbersInvoice();
						if(list != null){
							int errorCount = 0;
							int mainCount = 0;
							LoadFileProgressBar progress = new LoadFileProgressBar(list.size()).activated();
							try{
								for(int index=0;index<list.size();index++){
									AvEStatus status = EVatServiceSingleton.getInstance().getService().getStatus(list.get(index));
									boolean isValid = status.verify();
									if(isValid){
										if(WorkingIncomingTable.Update.updateStatus(status.getStatus(), list.get(index))){
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
							} catch (SQLException e) {
								JOptionPane.showMessageDialog(null, e.getLocalizedMessage()+System.lineSeparator()+"Обновление статусов прервано","Ошибка",JOptionPane.ERROR_MESSAGE);
								progress.disactivated();	
							}finally{
								JOptionPane.showMessageDialog(null, "Обновлены статусы у "+mainCount+" ЭСЧФ"+System.lineSeparator()+
										"Не обновлено из-за ошибок "+errorCount+" ЭСЧФ","Информация",JOptionPane.INFORMATION_MESSAGE);
								progress.disactivated();
								progress = null;
								list = null;
							}
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
	
	public static void updateFast(){
		if(EVatServiceSingleton.getInstance().isAuthorized()){
			if(EVatServiceSingleton.getInstance().isConnected()){
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
					@Override
					protected Void doInBackground() throws Exception {
						List<String> list = WorkingIncomingTable.Lists.selectNotSignedNumbersInvoice();
						if(list != null){
							int errorCount = 0;
							int mainCount = 0;
							LoadFileProgressBar progress = new LoadFileProgressBar(list.size()).activated();
							try{
								for(int index=0;index<list.size();index++){
									AvEStatus status = EVatServiceSingleton.getInstance().getService().getStatus(list.get(index));
									boolean isValid = status.verify();
									if(isValid){
										if(WorkingIncomingTable.Update.updateStatus(status.getStatus(), list.get(index))){
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
							}catch (SQLException e) {
								JOptionPane.showMessageDialog(null, e.getLocalizedMessage()+System.lineSeparator()+"Обновление статусов отменено","Ошибка",JOptionPane.ERROR_MESSAGE);
								progress.disactivated();
							}finally{
								JOptionPane.showMessageDialog(null, "Обновлены статусы у "+mainCount+" ЭСЧФ"+System.lineSeparator()+
										"Не обновлено из-за ошибок "+errorCount+" ЭСЧФ","Информация",JOptionPane.INFORMATION_MESSAGE);
								progress.disactivated();
								progress = null;
								list = null;
							}
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
}
