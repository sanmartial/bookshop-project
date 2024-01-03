package com.globaroman.bookshopproject.dto.book;

public record BookSearchParameters(String[] title, String[] author, String[] isbn) {
}
