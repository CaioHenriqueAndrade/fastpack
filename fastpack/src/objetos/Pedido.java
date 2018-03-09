package objetos;


public class Pedido {
	private int idPedido, idPrestador, idAddressBusca, idAddressEntrega, valor, status;
	private String descPedido, horaPostado, horaPrazo, horaRecebido;
	
	private Address address;	
	
	public int getIdPedido() {
		return idPedido;
	}
	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}
	public int getIdPrestador() {
		return idPrestador;
	}
	public void setIdPrestador(int idPrestador) {
		this.idPrestador = idPrestador;
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
}
