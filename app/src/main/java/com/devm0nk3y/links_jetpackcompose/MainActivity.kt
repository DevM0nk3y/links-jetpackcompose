package com.devm0nk3y.links_jetpackcompose

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.devm0nk3y.links_jetpackcompose.ui.theme.LinksJetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LinksJetpackComposeTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    HyperlinkText(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun HyperlinkText(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val annotatedLinkString = buildAnnotatedString {
        val str = "Haz clic aquí para ir a Google"
        val linkedWord = "aqui"
        val startIndex = str.indexOf(linkedWord)
        val endIndex = startIndex + linkedWord.length
        append(str)
        addStyle(
            style = SpanStyle(
                color = Color.Blue,
                textDecoration = TextDecoration.Underline
            ), start = startIndex, end = endIndex
        )
        addStringAnnotation(
            tag = "URL",
            annotation = "https://www.google.com",
            start = startIndex,
            end = endIndex
        )
    }

    ClickableText(
        text = annotatedLinkString,
        modifier = Modifier
            .padding(16.dp),
        onClick = { offset ->
            annotatedLinkString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(annotation.item))
                    startActivity(context, intent, null)
                }
        }
    )
}