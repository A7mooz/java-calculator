package Caclulator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Calculator extends CalculatorConsts {
    final JFrame frame;
    private final JTextField textField;
    private final JTextField recent;
    final JButton[] numberButtons = new JButton[10];
    private final JButton[] functionButtons = new JButton[9];
    final JButton addButton, subButton, mulButton, divButton;
    final JButton decButton, eqButton, delButton, clrButton, negButton;
    private final JPanel panel;

    final HelpMenu helpMenu;
    private final JTextArea HelpText;

    static final Font myFont = new Font("Arial", Font.BOLD, 25);

    private static final ArrayList<String> equations = new ArrayList<String>();

    private final ButtonListener buttonListener = new ButtonListener(this);
    private final KeyboardListener keyboardListener = new KeyboardListener(this);

    private boolean Errored = false;

    public Calculator() {
        frame = new JFrame("Caclulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 560);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setResizable(false);
        frame.setLayout(null);

        recent = new JTextField();
        recent.setBounds(50, 1, 300, 25);
        recent.setFont(myFont.deriveFont(Font.PLAIN, 17));
        recent.setEditable(false);
        recent.setCursor(null);
        recent.setForeground(Color.WHITE);
        recent.setBackground(null);
        recent.setBorder(null);
        recent.setCaretColor(Color.black);
        recent.addKeyListener(keyboardListener);

        textField = new JTextField();
        textField.setBounds(50, 32, 300, 50);
        textField.setFont(myFont);
        textField.addKeyListener(keyboardListener);
        textField.setEditable(false);
        textField.setCaretColor(Color.WHITE);
        textField.setActionMap(null);

        HelpText = new JTextArea("Press 'i' for help.");
        HelpText.setBounds(50, 490, 150, 25);
        HelpText.setFont(myFont.deriveFont(Font.PLAIN, 15));
        HelpText.setEditable(false);
        HelpText.setForeground(Color.WHITE);
        HelpText.setBackground(null);
        HelpText.setBorder(null);
        HelpText.addKeyListener(keyboardListener);

        functionButtons[0] = addButton = new JButton(String.valueOf(PlusSymbol));
        functionButtons[1] = subButton = new JButton(String.valueOf(MinusSymbol));
        functionButtons[2] = mulButton = new JButton(ReadableMulSym);
        functionButtons[3] = divButton = new JButton(ReadableDivSym);
        functionButtons[4] = decButton = new JButton(String.valueOf(DecimalSeperator));
        functionButtons[5] = eqButton = new JButton(String.valueOf(EqualSymbol));
        functionButtons[6] = delButton = new JButton("Del");
        functionButtons[7] = clrButton = new JButton("Clear");
        functionButtons[8] = negButton = new JButton("(" + NegativeSymbol + ")");

        for (int i = 0; i < functionButtons.length; i++) {
            functionButtons[i].addActionListener(buttonListener);
            functionButtons[i].setFont(myFont);
            functionButtons[i].setBorder(BorderFactory.createLineBorder(Color.black));
            functionButtons[i].setFocusable(false);
            functionButtons[i].setBackground(Color.GRAY);
            functionButtons[i].setForeground(Color.WHITE);
        }

        for (int i = 0; i < numberButtons.length; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(buttonListener);
            numberButtons[i].setFont(myFont);
            numberButtons[i].setBorder(null);
            numberButtons[i].setFocusable(false);
            numberButtons[i].setBackground(Color.GRAY);
            numberButtons[i].setForeground(Color.WHITE);
        }

        negButton.setBounds(50, 430, 100, 50);
        delButton.setBounds(150, 430, 100, 50);
        clrButton.setBounds(250, 430, 100, 50);

        panel = new JPanel();
        panel.setBounds(50, 100, 300, 300);
        panel.setLayout(new GridLayout(4, 4, 10, 10));
        panel.addKeyListener(keyboardListener);
        panel.setBackground(null);

        helpMenu = new HelpMenu(this);

        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);

        panel.add(addButton);

        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);

        panel.add(subButton);

        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);

        panel.add(mulButton);
        panel.add(decButton);

        panel.add(numberButtons[0]);

        panel.add(eqButton);
        panel.add(divButton);

        frame.add(textField);
        frame.add(recent);
        frame.add(panel);
        frame.add(negButton);
        frame.add(delButton);
        frame.add(clrButton);
        frame.add(HelpText);
        frame.setVisible(true);
        textField.requestFocusInWindow();
    }

    static final short MAX_TEXT_LENGTH = 18;

    static final long MAX_NUMBER = (long)Math.pow(10, MAX_TEXT_LENGTH - 2);

    static final short MAX_DECIMAL_DIGITS = 15;

    void handleErr(String errorMessage) {
        Errored = true;
        recent.setText(errorMessage);
        textField.setText("");
    }

    private double getNumber() throws Exception {
        return NumberFormater.parse(textField.getText()).doubleValue();
    }

    private String format(double number, int scale) {
        if (scale >= MAX_DECIMAL_DIGITS)
            scale = MAX_DECIMAL_DIGITS;

        if (number > MAX_NUMBER) return NumberFormater.format(number);

        return String.format(locale, "%,." + scale + "f", number).toString();
    }

    private String format(double number) {
        int scale = 0;

        String str = String.valueOf(number);
        final String[] split = str.split("\\" + DecimalSeperator);
        scale = split.length == 2 ? split[1].length() : 0;

        if (Double.parseDouble(String.format("%.0f", number)) ==  number) scale = 0;

        return format(number, scale);
    }

    void appendNumber(int number) {
        if (Errored) {
            if (equations.size() <= 1)
                recent.setText("");
            else {
                recent.setText(equationToText());
            }

            Errored = false;
        }

        try {
            textField.setCaretPosition(textField.getText().length());
            if (textField.getText().length() > MAX_TEXT_LENGTH)
                return;

            final String NewTxt = textField.getText() + number;
            final double num = NumberFormater.parse(NewTxt).doubleValue();

            final String[] split = NewTxt.split("\\" + DecimalSeperator);

            final int scale = split.length == 2 ? split[1].length() : 0;

            if (scale >= MAX_DECIMAL_DIGITS) return;

            textField.setText(format(num, scale));
        } catch (Exception e) {
            handleErr("Error: Invalid number.");
            e.printStackTrace();
        }
    }

    private String equationToText() {
        String txt = "";

        for (String string : equations) {
            try {
                txt += format(Double.parseDouble(string));
            } catch (Exception e) {
                if (string.equals(String.valueOf(MulSymbol)))
                    txt += ReadableMulSym;
                else if (string.equals(String.valueOf(DivisionSymbol)))
                    txt += ReadableDivSym;
                else
                    txt += string;
            }
            txt += ' ';
        }

        return txt;
    }

    private void operate(char operation) {
        if (!validate(operation))
            return;

        try {
            if (!equations.isEmpty() && getNumber() == 0
                    && equations.get(equations.size() - 1).equals(String.valueOf(DivisionSymbol))) {
                handleErr("Math Error: division by zero.");
                return;
            }

            equations.add(String.valueOf(getNumber()));
            equations.add(String.valueOf(operation));

            recent.setText(equationToText());
            textField.setText("");
        } catch (Exception e) {
            handleErr("Error: Invalid number.");
            e.printStackTrace();
        }
    }

    private boolean validate(char operation) {
        if (textField.getText().isEmpty()) {
            if (equations.isEmpty())
                return false;

            equations.set(equations.size() - 1, String.valueOf(operation));
            recent.setText(equationToText());
            return false;
        }

        return true;
    }

    void add() {
        operate(PlusSymbol);
    }

    void subtract() {
        operate(MinusSymbol);
    }

    void divide() {
        operate(DivisionSymbol);
    }

    void multiply() {
        operate(MulSymbol);
    }

    void negative() {
        if (Errored) {
            if (equations.size() <= 1)
                recent.setText("");
            else {
                recent.setText(equationToText());
            }

            Errored = false;
        }

        final String text = textField.getText();

        if (text.startsWith(String.valueOf(NegativeSymbol)))
            textField.setText(text.substring(1, text.length()));
        else
            textField.setText(NegativeSymbol + text);
    }

    void decimal() {
        if (textField.getText().length() >= MAX_TEXT_LENGTH)
            return;

        if (Errored) {
            if (equations.size() <= 1)
                recent.setText("");
            else {
                recent.setText(equationToText());
            }

            Errored = false;
        }

        if (textField.getText().contains(String.valueOf(DecimalSeperator)))
            return;

        textField.setText(textField.getText() + DecimalSeperator);
    }

    void delete() {
        String str = textField.getText();

        if (str.isEmpty()) {
            if (equations.size() < 2)
                return;

            try {
                textField.setText(format(Double.parseDouble(equations.get(equations.size() - 2))));
                equations.remove(equations.size() - 1);
                equations.remove(equations.size() - 1);

                recent.setText(equationToText());
            } catch (Exception e) {
                e.printStackTrace();
                equations.clear();
                recent.setText("");
            }

            return;
        }

        if (equations.size() <= 1)
            recent.setText("");

        try {
            str = str.substring(0, str.length() - 1);
            if (!str.isEmpty()) {
                try {
                    double num = NumberFormater.parse(str).doubleValue();
                    String[] split = str.split("\\" + DecimalSeperator);

                    int scale = split.length == 2 ? split[1].length() : 0;

                    textField.setText(format(num, scale));
                } catch (Exception e) {
                    textField.setText(str);
                }
            } else
                textField.setText("");
        } catch (Exception e) {
            handleErr("Error: Invalid number.");
            e.printStackTrace();
        }
    }

    void clear() {
        equations.clear();
        recent.setText("");
        textField.setText("");
    }

    void equal() {
        if (equations.size() <= 1) {
            if (!recent.getText().isEmpty() && !recent.getText().endsWith(String.valueOf(EqualSymbol))) {
                try {
                    textField.setText(format(Double.parseDouble(equations.get(0))));
                    textField.setCaretPosition(0);
                    recent.setText(recent.getText() + " " + EqualSymbol);
                } catch (Exception e) {
                    handleErr("Error: Invalid number.");
                    e.printStackTrace();
                }
            }

            equations.clear();

            return;
        }

        int index = equations.indexOf(String.valueOf(DivisionSymbol));

        double num1 = 0;
        double num2 = 0;
        double result = 0;

        if (index >= 0 || (index = equations.indexOf(String.valueOf(MulSymbol))) >= 0) {
            char operation = equations.get(index).charAt(0);

            double[] nums = findNumbers(index, num1, num2);

            if (nums.length < 2)
                return;

            num1 = nums[0];
            num2 = nums[1];

            switch (operation) {
                case '/':
                    if (num2 == 0) {
                        handleErr("Math Error: division by zero.");
                        return;
                    }
                    result = num1 / num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
            }
        } else if ((index = equations.indexOf(String.valueOf(PlusSymbol))) > 0) {
            double[] nums = findNumbers(index, num1, num2);

            if (nums.length < 2)
                return;

            num1 = nums[0];
            num2 = nums[1];

            result = num1 + num2;
        } else if ((index = equations.indexOf(String.valueOf(MinusSymbol))) > 0) {
            double[] nums = findNumbers(index, num1, num2);

            if (nums.length < 2)
                return;

            num1 = nums[0];
            num2 = nums[1];

            result = num1 - num2;
        }

        equations.remove(index);
        equations.set(index - 1, String.valueOf(result));

        equal();
    }

    private double[] findNumbers(int index, double num1, double num2) {
        num1 = Double.parseDouble(equations.get(index - 1));
        try {
            num2 = Double.parseDouble(equations.get(index + 1));
            equations.remove(index + 1);
        } catch (Exception e) {
            try {
                num2 = getNumber();
                recent.setText(recent.getText() + format(num2));
            } catch (Exception err) {
                try {
                    textField.setText(format(Double.parseDouble(equations.get(0))));
                    textField.setCaretPosition(0);
                    recent.setText("");
                    equations.clear();
                } catch (Exception error) {
                    error.printStackTrace();
                }

                return new double[0];
            }
        }

        double[] numbers = { num1, num2 };

        return numbers;
    }

    /**
     * Closes the program.
     */
    public void close() {
        helpMenu.dispose();
        frame.dispose();
        System.exit(0);
    }
}