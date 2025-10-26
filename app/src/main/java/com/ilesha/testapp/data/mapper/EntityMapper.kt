package com.ilesha.testapp.data.mapper

import com.ilesha.testapp.data.dto.ItemDto
import com.ilesha.testapp.domain.model.Entity

fun ItemDto.toDomainModel(): Entity {
    return when (this.type) {
        "text" -> {
            Entity.Text(message ?: throw IllegalStateException("Text: missing message"))
        }
        "webview" -> {
            Entity.WebView(url ?: throw IllegalStateException("WebView: missing url"))
        }
        "image" -> {
            Entity.Image(url ?: throw IllegalStateException("Image: missing url"))
        }
        else -> {
            Entity.Game
        }
    }
}