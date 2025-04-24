package com.example.myapplication.Activity


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.MainViewModel.MainViewModel
import com.example.myapplication.R
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
            MyApplicationTheme {
                productList(products= getProductList())
            }
        }
    }
}
fun getProductList(): List<ProductType> {
    return listOf(
        ProductType(id = "1",
            Type = "",
            color = Color.White,
            image = R.drawable.ic_1,
            Title = "Ice Drink"
        ),
        ProductType(id = "2",
            Type = "",
            color = Color.White,
            image = R.drawable.ic_2,
            Title = "Hot Drink",
         ),
        ProductType(id = "3",
            Type = "",
            color = Color.White,
            image = R.drawable.ic_3,
            Title = "Hot Coffee",
            ),
        ProductType(id = "4",
            Type = "",
            color = Color.White,
            image = R.drawable.ic_4,
            Title = "Ice Coffee",
          ),
        ProductType(id = "5",
            Type = "",
            color = Color.White,
            image = R.drawable.ic_5,
            Title = "Brewing Coffee",
         ),
        ProductType(id = "6",
            Type = "",
            color = Color.White,
            image = R.drawable.ic_6,
            Title = "Shake",
           ),
        ProductType(id = "7",
            Type = "",
            color = Color.White,
            image = R.drawable.ic_7,
            Title = "Resturant",
           ),
        ProductType(id = "8",
            Type = "",
            color = Color.White,
            image = R.drawable.ic_8,
            Title = "Breakfast",
        ),
        ProductType(id = "9",
            Type = "",
            color = Color.White,
            image = R.drawable.ic_9,
            Title = "Cake"
        )
    )
}
@Composable
fun productList(products: List<ProductType>){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(painter = painterResource(id = R.drawable.menu_bg), contentDescription = "")
        Text(
            text = "Good Morning",
            fontSize = 60.sp,
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.pristina)) // 폰트 지정
            ),
            color = Color.White,
            modifier = Modifier.padding(top = 40.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            TypeGrid(productList = products)
        }
    }
}

@Composable
fun TypeGrid(productList: List<ProductType>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(50.dp))
            .padding(10.dp)
            .background(Color.White.copy(alpha = 0.2f)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Select Category",
            fontSize = 40.sp,
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.pristina))
        )
        Spacer(modifier = Modifier.height(20.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(3), // 3 열로 고정
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            items(productList) { product ->
                TypeCard(item = product)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun TypeCard(item: ProductType) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .size(100.dp)
                .clickable {
                    val intent = Intent(context, ListItemActivity::class.java).apply {
                        putExtra("categoryId", item.id)
                        putExtra("Category", item.Title,)
                    }
                    context.startActivity(intent)
                },
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent // Card 자체의 배경을 투명하게 설정
            ), // Card 자체의 배경을 투명하게 설정
            //elevation = 0.dp // 필요에 따라 그림자 제거
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.4f)) // Card 내부 배경에만 투명도 적용
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {
                    Image(
                        painter = painterResource(id = item.image),
                        contentDescription = item.Title,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        contentScale = ContentScale.Inside
                    )
                    Text(text = item.Title, color = Color.White, fontWeight = FontWeight.Medium, fontSize = 12.sp , modifier = Modifier.padding(bottom = 2.dp))
                }
            }
        }
    }
}

data class ProductType(
    val Type: String,
    val Title: String,
    @DrawableRes
    val image: Int,
    val color: Color,
    val id: String,


)

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    MyApplicationTheme {
        productList(products= getProductList())
    }
}