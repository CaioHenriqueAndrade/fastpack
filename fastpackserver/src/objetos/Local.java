package objetos;

import java.sql.ResultSet;

public class Local {

	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	private float latitude, longitude;

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public static Local getLocalIfExists(ResultSet rs) throws Exception {
		
		float latitude = rs.getFloat(LATITUDE );
		
		if( latitude != 0 ) {
			Local local = new Local();
			local.setLatitude( latitude );
			local.setLongitude( rs.getFloat( LONGITUDE ) );
			return local;
		}
		
		return null;
	}



}
