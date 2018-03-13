package objetos;

import java.sql.ResultSet;

import sql.Script;

public class Address extends ObjectBasic {

    private String street = "";

    private String complementary = "";

    private String streetNumber = "";

    private String neighborhood = "";

    private String city = "";

    private String state = "";

    private String zipcode = "";

    private String country = "";

    
    private int valido = VALIDADE_FALSE;
    
    private Local local;
    
    public static final int VALIDADE_FALSE = 0;
    public static final int VALIDADE_TRUE = 1;
    
    public static final int KEY_UUID  = 232;

    
	@Override
	public boolean getDados(ResultSet rs) throws Exception  {
		// TODO Auto-generated method stub
		setCity(			rs.getString( Script.Address.CIDADE ) 	);
		setComplementary(	rs.getString( Script.Address.COMPLEMENTARY) );
		setCountry(			rs.getString( Script.Address.PAIS	 )   );
		setNeighborhood(	rs.getString( Script.Address.BAIRRO	  )  );
		setState(		  	rs.getString( Script.Address.ESTADO	  )  );
		setStreet(			rs.getString( Script.Address.STREET	  )  );
		setStreetNumber(	rs.getString( Script.Address.STREET_NUMBER ) );
		setZipcode(			rs.getString( Script.Address.CEP 	  )  );
		
		local = Local.getLocalIfExists( rs );
		
		return true;
	}

    
	public boolean existsLocal() {
		return local != null && local.getLatitude() != 0;
	}
    
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getComplementary() {
        return complementary;
    }

    public void setComplementary(String complementary) {
        this.complementary = complementary;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    public String getZipcode() {
        return zipcode;
    }
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public int getValido() {
        return valido;
    }
    public void setValido(int valido) {
        this.valido = valido;
    }
    public boolean isValido() {
        return getValido() == VALIDADE_TRUE;
    }

	public Local getLocal() {
		return local;
	}
	public void setLocal(Local local) {
		this.local = local;
	}
    
    
}