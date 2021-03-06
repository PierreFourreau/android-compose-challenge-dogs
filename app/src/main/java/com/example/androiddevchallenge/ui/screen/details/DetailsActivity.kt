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
package com.example.androiddevchallenge.ui.screen.details

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.Dog
import com.example.androiddevchallenge.ui.screen.list.MainActivity.Companion.EXTRA_DOG_ID
import com.example.androiddevchallenge.ui.theme.AdoptDogTheme

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel = viewModels<DetailsViewModel>()

            intent.extras?.getString(EXTRA_DOG_ID)?.let { dogId ->
                viewModel.value.initDetails(dogId)
            }
            AdoptDogTheme {
                val dog = viewModel.value.dog.collectAsState()
                DetailsScreen(dog = dog.value)
            }
        }
    }
}

@Composable
fun DetailsScreen(dog: Dog) {
    val context = LocalContext.current
    Column {
        Image(
            painter = painterResource(dog.drawableId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(400.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = dog.name,
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 64.sp,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.width(24.dp))
        DetailsRow(R.drawable.shelter, "Shelter : ${dog.shelter}")
        DetailsRow(R.drawable.race, "Race : ${dog.race}")
        DetailsRow(R.drawable.size, "Size : ${dog.size}")
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = {
                Toast.makeText(context, "Thanks dude", Toast.LENGTH_LONG).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(
                text = "Adopt me !",
                fontSize = 22.sp,
            )
        }
    }
}

@Composable
fun DetailsRow(icon: Int, text: String) {
    Row(
        modifier = Modifier
            .padding(16.dp, 8.dp, 16.dp, 8.dp)
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier
                .height(24.dp)
                .width(24.dp)
                .fillMaxWidth()
        )
        Text(
            text = text,
            fontSize = 22.sp,
            modifier = Modifier
                .padding(4.dp, 0.dp, 0.dp, 0.dp)
        )
    }
}
