package zisac.com.pe.salutem24.utils;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StringUtils {
    public static boolean cadenaEsVacia(String cadena) {
        if (cadena == null) return true;
        if (cadena.isEmpty()) return true;
        if (cadena.trim().isEmpty()) return true;
        return false;
    }
    public static String mostrarddMMmmYYyy(){
        SimpleDateFormat formatter = new SimpleDateFormat(
                "dd/MM/yyyy", Locale.getDefault());
        Date curDate = new Date(System.currentTimeMillis());
        String ymdhms = formatter.format(curDate);
        //ymdhms = ymdhms.replace(" ", "*");
        return ymdhms;
    }

    public static String devuelveVacioString(String cadena){
        return cadena==null?"":((cadena.equals("") || cadena.equals("null"))?"":cadena);
    }

    public static boolean isNumeric(String strNum) {
        boolean ret = true;
        try {

            Double.parseDouble(strNum);

        }catch (NumberFormatException e) {
            ret = false;
        }
        return ret;
    }
}
