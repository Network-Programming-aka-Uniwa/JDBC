import java.sql.*;

/**
 *
 * @author Nick Z. Zacharis
 */
public class DatabaseExample {
  static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
  static final String db_URL = "jdbc:mysql://127.0.0.1:3306/dbsummer";

  // Database credentials
  static final String dbUSER = "sumadmin";
  static final String dbPASS = "78910";
  
  Connection dbConn = null;
  Statement dbStmt = null;
  
  DatabaseExample() {
      
  }
  
  public boolean Connect(){
      boolean ans = true;
      try
      {
          Class.forName(JDBC_DRIVER);
          dbConn = DriverManager.getConnection(db_URL, dbUSER, dbPASS);
      }
      catch(Exception ex){
          dbConn = null;
          ans = false;
      }
      return ans;
  }
          
  public void ShowRecords() 
  {
     if(dbConn != null)
        try {
          dbStmt = dbConn.createStatement();
          String sql = "SELECT id, uname, usurname, age, city "
                  + " FROM Users";
          ResultSet dbRs = dbStmt.executeQuery(sql);
          while(dbRs.next())
          {
             int id = dbRs.getInt("id");
             String firstname = dbRs.getString("uname");
             String lastname = dbRs.getString("usurname");
             int age = dbRs.getInt("age");
             String city = dbRs.getString("city");

             System.out.println("id : " + String.valueOf(id));
             System.out.println("FirstName : " + firstname);
             System.out.println("Lastname : " + lastname);
             System.out.println("Age : " + String.valueOf(age));
             System.out.println("City	: " + city);
             System.out.println("================================");
         }
       }
       catch(SQLException ex)  { 
           System.out.println(ex.getMessage());
       }
  }

  
  public void Disconnect()
  {
    try
    {
       dbConn.close();
    }
    catch(SQLException ex) { }
  }
  
  void InsertValues()
  {
      try
      {
          Statement dbSt = dbConn.createStatement();
          
          String sql = "Insert into Users (uname, usurname, age, city)"
                  + " values ('Joe', 'Brown', 34, 'London')";
          int count = dbSt.executeUpdate(sql);
          dbSt.close();
      }
      catch(Exception ex)
      {
          System.err.println("Exception : " + ex.getMessage());
      }
  }
  
  
  
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
      DatabaseExample db = new DatabaseExample();
      if(db.Connect()) {
         db.InsertValues();
         db.ShowRecords();
         db.Disconnect();
      }
    }
}
