import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {
    private JTextField inputField;
    private String operator = "";
    private double previousNumber = 0;

    public Calculator() {
        createUI();
    }

    private void createUI() {
        setTitle("Simple Calculator");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        inputField = new JTextField();
        add(inputField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4));
        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "C", "=", "+"
        };

        for (String buttonText : buttons) {
            JButton button = new JButton(buttonText);
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        add(buttonPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("C".equals(command)) {
            inputField.setText("");
            previousNumber = 0;
            operator = "";
        } else if (command.matches("[0-9]")) {
            inputField.setText(inputField.getText() + command);
        } else if (command.matches("[+\\-*/]")) {
            previousNumber = Double.parseDouble(inputField.getText());
            operator = command;
            inputField.setText("");
        } else if ("=".equals(command)) {
            double currentNumber = Double.parseDouble(inputField.getText());
            double result = CalculatorOperation.calculate(previousNumber, currentNumber, operator);
            inputField.setText(String.valueOf(result));
            operator = "";
            previousNumber = 0;
        }
    }
}
