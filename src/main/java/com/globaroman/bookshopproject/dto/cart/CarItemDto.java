package com.globaroman.bookshopproject.dto.cart;

public record CarItemDto(Long id, Long bookId, String bookTitle, int quantity) {
}
