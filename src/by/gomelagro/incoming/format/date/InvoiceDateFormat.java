package by.gomelagro.incoming.format.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class InvoiceDateFormat {

	private static final SimpleDateFormat sdf;
	private static final SimpleDateFormat strdf;
	
	static {
		sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("Europe/Minsk"));
		
		strdf = new SimpleDateFormat("dd.MM.yyyy");
		strdf.setTimeZone(TimeZone.getTimeZone("Europe/Minsk"));
	}
	
	public static Date string2Date(String date) throws ParseException {
		return sdf.parse(date);
	}

	public static String string2String(String date) throws ParseException{ 
		return sdf.format(strdf.parse(date));
	}
	
	public static String date2String(Date date) {
		return sdf.format(date);
	}
}
