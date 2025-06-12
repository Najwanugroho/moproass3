package com.najwashaqilaisnan607062300125.a607062300125_najwashaqila_moproass3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.najwashaqilaisnan607062300125.a607062300125_najwashaqila_moproass3.Screen.MainScreen
import com.najwashaqilaisnan607062300125.a607062300125_najwashaqila_moproass3.ui.theme._607062300125_najwashaqila_moproass3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _607062300125_najwashaqila_moproass3Theme {
              MainScreen()
                }
            }
        }
    }


