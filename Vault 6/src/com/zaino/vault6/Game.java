/**
 * This is the main class of the Fallout Fan Game Vault 6
 * @author Evan Kellough
 */


package com.zaino.vault6;

import java.awt.*;
import java.util.Scanner;
import javax.swing.*;

import com.zaino.vault6.*; //imports all game files

public class Game {
	
	public static void main(String[] args){

		boolean win = false;
		int choice = 0;
		
		Scanner Scan = new Scanner(System.in);
		System.out.println("Welcome to Vault 6!  A fan made indie text adventure game based on the cancelled");
		System.out.println("2000 game Fallout Extreme!  Please Enter 0 to begin your journey!");
		choice = Scan.nextInt();
		
		try{
		switch (choice){
			case 0:
				System.out.println("You begin your journey");
				choice = Scan.nextInt();
				break;
			case 1:
				System.out.println("a");
				choice = Scan.nextInt();
				break;
			case 2:
				System.out.println("b");
				choice = Scan.nextInt();
				break;
			case 3:
				System.out.println("c");
				choice = Scan.nextInt();
				break;
			case 4:
				System.out.println("d");
				choice = Scan.nextInt();
				break;
			case 5:
				System.out.println("e");
				choice = Scan.nextInt();
				break;
			case 6:
				System.out.println("f");
				break;
			case 7:
				System.out.println("g");
				break;
			case 8:
				System.out.println("h");
				break;
			case 9:
				System.out.println("");
				break;
			case 10:
				System.out.println("You have completed your journey!  Congratulations!");
				win = true;
				break;
			}
		}
		Finally{
		//System.out.println("You have completed your journey!  Congratulations!");
	}
}

/*		boolean lt, rt = false;
		JPanel scene = new JPanel ();
		JButton leftbutton = new JButton ();
		JButton rightbutton = new JButton ();
		//GUI Stuff for version 2
		JFrame frame;
		frame = new JFrame("Vault 6");
		frame.setVisible( true );
		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(1);
		frame.setLocationRelativeTo(null);*/