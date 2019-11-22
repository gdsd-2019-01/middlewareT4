package helloservlet;


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.DecimalFormat;

import org.json.JSONObject;

public class HelloServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
              throws IOException, ServletException {
     // Set the response message's MIME type
     response.setContentType("text/html;charset=UTF-8");
     // Allocate a output writer to write the response message into the network socket
     PrintWriter out = response.getWriter();

     try {
    	 float c1;
    	 float c2;
    	 c1 = Float.valueOf(request.getParameter("c1").trim()).floatValue();
    	 c2 = Float.valueOf(request.getParameter("c2").trim()).floatValue();
    	 String sign = request.getParameter("sign");
    	 String name = request.getParameter("name");
    	 float c3 = c1*c2;
    	 DecimalFormat decimalFormat = new DecimalFormat("0.##");
    	 String result =sign + decimalFormat.format(c3);
    	 out.println(result);
    	 
    	 try{
             Connection con = getConnection();
             PreparedStatement posted = con.prepareStatement("INSERT INTO amounts (name, _from, _to) VALUES ('"+name+"', '"+c1+"','"+result+"')");
            
             posted.executeUpdate();
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
	   String driver = "com.mysql.jdbc.Driver";
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