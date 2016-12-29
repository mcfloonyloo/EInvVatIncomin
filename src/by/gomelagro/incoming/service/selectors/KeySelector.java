package by.gomelagro.incoming.service.selectors;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import by.avest.edoc.client.PersonalKeyManager;
import by.avest.edoc.tool.ToolException;

/**
 * Класс для выбора контейнера личного ключа из запроса пароля к ключу
 * 
 */

public class KeySelector extends PersonalKeyManager {

	public KeySelector(KeyStore ks) {
		super(ks);
	}
	
	private boolean closed = false;
	public boolean isClosed(){return this.closed;}
	private void setClosed(boolean closed){this.closed = closed;}

	public KeySelector() throws ToolException,UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException,
	CertificateException, IOException {
		super(getDefaultKS());
	}

	private static KeyStore getDefaultKS() throws ToolException, UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException,
	CertificateException, IOException {
		KeyStore ks= null;
		ks = KeyStore.getInstance("AvPersonal");
		ks.load(null, null);
		return ks;
	}

	@Override
	public char[] promptPassword(String alias){
		// command line interface could be replaced with GUI
		String request = alias;
		char[] password = null;
		try {
			password = promptPasswordInternal(request);
		} catch (ToolException e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
		}
		return password;
	}

	private char[] promptPasswordInternal(String request) throws ToolException{
		char[] answer = promptForPassword(request);
		// validate entered password
		if(answer == null){
			setClosed(true);
			throw new ToolException("Авторизация пользователя отменена");
		}
		if ((answer.length >0) && (answer.length >= 8)) {
			return answer;
		} else {
			JOptionPane.showConfirmDialog(null, "Минимальная длина пароля 8 символов, повторите ввод пароля","Внимание", JOptionPane.WARNING_MESSAGE);
			return promptPasswordInternal(request);
		}
	}

	private char[] promptForPassword(String request) {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Введите пароль:");
		JPasswordField pass = new JPasswordField(65);
		panel.add(label);
		panel.add(pass);
		String[] options = new String[]{"OK", "Cancel"};
		char[] line = null;
		int option = JOptionPane.showOptionDialog(null, panel, "Запрос пароля для ключа ["+request+"]",
                JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
		if(option == 0) // pressing OK button
		{
		    line = pass.getPassword();
		}		
		return line;
	}

	@Override
	public String chooseAlias(String[] aliases) throws IOException {
		List<String> list = new ArrayList<String>();
		for(int index=0;index<aliases.length;index++){
			list.add(aliases[index]);
		}
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Введите пароль:");
		JComboBox<Object> keyControl = new JComboBox<Object>(list.toArray());	
		panel.add(label);
		panel.add(keyControl);
		String[] options = new String[]{"OK", "Cancel"};
		int option = JOptionPane.showOptionDialog(null, panel, "Запрос ключа для авторизации в системе",
                JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
		if(option == 0) // pressing OK button
		{
		    return aliases[keyControl.getSelectedIndex()];
		}else{
			try {
				setClosed(true);
				System.err.println("Ошибка: key is null");
				throw new ToolException("Авторизация отменена");
			} catch (ToolException e) { 
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}
		
		
		/*
		
		JComboBox<Object> keyControl = new JComboBox<Object>(list.toArray());	
		keyControl.setSelectedIndex(-1);
		JOptionPane.showInputDialog(null, keyControl, "Запрос ключа для авторизации в системе",JOptionPane.QUESTION_MESSAGE);
		int index = keyControl.getSelectedIndex();
		if(index == -1){
			try {
				throw new ToolException("Авторизация отменена");
			} catch (ToolException e) { 
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}
		if ((index > -1) && (index < aliases.length)) {
			return aliases[index];
		}else
			return null;*/
	}
}