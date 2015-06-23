package Mapper;

import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class WriteFile {
	
	private String path;
	private boolean append_to_file = false;
  
  public WriteFile(String file_path){
	  path = file_path;
  }

  public WriteFile(String file_path, boolean append_value ){
	  path = file_path;
	  append_to_file = append_value;
	  
  }
  
  public void writeToFile(String textLine) throws IOException{
	  FileWriter write = new FileWriter(path , append_to_file);
	  
	  PrintWriter print_line = new PrintWriter( write );
	  
	  print_line.printf("%s", textLine);
	  //print_line.printf("%s" + "%n", textLine); //%n Prints a new line
	  
	  
	  print_line.close();
  }
  
  public void LineBreak(String textLine) throws IOException{
	  FileWriter write = new FileWriter(path , append_to_file);
	  
	  PrintWriter print_line = new PrintWriter( write );
	  
	  //print_line.printf("%s", textLine);
	  print_line.printf("%s" + "%n", textLine); //%n Prints a new line
	  
	  
	  print_line.close();
  }
  
  /*

	  	String map = "C:/cartoon.txt";
	  	
	  	try{
	  		ReadFile file = new ReadFile(map)
	  		String[] aryLines = file.OpenFile();
	  		int i;
	  		for (i = 0; i < aryLines.length; i++){
	  			System.out.println(aryLines[i]);
	  		}
	  	}
	  	catch (IOException e){
	  		System.out.println("Didn't work :'(");
	  	}
  }
  
  
  //write to a file
  WriteFile data = new WriteFile(map, true);
  int x = 152;
  int y = 96;
  
  //data.writeToFile(null, x + " " + y + " ");
  data.writeToFile("Text");
  
  
  int count = 0 ;
  while ( count <= x )
  {
	 if (count = x){
		 count = 0;
	 }
	 data.writeToFile(count + " ");
     count++ ;
  }
  
  System.out.println("New Map Created! Go You!");

  }
*/
}