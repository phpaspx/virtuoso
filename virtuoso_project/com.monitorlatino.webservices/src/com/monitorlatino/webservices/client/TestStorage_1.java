package com.monitorlatino.webservices.client;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONObject;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import com.monitorlatino.webservices.Helper;
 
public class TestStorage_1 { 
	 
    public static void main(String[] args) {
        
        try {               
        	//String sJSON = "{\"url\": \"http://s3mant1c.com/ontologies/cfdi.owl\" }";
        	String sJSON = "{\"url\": \"http://52.25.37.139/ref.xml\" }";
            JSONObject jsonObject = new JSONObject(sJSON);
                                 
            try {
            	
                //URL url = new URL("http://127.0.0.1:8080/com.monitorlatino.webservices/rest/clsparam");
                URL url = new URL("http://52.9.34.151:8080/com.monitorlatino.webservices/rest/clswsstorage");
                                   
                URLConnection connection = url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setConnectTimeout(20000);
                connection.setReadTimeout(20000);
               
                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                out.write(jsonObject.toString());
                out.close();
 
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
 
                System.out.println(in.readLine().toString());
                in.close();
            } catch (Exception e){System.out.println(e.toString());}
            
            
        } catch (Exception e) {
        	e.printStackTrace();
        } 
            
      
    }
}

