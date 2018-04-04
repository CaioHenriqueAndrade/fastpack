package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import interfaces.Interfaces;
import objetos.Pedido;
import sql.Script;
import sql.StringSql;

public class ModelPedido extends BaseObjectMySql<Pedido> {

		private Pedido pedido;
		
		public ModelPedido(Pedido pedido) {
			this.pedido = pedido;
		}
		public ModelPedido() { }
		
		@Override
		public void setDados(PreparedStatement stmt) throws SQLException, Exception {
			stmt.setString(	1, pedido.getDescPedido());
			//stmt.setString(	2, pedido.getHoraPostado());
			stmt.setString(	2, pedido.getHoraPrazo());
			//stmt.setString(	3, pedido.getHoraRecebido());
			stmt.setInt(    3, pedido.getIdAddressBusca() );
			stmt.setInt(	4, pedido.getIdAddressEntrega());
			//stmt.setInt(	6, pedido.getIdPrestador());
			stmt.setInt(	5, pedido.getStatus());
			stmt.setInt(	6, pedido.getValor());
			stmt.setInt(	7, pedido.getIdUser() );

		}

		@Override
		public String getNameTable() {
			// TODO Auto-generated method stub
			return Script.Pedido.NAMETABLE;
		}

		@Override
		public boolean getDados(ResultSet rs) throws SQLException, Exception {
			return pedido.getDados( rs );
		}

		public Pedido getPedido() {
			return pedido;
		}
		

		public void inserir(boolean needValidate) throws Exception  {
			
			
			StringSql.Insert insertSql = new StringSql.Insert( getNameTable() );
			insertSql.addColumn( Script.Pedido.DESCPEDIDO);
		//	insertSql.addColumn( Script.Pedido.HORAPOSTADO);
			insertSql.addColumn( Script.Pedido.HORAPRAZO);
			//insertSql.addColumn( Script.Pedido.HORARECEBIDO);
			insertSql.addColumn( Script.Pedido.IDADDRESSBUSCA);	
			insertSql.addColumn( Script.Pedido.IDADDRESSENTREGA);
			//insertSql.addColumn( Script.Pedido.IDPRESTADOR);
			insertSql.addColumn( Script.Pedido.STATUS);	
			insertSql.addColumn( Script.Pedido.VALOR);	
			insertSql.addColumn( Script.Pedido.IDUSER);
			
			
			super.inserir( insertSql );	
		}

		@Override
		public Class<Pedido> getClassTypeT() {
			return Pedido.class;
		}
		public Collection<Pedido> buscaBy(String idUser, String ultimoCarregado, boolean isUser) throws Exception {
			String where;
			if(isUser) {
				where = Script.Pedido.IDUSER + " = " + idUser;	
			} else {
				where = Script.Pedido.IDPRESTADOR + " = " + idUser;
			}
			
			if( !ultimoCarregado.equals("") && !ultimoCarregado.equals("0") ) {
				where += " and " + Script.Pedido.IDPEDIDO + " < " + ultimoCarregado;
			}
			
			return buscar( where , " id desc " , "20" );
		}
}
