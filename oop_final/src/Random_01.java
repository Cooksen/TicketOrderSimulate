import java.util.Random;


public class Random_01 {
	
	public static int randomNum() {
		Random rm = new Random();
		double pross = (1 +rm.nextDouble())*Math.pow(10, 6);
		
		return (int)pross;
	}

	public static void mail(String[] args) {}
	
	public static void main(String[] args) {
		
		int[][] a;
		a = new int[10][1];

		for(int i = 0; i < 10; i++) {
			int tmp = randomNum();
			for(int j = 0; j < a.length; j++) {
				if(a[j][0]==tmp) {
					i--;
					continue;
				}
			}
			
			a[i][0]=tmp;
			System.out.println(a[i][0]);
		}
		
	}
}
