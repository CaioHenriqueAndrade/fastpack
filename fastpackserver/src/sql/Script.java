package sql;

public class Script {

	public static class Usuario {
		public static final String NAMETABLE 	= "usuario";
		public static final String ID			= "id";
		public static final String STATUS		= "status";
		public static final String TIPO			= "tipo";
		public static final String CPF			= "cpf";
		public static final String PASSWORD		= "password";
	}
	public static class UsuarioPrestador {
		public static final String NAMETABLE 	= "usuarioprestador";
		public static final String ID			= "id";
		public static final String LATITUDE		= "latitude";
		public static final String LONGITUDE	= "longitude";
		public static final String RAIO			= "raio";
	}
	
	public static class Pedido {
		public static final String NAMETABLE		="pedido";
		public static final String IDPEDIDO			="id";
		public static final String IDPRESTADOR		="idprestador";
		public static final String IDADDRESSBUSCA	="idaddressbusca";
		public static final String IDADDRESSENTREGA	="idaddressentrega";
		public static final String VALOR			="valor";
		public static final String STATUS			="status";
		public static final String DESCPEDIDO		="descpedido";
		public static final String HORAPOSTADO		="horapostado";
		public static final String HORAPRAZO		="horaprazo";
		public static final String HORARECEBIDO		="horarecebido";
	}
	public static class Address{
	    public static final String NAMETABLE 		= "Address";
	    public static final String ID 				= "id";
	    public static final String STREET 			= "street";
	    public static final String STREET_NUMBER 	= "streetn";
	    public static final String BAIRRO 			=  "neighborhood";
	    public static final String CIDADE 			= "city";
	    public static final String ESTADO 			= "state";
	    public static final String CEP 				= "zipcod";
	    public static final String PAIS 			= "country";
	    public static final String LATITUDE 		= "latitude";
	    public static final String LONGITUDE 		= "longitude";
	    public static final String COMPLEMENTARY 	= "complementary";
	    public static final String IDCRIADOR		= "idCriador";
	}
		
		
}
	

