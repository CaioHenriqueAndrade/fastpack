package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import objetos.Address;
import objetos.Local;
import objetos.Pedido;
import objetos.PedidoStatus;
import sql.Script;
import sql.SqlUtils;
import sql.StringSql;
import utils.HoraUtil;
import utils.UtilsConvert;

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
		stmt.setDouble( 8, pedido.getLocal().getLatitude() );
		stmt.setDouble( 9, pedido.getLocal().getLongitude());

	}

	@Override
	public String getNameTable() {
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
		inserirAllAddress();
		
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
		insertSql.addColumn( Script.Pedido.LATITUDE);
		insertSql.addColumn( Script.Pedido.LONGITUDE);

		
		super.inserir( insertSql );	
	}
	private void inserirAllAddress() throws Exception {
		
		ModelAddress modelAddress = new ModelAddress();
		
		modelAddress.setAddress( pedido.getAddressRetirada() );
		modelAddress.inserir();
		pedido.setIdAddressBusca( modelAddress.getAddress().getId() );
		modelAddress.setAddress( pedido.getAddressEntrega() );
		modelAddress.inserir();
		pedido.setIdAddressEntrega( pedido.getAddressEntrega().getId() );
		
	}
	private Collection<Pedido> getAllAddress(Collection<Pedido> pedidos) throws Exception {
		for(Pedido pedido : pedidos) {
			buscarAllAddress( pedido );
		}
		
		return pedidos;
	}
	
	private void buscarAllAddress(Pedido pedido) throws Exception {
		ModelAddress modelAddress = new ModelAddress();
		
		String where = Script.Address.ID + " = " + pedido.getIdAddressBusca();
		modelAddress.setAddress( new Address() );
		modelAddress.buscar( where );
		pedido.setAddressRetirada( modelAddress.getAddress() );
		
		where = Script.Address.ID + " = " + pedido.getIdAddressEntrega();
		modelAddress.setAddress( new Address() );
		modelAddress.buscar( where );
		pedido.setAddressEntrega( modelAddress.getAddress() );
		
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
		
		return getAllAddress( buscar( where , " id desc " , "20" ) ); 
	}
	
	public synchronized void alterar(PedidoStatus pedidoStatus) throws Exception {
		if( pedidoStatus.isValido() ) {
			
			//vamos buscar o pedido todo
			buscarPedido( pedidoStatus.getIdPedido() );
			
			//se ja esta no status certo, pode finalizar
			if( pedidoStatus.getNewStatus() == pedido.getStatus()
					&& pedidoStatus.getIdPrestador() == pedido.getIdPrestador() ) {
				return;
			}
			
			if( !getPedido().isPossivelModificarStatus( pedidoStatus.getNewStatus() , !(pedido.getIdUser() == pedidoStatus.getIdUser()) ) ) {
				throw new IllegalStateException("nao e possivel alterar o status nesta condicao");
			}
			
			String where = Script.Pedido.IDPEDIDO + " = " + pedido.getId();
			
			
			StringSql.Update up = new StringSql.Update( getNameTable() );
			up.add( Script.Pedido.STATUS );
			String script;
			
			if( pedidoStatus.getNewStatus() == Pedido.STATUS_APAGADO ) {
				script = up.build(where);
				SqlUtils.alterar(script, pedidoStatus.getNewStatus() );
				
			} else if(pedidoStatus.getNewStatus() == Pedido.STATUS_AGUARDE_ENTREGA ) {
				
				up.add( Script.Pedido.IDPRESTADOR );
				script = up.build(where);
				SqlUtils.alterar(script, pedidoStatus.getNewStatus() , pedidoStatus.getIdPrestador() );
				
			} else if(pedidoStatus.getNewStatus() == Pedido.STATUS_ENTREGUE) {
				script = up.build(where);
				SqlUtils.alterar( script , pedidoStatus.getNewStatus() );
			}
			
		}
		
	}
	
	public Collection<Pedido> buscarPedidos(Local local) throws Exception {
		String where = 
				Script.Pedido.STATUS + " = " + Pedido.STATUS_JUST_ENVIADO + " and " + 
				StringSql.WhereUtils.getSelectStringOfCoordenadas( local ).toString();
		
		where +=" and " + Script.Pedido.HORAPRAZO + " > '" + UtilsConvert.dateToDataMysql( HoraUtil.getHoje() ) + "'";

		return getAllAddress(buscar( where , "id desc" , "20"));
	}
	
		
	public void buscarPedido(int idPedido) throws Exception {
		setPedido( new Pedido() );
		String where = Script.Pedido.IDPEDIDO + " = " + idPedido;
		buscar( where );
	}
		 
	public static Pedido getPedido(int idUser, String idPedido) throws Exception {
		
		ModelPedido model = new ModelPedido( new Pedido() );
		String where = Script.Pedido.IDPEDIDO + " = " + idPedido;
		model.buscar( where );
		
		if( model.getPedido().getIdUser() != idUser &&
				model.getPedido().getIdPrestador() != idUser ) {
			throw new IllegalStateException("who try get this pedido?");
		}
		return model.pedido;
	}
		
		public Pedido getPedido(String idUsuario, String idPedido) throws Exception {
			String where = Script.Pedido.IDPEDIDO + " = " + idPedido;
			setPedido( new Pedido() );
			buscar( where );
			
			int idUser = Integer.parseInt( idUsuario );
			
			if(pedido.existsId() && (pedido.getIdUser() == idUser || 
									pedido.getIdPrestador() == idUser ) ) {
				return pedido;
			} else throw new IllegalArgumentException("this user not possible get pedido " + idUsuario );
		}
		public void setPedido(Pedido pedido) {
			this.pedido = pedido;
		}
		
}
