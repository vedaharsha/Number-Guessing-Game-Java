import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class NumberGuessingGame {

    private int randomNumber;
    private int attempts;
    private final int maxAttempts = 10;

    private JTextField guessField;
    private JTextField scoreField;
    private JTextField totalGuessField;
    private JLabel messageLabel;

    public NumberGuessingGame() {

        // Generate random number between 1 and 100
        generateRandomNumber();
        attempts = 0;

        // Create JFrame
        JFrame frame = new JFrame("Guess the Number");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 350);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.PINK);
        frame.setLocationRelativeTo(null);

        // Title
        JLabel titleLabel = new JLabel("Guess the Number");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setBounds(125, 20, 250, 30);
        frame.add(titleLabel);

        // Instructions
        JLabel promptLabel = new JLabel("Enter a number between 1 - 100");
        promptLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        promptLabel.setBounds(120, 60, 220, 20);
        frame.add(promptLabel);

        // Guess input
        guessField = new JTextField();
        guessField.setBounds(180, 85, 80, 28);
        frame.add(guessField);

        // Check button
        JButton guessButton = new JButton("Check");
        guessButton.setBounds(170, 125, 100, 30);
        frame.add(guessButton);

        // Stop button
        JButton stopButton = new JButton("Stop");
        stopButton.setBounds(90, 170, 100, 30);
        frame.add(stopButton);

        // Play Again button
        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setBounds(210, 170, 120, 30);
        frame.add(playAgainButton);

        // Score label
        JLabel scoreLabel = new JLabel("Score:");
        scoreLabel.setBounds(120, 220, 80, 20);
        frame.add(scoreLabel);

        // Score field
        scoreField = new JTextField();
        scoreField.setBounds(210, 220, 70, 20);
        scoreField.setEditable(false);
        frame.add(scoreField);

        // Total guesses label
        JLabel totalGuessLabel = new JLabel("Total Guesses:");
        totalGuessLabel.setBounds(120, 250, 100, 20);
        frame.add(totalGuessLabel);

        // Total guesses field
        totalGuessField = new JTextField();
        totalGuessField.setBounds(230, 250, 50, 20);
        totalGuessField.setEditable(false);
        frame.add(totalGuessField);

        // Message
        messageLabel = new JLabel("Try and guess it!");
        messageLabel.setBounds(120, 285, 250, 20);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        frame.add(messageLabel);

        // Check button action
        guessButton.addActionListener(e -> makeGuess());

        // Press Enter to check guess
        guessField.addActionListener(e -> makeGuess());

        // Stop button
        stopButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    frame,
                    "Thanks for playing!",
                    "Game Over",
                    JOptionPane.INFORMATION_MESSAGE
            );
            System.exit(0);
        });

        // Play Again button
        playAgainButton.addActionListener(e -> resetGame());

        // Display window
        frame.setVisible(true);
    }

    // Generate random number
    private void generateRandomNumber() {
        Random random = new Random();
        randomNumber = random.nextInt(100) + 1;
    }

    // Handle user's guess
    private void makeGuess() {

        try {

            String input = guessField.getText().trim();

            if (input.isEmpty()) {
                messageLabel.setText("Please enter a number.");
                return;
            }

            int userGuess = Integer.parseInt(input);

            // Validate range
            if (userGuess < 1 || userGuess > 100) {
                messageLabel.setText("Enter a number between 1 and 100.");
                return;
            }

            // Count attempt
            attempts++;

            totalGuessField.setText(String.valueOf(attempts));

            // Correct guess
            if (userGuess == randomNumber) {

                int score = Math.max(10, 100 - (attempts - 1) * 10);

                messageLabel.setText(
                        "Congratulations! You guessed it!"
                );

                scoreField.setText(String.valueOf(score));

                guessField.setEnabled(false);

                JOptionPane.showMessageDialog(
                        null,
                        "You guessed the number in "
                                + attempts
                                + " attempts!\nScore: "
                                + score,
                        "Congratulations!",
                        JOptionPane.INFORMATION_MESSAGE
                );

            }

            // Close to the number
            else if (Math.abs(userGuess - randomNumber) <= 10) {

                messageLabel.setText("You're close! Try again.");

            }

            // Guess is too low
            else if (userGuess < randomNumber) {

                messageLabel.setText("Too low! Try a higher number.");

            }

            // Guess is too high
            else {

                messageLabel.setText("Too high! Try a lower number.");

            }

            // Maximum attempts reached
            if (attempts >= maxAttempts && userGuess != randomNumber) {

                messageLabel.setText(
                        "Game Over! Number was " + randomNumber
                );

                guessField.setEnabled(false);

                scoreField.setText("0");

                JOptionPane.showMessageDialog(
                        null,
                        "You've used all 10 attempts.\n"
                                + "The number was: "
                                + randomNumber,
                        "Game Over",
                        JOptionPane.WARNING_MESSAGE
                );
            }

        } catch (NumberFormatException ex) {

            messageLabel.setText(
                    "Please enter a valid number."
            );
        }
    }

    // Reset the game
    private void resetGame() {

        generateRandomNumber();

        attempts = 0;

        guessField.setEnabled(true);
        guessField.setText("");

        scoreField.setText("");

        totalGuessField.setText("0");

        messageLabel.setText(
                "Try and guess it!"
        );

        guessField.requestFocus();
    }

    // Main method
    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                NumberGuessingGame::new
        );
    }
}