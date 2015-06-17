package Entity;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class RandomMap {
	
	public static void main(String[]args){
		Random randomGenerator = new Random();
		String filename = "random_map.map";
		final int colmax = 64;
		final int rowmax = 64;
		
				try{
					PrintWriter outputStream = new PrintWriter(filename);
					outputStream.println(colmax);
					outputStream.println(rowmax);
							for (int row = 1; row <=rowmax; ++row){
								for (int col =0; col < colmax; col++){
							      int randomInt = randomGenerator.nextInt(50);
							outputStream.print(randomInt + " ");
						}
						outputStream.println("");
					//}
				}
					
					outputStream.close(); //flushes data from RAM to file
					System.out.println("Random Map Generated");
				}catch (FileNotFoundException e){
					e.printStackTrace();
				}
		
	}
}
