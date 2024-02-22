package com.example.businesscard

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.businesscard.ui.theme.BusinessCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusinessCardTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "main") {
                        composable("main") {
                            MainScreen(navController)
                        }
                        composable("about") {
                            AboutScreen(navController)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun MainScreen(navController: NavController) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.background_business_card),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NameTitleLogo(
                image = painterResource(id = R.drawable.profile_pic),
                name = stringResource(id = R.string.full_name),
                title = stringResource(id = R.string.occupation)
            )
            Spacer(modifier = Modifier.size(80.dp))
            ContactInformation(
                phoneNumber = stringResource(R.string.phone_number),
                socials = stringResource(R.string.instagram_profile),
                instagramIcon = painterResource(id = R.drawable.instagram_icon),
                email = stringResource(R.string.email_address),
                instagramUri = stringResource(R.string.instagram_uri)
            )
            Spacer(modifier = Modifier.size(40.dp))
            Button(
                onClick = {
                    navController.navigate("about")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                ),
                border = BorderStroke(1.dp, Brush.radialGradient(
                    colors = listOf(Color.Black, Color(0xFF113D7F), Color.White),
                    center = Offset(1 / 2.0f, 1 / 2.0f),
                    radius = 32f
                ))
            ) {
                Text(stringResource(R.string.about_me_button_text))
            }
        }
    }

    @Composable
    fun NameTitleLogo(
        image: Painter,
        name: String,
        title: String,
    ) {
        Image(
            painter = image,
            contentDescription = "Picture of Kicke",
            modifier = Modifier
                .size(192.dp)
                .clip(CircleShape)
                .border(2.dp, Color.LightGray, CircleShape),
            contentScale = ContentScale.FillWidth
        )
        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.White
        )
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }

    @SuppressLint("QueryPermissionsNeeded")
    @Composable
    fun ContactInformation(
        phoneNumber: String,
        socials: String,
        email: String,
        instagramUri: String,
        instagramIcon: Painter,
        modifier: Modifier = Modifier
    ) {
        val context = LocalContext.current
        Column(
            modifier = modifier.padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.Call,
                    contentDescription = "Phone image",
                    modifier = Modifier
                        .clickable {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:$phoneNumber"))
                            context.startActivity(intent)
                        }
                        .padding(start = 16.dp, end = 16.dp)
                        .size(32.dp),
                    tint = Color.Black
                )
                Text(
                    text = phoneNumber,
                    textAlign = TextAlign.Justify,
                    color = Color.White
                )
            }
            Row(
                modifier = Modifier
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(instagramUri))
                        context.startActivity(intent)
                    }
                    .padding(top = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .size(32.dp),
                    painter = instagramIcon,
                    contentDescription = "Instagram icon"
                )
                Text(
                    text = socials,
                    color = Color.White
                )
            }
            Row(
                modifier = Modifier
                    .clickable {
                        val alertDialog = AlertDialog.Builder(context)
                        val editText = EditText(context)
                        editText.hint = "Enter subject"

                        alertDialog.setView(editText)
                            .setTitle("Email Subject")
                            .setPositiveButton("OK") { _, _ ->
                                val subject = editText.text.toString()
                                val intent = Intent(Intent.ACTION_SENDTO).apply {
                                    data =
                                        Uri.parse("mailto:") // This ensures only email apps respond
                                    putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                                    putExtra(
                                        Intent.EXTRA_SUBJECT,
                                        subject
                                    ) // Use the subject entered by the user
                                }
                                if (intent.resolveActivity(context.packageManager) == null) {
                                    context.startActivity(intent)
                                } else {
                                    Toast.makeText(
                                        context,
                                        "No email app found",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            .setNegativeButton("Cancel") { dialog, _ ->
                                dialog.cancel()
                            }
                            .create()
                            .show()
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.Email,
                    contentDescription = "Email icon",
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .size(32.dp),
                    tint = Color.Black
                )
                Text(
                    text = email,
                    color = Color.White
                )
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun MainScreenPreview() {
        BusinessCardTheme {
            MainScreen(navController = rememberNavController())
        }
    }
}
