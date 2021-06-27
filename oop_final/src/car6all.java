import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class car6all {
	
	public static void createcar6(int x, String db, String station){
	 
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	int car_num = 6;
	int car_id = x;
	
	try {
		Class.forName("com.mysql.jdbc.Driver");
		long start = System.currentTimeMillis();
		conn = DriverManager.getConnection("jdbc:mysql://kaycloud.i234.me:3306/sen_oop?useSSL=false", "senchao", "password");
		
		System.out.println(conn);

		String sql = "select * from "+db;
		ps = conn.prepareStatement(sql);
		 
		rs = ps.executeQuery();
		Statement stmt = conn.createStatement();
		
		long end = System.currentTimeMillis();
		System.out.println("time"+(end-start)+"ms");
			
		//System.out.println("Inserting records into the table...");        
		String sql1, word = "1D",word1 = "1E";
        
		sql1=String.format("INSERT INTO %s VALUES (%d,%d,'%s',%s)",db,car_id,car_num, word,station);	
    	stmt.executeUpdate(sql1);
    	sql1=String.format("INSERT INTO %s VALUES (%d,%d,'%s',%s)",db,car_id,car_num, word1,station);	
    	stmt.executeUpdate(sql1);
    	
        for (int i = 2; i < 18; i++) 
        {
        	for (int flag=0; flag <4; flag++) {
        		if(flag  == 2) {
        			continue;
        		}
        	tools tl = new tools();
        	sql1 = tl.insertSeat(car_id, flag, i, car_num,db,station);

        	stmt.executeUpdate(sql1);
        	}}
        
        System.out.println("Inserted into the table...");      
		
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
