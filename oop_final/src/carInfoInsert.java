



public class carInfoInsert{
	public static void main(String[] args) {
		String  station = "0,0,0,0,0,0,0,0,0,0,0,0";
		String station1 = "0,0,0,2,2,2,0,2,2,2,2,0";		
		String station2 = "0,0,0,0,0,0,0,2,2,2,2,2";
		
		
		for(int j = 27; j <31; j++) {
			
			String word = String.format("6_%02d",j);
			createDB.creatdb(word);
			
			for(int i =100;i<116;i+=3) {
			car1all.createcar1(i,word,station);
			System.out.println("car1 in"+word);
			
			car2all.createcar2(i,word,station);
			System.out.println("car2,4,8 in"+word);
			
			car3all.createcar3(i,word,station);
			System.out.println("car3,9 in"+word);
			
			car5all.createcar5(i,word,station);
			System.out.println("car5 in"+word);
			
			car6all.createcar6(i,word,station);
			System.out.println("car6 in"+word);
			
			car7all.createcar7(i,word,station);
			System.out.println("car7 in"+word);
			
			}
			
			for(int i =1000;i<1010;i+=3) {
				car1all.createcar1(i,word,station1);
				System.out.println("car1 in"+word);
				
				car2all.createcar2(i,word,station1);
				System.out.println("car2,4,8 in"+word);
				
				car3all.createcar3(i,word,station1);
				System.out.println("car3,9 in"+word);
				
				car5all.createcar5(i,word,station1);
				System.out.println("car5 in"+word);
				
				car6all.createcar6(i,word,station1);
				System.out.println("car6 in"+word);
				
				car7all.createcar7(i,word,station1);
				System.out.println("car7 in"+word);
				
				}
			
			for(int i =550;i<560;i+=3) {
				car1all.createcar1(i,word,station2);
				System.out.println("car1 in"+word);
				
				car2all.createcar2(i,word,station2);
				System.out.println("car2,4,8 in"+word);
				
				car3all.createcar3(i,word,station2);
				System.out.println("car3,9 in"+word);
				
				car5all.createcar5(i,word,station2);
				System.out.println("car5 in"+word);
				
				car6all.createcar6(i,word,station2);
				System.out.println("car6 in"+word);
				
				car7all.createcar7(i,word,station2);
				System.out.println("car7 in"+word);
				
				}
		}
		System.out.println("Done");
	}
	
	
}