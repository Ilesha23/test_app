package com.ilesha.testapp.ui.screen

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.ilesha.testapp.R
import com.ilesha.testapp.domain.model.Entity

@Composable
fun MainScreen() {
    val viewModel: MainScreenViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MainScreenContent(
        uiState = uiState,
        onNextClicked = viewModel::onNextClicked,
        onRefreshClicked = viewModel::getCurrentItem
    )
}

@Composable
fun MainScreenContent(
    uiState: MainScreenUiState,
    onNextClicked: () -> Unit,
    onRefreshClicked: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (uiState) {
            is MainScreenUiState.Content -> MainScreenContentContainer(
                onNextClicked = onNextClicked,
                content = {
                    when (uiState.currentEntity) {
                        Entity.Game -> {}
                        is Entity.Image -> EntityImageContent(uiState.currentEntity.url)

                        is Entity.Text -> EntityTextContent(uiState.currentEntity.message)

                        is Entity.WebView -> EntityWebContent(uiState.currentEntity.url)
                    }
                }
            )

            is MainScreenUiState.Error -> ErrorBox(
                message = uiState.message,
                onRefreshClicked = onRefreshClicked
            )

            is MainScreenUiState.Initial, MainScreenUiState.Loading -> LoadingBox()
        }
    }

}

@Composable
fun MainScreenContentContainer(
    onNextClicked: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        content()
        ContentButton(
            text = stringResource(R.string.main_screen_button_next),
            onClick = onNextClicked,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
        )
    }
}

@Composable
fun EntityTextContent(
    text: String
) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier
            .fillMaxWidth(0.8f)
    )
}

@Composable
fun EntityImageContent(
    url: String
) {
    AsyncImage(
        model = url,
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .fillMaxSize()
    )
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun EntityWebContent(
    url: String
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                )
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
            }
        },
        update = { webView ->
            webView.loadUrl(url)
        }
    )
}

@Composable
fun LoadingBox() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Text(
                text = stringResource(R.string.main_screen_loading)
            )
        }
    }
}

@Composable
fun ErrorBox(
    message: String,
    onRefreshClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = message,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.8f)
        )
        ContentButton(
            text = stringResource(R.string.main_screen_button_retry),
            onClick = onRefreshClicked,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
        )
    }
}

@Composable
fun ContentButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 10.dp,
            pressedElevation = 3.dp
        ),
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp)
        )
    }
}