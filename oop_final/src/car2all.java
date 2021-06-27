import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/*
 * 建立2,4,8車的座位(全站皆停)。
 */
public class car2all {
	
	public static void createcar2(int x, String db, String station){
	for(int car_num = 2;car_num<11;car_num+=2) {
		if(car_num==6||car_num==10) {
			continue;
		}

	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
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
		String sql1, word = "1A",word1 = "1B", word2 = "1C";
		String word3 = "20A",word4 = "20B", word5 = "20C";
        
		sql1=String.format("INSERT INTO %s VALUES (%d,%d,'%s',%s)",db,car_id,car_num, word,station);	
    	stmt.executeUpdate(sql1);
    	sql1=String.format("INSERT INTO %s VALUES (%d,%d,'%s',%s)",db,car_id,car_num, word1,station);	
    	stmt.executeUpdate(sql1);
    	sql1=String.format("INSERT INTO %s VALUES (%d,%d,'%s',%s)",db,car_id,car_num, word2,station);		
    	stmt.executeUpdate(sql1);

        for (int i = 2; i < 20; i++) 
        {
        	for (int flag=0; flag <5; flag++) {
        	tools tl = new tools();     	
        	sql1 = tl.insertSeat(car_id, flag, i, car_num,db,station);
        	stmt.executeUpdate(sql1);
        	}
        	}
        
    	sql1=String.format("INSERT INTO %s VALUES (%d,%d,'%s',%s)",db,car_id,car_num, word3,station);			
    	stmt.executeUpdate(sql1);
    	sql1=String.format("INSERT INTO %s VALUES (%d,%d,'%s',%s)",db,car_id,car_num, word4,station);		
    	stmt.executeUpdate(sql1);
    	sql1=String.format("INSERT INTO %s VALUES (%d,%d,'%s',%s)",db,car_id,car_num, word5,station);		
    	stmt.executeUpdate(sql1);
        
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
	
	}}}
}
