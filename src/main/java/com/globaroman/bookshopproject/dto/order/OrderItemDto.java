package com.globaroman.bookshopproject.dto.order;

public record OrderItemDto(Long id, Long bookId, int quantity) {
}
