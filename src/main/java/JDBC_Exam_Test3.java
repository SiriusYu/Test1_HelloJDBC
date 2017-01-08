
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;

public class JDBC_Exam_Test3 {
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
  
  public static void queryCourse(String userName) throws ClassNotFoundException
  {
	  Connection conn = null;
	  PreparedStatement ptmt = null;
	  ResultSet rs = null;
	  
	  //1.装载驱动程序
	  Class.forName(JDBC_DRIVER);
	  
	  try {
		  conn = ds.getConnection();  //连接池获取连接
		  ptmt = conn.prepareStatement("select CourseName from customer where UserName= ?");
		  ptmt.setString(1, userName);
		  rs = ptmt.executeQuery();
		  
		  while(rs.next())
		  {
			  System.out.println(rs.getString("CourseName"));
		  }
	  } catch (SQLException e) {
		  e.printStackTrace();
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
		    if (rs != null)
			{
			    rs.close();
			}
		  } catch (SQLException e1) {
			e1.printStackTrace();
		  }  
	  }
  }
  
  public static void main(String[] args) throws ClassNotFoundException
  {
	  dbpoolInit();
	  
	  //读取ZhangSan的课程
	  queryCourse("ZhangSan");
  }
}
