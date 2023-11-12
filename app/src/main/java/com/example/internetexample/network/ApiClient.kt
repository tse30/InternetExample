package com.example.internetexample.network

import com.example.internetexample.models.Post
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.accept
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json

object ApiClient {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com"
    private const val BLOG_POST = "$BASE_URL/posts"

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json()
        }
        // Default request for POST, PUT, DELETE,etc...
        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            //add this accept() for accept Json Body or Raw Json as Request Body
            accept(ContentType.Application.Json)
        }
    }

    suspend fun getAllPosts(): List<Post> = client.get(BLOG_POST).body()

    suspend fun createNewPost(newPost: Post): Post = client.post(BLOG_POST) {
        setBody(newPost)
    }.body()

    suspend fun updatePost(id: Int, post: Post): Post = client.put("$BLOG_POST/$id") {
        setBody(post)
    }.body()

    suspend fun deletePost(id: Int) = client.delete("$BLOG_POST/$id")

}