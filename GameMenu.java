
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
        game1Button.addActionListener(e -> showGameOptions());
        panel.add(game1Button);

        JButton game2Button = new JButton("Flappy Bird");
        game2Button.addActionListener(e -> showGameOptions());
        panel.add(game2Button);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showGameMenu());
        panel.add(backButton);

        add(panel);
        revalidate();
        repaint();
    }

    private void showGameOptions() {
        getContentPane().removeAll();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> startGame());
        panel.add(startButton);

        JButton tutorialButton = new JButton("Tutorial");
        tutorialButton.addActionListener(e -> showTutorial());
        panel.add(tutorialButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showPlayMenu());
        panel.add(backButton);

        add(panel);
        revalidate();
        repaint();
    }

    private void startGame() {
        JOptionPane.showMessageDialog(this, "Game Started!");
    }

    private void showTutorial() {
        JOptionPane.showMessageDialog(this, "Game rules go here.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameMenu::new);
    }
}