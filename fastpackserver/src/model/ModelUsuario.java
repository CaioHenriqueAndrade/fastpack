package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exception.IllegalLoginException;
import objetos.Usuario;
import sql.Script;
import sql.StringSql;

public class ModelUsuario extends BaseObjectMySql<Usuario> {

	private Usuario usuario;
	
	public ModelUsuario() {}
	public ModelUsuario(Usuario usuario) { this.usuario = usuario; }
	
	@Override
	public void setDados(PreparedStatement stmt) throws SQLException, Exception {
		stmt.setString(	1, usuario.getCpf( ));
		stmt.setString(	2, usuario.getPassword());
		stmt.setInt(	3, usuario.getStatus());
		stmt.setInt(	4, usuario.getTipo());

	}

	@Override
	public String getNameTable() {
		return Script.Usuario.NAMETABLE;
	}

	@Override
	public boolean getDados(ResultSet rs) throws SQLException, Exception {
		usuario.getDados( rs );
		return true; 
	}


	public synchronized void inserir( boolean needValidar ) throws Exception  {
		
		if( needValidar ) {
			if(!isPossivelInserir()) {
				throw new IllegalLoginException("already exists this account ");
			}
		}
		
		StringSql.Insert insertSql = new StringSql.Insert( getNameTable() );
		insertSql.addColumn( Script.Usuario.CPF);
		insertSql.addColumn( Script.Usuario.PASSWORD);
		insertSql.addColumn( Script.Usuario.STATUS);
		insertSql.addColumn( Script.Usuario.TIPO);	
		
		super.inserir( insertSql );	
		
	}

	@Override
	public Class<Usuario> getClassTypeT() {
		return Usuario.class;
	}
	
	//busca o usuario e devolve se existe
	//se nao existe gera um erro IllegalLoginException
	public Usuario tryBuscarUsuario(String cpf, String senha) throws Exception,IllegalLoginException  {
		//setando o usuario no model para receber as informacoes
		//novo usuario informacoes zeradas
		setUsuario( new Usuario() );
		
		//pegando o where de cpf e senha que chegaram
		
		//E buscando na base
		buscar( getWhereByCpfSenha( cpf , senha ) );

		
		
		//se buscou e deu tudo certo aqui o usuario recebeu os dados
		
		//vamos ver se ele recebeu dados
		if( !getUsuario().foiBuscadoComSucessoNaBase() ) {
			throw new IllegalLoginException("Nao foi encontrado esse usuario na base de dados ");
		}
		
		return getUsuario();
	}
	public boolean isPossivelInserir() throws Exception {
		ModelUsuario model = new ModelUsuario( new Usuario() );
		model.buscar( getWhereByCpf( usuario.getCpf() ).toString() );
		
		return !model.getUsuario().foiBuscadoComSucessoNaBase();
	}
	public String getWhereByCpfSenha(String cpf, String senha) {
		return getWhereByCpf(cpf).append("and ").append( Script.Usuario.PASSWORD ).append(" = '")
				.append( senha ).append("'").toString();
	}
	
	public StringBuilder getWhereByCpf(String cpf) {
		return new StringBuilder().append( Script.Usuario.CPF).append(" = '")
				.append( cpf ).append("' ");
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	

	
	
	
}
