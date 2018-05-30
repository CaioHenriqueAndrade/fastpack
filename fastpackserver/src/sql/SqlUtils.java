package sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import interfaces.Interfaces;

public class SqlUtils {

	public SqlUtils() {
		// TODO Auto-generated constructor stub
	}

	public static void buscar(String select, Interfaces.JustGetDados callback) throws Exception {
		/*
		 * Busca o id do grupo no momento do cadastro...
		 */

		Connection con = new Conexao().getConnection();
		PreparedStatement stmt;
		stmt = con.prepareStatement(select);
		System.out.println(select);

		try {
			// executa um select
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				
				if (!callback.getDados(rs)) break;
			}
			rs.close();

		} catch (SQLException e) {
			stmt.close();
			con.close();
			throw new SQLException("Nao foi possivel buscar em select \n" + select, e);
		}
	}

	public static int returnIdUltimoInBD(Connection con, String nameEntity, String nameTable) throws Exception {
		int autoIncKeyFromFunc = -1;
		PreparedStatement stmt = con.prepareStatement("SELECT MAX(" + nameEntity + ") FROM " + nameTable);

		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			autoIncKeyFromFunc = rs.getInt(1);
		}
		rs.close();
		return autoIncKeyFromFunc;
	}

	public static int returnIdUltimoInBD(String nameEntity, String nameTable) throws SQLException,Exception, ConnectionSQLException {
		int autoIncKeyFromFunc = -1;
		Connection con = new Conexao().getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT MAX(" + nameEntity + ") FROM " + nameTable);

		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			autoIncKeyFromFunc = rs.getInt(1);
		}
		con.close();
		rs.close();
		return autoIncKeyFromFunc;
	}

	public static int returnIdUltimoInBD1(Connection con, String nameEntity, String nameTable) throws Exception {
		// entity is id, here is final

		PreparedStatement t = (PreparedStatement) con.createStatement();
		int autoIncKeyFromFunc = -1;
		ResultSet rs = t.executeQuery("SELECT LAST_INSERT_ID() FROM " + nameTable);
		if (rs.next()) {
			autoIncKeyFromFunc = rs.getInt(1);
		}
		t.close();
		rs.close();
		return autoIncKeyFromFunc;
	}

	public static void alterar(String sql, Object... objects) throws Exception {
		/*
		 * Usamos este método para alterar o básico do usuário Geralmente o que ele pode
		 * alterar no perfil dele...
		 */

		Connection con = new Conexao().getConnection();

		try {
			System.out.println(sql);

			PreparedStatement stmt = con.prepareStatement(sql);
			for (int i = 0; i < objects.length; i++) {
				setPreparedStatement(i + 1, stmt, objects[i]);
			}

			// Executa a instrução
			stmt.executeUpdate();
		} catch (SQLException e) {
			con.close();
			throw new SQLException("Nao foi possivel alterar \n " + sql, e);
		} finally {
			con.close();

		}
	}

	public static void inserir(StringSql.Insert insertSql, Interfaces.GetDados<?> callback) throws Exception {

		Connection con = new Conexao().getConnection();
		String sql =  insertSql.build();

		System.out.println(sql);

		try {
			// cria um preparedStatement
				// preenche os valores
				PreparedStatement stmt = con.prepareStatement(sql);
				// preenche os valores
				callback.setDados(stmt);
				// executa
				stmt.execute();
				stmt.close();
		} catch(SQLException e) {
			con.close();
			throw new SQLException("Erro ao inserir " + sql , e);
			
		} finally {
			con.close();
		}

	}

	private static void setPreparedStatement(int numberArgument, PreparedStatement stmt, Object objeto)
			throws SQLException {
		
		if (objeto instanceof CharSequence || objeto instanceof String) {
			stmt.setString(numberArgument, (String) objeto);
		} else if (objeto instanceof Integer) {
			stmt.setInt(numberArgument, (int) objeto);
		} else if (objeto instanceof Float) {
			stmt.setFloat(numberArgument, (Float) objeto);
		} else if (objeto instanceof Double) {
			stmt.setDouble(numberArgument, (Double) objeto);
		} else {
			throw new IllegalArgumentException("not implements cast " + numberArgument + objeto.getClass().getName());
		}
	}

	public static StringBuilder getSelectBasic(String limit) {
		StringBuilder stringBuilder = new StringBuilder();
		// TODO Auto-generated method stub
		if (limit != null) {
			return stringBuilder.append("select top ").append(limit).append(" ");
		}

		return stringBuilder.append("select ");
	}

}
