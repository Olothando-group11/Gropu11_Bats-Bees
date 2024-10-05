import javax.swing.*;

public class FlappyBat {
    // Constructor to initialize the game
    public FlappyBat() {
        JFrame frame = new JFrame("Flappy Bat");
        GamePanel gamePanel = new GamePanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gamePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        //Music.playMusic();  // Make sure Music class exists
    }
}