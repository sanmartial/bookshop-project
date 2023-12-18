package com.globaroman.bookshopproject.dto;

public record BookSearchParameters(String[] title, String[] author, String[] isbn) {
}
