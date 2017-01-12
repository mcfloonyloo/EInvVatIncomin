package by.gomelagro.incoming.gui.db;

import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import by.gomelagro.incoming.format.date.InvoiceDateFormat;
import by.gomelagro.incoming.gui.db.files.data.UnloadedInvoice;
import by.gomelagro.incoming.gui.frames.report.ResultFont;
import by.gomelagro.incoming.status.Status;

public class WorkingIncomingTable {
	
	//количество всех ЭСЧФ в таблице
	public static int getCountAll(){
		String sql = "SELECT COUNT(*) AS COUNT FROM INCOMING WHERE STATUSINVOICEEN IS NOT NULL";
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
	
	//количество определенной ЭСЧФ в таблице 
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
	
	//количество всех ЭСЧФ в таблице за год
	public static int getCountAllInYear(String date){
		String sql = "SELECT COUNT(*) AS COUNT FROM INCOMING WHERE STATUSINVOICEEN IS NOT NULL AND strftime('%Y',DATECOMMISSION) = '"+date+"'";
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
	
	//количество подписанных ЭСЧФ в таблице
	public static int getCountCompleted(String date){
		String sql = "SELECT COUNT(*) AS COUNT FROM INCOMING WHERE STATUSINVOICEEN IS NOT NULL AND STATUSINVOICEEN = 'COMPLETED_SIGNED' AND strftime('%Y',DATECOMMISSION) = '"+date+"'";
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
	
	
	//количество неподписанных ЭСЧФ в таблице
	public static int getCountNoCompleted(String date){
		String sql = "SELECT COUNT(*) AS COUNT FROM INCOMING WHERE STATUSINVOICEEN IS NOT NULL AND STATUSINVOICEEN = 'COMPLETED' AND strftime('%Y',DATECOMMISSION) = '"+date+"'";
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
	
	//количество отменённых ЭСЧФ
	public static int getCountCancelled(String date){
		String sql = "SELECT COUNT(*) AS COUNT FROM INCOMING WHERE STATUSINVOICEEN IS NOT NULL AND (STATUSINVOICEEN = 'CANCELLED' OR STATUSINVOICEEN = 'ON_AGREEMENT_CANCEL') AND strftime('%Y',DATECOMMISSION) = '"+date+"'";
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
	
	//количество ЭСЧФ неопределенного статуса в таблице 
	public static int getCountUndetermined(String date){
		String sql = "SELECT COUNT(*) AS COUNT FROM INCOMING WHERE STATUSINVOICEEN IS NOT NULL AND (STATUSINVOICEEN = 'ON_AGREEMENT' OR "
				+ "STATUSINVOICEEN = 'IN_PROGRESS' OR STATUSINVOICEEN = 'NOT_FOUND' OR STATUSINVOICEEN = 'ERROR') AND strftime('%Y',DATECOMMISSION) = '"+date+"'";
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
		String sql = "INSERT INTO INCOMING(UNP, CODECOUNTRY, NAME, NUMBERINVOICE, TYPEINVOICE, STATUSINVOICERU, STATUSINVOICEEN, DATEISSUE, DATECOMMISSION, "
				+ "DATESIGNATURE, BYINVOICE, DATECANCELLATION, TOTALEXCISE, TOTALVAT, TOTALALL, TOTALCOST) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
			statement.setString(1,  fields[0]);
			statement.setString(2,  fields[1]);
			statement.setString(3,  fields[2]);
			statement.setString(4,  fields[8]);
			statement.setString(5,  fields[9]);
			statement.setString(6,  fields[10]);
			statement.setString(7, Status.valueRuOf(fields[10]));
			if(fields[11].trim().length() > 0){
				statement.setString(8,  InvoiceDateFormat.dateReverseSmallDash2String(InvoiceDateFormat.string2DateSmallDash(fields[11])));
			}else{
				statement.setString(8,  fields[11]);
			}
			if(fields[12].trim().length() > 0){
				statement.setString(9,  InvoiceDateFormat.dateReverseSmallDash2String(InvoiceDateFormat.string2DateSmallDash(fields[12])));
			}else{
				statement.setString(9,  fields[12]);
			}
			if(fields[13].trim().length() > 0){
				statement.setString(10,  InvoiceDateFormat.dateReverseSmallDash2String(InvoiceDateFormat.string2DateSmallDash(fields[13])));
			}else{
				statement.setString(10,  fields[13]);
			}
			statement.setString(11, fields[14]);
			if(fields[15].trim().length() > 0){
				statement.setString(12,  InvoiceDateFormat.dateReverseSmallDash2String(InvoiceDateFormat.string2DateSmallDash(fields[15])));
			}else{
				statement.setString(12,  fields[15]);
			}
			statement.setString(13, fields[16].replace(",", "."));
			statement.setString(14, fields[17].replace(",", "."));
			statement.setString(15, fields[18].replace(",", "."));
			statement.setString(16, String.format("%.3f",(Float.parseFloat(fields[18].replace(",", "."))-Float.parseFloat(fields[17].replace(",", "."))-Float.parseFloat(fields[16].replace(",", ".")))));
			statement.executeUpdate();
			return true;
		} catch (SQLException | ParseException e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	//список всех ЭСЧФ для обновления
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
	
	//список неподписанных ЭСЧФ для обновления
	public static List<String> selectNotSignedNumbersInvoice(){
		List<String> list = new ArrayList<String>();
		String sql = "SELECT NUMBERINVOICE FROM INCOMING WHERE STATUSINVOICEEN IS NOT NULL AND (STATUSINVOICEEN = 'COMPLETED' "
				+ "OR STATUSINVOICEEN = 'ON_AGREEMENT' OR STATUSINVOICEEN = 'IN_PROGRESS' OR STATUSINVOICEEN = 'NOT_FOUND')";
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
	
	//список ЭСЧФ для отчета
	public static List<UnloadedInvoice> selectSignedNumbersInvoice(){
		List<UnloadedInvoice> list = new ArrayList<UnloadedInvoice>();
		String sql = "SELECT UNP, DATECOMMISSION, NUMBERINVOICE, STATUSINVOICEEN, TOTALCOST, TOTALVAT, TOTALALL FROM INCOMING WHERE STATUSINVOICEEN IS NOT NULL";
		try(Statement statement = ConnectionDB.getInstance().getConnection().createStatement()){
			list.clear();
			ResultSet set = statement.executeQuery(sql);
			String statusRu = "";
			while(set.next()){
				if(set.getString("STATUSINVOICEEN").trim().equals("COMPLETED_SIGNED")){
					statusRu = "Подписан";
				}else{
					statusRu = "Не подписан";
				}
				list.add(new UnloadedInvoice.Builder()
						.setUnp(set.getString("UNP").trim())
						.setDateCommission(InvoiceDateFormat.dateSmallDot2String(InvoiceDateFormat.string2DateSmallDash(set.getString("DATECOMMISSION").trim())))
						.setNumberInvoice(set.getString("NUMBERINVOICE").trim())
						.setStatusInvoiceRu(statusRu)
						.setTotalCost(set.getString("TOTALCOST").trim())
						.setTotalVat(set.getString("TOTALVAT").trim())
						.setTotalAll(set.getString("TOTALALL").trim())
						.build());
			}
			return list;
		} catch (SQLException | ParseException e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
			return null;			
		}
	}
		
	//список ЭСЧФ для отчета на дату
	public static List<UnloadedInvoice> selectSignedNumbersInvoiceAtDate(Date date, Comparator<UnloadedInvoice> comparator, String status){
		List<UnloadedInvoice> list = new ArrayList<UnloadedInvoice>();
		String sql = "SELECT UNP, DATECOMMISSION, NUMBERINVOICE, STATUSINVOICEEN, TOTALCOST, TOTALVAT, TOTALALL FROM INCOMING WHERE STATUSINVOICEEN IS NOT NULL AND DATECOMMISSION = '"+date.toString()+"' "+status;
		try(Statement statement = ConnectionDB.getInstance().getConnection().createStatement()){
			list.clear();
			ResultSet set = statement.executeQuery(sql);
			String statusRu = "";
			Color color = Color.BLACK;
			while(set.next()){
				if(set.getString("STATUSINVOICEEN").trim().equals("COMPLETED_SIGNED")){
					statusRu = "Подписан";
					color = ResultFont.getGreen();
				}else{
					statusRu = "Не подписан";
					color = ResultFont.getRed();
				}
				list.add(new UnloadedInvoice.Builder()
						.setUnp(set.getString("UNP").trim())
						.setDateCommission(InvoiceDateFormat.dateSmallDot2String(InvoiceDateFormat.string2DateReverseSmallDash(set.getString("DATECOMMISSION").trim())))
						.setNumberInvoice(set.getString("NUMBERINVOICE").trim())
						.setStatusInvoiceRu(statusRu)
						.setTotalCost(set.getString("TOTALCOST").trim())
						.setTotalVat(set.getString("TOTALVAT").trim())
						.setTotalAll(set.getString("TOTALALL").trim())
						.setColor(color)
						.build());
			}
			Collections.sort(list, comparator);
			return list;
		} catch (SQLException | ParseException e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
			return null;			
		}
	}
	
	//список ЭСЧФ для отчета на период
	public static List<UnloadedInvoice> selectSignedNumbersInvoiceAtBetween(Date leftDate, Date rightDate, Comparator<UnloadedInvoice> comparator, String status){
		List<UnloadedInvoice> list = new ArrayList<UnloadedInvoice>();
		String sql = "SELECT UNP, DATECOMMISSION, NUMBERINVOICE, STATUSINVOICEEN, TOTALCOST, TOTALVAT, TOTALALL FROM INCOMING WHERE STATUSINVOICEEN IS NOT NULL AND DATECOMMISSION BETWEEN '"+leftDate.toString()+"' AND '"+rightDate.toString()+"' "+status;
		try(Statement statement = ConnectionDB.getInstance().getConnection().createStatement()){
			list.clear();
			ResultSet set = statement.executeQuery(sql);
			String statusRu = "";
			Color color = Color.BLACK;
			while(set.next()){
				if(set.getString("STATUSINVOICEEN").trim().equals("COMPLETED_SIGNED")){
					statusRu = "Подписан";
					color = ResultFont.getGreen();
				}else{
					statusRu = "Не подписан";
					color = ResultFont.getRed();
				}
				list.add(new UnloadedInvoice.Builder()
						.setUnp(set.getString("UNP").trim())
						.setDateCommission(InvoiceDateFormat.dateSmallDot2String(InvoiceDateFormat.string2DateReverseSmallDash(set.getString("DATECOMMISSION").trim())))
						.setNumberInvoice(set.getString("NUMBERINVOICE").trim())
						.setStatusInvoiceRu(statusRu)
						.setTotalCost(set.getString("TOTALCOST").trim())
						.setTotalVat(set.getString("TOTALVAT").trim())
						.setTotalAll(set.getString("TOTALALL").trim())
						.setColor(color)
						.build());
			}
			Collections.sort(list, comparator);
			return list;
		} catch (SQLException | ParseException e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
			return null;			
		}
	}
	
	public static List<String> selectYearInvoice(){
		List<String> list = new ArrayList<String>();
		String sql = "SELECT strftime('%Y',DATECOMMISSION) as cYEAR FROM INCOMING GROUP BY cYEAR ORDER BY cYEAR";
		try(Statement statement = ConnectionDB.getInstance().getConnection().createStatement()){
			ResultSet set = statement.executeQuery(sql);
			while(set.next()){
				list.add(set.getString("cYEAR"));
			}
			return list;
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
	}

	//обновление статусов ЭСЧФ
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
	
	public static boolean updateStatusFromFile(String ruStatus, String number){
		String sql = "UPDATE INCOMING SET STATUSINVOICEEN = ? WHERE NUMBERINVOICE = ?";
		try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
			statement.setString(1, Status.valueRuOf(ruStatus.trim()));
			statement.setString(2, number);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
			return false;			
		}
	}
	
}
