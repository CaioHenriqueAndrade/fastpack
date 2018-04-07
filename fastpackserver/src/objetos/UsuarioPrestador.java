package objetos;

import java.sql.ResultSet;

import sql.Script;

public class UsuarioPrestador extends Usuario {
	private int raio;
	private Local local;
	
	public UsuarioPrestador(Usuario usuario) {
		super( usuario );
	}
	public UsuarioPrestador() {
		
	}
	
	
	@Override
	public boolean getDados(ResultSet rs) throws Exception {
		setRaio( 	rs.getInt( Script.UsuarioPrestador.RAIO ) );
		
		local = new Local();
		
		getLocal().setLatitude(rs.getDouble( Script.UsuarioPrestador.LATITUDE ) );
		getLocal().setLongitude(rs.getDouble(Script.UsuarioPrestador.LONGITUDE) );
		
		return super.getDados( rs );
	}
	
	public int getRaio() {
		return raio;
	}
	public void setRaio(int raio) {
		this.raio = raio;
	}
	public Local getLocal() {
		return local;
	}
	public void setLocal(Local local) {
		this.local = local;
	}



}
