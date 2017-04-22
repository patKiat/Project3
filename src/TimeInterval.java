import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeInterval {
	public static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	public static final SimpleDateFormat shortTimeFormat = new SimpleDateFormat("HH");
	
	public Time startTime;
	public Time endTime;
	
	// in case the string input is "8:00-20:30"
	public TimeInterval(String bothStartEnd){
		String[] temp = bothStartEnd.split("-");
		setStartTimeEndTime(temp[0], temp[1]);
	}
	
	// in case the start string is "8:00" and end string is "20;00"
	public TimeInterval(String start, String end){
		setStartTimeEndTime(start,end);	
	}
	
	/*
	 * convert string time into startTime and EndTime
	 * 
	 */
	public void setStartTimeEndTime(String start, String end){
		// YOUR CODE GOES HERE

		if(!start.equals("-") && !end.equals("-")){
			try {
				if (!start.contains(":")) {
					startTime = new Time(shortTimeFormat.parse(start).getTime());
				}
				else {
					startTime = new Time(timeFormat.parse(start).getTime());
				}

				if (!end.contains(":")) {
					endTime = new Time(shortTimeFormat.parse(end).getTime());
				}
				else {
					endTime = new Time(timeFormat.parse(end).getTime());
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		else {
			startTime = null;
			endTime = null;
		}

	}
	
	/*
	 * to check whether this TimeInterval contains the current time or not
	 * For example, if the TimeInteval is from 8:00 - 20:00 and the current time is 9:00, 
	 * this method will return true. If the current time is 22:00, this method will return false.
	 */
	public boolean contains(String str){
		// YOUR CODE GOES HERE
		long current;

		try {
			if (!str.contains(":")) {
				current = (shortTimeFormat.parse(str).getTime());
			}
			else {
				current = (timeFormat.parse(str).getTime());
			}

			if(current >= startTime.getTime() && current <= endTime.getTime()) {
				return true;
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	public String toString(){
		if(startTime == null || endTime == null)
			return "";
		else
			return timeFormat.format(startTime) + "-" + timeFormat.format(endTime);
	}
	
	
	public static void main(String[] args){
		
		// implement the main method to test this class
		
		TimeInterval t = new TimeInterval("8-22:30");
		System.out.println(t.toString());
		Date now = new Date();
		Time time = new Time(now.getTime());
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		System.out.println(sdf.format(time));
		if(t.contains(sdf.format(now))){
			System.out.println("still open " + now.getDay());
		} else{
			System.out.println("already closed");
		}
	}
}
