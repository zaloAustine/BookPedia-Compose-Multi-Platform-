/**
 * Created by  Zalo Austine
 * Date: 16/01/2025
 */
package org.example.project.book.presentation.book_list

import org.example.project.book.domain.Book

sealed interface BookListAction {
    data class OnSearchQueryChange(val query:String): BookListAction
    data class OnBookClick(val book: Book): BookListAction
    data class  OnTabSelected(val index:Int): BookListAction
}