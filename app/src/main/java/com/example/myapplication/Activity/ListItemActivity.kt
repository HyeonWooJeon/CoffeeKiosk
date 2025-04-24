package com.example.myapplication.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.CHLog
import com.example.myapplication.MainViewModel.MainViewModel
import com.example.myapplication.Model.ItemsModel
import com.example.myapplication.R
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.firebase.Firebase
import com.google.firebase.storage.storage

class ListItemActivity:ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
                val viewModel: MainViewModel by viewModels()
                val itemId = intent.getStringExtra("categoryId")?:""
                val titleName = intent.getStringExtra("Category")?:""
                val DBItems by viewModel.loadFiltered(itemId).observeAsState(initial = emptyList())
                CHLog.d("DBItems", "FireBaseStorage1 $DBItems")

                if (DBItems.isNotEmpty()) {
                    CHLog.d("DBItems", "FireBaseStorage2 $DBItems")
                }
            MyApplicationTheme {
                ItemListColumn(DBItems,titleName)
            }
        }
    }
}

@Composable
fun ItemListColumn(DBItems: List<ItemsModel>,titleName:String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        if (DBItems.isEmpty()) {
            // 데이터 로딩 중일 때 로딩 메시지 표시
            Text(
                text = "Loading...",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            // 데이터가 로드되었을 때 UI 표시
            Column(modifier = Modifier
                .fillMaxWidth()
                .background(Color.White.copy(alpha = 0.2f))
                .clip(RoundedCornerShape(10.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(20.dp)
                        .background(Color.White.copy(alpha = 0.4f))
                        .border(
                            border = BorderStroke(1.dp, Color.Transparent),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(10.dp))
                    Image(
                        painter = painterResource(id = R.drawable.menu),
                        contentDescription = "menu Icon",
                        modifier = Modifier.size(24.dp)
                    )
                    // 첫 번째 아이템의 title을 사용하여 텍스트 표시
                    Text(
                        text = titleName,
                        color = Color.White,
                        modifier = Modifier.padding(start = 15.dp),
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 5.dp, horizontal = 24.dp)
                ) {
                    items(DBItems) { item ->
                        ItemList(item)
                    }
                }
            }
        }
    }
}
@Composable
fun ItemList(item: ItemsModel) {
    val context = LocalContext.current
//    val storageReference = Firebase.storage.reference
//     val storage = Firebase.storage
//    CHLog.d("Log",storage.toString())

    // 카드 형태로 아이템 표시
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
//            Image(
//                painter = rememberImagePainter(
//                    data = item.picUrl.toString(),
//                    builder = {
//                        crossfade(false)
//                        placeholder(R.drawable.ic_launcher_foreground)
//                    }
//                ),
//                contentDescription = "description",
//                contentScale = ContentScale.Crop
//            )
            val tt = item.picUrl[0]

            Image(
                painter = rememberAsyncImagePainter(model = "https://"+"${tt}"),
                contentDescription = null,
                modifier = Modifier.size(90.dp),
                contentScale = ContentScale.FillWidth
            )

//            AndroidView(
//                modifier = Modifier.size(50.dp),
//                factory = { context ->
//                    ImageView(context).apply {
//                        scaleType = ImageView.ScaleType.CENTER_CROP
//                    }
//                },
//                update = { imageView ->
//                    Glide.with(imageView.context)
//                        .load(item.picUrl)
//                        .into(imageView)
//                }
//            )

//
//            AsyncImage(
//                model = item.picUrl.toString(),
//                contentDescription = "Item Image",
//                modifier = Modifier.size(50.dp),
//                placeholder = painterResource(R.drawable.ic_launcher_foreground),
//            )

//            AsyncImage(
//                model = ImageRequest.Builder(context)
//                    .data(item)
//                    .crossfade(true)
//                    .build(),
//                contentDescription = "Item Image",
//                contentScale = ContentScale.Crop, // 원하는 contentScale을 설정
//                modifier = Modifier.size(50.dp),
//                onState = { state ->
//                    when (state) {
//                        is AsyncImagePainter.State.Success -> {
//                            CHLog.d("listItem","Image Load Success")
//                        }
//                        is AsyncImagePainter.State.Error -> {
//                            CHLog.d("listItem","${state.result.throwable.message}")
//                        }
//                        else -> {}
//                    }
//                }
//            )

            // 디버깅 로그
            CHLog.d("ListItem", item.toString())
            Spacer(modifier = Modifier.width(16.dp))

            // 아이템 정보 표시
            Column(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight()
                    .clickable {
                        // 아이템 클릭 시 상세 페이지로 이동
                        val intent = Intent(context, DetailActivity::class.java).apply {
                            putExtra("title", item.title)
                        }
                        context.startActivity(intent)
                    },
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = item.title,
                    modifier = Modifier.padding(bottom = 8.dp),
                    style = MaterialTheme.typography.titleMedium // 스타일 추가 가능
                )
                Text(
                    text = "가격: ${item.price}",
                    modifier = Modifier.padding(bottom = 4.dp),
                    style = MaterialTheme.typography.bodyMedium // 스타일 추가 가능
                )
                Text(
                    text = item.extra,
                    style = MaterialTheme.typography.bodySmall // 스타일 추가 가능
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun ListItemActivityPreview() {
    MyApplicationTheme {
//        ItemListColumn(items)
    }
}