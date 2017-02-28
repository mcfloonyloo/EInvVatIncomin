package by.gomelagro.incoming.format.date;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateValidator {
	private static Pattern pattern;
	private static Matcher matcher;
	
	private static final String DATE_PATTERN =
			"^([0-9]{2})\\."+
			"([0-9]{2})\\."+
			"([0-9]{4})$";

	public static boolean validate(final String name){
		pattern = Pattern.compile(DATE_PATTERN);
		matcher = pattern.matcher(name);
		return matcher.matches();
	}
}
