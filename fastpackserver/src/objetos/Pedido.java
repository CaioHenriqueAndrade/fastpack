package objetos;

import java.sql.ResultSet;
import java.sql.SQLException;

import sql.Script;

public class Pedido extends ObjectBasic {
	private int idUser , idPrestador, idAddressBusca, idAddressEntrega, valor, status;
	private String descPedido, horaPostado, horaPrazo, horaRecebido;
	
	//Quando o pedido e criado e chega ao servidor
	public static final int STATUS_JUST_ENVIADO = 0;
	
	//quando esta aguardando a entrega, ja selecionado quem ira realizar
	public static final int STATUS_AGUARDE_ENTREGA = 1;
	
	//quando ja esta entregue
	public static final int STATUS_ENTREGUE		= 2;
	
	//quando apagado/cancelado
	public static final int STATUS_APAGADO		= 3;

	//sobre os comentarios que serao realizados apos a finalizacao do pedido,
	public static final int COMENTARIO_NENHUM 		= 0;
	public static final int COMENTARIO_USER_NORMAL  = 1;
	public static final int COMENTARIO_PRESTADOR  	= 2;
	public static final int COMENTARIO_OK  			= 3;
	
	private Address addressEntrega, addressRetirada;
	
	@Override
	public boolean getDados(ResultSet rs) throws SQLException, Exception {
		setId(				rs.getInt(Script.Pedido.IDPEDIDO ) );
		setIdUser(			rs.getInt(Script.Pedido.IDUSER ) );
		setIdPrestador( 	rs.getInt(Script.Pedido.IDPRESTADOR ) );
		setIdAddressBusca(	rs.getInt(Script.Pedido.IDADDRESSBUSCA ) );
		setIdAddressEntrega(rs.getInt(Script.Pedido.IDADDRESSENTREGA));
		setValor(			rs.getInt(Script.Pedido.VALOR));
		setStatus(			rs.getInt(Script.Pedido.STATUS));
		setDescPedido(		rs.getString(Script.Pedido.DESCPEDIDO));
		setHoraPostado(		rs.getString(Script.Pedido.HORAPOSTADO));
		setHoraPrazo(		rs.getString(Script.Pedido.HORAPRAZO));
		setHoraRecebido(	rs.getString(Script.Pedido.HORARECEBIDO));
		return true;
	}

	public boolean existsId() {
		return getId() != 0;
		
	}
	public int getIdPrestador() {
		return idPrestador;
	}
	public void setIdPrestador(int idPrestador) {
		this.idPrestador = idPrestador;
	}
	public int getIdAddressBusca() {
		return idAddressBusca;
	}
	public void setIdAddressBusca(int id) {
		this.idAddressBusca = id;
	}
	public int getIdAddressEntrega() {
		return idAddressEntrega;
	}
	public void setIdAddressEntrega(int idAddressEntrega) {
		this.idAddressEntrega = idAddressEntrega;
	}
	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDescPedido() {
		return descPedido;
	}
	public void setDescPedido(String descPedido) {
		this.descPedido = descPedido;
	}
	public String getHoraPostado() {
		return horaPostado;
	}
	public void setHoraPostado(String horaPostado) {
		this.horaPostado = horaPostado;
	}
	public String getHoraPrazo() {
		return horaPrazo;
	}
	public void setHoraPrazo(String horaPrazo) {
		this.horaPrazo = horaPrazo;
	}
	public String getHoraRecebido() {
		return horaRecebido;
	}
	public void setHoraRecebido(String horaRecebido) {
		this.horaRecebido = horaRecebido;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public Address getAddressEntrega() {
		return addressEntrega;
	}

	public void setAddressEntrega(Address addressEntrega) {
		this.addressEntrega = addressEntrega;
	}

	public Address getAddressRetirada() {
		return addressRetirada;
	}

	public void setAddressRetirada(Address addressRetirada) {
		this.addressRetirada = addressRetirada;
	}


	
}
