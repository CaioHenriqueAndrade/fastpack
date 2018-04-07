package objetos;

import java.sql.ResultSet;
import java.sql.SQLException;

import interfaces.Interfaces;
import sql.Script;

public class PedidoSolicitacao implements Interfaces.JustGetDados {
	
	private int idUserPrestador;
	private int idPedido;
	
	
	
	public int getIdUserPrestador() {
		return idUserPrestador;
	}
	public void setIdUserPrestador(int idUserPrestador) {
		this.idUserPrestador = idUserPrestador;
	}
	public int getIdPedido() {
		return idPedido;
	}
	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}
	
	@Override
	public boolean getDados(ResultSet rs) throws SQLException, Exception {
		idPedido 		= rs.getInt( Script.PedidoSolicitacao.IDPEDIDO );
		idUserPrestador = rs.getInt( Script.PedidoSolicitacao.IDPRESTADOR );
		return true;
	}
	
	
	
	

}
