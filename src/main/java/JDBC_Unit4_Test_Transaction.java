
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;

public class JDBC_Unit4_Test_Transaction {
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
  
  public static void buy(String buyer, String product) throws ClassNotFoundException
  {
	  Connection conn = null;
	  PreparedStatement ptmt = null;
	  
	  try {
		  conn = ds.getConnection();  //连接池获取连接
		  conn.setAutoCommit(false);  //开启事物
		  ptmt = conn.prepareStatement("update Inventory set Inventory=Inventory-1 where ProductName= ?");
		  ptmt.setString(1, "\"" + product + "\"");
		  ptmt.execute();
		  ptmt.close();
		  ptmt = conn.prepareStatement("insert into Order set buyer= ?, ProductName= ?");
		  ptmt.setString(1, "\"" + buyer + "\"");
		  ptmt.setString(2, "\"" + product + "\"");
		  ptmt.execute();
		  conn.commit();  //提交事物
	  } catch (SQLException e) {
		  if (conn != null)
		  {
			  try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		  }
	  } finally {
	      try {
		    if (conn != null)
			{
			    conn.close(); //连接归还给连接池
			}
		    if (ptmt != null)
			{
		    	ptmt.close();
			}
		  } catch (SQLException e) {
			e.printStackTrace();
		  }  
	  }
  }
  
  public static void main(String[] args) throws ClassNotFoundException
  {
	  dbpoolInit();
	  buy("XiaoMing", "bag");
  }
}
