package by.gomelagro.incoming.gui.db.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorkingFiles {
	public static List<String> readCSVFile(File file) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(file));
		List<String> lines = new ArrayList<String>();
		String line = "";
		line = reader.readLine()+reader.readLine();
		if(line.trim() == "INVOICESH;Исходный СХ"){
			reader.close();
			return null;
		}		
		while(reader.ready()){
			line = reader.readLine();
			if(!line.trim().isEmpty())
				if((line.toCharArray()[0] != 'I')&&(line.toCharArray()[0] != '"'))
					lines.add(line);
		}
		reader.close();
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
