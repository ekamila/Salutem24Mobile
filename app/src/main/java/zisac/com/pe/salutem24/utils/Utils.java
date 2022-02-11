package zisac.com.pe.salutem24.utils;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.protocol.HTTP;

public class Utils {
    public static boolean gpsEstaActivado(Context context){
        boolean gpsActivo = true;
        try {
            final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(context, "Ativar GPS", Toast.LENGTH_SHORT).show();
                gpsActivo = false;// Call your Alert message
            }
        }catch (Exception e){
            Log.e("ExceptionGpsActivo", "" + e.getMessage());
            gpsActivo = false;
        }
        return gpsActivo;
    }

    public static boolean redOrWifiActivo(Context context){
        boolean isConnected = true;
        try {
            /*ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();*/
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() != NetworkInfo.State.CONNECTED &&
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() != NetworkInfo.State.CONNECTED) {
                //we are connected to a network
                //Log.e("Red", "false");
                isConnected = false;
            }
        }catch (Exception e){
            isConnected = false;
            Log.e("ExceptionRedoWifi", "" + e.getMessage());
        }

        return isConnected;
    }


    public static String POST(String url, JSONObject object){
        InputStream inputStream;
        String result = "";
        try {
            //StringEntity se = new StringEntity(object.toString());
            StringEntity se = new StringEntity(object.toString(), HTTP.UTF_8);
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            HttpResponse httpResponse = httpclient.execute(httpPost);
            inputStream = httpResponse.getEntity().getContent();

            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "ERROR";
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    public String getValueStringOrNull(JSONObject objeto, String key){
        try {
            String resultado = (objeto.isNull(key) ? null : objeto.getString(key));

            if(resultado!=null) {
                if (resultado.equals("null")) {
                    resultado = null;
                }
            }

            return resultado;
        }catch(Exception e){
            //Log.e("ERROR",Log.getStackTraceString(e));
            return null;
        }
    }

    public JSONObject getJSONfromURL(String url){
        JSONObject json = null;
        try{
            String result = getResultConnectivity(url);
            if(result!=null) {
                if(!result.equals("")) {
                    json = new JSONObject(result);
                }
            }
        }catch(JSONException e){
            //Log.e("JSON", "Falló la conversión a JSONObject");
            //Log.e("ERROR",Log.getStackTraceString(e));
            return null;
        }

        return json;
    }
    public JSONArray getJSONArrayURL(String url){
        String result = getResultConnectivity(url);
        JSONArray objetos=null;

        try{
            if(result!=null) {
                if (!result.equals("")) {
                    objetos = new JSONArray(result);
                }
            }
        }catch(JSONException e){
            //Log.e("JSON", "Falló la conversión a JSONArray");
            //Log.e("ERROR",Log.getStackTraceString(e));
            return null;
        }
        return objetos;
    }

    public String getResultConnectivity(String url){
        InputStream is = null;
        String result = "";
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(Uri.encode(url, Constantes.ALLOWED_URI_CHARS));
            HttpResponse response = httpclient.execute(httpget);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
            }
        } catch (Exception e) {
            //Log.e("ERROR", Log.getStackTraceString(e));
            return null;
        }

        try{
            if(is!=null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                is.close();
                result = sb.toString();
            }
        } catch(Exception e){
            //Log.e("JSON", "Falló la descarga de archivo json");
            //Log.e("ERROR",Log.getStackTraceString(e));
            return null;
        }
        return result;
    }
}
