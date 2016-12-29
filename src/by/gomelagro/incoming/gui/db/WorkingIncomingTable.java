package by.gomelagro.incoming.gui.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import by.gomelagro.incoming.status.Status;

public class WorkingIncomingTable {
	
	public static int getCountRecord(String number){
		String sql = "SELECT COUNT(*) AS COUNT FROM INCOMING WHERE NUMBERINVOICE = '"+number+"'";
		try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
			int count = -1;
			ResultSet set = statement.executeQuery();
			while(set.next()){
				count = set.getInt(1);
			}
			return count;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
			return -1;
		}
	}	
	
	public static int getCountAll(){
		String sql = "SELECT COUNT(*) AS COUNT FROM INCOMING";
		try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
			int count = -1;
			ResultSet set = statement.executeQuery();
			while(set.next()){
				count = set.getInt(1);
			}
			return count;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
			return -1;
		}
	}
	
	public static boolean insertIncoming(String[] fields){
		String sql = "INSERT INTO INCOMING(UNP, CODECOUNTRY, NAME, NUMBERINVOICE, TYPEINVOICE, STATUSINVOICERU, DATEISSUE, DATECOMMISSION, "
				+ "DATESIGNATURE, BYINVOICE, DATECANCELLATION, TOTALEXCISE, TOTALVAT, TOTALALL, TOTALCOST) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
			statement.setString(1, fields[0]);
			statement.setString(2, fields[1]);
			statement.setString(3, fields[2]);
			statement.setString(4, fields[8]);
			statement.setString(5, fields[9]);
			statement.setString(6, fields[10]);
			statement.setString(7, fields[11]);
			statement.setString(8, fields[12]);
			statement.setString(9, fields[13]);
			statement.setString(10, fields[14]);
			statement.setString(11, fields[15]);
			statement.setString(12, fields[16].replace(",", "."));
			statement.setString(13, fields[17].replace(",", "."));
			statement.setString(14, fields[18].replace(",", "."));
			statement.setString(15, String.format("%1f",(Float.parseFloat(fields[18].replace(",", "."))-Float.parseFloat(fields[17].replace(",", "."))-Float.parseFloat(fields[16].replace(",", ".")))));
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	public static List<String> selectNumbersInvoice(){
		List<String> list = new ArrayList<String>();
		String sql = "SELECT NUMBERINVOICE FROM INCOMING";
		try(Statement statement = ConnectionDB.getInstance().getConnection().createStatement()){
			list.clear();
			ResultSet set = statement.executeQuery(sql);
			while(set.next()){
				list.add(set.getString(1).trim());
			}
			return list;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
			return null;			
		}
	}
	
	public static boolean updateStatus(String status, String number){
		String sql = "UPDATE INCOMING SET STATUSINVOICEEN = ?, STATUSINVOICERU = ? WHERE NUMBERINVOICE = ?";
		try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
			statement.setString(1, Status.valueOf(status.trim()).getEnValue());
			statement.setString(2, Status.valueOf(status.trim()).getRuValue());
			statement.setString(3, number);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
			return false;			
		}
	}
	
}
