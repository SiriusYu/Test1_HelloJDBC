
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBC_Exam_Test2 {
  static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
  static String DB_URL = "jdbc:mysql://localhost/cloud_study";
  static final String USER = "root";
  static final String PASSWORD = "12345";
  
  public static void queryCourse() throws ClassNotFoundException
  {
	  Connection conn = null;
	  PreparedStatement ptmt = null;
	  ResultSet rs = null;
	  
	  //1.装载驱动程序
	  Class.forName(JDBC_DRIVER);
	  
	  try {
		  //开启游标
		  DB_URL = DB_URL + "?useCursorFetch=true";
		  
		  //2.建立数据库连接
		  conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
		  
		  //3.执行SQL语句
		  ptmt = conn.prepareStatement("select CourseName,UserName from customer");
		  ptmt.setFetchSize(1);
		  rs = ptmt.executeQuery();
		  
		  //4.获取执行结果
		  while(rs.next())
		  {
			  System.out.println(rs.getString("UserName") + ": " + rs.getString("CourseName"));
		  }
	  } catch (SQLException e) {
		  //异常处理
		  e.printStackTrace();
	  } finally {
		  //5.清理环境
	      try {
		    if (conn != null)
			{
			    conn.close();
			}
		    if (ptmt != null)
			{
		    	ptmt.close();
			}
		    if (rs != null)
			{
			    rs.close();
			}
		  } catch (SQLException e) {
			// ignore
			e.printStackTrace();
		  }  
	  }
  }
  
  public static void main(String[] args) throws ClassNotFoundException
  {
	  queryCourse();
  }
}
