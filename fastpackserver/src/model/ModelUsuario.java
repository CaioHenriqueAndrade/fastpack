package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import objetos.Usuario;
import sql.Script;
import sql.StringSql;

public class ModelUsuario extends BaseObjectMySql<Usuario> {

	private Usuario usuario;
	
	@Override
	public void setDados(PreparedStatement stmt) throws SQLException, Exception {
		stmt.setString(	1, usuario.getCpf( ));
		stmt.setInt(	2, usuario.getIdUser( ));
		stmt.setString(	3, usuario.getPassword());
		stmt.setInt(	4, usuario.getStatus());
		stmt.setInt(	5, usuario.getTipo());
	}

	@Override
	public String getNameTable() {
		// TODO Auto-generated method stub
		return Script.Usuario.NAMETABLE;
	}

	@Override
	public boolean getDados(ResultSet rs) throws SQLException, Exception {
		usuario.getDados( rs );
		return true; 
	}

	public Usuario getUsuario() {
		return usuario;
	}
	

	public void inserir() throws Exception  {
		StringSql.Insert insertSql = new StringSql.Insert( getNameTable() );
		insertSql.addColumn( Script.Usuario.CPF);
		insertSql.addColumn( Script.Usuario.ID);
		insertSql.addColumn( Script.Usuario.PASSWORD);
		insertSql.addColumn( Script.Usuario.STATUS);
		insertSql.addColumn( Script.Usuario.TIPO);	
		
		super.inserir( insertSql );	
		
	}

	@Override
	public Class<Usuario> getClassTypeT() {
		// TODO Auto-generated method stub
		return Usuario.class;
	}
	
	
	
	
}
