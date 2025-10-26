package com.ilesha.testapp.domain.model

sealed class Entity {

    data class Text(val message: String) : Entity()
    data class WebView(val url: String) : Entity()
    data class Image(val url: String) : Entity()
    object Game : Entity()

}