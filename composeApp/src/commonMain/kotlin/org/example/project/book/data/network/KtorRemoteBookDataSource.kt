package org.example.project.book.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.example.project.book.data.dto.SearchResponseDto
import org.example.project.core.data.safeCall
import org.example.project.core.domain.DataError
import org.example.project.core.domain.Result

private const val BASE_URL = "https://openlibrary.org"
class KtorRemoteBookDataSource(private val httpClient: HttpClient):RemoteBookDataSource {
    override suspend fun searchBooks(
        query: String,
        resultLimit: Int?
    ): Result<SearchResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "$BASE_URL/search.json"
            ){
                parameter("q",query)
                parameter("limit",resultLimit)
                parameter("language","eng")
                parameter("fields", "key,title,author_name,author_key,cover_edition_key,cover_i,ratings_average,ratings_count,first_publish_year,language,number_of_pages_median,edition_count")
            }
        }
    }
}