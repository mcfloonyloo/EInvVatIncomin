package by.gomelagro.incoming.gui.console;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import by.gomelagro.incoming.gui.console.JConsole.ConsoleOutputStream;

/**
 * 
 * @author dok http://www.dokwork.ru/2011/08/jconsole.html
 * @version 1.0
 *
 */

public class JConsole extends JTextPane {

	private static final long serialVersionUID = 1L;
	
	private ConsoleOutputStream err;
	private ConsoleOutputStream out;
	
	public class ConsoleOutputStream extends OutputStream {
		
		private PrintStream old;
		private Color color;
		public void setColor(Color color){this.color = color;}
		public Color getColor(){return this.color;}
		
		@Override
		public void write(int b){
			insertText("" + (char)b, color);

		}

		@Override
		public void write(byte[] b, int off, int len) throws IOException{
			insertText(new String(b, off, len),color);
		}
		
		@Override
		public void write(byte[] b){
			insertText(new String(b), color);
		}
		
		public ConsoleOutputStream(PrintStream old, Color color){
			if(old == null){
				throw new NullPointerException("—сылка на замен€емый поток не указана");
			}
			if(color == null){
				throw new NullPointerException("—сылка на цвет не указана");
			}
			this.color = color;
			this.old = old;
		}
	}
	
	public void setColorOut(Color colorOut){this.out.setColor(colorOut);}
	public Color getColorOut(){return out.getColor();}
	
	public void setColorErr(Color colorErr){this.err.setColor(colorErr);}
	public Color getColorErr(){return err.getColor();}
	
	public JConsole(){
		setEditable(false);
		err = new ConsoleOutputStream(System.err, Color.red);
		out = new ConsoleOutputStream(System.out, Color.black);
		System.setErr(new PrintStream(err));
		System.setOut(new PrintStream(out));
	}
	
	private void insertText(final String text, final Color color){
		SimpleAttributeSet att = new SimpleAttributeSet();
		StyleConstants.setForeground(att, color);
		try{
			int offset = getStyledDocument().getLength();
			getStyledDocument().insertString(offset, text, att);
			this.setCaretPosition(this.getDocument().getLength());
		}catch(BadLocationException e){
			System.err.println(e.getMessage());
		}
		
	}

}