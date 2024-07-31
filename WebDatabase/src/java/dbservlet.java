/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Nick Z. Zacharis
 */
@WebServlet(urlPatterns = {"/dbservlet"})
public class dbservlet extends HttpServlet {

  String JDBC_DRIVER = "com.mysql.jdbc.Driver";
  String db_URL = "jdbc:mysql://127.0.0.1:3306/dbsummer?characterEncoding=utf8";  
  String dbUSER = "sumadmin";
  String dbPASS = "78910";
  Connection dbConn = null;
  Statement dbStmt = null;  
   
  boolean initConnection() {
     try {
         Class.forName(JDBC_DRIVER);
         dbConn = DriverManager.getConnection(db_URL, dbUSER, dbPASS);  
         if(dbConn != null)  {  return true;  }
      }
      catch(Exception ex){  }
      return false;
  }
   
    
    void doSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if(!initConnection()) {
           response.sendRedirect("dberror.html");
           return;
        }

        String city = request.getParameter("city");
            
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet dbservlet</title>");   
            out.println("<meta charset=\"UTF-8\">");
            out.println("</head>");
            out.println("<body>");
            
          dbStmt = dbConn.createStatement();
          String sql = "SELECT id, uname, usurname, age, city "
                  + " FROM Users where city = '";
          sql = sql + request.getParameter("city") + "';";
          ResultSet dbRs = dbStmt.executeQuery(sql);
          
          if(!dbRs.isBeforeFirst​())
          {
            out.print("No records found");
          }
          else
          {
            while(dbRs.next())
            {
             int id = dbRs.getInt("id");
             String firstname = dbRs.getString("uname");
             String lastname = dbRs.getString("usurname");
             int age = dbRs.getInt("age");
             out.print("id : " + String.valueOf(id) + "<br>");
             out.print("FirstName : " + firstname + "<br>");
             out.print("Lastname : " + lastname +  "<br>");
             out.print("Age : " + String.valueOf(age) + "<br>");
             out.print("City	: " + city  + "<br>");
             out.print("================================<br>");
           }      
           dbConn.close();  
           out.println("</body>");
           out.println("</html>");
          }
        }
        catch(Exception ex) {}
    }

    void doInsert(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
           
           String uname= request.getParameter("uname");
           String usurname= request.getParameter("usurname");
           String age = request.getParameter("age");
           String city = request.getParameter("city");
           String sql = "";
          
           if( (uname == null || uname.equals("")) ||
               (usurname == null || usurname.equals("")) ||
               (age == null || age.equals("")) )  {
               response.sendRedirect("dberror.html");
               return;
           }
       
           if(!initConnection()) {
              response.sendRedirect("dberror.html");
              return;
        }
       
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
        
           dbStmt = dbConn.createStatement();
                   
           if(city == null || city.equals("")) {
             sql = "Insert into Users (uname, usurname, age)"
                  + " values ('" + uname + "', '" 
                  +  usurname + "'," + age + ")";
           }
           else {
             sql = "Insert into Users (uname, usurname, age,city)"
              + " values ('" + uname + "', '" 
              +  usurname + "'," + age + ", '"
              + city + "')";
           }
           
           int count = dbStmt.executeUpdate(sql);            
           out.println("<!DOCTYPE html>");
           out.println("<html>");
           out.println("<head>");
           out.println("<meta charset=\"UTF-8\">");
           out.println("<title>Servlet dbservlet</title>");            
           out.println("</head>");
           out.println("<body>");
          
           if(count == 1) {
               out.print("A new record inserted in Users table");
           }
           else {
               out.print("An error has occured.");
           }
            out.println("</body>");
            out.println("</html>");

           dbConn.close();    
        }catch(Exception ex) {}
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, 
        HttpServletResponse response)throws ServletException, IOException {            
        try{
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            String action = request.getParameter("action");
            if(action == null || action.equals(""))  {
                 response.sendRedirect("missingdata.html"); return;
            }
            else if(action.equals("search")) {
                doSearch(request, response);
            }
            else if(action.equals("insert")) {
                doInsert(request, response);
            }
            else {
                 response.sendRedirect("missingdata.html"); return;
            }
        }catch(Exception ex){}
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
