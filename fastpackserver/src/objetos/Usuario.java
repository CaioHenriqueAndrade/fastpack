package objetos;

import java.sql.ResultSet;
import java.sql.SQLException;

import sql.Script;

public class Usuario extends ObjectBasic {

	private int idUser, tipo, status;
	private String cpf, password;
	
	@Override
	public boolean getDados(ResultSet rs) throws SQLException, Exception {
		setIdUser( 	rs.getInt(    Script.Usuario.ID    ) );
		setTipo(	rs.getInt(    Script.Usuario.TIPO  ) );
		setStatus(	rs.getInt(    Script.Usuario.STATUS) );
		setCpf(     rs.getString( Script.Usuario.CPF   ) );
		setPassword(rs.getString( Script.Usuario.PASSWORD));
		
		return true;
	}
	
	
	
	
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
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

	
	
}
