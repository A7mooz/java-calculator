package Caclulator;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class KeyboardListener implements KeyListener {
    private final Calculator calculator;

    KeyboardListener(Calculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar()) {
            case Calculator.PlusSymbol:
                calculator.add();
                break;
            case Calculator.MinusSymbol:
                calculator.subtract();
                break;
            case Calculator.DivisionSymbol:
                calculator.divide();
                break;
            case Calculator.MulSymbol:
                calculator.multiply();
                break;
            case Calculator.EqualSymbol:
            case KeyEvent.VK_ENTER:
                calculator.equal();
                break;
            case ',':
            case '.':
                calculator.decimal();
                break;
            case KeyEvent.VK_DELETE:
            case KeyEvent.VK_BACK_SPACE:
                calculator.delete();
                break;
            case 'C':
                calculator.clear();
                break;
            case 'n':
                calculator.negative();
                break;
            case 'i':
            case 'ı':
                calculator.helpMenu.open();
                break;
            default:
                if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9')
                    calculator.appendNumber(Integer.parseInt(String.valueOf(e.getKeyChar())));
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Handle when key typed
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ESCAPE)
            calculator.close();
    }
}
