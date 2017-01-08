
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.commons.dbcp2.BasicDataSource;

public class JDBC_Unit2_Test_DBCP {
  static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
  static final String DB_URL = "jdbc:mysql://localhost/cloud_study";
  static final String USER = "root";
  static final String PASSWORD = "12345";
  
  static BasicDataSource ds = null;
  
  public static void dbpoolInit()
  {
	  ds = new BasicDataSource();
	  ds.setDriverClassName(JDBC_DRIVER);
	  ds.setUrl(DB_URL);
	  ds.setUsername(USER);
	  ds.setPassword(PASSWORD);
  }
  
  public static void queryProduct(int Id) throws ClassNotFoundException
  {
	  Connection conn = null;
	  Statement stmt = null;
	  ResultSet rs = null;
	  
	  try {
		  conn = ds.getConnection();  //连接池获取连接
		  stmt = conn.createStatement();
		  rs = stmt.executeQuery("select ProductName,Inventory from Product where Id=" + Id);
		  while(rs.next())
		  {
			  System.out.println("Id " + Id);
			  System.out.println("ProductName " + rs.getString("ProductName"));
			  System.out.println("Inventory " + rs.getString("Inventory"));
		  }
	  } catch (SQLException e) {
		  e.printStackTrace();
	  } finally {
	      try {
		    if (conn != null)
			{
			    conn.close(); //连接归还给连接池
			}
		    if (stmt != null)
			{
		    	stmt.close();
			}
		  } catch (SQLException e1) {
			e1.printStackTrace();
		  }  
	  }
  }
  
  public static void main(String[] args) throws ClassNotFoundException
  {
	  dbpoolInit();
	  //读取商品ID为1的商品记录
	  queryProduct(1);
  }
}
