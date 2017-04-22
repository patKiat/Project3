import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class SearchEngineModel {
	
	Map<Integer, Restaurant> restaurants = new HashMap<Integer, Restaurant>(); 
	String path;
	double[][] simScore;
	
	/*
	 * set path (full/ or sample/ folder)
	 */
	public void setPath(String path){
		this.path = path;
	}
	
	/*
	 * Open the file containing restaurant data.
	 * Each row must be split into eight attributes to create a Restaurant object.
	 * Then the Restaurant will be added into restaurants HashMap.
	 * If the number of attribute != 8, the entire row will be skipped.
	 * 
	 * input: file's name
	 * output: void
	 */
	public void LoadRestaurantData(String fileName){
		// YOUR CODE GOES HERE
		try {
			File dbFile = new File(path + fileName);
			List<String> lines = FileUtils.readLines(dbFile);

			for(String line: lines) {
				String[] part = line.split("\\|");
				if(part.length == 8){
					int id = Integer.parseInt(part[0]);
					String name = part[1];
					String phone = part[3];
					String desc = part[4];
					String cat = part[5];
					String pr = part[6];
					String hours = part[7];
					Address fullAddress = new Address(part[2]);

					Restaurant shop = new Restaurant(id, name, fullAddress, phone, desc);

					shop.setCategories(cat);
					shop.setPriceRange(pr);
					shop.setHours(hours);
					shop.calculateAverageRating();

					restaurants.put(id, shop);

				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/*
	 * Open the file containing review data. Each row in the file represent one review.
	 * This method will add a new review into the existing list of reviews of a given restaurant.
	 * Note that each restaurant object supposes to have an ArrayList of reviews.
	 * 
	 * input: file's name
	 * output: void
	 */
	public void LoadReivewData(String fileName){

		// YOUR CODE GOES HERE
		try {
			File reviewFile = new File(path + fileName);
			List<String> lines = FileUtils.readLines(reviewFile);

			for(String line: lines) {
				//int bizID, String dateStr, int rating, String detail
				String[] part = line.split("\\|");
				int bizID = Integer.parseInt(part[0]);
				String dateStr = part[1];
				int rating = Integer.parseInt(part[2]);
				String detail = part[3];
				Review shop = new Review(bizID, dateStr, rating, detail);
				for (Restaurant restaurant: restaurants.values()) {
					if(restaurant.getID() == bizID){
						restaurant.setReviews(shop);
						//System.out.println(bizID);
						//System.out.println(restaurant.getReviews());

					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Open the file containing similarity score data and 
	 * assign those values into simScore matrix. 
	 * 
	 * input: file's name
	 * output: void
	 */
	public void LoadSimilarityData(String fileName){
		
		// YOUR CODE GOES HERE
		this.simScore = new double[getNumberOfRestaurants()][getNumberOfRestaurants()];
		try {
			File dbFile = new File(path + fileName);
			List<String> lines = FileUtils.readLines(dbFile);

			int idIndex = 0;

			for(String line: lines) {
				String[] part = line.split(",");

				for(int j=0; j<part.length; j++){
					this.simScore[idIndex][j] = Double.parseDouble(part[j]);
				}
				idIndex++;

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * To return a restaurant from the HashMap according to the given id.
	 */
	public Restaurant getRestaurantByID(int id){
		// YOUR CODE GOES HERE

		return restaurants.get(id);
	}
	
	/* 
	 * To get the total number of restaurants in the HashMap
	 */
	public int getNumberOfRestaurants(){
		// YOUR CODE GOES HERE

		return restaurants.size();
	}
	
	/*
	 * To get the total number of reviews of a specific restaurant
	 */
	public int getNumberOfReviews(int restaurantID){
		// YOUR CODE GOES HERE

		return restaurants.get(restaurantID).getReviews().size();
	}
	
	/*
	 * To get a list of reviews of a specific restaurant
	 */
	public List<Review> getAllReviews(int restaurantID){
		// YOUR CODE GOES HERE

		return restaurants.get(restaurantID).getReviews();
	}
	
	public double getSimilarityScore(int restaurantID1, int restaurantID2){
		// YOUR CODE GOES HERE

		return simScore[restaurantID1][restaurantID2];
	}
	
	/*
	 * To search for restaurants that satisfy ALL the given input parameters
	 * @input: 	name (String) - restaurant name (the results must "contain" the name parameter)
	 * 			category (String) - category of restaurant e.g., Cafe or Thai
	 * 			priceRange (String) - the price level must be $, $$ or $$$
	 * 			checkOpen (boolean) - if true, you must check whether the restaurant still opens or not by looking 
	 * 								  at its open hours. If false, you do not have to check its open hours.
	 * 
	 * @output: array of restaurants that satisfy ALL the input parameters
	 */
	public Restaurant[] searchRestaurant(String name, String category, String priceRange, Boolean checkOpen){
		// YOUR CODE GOES HERE
		ArrayList<Restaurant> restaurant = new ArrayList<Restaurant>();

		boolean checkName = false;
		boolean checkCat = false;
		boolean checkPrice = false;
		boolean open = false;

		Date date = new Date();
		// creates a new calendar instance
		Calendar calendar = GregorianCalendar.getInstance();
		// assigns calendar to given date
		calendar.setTime(date);

		// gets hour in 24h format
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int mins = calendar.get(Calendar.MINUTE);
		int day  = calendar.get(Calendar.DAY_OF_WEEK);

		for (Restaurant a: restaurants.values()) {

			if(a.getOpenHours()[day - 1].contains(hour + ":" + mins) == checkOpen)
			{
				if(a.getName().contains(name) || name.equals("")){
					checkName = true;
				} else {
					checkName = a.getName().contains(name);
				}
				if(a.getCategories().contains(category)|| category.equals("")){
					checkCat = true;
				} else {
					checkCat = a.getCategories().contains(category);
				}
				if(priceRange.equals("")){
					checkPrice = true;
				} else {
					checkPrice = (a.getPriceRange() == Restaurant.PRICE.valueOf(priceRange));
				}
			}
			if(checkName == true && checkCat == true && checkPrice == true && checkOpen == true){
				restaurant.add(a);
			}
        }

        Restaurant[] realRestaurant = new Restaurant[restaurant.size()];
        realRestaurant = restaurant.toArray(realRestaurant);

		return realRestaurant;
	}
	
	/*
	 * To sorted the array of restaurant according to the specific key attributes (i.e., Name, Rating, #Reviews)
	 * 
	 * @input: the array of restaurants and the key attribute 
	 * 
	 * @output: the sorted array of restaurant based on the sorted key attribute.
	 */
	public Restaurant[] sortedResultBy(Restaurant[] array, String sort){
		// YOUR CODE GOES HERE
		if(sort.equals("Name")){
			boolean flag = true;   	// set flag to true to begin first pass
			while ( flag ){
				flag= false;    	//set flag to false awaiting a possible swap
				for(int i=(array.length); i>0; i--)
				{
					for(int j=0; j<(array.length-i); j++)
					{
						if( array[j].getName().compareTo(array[j+1].getName())>0)// change to > for ascending sort
						{
							Restaurant Temp = array[j];		//swap elements
							array[j] = array[j+1];
							array[j+1] = Temp;
							flag = true;              		//shows a swap occurred
						}
					}
				}
			}
		}
		if(sort.equals("Rating")){
			boolean flag = true;   	// set flag to true to begin first pass
			while ( flag ){
				flag= false;    	//set flag to false awaiting a possible swap
				for(int i=(array.length); i>0; i--)
				{
					for(int j=0; j<(array.length-i); j++)
					{
						if( array[j].getAverageRating() > array[j+1].getAverageRating())// change to > for ascending sort
						{
							Restaurant Temp = array[j];		//swap elements
							array[j] = array[j+1];
							array[j+1] = Temp;
							flag = true;              		//shows a swap occurred
						}
					}
				}
			}
		}
		if(sort.equals("#Reviews")){
			boolean flag = true;
			while ( flag ){
				flag= false;    	//set flag to false awaiting a possible swap
				for(int i=(array.length); i>0; i--)
				{
					for(int j=0; j<(array.length-i); j++)
					{
						if(array[j].getReviews().size() > array[j+1].getReviews().size())// change to > for ascending sort
						{
							Restaurant Temp = array[j];		//swap elements
							array[j] = array[j+1];
							array[j+1] = Temp;
							flag = true;              		//shows a swap occurred
						}
					}
				}
			}
		}
		return array;
	}
	
	/*
	 * To find the N number of similar restaurants that have the most top N highest similarity scores 
	 * 
	 * @input: the restaurant id, and number of similar restaurants that will be returned
	 * 
	 * @output: the array of similar restaurants. The size of the array is numOfRestaurant input parameter 
	 */
	
	public Restaurant[] findSimilarRestaurant(int restaurantID, int numOfRestaurant){
		// YOUR CODE GOES HERE
		Restaurant[] restaurants = new Restaurant[numOfRestaurant];
		double[] simScore = new double[this.simScore[0].length];
		simScore = this.simScore[restaurantID].clone();

		int x = 0;

		double max = 0;
		for (int j = 0; j < numOfRestaurant; j++){

			for(int i = 0; i < simScore.length; i++){
				if(simScore[i] > max && i != restaurantID){
					max = simScore[i];
				}
			}

			for(int i = 0; i < simScore.length; i++){
				if(simScore[i] == max && i != restaurantID){
					restaurants[x] = this.restaurants.get(i);
					x++;
					max = 0;
					simScore[i] = 0;
					break;
				}
			}

		}
		return restaurants;
	}
}
