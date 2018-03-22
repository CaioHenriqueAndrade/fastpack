package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import objetos.Pedido;
import objetos.Usuario;
import sql.Script;
import sql.StringSql;

public class ModelPedido extends BaseObjectMySql {

		private Pedido pedido;
		
		@Override
		public void setDados(PreparedStatement stmt) throws SQLException, Exception {
			stmt.setString(	1, pedido.getDescPedido());
			stmt.setString(	2, pedido.getHoraPostado());
			stmt.setString(	3, pedido.getHoraPrazo());
			stmt.setString(	4, pedido.getHoraRecebido());
			stmt.setInt(	5, pedido.getIdAddressEntrega());
			stmt.setInt(	6, pedido.getIdPedido());
			stmt.setInt(	7, pedido.getIdPrestador());
			stmt.setInt(	8, pedido.getStatus());
			stmt.setInt(	9, pedido.getValor());
		}

		@Override
		public String getNameTable() {
			// TODO Auto-generated method stub
			return Script.Pedido.NAMETABLE;
		}

		@Override
		public boolean getDados(ResultSet rs) throws SQLException, Exception {
			// TODO Auto-generated method stub
			return false;
		}

		public Pedido getPedido() {
			return pedido;
		}
		

		public void inserir() throws Exception  {
			StringSql.Insert insertSql = new StringSql.Insert( getNameTable() );
			insertSql.addColumn( Script.Pedido.DESCPEDIDO);
			insertSql.addColumn( Script.Pedido.HORAPOSTADO);
			insertSql.addColumn( Script.Pedido.HORAPRAZO);
			insertSql.addColumn( Script.Pedido.HORARECEBIDO);
			insertSql.addColumn( Script.Pedido.IDADDRESSBUSCA);	
			insertSql.addColumn( Script.Pedido.IDADDRESSENTREGA);
			insertSql.addColumn( Script.Pedido.IDPEDIDO);
			insertSql.addColumn( Script.Pedido.IDPRESTADOR);
			insertSql.addColumn( Script.Pedido.STATUS);	
			insertSql.addColumn( Script.Pedido.VALOR);	
			
			
			super.inserir( insertSql );	
		}
}
