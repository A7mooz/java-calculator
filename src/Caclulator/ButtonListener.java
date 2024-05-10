package Caclulator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ButtonListener implements ActionListener {
    private final Calculator calculator;
    ButtonListener(Calculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calculator.addButton) {
            calculator.add();
            return;
        }

        if (e.getSource() == calculator.subButton) {
            calculator.subtract();
            return;
        }

        if (e.getSource() == calculator.divButton) {
            calculator.divide();
            return;
        }

        if (e.getSource() == calculator.mulButton) {
            calculator.multiply();
            return;
        }

        
        if (e.getSource() == calculator.eqButton) {
            calculator.equal();
            return;
        }

        if (e.getSource() == calculator.delButton) {
            calculator.delete();
            return;
        }

        if (e.getSource() == calculator.decButton) {
            calculator.decimal();
            return;
        }

        if (e.getSource() == calculator.clrButton) {
            calculator.clear();
            return;
        }

        if (e.getSource() == calculator.negButton) {
            calculator.negative();
            return;
        }

        for (int i = 0; i < calculator.numberButtons.length; i++) {
            if (e.getSource() == calculator.numberButtons[i]) {
                calculator.appendNumber(i);
                return;
            }
        }
    }
}
