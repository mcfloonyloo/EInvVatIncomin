package by.gomelagro.incoming.gui.frames.report;

import java.awt.Color;
import java.awt.Font;

public class ResultFont {
	public static Font getFont(){
		return new Font("Courier New", Font.PLAIN, 11);
	}
	
	public static Font getBoldFont(){
		return new Font("Courier New", Font.BOLD, 11);
	}
	
	public static Color getRed(){
		return new Color(176,0,0);
	}
	
	public static Color getGreen(){
		return new Color(0,176,0);
	}
	
	public static Color getBlack(){
		return new Color(0,0,0);
	}
}
