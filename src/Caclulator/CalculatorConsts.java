package Caclulator;

import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

abstract class CalculatorConsts {
    static final Locale locale = Locale.getDefault(Locale.Category.FORMAT);
    static final NumberFormat NumberFormater = NumberFormat.getInstance(locale);
    static final DecimalFormatSymbols dfs = new DecimalFormatSymbols(locale);

    static final char PlusSymbol = '+', MinusSymbol = '-', NegativeSymbol = MinusSymbol, DivisionSymbol = '/',
            MulSymbol = '*', EqualSymbol = '=', DecimalSeperator = dfs.getDecimalSeparator();

    static final String ReadableDivSym = "÷", ReadableMulSym = "×";
}
