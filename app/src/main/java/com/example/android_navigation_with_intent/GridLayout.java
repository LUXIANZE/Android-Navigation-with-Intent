package com.example.android_navigation_with_intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GridLayout extends AppCompatActivity {

    private String expression = "";
    TextView textView;
    private final View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button btn = (Button) v;
            if (btn != null){
                String input = (String) btn.getText();
                if (input.equalsIgnoreCase("X")){
                    expression += "*";
                }else if (input.equalsIgnoreCase("÷")){
                    expression += "/";
                }else{
                    expression += input;
                }
            }
            textView.setText(expression);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_layout);

        Button btn0 = findViewById(R.id.Btn0);
        Button Btn1 = findViewById(R.id.Btn1);
        Button Btn2 = findViewById(R.id.Btn2);
        Button Btn3 = findViewById(R.id.Btn3);
        Button Btn4 = findViewById(R.id.Btn4);
        Button Btn5 = findViewById(R.id.Btn5);
        Button Btn6 = findViewById(R.id.Btn6);
        Button Btn7 = findViewById(R.id.Btn7);
        Button Btn8 = findViewById(R.id.Btn8);
        Button Btn9 = findViewById(R.id.Btn9);

        Button BtnPlus     = findViewById(R.id.BtnPlus);
        Button BtnSubtract = findViewById(R.id.BtnSubtract);
        Button BtnMultiply = findViewById(R.id.BtnMultiply);
        Button BtnDivide   = findViewById(R.id.BtnDivide);
        Button BtnDecimal  = findViewById(R.id.BtnDecimal);

        textView = findViewById(R.id.textView1);

        btn0.setOnClickListener(buttonListener);
        Btn1.setOnClickListener(buttonListener);
        Btn2.setOnClickListener(buttonListener);
        Btn3.setOnClickListener(buttonListener);
        Btn4.setOnClickListener(buttonListener);
        Btn5.setOnClickListener(buttonListener);
        Btn6.setOnClickListener(buttonListener);
        Btn7.setOnClickListener(buttonListener);
        Btn8.setOnClickListener(buttonListener);
        Btn9.setOnClickListener(buttonListener);
        BtnPlus.setOnClickListener(buttonListener);
        BtnSubtract.setOnClickListener(buttonListener);
        BtnMultiply.setOnClickListener(buttonListener);
        BtnDivide.setOnClickListener(buttonListener);
        BtnDecimal.setOnClickListener(buttonListener);
    }

    public void EqualBtnClicked(View view) {
        String result = "";
        try {
            if (expression.isEmpty()){
                throw new Exception("No expression!");
            }
            result = String.valueOf(eval(expression));
        }catch (Exception ex){
            Context context = getApplicationContext();
            CharSequence text = ex.getMessage();
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        textView.setText(result);
        expression = result;
    }

    private double eval(String str){
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    switch (func) {
                        case "sqrt":
                            x = Math.sqrt(x);
                            break;
                        case "sin":
                            x = Math.sin(Math.toRadians(x));
                            break;
                        case "cos":
                            x = Math.cos(Math.toRadians(x));
                            break;
                        case "tan":
                            x = Math.tan(Math.toRadians(x));
                            break;
                        default:
                            throw new RuntimeException("Unknown function: " + func);
                    }
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

    public void backToHomeBtnClicked(View view) {
        super.onBackPressed();
    }

    public void clearBtnClicked(View view) {
        expression = "";
        textView.setText(expression);
    }
}