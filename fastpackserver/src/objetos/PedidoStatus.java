package objetos;

public class PedidoStatus {
	private int idUser = 0;
    private int idPrestador = 0;
	private int newStatus, idPedido;
	
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public int getIdPrestador() {
		return idPrestador;
	}
	public void setIdPrestador(int idPrestador) {
		this.idPrestador = idPrestador;
	}
	public int getNewStatus() {
		return newStatus;
	}
	public void setNewStatus(int newStatus) {
		this.newStatus = newStatus;
	}
	public int getIdPedido() {
		return idPedido;
	}
	public boolean isValido() {
		return idUser != 0 && (newStatus == Pedido.STATUS_APAGADO || newStatus == Pedido.STATUS_AGUARDE_ENTREGA || newStatus == Pedido.STATUS_ENTREGUE);
	}
	
	
	
}
	    
