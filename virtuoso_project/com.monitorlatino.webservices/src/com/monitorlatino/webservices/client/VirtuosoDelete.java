package com.monitorlatino.webservices.client;
 
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.RDFNode;
import virtuoso.jena.driver.*; 

import java.util.ArrayList;
import java.util.List;

import com.hp.hpl.jena.graph.*;
import com.hp.hpl.jena.rdf.model.Model;
//import oracle.rdf.kv.client.jena.*;
 
public class VirtuosoDelete { 
	
	
    public static void main(String[] args) {
    	 
		String url;
		if(args.length == 0)
		    url = "jdbc:virtuoso://localhost:1111";
		else
		    url = args[0];

		/*			STEP 1			*/
		VirtGraph set = new VirtGraph ("jdbc:virtuoso://s3mant1c.com:8080", "dba", "Passw0rd1");		

		/*			STEP 2			*/
		System.out.println("\nexecute: CLEAR GRAPH <commercial>");
        //String str = "CLEAR GRAPH <http://test1>";
        String str = "CLEAR GRAPH <commercial>";
        //String str = "CLEAR GRAPH <commercial>";
        VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(str, set);
        vur.exec();

        //System.out.println("\nexecute: INSERT INTO GRAPH <http://test1> { <aa> <bb> 'cc' . <aa1> <bb1> 123. }");
        //str = "INSERT INTO GRAPH <http://test1> { <aa> <bb> 'cc' . <aa1> <bb1> 123. }";
        //vur = VirtuosoUpdateFactory.create(str, set);
        //vur.exec();

        /*			STEP 3			*/
        /*		Select all data in virtuoso	*/
        System.out.println("\nexecute: SELECT * FROM <http://test1> WHERE { ?s ?p ?o }");
		Query sparql = QueryFactory.create("SELECT * FROM <http://test1> WHERE { ?s ?p ?o }");

		/*			STEP 4			*/
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (sparql, set);

		ResultSet results = vqe.execSelect();
		while (results.hasNext()) {
			QuerySolution rs = results.nextSolution();
		    RDFNode s = rs.get("s");
		    RDFNode p = rs.get("p");
		    RDFNode o = rs.get("o");
		    System.out.println(" { " + s + " " + p + " " + o + " . }");
		}

		///----------------------------------------------------------
		System.out.println("\nexecute: DELETE FROM GRAPH <http://test1> <aa> <bb> 'cc' ");
        str = "DELETE FROM GRAPH <http://test1> { <aa> <bb> 'cc' }";
        vur = VirtuosoUpdateFactory.create(str, set);
        vur.exec();
        
        ///----------------------------------------------------------
        System.out.println("\nexecute: DELETE FROM GRAPH <http://test1> <aa1> <bb1> <123>");
        str = "DELETE FROM GRAPH <http://test1> { <aa1> <bb1> <123> }";
        vur = VirtuosoUpdateFactory.create(str, set);
        vur.exec();

        ///----------------------------------------------------------
        System.out.println("\nexecute: DELETE FROM GRAPH <http://test1>  <xx> <yy> <zz>");
        str = "DELETE FROM GRAPH <http://test1> { <xx> <yy> <zz> }";
        vur = VirtuosoUpdateFactory.create(str, set);
        vur.exec();
        
        System.out.println("\nexecute: SELECT * FROM <http://test1> WHERE { ?s ?p ?o }");
		vqe = VirtuosoQueryExecutionFactory.create (sparql, set);
                results = vqe.execSelect();
		while (results.hasNext()) {
				QuerySolution rs = results.nextSolution();
			    RDFNode s = rs.get("s");
			    RDFNode p = rs.get("p");
			    RDFNode o = rs.get("o");
			    System.out.println(" { " + s + " " + p + " " + o + " . }");
	    } 
    	        
    }
}

