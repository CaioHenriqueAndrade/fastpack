package utils;

public class UtilsConvert {

	public UtilsConvert() { }
	
    public static String dateToDataMysql(String data) {
        String dias = data.substring(0,2);
        String mes = data.substring(3,5);
        String ano = data.substring(6,10);

        return ano + "-" + mes + "-" + dias + " 00:00";
    }

    public static String dataMysqlToData(String data) {
        String ano = data.substring(0,4);
        String mes = data.substring(5,7);
        String dias = data.substring(8,10);
        return dias + "/" + mes + "/" + ano+ " 00:00";
    }


}
