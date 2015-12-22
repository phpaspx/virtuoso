<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.io.OutputStreamWriter" %>
<%@ page import="java.net.ServerSocket" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.net.URLConnection" %>
<%@ page import="org.json.*" %>
<%@ page import="java.io.*" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Virtuoso Insert</title>
</head>
<body>

<% 
/*
try {               	     
	    
*/
  %>

    <% 
    	String sResult = "";
        if(request.getParameter("buttonName") != null) {
        	try {                                   	
        		
        		//String sJSON = "{\"url\": \"http://52.25.37.139/ref.xml\" }"; 
        		String sJSON = "";
        		
        		String sIsOWL = "false"; 
        		String sURL = request.getParameter("buttonName");
        		
        		if(request.getParameter("txtOnLine") != null) {
        			  String sOWL = request.getParameter("txtOnLine");
        			  sOWL = sOWL.replace('"', '^');
        			  sOWL = sOWL.replace("\n", "").replace("\r", "");
        			  if (!sOWL.trim().equals("")) {
        			  	  sIsOWL = "true";
        			   	  sURL = sOWL;
        			  }
        			  sResult += "Enviando OWL o RDF: " + sOWL +"<br>" ;
        		}   
        		
        		sJSON = "{\"url\": \""+sURL+"\",\"isowl\":\""+sIsOWL+"\" }";  			  
        		JSONObject jsonObject = new JSONObject(sJSON);
                                     
                try {
                	 
                    URL url = new URL("http://52.9.34.151:8080/com.monitorlatino.webservices/rest/clswsstorage");
                    URLConnection connection = url.openConnection();
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setConnectTimeout(20000);
                    connection.setReadTimeout(20000);
                   
                    OutputStreamWriter outInject = new OutputStreamWriter(connection.getOutputStream());
                    outInject.write(jsonObject.toString());
                    outInject.close();
     
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            connection.getInputStream()));
     
                    sResult += "Insert to virtuoso response: <br>";
                    sResult += in.readLine().toString();
                    System.out.println(in.readLine().toString());
                    in.close();
                } catch (Exception e){
                	System.out.println(e.toString());
                }            		 
        		                
            } catch (Exception e) {
            	e.printStackTrace();
            }  
        
    %>
             <h2></h2><p><center><table border="2"><tr><td>(<%= sResult %>)</td></tr>></table></center></p>
             
    <%
        } else {
    %>
             <center><h2>Web Service Client to Virtuoso </h2></center><p></p>
    <%
        }
    %>


<center>
  <table bgcolor="#8a9597">
  <tr>
      <td>
	    <FORM NAME="form1" METHOD="POST">
	         
	        <INPUT TYPE="HIDDEN" NAME="buttonName">
	        <br><b> Plase enter URL example: http://52.25.37.139/vcard2.xml</b><br>
	        <INPUT TYPE="text" NAME="txtUrl" value="http://52.25.37.139/vcard2.xml" size=70><br>	        
			<textarea name="txtOnLine"   cols="70" rows="20" ></textarea><br>	       
			 
	        <INPUT TYPE="BUTTON" VALUE="Read RDF" ONCLICK="button1()"> <p>
	        <center>
		        <table border=1>
		          <tr>
		          	<td>
		        		<small>
				           <b>This JPS is using the Storage Web Service in s3mant1cs 002:</b> <br>
					       http://52.9.34.151:8080/com.monitorlatino.webservices/rest/clsparam <p><p>
					       
					       <b>JSON with a single param called "url":</b><br> 
				           {"url": "http://52.25.37.139/vcard2.xml" } <br>
				        </small>  	
		          	</td>
		          </tr>
		        </table>
	        </center>
	    </FORM>
	
	    <SCRIPT LANGUAGE="JavaScript">
	        <!--
	        function button1()
	        {
	            document.form1.buttonName.value = document.form1.txtUrl.value;
	            form1.submit();
	        } 
	        // --> 
	    </SCRIPT>
      </td>
    </tr>
  </table>
</center>  
    
</body>
</html>