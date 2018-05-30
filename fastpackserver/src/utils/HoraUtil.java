package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HoraUtil {

	public HoraUtil() { }

	public static String getHoje() {
		SimpleDateFormat sdff = new SimpleDateFormat("dd/MM/yyyy");
		return (sdff.format(new Date()));
	}

}
