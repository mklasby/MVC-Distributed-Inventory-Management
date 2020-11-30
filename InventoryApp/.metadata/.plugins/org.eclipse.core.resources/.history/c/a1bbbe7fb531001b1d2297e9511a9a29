package paymentManager;

import java.security.SecureRandom;
import java.util.Date;

public class Receipt {
	private int receiptId;
	private double amount;
	private Date theDate;
	
	public Receipt() {
		SecureRandom random = new SecureRandom();
		int num = random.nextInt(100000); // creates random 5 digit Id
//		String formatted = String.format("%05d", num); 
		setReceiptId(num);
		theDate = new Date(); // can adjust to make it a String with desired format 
;	}
	
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
		
	}

	public Date getTheDate() {
		return theDate;
	}

	public void setTheDate(Date theDate) {
		this.theDate = theDate;
	}

	public int getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(int receiptId) {
		this.receiptId = receiptId;
	}

	

}
