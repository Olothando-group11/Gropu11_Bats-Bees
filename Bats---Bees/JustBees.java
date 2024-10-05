import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class JustBees extends JPanel implements ActionListener, KeyListener {
	int boardWidth = 360;
	int boardHeight = 640;
	Image backgroundImg;
	Image batImg;
	Image topRockImg;
	Image bottomRockImg;
	
	int batX =  boardWidth/8;
	int batY = boardHeight/2;
	int batWidth = 50;
	int batHeight = 50;
	
	class Bat{
		int x = batX;
		int y = batY;
		int width = batWidth;
		int height = batHeight;
		Image img;
		
		Bat(Image img){
			this.img = img;
		}
	}
	
	int rockX = boardWidth;
	int rockY = 0;
	int rockWidth = 64;
	int rockHeight = 512;
	
	class Rock{
		int x = rockX;
		int y = rockY;
		int width = rockWidth;
		int height = rockHeight;
		Image img;
		boolean passed = false;
		
		Rock(Image img){
			this.img = img;
		}
	}
	//Game Loop
	Bat bat;
	int velocityX = -4;
	int velocityY = 0;
	int gravity = 1;
	
	ArrayList<Rock> rocks;
	Random random = new Random();
	
	Timer gameLoop;
	Timer placeRocksTimer;
	boolean gameOver = false;
	double score = 0;
	
	
	JustBees() {
		setPreferredSize(new Dimension(boardWidth, boardHeight));
		
		setFocusable(true);
		addKeyListener(this);
		
		backgroundImg = new ImageIcon(getClass().getResource("background1.jpg")).getImage();
		batImg = new ImageIcon(getClass().getResource("bee.png")).getImage();
		topRockImg = new ImageIcon(getClass().getResource("toppipe1.png")).getImage();
		bottomRockImg = new ImageIcon(getClass().getResource("bottompipe1.png")).getImage();
		
		bat = new Bat(batImg);
		rocks = new ArrayList<Rock>();
		
		placeRocksTimer = new Timer(1500, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				placeRocks();
			}
		});
		placeRocksTimer.start();
		
		gameLoop = new Timer(1000/60, this);
		gameLoop.start();
	}

public void placeRocks(){
	int randomRockY = (int)(rockY - rockHeight/4 - Math.random()*(rockHeight/2));
	int openingSpace = boardHeight/4;
	Rock topRock = new Rock(topRockImg);
	topRock.y = randomRockY;
	rocks.add(topRock);
	
	Rock bottomRock = new Rock(bottomRockImg);
	bottomRock.y = topRock.y + rockHeight + openingSpace;
	rocks.add(bottomRock);
	
}


public void paintComponent(Graphics g){
	super.paintComponent(g);
	draw(g);
}

public void draw(Graphics g){
	g.drawImage(backgroundImg,0,0, boardWidth, boardHeight, null);
	
	g.drawImage(bat.img,bat.x,bat.y, bat.width, bat.height, null);
	
	for (int i = 0; i < rocks.size(); i ++){
		Rock rock = rocks.get(i);
		g.drawImage(rock.img,rock.x,rock.y,rock.width,rock.height, null);}
		
		g.setColor(Color.white);
		g.setFont(new Font("TimesNewRoman", Font.PLAIN, 32));
		if (gameOver){
			g.drawString("Game Over: " + String.valueOf((int) score), 10, 35);
		}
		else{
		g.drawString(String.valueOf((int) score), 10, 35);}
}

public void move() {
	
	velocityY += gravity;
	bat.y += velocityY;
	bat.y = Math.max(bat.y,0);
	
	for (int i = 0; i < rocks.size(); i ++){
		Rock rock = rocks.get(i);
		rock.x += velocityX;
		
		if(!rock.passed && bat.x > rock.x + rock.width){
			rock.passed = true;
			score += 0.5;
		}
		
		if (collision(bat, rock)){
			gameOver = true;
		}
}
	if (bat.y > boardHeight) {
		gameOver = true;
	}
}

public boolean collision(Bat a, Rock b){
	return a.x < b.x +b.width &&
	a.x + a.width > b.x &&
	a.y < b.y + b.height &&
	a.y + a.height > b.y;
}


@Override
public void actionPerformed(ActionEvent e){
	move();
	repaint();
	if (gameOver){
		placeRocksTimer.stop();
		gameLoop.stop();
	}
	}


@Override
public void keyPressed(KeyEvent e){
	if (e.getKeyCode() == KeyEvent.VK_SPACE){
		velocityY = -9;
		if (gameOver){
			bat.y = batY;
			velocityY = 0;
			rocks.clear();
			score = 0;
			gameOver = false;
			gameLoop.start();
		placeRocksTimer.start();}
	}
}

@Override
public void keyTyped(KeyEvent e){}

@Override
public void keyReleased(KeyEvent e){}

}
