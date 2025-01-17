package org.example.project.book.presentation.book_list

import org.example.project.book.domain.Book
import org.example.project.core.presentation.UiText

data class BookListState(
    val searchQuery:String = "Kotlin",
    val searchResult:List<Book> = books,
    val favoriteBooks:List<Book> = emptyList(),
    val isLoading:Boolean = false,
    val selectedTabIndex:Int = 0,
    val errorMessages: UiText? = null
)

val books = (1..10).map {
    Book(
id = it.toString(),
        title = "Book $it",
        imageUrl = "https://picsum.photos/200",
        authors = listOf("Zalo Austine"),
        description = "Description $it",
        languages = emptyList(),
        firstPublishYear = null,
        averageRating = 5.55555,
        ratingCount = 5,
        numPages = 45,
        numEditions = 6
    )
}