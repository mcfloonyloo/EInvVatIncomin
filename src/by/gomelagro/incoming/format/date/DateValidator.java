package by.gomelagro.incoming.format.date;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateValidator {
	private static Pattern pattern;
	private static Matcher matcher;
	
	public static final String ORIGINAL_DATE_PATTERN =
			"^([0-9]{2})\\."+
			"([0-9]{2})\\."+
			"([0-9]{4})$";
	public static final String REVERSE_DATE_PATTERN = 
			"^([0-9]{4})\\."+
			"([0-9]{2})\\."+
			"([0-9]{2})$";
	public static final String ORIGINAL_DASH_DATE_PATTERN = 
			"^([0-9]{2})-"+
			"([0-9]{2})-"+
			"([0-9]{4})$";
	public static final String REVERSE_DASH_DATE_PATTERN = 
			"^([0-9]{2})-"+
			"([0-9]{2})-"+
			"([0-9]{4})$";

	public static boolean validate(final String stringPattern, String name){
		pattern = Pattern.compile(stringPattern);
		matcher = pattern.matcher(name);
		return matcher.matches();
	}
}
