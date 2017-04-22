import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Restaurant implements BusinessInterface{
	public static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");


    public static enum PRICE{
		$, $$, $$$, UNKNOWN			// the default value is PRICE.UNKNOWN
	}
	
	private int id;
	private String name;
	private Address address;
	private String phone;
	private String description;
	private Set<String> categories;
	private PRICE priceRange;
	private TimeInterval[] hours;	//(0 = Sunday, 1 = Monday, 2 = Tuesday, 3 = Wednesday, 4 = Thursday, 5 = Friday, 6 = Saturday) 
	private List<Review> reviews;
	private double rating;

    private String date;
    private String detail;
    private int bizID;
	
	public Restaurant(int id, String name){
		// initialize all values
		// YOUR CODE GOES HERE
		this.id = id;
		this.name = name;
	}
	
	public Restaurant(int id, String name, Address address, String phone, String desc){
		// initialize all values
		// YOUR CODE GOES HERE
		this.id = id;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.description = desc;

		hours= new TimeInterval[7];
		categories = new HashSet<String>();
		reviews = new ArrayList<Review>();
	}
	
	/*
	 * additional method for setting address
	 */
	public void setAddress(String street, String district, String province, String postalCode, double lat, double lon) {
		this.address = new Address(street, district, province, postalCode, lat, lon);
	}
	
	/*
	 * additional method for setting categories
	 * You will have to split each category in the input string and add into categories Set
	 * For example, if the string is "[Thai,Cafe]", you will add two categories into the categories Set
	 */
	public void setCategories(String str){
		// YOUR CODE GOES HERE
		String[] cat = str.replaceAll("[\\[\\]]", "").split(",");
		for (String i: cat) {
			categories.add(i);
		}

	}

	/*
	 * additional method for setting price range
	 * You must convert from String to enum PRICE
	 * For example, if the string is "$", then the priceRange will be enum of PRICE.$
	 */
	public void setPriceRange(String pr){
		// YOUR CODE GOES HERE
		priceRange = PRICE.valueOf(pr);
		if(priceRange.equals("")){
			priceRange = PRICE.UNKNOWN;
		}
	}
	
	
	/*
	 * additional method for setting open hours
	 * You must split the input string into an array containing exactly 7 elements.
	 * Each element will be used to create a TimeInterval object 
	 * where the start time is the open hour and the end time is the close hour.
	 * If an element equals to "close", then the TimeInterval object is null.
	 * For example, if string is "[8-20,close,8-20,8-20,8-20,8-20,8-20]", then
	 * 		hours[0] = new TimeInterval("8-20");
	 * 		hours[1] = null;
	 * 		hours[2] = new TimeInterval("8-20"); .... etc
	 */
	public void setHours(String str){
		// YOUR CODE GOES HERE
		String[] hr = str.replaceAll("[\\[\\]]", "").split(",");

		for(int i=0; i<7; i++){
			if(hr[i].contains("close")){
				hours[i] = new TimeInterval("-", "-");
			}
			else {
				hours[i] = new TimeInterval(hr[i]);
			}
		}
	}
	
	public Set<String> getCategories(){
		return this.categories;
	}
	
	public String getCategoriesString(){
		String str = "";
		for(String c: this.categories){
			str += c + ", ";
		}
		if(!str.isEmpty())
			str = str.substring(0, str.lastIndexOf(","));
		return str;
	}
	
	public PRICE getPriceRange(){
		// YOUR CODE GOES HERE
		
		return this.priceRange;
	}

	public TimeInterval[] getOpenHours(){
		// YOUR CODE GOES HERE

		return hours;
	}

	public TimeInterval getOpenHoursOnDay(int index){
		// YOUR CODE GOES HERE

		return hours[index];
	}

	/*
	The getAverageRating() method is used to return value from 'rating' without any further calculation.
	 */
	public double getAverageRating(){
		// YOUR CODE GOES HERE
//        System.out.println(this.calculateAverageRating());
        return this.calculateAverageRating();
	}
	
	public List<Review> getReviews(){
		// YOUR CODE GOES HERE

		return this.reviews;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public void setHours(TimeInterval[] hours) {

		this.hours = hours;
	}

	public void setPriceRange(PRICE priceRange) {

		this.priceRange = priceRange;
	}

	public void setCategories(Set<String> categories) {

		this.categories = categories;
	}

	public void setId(int id) {

		this.id = id;
	}

	/*public void setReviews(int bizID, String dateStr, int rating, String detail) {
        //review = new Review(review.getBizID(), review.getDate().toString(), review.getRating(), review.getDetail());
        this.bizID = bizID;
        this.rating = rating;
        this.detail = detail;
        this.date = dateStr;
        Review review = new Review(bizID, dateStr, rating, detail);
		reviews.add(review);
	}*/


    public void setReviews(Review review) {
        reviews.add(review);
//      System.out.println(review.getRating());
    }

	/*
            The calculateAverageRating() method is used to re-calculate rating score.
            The average rating is calculated by the sum of rating from all the review divided by the number of reviews.
            This method will be called when there is a new review inserted into the system.
             */
	public double calculateAverageRating(){
		// YOUR CODE GOES HERE
		double sumRating = 0.0;
		int count = 0;
		for(Review review: reviews){
			sumRating += review.getRating();
			count++;

		}
		if(count == 0){
			return 0;
		}
		rating = sumRating/count;
		return sumRating/count;
	}

	@Override
	public void setID(int id) {
		this.id = id;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public void setDescription(String desc) {
		this.description = desc;
	}

	@Override
	public int getID() {
		return this.id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Address getAddress() {
		return this.address;
	}

	@Override
	public String getPhone() {
		return this.phone;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public String getSnippetProfile() {
		String shortDesc = "";
		if(this.description.length() > 100)
			shortDesc = this.description.substring(0, 100) + "(more...)";
		else
			shortDesc = this.description;
		
		return this.name + " (" + this.priceRange.name() + ")\n" 
				+ shortDesc + "\n"
				+ "Categories: " + this.getCategoriesString() + "\n"
				+ "Rating: " + this.rating + " (" + this.reviews.size() + ")";
	}
	
	@Override
	public String getFullProfile() {
		return this.name + " (" + this.priceRange.name() + ")\n"
				+ this.description + "\n"
				+ "Categories: " + this.getCategoriesString() + "\n"
				+ "Rating: " + this.rating + " (" + this.reviews.size() + ")\n"
				+ "Address: " + this.address.toString() + "\n"
				+ "Phone: " + this.phone + "\n"
				+ "Hours: " + this.getHoursString();
	}
	
	public String toString(){
		return getFullProfile();
	}
	
	public String getHoursString(){
		String getStr = "\nSun: " + this.getOpenHour(hours[0]);
		getStr += "\nMon: " + this.getOpenHour(hours[1]);
		getStr += "\nTue: " + this.getOpenHour(hours[2]);
		getStr += "\nWed: " + this.getOpenHour(hours[3]);
		getStr += "\nThu: " + this.getOpenHour(hours[4]);
		getStr += "\nFri: " + this.getOpenHour(hours[5]);
		getStr += "\nSat: " + this.getOpenHour(hours[6]);
		
		return getStr;
	}
	
	public String getOpenHour(TimeInterval hour){
		if(hour.startTime == null || hour.endTime == null)
			return "close";

		else
			return timeFormat.format(hour.startTime) + "-" + timeFormat.format(hour.endTime);
	}
	
}
