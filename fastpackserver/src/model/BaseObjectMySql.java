package model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import interfaces.Interfaces;
import sql.SqlUtils;
import sql.StringSql;

public abstract class BaseObjectMySql<T> implements Interfaces.ObjectMethods<T> ,  Interfaces.GetDados<T> {

	//Interfaces.GetDados<T> getDados;
	public BaseObjectMySql(Interfaces.GetDados<T> getDados) {
		//this.getDados = getDados;
	}

	public BaseObjectMySql() {
		
	}
	
	@Override
	public void buscar(String where) throws Exception {
		buscar( null , where );
	}

	@Override
	public void buscar(String inners, String where) throws Exception {
		StringBuilder select = getSelectBasic();
		if( inners != null) {
			select.append( inners );
		}
		select.append(" where ").append( where ).toString();
		SqlUtils.buscar( select.toString() , this);
	}
	
	@Override
	public Collection<T> buscar(String where, String orderBy, String limit)
			throws Exception {
		return buscar( null , where, orderBy, limit );
	}
	
	@Override
	public Collection<T> buscar(String inners , String where, String orderBy, String limit) throws Exception {
		StringBuilder select = getSelectBasic();
		
		if(inners != null) {
			select.append( inners );
		}
		
		if(where != null) {
			select.append(" where ").append(where);
		}
		
		
		
		select.append(" order by ").append(orderBy).toString();
		
		if( limit != null) {
			select.append(" limit ").append( limit );
		}

		List<T> collection = new ArrayList<>();
		SqlUtils.buscar( select.toString() , new Interfaces.JustGetDados() {

			@Override
			public boolean getDados(ResultSet rs) throws Exception {
				// TODO Auto-generated method stub
				T classe = null;

				// aqui pode dar illegalAcessException se nao tiver construtor default
				try {
					//classe = (T) classe.getClass().newInstance();
					classe = getClassTypeT().newInstance();
					
					switch( BaseObjectMySql.this.getDados(classe, rs) ) {
					
					case ADD_COLECTION:
						collection.add(classe);
						break;
					case BREAK_LOOP:
						return false;
						
					case NO_ADD_COLECTION:
					}
					
							
					

					
				} catch (InstantiationException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return true;
			}
		});

		return collection;

	}
	
	@Override
	public int getDados(T instanceOfObject, ResultSet rs) throws Exception {
		((Interfaces.JustGetDados) instanceOfObject ).getDados( rs );
		return ADD_COLECTION;
	}
	
	public static StringBuilder getSelectBasic(String limit, String nameTable) {
		return new StringBuilder().append(SqlUtils.getSelectBasic(limit)).append(" * from " + nameTable).append(" ");
	}
	
	
	@Override
	public Object inserir(StringSql.Insert insertSql) throws Exception {
		// TODO Auto-generated method stub
		SqlUtils.inserir(insertSql, this );
		return SqlUtils.returnIdUltimoInBD( "id" , getNameTable() );
		
	}
	
	public StringBuilder getSelectBasic() {
		return new StringBuilder().append( "select * from " ).append( getNameTable() ).append(" ");
	}


	public static final int ADD_COLECTION = 0;
	public static final int NO_ADD_COLECTION = 1;
	public static final int BREAK_LOOP = 2;
}

