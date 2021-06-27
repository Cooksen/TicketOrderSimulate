
public class tools{
	String sql1, word;
	
	public String insertSeat(int car_id, int flag, int i,int car_num,String db, String station) {
		switch (flag) {
		case 0:	word = String.format("%dA",i);
		break;
		case 1: word = String.format("%dB",i);
		break;
		case 2: word = String.format("%dC",i);
		break;
		case 3: word = String.format("%dD",i);
		break;
		case 4: word = String.format("%dE",i);
		break;
		default:
		}
		return sql1=String.format("INSERT INTO %s VALUES (%d,%d,'%s',%s)", db, car_id, car_num, word, station);	
		}
	public String insertDiscount(int car_id, int flag, int i, int car_num, String db, String station, int x) {
		
		switch (flag) {
		case 0:	word = String.format("%dA",i);
		break;
		case 1: word = String.format("%dB",i);
		break;
		case 2: word = String.format("%dC",i);
		break;
		case 3: word = String.format("%dD",i);
		break;
		case 4: word = String.format("%dE",i);
		break;
		default:
		}
		return sql1=String.format("INSERT INTO %s VALUES (%d,%d,'%s',%s)", db, car_id, car_num, word, station);	
	}
	
}
