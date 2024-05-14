package Caclulator;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JTextArea;

class HelpMenu extends JFrame implements KeyListener {
    private final Font myFont = Calculator.myFont.deriveFont(Font.PLAIN, 15);

    Calculator calculator;

    HelpMenu(Calculator calculator) {
        this.calculator = calculator;
        setTitle("Help Menu");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(calculator.frame.getX(), calculator.frame.getY(),500, 500);
        getContentPane().setBackground(Color.BLACK);
        setResizable(false);
        setLayout(null);

        final JTextArea help = new JTextArea();
        help.setBounds(1, 1, 500, 500);
        help.setFont(myFont);
        help.setEditable(false);
        help.setForeground(Color.WHITE);
        help.setBackground(null);
        help.setBorder(null);

        String txt;

        txt = "- Pressing numbers from 0-9 on your keyboard will type them.";
        txt += "\n- Pressing '" + Calculator.PlusSymbol + "' will invoke Addition.";
        txt += "\n- Pressing '" + Calculator.MinusSymbol + "' will invoke Subtraction.";
        txt += "\n- Pressing '" + Calculator.DivisionSymbol + "' will invoke Division.";
        txt += "\n- Pressing '" + Calculator.MulSymbol + "' will invoke Multiplication.";
        txt += "\n- Pressing '" + Calculator.EqualSymbol + "' or 'Enter' will show the result.";
        txt += "\n- Pressing ',' or '.' will add decimal seperator depending on your\nlocale settings. (Currently set to '"
                + Calculator.DecimalSeperator + "')";
        txt += "\n- Pressing 'DEL' or 'BACKSPACE' will delete the last thing you wrote\nor edit the recent number above.";
        txt += "\n- Pressing UPPERCASE 'C' will clear everything.";
        txt += "\n- Pressing 'n' will make the current number negative.";
        txt += "\n- Pressing 'ESC' will close this window or if it's not open it'll exit\nthe program.";
        txt += "\n- Pressing 'i' will show this.";

        help.setText(txt);

        help.addKeyListener(this);
        addKeyListener(this);

        add(help);
    }

    void open() {
        setBounds(calculator.frame.getX(), calculator.frame.getY(),500, 500);
        setExtendedState(JFrame.NORMAL);
        setVisible(true);
        requestFocus();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // ignore
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // ignore
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ESCAPE)
            dispose();
    }
}
