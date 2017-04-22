public class Address {
	
	String street;		// e.g., 999 Phuttamonthon 4 Road 
	String district;	// e.g., Salaya
	String province;	// e.g., Nakhon Pathom
	String postalCode;		// e.g., 73170
	double latitude = 0.0;	// e.g., 100.324611
	double longitude = 0.0;	// e.g., 13.794760
	
	public Address(String fullAddress){
		// YOUR CODE GOES HERE
		String[] address = fullAddress.replaceAll("[\\[\\]]", "").split(",");

		if(address.length == 6){
			this.street = address[0];
			this.district = address[1];
			this.province = address[2];
			this.postalCode = address[3];
			this.latitude = Double.parseDouble(address[4]);
			this.longitude = Double.parseDouble(address[5]);
		}
		else{
			this.street = "";
			this.district = "";
			this.province = "";
			this.postalCode = "";
		}
	}
	
	public Address(String street, String district, String province, String postalCode, double lat, double lon){
		// YOUR CODE GOES HERE
		this.street = street;
		this.district = district;
		this.province = province;
		this.postalCode = postalCode;
		this.latitude = lat;
		this.longitude = lon;
	}
	
	public String toString(){
		String str = street;
		if(!district.isEmpty())
			str += ", " + district;
		if(!province.isEmpty())
			str += ", " + province;
		if(!postalCode.isEmpty())
			str += ", " + postalCode;
		if(latitude != 0.0 & longitude != 0.0)
			str += "(" + latitude +"," + longitude + ")";
		return str;
	}
}
