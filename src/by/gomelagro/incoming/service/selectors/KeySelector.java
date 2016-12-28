package by.gomelagro.incoming.service.selectors;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.swing.JOptionPane;

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
		String request = "Введите пароль для ключа \"" + alias + "\": ";
		char[] password = null;
		try {
			password = promptPasswordInternal(request);
		} catch (ToolException e) {
			System.err.println(e.getLocalizedMessage());
		}
		return password;
	}

	private char[] promptPasswordInternal(String request) throws ToolException{
		char[] answer = promptForPassword(request);
		// validate entered password
		if(answer == null){
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
		String line = JOptionPane.showInputDialog(null, request, "Ввод пароля", JOptionPane.NO_OPTION);
		return line == null ? null : line.toCharArray();
	}

	@Override
	public String chooseAlias(String[] aliases) throws IOException {
		return aliases[promptAliasIndex(aliases)];
	}

	private int promptAliasIndex(String[] aliases) throws IOException {
		int numberAlias = -1;
		try {
			numberAlias = Integer.parseInt(promptAliasIndexInternal(aliases)) - 1;
		} catch (NumberFormatException | ToolException e) {
			e.printStackTrace();
		}
		return numberAlias;
	}
	
	public String getAliases(String[] aliases){
		String lines = "Список ключей:\n";
		for (int i = 0; i < aliases.length; i++) {
			System.out.println((i + 1) + ": " + aliases[i]);
			lines = lines + (i+1) + ": "+aliases[i]+"\n";
		}
		return lines;
	}

	private String promptAliasIndexInternal(String[] aliases) throws ToolException {
		//BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		//String line = bufferedReader.readLine();
		String line = JOptionPane.showInputDialog(null, getAliases(aliases) + "Введите номер ключа:", "Выбор ключа", JOptionPane.CANCEL_OPTION);
		if (isAliasValid(line, aliases)) {
			return line;
		} else {
			if(line == null){
				throw new ToolException("Cancel");
			}
			if (line.isEmpty()) {
				JOptionPane.showConfirmDialog(null, "Не выбран номер ключа. Введите номер ключа от 1 до " + aliases.length + ": ","Внимание", JOptionPane.WARNING_MESSAGE);

			} else {
				JOptionPane.showConfirmDialog(null, "Неверный номер \"" + (line) + "\". Повторно введите номер ключа от 1 до "
						+ aliases.length + ": ","Внимание", JOptionPane.WARNING_MESSAGE);
			}
			return promptAliasIndexInternal(aliases);
		}
	}

	private boolean isAliasValid(String line, String[] aliases) {
		try {
			int index = Integer.parseInt(line);
			return (index > 0) && (index <= aliases.length);
		} catch (NumberFormatException e) {
			return false;
		}
	}

}