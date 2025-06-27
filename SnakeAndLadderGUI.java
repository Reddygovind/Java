import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SnakeAndLadderGUI extends JFrame {
    private JPanel boardPanel;
    private JLabel player1Label, player2Label, diceLabel, statusLabel;
    private JButton rollButton;
    private int[] positions = {0, 0};
    private int currentPlayer = 0;
    private Map<Integer, Integer> snakes = new HashMap<>();
    private Map<Integer, Integer> ladders = new HashMap<>();
    private Random random = new Random();

    public SnakeAndLadderGUI() {
        setTitle("Snake and Ladder Game");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setupBoard();

        boardPanel = new BoardPanel();
        add(boardPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        player1Label = new JLabel("Player 1: 0");
        player2Label = new JLabel("Player 2: 0");
        diceLabel = new JLabel();
        diceLabel.setPreferredSize(new Dimension(60, 60));
        statusLabel = new JLabel("Player 1's turn");

        rollButton = new JButton("Roll Dice");
        rollButton.addActionListener(e -> rollDice());

        controlPanel.add(player1Label);
        controlPanel.add(player2Label);
        controlPanel.add(diceLabel);
        controlPanel.add(rollButton);
        controlPanel.add(statusLabel);

        add(controlPanel, BorderLayout.SOUTH);
    }

    private void setupBoard() {
        ladders.put(3, 22);
        ladders.put(5, 8);
        ladders.put(11, 26);
        ladders.put(20, 29);

        snakes.put(27, 1);
        snakes.put(21, 9);
        snakes.put(17, 4);
        snakes.put(19, 7);
    }

    private void rollDice() {
        int roll = random.nextInt(6) + 1;
        showDiceImage(roll);

        int pos = positions[currentPlayer] + roll;
        if (pos <= 30) {
            pos = checkSnakesAndLadders(pos);
            positions[currentPlayer] = pos;
        }

        player1Label.setText("Player 1: " + positions[0]);
        player2Label.setText("Player 2: " + positions[1]);
        boardPanel.repaint();

        if (positions[currentPlayer] == 30) {
            statusLabel.setText("Player " + (currentPlayer + 1) + " wins!");
            rollButton.setEnabled(false);
            return;
        }

        currentPlayer = 1 - currentPlayer;
        statusLabel.setText("Player " + (currentPlayer + 1) + "'s turn");
    }

    private int checkSnakesAndLadders(int pos) {
        if (ladders.containsKey(pos)) {
            JOptionPane.showMessageDialog(this, "Ladder! Go up to " + ladders.get(pos));
            return ladders.get(pos);
        } else if (snakes.containsKey(pos)) {
            JOptionPane.showMessageDialog(this, "Snake! Go down to " + snakes.get(pos));
            return snakes.get(pos);
        }
        return pos;
    }

    private void showDiceImage(int number) {
        ImageIcon icon = new ImageIcon("resources/dice" + number + ".png");
        diceLabel.setIcon(new ImageIcon(icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
    }

    private class BoardPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int size = 60;

            // Draw cells
            for (int i = 0; i < 30; i++) {
                int x = (i % 6) * size;
                int y = (i / 6) * size;
                g.drawRect(x, y, size, size);
                g.drawString(String.valueOf(i + 1), x + 20, y + 20);
            }

            // Draw players
            for (int i = 0; i < 2; i++) {
                int pos = positions[i];
                if (pos > 0) {
                    int index = pos - 1;
                    int x = (index % 6) * size + 10 + (i * 20);
                    int y = (index / 6) * size + 30;
                    g.setColor(i == 0 ? Color.RED : Color.BLUE);
                    g.fillOval(x, y, 20, 20);
                }
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(360, 360);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SnakeAndLadderGUI game = new SnakeAndLadderGUI();
            game.setVisible(true);
        });
    }
}
