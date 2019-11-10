package helloservlet;


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Visits extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
              throws IOException, ServletException {
     // Set the response message's MIME type
     response.setContentType("text/html");
     // Allocate a output writer to write the response message into the network socket
     PrintWriter out = response.getWriter();

     try {
    	
    	
    	 
    	 try{
    		 Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement("SELECT * FROM visits");
            
             ResultSet result = statement.executeQuery();
             int i =0;
             while(result.next()){
            	 i++;
             }
           out.print(i);
         } catch(Exception e){System.out.println(e);}
    	 
    	 try{
             Connection con = getConnection();
             String sql = "INSERT INTO visits(count) VALUES (?)";
             PreparedStatement st = con.prepareStatement(sql);
             st.setNull(1, Types.INTEGER);
             st.executeUpdate();
             
         } catch(Exception e){System.out.println(e);}
         finally {
             System.out.println("Insert Completed.");
         }
         
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