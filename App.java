import javax.swing.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
	
public class App {

	static JFrame frame = new JFrame("Bats & Bees");
	public App()throws UnsupportedAudioFileException, IOException, LineUnavailableException{
	int boardWidth = 360;
	int boardHeight = 640;
	
	// File file = new File("8bit.wav");
	// AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
	// Clip clip = AudioSystem.getClip();
	// clip.open(audioStream);
	// clip.loop(Clip.LOOP_CONTINUOUSLY);
	// clip.start();
	
	enum STATE{
		MENU,
		GAME
	};

	STATE State = STATE.MENU;
	if (Menu.game_state == true){
	System.out.print("yeahh");}
	
	//frame.setVisible(true);
	frame.setSize(boardWidth, boardHeight);
	frame.setLocationRelativeTo(null);
	frame.setResizable(false);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	

		
	if(State == STATE.GAME) {
		
		BatsBees batsbees = new BatsBees();
		frame.add(batsbees);
		frame.pack();
		batsbees.requestFocus();
		frame.setVisible(true);
		

		
		
		}
	else if(State == STATE.MENU){
		Menu menu = new Menu();
		frame.add(menu);
		frame.pack();
		frame.setVisible(true);
		
	}
	}
}