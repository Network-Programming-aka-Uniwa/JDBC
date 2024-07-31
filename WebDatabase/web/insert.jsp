<%-- 
    Document   : insert
    Created on : Nov 26, 2018, 6:41:49 PM
    Author     : Nick Z. Zacharis
--%>
<%@page import="java.sql.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Insert Record</title>
    </head>
    <body>
<%  
  String JDBC_DRIVER = "com.mysql.jdbc.Driver";
  String db_URL = "jdbc:mysql://127.0.0.1:3306/dbsummer?characterEncoding=utf8";  
  String dbUSER = "sumadmin";
  String dbPASS = "78910";
  Connection dbConn = null;
  Statement dbStmt = null;  
   
  try {
    Class.forName(JDBC_DRIVER);
    dbConn = DriverManager.getConnection(db_URL, dbUSER, dbPASS);  
    
    if(dbConn != null) {
        dbStmt = dbConn.createStatement();
        request.setCharacterEncoding("UTF-8");
        String uname= request.getParameter("uname");
        String usurname= request.getParameter("usurname");
        String age = request.getParameter("age");
        String city = request.getParameter("city");
        String sql = "";
        
        if(city == null || city.equals("")) {
        sql = "Insert into Users (uname, usurname, age)"
                  + " values ('" + uname + "', '" 
                  +  usurname + "'," + age + ")";
        }
        else
        {
        sql = "Insert into Users (uname, usurname, age,city)"
              + " values ('" + uname + "', '" 
              +  usurname + "'," + age + ", '"
              + city + "')";
        }
        int count = dbStmt.executeUpdate(sql);            
        if(count == 1) {
            out.print("A new record inserted in Users table");
        }
        else
        {
            out.print("An error has occured.");
        }
      dbConn.close();    
    }
  }
  catch(SQLException e)
  {
    dbConn = null;
    out.println("SQLException caught: " +e.getMessage());
  }
%>
    </body>
</html>
