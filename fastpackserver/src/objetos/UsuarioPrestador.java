package objetos;

import java.sql.ResultSet;

import sql.Script;

public class UsuarioPrestador extends Usuario {
	private int raio;
    private int precoMedio;

	public UsuarioPrestador(Usuario usuario) {
		super( usuario );
	}
	public UsuarioPrestador() {
		
	}
	
	
	@Override
	public boolean getDados(ResultSet rs) throws Exception {
		setRaio( 	rs.getInt( Script.UsuarioPrestador.RAIO ) );
		setPrecoMedio( rs.getInt(Script.UsuarioPrestador.PRECOMEDIO ) );
		return super.getDados( rs );
	}
	
	public int getRaio() {
		return raio;
	}
	public void setRaio(int raio) {
		this.raio = raio;
	}
	public int getPrecoMedio() {
		return precoMedio;
	}
	public void setPrecoMedio(int precoMedio) {
		this.precoMedio = precoMedio;
	}



}
