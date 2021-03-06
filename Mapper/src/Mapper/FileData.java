package Mapper;
import java.io.IOException;

public class FileData {
	
	public static void main(String[] args)throws IOException{
		String file_name = "/Users/Evan/Desktop/Workspace/Creeper_Run/Resources/Maps/cartoon.map";
		
		try {
			ReadFile file = new ReadFile(file_name);
			String[] aryLines = file.OpenFile();
			
			int i;
			for (i = 0; i < aryLines.length; i++){
				System.out.println(aryLines[i]);
			}
			
		}
		catch (IOException e){
			System.out.println(e.getMessage() );
		}
		
		///////////////////////////////////////////////////
		/*
		 * The following loop takes an image and creates a map out of it by breaking it down
		 * into individual tiles instead of using repeating tiles to make a map.
		 * In the future I plan to make a loop that creates a procedurally generated map.
		 */
		///////////////////////////////////////////////////
		int x = 1; //prints numbers between 0 and the max
		int y = 1; //Number of Rows
		int z = 0; //Keeps track of numbers per row like a type writer
		int col = 152;
		int row = 96;
		int max = col * row; //max is the number of tiles in an image
		
		try{
			WriteFile data = new WriteFile(file_name, true);
			WriteFile newline = new WriteFile(file_name, true);
			String num1 = String.valueOf(col + " ");
			String num2 = String.valueOf(row + " ");
			newline.LineBreak(num1);
			newline.LineBreak(num2);
				while(x < max){
					if(y == col){
						//WriteFile newline = new WriteFile(file_name, true); //for creating new lines
						String line = String.valueOf(x + " ");
						newline.LineBreak(line); //The newline method creates a new line, the writeToFile method does not
						y = 1;
						x++;
					}
					//WriteFile data = new WriteFile(file_name, true); //for making regular lines
					/*
					String num1 = String.valueOf(col + " ");
					String num2 = String.valueOf(row + " ");
					newLine.LineBreak(num1);
					newLine.LineBreak(num2);
					*/
				String number = String.valueOf(x);
				data.writeToFile(number + " "); //prints the number and a space
				//System.out.println(max);
				x++;
				y++;
		}
	}
				catch (IOException e){
					System.out.println(e.getMessage() );
		}
		System.out.println("finished");
	}
}