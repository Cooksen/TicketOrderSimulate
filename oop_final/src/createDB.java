import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class createDB {

	public static void creatdb(String db){
		
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	try {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://kaycloud.i234.me:3306/sen_oop?useSSL=false", "senchao", "password");
		
		System.out.println(conn);
		
		StringBuilder sql = new StringBuilder("CREATE TABLE "+db);
        sql.append("(car_id INT(4) not NULL, ");
        sql.append("car_num INT(4), "); 
        sql.append("seat VARCHAR(4), ");
        sql.append("`1` INT(3), ");
        sql.append("`2` INT(3), ");
        sql.append("`3` INT(3), ");
        sql.append("`4` INT(3), ");
        sql.append("`5` INT(3), ");
        sql.append("`6` INT(3), ");
        sql.append("`7` INT(3), ");
        sql.append("`8` INT(3), ");
        sql.append("`9` INT(3), ");
        sql.append("`10` INT(3), ");
        sql.append("`11` INT(3), ");
        sql.append("`12` INT(3)); ");
        
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql.toString());
        System.out.println("Table "+db+" created...");      
		
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
