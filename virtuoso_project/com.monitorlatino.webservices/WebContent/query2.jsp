<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.io.OutputStreamWriter" %>
<%@ page import="java.net.ServerSocket" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.net.URLConnection" %>
<%@ page import="org.json.*" %>

<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>

<%@ page import="javax.ws.rs.Consumes" %>
<%@ page import="javax.ws.rs.POST" %>
<%@ page import="javax.ws.rs.Path" %>
<%@ page import="javax.ws.rs.core.MediaType" %>

<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="org.json.JSONObject" %>

<%@ page import="com.hp.hpl.jena.graph.Node" %>
<%@ page import="com.hp.hpl.jena.graph.Triple" %>
<%@ page import="com.hp.hpl.jena.query.*" %>
<%@ page import="com.hp.hpl.jena.rdf.model.Model" %>
<%@ page import="com.hp.hpl.jena.rdf.model.ModelFactory" %>
<%@ page import="com.hp.hpl.jena.rdf.model.Property" %>
<%@ page import="com.hp.hpl.jena.rdf.model.RDFNode" %>
<%@ page import="com.hp.hpl.jena.rdf.model.Resource" %>
<%@ page import="com.hp.hpl.jena.rdf.model.Statement" %>
<%@ page import="com.hp.hpl.jena.rdf.model.StmtIterator" %>
<%@ page import="com.hp.hpl.jena.util.FileManager" %>

<%@ page import="virtuoso.jena.driver.*" %> 
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>

<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
    
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
        if(true) {
        	Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

        	try {               
            	
        		///  Before open connection to virtuoso 
                ///----------------------------------------------------------------------------------------
                cal = Calendar.getInstance();
                sResult += "<center><table border=\"1\">";
                sResult += "Connecting to virtuoso ..."+ sdf.format(cal.getTime()) + "<br>";
        		VirtGraph graph = new VirtGraph ("jdbc:virtuoso://s3mant1c.com:8080", "dba", "Passw0rd1");
        		    		 
        		cal = Calendar.getInstance();
        		sResult += "Connected to virtuoso ..." + sdf.format(cal.getTime()) + "<br>";
        		
        		Query sparql = QueryFactory.create("SELECT * FROM <commercial> WHERE { ?s ?p ?o }");
        		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (sparql, graph);
        		
        		cal = Calendar.getInstance();
        		sResult += "Preparing query ..." + sdf.format(cal.getTime()) + "<br>";
        		sResult += "</table></center><br><br>";
        		
        		sResult += "<center><table border=1>";
        		sResult += "<tr> <td>Fila</td><td><b>Subject</b></td> <td><b>Predicate</b></td> <td><b>Object</b></td> </tr>";
    			ResultSet results = vqe.execSelect();
    			int iCount = 1;
    			while (results.hasNext()) {
    				QuerySolution result = results.nextSolution();
    			    RDFNode rdfNode = result.get("graph");
    			    RDFNode s = result.get("s");
    			    RDFNode p = result.get("p");
    			    RDFNode o = result.get("o");
    			    System.out.println(rdfNode + " { " + s + " " + p + " " + o + " . }");
    			    
    			    sResult += "<tr> <td>"+ String.valueOf(iCount) +"</td> <td>" + s + "</td> <td>" + p + "</td> <td>" + o + "</td> </tr>";
    			    cal = Calendar.getInstance();
    			    iCount++;
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
 

</body>
</html>