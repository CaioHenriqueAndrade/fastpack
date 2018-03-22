package sql;

public class StringSql {

	public static class Insert {
		StringBuilder stringBuilder = new StringBuilder();
		int numberOfParams = 0;

		public Insert(String nameTable) {
			stringBuilder.append("insert into ").append(nameTable).append(" ( ");
		}

		public void addColumn(String nameColum) {
			numberOfParams++;
			
			if (numberOfParams != 1)
				stringBuilder.append(",").append(nameColum);
			else
				stringBuilder.append(nameColum);
			
		}

		public String build() {

			stringBuilder.append( " ) values ( ");
			for(int i = 0 ; i != numberOfParams ; i++) {
				if(i == 0)
						stringBuilder.append("?");
					else
						stringBuilder.append(",?");
			}
			return stringBuilder.append( " ) ").toString();
		}
	}
	public static class Update {

		private StringBuilder criandoTabela = new StringBuilder();

		public Update(String nameTable) {
			verifica = false;
			criandoTabela.append("UPDATE ").append( nameTable ).append(" SET ");

		}

		public void add(String nameAtributo) {

			if (verifica == false) {
				verifica = true;
				criandoTabela.append(" ").append( nameAtributo ).append(" = ? ");
			} else {
				criandoTabela.append(", ").append(nameAtributo).append(" = ? ");
			}

		}

		public String build(String where) {

			return criandoTabela.append(" where ").append(where).toString();

		}

		private boolean verifica = false;
	}
}
