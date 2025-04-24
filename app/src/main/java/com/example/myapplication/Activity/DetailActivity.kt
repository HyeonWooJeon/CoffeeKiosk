package com.example.myapplication.Activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.CHLog
import com.example.myapplication.MainViewModel.MainViewModel
import com.example.myapplication.Model.ItemsModel
import com.example.myapplication.R
import com.example.myapplication.ui.theme.MyApplicationTheme

class DetailActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                val title = intent.getStringExtra("title")?:""
                val viewModel: MainViewModel by viewModels()
                val DBTitle by viewModel.loadTitle(title).observeAsState(initial = null)

            MyApplicationTheme {
                when {
                    DBTitle == null -> Text(
                        "Loading...",
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center),
                        color = Color.White
                    )
                    DBTitle!!.isEmpty() -> Text(
                        "No data available",
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center),
                        color = Color.White
                    )
                    else -> detailView(DBTitle!!.first())
                }
            }
        }
    }
}
@Composable
fun detailView(item: ItemsModel) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Gray)) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Center)
        ) {
val view = item.picUrl[0]

            Image(
                painter = rememberAsyncImagePainter(
                    model = "https://"+"${view}",
                    error = painterResource(R.drawable.ic_launcher_foreground) // 에러 시 보여줄 대체 이미지
                ),
                contentDescription = "Detail Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(16.dp))
Column(modifier = Modifier
    .fillMaxWidth()
    .height(360.dp)
    .padding(start = 5.dp,end=5.dp, bottom = 10.dp, top = 20.dp)
    .clip(RoundedCornerShape(30.dp))
    .background(Color.White.copy(alpha = 0.2f)),verticalArrangement = Arrangement.Bottom) {
            Text(
                text = item.title,
                color = Color.White,
                modifier = Modifier.align(CenterHorizontally),
                fontSize = 40.sp,
                lineHeight = 40.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "가격 : ${item.price}",
                color = Color.White,
                fontSize = 30.sp,
                modifier = Modifier.padding(start = 10.dp, end =10.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "설명 : ${item.description}",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.padding(10.dp)
            )
    Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
}

