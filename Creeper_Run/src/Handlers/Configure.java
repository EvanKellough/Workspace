package Handlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Configure {
    public static void main(String[] args) throws IOException {
        BufferedReader bfr;
        String line;    
        bfr=new BufferedReader(new InputStreamReader(System.in));
        String fileName=bfr.readLine();
        File file=new File("config.txt");       
        if(!file.exists()){
            file.createNewFile();
        }       
        try{
            bfr=new BufferedReader(new FileReader(file));

            while((line=bfr.readLine())!=null){
                System.out.println("blah blah blah");
            }
                FileWriter fw=new FileWriter(file,true);            
                for(int i=25;i<30;i++){                 
                    fw.append(String.valueOf(i)+"\n"); //Here I've made the small correction
                }
            }

            while((line=bfr.readLine())!=null){
                System.out.println("ho ho ho");
            }
            bfr.close();
            fw.close();

        }catch(FileNotFoundException fex){
            fex.printStackTrace();
        }

    }
