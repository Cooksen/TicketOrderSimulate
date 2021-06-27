import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class test {

	public static void main(String[] args) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			long start = System.currentTimeMillis();
			conn = DriverManager.getConnection("jdbc:mysql://kaycloud.i234.me:3306/sen_oop?useSSL=false", "senchao", "password");
			
			System.out.println(conn);
			String sql = "SELECT phone FROM record";
			ps = conn.prepareStatement(sql);
			
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery("SELECT * FROM record");
			while(resultSet.next()) {
				String IDCard = resultSet.getString("IDCard");
				
				System.out.println(IDCard);
				
			}

			
			long end = System.currentTimeMillis();
			System.out.println("time"+(end-start)+"ms");       
	        
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{

		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		if(ps!=null){
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		}

	}

}
