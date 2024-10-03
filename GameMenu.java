import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMenu extends JFrame {
    public GameMenu() {
        setTitle("Game Menu");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        showMainMenu();
    }

    private void showMainMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel heading = new JLabel("Welcome", SwingConstants.CENTER);
        panel.add(heading, BorderLayout.NORTH);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> showGameMenu());
        panel.add(startButton, BorderLayout.CENTER);

        add(panel);
        setVisible(true);
    }

    private void showGameMenu() {
        getContentPane().removeAll();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        JButton playButton = new JButton("Play a game");
        playButton.addActionListener(e -> showPlayMenu());
        panel.add(playButton);

        JButton settingsButton = new JButton("Settings");
        settingsButton.addActionListener(e -> showSettingsMenu());
        panel.add(settingsButton);

        add(panel);
        revalidate();
        repaint();
    }

    private void showSettingsMenu() {
        getContentPane().removeAll();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel brightnessLabel = new JLabel("Brightness:");
        JSlider brightnessSlider = new JSlider(0, 100, 50);
        JLabel volumeLabel = new JLabel("Volume:");
        JSlider volumeSlider = new JSlider(0, 100, 50);

        panel.add(brightnessLabel);
        panel.add(brightnessSlider);
        panel.add(volumeLabel);
        panel.add(volumeSlider);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showGameMenu());
        panel.add(backButton);

        add(panel);
        revalidate();
        repaint();
    }

    private void showPlayMenu() {
        getContentPane().removeAll();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JLabel chooseGameLabel = new JLabel("Choose a game:");
        panel.add(chooseGameLabel);

        JButton game1Button = new JButton("Snakes and Ladders");
        game1Button.addActionListener(e -> showGameOptions("Snakes and Ladders"));
        panel.add(game1Button);

        JButton game2Button = new JButton("Flappy Bird");
        game2Button.addActionListener(e -> showGameOptions("Flappy Bird"));
        panel.add(game2Button);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showGameMenu());
        panel.add(backButton);

        add(panel);
        revalidate();
        repaint();
    }

    private void showGameOptions(String gameTitle) {
        getContentPane().removeAll();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> startGame(gameTitle));
        panel.add(startButton);

        JButton tutorialButton = new JButton("Tutorial");
        tutorialButton.addActionListener(e -> showTutorial(gameTitle));
        panel.add(tutorialButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showPlayMenu());
        panel.add(backButton);

        add(panel);
        revalidate();
        repaint();
    }

    private void startGame(String gameTitle) {
        JOptionPane.showMessageDialog(this, gameTitle + " Game Started!");
    }

private void showTutorial(String gameTitle) {
    JFrame tutorialFrame = new JFrame(gameTitle + " Tutorial");
    tutorialFrame.setSize(400, 300);
    tutorialFrame.setLocationRelativeTo(null);

    JTextArea textArea = new JTextArea();
    textArea.setEditable(false);

    String[] snakesAndLaddersTutorial = {
        "Step 1: Objective of the Game\nThe objective is to be the first player to reach the last square (usually numbered 100).",
        "Step 2: Game Setup\nThe board consists of 100 squares numbered from 1 to 100.\nSome squares have ladders (move ahead) and some have snakes (move back).",
        "Step 3: Rolling the Dice\nEach player takes turns rolling a die.\nThe number rolled determines how many squares your piece moves forward.",
        "Step 4: Ladders\n\nLanding on the bottom of a ladder moves your piece to the top of the ladder.",
        "Step 5: Snakes\nLanding on the head of a snake sends your piece back to the tail of the snake.",
        "Step 6: Special Rules\n\nYou need to land exactly on square 100 to win.\nIf your roll exceeds 100, you stay in your current spot.",
        "Step 7: How to Win\nThe first player to land on square 100 wins the game."
    };

    String[] flappyBirdTutorial = {
        "Step 1: Objective of the Game\n\nThe goal is to keep your bird flying as long as possible without hitting obstacles.",
        "Step 2: Controls\nPress the space bar or click to make the bird flap its wings and fly higher.",
        "Step 3: Obstacles\nYou need to avoid hitting the pipes that come from above and below.\nEach set of pipes gives you a point when you pass through.",
        "Step 4: How to Win\nThe game continues until you hit an obstacle.\nTry to score as many points as possible!"
    };

    String[] tutorialSteps = gameTitle.equals("Snakes and Ladders") ? snakesAndLaddersTutorial : flappyBirdTutorial;


    textArea.setText(tutorialSteps[0]);

    JScrollPane scrollPane = new JScrollPane(textArea);

    JButton nextButton = new JButton("Next Step");
    JButton backButton = new JButton("Back");


    backButton.setEnabled(false);

    final int[] step = {0};
    nextButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            step[0]++;
            if (step[0] < tutorialSteps.length) {
                textArea.setText(tutorialSteps[step[0]]);
            } else {
                textArea.setText("You have completed the Tutorial. Enjoy :)");
                nextButton.setEnabled(false);
                backButton.setEnabled(true); 
            }
        }
    });

    backButton.addActionListener(e -> {
        tutorialFrame.dispose();  
        showGameOptions(gameTitle);  
    });

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(scrollPane, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(nextButton);
    buttonPanel.add(backButton);
    
    panel.add(buttonPanel, BorderLayout.SOUTH);

    tutorialFrame.add(panel);
    tutorialFrame.setVisible(true);
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameMenu::new);
    }
}
