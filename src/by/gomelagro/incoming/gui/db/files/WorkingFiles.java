package by.gomelagro.incoming.gui.db.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorkingFiles {
	public static List<String> readCSVFile(File file) throws IOException{
		List<String> lines = null;
		try(BufferedReader reader = new BufferedReader(new FileReader(file))){
			lines = new ArrayList<String>();
			String line = "";
			line = reader.readLine();
			if(line.trim().length()>0){
				reader.close();
				return null;
			}
			line = reader.readLine();
			//if(line.trim() == "INVOICESH;Исходный СХ"){
			if(line.trim() == "INVOICE;Исходный"){
				reader.close();
				return null;
			}
			line = reader.readLine();
			//if(!line.contains("\"УНП поставщика\";Имнс поставщика;")){
			if(!line.contains("\"Код страны поставщика\";УНП поставщика;")){
				reader.close();
				return null;	
			}		
			while(reader.ready()){
				line = reader.readLine();
				if(!line.trim().isEmpty()){
					lines.add(line);
				}
			}
		}
		return lines;
	}
	
	public static boolean isFile(String filePath){
		File file = new File(filePath);
		if(file.exists() && file.isFile()){
			return true;
		}
		else{
			return false;
		}
	}
}
