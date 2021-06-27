import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class id_time {

	public static void main(String[] args){
	 
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	try {
		Class.forName("com.mysql.jdbc.Driver");
		long start = System.currentTimeMillis();
		conn = DriverManager.getConnection("jdbc:mysql://kaycloud.i234.me:3306/sen_oop?useSSL=false", "senchao", "senchao");
		
		System.out.println(conn);

		String sql = "select * from car_time";
		ps = conn.prepareStatement(sql);
		
		rs = ps.executeQuery();
		Statement stmt = conn.createStatement();
		
		long end = System.currentTimeMillis();
		System.out.println("time"+(end-start)+"ms");

		String sql1;
		
		
		for(int date = 15;date <29; date++) {
			int dTime = 6;
			int dm = 0;
			int ah = 0;
			int am = 25;
			for(int i = 100; i < 116;i+=3) {
				ah = dTime + 2;
				
				sql1=String.format("INSERT INTO car_time VALUES (%d,'2021-06-%d','%02d:%02d','%02d:%02d',0)",i,date,dTime,dm,ah,am);	
		    	stmt.executeUpdate(sql1);
		        
		        System.out.println("Inserted into the table..."); 
		        dTime += 3;
			}
		}
		
		for(int date = 15;date <29; date++) {
			int dTime = 10;
			int dm = 30;
			int ah = 0;
			int am = 15;
			for(int i = 1000; i < 1010;i+=3) {
				ah = dTime + 2;
				
				sql1=String.format("INSERT INTO car_time VALUES (%d,'2021-06-%d','%02d:%02d','%02d:%02d',0)",i,date,dTime,dm,ah,am);	
		    	stmt.executeUpdate(sql1);
		        
		        System.out.println("Inserted into the table..."); 
		        dTime += 3;
			}
		}
		
		for(int date = 15;date <29; date++) {
			int dTime = 9;
			int dm = 15;
			int ah = 0;
			int am = 30;
			for(int i = 500; i < 510;i+=3) {
				ah = dTime + 1;
				
				sql1=String.format("INSERT INTO car_time VALUES (%d,'2021-06-%d','%02d:%02d','%02d:%02d',0)",i,date,dTime,dm,ah,am);	
		    	stmt.executeUpdate(sql1);
		        
		        System.out.println("Inserted into the table..."); 
		        dTime += 3;
			}
		}
        
		
		
             
		
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
	
	}}
	

}
