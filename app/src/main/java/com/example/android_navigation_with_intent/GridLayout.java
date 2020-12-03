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
    private Button Btn0, Btn1, Btn2, Btn3, Btn4, Btn5, Btn6, Btn7, Btn8, Btn9, BtnPlus, BtnSubtract, BtnMultiply, BtnDivide, BtnEqual, BtnDecimal;
    TextView textView;
    private View.OnClickListener buttonListener = new View.OnClickListener() {
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

        Btn0 = (Button) findViewById(R.id.Btn0);
        Btn1 = (Button) findViewById(R.id.Btn1);
        Btn2 = (Button) findViewById(R.id.Btn2);
        Btn3 = (Button) findViewById(R.id.Btn3);
        Btn4 = (Button) findViewById(R.id.Btn4);
        Btn5 = (Button) findViewById(R.id.Btn5);
        Btn6 = (Button) findViewById(R.id.Btn6);
        Btn7 = (Button) findViewById(R.id.Btn7);
        Btn8 = (Button) findViewById(R.id.Btn8);
        Btn9 = (Button) findViewById(R.id.Btn9);

        BtnPlus     = (Button) findViewById(R.id.BtnPlus);
        BtnSubtract = (Button) findViewById(R.id.BtnSubtract);
        BtnMultiply = (Button) findViewById(R.id.BtnMultiply);
        BtnDivide   = (Button) findViewById(R.id.BtnDivide);
        BtnEqual    = (Button) findViewById(R.id.BtnEqual);
        BtnDecimal  = (Button) findViewById(R.id.BtnDecimal);

        textView = (TextView)findViewById(R.id.textView1);

        Btn0.setOnClickListener(buttonListener);
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
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
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