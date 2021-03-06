package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import objetos.Address;
import sql.Script;
import sql.StringSql;

public class ModelAddress extends BaseObjectMySql<Address> {

	private Address address;
	
	public ModelAddress() {
	}
	public ModelAddress(Address address) {
		this.address = address;
	}	

	
	@Override
	public void setDados(PreparedStatement stmt) throws SQLException {
		// TODO Auto-generated method stub
		stmt.setString( 1, getAddress().getNeighborhood() 	);
		stmt.setString( 2, getAddress().getZipcode() 		);
		stmt.setString( 3, getAddress().getCity() 			);
		stmt.setString( 4, getAddress().getComplementary()  );
		stmt.setString( 5, getAddress().getState() 			);
		stmt.setString( 6, getAddress().getCountry() 		);
		stmt.setString( 7, getAddress().getStreet() 		);
		stmt.setString( 8, getAddress().getStreetNumber() 	);
		stmt.setInt( 	9, getAddress().getIdCriador() 		);
		
	}

	@Override
	public String getNameTable() {
		// TODO Auto-generated method stub
		return Script.Address.NAMETABLE;
	}

	@Override
	public boolean getDados(ResultSet rs) throws SQLException, Exception {
		return address.getDados( rs );
	}

	public void inserir() throws Exception {
		StringSql.Insert insertSql = new StringSql.Insert( getNameTable() );
		insertSql.addColumn( Script.Address.BAIRRO);
		insertSql.addColumn( Script.Address.CEP);
		insertSql.addColumn( Script.Address.CIDADE);
		insertSql.addColumn( Script.Address.COMPLEMENTARY);
		insertSql.addColumn( Script.Address.ESTADO);
		insertSql.addColumn( Script.Address.PAIS);
		insertSql.addColumn( Script.Address.STREET);
		insertSql.addColumn( Script.Address.STREET_NUMBER);
		insertSql.addColumn( Script.Address.IDCRIADOR);
		
		getAddress().setId( (Integer) super.inserir( insertSql ) );
	}
	
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public Class<Address> getClassTypeT() {
		// TODO Auto-generated method stub
		return Address.class;
	}
	
}
