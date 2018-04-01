package objetos;

import java.sql.ResultSet;

public class Local {

	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	private double latitude, longitude;

	public Local() { }
	public Local(String latitude, String longitude) {
		this.latitude = Double.parseDouble( latitude );
		this.longitude = Double.parseDouble( longitude );
	}
	
	
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public static Local getLocalIfExists(ResultSet rs) throws Exception {
		
		double latitude = rs.getDouble(LATITUDE );
		
		if( latitude != 0 ) {
			Local local = new Local();
			local.setLatitude( latitude );
			local.setLongitude( rs.getDouble( LONGITUDE ) );
			return local;
		}
		
		return null;
	}



}
