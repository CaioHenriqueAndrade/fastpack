package objetos;

import java.sql.ResultSet;
import java.sql.SQLException;

import sql.Script;

public class Usuario extends ObjectBasic {

	public static final int RESPONSE_LOGIN_ERROR = 473;
	public static final int TIPO_PRESTADOR = 1;
	public static final int TIPO_NORMAL    = 0;

	
	
	private int tipo, status;
	private String cpf, password;
	
	private Local local;
	
	public Usuario(Usuario usuario) {
		usuario.setId( usuario.getId() );
		usuario.setTipo(usuario.getTipo() );
		usuario.setStatus(usuario.getStatus());
		usuario.setCpf(usuario.getCpf());
		usuario.setPassword(usuario.getPassword());
	}
	public Usuario() {}

	@Override
	public boolean getDados(ResultSet rs) throws SQLException, Exception {
		setId( 	rs.getInt(    Script.Usuario.ID    ) );
		setTipo(	rs.getInt(    Script.Usuario.TIPO  ) );
		setStatus(	rs.getInt(    Script.Usuario.STATUS) );
		setCpf(     rs.getString( Script.Usuario.CPF   ) );

		setPassword(rs.getString( Script.Usuario.PASSWORD));
		setLocal (new Local()  );
		getLocal().setLatitude(rs get );
		return true;
	}

	public boolean foiBuscadoComSucessoNaBase() {
		return getId() != 0;
	}
	
	public boolean userIsPrestador() {
		return getTipo() == TIPO_PRESTADOR;
	}
	public boolean userIsComum() {
		return getTipo() == TIPO_NORMAL;
	}	
	
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Local getLocal() {
		return local;
	}
	public void setLocal(Local local) {
		this.local = local;
	}


	
	
}
