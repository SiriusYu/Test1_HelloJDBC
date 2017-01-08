
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;

public class JDBC_Exam_Test4 {
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
  
  public static void changeCourse(String courseName, String userFrom, String userTo) throws ClassNotFoundException
  {
	  Connection conn = null;
	  PreparedStatement ptmt = null;

	  //1.装载驱动程序
	  Class.forName(JDBC_DRIVER);
	  
	  try {
		  conn = ds.getConnection();  //连接池获取连接
		  conn.setAutoCommit(false);  //开启事物
		  
		  ptmt = conn.prepareStatement("delete from customer where UserName= ? and CourseName= ?");
		  ptmt.setString(1, userFrom);
		  ptmt.setString(2, courseName);
		  ptmt.execute();
		  ptmt.close();
		  ptmt = conn.prepareStatement("insert customer (UserName,CourseName) values(?, ?)");
		  ptmt.setString(1, userTo);
		  ptmt.setString(2, courseName);
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

	  changeCourse("math", "ZhangSan", "LiSi");
  }
}
