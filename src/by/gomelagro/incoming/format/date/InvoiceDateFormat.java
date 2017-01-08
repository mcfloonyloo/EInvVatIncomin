package by.gomelagro.incoming.format.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class InvoiceDateFormat {

	private static final SimpleDateFormat sdfFull;
	private static final SimpleDateFormat sdfSmallDot;
	private static final SimpleDateFormat sdfSmallDash;
	private static final SimpleDateFormat sdfReverseSmallDash;
	
	static {
		sdfFull = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		sdfFull.setTimeZone(TimeZone.getTimeZone("Europe/Minsk"));
		
		sdfSmallDot = new SimpleDateFormat("dd.MM.yyyy");
		sdfSmallDot.setTimeZone(TimeZone.getTimeZone("Europe/Minsk"));
		
		sdfSmallDash = new SimpleDateFormat("dd-MM-yyyy");
		sdfSmallDash.setTimeZone(TimeZone.getTimeZone("Europe/Minsk"));
		
		sdfReverseSmallDash = new SimpleDateFormat("yyyy-MM-dd");
		sdfSmallDash.setTimeZone(TimeZone.getTimeZone("Europe/Minsk"));
	}
	
	public static Date string2DateFull(String date) throws ParseException{
		return sdfFull.parse(date);
	}
	
	public static Date string2DateSmallDot(String date) throws ParseException{
		return sdfSmallDot.parse(date);
	}
		
	public static Date string2DateSmallDash(String date) throws ParseException{
		return sdfSmallDash.parse(date);
	}
	
	public static Date string2DateReverseSmallDash(String date) throws ParseException{
		return sdfReverseSmallDash.parse(date);
	}
	
	
	public static String dateFull2String(Date date) throws ParseException{
		return sdfFull.format(date);
	}
	
	public static String dateSmallDot2String(Date date) throws ParseException{
		return sdfSmallDot.format(date);
	}
	
	public static String dateReverseSmallDash2String(Date date) throws ParseException{
		return sdfReverseSmallDash.format(date);
	}
	
	public static String dateSmallDash2String(Date date) throws ParseException{
		return sdfSmallDash.format(date);
	}
	
}
