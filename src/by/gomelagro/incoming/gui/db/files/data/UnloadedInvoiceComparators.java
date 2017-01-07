package by.gomelagro.incoming.gui.db.files.data;

import java.util.Comparator;

public class UnloadedInvoiceComparators {
	public static Comparator<UnloadedInvoice> compareToUnp = new Comparator<UnloadedInvoice>(){
		public int compare(UnloadedInvoice invoice1, UnloadedInvoice invoice2){
			return invoice1.getUnp().compareTo(invoice2.getUnp());
		}
	};
	
	public static Comparator<UnloadedInvoice> compareToDate = new Comparator<UnloadedInvoice>(){
		public int compare(UnloadedInvoice invoice1, UnloadedInvoice invoice2){
			return invoice1.getDateCommission().compareTo(invoice2.getDateCommission());
		}
	};
	
	public static Comparator<UnloadedInvoice> compareToStatus = new Comparator<UnloadedInvoice>(){

		public int compare(UnloadedInvoice invoice1, UnloadedInvoice invoice2) {
			return invoice1.getStatusInvoice().compareTo(invoice2.getStatusInvoice());
		}
		
	};
}
