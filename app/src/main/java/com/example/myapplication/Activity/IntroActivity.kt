package com.example.myapplication.Activity


import android.content.Intent
import android.os.Build

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay

class IntroActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                introBG()

            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun introBG(){
    val context = LocalContext.current
    Box(modifier = Modifier
        .fillMaxSize()) { Image(painter = painterResource(id = R.drawable.intro_bg), contentDescription = "", contentScale = ContentScale.FillWidth)
        Column(Modifier.fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {
            Text(text = "coffee Shop", fontSize = 80.sp,
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.pristina)) // 폰트 지정
                             ),
                color = Color.White,
            modifier = Modifier.padding(bottom = 20.dp))
        }
    }
    LaunchedEffect(Unit){
        delay(1500)
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }
}


@RequiresApi(Build.VERSION_CODES.Q)
@Preview(showBackground = true)
@Composable
fun introBGPreview() {
    MyApplicationTheme {
        introBG()
    }
}