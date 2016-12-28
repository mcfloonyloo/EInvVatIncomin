package by.gomelagro.incoming.base;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import by.gomelagro.incoming.gui.frames.MainFrame;

public class BaseForm {
		
		static{
			try {
				javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e) {
				System.err.println(e.getMessage());
			}
		}

		@SuppressWarnings("unused")
		private static  MainFrame main;
		
		public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {					
						main = new MainFrame();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}

}
