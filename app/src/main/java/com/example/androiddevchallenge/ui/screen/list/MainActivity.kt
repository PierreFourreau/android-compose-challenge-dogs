/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.screen.list

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.Dog
import com.example.androiddevchallenge.ui.screen.details.DetailsActivity
import com.example.androiddevchallenge.ui.screen.list.MainActivity.Companion.EXTRA_DOG_ID
import com.example.androiddevchallenge.ui.theme.AdoptDogTheme

class MainActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DOG_ID = "EXTRA_DOG_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = viewModels<MainViewModel>()
            AdoptDogTheme {
                val dogs = viewModel.value.dogs.collectAsState()
                MyApp(dogs = dogs.value)
            }
        }
    }
}

@Composable
fun MyApp(dogs: List<Dog>) {
    val context = LocalContext.current
    Surface(color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier
                .padding(16.dp, 16.dp, 16.dp, 0.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "My puppies list",
                color = Color.LightGray,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                dogs.forEach {
                    item {
                        DogCell(it) {
                            context.startActivity(
                                Intent(
                                    context,
                                    DetailsActivity::class.java
                                ).apply {
                                    putExtra(EXTRA_DOG_ID, it.id)
                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun DogCell(dog: Dog, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        ) {
            Image(
                painter = painterResource(dog.drawableId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(128.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = dog.name,
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = dog.race,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = "Age : ${dog.year}",
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    AdoptDogTheme {
        MyApp(
            dogs = listOf(
                Dog(
                    "1",
                    "Jaycee",
                    "Refuge la ferme des arches",
                    R.drawable.jaycee,
                    4,
                    "Medium",
                    "sShiba Inu"
                ),
                Dog(
                    "1",
                    "James",
                    "Refuge Le Moulin d'en Haut",
                    R.drawable.james,
                    5,
                    "Large",
                    "Brachet"
                ),
                Dog(
                    "1",
                    "Jamie",
                    "Maison SPA",
                    R.drawable.jamie,
                    2,
                    "Small",
                    "Spaniel"
                ),
                Dog(
                    "1",
                    "James",
                    "Refuge Le Moulin d'en Haut",
                    R.drawable.james,
                    5,
                    "Large",
                    "Brachet"
                ),
            )
        )
    }
}
