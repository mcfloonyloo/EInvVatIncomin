package by.gomelagro.incoming.gui.db;

import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JOptionPane;

import by.gomelagro.incoming.base.ApplicationConstants;
import by.gomelagro.incoming.format.date.InvoiceDateFormat;
import by.gomelagro.incoming.gui.db.files.data.UnloadedInvoice;
import by.gomelagro.incoming.gui.frames.list.MonthYearItem;
import by.gomelagro.incoming.gui.frames.report.ResultFont;
import by.gomelagro.incoming.status.Status;

public class WorkingIncomingTable {
	
	public static class Count{
		
		//количество всех ЭСЧФ в таблице
		public static int getCountAll(){
			
			String sql = "SELECT COUNT(*) AS COUNT"
					    	+" FROM "+ApplicationConstants.DB_TABLENAME
					    	+" WHERE STATUSINVOICEEN IS NOT NULL";
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				int count = -1;
				ResultSet set = statement.executeQuery();
				while(set.next()){count = set.getInt(1);}
				return count;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}
		
		//количество определенной ЭСЧФ в таблице 
		public static int getCountRecord(String number){
			String sql = "SELECT COUNT(*) AS COUNT"
					   		+ " FROM "+ApplicationConstants.DB_TABLENAME+""
					   		+ " WHERE NUMBERINVOICE = '"+number+"'";
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				int count = -1;
				ResultSet set = statement.executeQuery();
				while(set.next()){count = set.getInt(1);}
				return count;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}	
		
		//количество всех ЭСЧФ в таблице за год
		public static int getCountAllInYear(String year){
			String sql = "SELECT COUNT(*) AS COUNT"
					   		+ " FROM "+ApplicationConstants.DB_TABLENAME+""
					   		+ " WHERE STATUSINVOICEEN IS NOT NULL"
					   			+ " AND strftime('%Y',DATECOMMISSION) = '"+year+"'";
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				int count = -1;
				ResultSet set = statement.executeQuery();
				while(set.next()){count = set.getInt(1);}
				return count;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}
		
		//количество подписанных ЭСЧФ в таблице за год
		public static int getCountCompletedInYear(String year){
			String sql = "SELECT COUNT(*) AS COUNT"
							+ " FROM "+ApplicationConstants.DB_TABLENAME
							+ " WHERE STATUSINVOICEEN IS NOT NULL"
								+ " AND STATUSINVOICEEN = 'COMPLETED_SIGNED'"
								+ " AND strftime('%Y',DATECOMMISSION) = '"+year+"'";
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				int count = -1;
				ResultSet set = statement.executeQuery();
				while(set.next()){count = set.getInt(1);}
				return count;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}
		
		//количество неподписанных ЭСЧФ в таблице за год
		public static int getCountUncompletedInYear(String year){
			String sql = "SELECT COUNT(*) AS COUNT"
							+ " FROM "+ApplicationConstants.DB_TABLENAME
							+ " WHERE STATUSINVOICEEN IS NOT NULL"
								+ " AND STATUSINVOICEEN = 'COMPLETED'"
								+ " AND strftime('%Y',DATECOMMISSION) = '"+year+"'";
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				int count = -1;
				ResultSet set = statement.executeQuery();
				while(set.next()){count = set.getInt(1);}
				return count;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}
		
		//количество отменённых ЭСЧФ за год
		public static int getCountCancelledInYear(String year){
			String sql = "SELECT COUNT(*) AS COUNT"
							+ " FROM "+ApplicationConstants.DB_TABLENAME
							+ " WHERE STATUSINVOICEEN IS NOT NULL"
								+ " AND (STATUSINVOICEEN = 'CANCELLED'"
								+ " OR STATUSINVOICEEN = 'ON_AGREEMENT_CANCEL')"
								+ " AND strftime('%Y',DATECOMMISSION) = '"+year+"'";
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				int count = -1;
				ResultSet set = statement.executeQuery();
				while(set.next()){count = set.getInt(1);}
				return count;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}
		
		//количество ЭСЧФ неопределенного статуса в таблице  за год
		public static int getCountUndeterminedInYear(String year){
			String sql = "SELECT COUNT(*) AS COUNT"
							+ " FROM "+ApplicationConstants.DB_TABLENAME
							+ " WHERE STATUSINVOICEEN IS NOT NULL AND (STATUSINVOICEEN = 'ON_AGREEMENT'"
								+ " OR STATUSINVOICEEN = 'IN_PROGRESS'"
								+ " OR STATUSINVOICEEN = 'NOT_FOUND'"
								+ " OR STATUSINVOICEEN = 'ERROR')"
								+ " AND strftime('%Y',DATECOMMISSION) = '"+year+"'";
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				int count = -1;
				ResultSet set = statement.executeQuery();
				while(set.next()){count = set.getInt(1);}
				return count;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}
		

		//
		//количество подписанных ЭСЧФ в таблице за месяц года
		public static int getCountCompletedInMonthYear(String month, String year){
			String sql = "SELECT COUNT(*) AS COUNT"
							+ " FROM "+ApplicationConstants.DB_TABLENAME
							+ " WHERE STATUSINVOICEEN IS NOT NULL"
								+ " AND STATUSINVOICEEN = 'COMPLETED_SIGNED'"
								+ " AND strftime('%Y',DATECOMMISSION) = '"+year+"'"
								+ " AND strftime('%m',DATECOMMISSION) = '"+month+"'";
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				int count = -1;
				ResultSet set = statement.executeQuery();
				while(set.next()){count = set.getInt(1);}
				return count;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}
		
		
		//количество неподписанных ЭСЧФ в таблице за месяц года
		public static int getCountUncompletedInMonthYear(String month, String year){
			String sql = "SELECT COUNT(*) AS COUNT"
							+ " FROM "+ApplicationConstants.DB_TABLENAME
							+ " WHERE STATUSINVOICEEN IS NOT NULL"
								+ " AND STATUSINVOICEEN = 'COMPLETED'"
								+ " AND strftime('%Y',DATECOMMISSION) = '"+year+"'"
								+ " AND strftime('%m',DATECOMMISSION) = '"+month+"'";
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				int count = -1;
				ResultSet set = statement.executeQuery();
				while(set.next()){count = set.getInt(1);}
				return count;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}
		
		//количество отменённых ЭСЧФ за месяц года
		public static int getCountCancelledInMonthYear(String month, String year){
			String sql = "SELECT COUNT(*) AS COUNT"
							+ " FROM "+ApplicationConstants.DB_TABLENAME
							+ " WHERE STATUSINVOICEEN IS NOT NULL"
								+ " AND (STATUSINVOICEEN = 'CANCELLED'"
								+ " OR STATUSINVOICEEN = 'ON_AGREEMENT_CANCEL')"
								+ " AND strftime('%Y',DATECOMMISSION) = '"+year+"'"
								+ " AND strftime('%m',DATECOMMISSION) = '"+month+"'";
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				int count = -1;
				ResultSet set = statement.executeQuery();
				while(set.next()){count = set.getInt(1);}
					return count;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}
		
		//количество ЭСЧФ неопределенного статуса в таблице за месяц года
		public static int getCountUndeterminedInMonthYear(String month, String year){
			String sql = "SELECT COUNT(*) AS COUNT"
							+ " FROM "+ApplicationConstants.DB_TABLENAME
							+ " WHERE STATUSINVOICEEN IS NOT NULL"
								+ " AND (STATUSINVOICEEN = 'ON_AGREEMENT'"
								+ " OR STATUSINVOICEEN = 'IN_PROGRESS'"
								+ " OR STATUSINVOICEEN = 'NOT_FOUND'"
								+ " OR STATUSINVOICEEN = 'ERROR')"
								+ " AND strftime('%Y',DATECOMMISSION) = '"+year+"'"
								+ " AND strftime('%m',DATECOMMISSION) = '"+month+"'";
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				int count = -1;
				ResultSet set = statement.executeQuery();
				while(set.next()){count = set.getInt(1);}
				return count;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		}	
		//	
		
	}
	
	public static class Insert{
		
		//добавление ЭСЧФ
		/*public static boolean insertIncoming(String[] fields) throws SQLException, ParseException{
			String sql = "INSERT INTO "+ApplicationConstants.DB_TABLENAME+"("
					+ "UNP, " 				// 01 00
					+ "CODECOUNTRY, " 		// 02 02
					+ "NAME, " 				// 03 03
					+ "NUMBERINVOICE, " 	// 04 12
					+ "TYPEINVOICE, " 		// 05 13
					+ "STATUSINVOICERU, " 	// 06 14
					+ "STATUSINVOICEEN, " 	// 07 
					+ "DATEISSUE, " 		// 08 15
					+ "DATECOMMISSION, " 	// 09 16
					+ "DATESIGNATURE, " 	// 10 17
					+ "BYINVOICE, " 		// 11 18
					+ "DATECANCELLATION, " 	// 12 19
					+ "TOTALEXCISE, " 		// 13 20
					+ "TOTALVAT, " 			// 14 21
					+ "TOTALALL, " 			// 15 22
					+ "TOTALCOST, " 		// 16 
					+ "DATEDOCUMENT)" 		// 17 29
					+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			boolean result = false;
				PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql);
				statement.setString(1,  fields[0]);
				statement.setString(2,  fields[2]);
				statement.setString(3,  fields[3]);
				statement.setString(4,  fields[12]);
				statement.setString(5,  fields[13]);
				statement.setString(6,  fields[14]);
				statement.setString(7, Status.valueRuOf(fields[14]));
				if(fields[15].trim().length() > 0){
					//System.out.println("1 - "+fields[13].trim()+" "+DateValidator.validate(fields[13].trim()));
					//if(DateValidator.validate(DateValidator.ORIGINAL_DASH_DATE_PATTERN, fields[13].trim())){
					statement.setString(8,  InvoiceDateFormat.dateReverseSmallDash2String(InvoiceDateFormat.string2DateSmallDash(fields[15])));
					//}
					//statement.setString(8,  InvoiceDateFormat.dateReverseSmallDash2String(new SimpleDateFormat("dd.MM.yyyy").parse(fields[13])));
					//if(DateValidator.validate(DateValidator.REVERSE_DASH_DATE_PATTERN, fields[13].trim())){
						//statement.setString(8,  fields[13]);
					//}
				}else{
					statement.setString(8,  fields[15]);
				}
				if(fields[16].trim().length() > 0){
					//System.out.println("2 - "+fields[14].trim()+" "+DateValidator.validate(fields[14].trim()));
					statement.setString(9,  InvoiceDateFormat.dateReverseSmallDash2String(InvoiceDateFormat.string2DateSmallDash(fields[16])));
					//statement.setString(9,  InvoiceDateFormat.dateReverseSmallDash2String(new SimpleDateFormat("dd.MM.yyyy").parse(fields[14])));
					//statement.setString(9,  fields[14]);
				}else{
					statement.setString(9,  fields[16]);
				}
				if(fields[17].trim().length() > 0){
					//System.out.println("3 - "+fields[15].trim()+" "+DateValidator.validate(fields[15].trim()));
					statement.setString(10,  InvoiceDateFormat.dateReverseSmallDash2String(InvoiceDateFormat.string2DateSmallDash(fields[17])));
					//statement.setString(10,  InvoiceDateFormat.dateReverseSmallDash2String(new SimpleDateFormat("dd.MM.yyyy").parse(fields[15])));
					//statement.setString(10,  fields[15]);
				}else{
					statement.setString(10,  fields[17]);
				}
				statement.setString(11, fields[18]);
				if(fields[19].trim().length() > 0){
					//System.out.println("4 - "+fields[17].trim()+" "+DateValidator.validate(fields[17].trim()));
					statement.setString(12,  InvoiceDateFormat.dateReverseSmallDash2String(InvoiceDateFormat.string2DateSmallDash(fields[19])));
					//statement.setString(12,  InvoiceDateFormat.dateReverseSmallDash2String(new SimpleDateFormat("dd.MM.yyyy").parse(fields[17])));
					//statement.setString(12,  fields[17]);
				}else{
					statement.setString(12,  fields[19]);
				}
				statement.setString(13, fields[20].replace(",", "."));
				statement.setString(14, fields[21].replace(",", "."));
				statement.setString(15, fields[22].replace(",", "."));
				statement.setString(16, String.format("%.3f",(Float.parseFloat(fields[22].replace(",", "."))-Float.parseFloat(fields[21].replace(",", "."))-Float.parseFloat(fields[20].replace(",", ".")))));
				if(fields[29].trim().length() > 0){
					//System.out.println("5 - "+fields[27].trim()+" "+DateValidator.validate(fields[27].trim()));
					//statement.setString(17,  InvoiceDateFormat.dateReverseSmallDash2String(InvoiceDateFormat.string2DateSmallDash(fields[27])));
					//statement.setString(17,  InvoiceDateFormat.dateReverseSmallDash2String(new SimpleDateFormat("dd.MM.yyyy").parse(fields[27])));
					statement.setString(17,  fields[29]);
				}else{
					statement.setString(17,  fields[29]);
				}
				statement.executeUpdate();
				System.out.println("add");
				result = true;
				return result;		
		}
		
	}*/
		
		public static boolean insertIncoming(String[] fields) throws SQLException, ParseException{
			String sql = "INSERT INTO "+ApplicationConstants.DB_TABLENAME+"("
					+ "UNP, " 				// 01 00	01
					+ "CODECOUNTRY, " 		// 02 02	00
					+ "NAME, " 				// 03 03	03
					+ "NUMBERINVOICE, " 	// 04 12	13
					+ "TYPEINVOICE, " 		// 05 13	15
					+ "STATUSINVOICERU, " 	// 06 14	16
					+ "STATUSINVOICEEN, " 	// 07 
					+ "DATEISSUE, " 		// 08 15	17
					+ "DATECOMMISSION, " 	// 09 16	18
					+ "DATESIGNATURE, " 	// 10 17	19
					+ "BYINVOICE, " 		// 11 18	14
					+ "DATECANCELLATION, " 	// 12 19	20
					+ "TOTALEXCISE, " 		// 13 20	38
					+ "TOTALVAT, " 			// 14 21	39
					+ "TOTALALL, " 			// 15 22	40
					+ "TOTALCOST, " 		// 16 		37
					+ "DATEDOCUMENT)" 		// 17 29	36
					+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			boolean result = false;
				PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql);
				statement.setString(1,  fields[1]);
				statement.setString(2,  fields[0]);
				statement.setString(3,  fields[3]);
				statement.setString(4,  fields[13]);
				statement.setString(5,  fields[15]);
				statement.setString(6,  fields[16]);
				statement.setString(7, Status.valueRuOf(fields[16]));
				if(fields[17].trim().length() > 0){
					statement.setString(8,  InvoiceDateFormat.dateReverseSmallDash2String(InvoiceDateFormat.string2DateSmallDash(fields[17])));
				}else{
					statement.setString(8,  fields[15]);
				}
				if(fields[18].trim().length() > 0){
					statement.setString(9,  InvoiceDateFormat.dateReverseSmallDash2String(InvoiceDateFormat.string2DateSmallDash(fields[18])));
				}else{
					statement.setString(9,  fields[18]);
				}
				if(fields[19].trim().length() > 0){
					statement.setString(10,  InvoiceDateFormat.dateReverseSmallDash2String(InvoiceDateFormat.string2DateSmallDash(fields[19])));
				}else{
					statement.setString(10,  fields[19]);
				}
				statement.setString(11, fields[14]);
				if(fields[20].trim().length() > 0){
					statement.setString(12,  InvoiceDateFormat.dateReverseSmallDash2String(InvoiceDateFormat.string2DateSmallDash(fields[20])));
				}else{
					statement.setString(12,  fields[20]);
				}
				statement.setString(13, fields[38].replace(",", "."));
				statement.setString(14, fields[39].replace(",", "."));
				statement.setString(15, fields[40].replace(",", "."));
				statement.setString(16, fields[37].replace(",", "."));
				if(fields[36].trim().length() > 0){
					statement.setString(17,  fields[36]);
				}else{
					statement.setString(17,  fields[36]);
				}
				statement.executeUpdate();
				System.out.println("add");
				result = true;
				return result;		
		}
		
	}
	
	public static class Lists{
		
		//список всех ЭСЧФ для обновления
		public static List<String> selectNumbersInvoice(){
			List<String> list = new ArrayList<String>();
			String sql = "SELECT NUMBERINVOICE"
							+ " FROM "+ApplicationConstants.DB_TABLENAME;
			try(Statement statement = ConnectionDB.getInstance().getConnection().createStatement()){
				list.clear();
				ResultSet set = statement.executeQuery(sql);
				while(set.next()){list.add(set.getString(1).trim());}
				return list;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return null;			
			}
		}
		
		//список неподписанных ЭСЧФ для обновления
		public static List<String> selectNotSignedNumbersInvoice(){
			List<String> list = new ArrayList<String>();
			String sql = "SELECT NUMBERINVOICE"
							+ " FROM "+ApplicationConstants.DB_TABLENAME
							+ " WHERE STATUSINVOICEEN IS NOT NULL"
								+ " AND (STATUSINVOICEEN = 'COMPLETED'"
								+ " OR STATUSINVOICEEN = 'ON_AGREEMENT'"
								+ " OR STATUSINVOICEEN = 'IN_PROGRESS'"
								+ " OR STATUSINVOICEEN = 'NOT_FOUND')";
			try(Statement statement = ConnectionDB.getInstance().getConnection().createStatement()){
				list.clear();
				ResultSet set = statement.executeQuery(sql);
				while(set.next()){list.add(set.getString(1).trim());}
				return list;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return null;			
			}
		}
	}
	
	public static class Report{
		
		//список ЭСЧФ для отчета
		public static List<UnloadedInvoice> selectSignedNumbersInvoice(){
			List<UnloadedInvoice> list = new ArrayList<UnloadedInvoice>();
			String sql = "SELECT UNP, DATECOMMISSION, NUMBERINVOICE, STATUSINVOICEEN, TOTALCOST, TOTALVAT, TOTALALL, DATEDOCUMENT"
							+ " FROM "+ApplicationConstants.DB_TABLENAME
							+ " WHERE STATUSINVOICEEN IS NOT NULL";
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
					String dateDocument = "";
					if(set.getString("DATEDOCUMENT").length()>0){
						dateDocument = InvoiceDateFormat.dateSmallDot2String(InvoiceDateFormat.string2DateReverseSmallDash(set.getString("DATEDOCUMENT").trim()));
					}
					list.add(new UnloadedInvoice.Builder()
							.setUnp(set.getString("UNP").trim())
							.setDateCommission(new SimpleDateFormat("dd.MM.yyyy").parse(
									InvoiceDateFormat.dateSmallDot2String(
											InvoiceDateFormat.string2DateReverseSmallDash(
													set.getString("DATECOMMISSION").trim()))))
							.setNumberInvoice(set.getString("NUMBERINVOICE").trim())
							.setStatusInvoiceRu(statusRu)
							.setTotalCost(set.getString("TOTALCOST").trim())
							.setTotalVat(set.getString("TOTALVAT").trim())
							.setTotalAll(set.getString("TOTALALL").trim())
							.setDateDocument(dateDocument)
							.build());
				}
				return list;
			} catch (SQLException | ParseException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return null;			
			}
		}
			
		//список ЭСЧФ для отчета на дату
		public static List<UnloadedInvoice> selectSignedNumbersInvoiceAtDate(java.sql.Date date, Comparator<UnloadedInvoice> comparator, String status){
			List<UnloadedInvoice> list = new ArrayList<UnloadedInvoice>();
			String sql = "SELECT UNP, DATECOMMISSION, NUMBERINVOICE, STATUSINVOICEEN, TOTALCOST, TOTALVAT, TOTALALL, DATEDOCUMENT"
							+ " FROM "+ApplicationConstants.DB_TABLENAME
							+ " WHERE STATUSINVOICEEN IS NOT NULL"
								+ " AND DATECOMMISSION = '"+date.toString()+"' "+status
							+ " ORDER BY strftime('%Y',DATECOMMISSION), DATE(DATECOMMISSION)";
			try(Statement statement = ConnectionDB.getInstance().getConnection().createStatement()){
				list.clear();
				ResultSet set = statement.executeQuery(sql);
				String statusRu = "";
				Color color = Color.BLACK;
				while(set.next()){
					if(set.getString("STATUSINVOICEEN").trim().equals("COMPLETED_SIGNED")){
						statusRu = "Подписан";
						color = ResultFont.getGreen();
					}else if((set.getString("STATUSINVOICEEN").trim().equals("CANCELLED"))||(set.getString("STATUSINVOICEEN").trim().equals("ON_AGREEMENT_CANCELLED"))){
						statusRu = "Аннулирован";
						color = ResultFont.getBlack();
					}
					else{
						statusRu = "Не подписан";
						color = ResultFont.getRed();
					}
					String dateDocument = "";
					if(set.getString("DATEDOCUMENT").length()>0){
						dateDocument = InvoiceDateFormat.dateSmallDot2String(InvoiceDateFormat.string2DateReverseSmallDash(set.getString("DATEDOCUMENT").trim()));
					}
					list.add(new UnloadedInvoice.Builder()
							.setUnp(set.getString("UNP").trim())
							.setDateCommission(new SimpleDateFormat("dd.MM.yyyy").parse(
									InvoiceDateFormat.dateSmallDot2String(
											InvoiceDateFormat.string2DateReverseSmallDash(
													set.getString("DATECOMMISSION").trim()))))
							.setNumberInvoice(set.getString("NUMBERINVOICE").trim())
							.setStatusInvoiceRu(statusRu)
							.setTotalCost(set.getString("TOTALCOST").trim())
							.setTotalVat(set.getString("TOTALVAT").trim())
							.setTotalAll(set.getString("TOTALALL").trim())
							.setDateDocument(dateDocument)
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
		public static List<UnloadedInvoice> selectSignedNumbersInvoiceAtBetween(java.sql.Date dateFirst, java.sql.Date dateLast, Comparator<UnloadedInvoice> comparator, String status){
			List<UnloadedInvoice> list = new ArrayList<UnloadedInvoice>();
			String sql = "SELECT UNP, DATECOMMISSION, NUMBERINVOICE, STATUSINVOICEEN, TOTALCOST, TOTALVAT, TOTALALL, DATEDOCUMENT"
							+ " FROM "+ApplicationConstants.DB_TABLENAME
							+ " WHERE STATUSINVOICEEN IS NOT NULL"
								+ " AND DATECOMMISSION BETWEEN '"+dateFirst.toString()+"' AND '"+dateLast.toString()+"' "+status;
			try(Statement statement = ConnectionDB.getInstance().getConnection().createStatement()){
				list.clear();
				ResultSet set = statement.executeQuery(sql);
				String statusRu = "";
				Color color = Color.BLACK;
				while(set.next()){
					if(set.getString("STATUSINVOICEEN").trim().equals("COMPLETED_SIGNED")){
						statusRu = "Подписан";
						color = ResultFont.getGreen();
					}else if((set.getString("STATUSINVOICEEN").trim().equals("CANCELLED"))||(set.getString("STATUSINVOICEEN").trim().equals("ON_AGREEMENT_CANCELLED"))){
						statusRu = "Аннулирован";
						color = ResultFont.getBlack();
					}
					else{
						statusRu = "Не подписан";
						color = ResultFont.getRed();
					}
					String dateDocument = "";
					if(set.getString("DATEDOCUMENT").length()>0){
						dateDocument = InvoiceDateFormat.dateSmallDot2String(InvoiceDateFormat.string2DateReverseSmallDash(set.getString("DATEDOCUMENT").trim()));
					}
					list.add(new UnloadedInvoice.Builder()
							.setUnp(set.getString("UNP").trim())
							.setDateCommission(new SimpleDateFormat("dd.MM.yyyy").parse(
									InvoiceDateFormat.dateSmallDot2String(
											InvoiceDateFormat.string2DateReverseSmallDash(
													set.getString("DATECOMMISSION").trim()))))
							.setNumberInvoice(set.getString("NUMBERINVOICE").trim())
							.setStatusInvoiceRu(statusRu)
							.setTotalCost(set.getString("TOTALCOST").trim())
							.setTotalVat(set.getString("TOTALVAT").trim())
							.setTotalAll(set.getString("TOTALALL").trim())
							.setDateDocument(dateDocument)
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
		
	}	

	public static class Update{
		
		//обновление статусов ЭСЧФ
		public static boolean updateStatus(String status, String number) throws SQLException{
			String sql = "UPDATE "+ApplicationConstants.DB_TABLENAME
							+ " SET STATUSINVOICEEN = ?, STATUSINVOICERU = ?"
							+ " WHERE NUMBERINVOICE = ?";
			boolean result = false;
			PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql);
				statement.setString(1, Status.valueOf(status.trim()).getEnValue());
				statement.setString(2, Status.valueOf(status.trim()).getRuValue());
				statement.setString(3, number);
				statement.executeUpdate();
				result = true;
				return result;
		}
		
		//обновление статусов при загрузке файла
		public static boolean updateStatusFromFile(String ruStatus, String number) throws SQLException{
			String sql = "UPDATE "+ApplicationConstants.DB_TABLENAME
							+ " SET STATUSINVOICEEN = ? WHERE NUMBERINVOICE = ?";
			boolean result = false;
			PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql);
			statement.setString(1, Status.valueRuOf(ruStatus.trim()));
			statement.setString(2, number);
			statement.executeUpdate();
			result = true;
			return result;
		}

		public static boolean updateDateFromFile(String column, String date, String number) throws SQLException{
			boolean result = false;
			if(!Table.isContainsColumn(Table.getColumns(), column)){
				System.err.println("Столбец ["+column+"] отсутствует в базе данных");
			}else{
				String sql = "UPDATE "+ApplicationConstants.DB_TABLENAME+" SET "+column+" = ? WHERE NUMBERINVOICE = ?";
				PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql);
				statement.setString(1, date.trim());
				statement.setString(2, number.trim());
				statement.executeUpdate();
				result = true;
			}
			return result;
				
		}
	}
	
	public static class Date{
		
		//список годов на основе таблицы
		public static List<String> selectYearInvoice(){
			List<String> list = new ArrayList<String>();
			String sql = "SELECT strftime('%Y',DATECOMMISSION) as cYEAR"
							+ " FROM "+ApplicationConstants.DB_TABLENAME
							+ " GROUP BY cYEAR ORDER BY cYEAR DESC";
			try(Statement statement = ConnectionDB.getInstance().getConnection().createStatement()){
				ResultSet set = statement.executeQuery(sql);
				while(set.next()){list.add(set.getString("cYEAR"));}
				return list;
			}catch(SQLException e){
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}
		
		//список месяцев из года на основе таблицы 
		public static List<String> selectMonthInvoiceOfYear(String year){
			List<String> list = new ArrayList<String>();
			String sql = "SELECT strftime('%m',DATECOMMISSION) AS cMONTH"
							+ " FROM "+ApplicationConstants.DB_TABLENAME
							+ " GROUP BY cMonth HAVING strftime('%Y',DATECOMMISSION) ='"+year.trim()+"'";
			try(Statement statement = ConnectionDB.getInstance().getConnection().createStatement()){
				ResultSet set = statement.executeQuery(sql);
				while(set.next()){list.add(set.getString("cMONTH"));}
				return list;
			}catch(SQLException e){
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}

		//получение списка пар Год-Месяц 
		public static List<MonthYearItem> selectMonthYear(String year){
			List<MonthYearItem> list = new ArrayList<MonthYearItem>();
			String sql = "SELECT strftime('%Y',DATECOMMISSION) as cYEAR, strftime('%m',DATECOMMISSION) as cMONTH"
							+ " FROM "+ApplicationConstants.DB_TABLENAME
							+ " GROUP BY cYEAR, cMONTH HAVING cYEAR = '"+year+"'"
							+ " ORDER BY cMONTH DESC";
			try(Statement statement = ConnectionDB.getInstance().getConnection().createStatement()){
				ResultSet set = statement.executeQuery(sql);
				while(set.next()){list.add(new MonthYearItem.Builder().setYear(set.getString("cYEAR")).setMonth(set.getString("cMONTH")).build());}
				return list;
			}catch(SQLException e){
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}
		
		//получение даты начала месяца
		public static String getStartMonthOfDate(String month, String year){
			String sql = "SELECT date(DATECOMMISSION,'start of month') AS startMonth"
							+ " FROM "+ApplicationConstants.DB_TABLENAME
							+ " GROUP BY date(DATECOMMISSION,'start of month')"
							+ " HAVING strftime('%Y',DATECOMMISSION) = '"+year+"'"
									+ " AND strftime('%m',DATECOMMISSION) = '"+month+"'";
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				String start = "";
				ResultSet set = statement.executeQuery();
				while(set.next()){start = set.getString("startMonth");}
				return start;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return "01.01.1999";
			}
		}
		
		//получение даты конца месяца
		public static String getEndMonthOfDate(String month, String year){
			String sql = "SELECT date(DATECOMMISSION,'start of month','+1 month','-1 day') AS endMonth"
							+ " FROM "+ApplicationConstants.DB_TABLENAME
							+ " GROUP BY date(DATECOMMISSION,'start of month')"
							+ " HAVING strftime('%Y',DATECOMMISSION) = '"+year+"'"
									+ " AND strftime('%m',DATECOMMISSION) = '"+month+"'";
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				String start = "";
				ResultSet set = statement.executeQuery();
				while(set.next()){start = set.getString("endMonth");}
				return start;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return "31.01.1999";
			}
		}
		
	}
	
	public static class Table{
		
		//получение списка столбцов
		public static List<String> getColumns(){
			List<String> list = new ArrayList<String>();
			String sql = "PRAGMA table_info("+ApplicationConstants.DB_TABLENAME+") ";
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				ResultSet set = statement.executeQuery();
				while(set.next()){list.add(set.getString("name"));}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
			}
			return list;
		}

		//есть ли столбец в списке столбцов?
		public static boolean isContainsColumn(List<String> list, String column){
			boolean result = false;
			if(list.contains(column)){
				result = true;
			}
			return result;
		}
		
		//добавление столбца в таблицу
		public static boolean addColumn(String column, String type){
			String sql = "ALTER TABLE "+ApplicationConstants.DB_TABLENAME+" ADD COLUMN "+column.trim()+" "+type.trim();
			boolean result = false;
			try(PreparedStatement statement = ConnectionDB.getInstance().getConnection().prepareStatement(sql)){
				statement.executeUpdate();
				result = true;
			}catch(SQLException e){
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Ошибка",JOptionPane.ERROR_MESSAGE);
				return false;
			}
			return result;
		}
		
	}
	
	
}
