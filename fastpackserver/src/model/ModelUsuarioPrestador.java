package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import objetos.Local;
import objetos.UsuarioPrestador;
import sql.Script;
import sql.StringSql;

public class ModelUsuarioPrestador extends BaseObjectMySql<UsuarioPrestador> {

	private UsuarioPrestador usuario;
	
	public ModelUsuarioPrestador() {
	}

	public ModelUsuarioPrestador(UsuarioPrestador usuario) {
		setUsuario( usuario );
	}
	
	
	public void inserir(boolean needValidacao) throws Exception {
		
		StringSql.Insert insert = new StringSql.Insert( getNameTable() );
		insert.addColumn( Script.UsuarioPrestador.LATITUDE );
		insert.addColumn( Script.UsuarioPrestador.LONGITUDE );
		insert.addColumn( Script.UsuarioPrestador.RAIO );
		
		super.inserir( insert );
	}
	
	@Override
	public void setDados(PreparedStatement stmt) throws SQLException, Exception {
		stmt.setDouble(1, getUsuarioPrestador().getLocal().getLatitude()   );
		stmt.setDouble(2, getUsuarioPrestador().getLocal().getLongitude()  );
		stmt.setInt(   3, getUsuarioPrestador().getRaio()	 	);
	}

	public String getInnerJoin() {
		return StringSql.WhereUtils.getInnerJoin( Script.Usuario.NAMETABLE  , Script.UsuarioPrestador.NAMETABLE , Script.Usuario.ID , Script.UsuarioPrestador.ID );
	}
	
	public void buscar(int idUsuario) throws Exception {
		if( usuarioPrestadorIsNull() )
			setUsuario( new UsuarioPrestador() );
		
		String where = 
				getIdIdentifiedByNameTable() + " = " + idUsuario;
		buscar( getInnerJoin() , where );
	}
	
	public String getIdIdentifiedByNameTable() {
		return StringSql.WhereUtils.appendNameTableWithColumn( getNameTable() , Script.UsuarioPrestador.ID);
	}

	public Collection<UsuarioPrestador> buscarProximos(Local local) throws Exception {
		String where = StringSql.WhereUtils.getSelectStringOfCoordenadas( local ).toString();
		return buscar( getInnerJoin() , where , getIdIdentifiedByNameTable() + " asc", "20" );
	}
	
	private boolean usuarioPrestadorIsNull() {
		return usuario == null;
	}

	@Override
	public boolean getDados(ResultSet rs) throws SQLException, Exception {
		getUsuarioPrestador().getDados( rs );
		return false;
	}
	
	public void setUsuario(UsuarioPrestador usuario) {
		this.usuario = usuario;
	}

	public UsuarioPrestador getUsuarioPrestador() {
		return usuario;
	}

	@Override
	public Class<UsuarioPrestador> getClassTypeT() {
		return UsuarioPrestador.class;
	}

	@Override
	public String getNameTable() {
		return Script.UsuarioPrestador.NAMETABLE;
	}



}
