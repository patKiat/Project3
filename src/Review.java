import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Review {
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	private Date date;
	private String detail;
	private int rating;
	private int bizID;
	
	
	public Review(int bizID, String dateStr, int rating, String detail){
		// Initialize all attributes
		// YOUR CODE GOES HERE
		this.bizID = bizID;
		this.rating = rating;
		this.detail = detail;

		try {
			date = dateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	// create all setter and getter methods
	// YOUR CODE GOES HERE

	public static SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getBizID() {
		return bizID;
	}

	public void setBizID(int bizID) {
		this.bizID = bizID;
	}

	public String toString(){
		return dateFormat.format(date) + " (" + rating + ") " + detail;
	}
	
}

