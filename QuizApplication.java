import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizApplication {
    private JFrame frame;
    private JPanel questionPanel;
    private ButtonGroup buttonGroup;
    private JRadioButton[] options;
    private JButton nextButton, previousButton;
    private JLabel questionLabel;

    private String[] questions = {
        "What is the capital city of France?",
        "Who developed the theory of relativity?",
        "Which language is the most spoken in the world by native speakers?",
        "Which planet is known as the Red Planet?",
        "What is the most popular sport in the world?"
    };

    private String[][] optionsData = {
        {"Madrid", "Berlin", "Paris", "Rome"},
        {"Isaac Newton", "Albert Einstein", "Galileo Galilei", "Nikola Tesla"},
        {"English", "Hindi", "Mandarin Chinese", "Spanish"},
        {"Earth", "Mars", "Jupiter", "Saturn"},
        {"Basketball", "Cricket", "Football (Soccer)", "Tennis"}
    };


    private int[] correctAnswers = {2, 1, 2, 1, 1}; // Correct options indices
    private int currentQuestion = 0;
    private int score = 0;
    private int[] userAnswers; // To track user's selected answers

    public QuizApplication() {
        frame = new JFrame("Quiz Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        questionPanel = new JPanel();
        questionPanel.setLayout(new GridLayout(5, 1));

        questionLabel = new JLabel("", JLabel.CENTER);
        questionPanel.add(questionLabel);

        buttonGroup = new ButtonGroup();
        options = new JRadioButton[4];
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            buttonGroup.add(options[i]);
            questionPanel.add(options[i]);
        }

        userAnswers = new int[questions.length];
        for (int i = 0; i < userAnswers.length; i++) {
            userAnswers[i] = -1; // Initialize with -1 to indicate no answer
        }

        nextButton = new JButton("Next");
        previousButton = new JButton("Previous");
        JPanel navigationPanel = new JPanel();
        navigationPanel.add(previousButton);
        navigationPanel.add(nextButton);

        frame.add(questionPanel, BorderLayout.CENTER);
        frame.add(navigationPanel, BorderLayout.SOUTH);

        loadQuestion();

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAnswer();
                if (currentQuestion < questions.length - 1) {
                    currentQuestion++;
                    loadQuestion();
                } else {
                    calculateScore();
                    JOptionPane.showMessageDialog(frame, "Quiz completed! Your score: " + score);
                    System.exit(0);
                }
            }
        });

        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAnswer();
                if (currentQuestion > 0) {
                    currentQuestion--;
                    loadQuestion();
                }
            }
        });

        frame.setVisible(true);
    }

    private void loadQuestion() {
        questionLabel.setText((currentQuestion + 1) + ". " + questions[currentQuestion]);
        String[] currentOptions = optionsData[currentQuestion];
        for (int i = 0; i < options.length; i++) {
            options[i].setText(currentOptions[i]);
            options[i].setSelected(userAnswers[currentQuestion] == i);
        }
        previousButton.setEnabled(currentQuestion > 0);
        nextButton.setText(currentQuestion == questions.length - 1 ? "Submit" : "Next");
    }

    private void saveAnswer() {
        for (int i = 0; i < options.length; i++) {
            if (options[i].isSelected()) {
                userAnswers[currentQuestion] = i;
                break;
            }
        }
    }

    private void calculateScore() {
        score = 0;
        for (int i = 0; i < questions.length; i++) {
            if (userAnswers[i] == correctAnswers[i]) {
                score++;
            }
        }
    }

    public static void main(String[] args) {
        new QuizApplication();
    }
}
