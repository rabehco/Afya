package com.example.afya

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.afya.ui.theme.AfyaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AfyaTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(selectedTab) { selectedTab = it }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFFF5F5F5)) // Light background color
        ) {
            FirstUI()
        }
    }
}

@Composable
fun FirstUI() {
    var searchQuery by remember { mutableStateOf("") }

    val medicines = listOf(
        "Aviptect sirop" to R.drawable.medicine1,
        "Otrivin" to R.drawable.medicine2,
        "Voltaren" to R.drawable.medicine3,
        "Nurofen enfants" to R.drawable.medicine4,
        "Panadol" to R.drawable.medicine5,
        "Advil" to R.drawable.medicine6
    )

    val displayedItems = if (searchQuery.isEmpty()) medicines else
        medicines.filter { it.first.contains(searchQuery, ignoreCase = true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // **Top Image Banner**
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Afya Logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)




        )

        // **Search Bar**
        SearchInputBar(searchQuery, onSearch = { query -> searchQuery = query })

        // **Medicine Grid (2 per row) with Proper Spacing**
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(displayedItems.chunked(2)) { _, rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    for ((medicineName, imageRes) in rowItems) {
                        MedicineCard(medicineName, imageRes, Modifier.weight(1f))
                    }
                    if (rowItems.size == 1) Spacer(modifier = Modifier.weight(1f)) // Balance the row
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    NavigationBar(
        containerColor = Color.White
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = selectedTab == 0,
            onClick = { onTabSelected(0) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
            label = { Text("Search") },
            selected = selectedTab == 1,
            onClick = { onTabSelected(1) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = selectedTab == 2,
            onClick = { onTabSelected(2) }
        )
    }
}

@Composable
fun SearchInputBar(searchQuery: String, onSearch: (String) -> Unit) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearch,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        placeholder = { Text("Search medicine...") }
    )
}

@Composable
fun MedicineCard(medicineName: String, imageRes: Int, modifier: Modifier) {
    Card(
        modifier = modifier
            .padding(1.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Medicine Image",
                modifier = Modifier
                    .size(150.dp)
                    .padding(bottom = 8.dp)
            )

            Text(
                text = medicineName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            // **Buttons (Same Size, Icons Added)**
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { /* Call Action */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    modifier = Modifier.weight(0.2f)
                ) {
                    Icon(Icons.Filled.Call, contentDescription = "Call", tint = Color.White)
                    Spacer(modifier = Modifier.width(0.2.dp))
                    Text("Call", fontSize = 0.2.sp, color = Color.White)
                }

                Button(
                    onClick = { /* Show Description */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                    modifier = Modifier.weight(0.2f)
                ) {
                    Icon(Icons.Filled.Info, contentDescription = "Info", tint = Color.White)
                    Spacer(modifier = Modifier.width(0.2.dp))
                    Text("Description", fontSize = 0.2.sp, color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FirstUIPreview() {
    AfyaTheme {
        MainScreen()
    }
}
