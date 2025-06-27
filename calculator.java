import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class calculator {
    int boardWidth = 360;
    int boardHeight = 540;

    Color customLightGray = new Color(212, 212, 210);
    Color customDarkGray = new Color(80, 80, 80);
    Color customBlack = new Color(28, 28, 28);
    Color customOrange = new Color(255, 149, 0);

    String[] buttonValues = {
        "AC", "+/-", "%", "÷", 
        "7", "8", "9", "×", 
        "4", "5", "6", "-", 
        "1", "2", "3", "+", 
        "0", ".", "√", "="
    };
    String[] rightSymbols = {"÷", "×", "-", "+", "="};
    String[] topSymbols = {"AC", "+/-", "%", "√"};

    JFrame frame = new JFrame("Calculator");
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    String A = "0";
    String operator = null;
    String B = null;

    calculator() {
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        displayLabel.setBackground(customBlack);
        displayLabel.setForeground(Color.white);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 60));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);
        frame.add(displayPanel, BorderLayout.NORTH);

        buttonsPanel.setLayout(new GridLayout(5, 4));
        buttonsPanel.setBackground(customBlack);
        frame.add(buttonsPanel);

        for (String buttonValue : buttonValues) {
            JButton button = new JButton(buttonValue);
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setFocusable(false);
            button.setBorder(new LineBorder(customBlack));

            if (Arrays.asList(topSymbols).contains(buttonValue)) {
                button.setBackground(customLightGray);
                button.setForeground(customBlack);
            } else if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                button.setBackground(customOrange);
                button.setForeground(Color.white);
            } else {
                button.setBackground(customDarkGray);
                button.setForeground(Color.white);
            }

            buttonsPanel.add(button);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String buttonValue = button.getText();
                    String currentText = displayLabel.getText();

                    if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                        if (buttonValue.equals("=")) {
                            if (A != null && operator != null) {
                                B = currentText;
                                double numA = Double.parseDouble(A);
                                double numB = Double.parseDouble(B);

                                switch (operator) {
                                    case "+":
                                        displayLabel.setText(removeZeroDecimal(numA + numB));
                                        break;
                                    case "-":
                                        displayLabel.setText(removeZeroDecimal(numA - numB));
                                        break;
                                    case "×":
                                        displayLabel.setText(removeZeroDecimal(numA * numB));
                                        break;
                                    case "÷":
                                        if (numB == 0) {
                                            displayLabel.setText("Err");
                                        } else {
                                            displayLabel.setText(removeZeroDecimal(numA / numB));
                                        }
                                        break;
                                }
                                clearAll();
                            }
                        } else {
                            A = currentText;
                            operator = buttonValue;
                            displayLabel.setText("0");
                        }
                    } else if (Arrays.asList(topSymbols).contains(buttonValue)) {
                        switch (buttonValue) {
                            case "AC":
                                clearAll();
                                displayLabel.setText("0");
                                break;
                            case "+/-":
                                double num = Double.parseDouble(currentText);
                                num *= -1;
                                displayLabel.setText(removeZeroDecimal(num));
                                break;
                            case "%":
                                double numPercent = Double.parseDouble(currentText);
                                numPercent /= 100;
                                displayLabel.setText(removeZeroDecimal(numPercent));
                                break;
                            case "√":
                                double numRoot = Double.parseDouble(currentText);
                                if (numRoot >= 0) {
                                    displayLabel.setText(removeZeroDecimal(Math.sqrt(numRoot)));
                                } else {
                                    displayLabel.setText("Err");
                                }
                                break;
                        }
                    } else {
                        if (buttonValue.equals(".")) {
                            if (!currentText.contains(".")) {
                                displayLabel.setText(currentText + ".");
                            }
                        } else {
                            if (currentText.equals("0")) {
                                displayLabel.setText(buttonValue);
                            } else {
                                displayLabel.setText(currentText + buttonValue);
                            }
                        }
                    }
                }
            });
        }

        frame.setVisible(true); // Only called once
    }

    void clearAll() {
        A = "0";
        operator = null;
        B = null;
    }

    String removeZeroDecimal(double numDisplay) {
        if (numDisplay % 1 == 0) {
            return Integer.toString((int) numDisplay);
        }
        return Double.toString(numDisplay);
    }

    public static void main(String[] args) {
        new calculator(); // Run the calculator
    }
}
