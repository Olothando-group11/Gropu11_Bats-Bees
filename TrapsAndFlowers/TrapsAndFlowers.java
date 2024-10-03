import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.swing.*;

public class TrapsAndFlowers extends JFrame {
    private final int BOARD_SIZE = 10;
    private final int TOTAL_CELLS = BOARD_SIZE * BOARD_SIZE;
    private JLabel[] cells = new JLabel[TOTAL_CELLS];
    private JPanel boardPanel;
    private JButton rollButtonP1, rollButtonP2;
    private JLabel diceLabel;
    private int player1Pos = 1;
    private int player2Pos = 1;
    private boolean isPlayer1Turn = true;
    private ImageIcon[] diceIcons = new ImageIcon[6];
    private ImageIcon p1Icon, p2Icon;
    private ImageIcon boardImage;

    private Map<Integer, Integer> flowers = new HashMap<>();
    private Map<Integer, Integer> traps = new HashMap<>();

    public TrapsAndFlowers() {
        setTitle("Traps and Flowers");
        setSize(600, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initFlowersAndTraps(); // Initialize the traps and flowers

        for (int i = 0; i < 6; i++) {
            ImageIcon originalIcon = new ImageIcon("dice" + (i + 1) + ".png");
            Image scaledImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            diceIcons[i] = new ImageIcon(scaledImage);
        }

        p1Icon = new ImageIcon(new ImageIcon("P1.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        p2Icon = new ImageIcon(new ImageIcon("P2.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));

        boardImage = new ImageIcon(new ImageIcon("board.png").getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH));

        // Setup board with background image and transparent cells
        boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(boardImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        boardPanel.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        boardPanel.setBackground(Color.GREEN);

        for (int i = 0; i < TOTAL_CELLS; i++) {
            cells[i] = new JLabel();
            cells[i].setOpaque(false);
            cells[i].setBorder(BorderFactory.createEmptyBorder());
            boardPanel.add(cells[i]);
        }
        add(boardPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new GridBagLayout());
        controlPanel.setBackground(Color.GREEN);
        GridBagConstraints gbc = new GridBagConstraints();

        // Player 1 button and icon
        rollButtonP1 = createSquareButton("P1");
        rollButtonP1.addActionListener(new RollDiceListener());
        rollButtonP1.setEnabled(true);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 20, 10, 20);
        controlPanel.add(rollButtonP1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel p1ImageLabel = new JLabel(p1Icon);
        controlPanel.add(p1ImageLabel, gbc);

        // Dice label in the middle
        diceLabel = new JLabel();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.insets = new Insets(10, 50, 10, 50);
        controlPanel.add(diceLabel, gbc);

        // Player 2 button and icon
        rollButtonP2 = createSquareButton("P2");
        rollButtonP2.addActionListener(new RollDiceListener());
        rollButtonP2.setEnabled(false);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        controlPanel.add(rollButtonP2, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        JLabel p2ImageLabel = new JLabel(p2Icon);
        controlPanel.add(p2ImageLabel, gbc);

        add(controlPanel, BorderLayout.SOUTH);
    }

    private void initFlowersAndTraps() {
        flowers.put(3, 24);
        flowers.put(19, 61);
        flowers.put(29, 86);
        flowers.put(37, 65);
        flowers.put(72, 93);

        traps.put(14, 5);
        traps.put(33, 18);
        traps.put(58, 22);
        traps.put(66, 36);
        traps.put(77, 51);
        traps.put(92, 18);
        traps.put(99, 80);
    }

    // Create square buttons for players
    private JButton createSquareButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                super.paintComponent(g2);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(50, 50);
            }

            @Override
            public void setContentAreaFilled(boolean b) {
            }
        };
        button.setForeground(new Color(0, 100, 0));
        button.setFocusPainted(false);
        return button;
    }

    // Move player after dice roll and handle turn switching
    private void movePlayer(int diceValue) {
        if (isPlayer1Turn) {
            player1Pos = movePlayerToPosition(player1Pos, diceValue, p1Icon);
            rollButtonP1.setEnabled(false);
            rollButtonP2.setEnabled(true);
        } else {
            player2Pos = movePlayerToPosition(player2Pos, diceValue, p2Icon);
            rollButtonP1.setEnabled(true);
            rollButtonP2.setEnabled(false);
        }

        isPlayer1Turn = !isPlayer1Turn;
        checkForWinner();
    }

    // Move player to new position and check for traps or flowers
    private int movePlayerToPosition(int currentPosition, int diceValue, ImageIcon playerIcon) {
        int newPosition = currentPosition + diceValue;

        if (newPosition > TOTAL_CELLS) {
            newPosition = currentPosition;
        } else {
            if (flowers.containsKey(newPosition)) {
                newPosition = flowers.get(newPosition);
            } else if (traps.containsKey(newPosition)) {
                newPosition = traps.get(newPosition);
            }
        }

        updateBoard(currentPosition, newPosition, playerIcon);
        return newPosition;
    }

    // Update player position on the board
    private void updateBoard(int oldPosition, int newPosition, ImageIcon playerIcon) {
        cells[getZigzagIndex(oldPosition)].setIcon(null);
        cells[getZigzagIndex(newPosition)].setIcon(playerIcon);
    }

    // Calculate the correct index for zigzag movement on the board
    private int getZigzagIndex(int linearPosition) {
        int zeroBasedPos = linearPosition - 1;
        int row = BOARD_SIZE - 1 - (zeroBasedPos / BOARD_SIZE);
        int col = zeroBasedPos % BOARD_SIZE;

        if (row % 2 == 1) {
            return row * BOARD_SIZE + col;
        } else {
            return row * BOARD_SIZE + (BOARD_SIZE - 1 - col);
        }
    }

    // Check if a player has won the game
    private void checkForWinner() {
        if (player1Pos == TOTAL_CELLS) {
            JOptionPane.showMessageDialog(this, "Player 1 Wins!");
            restartGame();
        } else if (player2Pos == TOTAL_CELLS) {
            JOptionPane.showMessageDialog(this, "Player 2 Wins!");
            restartGame();
        }
    }

    // Reset the game to the initial state
    private void restartGame() {
        player1Pos = 1;
        player2Pos = 1;
        isPlayer1Turn = true;
        for (int i = 0; i < TOTAL_CELLS; i++) {
            cells[i].setIcon(null);
        }
        rollButtonP1.setEnabled(true);
        rollButtonP2.setEnabled(false);
        diceLabel.setIcon(null);
    }

    // Listener for rolling the dice
    private class RollDiceListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int diceValue = new Random().nextInt(6) + 1;
            diceLabel.setIcon(diceIcons[diceValue - 1]);
            movePlayer(diceValue);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TrapsAndFlowers game = new TrapsAndFlowers();
            game.setVisible(true);
        });
    }
}