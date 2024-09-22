package com.example.oliviama_shoppinglistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.oliviama_shoppinglistapp.ui.theme.OliviaMaShoppingListAppTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.ui.Alignment
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OliviaMaShoppingListAppTheme {
                Scaffold(modifier = Modifier
                    .fillMaxSize()
                    .padding(WindowInsets.statusBars.asPaddingValues())) { innerPadding ->
                    ShoppingListApp(modifier =
                    Modifier.displayCutoutPadding().padding(innerPadding))
                }
            }
        }
    }
}

data class Item(
    var name: String,
    var quantity: String
)

@Composable
fun ShoppingListApp(modifier: Modifier = Modifier) {
    var items = remember { mutableStateListOf<Item>()}
    var showDialog by remember { mutableStateOf(false) }
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { showDialog = true },
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 16.dp)
                    .weight(1f)
            ) {
                Text("Add Item")
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(items.size) { index ->
                ShoppingListItem(items[index])
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(onClick = {
                            if (itemName.isNotBlank() && itemQuantity.isNotBlank()) {
                                val newItem = Item(
                                    name = itemName,
                                    quantity = itemQuantity
                                )
                                items.add(newItem)
                                showDialog = false
                                itemName = ""
                                itemQuantity = ""
                            }
                        }) {
                            Text(text = "Add")
                        }
                        Button(onClick = {
                            showDialog = false
                            itemName = ""
                            itemQuantity = ""
                        }) {
                            Text(text = "Cancel")
                        }
                    }
                },
                title = { Text(text = "Add Shopping Item") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = itemName,
                            onValueChange = { itemName = it },
                            label = { Text("Item:") },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                        OutlinedTextField(
                            value = itemQuantity,
                            onValueChange = { itemQuantity = it },
                            label = { Text("Quantity:") },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun ShoppingListItem(item: Item){
    var checked by remember { mutableStateOf(false) }
    Row(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,)
    {
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it }
        )

        Text(text = item.name,
            modifier = Modifier.padding(8.dp),
            style = TextStyle(
            textDecoration = if (checked) TextDecoration.LineThrough else TextDecoration.None
        )  )
        Text(text = "Quantity: ${item.quantity}",
            modifier = Modifier.padding(8.dp),
            style = TextStyle(
            textDecoration = if (checked) TextDecoration.LineThrough else TextDecoration.None
        ))
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingListAppPreview() {
    ShoppingListApp()
}