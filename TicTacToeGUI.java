
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToeGUI extends JFrame implements ActionListener {

    private JButton[][] buttons = new JButton[3][3];
    private char currentPlayer = 'X';
    private boolean gameOver = false;

    private JLabel statusLabel;
    private JButton resetButton;

    public TicTacToeGUI() {
        setTitle("NEON TIC TAC TOE");
        setSize(430, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(new Color(0, 10, 30));

        statusLabel = new JLabel("Player X's Turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 24));
        statusLabel.setForeground(new Color(0, 255, 100));
        add(statusLabel, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));
        panel.setBackground(new Color(0, 10, 40));

        Font f = new Font("Arial", Font.BOLD, 60);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(f);
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].setBackground(new Color(0, 0, 20));
                buttons[i][j].setForeground(new Color(0, 255, 100));
                buttons[i][j].setBorder(
                    BorderFactory.createLineBorder(new Color(0, 255, 255), 2)
                );
                buttons[i][j].addActionListener(this);
                panel.add(buttons[i][j]);
            }
        }

        add(panel, BorderLayout.CENTER);

        resetButton = new JButton("RESET GAME");
        resetButton.setFont(new Font("Arial", Font.BOLD, 20));
        resetButton.setBackground(Color.BLACK);
        resetButton.setForeground(new Color(0, 255, 100));
        resetButton.setFocusPainted(false);
        resetButton.addActionListener(e -> resetGame());

        add(resetButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) return;

        JButton btn = (JButton) e.getSource();

        if (!btn.getText().equals("")) return;

        btn.setText(String.valueOf(currentPlayer));
        glowEffect(btn);

        if (checkWinner()) {
            statusLabel.setText("Player " + currentPlayer + " Wins!");
            flashWinner();
            gameOver = true;
        }
        else if (isBoardFull()) {
            statusLabel.setText("Match Draw!");
            gameOver = true;
        }
        else {
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            statusLabel.setText("Player " + currentPlayer + "'s Turn");
        }
    }

    private boolean checkWinner() {
        for (int i = 0; i < 3; i++)
            if (same(buttons[i][0], buttons[i][1], buttons[i][2])) return true;

        for (int i = 0; i < 3; i++)
            if (same(buttons[0][i], buttons[1][i], buttons[2][i])) return true;

        if (same(buttons[0][0], buttons[1][1], buttons[2][2])) return true;
        if (same(buttons[0][2], buttons[1][1], buttons[2][0])) return true;

        return false;
    }

    private boolean same(JButton b1, JButton b2, JButton b3) {
        String s1 = b1.getText();
        return (!s1.equals("") && s1.equals(b2.getText()) && s1.equals(b3.getText()));
    }

    private boolean isBoardFull() {
        for (JButton[] row : buttons)
            for (JButton b : row)
                if (b.getText().equals("")) return false;
        return true;
    }

    private void resetGame() {
        currentPlayer = 'X';
        gameOver = false;
        statusLabel.setText("Player X's Turn");

        for (JButton[] row : buttons)
            for (JButton b : row) {
                b.setText("");
                b.setBackground(new Color(0, 0, 20));
            }
    }

    private void glowEffect(JButton btn) {
        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    btn.setBackground(new Color(0, 255, 100));
                    Thread.sleep(80);
                    btn.setBackground(new Color(0, 0, 20));
                    Thread.sleep(80);
                }
                btn.setBackground(new Color(0, 0, 40));
            } catch (Exception ignored) {}
        }).start();
    }

    private void flashWinner() {
        new Thread(() -> {
            try {
                for (int x = 0; x < 5; x++) {
                    getContentPane().setBackground(Color.BLACK);
                    Thread.sleep(120);
                    getContentPane().setBackground(new Color(0, 10, 30));
                    Thread.sleep(120);
                }
            } catch (Exception ignored) {}
        }).start();
    }

    public static void main(String[] args) {
        new TicTacToeGUI();
    }
}