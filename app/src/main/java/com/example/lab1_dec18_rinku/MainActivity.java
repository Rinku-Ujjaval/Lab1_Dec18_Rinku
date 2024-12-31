package com.example.lab1_dec18_rinku;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView display;
    private TextView historyDisplay;
    private Button modeToggleButton;
    private Calculator calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.result_view);
        historyDisplay = findViewById(R.id.history_view);
        modeToggleButton = findViewById(R.id.toggle_mode_button);
        calculator = new Calculator();

        // Set initial visibility for history
        historyDisplay.setVisibility(View.GONE);

        int[] buttonIds = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6, R.id.button7,
                R.id.button8, R.id.button9, R.id.plus, R.id.minus,
                R.id.multiply, R.id.divide, R.id.equals, R.id.clear
        };

        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(this::onButtonClick);
        }

        modeToggleButton.setOnClickListener(v -> toggleMode());
    }

    @SuppressLint("SetTextI18n")
    private void onButtonClick(View view) {
        Button button = (Button) view;
        String value = button.getText().toString();

        switch (value) {
            case "C": // Clear button
                calculator = new Calculator();
                display.setText("0");
                historyDisplay.setText("");
                break;

            case "=": // Equals button
                try {
                    int result = calculator.calculate();
                    display.setText(String.valueOf(result));

                    if (calculator.isAdvancedMode()) {
                        historyDisplay.setText(calculator.getHistory());
                    }
                } catch (ArithmeticException e) {
                    display.setText("Error");
                }
                break;

            default: // Digits and operators
                calculator.push(value);
                String currentDisplay = display.getText().toString();
                display.setText(currentDisplay.equals("0") ? value : currentDisplay + " " + value);
                break;
        }
    }

    private void toggleMode() {
        calculator.toggleMode();

        if (calculator.isAdvancedMode()) {
            modeToggleButton.setText("Standard – No History");
            historyDisplay.setVisibility(View.VISIBLE);
            historyDisplay.setText(calculator.getHistory());
        } else {
            modeToggleButton.setText("Advance – With History");
            historyDisplay.setVisibility(View.GONE);
        }
    }
}
