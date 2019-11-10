package helloservlet;


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class History extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
              throws IOException, ServletException {
     // Set the response message's MIME type
     response.setContentType("application/json");
     // Allocate a output writer to write the response message into the network socket
     PrintWriter out = response.getWriter();

     try {
    	 JSONArray jsonarray = new JSONArray();
    	
    	 
    	 try{
    		 Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement("SELECT * FROM amounts ORDER BY id DESC");
            
             ResultSet result = statement.executeQuery();
        
             while(result.next()){
            	 JSONObject json = new JSONObject();
            	 json.put("name",result.getString("name"));
            	 json.put("_from",result.getString("_from"));
            	 json.put("_to",result.getString("_to")); 
            	 jsonarray.put(json);
             }
            //System.out.println(jsonarray.toString());
            out.print(jsonarray);
         } catch(Exception e){System.out.println(e);}
         
     } finally {
        out.close();  // Always close the output writer
     }
  }
  
  public static Connection getConnection() throws Exception{
	  try{
	   String driver = "com.mysql.cj.jdbc.Driver";
	   String url = "jdbc:mysql://localhost:3306/history";
	   String username = "root";
	   String password = "";
	   Class.forName(driver);
	   
	   Connection conn = DriverManager.getConnection(url,username,password);
	   System.out.println("Connected");
	   return conn;
	  } catch(Exception e){System.out.println(e);}
	  
	  
	  return null;
	 }
}