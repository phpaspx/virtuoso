package com.monitorlatino.webservices;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;

import virtuoso.jena.driver.*; 
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/*
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.NoHostAvailableException;
import com.datastax.driver.core.exceptions.QueryExecutionException;
import com.datastax.driver.core.exceptions.QueryValidationException;
import com.monitorlatino.webservices.Helper;
import com.monitorlatino.webservices.Hit;
*/

@Path("/clswsstorage")
public class ClsWsStorage {
	static final Logger InfoLogger = Logger.getLogger("infoLogger");
	static final Logger ErrorLogger = Logger.getLogger("errorLogger");
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String SaveOntolooy(InputStream incomingData  ) {
    	String output = "";
        StringBuilder builder = new StringBuilder();
       
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        try { 	
            output += "Web servce Transaction 18 dic 10:33  start..." + sdf.format(cal.getTime()) +"<br>";
        	 
        	BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String line = null;
            while ((line = in.readLine()) != null) {
            	builder.append(line);	
            }
            
            cal = Calendar.getInstance();
            output += "Json stream readed: " + sdf.format(cal.getTime()) + "<br>";  
            
            JSONObject jsonObject = new JSONObject(builder.toString());             
            String sUrl = jsonObject.getString("url") ;
            String sIsOWL = jsonObject.getString("isowl") ;
            
            sUrl = sUrl.replace('^','"');
		   
            output += "sUrl: " + sUrl + " <br>";
            output += "sIsOWL: " + sIsOWL + " <br>";
            
            cal = Calendar.getInstance();
            output += "| sUrl = " + sUrl + sdf.format(cal.getTime()) + "<br>";

            ///  Try open file manager
            ///----------------------------------------------------------------------------------------           
            output += " |Before reading URL ";
            
            InputStream inUrl;
            if (sIsOWL.trim().equalsIgnoreCase("true")) { 
            	inUrl = new ByteArrayInputStream(sUrl.getBytes());
            	
            	output += " ByteArrayInputStream to inUrl <br>";
            } else {            	
            	inUrl = FileManager.get().open(sUrl);
	            if (inUrl == null) {
	            	output += "URL: " + sUrl + " not found";
	                throw new IllegalArgumentException( "URL: " + sUrl + " not found");
	            }
	            cal = Calendar.getInstance();
	            output += " |URL readed owl or RDF: " + sUrl + " " + sdf.format(cal.getTime()) + "<br>";
            }
             
            /// create an empty model
            ///----------------------------------------------------------------------------------------
            output += " |create an empty model";
            Model model = ModelFactory.createDefaultModel();
            // read the RDF/XML file
            model.read(inUrl, "");
            
            cal = Calendar.getInstance();
            output += " |model readed model.read " + sdf.format(cal.getTime()) + "<br>";
            
    		///  
            ///----------------------------------------------------------------------------------------
            output += "|Before open connection to virtuoso ...";
    		VirtGraph graph = new VirtGraph ("commercial","jdbc:virtuoso://s3mant1c.com:8080", "dba", "Passw0rd1");
    		
    		cal = Calendar.getInstance();
    		output += "|Connected to virtuoso ..." + sdf.format(cal.getTime()) + "<br>";
    		  
    		// set up the output
            System.out.println("elementos ");
            // list the nicknames
            StmtIterator iter = model.listStatements();
             
	        // print out the predicate, subject and object of each statement
            ///---------------------------------------------------------------------------------------------
            cal = Calendar.getInstance();
            output += "before while ...."+ sdf.format(cal.getTime()) + "<br>";
            
            System.out.println("before Transaction Commit.<br>");
	 		graph.getTransactionHandler().begin();
	 		System.out.println("begin Transaction.<br>");
	 		System.out.println("Add all triples to graph commercial <br>");
	 		
	 		int iCommit=1;            
	 		try {
	            while (iter.hasNext()) {
		             Statement stmt      = iter.nextStatement();  // get next statement
		             Resource  subject   = stmt.getSubject();     // get the subject
		             Property  predicate = stmt.getPredicate();   // get the predicate
		             RDFNode   object    = stmt.getObject();      // get the object
		             
		             //output += "<b>subject:</b>" + subject.toString() + "<b>predicate:</b>" + predicate.toString()+ "<b>object:</b>" + object.toString() + "<br>";
		
		             /// Inserts to virtuoso
		    	  	 ///----------------------------------------------------------------------------------------
		             Node urlSub = Node.createURI(subject.toString());
		             Node urlPre = Node.createURI(predicate.toString());
		             Node urlObj = Node.createURI(object.toString());
		              
		     		 graph.add(new Triple(urlSub, urlPre, urlObj));
		     		 
		     		cal = Calendar.getInstance();
		            output += "Tripplet Added ...."+ sdf.format(cal.getTime()) + "<br>";
		        }    
	        } catch (Exception e) {	
				iCommit = 0;
	 			
	 			graph.getTransactionHandler().abort();
	 			output += "abort Transaction.<br>";
	 			output += "graph.isEmpty() = " + graph.isEmpty() + "<br>";
	 			output += "graph.getCount() = " + graph.getCount() + "<br>";
	 			
	        	e.printStackTrace();
	        } 
            
	 		cal = Calendar.getInstance();
            output += "End while ...."+ sdf.format(cal.getTime()) + "<br>";
	 		
            if (iCommit==1) {
		 		graph.getTransactionHandler().commit();
		 		
		 		cal = Calendar.getInstance();
		 		output += "commit Transaction: "+ sdf.format(cal.getTime()) + "<br>";
		 		output += "graph.isEmpty() = " + graph.isEmpty() + "<br>";
		 		output += "graph.getCount() = " + graph.getCount()+ "<br>";
	 		}
    		            
       	 	output += "|Response Web Service: END...";
       	 	
        } catch (Exception e) {
        	cal = Calendar.getInstance();
			output += "try catch: " + e.toString() + sdf.format(cal.getTime()) + "<br>";
		} 
        
        // return HTTP response 200 in case of success
        return output;    
        
    }
}