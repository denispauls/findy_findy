package com.example.findy.presentation.signup_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.findy.R
import com.example.findy.navigation.Screens
import com.example.findy.ui.theme.RegularFont
import com.example.findy.ui.theme.lightBlue
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel()
){
    var email by rememberSaveable{
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = viewModel.signUpState.collectAsState(initial = null)

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(start = 30.dp, end = 30.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(text = "Findy", fontWeight = FontWeight.Bold, fontSize = 100.sp, color = Color.Black, fontFamily = RegularFont)
        Text(text = "Gebe deine Daten ein", fontWeight = FontWeight.Medium, fontSize = 15.sp, color = Color.Gray, fontFamily = RegularFont)

        TextField(value = email, onValueChange = {
            email = it
        }, modifier = Modifier.fillMaxWidth(), colors = TextFieldDefaults.textFieldColors(backgroundColor = lightBlue, cursorColor = Color.Black, disabledLabelColor = lightBlue, unfocusedIndicatorColor = Color.Transparent, focusedIndicatorColor = Color.Transparent ),
            shape = RoundedCornerShape(8.dp), singleLine = true, placeholder = {
                Text(text = "Email")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(value = password, onValueChange = {
            password = it
        }, modifier = Modifier.fillMaxWidth(), colors = TextFieldDefaults.textFieldColors(backgroundColor = lightBlue, cursorColor = Color.Black, disabledLabelColor = lightBlue, unfocusedIndicatorColor = Color.Transparent, focusedIndicatorColor = Color.Transparent ),
            shape = RoundedCornerShape(8.dp), singleLine = true, placeholder = {
                Text(text = "Passwort")
            }
        )

       Button(onClick = {
            scope.launch {
                viewModel.registerUser(email,password)
            }
       }, modifier = Modifier
           .fillMaxWidth()
           .padding(top = 20.dp, start = 30.dp, end = 30.dp), colors = ButtonDefaults.buttonColors(
           backgroundColor = Color.Black, contentColor = Color.White
           ), shape = RoundedCornerShape(15.dp)
       ) {
            Text(text = "Konto eröffnen", color = Color.White, modifier = Modifier.padding(7.dp))

        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            if (state.value?.isLoading == true){
                CircularProgressIndicator()
            }
        }
        Text(text = "Schon einen Account? Dann melde dich an", fontWeight = FontWeight.Bold, color = Color.Black, fontFamily = RegularFont)
        Text(text = "oder verbinde dich mit", fontWeight = FontWeight.Medium, color = Color.Gray)
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp), horizontalArrangement = Arrangement.Center) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(painter = painterResource(id = R.drawable.ic_google), contentDescription = "Google Icon", modifier = Modifier.size(50.dp), tint = Color.Unspecified)
            }

            LaunchedEffect(key1 = state.value?.isSuccess){
                scope.launch {
                    if (state.value?.isSuccess?.isNotEmpty() == true){
                        val success = state.value?.isSuccess
                        Toast.makeText(context, "${success}", Toast.LENGTH_LONG).show()
                    }
                }
            }
            LaunchedEffect(key1 = state.value?.isError){
                scope.launch {
                    if (state.value?.isError?.isNotEmpty() == true){
                        val error = state.value?.isError
                        Toast.makeText(context, "${error}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}