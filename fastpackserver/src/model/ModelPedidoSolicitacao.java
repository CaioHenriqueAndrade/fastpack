package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import objetos.PedidoSolicitacao;
import sql.Script;
import sql.StringSql;

public class ModelPedidoSolicitacao extends BaseObjectMySql<PedidoSolicitacao>{

	private PedidoSolicitacao pedido;
	
	@Override
	public Class<PedidoSolicitacao> getClassTypeT() {
		return PedidoSolicitacao.class;
	}

	public void inserir() {
		StringSql.Insert insert = new StringSql.Insert( getNameTable() );
		insert.addColumn( Script.PedidoSolicitacao.IDPEDIDO    );
		insert.addColumn( Script.PedidoSolicitacao.IDPRESTADOR );
	}
	
	@Override
	public void setDados(PreparedStatement stmt) throws SQLException, Exception {
		stmt.setInt( 1 , getPedidoSol().getIdPedido() );
		stmt.setInt( 2 , getPedidoSol().getIdUserPrestador() );
	}

	@Override
	public String getNameTable() {
		return Script.PedidoSolicitacao.NAMETABLE;
	}

	@Override
	public boolean getDados(ResultSet rs) throws SQLException, Exception {
		return false;
	}
	
	public PedidoSolicitacao getPedidoSol() {
		return pedido;
	}

	

}
