package com.example.calculator2;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final int ADD = 0;
    private final int SUB = 1;
    private final int MULT = 2;
    private final int DIV = 3;

    private double valueHolder = -1;
    private int operator = -1;

    private TextView calculatorText;

    //keep track of when the "=" button is clicked, do not allow overwriting
    private boolean overwriteable = true;
    //keep track of diviceByZero errors, do not allow writing after divide by zero until cleared
    boolean writeable = true;
    //keep track of if a decimal has been clicked, to prevent double decimals in a num
    private boolean decimalClicked = false;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate");
        calculatorText = findViewById(R.id.my_textview);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }

    public void onButtonClick(View view) {

        if (calculatorText.getText().toString().trim().equals("0")) {
            calculatorText.setText("");
        }

        if (!writeable) {
            if (view.getId() == R.id.AC) {
                calculatorText.setText("0");
            }
        } else {
            switch (view.getId()) {
                case R.id.my_textview:
                    break;
                case R.id.AC:
                    calculatorText.setText("0");
                    overwriteable = true;
                    decimalClicked = false;
                    break;
                case R.id.equals:
                    if (calculatorText.getText().toString().trim().equals("")) {
                        calculatorText.setText("0");
                    }
                    equation();
                    overwriteable = false;
                    decimalClicked = false;
                    break;
                case R.id.division:
                    valueHolder = Double.parseDouble(calculatorText.getText().toString());
                    calculatorText.setText("0");
                    operator = DIV;
                    overwriteable = true;
                    decimalClicked = false;
                    break;
                case R.id.plus:
                    valueHolder = Double.parseDouble(calculatorText.getText().toString());
                    calculatorText.setText("0");
                    operator = ADD;
                    overwriteable = true;
                    decimalClicked = false;
                    break;
                case R.id.minus:
                    valueHolder = Double.parseDouble(calculatorText.getText().toString());
                    calculatorText.setText("0");
                    operator = SUB;
                    overwriteable = true;
                    decimalClicked = false;
                    break;
                case R.id.times:
                    valueHolder = Double.parseDouble(calculatorText.getText().toString());
                    calculatorText.setText("0");
                    operator = MULT;
                    overwriteable = true;
                    decimalClicked = false;
                    break;
                case R.id.decimal:
                    if (decimalClicked) {
                        //do nothing, to avoid double decimals
                    } else if (calculatorText.getText().toString().equals("")) {
                        calculatorText.setText("0.");
                    } else if (overwriteable) {
                        calculatorText.setText(calculatorText.getText().toString().trim() + ".");
                    }
                    decimalClicked = true;
                    break;
                default:
                    if (calculatorText.getText().toString().equals("") || overwriteable) {
                        calculatorText.setText(calculatorText.getText().toString().trim() + ((Button) view).getText());
                    }
            }
        }
    }

    public void equation(){
        double val = Double.parseDouble(calculatorText.getText().toString().trim());
        double answer = 0;
        boolean divideByZero = false;
        switch(operator){
            case ADD:
                answer = valueHolder + val;
                valueHolder = 0;
                break;
            case SUB:
                answer = valueHolder - val;
                valueHolder = 0;
                break;
            case MULT:
                answer = valueHolder * val;
                valueHolder = 0;
                break;
            case DIV:
                if(val==0){
                    divideByZero = true;
                }else {
                    answer = valueHolder / val;
                }
                valueHolder = 0;
        }
        if(divideByZero){
            calculatorText.setText("ERROR");
            writeable = false;
        }else {
            String textToBe = ""+answer;
            int length = textToBe.length();
            if(textToBe.substring(length-2,length).equals(".0")){
                textToBe = textToBe.substring(0,length-2);
            }
            calculatorText.setText(textToBe);
        }
    }


}