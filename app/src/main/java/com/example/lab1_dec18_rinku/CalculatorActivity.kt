package com.example.lab1_dec18_rinku

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.CalendarContract.Colors
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab1_dec18_rinku.ui.theme.Lab1_Dec18_RinkuTheme

class CalculatorActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab1_Dec18_RinkuTheme {
                Scaffold(
                    modifier = Modifier,
                    topBar = {
                        TopAppBar(
                            title = {
                                Text("Calculator")
                            },
                            modifier = Modifier.background(Color.LightGray)
                        )
                    }
                ) { paddingValue ->
                    CalculatorApp(paddingValue)
                }
            }
        }
    }
}

@Composable
fun CalculatorApp(paddingValue: PaddingValues) {
    var calculator = remember { Calculator2() }
    var result = remember { mutableStateOf("0") }
    var history = remember { mutableStateOf("") }
    var isAdvanced = remember { mutableStateOf(false) }

    CalculatorUI(
        paddingValue,
        onButtonClick = { value ->
            when (value) {
                "=" -> {
                    result.value = calculator.calculate().toString()
                    if (isAdvanced.value) {
                        history.value = calculator.getHistory()
                    }
                }

                "C" -> {
                    calculator.clear()
                    result.value = "0"
                }

                else -> {
                    if (result.value == "0") {
                        result.value = value
                    } else {
                        result.value = result.value + " " + value
                    }
                    calculator.push(value)
                }
            }
        },
        result = result.value,
        history = history.value,
        toggleMode = { isAdvanced.value = !isAdvanced.value },
        isAdvanced = isAdvanced.value
    )
}

@Composable
fun CalculatorUI(
    paddingValue: PaddingValues,
    onButtonClick: (String) -> Unit,
    result: String,
    history: String,
    toggleMode: () -> Unit,
    isAdvanced: Boolean
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValue).padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = result,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.LightGray)
                .padding(16.dp),
            fontSize = 24.sp
        )


        // Buttons
        val buttons = listOf(
            listOf("7", "8", "9", "+"),
            listOf("4", "5", "6", "-"),
            listOf("1", "2", "3", "*"),
            listOf("C", "0", "=", "/")
        )

        for (row in buttons) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { label ->
                    Button(
                        onClick = {
                            onButtonClick(label)
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = label, fontSize = 20.sp)
                    }
                }
            }
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = toggleMode,
        ) {
            Text(text = if (isAdvanced) "Standard – No History" else "Advance – With History")
        }
        // History (Visible only in advanced mode)
        if (isAdvanced) {
            Text(
                text = history,
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.LightGray)
                    .padding(16.dp),
                fontSize = 16.sp
            )
        }
    }
    // Toggle button
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab1_Dec18_RinkuTheme {
        Greeting("Android")
    }
}