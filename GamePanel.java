import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    int Bwidth = 360;  // Game window width
    int Bheight = 640; // Game window height
    boolean paused = false;

    

    // Game components
    Bat bat;                // The bat (player character)
    ArrayList<Spike> spikes; // List of spikes (obstacles)

    // Images for background, top spike, and bottom spike
    Image backgrImg;
    Image topSpikeImg;
    Image bottomSpikeImg;

    // Game state variables
    double velx = -5.0;  // Speed at which spikes move left across the screen
    int gravity = 1;     // Gravity affecting the bat
    double score = 0.0;  // Player's score
    boolean gameover = false;  // Check if the game is over

    Timer gameLoop;        // Timer to control the game loop
    Timer putSpikesTimer;  // Timer to place new spikes periodically

    public GamePanel() {
        
        setPreferredSize(new Dimension(Bwidth, Bheight));
        setFocusable(true);
        addKeyListener(this);

        // Load images
        backgrImg = new ImageIcon(getClass().getResource("./Nuwe.jpg")).getImage();
        Image batImg = new ImageIcon(getClass().getResource("./Battt.png")).getImage();
        topSpikeImg = new ImageIcon(getClass().getResource("./MarioA.png")).getImage();
        bottomSpikeImg = new ImageIcon(getClass().getResource("./MarioB.png")).getImage();

        // Initialize game objects
        bat = new Bat(Bwidth / 8, Bheight / 2, 55, 48, batImg);
        spikes = new ArrayList<>();

        // Timer to place new spikes every 1.5 seconds
        putSpikesTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeSpikes(); // Add new spikes to the game
            }
        });
        putSpikesTimer.start();

        // Game loop to update the game 60 times per second
        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    }

    // Method to place new spikes on the screen
    public void placeSpikes() {
        int spikeHeight = 512;
        int spikeY = (int) (Math.random() * (spikeHeight / 2));
        int freeSpace = Bheight / 4; // Space between top and bottom spikes

        // Create top spike
        Spike topSpike = new Spike(Bwidth, spikeY - spikeHeight, 64, spikeHeight, topSpikeImg);
        spikes.add(topSpike);

        // Create bottom spike
        Spike bottomSpike = new Spike(Bwidth, topSpike.getY() + spikeHeight + freeSpace, 64, spikeHeight, bottomSpikeImg);
        spikes.add(bottomSpike);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g); // Call the draw method
    }

    // Method to draw the game components
    public void draw(Graphics g) {
        g.drawImage(backgrImg, 0, 0, Bwidth, Bheight, null); // Draw background
        bat.draw(g); // Draw the bat

        // Draw each spike in the spikes list
        for (Spike spike : spikes) {
            spike.draw(g);
        }

        // Draw the score
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (gameover) {
            g.drawString("Game Over " + String.valueOf((int) score), 10, 35);
        } else {
            g.drawString(String.valueOf((int) score), 10, 35);
        }
    }

    // Update the game state
    public void move() {
        if (gameover) return;

        // Move the bat
        bat.update(gravity);

        // Move the spikes
        for (Spike spike : spikes) {
            spike.update(velx);

            // Check if the bat has passed a spike
            if (!spike.isPassed() && bat.getX() > spike.getX() + spike.getWidth()) {
                spike.setPassed(true);
                score += 0.5; // Increase score by 0.5 for each passed spike
            }

            // Check if the bat has collided with a spike
            if (bat.collision(spike)) {
                gameover = true;
            }
        }

        // Remove spikes that are out of the screen
        spikes.removeIf(spike -> spike.getX() < -spike.getWidth());

        // End game if bat falls below the screen
        if (bat.getY() > Bheight) {
            gameover = true;
        }
    }
    private void showPauseMenu() {
        // Create a new JPanel for the pause menu
        JPanel pauseMenu = new JPanel();
        pauseMenu.setLayout(new GridLayout(3, 1));
    
        // Create Resume button
        JButton resumeButton = new JButton("Resume");
        resumeButton.addActionListener(e -> {
            togglePause();  // Resume the game
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.dispose();
        });
    
        // Create Quit button
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> {
            
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.dispose();
        
            new GameMenu(); // Exit the game
        });
    
        // Add buttons to the pause menu
        pauseMenu.add(resumeButton);
        pauseMenu.add(quitButton);
    
        // Show the pause menu as a dialog
        JOptionPane.showOptionDialog(this, pauseMenu, "Game Paused",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
    }
    

    private void togglePause() {
        paused = !paused;  // Toggle pause state
    
        if (paused) {
            gameLoop.stop();
            putSpikesTimer.stop();
            showPauseMenu();  // Show pause menu when game is paused
            
        } else {
            gameLoop.start();
            putSpikesTimer.start();
            
        }
    }

    

    // Method to handle key presses
    @Override
public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        if (!paused && !gameover) {
            bat.jump(); // Make the bat jump
        }

        // If game is over, reset it
        if (gameover) {
            resetGame();
        }
    }

    if (e.getKeyCode() == KeyEvent.VK_P) {
        if (!gameover) {
            togglePause(); // Toggle the pause state
        }
    }
}


    // Method to reset the game
    private void resetGame() {
        
        bat.setY(Bheight / 2);
        bat.setVelocityY(0);
        spikes.clear();
        score = 0;
        gameover = false;
        gameLoop.start();
        putSpikesTimer.start();
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();

        if (gameover) {
            putSpikesTimer.stop();
            gameLoop.stop();
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}